package elka.pw.edu.pl.projects.Algorythms;

import elka.pw.edu.pl.projects.Board;
import elka.pw.edu.pl.projects.Enums.FieldType;

import java.util.List;

public class MinMax {
    private Game currentState;

    public MinMax(Game state) {
        this.currentState.setBoard(state.getBoard());
    }

    public int doMinMax(int n, Board board) {
        int ret;
        int tmp;
        Game game = new Game(board, FieldType.CIRCLE);

        if (n % 2 == 1) {
            game.setPlayerSymbol(FieldType.CIRCLE);
            game.setOpponentSymbol(FieldType.CROSS);
        } else {
            game.setPlayerSymbol(FieldType.CROSS);
            game.setOpponentSymbol(FieldType.CIRCLE);
        }

        if (game.getPlayerSymbol() == FieldType.CIRCLE)
            ret = 1000;
        else
            ret = -1000;

        if (n > 0){
            game.findAllMoves();
        }

        if (n == 0)
            return game.rateBoard();
        List<Board> moves = game.getPossibleMoves();
        for (Board mv : moves){
            tmp = doMinMax(n-1, mv);
            if ( game.getPlayerSymbol() == FieldType.CROSS )
                ret = ( ret > tmp ? ret : tmp );
            else
                ret = ( ret < tmp ? ret : tmp );
        }
        return ret;
    }
}
