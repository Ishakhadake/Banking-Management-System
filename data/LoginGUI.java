package bankmangamentsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginGUI extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public LoginGUI() {

        setTitle("MyBank - Login");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // MAIN PANEL
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        // TITLE
        JLabel title = new JLabel("BANK LOGIN");
        title.setBounds(50, 35, 350, 50);
        title.setFont(new Font("Arial", Font.BOLD, 35));
        title.setForeground(new Color(5, 35, 90));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title);

        // USERNAME LABEL
        JLabel username = new JLabel("Username:");
        username.setBounds(35, 125, 150, 35);
        username.setFont(new Font("Arial", Font.BOLD, 20));
        username.setForeground(new Color(40, 55, 75));
        panel.add(username);

        // USERNAME FIELD
        usernameField = new JTextField();
        usernameField.setBounds(165, 115, 240, 50);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(usernameField);

        // PASSWORD LABEL
        JLabel password = new JLabel("Password:");
        password.setBounds(35, 220, 150, 35);
        password.setFont(new Font("Arial", Font.BOLD, 20));
        password.setForeground(new Color(40, 55, 75));
        panel.add(password);

        // PASSWORD FIELD
        passwordField = new JPasswordField();
        passwordField.setBounds(165, 210, 240, 50);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(passwordField);

        // REMEMBER ME
        JCheckBox rememberMe = new JCheckBox("Remember Me");
        rememberMe.setBounds(35, 270, 185, 35);
        rememberMe.setFont(new Font("Arial", Font.PLAIN, 16));
        rememberMe.setBackground(Color.WHITE);
        panel.add(rememberMe);

        // FORGOT PASSWORD
        JLabel forgotPassword = new JLabel("Forgot Password?");
        forgotPassword.setBounds(230, 270, 190, 35);
        forgotPassword.setFont(new Font("Arial", Font.BOLD, 15));
        forgotPassword.setForeground(new Color(0, 65, 190));
        forgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(forgotPassword);

        forgotPassword.addMouseListener(
                new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                new ForgotPasswordGUI();
                dispose();
            }
        });

        // LOGIN BUTTON
        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(75, 325, 300, 65);
        loginButton.setFont(new Font("Arial", Font.BOLD, 30));
        loginButton.setForeground(new Color(5, 35, 90));
        loginButton.setBackground(new Color(220, 235, 250));
        loginButton.setFocusPainted(false);

        loginButton.setBorder(
                BorderFactory.createLineBorder(
                        new Color(150, 175, 200), 2));

        panel.add(loginButton);

        loginButton.addActionListener(e -> login());

        // BOTTOM LOGIN PANEL
        JPanel bottomPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 5, 0));

        bottomPanel.setBounds(20, 410, 410, 35);
        bottomPanel.setBackground(Color.WHITE);

        // DON'T HAVE AN ACCOUNT
        JLabel accountText =
                new JLabel("Don't have an account?");

        accountText.setFont(
                new Font("Arial", Font.PLAIN, 14));

        accountText.setForeground(Color.BLACK);

        // CREATE ACCOUNT
        JLabel createAccount =
                new JLabel("Create Account");

        createAccount.setFont(
                new Font("Arial", Font.BOLD, 15));

        createAccount.setForeground(Color.BLUE);

        createAccount.setCursor(
                new Cursor(Cursor.HAND_CURSOR));

        bottomPanel.add(accountText);
        bottomPanel.add(createAccount);

        panel.add(bottomPanel);

        createAccount.addMouseListener(
                new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                dispose();
                new RegisterGUI();
            }
        });

        setVisible(true);
    }

    // LOGIN METHOD
    private void login() {

        String username = usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please enter Username and Password!");

            return;
        }

        try {

            Connection con = JDBC.getConnection();

            String sql =
                    "SELECT * FROM users WHERE username = ? AND password = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                JOptionPane.showMessageDialog(this,
                        "Login Successful!");

                rs.close();
                ps.close();
                con.close();

                dispose();

                new Dashboard();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Invalid Username or Password!");

                rs.close();
                ps.close();
                con.close();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Database Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new LoginGUI());
    }
}