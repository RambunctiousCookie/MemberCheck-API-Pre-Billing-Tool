package Util;

import Service.ApiService;
import data.TreeNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import data.TreePackage;
import enumerable.Status;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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
            String id = jsonObject.get("id") != null ? jsonObject.get("id").getAsString() : "";
            String name = jsonObject.get("name") != null ? jsonObject.get("name").getAsString() : "";

            //String complianceOfficer = jsonObject.get("complianceOfficer") != null ? jsonObject.get("complianceOfficer").getAsString() : "";
            String email = jsonObject.get("email") != null ? jsonObject.get("email").getAsString() : "";
            String isIdvActive = jsonObject.get("isIdvActive") != null ? jsonObject.get("isIdvActive").getAsString() : "";
            String isMonitoringActive = jsonObject.get("isMonitoringActive") != null ? jsonObject.get("isMonitoringActive").getAsString() : "";
            String status = jsonObject.get("status") != null ? jsonObject.get("status").getAsString() : "";

            TreeNode node = new TreeNode(id, name, email,isIdvActive,isMonitoringActive,status);
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


//    public static int recursiveSumSingleAndBatchScansForPeriod(TreeNode node, ApiService apiService, LocalDate[] desiredDate) throws IOException {
//        //recursive method- too slow
//        if (node == null) {
//            return 0;   //base case
//        }
//        int totalValue = CalculationUtil.getTotalScansByOrgId(apiService,node.getId(),desiredDate);
//
//        for (TreeNode child : node.getChildren()) {
//            totalValue += recursiveSumSingleAndBatchScansForPeriod(child, apiService,desiredDate);
//        }
//        return totalValue;
//        //TODO: reorganize, test this recursive
//    }

    //use getChildren() to get the nodes to query
    public static int sumSumSingleAndBatchScansForPeriod (TreeNode root, ApiService apiService, LocalDate[] desiredDate) throws IOException {
        int sum = 0;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode currentNode = stack.pop();
            sum += CalculationUtil.getTotalScansByOrgId(apiService,currentNode.getId(),desiredDate);

            for (TreeNode child : currentNode.getChildren()) {
                stack.push(child);
            }
        }
        return sum;
    }

    public static int sumMonitoringScansForPeriodByStatus(TreeNode root, ApiService apiService, LocalDate[] desiredDate, Status status) throws IOException {
        int sum = 0;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode currentNode = stack.pop();
            sum += CalculationUtil.getTotalMonitoringScansByOrgId(apiService,currentNode.getId(),desiredDate, status);

            for (TreeNode child : currentNode.getChildren()) {
                stack.push(child);
            }
        }
        return sum;
    }


}
