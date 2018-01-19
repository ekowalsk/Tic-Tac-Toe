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
        finishState = new Game (game.board, game.playerSymbol);
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

    public void printMove(){
        finishState.printBoard();
        System.out.println(finishState.playerSymbol);
    }

    public Position getMove (){
        return moves[0];
    }

}
