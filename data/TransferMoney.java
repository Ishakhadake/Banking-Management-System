package bankmangamentsystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


// 1. Create class
class Transfer {

    // 2. Declare variables
    int senderAccount;
    int receiverAccount;
    double amount;

    // 3. Declare method
    void transferMoney() {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== TRANSFER MONEY =====");

        System.out.print("Enter Sender Account Number: ");
        senderAccount = sc.nextInt();

        System.out.print("Enter Receiver Account Number: ");
        receiverAccount = sc.nextInt();

        System.out.print("Enter Transfer Amount: ");
        amount = sc.nextDouble();

        if (amount <= 0) {

            System.out.println("Invalid Amount!");
            sc.close();
            return;
        }

        if (senderAccount == receiverAccount) {

            System.out.println(
                    "Sender and Receiver cannot be same!");

            sc.close();
            return;
        }

        try {

            Connection con = JDBC.getConnection();

            // Check sender account
            String senderSql =
                    "SELECT balance FROM accounts "
                    + "WHERE account_no = ?";

            PreparedStatement senderPs =
                    con.prepareStatement(senderSql);

            senderPs.setInt(1, senderAccount);

            ResultSet senderRs =
                    senderPs.executeQuery();

            if (!senderRs.next()) {

                System.out.println(
                        "Sender Account Not Found!");

                con.close();
                sc.close();
                return;
            }

            double senderBalance =
                    senderRs.getDouble("balance");

            // Check sufficient balance
            if (amount > senderBalance) {

                System.out.println(
                        "Insufficient Balance!");

                con.close();
                sc.close();
                return;
            }

            // Check receiver account
            String receiverSql =
                    "SELECT account_no FROM accounts "
                    + "WHERE account_no = ?";

            PreparedStatement receiverPs =
                    con.prepareStatement(receiverSql);

            receiverPs.setInt(1, receiverAccount);

            ResultSet receiverRs =
                    receiverPs.executeQuery();

            if (!receiverRs.next()) {

                System.out.println(
                        "Receiver Account Not Found!");

                con.close();
                sc.close();
                return;
            }

            // Deduct money from sender
            String withdrawSql =
                    "UPDATE accounts "
                    + "SET balance = balance - ? "
                    + "WHERE account_no = ?";

            PreparedStatement withdrawPs =
                    con.prepareStatement(withdrawSql);

            withdrawPs.setDouble(1, amount);
            withdrawPs.setInt(2, senderAccount);

            withdrawPs.executeUpdate();

            // Add money to receiver
            String depositSql =
                    "UPDATE accounts "
                    + "SET balance = balance + ? "
                    + "WHERE account_no = ?";

            PreparedStatement depositPs =
                    con.prepareStatement(depositSql);

            depositPs.setDouble(1, amount);
            depositPs.setInt(2, receiverAccount);

            depositPs.executeUpdate();

            // Sender transaction
            String senderTransaction =
                    "INSERT INTO transactions "
                    + "(account_no, transaction_type, amount) "
                    + "VALUES (?, ?, ?)";

            PreparedStatement senderTransactionPs =
                    con.prepareStatement(senderTransaction);

            senderTransactionPs.setInt(1, senderAccount);
            senderTransactionPs.setString(
                    2, "TRANSFER OUT");
            senderTransactionPs.setDouble(3, amount);

            senderTransactionPs.executeUpdate();

            // Receiver transaction
            String receiverTransaction =
                    "INSERT INTO transactions "
                    + "(account_no, transaction_type, amount) "
                    + "VALUES (?, ?, ?)";

            PreparedStatement receiverTransactionPs =
                    con.prepareStatement(receiverTransaction);

            receiverTransactionPs.setInt(1, receiverAccount);
            receiverTransactionPs.setString(
                    2, "TRANSFER IN");
            receiverTransactionPs.setDouble(3, amount);

            receiverTransactionPs.executeUpdate();

            // Success message
            System.out.println(
                    "\nMoney Transferred Successfully!");

            System.out.println(
                    "Amount: ₹" + amount);

            // Close resources
            senderRs.close();
            senderPs.close();

            receiverRs.close();
            receiverPs.close();

            withdrawPs.close();
            depositPs.close();

            senderTransactionPs.close();
            receiverTransactionPs.close();

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        sc.close();
    }
}


// 4. Create main class
public class TransferMoney {

    public static void main(String[] args) {

        // 5. Create object
        Transfer obj = new Transfer();

        // 6. Access method using object
        obj.transferMoney();
    }
}