package bankmangamentsystem;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard() {

        setTitle("Bank Management System");
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("BANK MANAGEMENT SYSTEM");
        title.setBounds(120, 30, 300, 30);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);

        JButton createAccount = new JButton("Create Account");
        createAccount.setBounds(140, 90, 220, 40);
        add(createAccount);
        createAccount.addActionListener(e -> {
            CreateAccountGUI.main(new String[]{});
        });

        JButton deposit = new JButton("Deposit Money");
        deposit.setBounds(140, 145, 220, 40);
        add(deposit);

        deposit.addActionListener(e -> {
            new DepositMoneyGUI();
        });

        JButton withdraw = new JButton("Withdraw Money");
        withdraw.setBounds(140, 200, 220, 40);
        add(withdraw);
        
        withdraw.addActionListener(e -> {
            new WithdrawMoneyGUI();
        });

        JButton balance = new JButton("Check Balance");
        balance.setBounds(140, 255, 220, 40);
        add(balance);
        balance.addActionListener(e -> {
            new CheckBalanceGUI();
        });

        JButton transfer = new JButton("Transfer Money");
        transfer.setBounds(140, 310, 220, 40);
        add(transfer);
        transfer.addActionListener(e -> {
            new TransferMoneyGUI();
        });

        JButton history = new JButton("Transaction History");
        history.setBounds(140, 365, 220, 40);
        add(history);
        history.addActionListener(e -> {
            new TransactionHistoryGUI();
        });
        JButton logout = new
        		JButton("logout");
        logout.setBounds(140,420,220,40);
        add(logout);
        logout.addActionListener(e->{
        	dispose();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
