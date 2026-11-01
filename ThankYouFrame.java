import javax.swing.*;
import java.awt.*;

public class ThankYouFrame extends JFrame {
    public ThankYouFrame(String username) {
        setTitle("Thank You - Tic Tac Toe");
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(new Color(235, 242, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 40, 40, 40));

        JLabel title = new JLabel("THANK YOU!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(new Color(50, 100, 170));

        JLabel message = new JLabel(
            "<html><center>Thank you for playing, " + username + "!<br><br>We hope you enjoyed the game.<br>See you again!</center></html>",
            SwingConstants.CENTER
        );
        message.setFont(new Font("Arial", Font.PLAIN, 19));

        JButton exitButton = new JButton("EXIT APPLICATION");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setBackground(new Color(190, 70, 70));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(title, BorderLayout.NORTH);
        panel.add(message, BorderLayout.CENTER);
        panel.add(exitButton, BorderLayout.SOUTH);

        add(panel);
    }
}