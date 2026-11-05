package bankmangamentsystem;
import java.util.Scanner;

public class BankManagementSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // =========================
        // LOGIN
        // =========================

        UserLogin loginObj = new UserLogin();

        boolean loginResult = loginObj.login();

        if (!loginResult) {

            System.out.println("Login Failed!");

            sc.close();
            return;
        }


        // =========================
        // CREATE OBJECTS
        // =========================

        Account accountObj = new Account();

        Deposit depositObj = new Deposit();

        Withdraw withdrawObj = new Withdraw();

        Balance balanceObj = new Balance();

        Transfer transferObj = new Transfer();

        Transaction transactionObj = new Transaction();


        // =========================
        // BANK MENU
        // =========================

        int choice;

        do {

            System.out.println("\n================================");
            System.out.println("     BANK MANAGEMENT SYSTEM");
            System.out.println("================================");

            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. Transfer Money");
            System.out.println("6. Transaction History");
            System.out.println("7. Logout");

            System.out.print("Enter Your Choice: ");

            choice = sc.nextInt();


            // =========================
            // SWITCH
            // =========================

            switch (choice) {

                case 1:

                    accountObj.createAccount();

                    break;


                case 2:

                    depositObj.depositMoney();

                    break;


                case 3:

                    withdrawObj.withdrawMoney();

                    break;


                case 4:

                    balanceObj.checkBalance();

                    break;


                case 5:

                    transferObj.transferMoney();

                    break;


                case 6:

                    transactionObj.transactionHistory();

                    break;


                case 7:

                    System.out.println("You have been logged out successfully.!");

                    break;


                default:

                    System.out.println("Invalid Choice!");
            }

        } while (choice != 7);


        sc.close();
    }
}