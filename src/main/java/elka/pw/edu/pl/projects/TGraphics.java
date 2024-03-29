package elka.pw.edu.pl.projects;

import elka.pw.edu.pl.projects.Algorythms.Game;
import elka.pw.edu.pl.projects.Algorythms.MinMax;
import elka.pw.edu.pl.projects.Enums.FieldType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Tic-Tac-Toe: Two-player Graphics version with Simple-OO
 */
@SuppressWarnings("serial")
public class TGraphics extends JFrame {
    // Named-constants for the game board
    public static final int ROWS = 3;  // ROWS by COLS cells
    public static final int COLS = 3;

    // Named-constants of the various dimensions used for graphics drawing
    public static final int CELL_SIZE = 100; // cell width and height (square)
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;  // the drawing canvas
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static final int GRID_WIDTH = 8;                   // Grid-line's width
    public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2; // Grid-line's half-width
    // Symbols (cross/nought) are displayed inside a cell, with padding from border
    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; // width/height
    public static final int SYMBOL_STROKE_WIDTH = 8; // pen's stroke width

    // Use an enumeration (inner class) to represent the various states of the game
    public enum GameState {
        PLAYING, DRAW, CROSS_WON, NOUGHT_WON
    }

    private GameState currentState;  // the current game state

    private FieldType currentPlayer;  // the current player

    private FieldType[][] board;
    private DrawCanvas canvas; // Drawing canvas (JPanel) for the game board
    private JLabel statusBar;  // Status Bar
    public static int depthX;
    public static int depthO;
    public static int mode;


    /**
     * Constructor to setup the game and the GUI components
     */
    public TGraphics(int dX, int dO, int m) {

        canvas = new DrawCanvas();  // Construct a drawing canvas (a JPanel)
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        depthX = dX;
        depthO = dO;
        mode = m;

        // The canvas (JPanel) fires a MouseEvent upon mouse-click
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
                int mouseX = e.getX();
                int mouseY = e.getY();
                // Get the row and column clicked
                int rowSelected = mouseY / CELL_SIZE;
                int colSelected = mouseX / CELL_SIZE;

                if (currentState == GameState.PLAYING) {
                    if (mode == 1) {
                        if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0
                                && colSelected < COLS && board[rowSelected][colSelected] == FieldType.E) {
                            board[rowSelected][colSelected] = currentPlayer; // Make a move
                            updateGame(currentPlayer, rowSelected, colSelected); // update state
                            // Switch player
                            currentPlayer = (currentPlayer == FieldType.X) ? FieldType.O : FieldType.X;

                            if (currentState != GameState.CROSS_WON) {
                                Game actualGame = new Game(board, currentPlayer);
                                MinMax minMax = new MinMax(actualGame);
                                minMax.chooseMove(depthO);
                                if (MinMax.possibleMoves[0] != null) {
                                    board[MinMax.possibleMoves[0].xMove][MinMax.possibleMoves[0].yMove] = currentPlayer;
                                    updateGame(currentPlayer, MinMax.possibleMoves[0].xMove, MinMax.possibleMoves[0].yMove);
                                    currentPlayer = (currentPlayer == FieldType.X) ? FieldType.O : FieldType.X;
                                }
                            }
                        }
                    }
                    else
                        comp2comp();
                } else {       // game over
                    initGame(); // restart the game
                }

