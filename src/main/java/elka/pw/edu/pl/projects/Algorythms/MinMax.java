package elka.pw.edu.pl.projects.Algorythms;

import elka.pw.edu.pl.projects.Enums.FieldType;
import elka.pw.edu.pl.projects.Enums.HasWon;
import elka.pw.edu.pl.projects.MoveTrack;
import elka.pw.edu.pl.projects.Position;

public class MinMax {
    public Game currentState;
    public static MoveTrack[] possibleMoves;

    public MinMax(Game state) {
        currentState = new Game(state.board, state.playerSymbol);
        possibleMoves = new MoveTrack[600];
    }

    public void findAllMoves(Game game, MoveTrack[] moves) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (game.board[x][y] == FieldType.E) {
                    Game newGame = new Game(game.board, game.opponentSymbol);
                    newGame.board[x][y] = game.playerSymbol;
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
        Game newGame = new Game(game.board, game.playerSymbol);

        if (player == FieldType.O)
            opponent = FieldType.X;
        else
            opponent = FieldType.O;
        if (n == 0) {
            newGame.playerSymbol = player;
            newGame.opponentSymbol = opponent;
        }

        if (n > 0)
            findAllMoves(newGame, moves);

        if (n == 0)
            return newGame.rateBoard(winner);

        if (moves[0] == null) {
            newGame.playerSymbol = player;
            newGame.opponentSymbol = opponent;
            return newGame.rateBoard(winner);
        }

        if (newGame.playerSymbol == player) {
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
        int currentPoints = 0;
        findAllMoves(currentState, possibleMoves);
        int k = 0;
        while (possibleMoves[k] != null) {
            currentPoints = doMinMax(nMoves, possibleMoves[k].getGame(), currentState.playerSymbol, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (currentPoints > maxPoints) {
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

    public void comp2comp() {
        HasWon isWinner = new HasWon();
        currentState.rateBoard(isWinner);
        int index = chooseMove(6);
        while (possibleMoves[0] != null) {
            Position pos = possibleMoves[index].getMove();
            currentState.board[pos.getX()][pos.getY()] = currentState.playerSymbol;
            //currentState.getBoard().setField(pos, currentState.getPlayerSymbol());
            if (currentState.playerSymbol == FieldType.O) {
                currentState.playerSymbol = FieldType.X;
                currentState.opponentSymbol = FieldType.O;
            } else {
                currentState.playerSymbol = FieldType.O;
                currentState.opponentSymbol = FieldType.X;
            }
            currentState.rateBoard(isWinner);
            currentState.printBoard();
            cleanMoves();
            index = chooseMove(6);
        }
    }

    public static void cleanMoves() {
        MoveTrack newTracks[] = new MoveTrack[100];
        possibleMoves = newTracks;
    }
}
