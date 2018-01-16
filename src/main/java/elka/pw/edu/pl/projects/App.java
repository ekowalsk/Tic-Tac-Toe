package elka.pw.edu.pl.projects;

import elka.pw.edu.pl.projects.Algorythms.Game;
import elka.pw.edu.pl.projects.Algorythms.MinMax;
import elka.pw.edu.pl.projects.Enums.FieldType;

public class App {

    public static void main(String[] args) {
        Board actualBoard = new Board();
        actualBoard.setField(new Position(1,1), FieldType.O);
        actualBoard.setField(new Position(0,0), FieldType.X);

        Game actualGame = new Game(actualBoard,FieldType.O);
        MinMax minMax = new MinMax(actualGame);
        int index = minMax.chooseMove(5);
        minMax.currentState.getBoard().print();


    }
}
