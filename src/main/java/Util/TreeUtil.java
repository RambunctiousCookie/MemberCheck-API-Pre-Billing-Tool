package Util;

import Model.TreeNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;

public class TreeUtil {
    public static TreeNode buildTree(JsonElement orgListJson){
        JsonArray jsonArray = orgListJson.getAsJsonArray();

        Map<String, TreeNode> idToNodeMap = new HashMap<>();
        TreeNode root = null;

        // First pass to create nodes and build the map
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
                root = node; // Assume there is only one root
            }
        }

        // Second pass to link children to their parents
        for (TreeNode node : idToNodeMap.values()) {
            if (node.getParentId() != null) {
                idToNodeMap.get(node.getParentId()).getChildren().add(node);
            }
        }

//        idToNodeMap.values().forEach(node -> {
//            if (node.getParent() != null) {
//                node.getParent().getChildren().add(node);
//            }
//        });

        return root;
    }

    public static void printTree(TreeNode node, String indent) {
        //recursion
        System.out.println(indent + node);
        for (TreeNode child : node.getChildren()) {
            printTree(child, indent + "  ");
        }
    }

}
