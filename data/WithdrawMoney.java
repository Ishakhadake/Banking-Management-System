package bankmangamentsystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


// 1. Create class
class Withdraw {

    // 2. Declare variables
    int accountNo;
    double amount;

    // 2. Declare method
    void withdrawMoney() {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== WITHDRAW MONEY =====");

        System.out.print("Enter Account Number: ");
        accountNo = sc.nextInt();

        System.out.print("Enter Withdraw Amount: ");
        amount = sc.nextDouble();

        if (amount <= 0) {

            System.out.println("Invalid Amount!");
            sc.close();
            return;
        }

        try {

            Connection con = JDBC.getConnection();

            // Check account and balance
            String checkSql =
                    "SELECT balance FROM accounts WHERE account_no = ?";

            PreparedStatement checkPs =
                    con.prepareStatement(checkSql);

            checkPs.setInt(1, accountNo);

            ResultSet rs =
                    checkPs.executeQuery();

            if (rs.next()) {

                double balance =
                        rs.getDouble("balance");

                // Check sufficient balance
                if (amount > balance) {

                    System.out.println(
                            "Insufficient Balance!");

                } else {

                    double newBalance =
                            balance - amount;

                    // Update balance
                    String updateSql =
                            "UPDATE accounts SET balance = ? "
                            + "WHERE account_no = ?";

                    PreparedStatement updatePs =
                            con.prepareStatement(updateSql);

                    updatePs.setDouble(1, newBalance);
                    updatePs.setInt(2, accountNo);

                    int result =
                            updatePs.executeUpdate();

                    // Store transaction
                    String transactionSql =
                            "INSERT INTO transactions "
                            + "(account_no, transaction_type, amount) "
                            + "VALUES (?, ?, ?)";

                    PreparedStatement transactionPs =
                            con.prepareStatement(transactionSql);

                    transactionPs.setInt(1, accountNo);
                    transactionPs.setString(2, "WITHDRAW");
                    transactionPs.setDouble(3, amount);

                    transactionPs.executeUpdate();

                    transactionPs.close();

                    if (result > 0) {

                        System.out.println(
                                "Money Withdrawn Successfully!");

                        System.out.println(
                                "Old Balance: " + balance);

                        System.out.println(
                                "Withdrawn: " + amount);

                        System.out.println(
                                "New Balance: " + newBalance);
                    }

                    updatePs.close();
                }

            } else {

                System.out.println(
                        "Account Not Found!");
            }

            rs.close();
            checkPs.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        sc.close();
    }
}


// 3. Create main class
public class WithdrawMoney {

    public static void main(String[] args) {

        // 4. Create object
        Withdraw obj = new Withdraw();

        // Access class method using object
        obj.withdrawMoney();
    }
}