package Util;

import Service.ApiService;
import data.TreeNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import data.TreePackage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TreeUtil {
    public static TreeNode buildTreeBySpecifyingRoot(JsonElement orgListJson, String rootId) {
        JsonArray jsonArray = orgListJson.getAsJsonArray();

        Map<String, TreeNode> idToNodeMap = new HashMap<>();
        TreeNode root = null;

        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            String id = jsonObject.get("id").getAsString();
            String name = jsonObject.get("name").getAsString();

            TreeNode node = new TreeNode(id, name);
            idToNodeMap.put(id, node);

            //assume jsonObject.has("parentOrg")
            if (!jsonObject.get("parentOrg").isJsonNull()) {
                String parentId = jsonObject.getAsJsonObject("parentOrg").get("id").getAsString();
                node.setParentId(parentId);
            }

            if(id.equals(rootId))
                root = node;
        }
        // TODO: cut down so it only links children in the map
        for (TreeNode node : idToNodeMap.values()) {
            if (node.getParentId() != null) {
                idToNodeMap.get(node.getParentId()).getChildren().add(node);
            }
        }
        return root;
    }



    public static TreePackage buildTree(JsonElement orgListJson){
        JsonArray jsonArray = orgListJson.getAsJsonArray();

        Map<String, TreeNode> idToNodeMap = new HashMap<>();
        TreeNode root = null;

        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            String id = jsonObject.get("id").getAsString();
            String name = jsonObject.get("name").getAsString();

            TreeNode node = new TreeNode(id, name);
            idToNodeMap.put(id, node);

            //assume jsonObject.has("parentOrg")
            if (!jsonObject.get("parentOrg").isJsonNull()) {
                String parentId = jsonObject.getAsJsonObject("parentOrg").get("id").getAsString();
                node.setParentId(parentId);
            } else {
                root = node;
            }
        }

        // Second pass to link children to their parents
        for (TreeNode node : idToNodeMap.values()) {
            if (node.getParentId() != null) {
                idToNodeMap.get(node.getParentId()).getChildren().add(node);
            }
        }
        return new TreePackage(root,idToNodeMap);
    }

    public static void printTree(TreeNode node, String indent) {
        //recursion
        System.out.println(indent + node);
        for (TreeNode child : node.getChildren()) {
            printTree(child, indent + "  ");
        }
    }

    public static int  ParallelTreeValueCalculation(TreeNode root, ApiService apiService, LocalDate[] desiredDate) throws IOException, ExecutionException, InterruptedException {
        int numThreads = Runtime.getRuntime().availableProcessors();

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        AtomicInteger totalValue = new AtomicInteger(0);

        List<Future<Integer>> futures = new LinkedList<>();

        // Divide the tree into subtrees and calculate their total values
        for (TreeNode child : root.getChildren()) {
            futures.add(executor.submit(() -> getTotalSingleAndBatchScansForPeriod(child, apiService, desiredDate)));
        }

        // Wait for all threads to complete and accumulate the results
        for (Future<Integer> future : futures) {
            totalValue.addAndGet(future.get());
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        return totalValue.get();
    }

    public static int getTotalSingleAndBatchScansForPeriod(TreeNode node, ApiService apiService, LocalDate[] desiredDate) throws IOException {
        if (node == null) {
            return 0;   //base case
        }
        int totalValue = CalculationUtil.getTotalScansByOrgId(apiService,node.getId(),desiredDate);

        for (TreeNode child : node.getChildren()) {
            totalValue += getTotalSingleAndBatchScansForPeriod(child, apiService,desiredDate);
        }
        return totalValue;
    }

//    public static int getTotalSingleAndBatchScansForPeriod(TreeNode node, ApiService apiService, LocalDate[] desiredDate) throws IOException {
//        //recursive method- too slow
//        if (node == null) {
//            return 0;   //base case
//        }
//        int totalValue = CalculationUtil.getTotalScansByOrgId(apiService,node.getId(),desiredDate);
//
//        for (TreeNode child : node.getChildren()) {
//            totalValue += getTotalSingleAndBatchScansForPeriod(child, apiService,desiredDate);
//        }
//        return totalValue;
//    }
}
