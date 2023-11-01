package Util;

public class HandlerDir {
    public static void inputExecutor(int input){
        switch (input) {
            case 0:
                System.out.println("You entered 0- Transitioning to Update API Key Use Case");

                break;
            case 1:
                System.out.println("You entered 1- Now Attempting to Output Organization Tree CSV");
                break;
            case 2:
                System.out.println("You entered 2-  Now Attempting to Quarterly Billing CSV");
                //output file to not overwrite the csv
                break;
            case 3:
                System.out.println("You entered 3- Now Retrieving Monitoring Scans for all Organizations");
                break;
            case 4:
                System.out.println("You entered 4- Now Retrieving Monitoring Scan Details for Specific Organization");
                break;
            default:
                System.out.println("Invalid input. Please enter a number between 0 and 4.");
        }
    }


}