                // Refresh the drawing canvas
                repaint();  // Call-back paintComponent().
            }
        });

        // Setup the status bar (JLabel) to display status message
        statusBar = new JLabel("  ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);
        cp.add(statusBar, BorderLayout.PAGE_END); // same as SOUTH

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();  // pack all the components in this JFrame
        setTitle("Tic Tac Toe");
        setVisible(true);  // show this JFrame
        initGame(); // initialize the game board contents and game variables
    }

    public void comp2comp() {
        if (currentState != GameState.CROSS_WON && currentState != GameState.NOUGHT_WON) {
            Game actualGame = new Game(board, currentPlayer);
            MinMax minMax = new MinMax(actualGame);
            if (currentPlayer == FieldType.X)
                minMax.chooseMove(depthX);
            else
                minMax.chooseMove(depthO);
            if (MinMax.possibleMoves[0] != null) {
                board[MinMax.possibleMoves[0].xMove][MinMax.possibleMoves[0].yMove] = currentPlayer;
                updateGame(currentPlayer, MinMax.possibleMoves[0].xMove, MinMax.possibleMoves[0].yMove);
                currentPlayer = (currentPlayer == FieldType.X) ? FieldType.O : FieldType.X;
            }
        }
    }

    /**
     * Initialize the game-board contents and the status
     */
    public void initGame() {
        board = new FieldType[ROWS][COLS]; // allocate array
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col] = FieldType.E; // all cells empty
            }
        }
        currentState = GameState.PLAYING; // ready to play
        currentPlayer = FieldType.X;       // cross plays first
    }

    /**
     * Update the currentState after the player with "theSeed" has placed on
     * (rowSelected, colSelected).
     */
    public void updateGame(FieldType theSeed, int rowSelected, int colSelected) {
        if (hasWon(theSeed, rowSelected, colSelected)) {  // check for win
            currentState = (theSeed == FieldType.X) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
        } else if (isDraw()) {  // check for draw
            currentState = GameState.DRAW;
        }
        // Otherwise, no change to current state (still GameState.PLAYING).
    }

    /**
     * Return true if it is a draw (i.e., no more empty cell)
     */
    public boolean isDraw() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (board[row][col] == FieldType.E) {
                    return false; // an empty cell found, not draw, exit
                }
            }
        }
        return true;  // no more empty cell, it's a draw
    }

    /**
     * Return true if the player with "theSeed" has won after placing at
     * (rowSelected, colSelected)
     */
    public boolean hasWon(FieldType theSeed, int rowSelected, int colSelected) {
        return (board[rowSelected][0] == theSeed  // 3-in-the-row
                && board[rowSelected][1] == theSeed
                && board[rowSelected][2] == theSeed
                || board[0][colSelected] == theSeed      // 3-in-the-column
                && board[1][colSelected] == theSeed
                && board[2][colSelected] == theSeed
                || rowSelected == colSelected            // 3-in-the-diagonal
                && board[0][0] == theSeed
                && board[1][1] == theSeed
                && board[2][2] == theSeed
                || rowSelected + colSelected == 2  // 3-in-the-opposite-diagonal
                && board[0][2] == theSeed
                && board[1][1] == theSeed
                && board[2][0] == theSeed);
    }

    /**
     * Inner class DrawCanvas (extends JPanel) used for custom graphics drawing.
     */
    class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {  // invoke via repaint()
            super.paintComponent(g);    // fill background
            setBackground(Color.WHITE); // set its background color

            // Draw the grid-lines
            g.setColor(Color.LIGHT_GRAY);
            for (int row = 1; row < ROWS; ++row) {
                g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDHT_HALF,
                        CANVAS_WIDTH - 1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
            }
            for (int col = 1; col < COLS; ++col) {
                g.fillRoundRect(CELL_SIZE * col - GRID_WIDHT_HALF, 0,
                        GRID_WIDTH, CANVAS_HEIGHT - 1, GRID_WIDTH, GRID_WIDTH);
            }

            // Draw the Seeds of all the cells if they are not empty
            // Use Graphics2D which allows us to set the pen's stroke
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND));  // Graphics2D only
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    int x1 = col * CELL_SIZE + CELL_PADDING;
                    int y1 = row * CELL_SIZE + CELL_PADDING;
                    if (board[row][col] == FieldType.X) {
                        g2d.setColor(Color.BLACK);
                        int x2 = (col + 1) * CELL_SIZE - CELL_PADDING;
                        int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
                        g2d.drawLine(x1, y1, x2, y2);
                        g2d.drawLine(x2, y1, x1, y2);
                    } else if (board[row][col] == FieldType.O) {
                        g2d.setColor(Color.RED);
                        g2d.drawOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);

                    }
                }
            }

            // Print status-bar message
            if (currentState == GameState.PLAYING) {
                statusBar.setForeground(Color.BLACK);
                if (currentPlayer == FieldType.X) {
                    statusBar.setText("X's Turn");
                } else {
                    statusBar.setText("O's Turn");
                }
            } else if (currentState == GameState.DRAW) {
                statusBar.setForeground(Color.RED);
                statusBar.setText("It's a Draw! Click to play again.");
            } else if (currentState == GameState.CROSS_WON) {
                statusBar.setForeground(Color.RED);
                statusBar.setText("'X' Won! Click to play again.");
            } else if (currentState == GameState.NOUGHT_WON) {
                statusBar.setForeground(Color.RED);
                statusBar.setText("'O' Won! Click to play again.");
            }

        }
    }

    private static int setXdepth(int a) {
        return a;
    }
}
