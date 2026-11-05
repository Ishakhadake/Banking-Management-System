package bankmangamentsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterGUI extends JFrame {

    JTextField nameField;
    JTextField usernameField;
    JPasswordField passwordField;

    public RegisterGUI() {

        setTitle("MyBank - Registration");
        setSize(450, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // TITLE
        JLabel title = new JLabel("REGISTRATION FORM");
        title.setBounds(75, 25, 320, 35);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(40, 55, 75));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);

        // FULL NAME
        JLabel name = new JLabel("Full Name:");
        name.setBounds(40, 100, 110, 25);
        name.setFont(new Font("Arial", Font.BOLD, 16));
        add(name);

        nameField = new JTextField();
        nameField.setBounds(155, 100, 230, 30);
        nameField.setFont(new Font("Arial", Font.PLAIN, 15));
        add(nameField);

        // USERNAME
        JLabel username = new JLabel("Username:");
        username.setBounds(40, 160, 110, 25);
        username.setFont(new Font("Arial", Font.BOLD, 16));
        add(username);

        usernameField = new JTextField();
        usernameField.setBounds(155, 160, 230, 30);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 15));
        add(usernameField);

        // PASSWORD
        JLabel password = new JLabel("Password:");
        password.setBounds(40, 220, 110, 25);
        password.setFont(new Font("Arial", Font.BOLD, 16));
        add(password);

        passwordField = new JPasswordField();
        passwordField.setBounds(155, 220, 230, 30);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        add(passwordField);

        // REGISTER BUTTON
        JButton register = new JButton("Register");
        register.setBounds(125, 300, 200, 45);
        register.setFont(new Font("Arial", Font.BOLD, 18));
        register.setBackground(new Color(220, 235, 250));
        register.setForeground(Color.BLACK);
        register.setFocusPainted(false);
        add(register);

        register.addActionListener(e -> registerUser());

     // LOGIN PANEL
        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(50, 375, 350, 30);
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        // ALREADY HAVE ACCOUNT - BLACK
        JLabel alreadyAccount = new JLabel("Already have an account?");
        alreadyAccount.setFont(new Font("Arial", Font.PLAIN, 15));
        alreadyAccount.setForeground(Color.BLACK);

        // LOGIN - BLUE
        JLabel loginText = new JLabel("Login");
        loginText.setFont(new Font("Arial", Font.BOLD, 15));
        loginText.setForeground(Color.BLUE);
        loginText.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginPanel.add(alreadyAccount);
        loginPanel.add(loginText);

        add(loginPanel);

        // LOGIN CLICK
        loginText.addMouseListener(
            new java.awt.event.MouseAdapter() {

                public void mouseClicked(java.awt.event.MouseEvent e) {
                    dispose();
                    new LoginGUI();
                }
            }
        );
        setVisible(true);
    }

    // REGISTER METHOD
    private void registerUser() {

        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (name.isEmpty() || username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!"
            );

            return;
        }

        try {

            Connection con = JDBC.getConnection();

            String sql = "INSERT INTO users "
                    + "(full_name, username, password) "
                    + "VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, password);

            int result = ps.executeUpdate();

            if (result > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration Successful!"
                );

                ps.close();
                con.close();

                dispose();
                new LoginGUI();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + e.getMessage()
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new RegisterGUI()
        );
    }
}