package elka.pw.edu.pl.projects;

import elka.pw.edu.pl.projects.Algorythms.Game;

/**
 * stores finish state of board with all moves of player and points after last move
 */
public class MoveTrack {
    private Game finishState;
    public int xMove;
    public int yMove;

    public MoveTrack(Game game, int x, int y) {
        finishState = new Game (game.board, game.playerSymbol);
        xMove = x;
        yMove = y;
    }

    public Game getGame() {
        return finishState;
    }

    public void printMove(){
        finishState.printBoard();
        System.out.println(finishState.playerSymbol);
    }
}
