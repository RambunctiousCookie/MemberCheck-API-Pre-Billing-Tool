package Model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class TreeNode {
    String id;
    String name;
    TreeNode parent;
    List<TreeNode> children;

    public TreeNode(String id, String name) {
        this.id = id;
        this.name = name;
        this.children = new ArrayList<>();
    }
}