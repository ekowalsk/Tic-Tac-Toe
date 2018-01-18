package elka.pw.edu.pl.projects;

import elka.pw.edu.pl.projects.Algorythms.Game;
import elka.pw.edu.pl.projects.Algorythms.MinMax;
import elka.pw.edu.pl.projects.Enums.FieldType;

public class App {

    public static void main(String[] args) {
        Board actualBoard = new Board();
        actualBoard.setField(new Position(1,1), FieldType.X);
        actualBoard.setField(new Position(0,0), FieldType.O);
        actualBoard.setField(new Position(1,0), FieldType.X);
        actualBoard.setField(new Position(1,2), FieldType.O);
        actualBoard.setField(new Position(2,0), FieldType.X);
        actualBoard.setField(new Position(0,2), FieldType.O);
        actualBoard.setField(new Position(0,1), FieldType.X);
        actualBoard.setField(new Position(2,1), FieldType.O);


        Game actualGame = new Game(actualBoard,FieldType.X);
        MinMax minMax = new MinMax(actualGame);
        //minMax.findAllMoves(minMax.currentState, minMax.possibleMoves);
        int index = minMax.chooseMove(4);
        minMax.currentState.getBoard().print();


    }
}
