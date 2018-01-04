package elka.pw.edu.pl.projects.Algorythms;
import elka.pw.edu.pl.projects.Position;
import elka.pw.edu.pl.projects.Board;
import elka.pw.edu.pl.projects.Player;



public class pickBestField {
    private Position bestPosition;
    private Board board;
    private Player currentPlayer;

    public pickBestField(){

    }

    public Position getBestPosition() {
        return bestPosition;
    }

    public void setBestPosition(Position bestPosition) {
        this.bestPosition = bestPosition;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

}
