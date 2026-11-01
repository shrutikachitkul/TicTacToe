import javax.swing.*;
import java.awt.*;

public class GameModeFrame extends JFrame {
    private String username;

    public GameModeFrame(String username) {
        this.username = username;
        setTitle("Tic Tac Toe - Select Game Mode");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        createModeInterface();
    }

    private void createModeInterface() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(238, 244, 252));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        JLabel title = new JLabel("TIC TAC TOE", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 38));
        title.setForeground(new Color(50, 90, 170));

        JLabel welcome = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel instruction = new JLabel("Choose how you want to play", SwingConstants.CENTER);
        instruction.setFont(new Font("Arial", Font.PLAIN, 17));

        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.setOpaque(false);
        topPanel.add(title);
        topPanel.add(welcome);
        topPanel.add(instruction);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel modePanel = new JPanel(new GridLayout(1, 2, 30, 0));
        modePanel.setOpaque(false);

        JButton twoPlayerButton = new JButton(
            "<html><center><font size='6'>2 PARTICIPANTS</font><br><br>Play with a friend</center></html>"
        );

        JButton computerButton = new JButton(
            "<html><center><font size='6'>VS COMPUTER</font><br><br>Challenge the computer</center></html>"
        );

        twoPlayerButton.setBackground(new Color(60, 160, 100));
        twoPlayerButton.setForeground(Color.WHITE);
        computerButton.setBackground(new Color(60, 120, 190));
        computerButton.setForeground(Color.WHITE);
        twoPlayerButton.setFocusPainted(false);
        computerButton.setFocusPainted(false);

        modePanel.add(twoPlayerButton);
        modePanel.add(computerButton);
        mainPanel.add(modePanel, BorderLayout.CENTER);

        JButton logoutButton = new JButton("LOGOUT");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        mainPanel.add(logoutButton, BorderLayout.SOUTH);

        twoPlayerButton.addActionListener(e -> {
            dispose();
            new TicTacToeGame(username, false).setVisible(true);
        });

        computerButton.addActionListener(e -> {
            dispose();
            new TicTacToeGame(username, true).setVisible(true);
        });

        add(mainPanel);
    }
}