package bankmangamentsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


// 1. Create class
class UserLogin {

    // 2. Declare variables
    String username;
    String password;

    // 3. Declare method
    boolean login() {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n===== BANK LOGIN =====");

        System.out.print("Enter Username: ");
        username = sc.nextLine();

        System.out.print("Enter Password: ");
        password = sc.nextLine();

        try {

            Connection con = JDBC.getConnection();

            String sql =
                    "SELECT * FROM users "
                    + "WHERE username = ? AND password = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                System.out.println("\nLogin Successful!");
                System.out.println(
                        "Welcome " + username);

                rs.close();
                ps.close();
                con.close();

                return true;

            } else {

                System.out.println(
                        "\nInvalid Username or Password!");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}


// 4. Create main class
public class Login {

    public static void main(String[] args) {

        // 5. Create object
        UserLogin obj = new UserLogin();

        // 6. Access method using object
        boolean result = obj.login();

        if (result) {
            System.out.println("You can access Bank System.");
        } else {
            System.out.println("Login Failed!");
        }
    }
}