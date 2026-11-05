package bankmangamentsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TransferMoneyGUI extends JFrame {

    JTextField senderField;
    JTextField receiverField;
    JTextField amountField;

    public TransferMoneyGUI() {

        setTitle("Transfer Money");
        setSize(450, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("TRANSFER MONEY");
        title.setBounds(135, 25, 200, 30);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);

        JLabel senderLabel = new JLabel("Sender Account:");
        senderLabel.setBounds(40, 80, 130, 25);
        add(senderLabel);

        senderField = new JTextField();
        senderField.setBounds(180, 80, 200, 25);
        add(senderField);

        JLabel receiverLabel = new JLabel("Receiver Account:");
        receiverLabel.setBounds(40, 125, 130, 25);
        add(receiverLabel);

        receiverField = new JTextField();
        receiverField.setBounds(180, 125, 200, 25);
        add(receiverField);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(40, 170, 130, 25);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(180, 170, 200, 25);
        add(amountField);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(145, 230, 150, 40);
        add(transferButton);

        transferButton.addActionListener(e -> transferMoney());

        setVisible(true);
    }

    private void transferMoney() {

        try {

            int senderAccount =
                    Integer.parseInt(senderField.getText());

            int receiverAccount =
                    Integer.parseInt(receiverField.getText());

            double amount =
                    Double.parseDouble(amountField.getText());

            if (amount <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Amount must be greater than 0!"
                );
                return;
            }

            if (senderAccount == receiverAccount) {
                JOptionPane.showMessageDialog(
                        this,
                        "Sender and Receiver accounts cannot be same!"
                );
                return;
            }

            Connection con = JDBC.getConnection();

            // Check sender
            String senderSql =
                    "SELECT balance FROM accounts WHERE account_no = ?";

            PreparedStatement senderPs =
                    con.prepareStatement(senderSql);

            senderPs.setInt(1, senderAccount);

            ResultSet senderRs =
                    senderPs.executeQuery();

            if (!senderRs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Sender Account Not Found!"
                );

                senderRs.close();
                senderPs.close();
                con.close();
                return;
            }

            double senderBalance =
                    senderRs.getDouble("balance");

            if (amount > senderBalance) {

                JOptionPane.showMessageDialog(
                        this,
                        "Insufficient Balance!"
                );

                senderRs.close();
                senderPs.close();
                con.close();
                return;
            }

            // Check receiver
            String receiverSql =
                    "SELECT account_no FROM accounts WHERE account_no = ?";

            PreparedStatement receiverPs =
                    con.prepareStatement(receiverSql);

            receiverPs.setInt(1, receiverAccount);

            ResultSet receiverRs =
                    receiverPs.executeQuery();

            if (!receiverRs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Receiver Account Not Found!"
                );

                senderRs.close();
                senderPs.close();
                receiverRs.close();
                receiverPs.close();
                con.close();
                return;
            }

            double newSenderBalance =
                    senderBalance - amount;

            // Deduct sender balance
            String updateSender =
                    "UPDATE accounts SET balance = ? " +
                    "WHERE account_no = ?";

            PreparedStatement senderUpdatePs =
                    con.prepareStatement(updateSender);

            senderUpdatePs.setDouble(1, newSenderBalance);
            senderUpdatePs.setInt(2, senderAccount);

            senderUpdatePs.executeUpdate();

            // Add receiver balance
            String updateReceiver =
                    "UPDATE accounts SET balance = balance + ? " +
                    "WHERE account_no = ?";

            PreparedStatement receiverUpdatePs =
                    con.prepareStatement(updateReceiver);

            receiverUpdatePs.setDouble(1, amount);
            receiverUpdatePs.setInt(2, receiverAccount);

            receiverUpdatePs.executeUpdate();

            // Sender transaction
            String transactionSql =
                    "INSERT INTO transactions " +
                    "(account_no, transaction_type, amount) " +
                    "VALUES (?, ?, ?)";

            PreparedStatement transactionPs =
                    con.prepareStatement(transactionSql);

            transactionPs.setInt(1, senderAccount);
            transactionPs.setString(2, "TRANSFER OUT");
            transactionPs.setDouble(3, amount);

            transactionPs.executeUpdate();

            // Receiver transaction
            transactionPs.setInt(1, receiverAccount);
            transactionPs.setString(2, "TRANSFER IN");
            transactionPs.setDouble(3, amount);

            transactionPs.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Money Transferred Successfully!"
            );

            senderField.setText("");
            receiverField.setText("");
            amountField.setText("");

            transactionPs.close();
            senderUpdatePs.close();
            receiverUpdatePs.close();
            senderRs.close();
            senderPs.close();
            receiverRs.close();
            receiverPs.close();
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
                    "Database Error: " + e.getMessage()
            );
        }
    }
}