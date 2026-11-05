package bankmangamentsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


// 1. Create class
class Balance {

    // 2. Declare variable
    int accountNo;

    // 2. Declare method
    void checkBalance() {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== CHECK BALANCE =====");

        System.out.print("Enter Account Number: ");
        accountNo = sc.nextInt();

        try {

            Connection con = JDBC.getConnection();

            String sql =
                    "SELECT name, account_type, balance "
                    + "FROM accounts WHERE account_no = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, accountNo);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                System.out.println("\nAccount Details");
                System.out.println("----------------------");

                System.out.println(
                        "Account Number: " + accountNo);

                System.out.println(
                        "Name: " + rs.getString("name"));

                System.out.println(
                        "Account Type: "
                        + rs.getString("account_type"));

                System.out.println(
                        "Balance: ₹"
                        + rs.getDouble("balance"));

            } else {

                System.out.println(
                        "Account Not Found!");
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


// 3. Create main class
public class CheckBalance {

    public static void main(String[] args) {

        // 4. Create object
        Balance obj = new Balance();

        // Access class method using object
        obj.checkBalance();
    }
}