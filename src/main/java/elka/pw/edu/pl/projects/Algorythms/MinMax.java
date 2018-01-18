package elka.pw.edu.pl.projects.Algorythms;

import elka.pw.edu.pl.projects.Board;
import elka.pw.edu.pl.projects.Enums.FieldType;
import elka.pw.edu.pl.projects.Enums.HasWon;
import elka.pw.edu.pl.projects.MoveTrack;
import elka.pw.edu.pl.projects.Position;

public class MinMax {
    public Game currentState;
    public static MoveTrack[] possibleMoves;

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

    public int doMinMax(int n, Game game, FieldType player, int alpha, int beta) {
        int tmp;
        HasWon winner = new HasWon();
        FieldType opponent;
        MoveTrack[] moves = new MoveTrack[100];
        Game newGame = new Game(game.getBoard(), game.getPlayerSymbol());

        if (player == FieldType.O)
            opponent = FieldType.X;
        else
            opponent = FieldType.O;
        if (n == 0) {
            newGame.setPlayerSymbol(player);
            newGame.setOpponentSymbol(opponent);
        }

        if (n > 0)
            findAllMoves(newGame, moves);

        if (n == 0)
            return newGame.rateBoard(winner);

        if (moves[0] == null) {
            newGame.setPlayerSymbol(player);
            newGame.setOpponentSymbol(opponent);
            return newGame.rateBoard(winner);
        }

        if (newGame.getPlayerSymbol() == player) {
            for (int k = 0; moves[k] != null; k++) {
                tmp = doMinMax(n - 1, moves[k].getGame(), player, alpha, beta);
                if (tmp > alpha)
                    alpha = tmp;
                if (alpha >= beta) {
                    break;
                }
            }
            return alpha;

        } else {
            for (int k = 0; moves[k] != null; k++) {
                tmp = doMinMax(n - 1, moves[k].getGame(), player, alpha, beta);
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
        int currentPoints;
        findAllMoves(currentState, possibleMoves);
        int k = 0;
        while (possibleMoves[k] != null) {
            if ((currentPoints = doMinMax(nMoves, possibleMoves[k].getGame(), currentState.getPlayerSymbol(), Integer.MIN_VALUE, Integer.MAX_VALUE)) > maxPoints) {
                maxPoints = currentPoints;
                index = k;
            }
            k++;
        }
        if (possibleMoves[index] != null) {
            currentState.setBoard(possibleMoves[index].getGame().getBoard());
            possibleMoves[0] = possibleMoves[index];
        }
        return index;
    }

    public void comp2comp() {
        HasWon isWinner = new HasWon();
        currentState.rateBoard(isWinner);
        int index = chooseMove(6);
        while (possibleMoves[0] != null) {
            Position pos = possibleMoves[index].getMove();
            currentState.getBoard().setField(pos, currentState.getPlayerSymbol());
            if (currentState.getPlayerSymbol() == FieldType.O) {
                currentState.setPlayerSymbol(FieldType.X);
                currentState.setOpponentSymbol(FieldType.O);
            } else {
                currentState.setPlayerSymbol(FieldType.O);
                currentState.setOpponentSymbol(FieldType.X);
            }
            currentState.rateBoard(isWinner);
            currentState.getBoard().print();
            cleanMoves();
            index = chooseMove(6);
        }
    }

    private static void cleanMoves() {
        MoveTrack newTracks[] = new MoveTrack[100];
        possibleMoves = newTracks;
    }
}
