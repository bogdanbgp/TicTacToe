import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToe implements ActionListener {

    Random random = new Random();
    JFrame tttFrame = new JFrame();
    JPanel tttTitlePanel = new JPanel();
    JPanel tttButtonPanel = new JPanel();
    JLabel tttTextField = new JLabel();
    JButton[] tttButtons = new JButton[9];
    boolean tttPlayer1_turn;

    // set up the game
    TicTacToe() {
        // frame stuff
        tttFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tttFrame.setSize(800, 800);
        tttFrame.getContentPane().setBackground(new Color(50, 50, 50));
        tttFrame.setLayout(new BorderLayout());

        // title setup
        tttTextField.setBackground(new Color(25, 25, 25));
        tttTextField.setForeground(new Color(25, 255, 0));
        tttTextField.setFont(new Font("Charm", Font.BOLD, 69));
        tttTextField.setHorizontalAlignment(JLabel.CENTER);
        tttTextField.setText("tic-tac-toe");
        tttTextField.setOpaque(true);

        tttTitlePanel.setLayout(new BorderLayout());

        // buttons
        tttButtonPanel.setLayout(new GridLayout(3, 3));
        tttButtonPanel.setBackground(new Color(150, 150, 150));

        for (int i = 0; i < 9; i++) {
            tttButtons[i] = new JButton();
            tttButtons[i].setFont(new Font("Charm", Font.BOLD, 120));
            tttButtons[i].setFocusable(false);
            tttButtons[i].addActionListener(this);
            tttButtons[i].setEnabled(false);
            tttButtonPanel.add(tttButtons[i]);
        }

        // put everything together
        tttTitlePanel.add(tttTextField);
        tttFrame.add(tttTitlePanel, BorderLayout.NORTH);
        tttFrame.add(tttButtonPanel);
        tttFrame.setVisible(true);

        // start the game
        firstTurn();
    }

    // button click handler
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == tttButtons[i] && tttButtons[i].getText().isEmpty()) {
                tttButtons[i].setText(tttPlayer1_turn ? "X" : "O");
                tttButtons[i].setForeground(tttPlayer1_turn ? Color.RED : Color.BLUE);
                tttTextField.setText(tttPlayer1_turn ? "O turn" : "X turn");
                tttPlayer1_turn = !tttPlayer1_turn;
                check();
            }
        }
    }

    // pick who goes first
    public void firstTurn() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        tttPlayer1_turn = random.nextInt(2) == 0;
        tttTextField.setText(tttPlayer1_turn ? "X turn" : "O turn");

        for (JButton button : tttButtons) {
            button.setEnabled(true);
        }
    }

    // check for a win
    public void check() {
        int[][] winConditions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] condition : winConditions) {
            if (tttButtons[condition[0]].getText().equals("X") &&
                    tttButtons[condition[1]].getText().equals("X") &&
                    tttButtons[condition[2]].getText().equals("X")) {
                xWins(condition);
                return;
            } else if (tttButtons[condition[0]].getText().equals("O") &&
                    tttButtons[condition[1]].getText().equals("O") &&
                    tttButtons[condition[2]].getText().equals("O")) {
                oWins(condition);
                return;
            }
        }
    }

    // x wins
    public void xWins(int[] winCondition) {
        setWinColor(winCondition);
        disableButtons();
        tttTextField.setText("X wins");
    }

    // o wins
    public void oWins(int[] winCondition) {
        setWinColor(winCondition);
        disableButtons();
        tttTextField.setText("O wins");
    }

    // highlight win
    private void setWinColor(int[] winCondition) {
        for (int index : winCondition) {
            tttButtons[index].setBackground(Color.GREEN);
        }
    }

    // stop the game
    private void disableButtons() {
        for (JButton button : tttButtons) {
            button.setEnabled(false);
        }
    }
}
