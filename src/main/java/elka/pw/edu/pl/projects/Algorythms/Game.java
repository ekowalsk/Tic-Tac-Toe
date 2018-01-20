package elka.pw.edu.pl.projects.Algorythms;

import elka.pw.edu.pl.projects.Enums.FieldType;

/**
 * stores current board and current player symbol, it also has a list of possible moves
 * of current player on current board
 */
public class Game {

    public FieldType[][] board;
    public FieldType playerSymbol;
    public FieldType opponentSymbol;
    public static final int MAX_POINTS = 1000;
    public static final int MIN_POINTS = -1000;



    public Game(FieldType[][] otherBoard, FieldType symbol) {
        board = new FieldType[3][3];
        setBoard(otherBoard);
        this.playerSymbol = symbol;
        if (symbol == FieldType.O)
            this.opponentSymbol = FieldType.X;
        else
            this.opponentSymbol = FieldType.O;
    }


    public int rateBoard(FieldType ratedPlayer) {
        int rating = 0;
        int playerRowCount = 0;
        int opponentRowCount = 0;
        int playerColumnCount = 0;
        int opponentColumnCount = 0;
        int playerCross1Count = 0;
        int opponentCross1Count = 0;
        int playerCross2Count = 0;
        int opponentCross2Count = 0;
        FieldType opponentPlayer;

        if (ratedPlayer == FieldType.O)
            opponentPlayer = FieldType.X;
        else
            opponentPlayer = FieldType.O;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board[x][y] == ratedPlayer)
                    playerColumnCount++;
                else if (board[x][y] == opponentPlayer)
                    opponentColumnCount++;
                if (board[y][x] == ratedPlayer)
                    playerRowCount++;
                else if (board[y][x] == opponentPlayer)
                    opponentRowCount++;
            }

            if (opponentColumnCount == 3 || opponentRowCount == 3)
                return MIN_POINTS;
            if (playerColumnCount == 3 || playerRowCount == 3)
                return MAX_POINTS;

            playerColumnCount = 0;
            opponentColumnCount = 0;
            playerRowCount = 0;
            opponentRowCount = 0;
        }
        for (int x = 0, y = 2; x < 3; x++, y--) {
            if (board[x][x] == ratedPlayer) {
                if (x == 1)
                    rating += 3;
                playerCross1Count++;
            } else if (board[x][x] == opponentPlayer) {
                if (x == 1)
                    rating -= 3;
                opponentCross1Count++;
            }
            if (board[x][y] == ratedPlayer) {
                playerCross2Count++;
            } else if (board[x][y] == opponentPlayer) {
                opponentCross2Count++;
            }
        }

        if (opponentCross1Count == 3 || opponentCross2Count == 3)
            return MIN_POINTS;
        if (playerCross1Count == 3 || playerCross2Count == 3)
            return MAX_POINTS;
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
