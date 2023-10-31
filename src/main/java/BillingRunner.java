import data.TreeNode;
import Service.ApiService;
import Util.*;
import com.google.gson.JsonElement;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BillingRunner {
    public static void main(String[] args) throws IOException, CsvException {
        String csvPath = "/sample.csv";
        String textFilePath = "/sample.txt";

        List<String[]> myCSV = HandlerCSV.Read(csvPath);
        String text = HandlerTxt.Read(textFilePath);

        HandlerCSV.Print(myCSV);
        System.out.println(text);

        ApiService apiService = new ApiService("632b36d11b82451380165944921ce1ee");


        JsonElement allOrgs = apiService.fetchOrgListData();
        System.out.println("API Call (OrgList) Success");
        TreeNode root = TreeUtil.buildTree(allOrgs).getRoot();
        if (root != null) {
            TreeUtil.printTree(root, "\t");
        }
        System.out.println("//----------------------------------------------------");
        List<String> orgIds = root.getChildren().stream().map(x->x.getId()).collect(Collectors.toList());

        //String[] orgIds = {"John_Org" };

        try {
            LocalDate[] desiredDate = DateUtil.getQuartileDates(2023, 4);

            Map<String,Integer> scanCountMapper = new HashMap<>();



            //OLD- not inclusive of subtrees //TODO: edit
            for(String orgId : orgIds)
                scanCountMapper.put(orgId,CalculationUtil.getTotalScansByOrgId(apiService, orgId, desiredDate));


//            {
////                int scans = CalculationUtil.getTotalScansByOrgId(apiService, orgId, desiredDate);
////                scanCountMapper.put(orgId,scans);
//            }


            for (var elem : scanCountMapper.entrySet())
                System.out.println(elem.getKey() + ": "+ elem.getValue() + " scans");

            //TODO: note that the report does not include scans done on day itself today
            //System.out.println(singleMemberScans+", "+ singleCorpScans);

            System.out.println("API Call (Scans) Success");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
//            JsonElement allOrgs = apiService.fetchOrgListData();
//            System.out.println("API Call (OrgList) Success");
//            TreeNode root = TreeUtil.buildTree(allOrgs).getRoot();
//
//            // Print or manipulate the tree as needed
//            if (root != null) {
//                TreeUtil.printTree(root, "\t");
//            }

            System.out.println("//----------------------------------------------------");

            root = TreeUtil.buildTreeBySpecifyingRoot(allOrgs, "John_Org");

            System.out.println();

            if (root != null) {
                TreeUtil.printTree(root, "\t");
            }

            System.out.println("//----------------------------------------------------");

            //int count = TreeUtil.recursiveSumSingleAndBatchScansForPeriod(root, apiService,DateUtil.getQuartileDates(2023, 4));

            int count = TreeUtil.sumSumSingleAndBatchScansForPeriod(root, apiService,DateUtil.getQuartileDates(2023, 4));

            System.out.println(count);

            System.out.println("//----------------------------------------------------");




//            try{
//                int count = TreeUtil.ParallelTreeValueCalculation(root,apiService,DateUtil.getQuartileDates(2023, 4));
//                System.out.println(count);
//            }catch (ExecutionException e){
//                System.out.println(e.getMessage());
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Finished");
    }
}
