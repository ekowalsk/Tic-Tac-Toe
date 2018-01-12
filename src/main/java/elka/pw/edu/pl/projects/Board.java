package elka.pw.edu.pl.projects;

import elka.pw.edu.pl.projects.Enums.FieldType;


/**
 * Board stores board used in tictactoe game
 */
public class Board {

    private FieldType[][] board;

    public Board() {
        board = new FieldType[3][3];
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++) {
                board[x][y] = FieldType.E;
            }
    }

    public FieldType[][] getBoard() {
        return board;
    }

    public void setBoard(FieldType[][] otherBoard) {
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++) {
                board[x][y] = otherBoard[x][y];
            }
    }

    public void setField(int x, int y, FieldType value) {
        board[x][y] = value;
    }

    public FieldType getField(int x, int y){
        return board[x][y];
    }

    public void print() {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                System.out.print(board[x][y] + " ");

            }
            System.out.println("\n");
        }
    }
}
