package RTT.billing;

import RTT.billing.Util.*;
import RTT.billing.Config.SystemParameters;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.google.gson.JsonArray;
import RTT.billing.data.TreeNode;
import RTT.billing.Service.ApiService;
import com.google.gson.JsonElement;
import com.opencsv.exceptions.CsvException;
import RTT.billing.enumerable.Status;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BillingRunner {
    public static void main(String[] args) throws IOException, CsvException {

//        System.out.println("===Test tree===");
//        TreeNode origin = new TreeNode("RTT Org", "RTT Root Organization");
//        TreeNode subOrg = new TreeNode("RTT SubOrg", "RTT Sub Organization");
//        TreeNode client1 = new TreeNode("Client1", "Railway Systems");
//
//        origin.getChildren().add(subOrg);
//        subOrg.getChildren().add(client1);
//
//        int levelToAccess = 0; // Change this to the desired level
//
//        try {
//            String nameAtLevel = origin.getIdAtLevel(levelToAccess);
//            System.out.println("ID at level " + levelToAccess + ": " + nameAtLevel);
//        } catch (IllegalArgumentException e) {
//            System.out.println(e.getMessage());
//        }


        System.out.println("===Testing CSVPath===");
        //TODO: output it to a csv

        String csvPath = "/sample.csv";
        String keyPath = "/0/key.txt";

        List<String[]> myCSV = HandlerCSV.Read(csvPath);
        //HandlerCSV.Print(myCSV);

        String apiKey = HandlerTxt.Read(keyPath);

        ApiService apiService = new ApiService(apiKey);

        JsonElement allOrgs = apiService.fetchOrgListData();
        System.out.println("API Call (OrgList) Success");

        System.out.println("//===[1] Get Organizational Tree (Top-Level Nodes)===");

        TreeNode root = TreeUtil.buildTree(allOrgs).getRoot();
        if (root != null) {
            TreeUtil.printTree(root, "\t");
        }

        System.out.println("//===[2] Get Quarterly Billing Statistics (Top-Level Nodes, Respective Scan Usage (Incl. Sub-Orgs))===");
        //List<String> orgIds = root.getChildren().stream().map(x->x.getId()).collect(Collectors.toList());

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

            System.out.println("//===[3] Get Contract Renewal Statistics (Top-Level Nodes, Respective Monitoring Scans which are CURRENTLY TURNED ON (Incl. Sub-Orgs)))===");

            Map<String,Integer> contractRenewalScanCountMapper = new HashMap<>();

            Status status = Status.On;
            //desiredDate = DateUtil.getQuartileDates(2023, 3);

            for(TreeNode node : companyNodes)
                contractRenewalScanCountMapper.put(node.getId(), TreeUtil.sumMonitoringScansForPeriodByStatus(node, apiService,desiredDate,status));

            for (var elem : contractRenewalScanCountMapper.entrySet())
                System.out.println(elem.getKey() + ": "+ elem.getValue() + " monitoring scans with status = \""+ status +"\"");

            System.out.println("//===[4] Get Contract Renewal Statistics (On/Off Monitoring Scan List Per Organization)===");

            status = Status.All;
            List<JsonArray> monitoringMemberScanArrayDetails = new ArrayList<>();
            List<JsonArray> monitoringCorpScanArrayDetails = new ArrayList<>();

            for(TreeNode node : companyNodes){
                monitoringMemberScanArrayDetails.add(apiService.fetchMonitoringMemberScanData(node.getId(),status).getAsJsonArray());
                monitoringCorpScanArrayDetails.add(apiService.fetchMonitoringCorpScanData(node.getId(),status).getAsJsonArray());
            }

            MonitoringScanUtil.sortMonitoringJsonArray(monitoringMemberScanArrayDetails);
            MonitoringScanUtil.sortMonitoringJsonArray(monitoringCorpScanArrayDetails);

//            monitoringMemberScanArrayDetails.stream().forEach(System.out::println);
//            monitoringCorpScanArrayDetails.stream().forEach(System.out::println);

            //TODO: NOTE TO SELF- use ("monitor" -> false) from API as equivalent of Status.Off

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Finished");
    }
}
