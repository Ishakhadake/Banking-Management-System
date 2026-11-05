package bankmangamentsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TransactionHistoryGUI extends JFrame {

    JTextField accountField;
    JTable table;
    DefaultTableModel model;

    public TransactionHistoryGUI() {

        setTitle("Transaction History");
        setSize(650, 450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("TRANSACTION HISTORY");
        title.setBounds(220, 20, 250, 30);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);

        JLabel accountLabel = new JLabel("Account Number:");
        accountLabel.setBounds(40, 70, 120, 25);
        add(accountLabel);

        accountField = new JTextField();
        accountField.setBounds(160, 70, 180, 25);
        add(accountField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(360, 70, 100, 25);
        add(searchButton);

        model = new DefaultTableModel();

        model.addColumn("Transaction ID");
        model.addColumn("Account No");
        model.addColumn("Type");
        model.addColumn("Amount");

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(40, 120, 560, 230);
        add(scrollPane);

        searchButton.addActionListener(e -> loadTransactions());

        setVisible(true);
    }

    private void loadTransactions() {

        try {

            int accountNo =
                    Integer.parseInt(accountField.getText());

            model.setRowCount(0);

            Connection con = JDBC.getConnection();

            String sql =
                    "SELECT transaction_id, account_no, " +
                    "transaction_type, amount " +
                    "FROM transactions " +
                    "WHERE account_no = ? " +
                    "ORDER BY transaction_id DESC";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, accountNo);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                model.addRow(new Object[] {
                        rs.getInt("transaction_id"),
                        rs.getInt("account_no"),
                        rs.getString("transaction_type"),
                        rs.getDouble("amount")
                });
            }

            if (!found) {

                JOptionPane.showMessageDialog(
                        this,
                        "No transactions found!"
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
        new TransactionHistoryGUI();
    }
}