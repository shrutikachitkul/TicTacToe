import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jdbc.Tictactoeconn;

public class SignupFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public SignupFrame() {

        setTitle("Tic Tac Toe - Create Account");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createSignupInterface();
    }

    private void createSignupInterface() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(15, 15));

        mainPanel.setBackground(
                new Color(235, 242, 250)
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 40, 30, 40
                )
        );

        JLabel title = new JLabel(
                "CREATE ACCOUNT",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 30)
        );

        title.setForeground(
                new Color(55, 120, 170)
        );

        mainPanel.add(title, BorderLayout.NORTH);

        JPanel formPanel =
                new JPanel(new GridLayout(6, 1, 8, 8));

        formPanel.setOpaque(false);

        JLabel usernameLabel =
                new JLabel("Create Username");

        usernameField = new JTextField();

        JLabel passwordLabel =
                new JLabel("Create Password");

        passwordField = new JPasswordField();

        JLabel confirmLabel =
                new JLabel("Confirm Password");

        confirmPasswordField =
                new JPasswordField();

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        formPanel.add(confirmLabel);
        formPanel.add(confirmPasswordField);

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        JButton createButton =
                new JButton("SIGN UP");

        JButton backButton =
                new JButton("BACK TO LOGIN");

        createButton.setBackground(
                new Color(60, 160, 100)
        );

        createButton.setForeground(Color.WHITE);

        JPanel buttonPanel =
                new JPanel(new GridLayout(1, 2, 15, 0));

        buttonPanel.setOpaque(false);

        buttonPanel.add(createButton);
        buttonPanel.add(backButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        createButton.addActionListener(
                e -> createAccount()
        );

        backButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        add(mainPanel);
    }

    private void createAccount() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        String confirmPassword =
                new String(
                        confirmPasswordField.getPassword()
                );

        if (username.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }

        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match."
            );

            return;
        }

        try {

            Connection conn =
                    Tictactoeconn.getConnection();

            // Check if username already exists
            String checkSql =
                    "SELECT * FROM users WHERE username = ?";

            PreparedStatement checkPs =
                    conn.prepareStatement(checkSql);

            checkPs.setString(1, username);

            ResultSet rs =
                    checkPs.executeQuery();

            if (rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Username already exists."
                );

                rs.close();
                checkPs.close();
                conn.close();

                return;
            }

            rs.close();
            checkPs.close();

            // Insert new account
            String sql =
                    "INSERT INTO users(username, password) VALUES (?, ?)";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            int result =
                    ps.executeUpdate();

            if (result > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Account created successfully!"
                );

                ps.close();
                conn.close();

                dispose();

                new LoginFrame().setVisible(true);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Account creation failed."
                );

                ps.close();
                conn.close();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error."
            );

            e.printStackTrace();
        }
    }
}