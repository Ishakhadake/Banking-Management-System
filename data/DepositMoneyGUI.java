package bankmangamentsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DepositMoneyGUI extends JFrame {

    JTextField accountField;
    JTextField amountField;

    public DepositMoneyGUI() {

        setTitle("Deposit Money");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("DEPOSIT MONEY");
        title.setBounds(130, 20, 200, 30);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);

        JLabel account = new JLabel("Account Number:");
        account.setBounds(40, 80, 120, 25);
        add(account);

        accountField = new JTextField();
        accountField.setBounds(170, 80, 180, 25);
        add(accountField);

        JLabel amount = new JLabel("Amount:");
        amount.setBounds(40, 125, 120, 25);
        add(amount);

        amountField = new JTextField();
        amountField.setBounds(170, 125, 180, 25);
        add(amountField);

        JButton deposit = new JButton("Deposit");
        deposit.setBounds(130, 180, 130, 40);
        add(deposit);

        deposit.addActionListener(e -> depositMoney());

        setVisible(true);
    }

    private void depositMoney() {

        int accountNo;
        double amount;

        try {
            accountNo = Integer.parseInt(accountField.getText());
            amount = Double.parseDouble(amountField.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter valid Account Number and Amount!"
            );
            return;
        }

        if (amount <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Amount must be greater than 0!"
            );
            return;
        }

        try {

            Connection con = JDBC.getConnection();

            String checkSql =
                    "SELECT balance FROM accounts WHERE account_no = ?";

            PreparedStatement checkPs =
                    con.prepareStatement(checkSql);

            checkPs.setInt(1, accountNo);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {

                double oldBalance = rs.getDouble("balance");
                double newBalance = oldBalance + amount;

                String updateSql =
                        "UPDATE accounts SET balance = ? "
                        + "WHERE account_no = ?";

                PreparedStatement updatePs =
                        con.prepareStatement(updateSql);

                updatePs.setDouble(1, newBalance);
                updatePs.setInt(2, accountNo);

                int result = updatePs.executeUpdate();

                if (result > 0) {

                    String transactionSql =
                            "INSERT INTO transactions "
                            + "(account_no, transaction_type, amount) "
                            + "VALUES (?, ?, ?)";

                    PreparedStatement transactionPs =
                            con.prepareStatement(transactionSql);

                    transactionPs.setInt(1, accountNo);
                    transactionPs.setString(2, "DEPOSIT");
                    transactionPs.setDouble(3, amount);

                    transactionPs.executeUpdate();

                    transactionPs.close();

                    JOptionPane.showMessageDialog(
                            this,
                            "Money Deposited Successfully!\n"
                            + "New Balance: ₹" + newBalance
                    );

                    accountField.setText("");
                    amountField.setText("");
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

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + e.getMessage()
            );
        }
    }

    public static void main(String[] args) {
        new DepositMoneyGUI();
    }
}