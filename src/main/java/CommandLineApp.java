import java.util.Scanner;

public class CommandLineApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 3) {
            System.out.println("\n\n===Main Menu===");
            System.out.println("To proceed, please type the number representing your choice and press enter.");
            System.out.println("0. Update API Key");
            System.out.println("1. Get Organizational Tree (Top-Level Nodes)");
            System.out.println("2. Menu 2");
            System.out.println("3. Quit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Consume the invalid input
                continue; // Restart the loop
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
            System.out.println("0. Call Function 0");
            System.out.println("1. Go Back to Main Menu");
            System.out.println("2. Quit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Consume the invalid input
                continue; // Restart the loop
            }

            switch (choice) {
                case 0:
                    callFunction0();
                    break;
                case 1:
                    return;
                case 2:
                    System.out.println("Exiting Menu 0.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
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
            System.out.println("2. Quit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Consume the invalid input
                continue; // Restart the loop
            }

            switch (choice) {
                case 0:
                    callFunction1();
                    break;
                case 1:
                    return;
                case 2:
                    System.out.println("Exiting Menu 1.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
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
            System.out.println("2. Quit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Consume the invalid input
                continue; // Restart the loop
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
            }
        }
    }

    private static void callFunction0() {
        System.out.println("Function 0 called.");
    }

    private static void callFunction1() {
        System.out.println("Function 1 called.");
    }

    private static void callFunction2() {
        System.out.println("Function 2 called.");
    }
}