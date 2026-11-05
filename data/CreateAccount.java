package bankmangamentsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

// 1. Create class
class Account {

    // 2. Declare variables
    String name;
    String phone;
    String address;
    String accountType;
    double balance;

    // 2. Declare method
    void createAccount() {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== CREATE BANK ACCOUNT =====");

        System.out.print("Enter Name: ");
        name = sc.nextLine();

        System.out.print("Enter Phone: ");
        phone = sc.nextLine();

        System.out.print("Enter Address: ");
        address = sc.nextLine();

        System.out.print("Enter Account Type: ");
        accountType = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        balance = sc.nextDouble();

        String sql = "INSERT INTO accounts "
                   + "(name, phone, address, account_type, balance) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try {

            Connection con = JDBC.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setString(4, accountType);
            ps.setDouble(5, balance);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Account Created Successfully!");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        sc.close();
    }
}


// 3. Create main class
public class CreateAccount {

    public static void main(String[] args) {

        // 4. Create object
        Account obj = new Account();

        // Access class method using object
        obj.createAccount();
    }
}