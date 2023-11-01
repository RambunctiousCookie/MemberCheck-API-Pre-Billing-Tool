import data.TreeNode;
import Service.ApiService;
import Util.*;
import com.google.gson.JsonElement;
import com.opencsv.exceptions.CsvException;
import enumerable.Status;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BillingRunner {
    public static void main(String[] args) throws IOException, CsvException {
        //TODO: output it to a csv

        String csvPath = "/sample.csv";
        String keyPath = "/0_ApiKey/key.txt";

        List<String[]> myCSV = HandlerCSV.Read(csvPath);
        //HandlerCSV.Print(myCSV);

        String apiKey = HandlerTxt.Read(keyPath);

        ApiService apiService = new ApiService(apiKey);

        JsonElement allOrgs = apiService.fetchOrgListData();
        System.out.println("API Call (OrgList) Success");
        TreeNode root = TreeUtil.buildTree(allOrgs).getRoot();
        if (root != null) {
            TreeUtil.printTree(root, "\t");
        }
        System.out.println("//----------------------------------------------------");
        List<String> orgIds = root.getChildren().stream().map(x->x.getId()).collect(Collectors.toList());

        List<TreeNode> companyNodes = root.getChildren();
        //TODO: more modular way of doing it

        try {
            LocalDate[] desiredDate = DateUtil.getQuartileDates(2023, 4);


            Map<String,Integer> quarterlyBillingScanCountMapper = new HashMap<>();

            for(TreeNode node : companyNodes)
                quarterlyBillingScanCountMapper.put(node.getId(), TreeUtil.sumSumSingleAndBatchScansForPeriod(node, apiService,desiredDate));

            for (var elem : quarterlyBillingScanCountMapper.entrySet())
                System.out.println(elem.getKey() + ": "+ elem.getValue() + " regular scans");

            //TODO: note that the report does not include scans done on day itself today
            //System.out.println("API Call (Scans) Success");

            System.out.println("//------------------------------------------------");

//            int totalMonitoringScansTurnedOn = CalculationUtil.getTotalMonitoringScansByOrgId(apiService, "John_Org", desiredDate, Status.On);
//
//            System.out.println(totalMonitoringScansTurnedOn);

            Map<String,Integer> contractRenewalScanCountMapper = new HashMap<>();

            Status status = Status.On;

            //desiredDate = DateUtil.getQuartileDates(2023, 3);

            for(TreeNode node : companyNodes)
                contractRenewalScanCountMapper.put(node.getId(), TreeUtil.sumMonitoringScansForPeriodByStatus(node, apiService,desiredDate,status));

            for (var elem : contractRenewalScanCountMapper.entrySet())
                System.out.println(elem.getKey() + ": "+ elem.getValue() + " monitoring scans");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Finished");
    }
}
