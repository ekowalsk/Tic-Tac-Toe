package elka.pw.edu.pl.projects.Algorythms;

import elka.pw.edu.pl.projects.Enums.FieldType;
import elka.pw.edu.pl.projects.MoveTrack;

public class MinMax {
    public Game currentState;
    public static MoveTrack[] possibleMoves;

    public MinMax(Game state) {
        currentState = new Game(state.board, state.playerSymbol);
        possibleMoves = new MoveTrack[600];
    }

    public void findAllMoves(Game game, MoveTrack[] moves) {
        int index = 0;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (game.board[x][y] == FieldType.E) {
                    Game newGame = new Game(game.board, game.opponentSymbol);
                    newGame.board[x][y] = game.playerSymbol;
                    MoveTrack moveTrack = new MoveTrack(newGame, x, y);
                    //int index = 0;
                    //while (moves[index] != null)
                    //index++;
                    moves[index] = moveTrack;
                    index++;
                }
            }
        }
    }

    public int doMinMax(int n, Game game, FieldType player, int alpha, int beta) {
        int tmp = 0;
        FieldType opponent;
        MoveTrack[] moves = new MoveTrack[100];
        Game newGame = new Game(game.board, game.playerSymbol);
        int ret;
        if (newGame.playerSymbol == player)
            ret = Integer.MIN_VALUE;
        else
            ret = Integer.MAX_VALUE;

        if (n > 0)
            findAllMoves(newGame, moves);

        if (n == 0 || moves[0] == null) {
            return newGame.rateBoard(player);
        }

        for (int k = 0; moves[k] != null; k++) {
            if (moves[k].getGame().rateBoard(player) == Game.MAX_POINTS)
                tmp = n * Game.MAX_POINTS;
            else if (moves[k].getGame().rateBoard(player) == Game.MIN_POINTS)
                tmp = n * Game.MIN_POINTS;
            else tmp = doMinMax(n - 1, moves[k].getGame(), player, alpha, beta);
            if (newGame.playerSymbol == player)
                ret = (ret > tmp ? ret : tmp);
            else
                ret = (ret < tmp ? ret : tmp);
        }
        return ret;
    }

    public int doMinMax2(int n, Game game, FieldType player, int alpha, int beta) {
        int tmp = 0;
        MoveTrack[] moves = new MoveTrack[100];
        Game newGame = new Game(game.board, game.playerSymbol);

        if (n > 0)
            findAllMoves(newGame, moves);

        if (n == 0 || moves[0] == null) {
            return newGame.rateBoard(player);
        }
        if (newGame.playerSymbol == player) {
            for (int k = 0; moves[k] != null; k++) {
                if (moves[k].getGame().rateBoard(player) == Game.MAX_POINTS)
                    tmp = n * Game.MAX_POINTS;
                else if (moves[k].getGame().rateBoard(player) == Game.MIN_POINTS)
                    tmp = n * Game.MIN_POINTS;
                else tmp = doMinMax2(n - 1, moves[k].getGame(), player, alpha, beta);
                if (tmp > alpha)
                    alpha = tmp;
                if (alpha >= beta) {
                    break;
                }
            }
            return alpha;
        } else {
            for (int k = 0; moves[k] != null; k++) {
                if (moves[k].getGame().rateBoard(player) == Game.MAX_POINTS) {
                    beta = n * Game.MAX_POINTS;
                    break;
                } else if (moves[k].getGame().rateBoard(player) == Game.MIN_POINTS) {
                    tmp = n * Game.MIN_POINTS;
                } else tmp = doMinMax2(n - 1, moves[k].getGame(), player, alpha, beta);
                if (tmp < beta)
                    beta = tmp;
                if (alpha >= beta) {
                    break;
                }
            }
            return beta;
        }
    }


    public int chooseMove(int nMoves) {
        int index = 0, maxPoints = Integer.MIN_VALUE;
        int currentPoints = 0;
        findAllMoves(currentState, possibleMoves);
        int k = 0;
        while (possibleMoves[k] != null) {
            currentPoints = doMinMax2(nMoves, possibleMoves[k].getGame(), currentState.playerSymbol, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (currentPoints >= maxPoints) {
                maxPoints = currentPoints;
                index = k;
            }
            k++;
        }
        if (possibleMoves[index] != null) {
            currentState.setBoard(possibleMoves[index].getGame().board);
            possibleMoves[0] = possibleMoves[index];
        }
        return index;
    }
}
