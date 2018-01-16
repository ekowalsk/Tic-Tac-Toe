package elka.pw.edu.pl.projects.Algorythms;

import elka.pw.edu.pl.projects.Board;
import elka.pw.edu.pl.projects.Enums.FieldType;
import elka.pw.edu.pl.projects.MoveTrack;
import elka.pw.edu.pl.projects.Position;

public class MinMax {
    public Game currentState;
    public MoveTrack[] possibleMoves;

    public MinMax(Game state) {
        currentState = new Game(state.getBoard(), state.getPlayerSymbol());
        possibleMoves = new MoveTrack[600];
    }

    public void findAllMoves(Game game, MoveTrack[] moves) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (game.getBoard().getField(x, y) == FieldType.E) {
                    Board newBoard = new Board();
                    newBoard.setBoard(game.getBoard().getBoard());
                    newBoard.setField(new Position(x, y), game.getPlayerSymbol());
                    Game newGame = new Game(newBoard, game.getOpponentSymbol());
                    MoveTrack moveTrack = new MoveTrack(newGame);
                    moveTrack.addMove(new Position(x, y));
                    int index = 0;
                    while (moves[index] != null)
                    index++;
                    moves[index] = moveTrack;

                }
            }
        }
    }


    public int doMinMax(int n, Game game) {
        int ret;
        int tmp;
        MoveTrack[] moves = new MoveTrack[500];
        Game newGame = new Game(game.getBoard(), game.getPlayerSymbol());

        if (newGame.getPlayerSymbol() == FieldType.O)
            ret = Integer.MIN_VALUE;
        else
            ret = Integer.MAX_VALUE;

        if (n > 0) {
            findAllMoves(newGame, moves);
        }
        /*for (int i = 0; moves[i] != null; i++) {
            moves[i].printMove();
        }*/

        if (n == 0 || moves[0] == null) {
            return newGame.rateBoard();
        }
        for (int k = 0; moves[k] != null; k++) {
            tmp = doMinMax(n - 1, moves[k].getGame());
            if (newGame.getPlayerSymbol() == FieldType.O)
                ret = (ret > tmp ? ret : tmp);
            else
                ret = (ret < tmp ? ret : tmp);
        }
        return ret;
    }

    public int chooseMove(int nMoves) {
        int index = 0, maxPoints = Integer.MIN_VALUE, currentPoints;
        findAllMoves(currentState, possibleMoves);
        /*int i = 0;
        while (possibleMoves[i] != null) {
            System.out.println(i);
            possibleMoves[i].printMove();
            i++;
        }*/
        int k = 0;
        while (possibleMoves[k] != null) {
            if ((currentPoints = doMinMax(nMoves, possibleMoves[k].getGame())) > maxPoints) {
                maxPoints = currentPoints;
                index = k;
            }
            k++;
        }
        currentState.setBoard(possibleMoves[index].getGame().getBoard());
        return index;
    }
}
