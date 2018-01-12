package elka.pw.edu.pl.projects;

import elka.pw.edu.pl.projects.Algorythms.Game;

/**
 * stores finish state of board with all moves of player and points after last move
 */
public class MoveTrack {
    private Game finishState;
    private Position[] moves;
    private int freeIndex;

    public MoveTrack(Game game) {
        finishState = new Game (game.getBoard(), game.getPlayerSymbol());
        moves = new Position[100];
        freeIndex = 0;
    }

    public Game getGame() {
        return finishState;
    }

    public void addMove(Position pos) {
        moves[freeIndex] = pos;
        freeIndex++;
    }

}
