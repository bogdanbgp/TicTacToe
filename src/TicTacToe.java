import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToe implements ActionListener {

    Random random = new Random(); // Random number generator for who goes first
    JFrame tttFrame = new JFrame(); // Main frame for the game
    JPanel tttTitlePanel = new JPanel(); // Panel for the title
    JPanel tttButtonPanel = new JPanel(); // Panel for buttons
    JLabel tttTextField = new JLabel(); // Text field to show who's turn it is
    JButton[] tttButtons = new JButton[9]; // Array for the 9 buttons
    boolean tttPlayer1_turn; // Boolean to check whose turn it is

    // Constructor for setting up the game
    TicTacToe() {
        // -----------------------------------------------------------
        // Frame Setup
        tttFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close app when frame is closed
        tttFrame.setSize(800, 800); // Size of the window
        tttFrame.getContentPane().setBackground(new Color(50, 50, 50)); // Background color
        tttFrame.setLayout(new BorderLayout()); // Layout for the frame
        // -----------------------------------------------------------

        // -----------------------------------------------------------
        // Title Setup
        tttTextField.setBackground(new Color(25, 25, 25)); // Background for text
        tttTextField.setForeground(new Color(25, 255, 0)); // Text color
        tttTextField.setFont(new Font("Charm", Font.BOLD, 69)); // Font for text
        tttTextField.setHorizontalAlignment(JLabel.CENTER); // Center the text
        tttTextField.setText("TiC-TaC-ToE"); // What it says
        tttTextField.setOpaque(true); // Make background visible

        tttTitlePanel.setLayout(new BorderLayout()); // Layout for title panel
        // -----------------------------------------------------------

        // -----------------------------------------------------------
        // Button Setup
        tttButtonPanel.setLayout(new GridLayout(3, 3)); // 3x3 grid for buttons
        tttButtonPanel.setBackground(new Color(150, 150, 150)); // Background color for buttons

        // Create buttons and add them to the panel
        for (int i = 0; i < 9; i++) {
            tttButtons[i] = new JButton(); // Create a button
            tttButtons[i].setFont(new Font("Charm", Font.BOLD, 120)); // Set font size
            tttButtons[i].setFocusable(false); // Don't let buttons be focused
            tttButtons[i].addActionListener(this); // Add action listener for button clicks
            tttButtons[i].setEnabled(false); // Disable buttons initially
            tttButtonPanel.add(tttButtons[i]); // Add button to panel
        }
        // -----------------------------------------------------------

        // -----------------------------------------------------------
        // Adding Panels to Frame
        tttTitlePanel.add(tttTextField); // Add title to the title panel
        tttFrame.add(tttTitlePanel, BorderLayout.NORTH); // Add title panel to top of frame
        tttFrame.add(tttButtonPanel); // Add button panel to the frame
        tttFrame.setVisible(true); // Show the frame
        // -----------------------------------------------------------

        // Start the game by determining who goes first
        firstTurn();
    }

    // -----------------------------------------------------------
    // Button click handler
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) { // Loop through all buttons
            if (e.getSource() == tttButtons[i] && tttButtons[i].getText().isEmpty()) { // Check if this button was clicked and is empty
                // Set text and color depending on whose turn it is
                tttButtons[i].setText(tttPlayer1_turn ? "X" : "O"); // Set X or O
                tttButtons[i].setForeground(tttPlayer1_turn ? Color.RED : Color.BLUE); // Color the text
                tttTextField.setText(tttPlayer1_turn ? "O turn" : "X turn"); // Update text field
                tttPlayer1_turn = !tttPlayer1_turn; // Toggle turn
                check(); // Check if someone has won
            }
        }
    }

// -----------------------------------------------------------

// Determine who goes first
public void firstTurn() {
    // Pause the execution for 2 seconds to give players a moment before the game starts
    try {
        Thread.sleep(2000); // Wait for 2000 milliseconds (2 seconds)
    } catch (InterruptedException e) {
        // If the sleep is interrupted, throw a runtime exception to indicate an error
        throw new RuntimeException(e); // Handle the exception by re-throwing it
    }

    // Randomly choose which player goes first; if the random number is 0, player 1 (X) goes first
    tttPlayer1_turn = random.nextInt(2) == 0; // Generates a random number: 0 (true for X) or 1 (false for O)

    // Update the text field to indicate whose turn it is based on the random choice
    tttTextField.setText(tttPlayer1_turn ? "X turn" : "O turn"); // If tttPlayer1_turn is true, show "X turn", otherwise show "O turn"

    // Enable all buttons now that the first player has been determined
    for (JButton button : tttButtons) {
        button.setEnabled(true); // Enable each button so players can make their moves
    }
}

// -----------------------------------------------------------

    // Check if someone has won
    public void check() {
        // Winning combinations in a 2D array
        int[][] winConditions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6} // Diagonals
        };

        // Check for a win for both players
        for (int[] condition : winConditions) {
            if (tttButtons[condition[0]].getText().equals("X") &&
                    tttButtons[condition[1]].getText().equals("X") &&
                    tttButtons[condition[2]].getText().equals("X")) {
                xWins(condition); // If X wins, highlight and stop
                return;
            } else if (tttButtons[condition[0]].getText().equals("O") &&
                    tttButtons[condition[1]].getText().equals("O") &&
                    tttButtons[condition[2]].getText().equals("O")) {
                oWins(condition); // If O wins, highlight and stop
                return;
            }
        }
    }

// -----------------------------------------------------------
    // Handle X's win
    public void xWins(int[] winCondition) {
        setWinColor(winCondition); // Highlight winning buttons
        disableButtons(); // Disable all buttons
        tttTextField.setText("X wins"); // Show X wins in text field
    }

    // Handle O's win
    public void oWins(int[] winCondition) {
        setWinColor(winCondition); // Highlight winning buttons
        disableButtons(); // Disable all buttons
        tttTextField.setText("O wins"); // Show O wins in text field
    }

// -----------------------------------------------------------
    // Highlight winning buttons
    private void setWinColor(int[] winCondition) {
        for (int index : winCondition) {
            tttButtons[index].setBackground(Color.GREEN); // Change color to green
        }
    }

    // Disable all buttons when the game ends
    private void disableButtons() {
        for (JButton button : tttButtons) {
            button.setEnabled(false); // Disable the button
        }
    }

}
