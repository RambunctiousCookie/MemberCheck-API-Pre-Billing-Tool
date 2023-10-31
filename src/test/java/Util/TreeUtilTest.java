package Util;

import Service.ApiService;
import data.TreeNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreeUtilTest {

    private static ApiService apiService;

    private static LocalDate[] desiredDate;

    private static TreeNode[] treeNodes;

    private static TreeNode root;

    @BeforeAll
    public static void setup() {
        apiService = Mockito.mock(ApiService.class);
        desiredDate = new LocalDate[] {
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 12, 31)
        };

        treeNodes = new TreeNode[]{
                new TreeNode("Root_Org", "Root"),
                new TreeNode("Child1_Org", "Child1"),
                new TreeNode("Child2_Org", "Child2"),
                new TreeNode("Child3_Org", "Child3"),
                new TreeNode("Child4_Org", "Child4")
        };

        root = treeNodes[0];
        root.getChildren().add(treeNodes[1]);
        root.getChildren().add(treeNodes[2]);
        root.getChildren().get(0).getChildren().add(treeNodes[3]);
        root.getChildren().get(0).getChildren().add(treeNodes[4]);
    }

    @Test
    public void testSumSumSingleAndBatchScansForPeriodReturnsCorrectSum() throws IOException {

        int expectedSum = 0;

        for(int i=0;i< treeNodes.length;i++){
            Mockito.when(CalculationUtil.getTotalScansByOrgId(apiService, treeNodes[i].getId(), desiredDate)).thenReturn(i);
            expectedSum+=i;
        }

        int actualSum = TreeUtil.sumSumSingleAndBatchScansForPeriod(root, apiService, desiredDate);

        assertEquals(expectedSum, actualSum);
    }

    @Test
    public void testSumSumSingleAndBatchScansForPeriodIteratesThroughEveryNode() throws IOException {
        Mockito.when(CalculationUtil.getTotalScansByOrgId(apiService, Mockito.any(), desiredDate)).thenReturn(1);

        int expectedCount = treeNodes.length;

        int actualCount = TreeUtil.sumSumSingleAndBatchScansForPeriod(root, apiService, desiredDate);

        assertEquals(expectedCount, actualCount);

    }

}
