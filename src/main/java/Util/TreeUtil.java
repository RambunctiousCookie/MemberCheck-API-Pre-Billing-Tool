package Util;

import data.TreeNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import data.TreePackage;

import java.util.HashMap;
import java.util.Map;

public class TreeUtil {
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
}
