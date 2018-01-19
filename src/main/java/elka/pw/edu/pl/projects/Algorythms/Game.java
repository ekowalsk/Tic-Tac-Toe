package elka.pw.edu.pl.projects.Algorythms;

import elka.pw.edu.pl.projects.Enums.FieldType;
import elka.pw.edu.pl.projects.Enums.HasWon;

/**
 * stores current board and current player symbol, it also has a list of possible moves
 * of current player on current board
 */
public class Game {

    public FieldType[][] board;
    public FieldType playerSymbol;
    public FieldType opponentSymbol;


    public Game(FieldType[][] otherBoard, FieldType symbol) {
        board = new FieldType[3][3];
        setBoard(otherBoard);
        this.playerSymbol = symbol;
        if (symbol == FieldType.O)
            this.opponentSymbol = FieldType.X;
        else
            this.opponentSymbol = FieldType.O;
    }

    /**
     * returns points in order to estimate how gainful is for the current Player the state of the Board after his move
     */
    public int rateBoard(HasWon isWinner) {
        int rating = 0;
        int playerRowCount = 0;
        int opponentRowCount = 0;
        int playerColumnCount = 0;
        int opponentColumnCount = 0;
        int playerCross1Count = 0;
        int opponentCross1Count = 0;
        int playerCross2Count = 0;
        int opponentCross2Count = 0;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board[x][y] == playerSymbol)
                    playerColumnCount++;
                else if (board[x][y] == opponentSymbol)
                    opponentColumnCount++;
                if (board[y][x] == playerSymbol)
                    playerRowCount++;
                else if (board[y][x] == opponentSymbol)
                    opponentRowCount++;
            }

            if (playerColumnCount == 3 || playerRowCount == 3) {
                isWinner.winner = playerSymbol;
                return 1000;
            }
            if (opponentColumnCount == 3 || opponentRowCount == 3) {
                isWinner.winner = opponentSymbol;
                return -1000;
            }

            playerColumnCount = 0;
            opponentColumnCount = 0;
            playerRowCount = 0;
            opponentRowCount = 0;
        }
        for (int x = 0, y = 2; x < 3; x++, y--) {
            if (board[x][x] == playerSymbol) {
                playerCross1Count++;
            } else if (board[x][x] == opponentSymbol) {
                opponentCross1Count++;
            }
            if (board[x][y] == playerSymbol) {
                playerCross2Count++;
            } else if (board[x][y] == opponentSymbol) {
                opponentCross2Count++;
            }
        }
        if (playerCross1Count == 3 || playerCross2Count == 3) {
            isWinner.winner = playerSymbol;
            return 1000;
        }
        if (opponentCross1Count == 3 || opponentCross2Count == 3) {
            isWinner.winner = opponentSymbol;
            return -1000;
        }
        return rating;
    }

    public void setBoard(FieldType[][] other) {
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                board[x][y] = other[x][y];
    }

    public void printBoard() {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++)
                System.out.print(board[x][y] + " ");
            System.out.println("\n");
        }
    }
}
