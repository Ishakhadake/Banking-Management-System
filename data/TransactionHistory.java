package bankmangamentsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


// 1. Create class
class Transaction {

    // 2. Declare variable
    int accountNo;

    // 3. Declare method
    void transactionHistory() {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== TRANSACTION HISTORY =====");

        System.out.print("Enter Account Number: ");
        accountNo = sc.nextInt();

        try {

            Connection con = JDBC.getConnection();

            String sql =
                    "SELECT transaction_id, transaction_type, "
                    + "amount, transaction_date "
                    + "FROM transactions "
                    + "WHERE account_no = ? "
                    + "ORDER BY transaction_date DESC";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, accountNo);

            ResultSet rs =
                    ps.executeQuery();

            boolean found = false;

            System.out.println("\nTransaction Details");
            System.out.println("----------------------------------------");

            while (rs.next()) {

                found = true;

                System.out.println(
                        "Transaction ID: "
                        + rs.getInt("transaction_id"));

                System.out.println(
                        "Type: "
                        + rs.getString("transaction_type"));

                System.out.println(
                        "Amount: ₹"
                        + rs.getDouble("amount"));

                System.out.println(
                        "Date: "
                        + rs.getTimestamp("transaction_date"));

                System.out.println(
                        "----------------------------------------");
            }

            if (!found) {

                System.out.println(
                        "No Transactions Found!");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        sc.close();
    }
}


// 4. Create main class
public class TransactionHistory {

    public static void main(String[] args) {

        // 5. Create object
        Transaction obj = new Transaction();

        // 6. Access method using object
        obj.transactionHistory();
    }
}