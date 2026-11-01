import javax.swing.*;
import java.awt.*;

public class TicTacToeGame extends JFrame {
    private JButton[][] cells = new JButton[3][3];
    private char[][] board = new char[3][3];
    private char currentPlayer = 'X';
    private String username;
    private boolean computerMode;
    private boolean gameFinished = false;
    private JLabel statusLabel;

    public TicTacToeGame(String username, boolean computerMode) {
        this.username = username;
        this.computerMode = computerMode;
        setTitle(computerMode ? "Tic Tac Toe - VS Computer" : "Tic Tac Toe - 2 Participants");
        setSize(650, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initializeBoard();
        createGameInterface();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private void createGameInterface() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(238, 243, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("TIC TAC TOE", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(new Color(50, 90, 170));

        statusLabel = new JLabel(username + "'s Turn - X", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setOpaque(false);
        topPanel.add(title);
        topPanel.add(statusLabel);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 8, 8));
        boardPanel.setBackground(Color.BLACK);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton cell = new JButton();
                cell.setBackground(Color.WHITE);
                cell.setFont(new Font("Arial", Font.BOLD, 65));
                cell.setFocusPainted(false);

                final int selectedRow = row;
                final int selectedCol = col;

                cell.addActionListener(e -> cellClicked(selectedRow, selectedCol));
                cells[row][col] = cell;
                boardPanel.add(cell);
            }
        }

        mainPanel.add(boardPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);

        JButton restartButton = new JButton("RESTART");
        JButton menuButton = new JButton("GAME MODES");

        restartButton.setFont(new Font("Arial", Font.BOLD, 15));
        menuButton.setFont(new Font("Arial", Font.BOLD, 15));

        bottomPanel.add(restartButton);
        bottomPanel.add(menuButton);

        restartButton.addActionListener(e -> restartGame());
        menuButton.addActionListener(e -> {
            dispose();
            new GameModeFrame(username).setVisible(true);
        });

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void cellClicked(int row, int col) {
        // Check if game is finished or cell is already occupied
        if (gameFinished || board[row][col] != ' ') {
            return;
        }

        // In computer mode, don't allow clicking when it's computer's turn
        if (computerMode && currentPlayer == 'O') {
            return;
        }

        // Place the current player's symbol (X or O)
        putSymbol(row, col, currentPlayer);

        // Check if game is over
        if (checkGame()) {
            return;
        }

        // Switch to next player
        if (computerMode) {
            // Computer mode: after player's move, computer plays
            currentPlayer = 'O';
            statusLabel.setText("Computer's Turn - O");
            
            Timer timer = new Timer(500, e -> computerMove());
            timer.setRepeats(false);
            timer.start();
        } else {
            // Two player mode: switch between X and O
            if (currentPlayer == 'X') {
                currentPlayer = 'O';
                statusLabel.setText("Player 2's Turn - O");
            } else {
                currentPlayer = 'X';
                statusLabel.setText(username + "'s Turn - X");
            }
        }
    }

    private void putSymbol(int row, int col, char symbol) {
        board[row][col] = symbol;
        cells[row][col].setText(String.valueOf(symbol));
        
        if (symbol == 'X') {
            cells[row][col].setForeground(new Color(40, 90, 210));
        } else {
            cells[row][col].setForeground(new Color(220, 50, 50));
        }
    }

    private void computerMove() {
        if (gameFinished) return;

        int row = -1, col = -1;

        // Try center first
        if (board[1][1] == ' ') {
            row = 1;
            col = 1;
        } else {
            // Find any empty cell
            for (int i = 0; i < 3 && row == -1; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        row = i;
                        col = j;
                        break;
                    }
                }
            }
        }

        if (row != -1) {
            putSymbol(row, col, 'O');
        }

        if (checkGame()) return;

        // Switch back to player's turn
        currentPlayer = 'X';
        statusLabel.setText(username + "'s Turn - X");
    }

    private boolean checkGame() {
        if (hasWinner('X')) {
            gameFinished = true;
            showResult(username + " WINS!");
            return true;
        }
        if (hasWinner('O')) {
            gameFinished = true;
            showResult(computerMode ? "COMPUTER WINS!" : "PLAYER 2 WINS!");
            return true;
        }
        if (boardFull()) {
            gameFinished = true;
            showResult("IT'S A DRAW!");
            return true;
        }
        return false;
    }

    private boolean hasWinner(char player) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }
        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        // Check diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        return false;
    }

    private boolean boardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void showResult(String result) {
        int choice = JOptionPane.showConfirmDialog(
            this,
            result + "\n\nDo you want to play again?",
            "GAME OVER",
            JOptionPane.YES_NO_OPTION
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            new GameModeFrame(username).setVisible(true);
        } else {
            dispose();
            new ThankYouFrame(username).setVisible(true);
        }
    }

    private void restartGame() {
        initializeBoard();
        currentPlayer = 'X';
        gameFinished = false;
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j].setText("");
                cells[i][j].setForeground(Color.BLACK);
            }
        }
        
        statusLabel.setText(username + "'s Turn - X");
    }
}