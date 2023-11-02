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

    public TreeNode getNodeAtLevel(int level) {
        if (level == 0) {
            return this;
        } else if (level > 0) {
//            TreeNode eg1 = children.get(0);
//            String eg2 = eg1.getIdAtLevel(level - 1);
//
//            return eg2;
            return children.get(0).getNodeAtLevel(level - 1); //TODO: adjust for better method when have access to the API. This is imperfect especially if RTT has multiple suborgs at the desired level.
        } else {
            throw new IllegalArgumentException("Level " + level + " is out of bounds.");
        }
    }

//    public String getIdAtLevel(int level) {
//        if (level == 0) {
//            return this.id;
//        } else if (level > 0) {
////            TreeNode eg1 = children.get(0);
////            String eg2 = eg1.getIdAtLevel(level - 1);
////
////            return eg2;
//            return children.get(0).getIdAtLevel(level - 1); //TODO: adjust for better method when have access to the API. This is imperfect especially if RTT has multiple suborgs at the desired level.
//        } else {
//            throw new IllegalArgumentException("Level " + level + " is out of bounds.");
//        }
//    }


}