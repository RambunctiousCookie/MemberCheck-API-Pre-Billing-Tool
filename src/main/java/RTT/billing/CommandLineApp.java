package RTT.billing;

import RTT.billing.Service.ApiService;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CommandLineApp {
    private static String apiKey;
    private static ApiService apiService;

    public static void main(String[] args) {

        apiService = new ApiService("");    //create the apiService at the start

        Scanner scanner = new Scanner(System.in);
        String inputKey = "";

        System.out.println("\n\nWelcome! This is a preliminary Command-Line Interface for the Pre-Billing Application. To Use, Please Enter Your API Key.");
        System.out.println("\t- Your API key can be obtained from the MemberCheck portal, under your profile.");
        System.out.println("\t- Depending on your Command-Line Interface, you might need to press CTRL+V or RIGHT-CLICK to paste your key in.");

        while (inputKey.equals("")) {

            System.out.print("Enter your key: ");

            try {
                String tempKey =scanner.next();

                if(apiService.isValidApiKey(tempKey)){
                    inputKey = tempKey;     //THIS IS THE ONLY WAY TO EXIT THE LOOP
                    System.out.println("Successfully parsed API key using MemberCheck service. Returning to main menu.");
                    menuPortal();
                }
                else {
                    System.out.println("Could not get a successful response from MemberCheck API; not a valid API key. Please re-enter.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid key.");
                scanner.nextLine();
                //continue;
            } catch (IOException e) {
                System.out.println("Issue occurred attempting to connect to MemberCheck API:" + e.getMessage());
                System.out.println("Try again.");
                scanner.nextLine();
                //continue;
            }
        }
    }

    private static void menuPortal(){
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 9) {
            System.out.println("\n\n===Main Menu===");
            System.out.println("To proceed, please type the number representing your choice and press enter.");
            System.out.println("[0] Update API Key");
            System.out.println("[1] Get Organizational Tree (Top-Level Nodes)");
            System.out.println("[2] Get Quarterly Billing Statistics (Top-Level Nodes, Respective Scan Usage (Incl. Sub-Orgs))");
            System.out.println("[3] Get Contract Renewal Statistics (Top-Level Nodes, Respective Monitoring Scans which are CURRENTLY TURNED ON (Incl. Sub-Orgs)))");
            //System.out.println("[4] Get Contract Renewal Statistics (On/Off Monitoring Scan List Per Organization)"); //  TODO: implement [4]- unable to map to CSV because I no longer have account access the API as of 231101
            System.out.println("[9] Quit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 0:
                    menu0();
                    break;
                case 1:
                    menu1();
                    break;
                case 2:
                    menu2();
                    break;
                case 3:
                    //menu3();
                    break;
                case 9:
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
    private static void menu0() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 2) {
            System.out.println("\n\n===Menu 0: Update API Key===");
            System.out.println("0. Enter Menu to Input Your API Key");
            System.out.println("1. Go Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 0:
                    callFunction0();
                    break;
                case 1:
                    System.out.println("Returning to Main Menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    private static void menu1() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 2) {
            System.out.println("\n\n===Menu 1: Get Organizational Tree (Top-Level Nodes)===");
            System.out.println("0. Call Function 1");
            System.out.println("1. Go Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 0:
                    callFunction1();
                    break;
                case 1:
                    System.out.println("Returning to Main Menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    private static void menu2() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 2) {
            System.out.println("\n\n===Menu 2===");
            System.out.println("0. Call Function 2");
            System.out.println("1. Go Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 0:
                    callFunction2();
                    break;
                case 1:
                    return;
                case 2:
                    System.out.println("Exiting Menu 2.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    private static void menu3() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 2) {
            System.out.println("\n\n===Menu 2===");
            System.out.println("0. Call Function 2");
            System.out.println("1. Go Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 0:
                    callFunction2();
                    break;
                case 1:
                    return;
                case 2:
                    System.out.println("Exiting Menu 2.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    private static void menu4() {
       //TODO: implement with access to the API
    }

    private static void callFunction0() {

    }

    private static void callFunction1() {
        System.out.println("Function 1 called.");
    }

    private static void callFunction2() {
        System.out.println("Function 2 called.");
    }

    public static boolean isString(String input) {
        // Use a regular expression to check if it contains only alphabetic characters
        return input.matches("^[a-zA-Z]+$");
    }


//    private static int getUserInput(Scanner scanner) {
//        int input = -1;
//        boolean validInput = false;
//
//        while (!validInput) {
//            try {
//                input = scanner.nextInt();
//                validInput = true;
//            } catch (InputMismatchException e) {
//                System.out.println("Invalid input. Please enter a valid integer.");
//                scanner.nextLine(); // Consume the invalid input
//            }
//        }
//
//        return input;
//    }
}