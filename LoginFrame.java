import javax.swing.*;
import java.awt.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jdbc.Tictactoeconn;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Tic Tac Toe - Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        createLoginInterface();
    }

    private void createLoginInterface() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(235, 242, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("TIC TAC TOE", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(new Color(50, 90, 170));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 8, 8));
        formPanel.setOpaque(false);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 17));

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 17));

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        JButton loginButton = new JButton("LOGIN");
        JButton signupButton = new JButton("CREATE ACCOUNT");

        loginButton.setBackground(new Color(60, 120, 190));
        loginButton.setForeground(Color.WHITE);
        signupButton.setBackground(new Color(60, 160, 100));
        signupButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        signupButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> login());
        signupButton.addActionListener(e -> {
            dispose();
            new SignupFrame().setVisible(true);
        });

        add(mainPanel);
    }

    private void login() {

    String username = usernameField.getText().trim();
    String password = new String(passwordField.getPassword());

    if (username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Please enter username and password.");
        return;
    }

    try {

        Connection conn = Tictactoeconn.getConnection();

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            JOptionPane.showMessageDialog(this,
                    "Login successful!");

            rs.close();
            ps.close();
            conn.close();

            dispose();

            new GameModeFrame(username).setVisible(true);

        } else {

            JOptionPane.showMessageDialog(this,
                    "Incorrect username or password.");

            rs.close();
            ps.close();
            conn.close();
        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,
                "Database error.");

        e.printStackTrace();
    }
}
}