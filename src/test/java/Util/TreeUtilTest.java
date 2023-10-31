package Util;

import Service.ApiService;
import data.TreeNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

public class TreeUtilTest {

    private static TreeNode[] treeNodes;
    private static TreeNode root;

    private static LocalDate[] desiredDate;

    @BeforeAll
    public static void setup() {

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

        desiredDate = DateUtil.getQuartileDates(2023,4);
    }

    @Test
    public void testSumSumSingleAndBatchScansForPeriodReturnsCorrectSum() throws IOException {

        try(MockedStatic<CalculationUtil> calculationUtil = Mockito.mockStatic(CalculationUtil.class)){
            int expectedSum = 0;

            for(int i=0;i< treeNodes.length;i++){
                int finalI = i;
                calculationUtil.when(() -> CalculationUtil.getTotalScansByOrgId(Mockito.any(), eq(treeNodes[finalI].getId()), Mockito.any())).thenReturn(i);
                expectedSum+=i;
            }

            int actualSum = TreeUtil.sumSumSingleAndBatchScansForPeriod(root, Mockito.mock(ApiService.class), desiredDate);

            assertEquals(expectedSum, actualSum);
        }
    }

    @Test
    public void testSumSumSingleAndBatchScansForPeriodIteratesThroughEveryNode() throws IOException {
        int expectedCount = treeNodes.length;

        try(MockedStatic<CalculationUtil> calculationUtil = Mockito.mockStatic(CalculationUtil.class)){
            calculationUtil.when(() -> CalculationUtil.getTotalScansByOrgId(Mockito.any(), Mockito.any(), Mockito.any()))
                    .thenReturn(1);

            int actualCount = TreeUtil.sumSumSingleAndBatchScansForPeriod(root, Mockito.mock(ApiService.class), desiredDate);

            assertEquals(expectedCount, actualCount);
        }
    }

}
