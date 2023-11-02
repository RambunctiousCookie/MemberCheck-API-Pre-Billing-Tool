package RTT.billing.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class TreeNode {
    private String id;
    private String name;
    private String email;
    private String isIdvActive;
    private String isMonitoringActive;
    private String status;

    private String parentId;
    //TreeNode parent;
    private List<TreeNode> children;

    public TreeNode(String id, String name) {
        this.id = id;
        this.name = name;
        this.children = new ArrayList<>();
    }

    public TreeNode(String id, String name, String email, String isIdvActive, String isMonitoringActive, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isIdvActive = isIdvActive;
        this.isMonitoringActive = isMonitoringActive;
        this.status = status;
        this.children = new ArrayList<>();
    }

    @Override
    public String toString(){
        //return this.name + " (" + this.id + ")";
        return this.id;
    }
}