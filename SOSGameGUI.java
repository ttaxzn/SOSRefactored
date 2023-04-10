import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SOSGameGUI extends JFrame {
	  private static final long serialVersionUID = 1L;
	    private JPanel gamePanel;
	    private JButton[][] buttonGrid;
	    private JLabel turnLabel;
	    private JRadioButton sRadioButton1, oRadioButton1, sRadioButton2, oRadioButton2;
	    private JRadioButton simpleGameRadioButton, generalGameRadioButton;
	    private ButtonGroup radioButtonGroup1, radioButtonGroup2, gameTypeButtonGroup;
	    private boolean isSimpleGame = true;
	    private boolean isPlayer1STurn = true;
	    private String player1Color = "blue";
	    private String player2Color = "red";
	    private int player1Score = 0;
	    private int player2Score = 0;
	    private int boardSize = 3;

    public SOSGameGUI() {
        super("SOS Game");

        // Set up game panel
        gamePanel = new JPanel(new GridLayout(boardSize, boardSize));
        buttonGrid = new JButton[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(100, 100));
                button.addActionListener(new ButtonListener(row, col));
                buttonGrid[row][col] = button;
                gamePanel.add(button);
            }
        }

        // Set up turn label
        turnLabel = new JLabel(player1Color + " (S) starts");
        turnLabel.setHorizontalAlignment(JLabel.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        turnLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Set up radio buttons for player 1
        JPanel radioButtonPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sRadioButton1 = new JRadioButton("S", true);
        oRadioButton1 = new JRadioButton("O", false);
        radioButtonGroup1 = new ButtonGroup();
        radioButtonGroup1.add(sRadioButton1);
        radioButtonGroup1.add(oRadioButton1);
        radioButtonPanel1.add(sRadioButton1);
        radioButtonPanel1.add(oRadioButton1);

        // Set up radio buttons for player 2
        JPanel radioButtonPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sRadioButton2 = new JRadioButton("S", true);
        oRadioButton2 = new JRadioButton("O", false);
        radioButtonGroup2 = new ButtonGroup();
        radioButtonGroup2.add(sRadioButton2);
        radioButtonGroup2.add(oRadioButton2);
        radioButtonPanel2.add(sRadioButton2);
        radioButtonPanel2.add(oRadioButton2);

        // Set up radio buttons for game type
        JPanel radioButtonPanel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        simpleGameRadioButton = new JRadioButton("Simple Game", true);
        generalGameRadioButton = new JRadioButton("General Game", false);
        gameTypeButtonGroup = new ButtonGroup();
        gameTypeButtonGroup.add(simpleGameRadioButton);
        gameTypeButtonGroup.add(generalGameRadioButton);
        radioButtonPanel3.add(simpleGameRadioButton);
        radioButtonPanel3.add(generalGameRadioButton);

        // Set up menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.addActionListener(new NewGameListener());
        gameMenu.add(newGameMenuItem);
        
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ExitListener());
        gameMenu.add(exitMenuItem);

        // Add game panel, turn label, and radio buttons to main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(turnLabel, BorderLayout.NORTH);
        mainPanel.add(radioButtonPanel1, BorderLayout.WEST);
        mainPanel.add(radioButtonPanel2, BorderLayout.EAST);
        mainPanel.add(radioButtonPanel3, BorderLayout.SOUTH);

        // Add menu bar to frame
        setJMenuBar(menuBar);
        menuBar.add(gameMenu);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void resetGame() {
        // Reset button text and enabled state
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                buttonGrid[row][col].setText("");
                buttonGrid[row][col].setEnabled(true);
            }
        }

        // Reset turn label and player scores
        isPlayer1STurn = true;
        player1Score = 0;
        player2Score = 0;
        updateTurnLabel();

        // Reset radio button states
        sRadioButton1.setSelected(true);
        sRadioButton2.setSelected(true);
        simpleGameRadioButton.setSelected(true);
    }

    private void updateTurnLabel() {
        if (isPlayer1STurn) {
            turnLabel.setText(player1Color + " (S)'s turn");
        } else {
            turnLabel.setText(player2Color + " (O)'s turn");
        }
    }

    private void switchTurns() {
        isPlayer1STurn = !isPlayer1STurn;
        updateTurnLabel();
    }

    private boolean checkForSOS(int row, int col) {
        boolean sosFound = false;

        // Check horizontal line to the left of (row, col)
        if (col >= 2 && buttonGrid[row][col-2].getText().equals("S")
                && buttonGrid[row][col-1].getText().equals("O")
                && buttonGrid[row][col].getText().equals("S")) {
            sosFound = true;
        }

        // Check horizontal line to the right of (row, col)
        if (col <= boardSize-3 && buttonGrid[row][col].getText().equals("S")
                && buttonGrid[row][col+1].getText().equals("O")
                && buttonGrid[row][col+2].getText().equals("S")) {
            sosFound = true;
        }

        // Check vertical line above (row, col)
        if (row >= 2 && buttonGrid[row-2][col].getText().equals("S")
                && buttonGrid[row-1][col].getText().equals("O")
                && buttonGrid[row][col].getText().equals("S")) {
            sosFound = true;
        }

        // Check vertical line below (row, col)
        if (row <= boardSize-3 && buttonGrid[row][col].getText().equals("S")
                && buttonGrid[row+1][col].getText().equals("O")
                && buttonGrid[row+2][col].getText().equals("S")) {
            sosFound = true;
        }

        // Check diagonal line from top-left to bottom-right of (row, col)
       
        if (row >= 2 && col >= 2 && buttonGrid[row-2][col-2].getText().equals("S")
        		&& buttonGrid[row-1][col-1].getText().equals("O")
        		&& buttonGrid[row][col].getText().equals("S")) {
        		sosFound = true;
        		}

     // Check diagonal line from bottom-left to top-right of (row, col)
        if (row <= boardSize-3 && col >= 2 && buttonGrid[row][col].getText().equals("S")
                && buttonGrid[row+1][col-1].getText().equals("O")
                && buttonGrid[row+2][col-2].getText().equals("S")) {
            sosFound = true;
        }

        // Check diagonal line from top-right to bottom-left of (row, col)
        if (row >= 2 && col <= boardSize-3 && buttonGrid[row-2][col+2].getText().equals("S")
                && buttonGrid[row-1][col+1].getText().equals("O")
                && buttonGrid[row][col].getText().equals("S")) {
            sosFound = true;
        }

        // Check diagonal line from bottom-right to top-left of (row, col)
        if (row <= boardSize-3 && col <= boardSize-3 && buttonGrid[row][col].getText().equals("S")
                && buttonGrid[row+1][col+1].getText().equals("O")
                && buttonGrid[row+2][col+2].getText().equals("S")) {
            sosFound = true;
        }

        return sosFound;
    }

    private void handleButtonClick(int row, int col) {
    // Check if button has already been clicked
    if (!buttonGrid[row][col].isEnabled()) {
    return;
    }
 // Set button text based on current player turn
    if (isPlayer1STurn) {
        buttonGrid[row][col].setText(player1Color);
    } else {
        buttonGrid[row][col].setText(player2Color);
    }

    // Check for SOS
    if (checkForSOS(row, col)) {
        if (isPlayer1STurn) {
            player1Score++;
        } else {
            player2Score++;
        }
        updateTurnLabel();
    } else {
        switchTurns();
    }

    // Disable button after click
    buttonGrid[row][col].setEnabled(false);
    }

    private class ButtonListener implements ActionListener {
    private int row;
    private int col;public ButtonListener(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        handleButtonClick(row, col);
    }
    }

    private class ExitListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
    System.exit(0);
    }
    }
    private class NewGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetGame();
        }
    }
    public static void main(String[] args) {
    new SOSGameGUI();
    }
}