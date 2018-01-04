package elka.pw.edu.pl.projects;

/**
 * TicTacToe represents popular paper and pencil game
 */
public class Board {

    public enum fieldType{
        CIRCLE, CROSS, EMPTY
    }

    private fieldType[][] board;

    public Board() {
        board = new fieldType[3][3];
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
            {
                board[x][y] = fieldType.EMPTY;
            }
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
