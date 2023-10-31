package Util;

import Service.ApiService;
import data.TreeNode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class TreeUtilTest {

    //ApiService apiService = Mockito.mock(ApiService.class);

    @Test
    public void testSumSumSingleAndBatchScansForPeriodIteratesThroughEveryNode() throws IOException {

        TreeNode[] treeNodes = {
                new TreeNode("Root_Org", "Root"),
                new TreeNode("Child1_Org", "Child1"),
                new TreeNode("Child2_Org", "Child2"),
                new TreeNode("Child3_Org", "Child3"),
                new TreeNode("Child4_Org", "Child4")
        };

        TreeNode root = new TreeNode("Root_Org", "Root");
        //TreeNode subNode = new TreeNode("Child0_Org", "Child0");
        root.getChildren().add(new TreeNode("Child1_Org", "Child1"));
        root.getChildren().add(new TreeNode("Child2_Org", "Child2"));
        root.getChildren().get(0).getChildren().add(new TreeNode("Child3_Org", "Child3"));
        root.getChildren().get(0).getChildren().add(new TreeNode("Child4_Org", "Child4"));

        int totalNodes = 5;

        Mockito.when(CalculationUtil.getTotalScansByOrgId(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(1);

    }

}
