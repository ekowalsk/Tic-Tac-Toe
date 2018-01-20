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
        //boolean isPlayerWinner = false;
        //boolean has2op = false;
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

            if (opponentColumnCount == 3 || opponentRowCount == 3) {
                return -1000;
            }

            if (playerColumnCount == 3 || playerRowCount == 3) {
                //isPlayerWinner = true;
                //return 1000;
                return 1000;
            }

            /*if ((opponentColumnCount == 2 && playerColumnCount == 0)
                    || (opponentRowCount == 2 && playerRowCount == 0)) {
                has2op = true;
            }*/

            /*if ((opponentColumnCount == 0 && playerColumnCount == 2)
                    || (opponentRowCount == 0 && playerRowCount == 2)) {
                rating += 100;
            }*/

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

        if (opponentCross1Count == 3 || opponentCross2Count == 3) {
            return -1000;
        }
        if (playerCross1Count == 3 || playerCross2Count == 3) {
            //isPlayerWinner = true;
            return 1000;
        }

       /*if ((opponentCross1Count == 2 && playerCross1Count == 0)
                || (opponentCross2Count == 2 && playerCross2Count == 0)) {
            has2op = true;
        }*/

        /*if ((opponentCross1Count == 0 && playerCross1Count == 2)
                || (opponentCross2Count == 0 && playerCross2Count == 2)) {
            rating += 100;
        }*/


        /*if (isPlayerWinner)
            return 1000;
        if (has2op)
            return -1000;*/
        return rating;
    }

    public boolean hasWon(FieldType symbol, int rowSelected, int colSelected) {
        return (board[rowSelected][0] == symbol  // 3-in-the-row
                && board[rowSelected][1] == symbol
                && board[rowSelected][2] == symbol
                || board[0][colSelected] == symbol      // 3-in-the-column
                && board[1][colSelected] == symbol
                && board[2][colSelected] == symbol
                || rowSelected == colSelected            // 3-in-the-diagonal
                && board[0][0] == symbol
                && board[1][1] == symbol
                && board[2][2] == symbol
                || rowSelected + colSelected == 2  // 3-in-the-opposite-diagonal
                && board[0][2] == symbol
                && board[1][1] == symbol
                && board[2][0] == symbol);
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
