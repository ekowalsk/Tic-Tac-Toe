package elka.pw.edu.pl.projects.Algorythms;

import elka.pw.edu.pl.projects.Board;
import elka.pw.edu.pl.projects.Enums.FieldType;
import elka.pw.edu.pl.projects.MoveTrack;
import elka.pw.edu.pl.projects.Position;

public class MinMax {
    public Game currentState;
    public MoveTrack[] possibleMoves;
    public int MoveIndex;

    public MinMax(Game state) {
        currentState = new Game(state.getBoard(), state.getPlayerSymbol());
        possibleMoves = new MoveTrack[400];
    }

    public void findAllMoves(Game game, MoveTrack[] moves) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (game.getBoard().getField(x, y) == FieldType.E) {
                    Board newBoard = new Board();
                    newBoard.setBoard(game.getBoard().getBoard());
                    newBoard.setField(x, y, game.getPlayerSymbol());
                    Game newGame = new Game(newBoard, game.getOpponentSymbol());
                    MoveTrack moveTrack = new MoveTrack(newGame);
                    moveTrack.addMove(new Position(x, y));
                    int index = 0;
                    while(moves[index] != null)
                        index++;
                    moves[index] = moveTrack;
                }
            }
        }
    }


    public int doMinMax(int n, Board board) {
        int ret;
        int tmp;
        MoveTrack[] moves = new MoveTrack[100];
        Game game = new Game(board, FieldType.O);

        if (n % 2 == 1) {
            game.setPlayerSymbol(FieldType.X);
            game.setOpponentSymbol(FieldType.O);
        } else {
            game.setPlayerSymbol(FieldType.O);
            game.setOpponentSymbol(FieldType.X);
        }

        if (game.getPlayerSymbol() == FieldType.O)
            ret = Integer.MAX_VALUE;
        else
            ret = Integer.MIN_VALUE;

        if (n > 0) {
            findAllMoves(game, moves);
        }
        for (int i = 0; moves[i] != null; i++){
            moves[i].getGame().getBoard().print();
        }

        if (n == 0 || moves[0] == null)
            return game.rateBoard();
        for (int k = 0; moves[k] != null; k++) {
            tmp = doMinMax(n-1, moves[k].getGame().getBoard());
            if (game.getPlayerSymbol() == FieldType.X)
                ret = (ret > tmp ? ret : tmp);
            else
                ret = (ret < tmp ? ret : tmp);
        }
        return ret;
    }

    public int chooseMove( int nMoves) {
        int index = 0, maxPoints = Integer.MIN_VALUE, currentPoints = 0;
        int i = 0;
        findAllMoves(currentState, possibleMoves);
        for (int l = 0; possibleMoves[l] != null; l++)
            possibleMoves[l].getGame().getBoard().print();
        while (possibleMoves[i] != null) {
            if ((currentPoints = doMinMax(nMoves, possibleMoves[i].getGame().getBoard())) > maxPoints) {
                maxPoints = currentPoints;
                index = i;
            }
            i++;
        }
        currentState.setBoard(possibleMoves[index].getGame().getBoard());
        possibleMoves[0] = possibleMoves[index];
        return index;
    }
}
