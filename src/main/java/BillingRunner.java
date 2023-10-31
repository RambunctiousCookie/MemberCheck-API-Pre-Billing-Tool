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
        //TODO: output it to a csv

        String csvPath = "/sample.csv";
        String keyPath = "/key.txt";

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

        List<TreeNode> nodes = root.getChildren();

        try {
            LocalDate[] desiredDate = DateUtil.getQuartileDates(2023, 4);

            Map<String,Integer> scanCountMapper = new HashMap<>();

            for(TreeNode node : nodes)
                scanCountMapper.put(node.getId(), TreeUtil.sumSumSingleAndBatchScansForPeriod(node, apiService,desiredDate));

            for (var elem : scanCountMapper.entrySet())
                System.out.println(elem.getKey() + ": "+ elem.getValue() + " scans");

            //TODO: note that the report does not include scans done on day itself today
            //System.out.println("API Call (Scans) Success");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Finished");
    }
}
