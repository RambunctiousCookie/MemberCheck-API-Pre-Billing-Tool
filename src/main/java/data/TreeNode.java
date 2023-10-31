package data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class TreeNode {
    String id;
    String name;
    String parentId;
    //TreeNode parent;
    List<TreeNode> children;

    public TreeNode(String id, String name) {
        this.id = id;
        this.name = name;
        this.children = new ArrayList<>();
    }

    @Override
    public String toString(){
        //return this.name + " (" + this.id + ")";
        return this.id;
    }
}