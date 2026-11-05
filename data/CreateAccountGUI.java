package bankmangamentsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreateAccountGUI extends JFrame {

    JTextField nameField;
    JTextField phoneField;
    JTextField addressField;
    JTextField typeField;
    JTextField balanceField;

    public CreateAccountGUI() {

        setTitle("Create Bank Account");
        setSize(450, 450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("CREATE BANK ACCOUNT");
        title.setBounds(120, 20, 250, 30);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);

        JLabel name = new JLabel("Name:");
        name.setBounds(50, 70, 100, 25);
        add(name);

        nameField = new JTextField();
        nameField.setBounds(160, 70, 220, 25);
        add(nameField);

        JLabel phone = new JLabel("Phone:");
        phone.setBounds(50, 110, 100, 25);
        add(phone);

        phoneField = new JTextField();
        phoneField.setBounds(160, 110, 220, 25);
        add(phoneField);

        JLabel address = new JLabel("Address:");
        address.setBounds(50, 150, 100, 25);
        add(address);

        addressField = new JTextField();
        addressField.setBounds(160, 150, 220, 25);
        add(addressField);

        JLabel type = new JLabel("Account Type:");
        type.setBounds(50, 190, 100, 25);
        add(type);

        typeField = new JTextField();
        typeField.setBounds(160, 190, 220, 25);
        add(typeField);

        JLabel balance = new JLabel("Initial Balance:");
        balance.setBounds(50, 230, 100, 25);
        add(balance);

        balanceField = new JTextField();
        balanceField.setBounds(160, 230, 220, 25);
        add(balanceField);

        JButton create = new JButton("Create Account");
        create.setBounds(130, 290, 180, 40);
        add(create);

        create.addActionListener(e -> createAccount());

        setVisible(true);
    }

    private void createAccount() {

        String name = nameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String accountType = typeField.getText();

        double balance;

        try {
            balance = Double.parseDouble(balanceField.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter valid balance!"
            );
            return;
        }

        if (name.isEmpty() || phone.isEmpty()
                || address.isEmpty() || accountType.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!"
            );
            return;
        }

        try {

            Connection con = JDBC.getConnection();

            String sql = "INSERT INTO accounts "
                    + "(name, phone, address, account_type, balance) "
                    + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setString(4, accountType);
            ps.setDouble(5, balance);

            int result = ps.executeUpdate();

            if (result > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Account Created Successfully!"
                );

                nameField.setText("");
                phoneField.setText("");
                addressField.setText("");
                typeField.setText("");
                balanceField.setText("");
            }

            ps.close();
            con.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + e.getMessage()
            );
        }
    }

    public static void main(String[] args) {
        new CreateAccountGUI();
    }
}