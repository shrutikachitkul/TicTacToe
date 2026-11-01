import javax.swing.SwingUtilities;

public class StyledTicTacToe {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });

    }
}