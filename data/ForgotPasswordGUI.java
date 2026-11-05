package bankmangamentsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ForgotPasswordGUI extends JFrame {

    JTextField usernameField;
    JPasswordField newPasswordField;
    JPasswordField confirmPasswordField;

    public ForgotPasswordGUI() {

        setTitle("Forgot Password");
        setSize(400, 350);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel title = new JLabel("RESET PASSWORD");
        title.setBounds(110, 25, 220, 30);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(40, 85, 120, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(160, 85, 180, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("New Password:");
        passwordLabel.setBounds(40, 130, 120, 25);
        add(passwordLabel);

        newPasswordField = new JPasswordField();
        newPasswordField.setBounds(160, 130, 180, 25);
        add(newPasswordField);

        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setBounds(40, 175, 120, 25);
        add(confirmLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(160, 175, 180, 25);
        add(confirmPasswordField);

        JButton resetButton = new JButton("RESET");
        resetButton.setBounds(130, 230, 130, 35);
        add(resetButton);

        resetButton.addActionListener(e -> resetPassword());

        setVisible(true);
    }

    private void resetPassword() {

        String username = usernameField.getText().trim();

        String password =
                new String(newPasswordField.getPassword());

        String confirmPassword =
                new String(confirmPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty()
                || confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please fill all fields!");
            return;
        }

        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(this,
                    "Password does not match!");
            return;
        }

        try {

            Connection con = JDBC.getConnection();

            String sql =
                    "UPDATE users SET password = ? WHERE username = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, password);
            ps.setString(2, username);

            int result = ps.executeUpdate();

            if (result > 0) {

                JOptionPane.showMessageDialog(this,
                        "Password Changed Successfully!");

                dispose();
                new LoginGUI();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Username Not Found!");
            }

            ps.close();
            con.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Database Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        new ForgotPasswordGUI();
    }
}