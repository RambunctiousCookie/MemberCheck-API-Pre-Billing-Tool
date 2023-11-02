package RTT.billing;

import RTT.billing.Service.ApiService;
import RTT.billing.Util.DateUtil;
import RTT.billing.Util.HandlerCSV;
import RTT.billing.Util.MonitoringScanUtil;
import RTT.billing.Util.TreeUtil;
import RTT.billing.data.TreeNode;
import RTT.billing.enumerable.Status;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.opencsv.exceptions.CsvException;

import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.time.Period;
import java.util.stream.Collectors;

public class CommandLineApp {
    //private static String apiKey;
    private static ApiService apiService;
    private static TreeNode root;
    private static int rttOrgLevel;

    public static void main(String[] args) {    //This function is in charge of setting the apiService

        apiService = new ApiService("");    //create the apiService at the start

        Scanner scanner = new Scanner(System.in);
        String inputKey = "";

        System.out.println("\n\n===Welcome===");
        System.out.println("This is a preliminary Command-Line Interface for the Pre-Billing Application. To Use, Please Enter Your API Key.");
        System.out.println("\t- Your API key can be obtained from the MemberCheck portal, under your profile.");
        System.out.println("\t- Depending on your Command-Line Interface, you might need to press CTRL+V or RIGHT-CLICK to paste your key in.");

        while (inputKey.equals("")) {
            System.out.print("Enter your key: ");

            try {
                String tempKey = scanner.next();

                if (apiService.isValidApiKey(tempKey)) {
                    inputKey = tempKey;     //THIS IS THE ONLY WAY TO EXIT THE LOOP
                    apiService.setApiKey(tempKey);
                    System.out.println("Successfully parsed API key using MemberCheck service. Entering main menu.");
                    chooseRttNode();
                    //menuPortal();
                } else {
                    System.out.println("Could not get a successful response from MemberCheck API; not a valid API key. Please re-enter.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid key.");
                scanner.nextLine();
                //continue;
            } catch (IOException e) {
                System.out.println("Issue occurred attempting to connect to MemberCheck API:" + e.getMessage() + " please try again.");
                scanner.nextLine();
                //continue;
            }
        }
    }

    private static void chooseRttNode() {    //This function is in charge of selecting up the Organizational Structure and RTT governing organization's Org_Id

        System.out.println("\n\n===Choose RTT Org Node===");
        System.out.println("In order to access billing data, the program will need guidance on which Org_Id is directly above the client organizations.");
        System.out.println("Please enter the number that corresponds to HOW FAR FROM THE ROOT the nearest RTT organization occupies above the client organizations. Here are some examples:");
        System.out.println(
                "\n- RTT Org -> [0]\n" +
                        "\t- Client1\n" +
                        "\t- Client1\n" +
                        "//[0] represents the root. Here, you would enter [0] since RTT Org is at the root AND closest to client-level.\n"
        );
        System.out.println(
                "- RTT Org -> [0]\n" +
                        "\t- RTT SubOrg -> [1]\n" +
                        "\t\t- Client1\n" +
                        "\t\t- Client2\n" +
                        "//[0] represents the root, and [1] represents 1 level away from root. Here, you would enter [1] since RTT SubOrg is 1 level away from the root AND is closest to client-level.\n"
        );
        System.out.println("I will now print the tree so that you can choose which level to enter.");

        try {
            JsonElement allOrgs = apiService.fetchOrgListData();
            root = TreeUtil.buildTree(allOrgs).getRoot();
            if (root != null) {
                System.out.println("\nAPI was successfully called and Tree was Successfully Constructed.");
                System.out.println("==================================================================");
                TreeUtil.printTree(root, "\t");
                System.out.println("\n");
            } else {
                System.out.println("\nTree data was not constructed successfully. There is no tree available.");
            }
        } catch (IOException e) {
            System.out.println("Issue occurred attempting to connect to MemberCheck API:" + e.getMessage() + " please try again.");
        } catch (Exception e) {
            System.out.println("Error trying to construct or print the resulting tree. The membercheck API may have changed.");
        }

        Scanner scanner = new Scanner(System.in);
        //int choice = -1;

        while (true) {
            try {
                System.out.print("Enter the closest RTT Organization Level: ");
                int temp = scanner.nextInt();
                System.out.print("You chose level " + temp + ". Are you sure? Re-type the number to confirm. ");
                int confirmation = scanner.nextInt();
                if (temp == confirmation) {
                    System.out.println("RTT organizational level confirmed. Continuing to main menu now.");
                    rttOrgLevel = temp;
                    menuPortal();
                    //choice =99;
                } else
                    System.out.println("Sorry, the numbers didn't match. Please re-enter.");

            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
                continue;
            }
        }
    }

    private static void menuPortal() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 99) {
            System.out.println("\n\n===Main Menu===");
            System.out.println("To proceed, please type the number representing your choice and press enter.");
            System.out.println("[1] Review Organizational Tree (Top-Level Nodes, RTT Node)");
            System.out.println("[2] Get Quarterly Billing Statistics (Top-Level Nodes, Respective Scan Usage (Incl. Sub-Orgs))");
            System.out.println("[3] Get Contract Renewal Statistics (Top-Level Nodes, Respective Monitoring Scans which are CURRENTLY TURNED ON (Incl. Sub-Orgs)))");
            //System.out.println("[4] Get Contract Renewal Statistics (On/Off Monitoring Scan List Per Organization)"); //  TODO: implement [4]- unable to map to CSV because I no longer have account access the API as of 231101
            System.out.println("[99] Quit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    chooseRttNode();
                    break;
                case 2:
                    menu2();
                    break;
                case 3:
                    menu3();
                    break;
//                case 4:
//                    menu4();
//                    break;
                case 99:
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void menu2() {   //GET QTE BILLING
        Scanner scanner = new Scanner(System.in);
        int[] yearAndQuarter = {-1000, -1};

        System.out.println("\n\n===Menu 2: Get Quarterly Billing Statistics (Top-Level Nodes, Respective Scan Usage (Incl. Sub-Orgs))===");
        System.out.println("If you wish to exit this menu and return to the main menu, please enter [99] twice.");
        System.out.println("Otherwise, to retrieve the quarterly billing statistics, follow these instructions:");
        System.out.println("\t- Check [config.yaml] in [src/main/resources] and ensure that the various start and end dates for each quarter are correct. Otherwise, data retrieval would not be over the correct period.");
        System.out.println("\t- First enter your desired YEAR (YYYY format), followed by your desired QUARTER (1-4)");
        System.out.print("Enter desired YEAR (YYYY format): ");
        while (yearAndQuarter[0] == -1000) {
            try {
                int temp = scanner.nextInt();
                if (temp >= 0 && temp <= 9999)
                    yearAndQuarter[0] = temp;
                else
                    System.out.println("Invalid year was entered. Please enter a non-negative number.");
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        }
        System.out.print("Enter desired QUARTER (1-4): ");
        while (yearAndQuarter[1] == -1) {
            try {
                int temp = scanner.nextInt();
                if (temp == 99 && yearAndQuarter[0] == 99) {
                    System.out.println("Returning to main menu.");
                    menuPortal();
                }
                if (temp >= 0 && temp < 5)
                    yearAndQuarter[1] = temp;
                else
                    System.out.println("Invalid quarter was entered. Please enter a number within [1,2,3,4].");
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        }

        try {
            TreeNode closestRttNode = root.getNodeAtLevel(rttOrgLevel);

            List<TreeNode> companyNodes = closestRttNode.getChildren();
            LocalDate[] desiredDate = DateUtil.getQuartileDates(yearAndQuarter[0], yearAndQuarter[1]);

            Map<String, Integer> quarterlyBillingScanCountMapper = new HashMap<>();

            for (TreeNode node : companyNodes)
                quarterlyBillingScanCountMapper.put(node.getId(), TreeUtil.sumSumSingleAndBatchScansForPeriod(node, apiService, desiredDate));

            for (var elem : quarterlyBillingScanCountMapper.entrySet())
                System.out.println(elem.getKey() + ": " + elem.getValue() + " regular scans");


            HandlerCSV.WriteUsage(quarterlyBillingScanCountMapper, "src/main/resources/2_QuarterlyBilling/Output.csv");

            //TODO: note that the report does not include scans done on day itself today
            System.out.println("Quarterly Scan Count Usage for Every Organization In This API Account has Been Printed to CSV. You can find it under src/main/resources/2_QuarterlyBilling/Output.csv");
            menuPortal();


        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            System.out.println("The tree data is invalid. Something has gone wrong.");
            System.out.println("Returning to main menu.");
            menuPortal();
        }
    }

    private static void menu3() {
        System.out.println("\n\n===Menu 3: Get Contract Renewal Statistics (Top-Level Nodes, Respective Monitoring Scans which are CURRENTLY TURNED ON (Incl. Sub-Orgs))===");
        System.out.println("BEFORE beginning, please check the following path:");
        System.out.println("src/main/resources/3_ContractRenewal/Input.csv");
        System.out.println("\tYou must FIRST update this CSV file with all ongoing contracts for this function to work.");
        System.out.println("\t\t- The format is as follows: Org_Id, Contract Start Date and Contract End Date for this function to work.");
        System.out.println("\t\t-Contract Start Date and End Date MUST be in DD-MM-YYYY format.");
        System.out.println("\tThe system will automatically filter out which contracts end in 2 months or less.");
        System.out.println("\tAfter following the format in the given file, input [1] to receive the statistics.");
        System.out.println("You may also input [99] to return to the main menu.");

        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (true) {
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        Status status = Status.On;  //retrieve the CURRENTLY ON SCANS

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                        String csvPath = "/3_ContractRenewal/Input.csv";
                        List<String[]> myCSV = HandlerCSV.read(csvPath);
                        Map<String, LocalDate[]> orgContractMapper = new HashMap<>();
                        for (String[] entry : myCSV) {
                            Period period = Period.between(LocalDate.now(), LocalDate.parse(entry[2], formatter));

                            if (period.getMonths() > 0 && period.getMonths() <= 2) { //Handle only entries with end dates less than 2 months
                                LocalDate[] contractStartEnd = new LocalDate[2];
                                contractStartEnd[0] = LocalDate.parse(entry[1], formatter);
                                contractStartEnd[1] = LocalDate.parse(entry[2], formatter);
                                orgContractMapper.put(entry[0], contractStartEnd);
                            }
                        }

                        TreeNode closestRttNode = root.getNodeAtLevel(rttOrgLevel);
                        List<TreeNode> companyNodes = closestRttNode.getChildren();

                        companyNodes = companyNodes.stream().filter(x -> orgContractMapper.containsKey(x.getId())).collect(Collectors.toList()); //filter the companyNodes list based on the orgContractMapper

                        Map<String, Integer> contractRenewalScanCountMapper = new HashMap<>();

                        for (TreeNode node : companyNodes)
                            contractRenewalScanCountMapper.put(node.getId(), TreeUtil.sumMonitoringScansForPeriodByStatus(node, apiService, orgContractMapper.get(node.getId()), status));

                        for (var elem : contractRenewalScanCountMapper.entrySet())
                            System.out.println(elem.getKey() + ": " + elem.getValue() + " monitoring scans with status = \"" + status + "\"");

                        HandlerCSV.WriteUsage(contractRenewalScanCountMapper, "src/main/resources/3_ContractRenewal/Output.csv");

                        //TODO: note that the report does not include scans done on day itself today
                        System.out.println("Contract Renewal Monitoring Scan Usage for Every Organization With Contract Date Expiring Within 2 Months has Been Printed to CSV. You can find it under src/main/resources/3_ContractRenewal/Output.csv");
                        menuPortal();

                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    } catch (CsvException e) {
                        System.out.println(e.getMessage());
                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                        System.out.println("The tree data is invalid. Something has gone wrong.");
                        System.out.println("Returning to main menu.");
                        menuPortal();
                    }


                    break;
                case 99:
                    System.out.println("Returning to Main Menu");
                    menuPortal();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    private static void menu4() {
        //TODO: implement when have access to the API- cannot decode the JSON structure otherwise
//        System.out.println("//===[4] Get Contract Renewal Statistics (On/Off Monitoring Scan List Per Organization)===");
//
//        status = Status.All;
//        List<JsonArray> monitoringMemberScanArrayDetails = new ArrayList<>();
//        List<JsonArray> monitoringCorpScanArrayDetails = new ArrayList<>();
//
//        for(TreeNode node : companyNodes){
//            monitoringMemberScanArrayDetails.add(apiService.fetchMonitoringMemberScanData(node.getId(),status).getAsJsonArray());
//            monitoringCorpScanArrayDetails.add(apiService.fetchMonitoringCorpScanData(node.getId(),status).getAsJsonArray());
//        }
//
//        MonitoringScanUtil.sortMonitoringJsonArray(monitoringMemberScanArrayDetails);
//        MonitoringScanUtil.sortMonitoringJsonArray(monitoringCorpScanArrayDetails);

    }


    public static boolean isString(String input) {
        // Use a regular expression to check if it contains only alphabetic characters
        return input.matches("^[a-zA-Z]+$");
    }

}