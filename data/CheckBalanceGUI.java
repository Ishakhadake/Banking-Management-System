package bankmangamentsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckBalanceGUI extends JFrame {

    JTextField accountField;

    public CheckBalanceGUI() {

        setTitle("Check Balance");
        setSize(450, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("CHECK BALANCE");
        title.setBounds(145, 30, 200, 30);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);

        JLabel accountLabel = new JLabel("Account Number:");
        accountLabel.setBounds(50, 90, 120, 25);
        add(accountLabel);

        accountField = new JTextField();
        accountField.setBounds(180, 90, 200, 25);
        add(accountField);

        JButton checkButton = new JButton("Check Balance");
        checkButton.setBounds(140, 150, 170, 40);
        add(checkButton);

        checkButton.addActionListener(e -> checkBalance());

        setVisible(true);
    }

    private void checkBalance() {

        try {

            int accountNo =
                    Integer.parseInt(accountField.getText());

            Connection con = JDBC.getConnection();

            String sql =
                    "SELECT name, balance FROM accounts " +
                    "WHERE account_no = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, accountNo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String name = rs.getString("name");
                double balance = rs.getDouble("balance");

                JOptionPane.showMessageDialog(
                        this,
                        "Account Holder: " + name +
                        "\nCurrent Balance: ₹" + balance,
                        "Balance",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Account Not Found!"
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Account Number!"
            );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + e.getMessage()
            );
        }
    }

    public static void main(String[] args) {
        new CheckBalanceGUI();
    }
}