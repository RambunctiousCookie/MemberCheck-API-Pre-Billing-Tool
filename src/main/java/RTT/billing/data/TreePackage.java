package RTT.billing.data;
import lombok.Data;
import java.util.Map;

@Data
public class TreePackage {
    private TreeNode root;
    private Map<String, TreeNode> idToNodeMap;

    public TreePackage(TreeNode root, Map<String, TreeNode> idToNodeMap) {
        this.root = root;
        this.idToNodeMap = idToNodeMap;
    }
}
