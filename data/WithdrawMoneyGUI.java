package bankmangamentsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WithdrawMoneyGUI extends JFrame {

    JTextField accountField;
    JTextField amountField;

    public WithdrawMoneyGUI() {

        setTitle("Withdraw Money");
        setSize(450, 350);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("WITHDRAW MONEY");
        title.setBounds(140, 30, 200, 30);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);

        JLabel accountLabel = new JLabel("Account Number:");
        accountLabel.setBounds(50, 90, 120, 25);
        add(accountLabel);

        accountField = new JTextField();
        accountField.setBounds(180, 90, 200, 25);
        add(accountField);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(50, 140, 120, 25);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(180, 140, 200, 25);
        add(amountField);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(150, 200, 150, 40);
        add(withdrawButton);

        withdrawButton.addActionListener(e -> withdrawMoney());

        setVisible(true);
    }

    private void withdrawMoney() {

        try {

            int accountNo =
                    Integer.parseInt(accountField.getText());

            double amount =
                    Double.parseDouble(amountField.getText());

            if (amount <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Amount!"
                );
                return;
            }

            Connection con = JDBC.getConnection();

            String checkSql =
                    "SELECT balance FROM accounts WHERE account_no = ?";

            PreparedStatement checkPs =
                    con.prepareStatement(checkSql);

            checkPs.setInt(1, accountNo);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {

                double oldBalance =
                        rs.getDouble("balance");

                if (amount > oldBalance) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Insufficient Balance!"
                    );

                    rs.close();
                    checkPs.close();
                    con.close();
                    return;
                }

                double newBalance =
                        oldBalance - amount;

                String updateSql =
                        "UPDATE accounts SET balance = ? " +
                        "WHERE account_no = ?";

                PreparedStatement updatePs =
                        con.prepareStatement(updateSql);

                updatePs.setDouble(1, newBalance);
                updatePs.setInt(2, accountNo);

                int result =
                        updatePs.executeUpdate();

                if (result > 0) {

                    // Save transaction
                    String transactionSql =
                            "INSERT INTO transactions " +
                            "(account_no, transaction_type, amount) " +
                            "VALUES (?, ?, ?)";

                    PreparedStatement transactionPs =
                            con.prepareStatement(transactionSql);

                    transactionPs.setInt(1, accountNo);
                    transactionPs.setString(2, "WITHDRAW");
                    transactionPs.setDouble(3, amount);

                    transactionPs.executeUpdate();

                    transactionPs.close();

                    JOptionPane.showMessageDialog(
                            this,
                            "Money Withdrawn Successfully!\n" +
                            "New Balance: " + newBalance
                    );
                }

                updatePs.close();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Account Not Found!"
                );
            }

            rs.close();
            checkPs.close();
            con.close();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numbers!"
            );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error!"
            );
        }
    }
}