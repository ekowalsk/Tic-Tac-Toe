package elka.pw.edu.pl.projects;
import elka.pw.edu.pl.projects.Algorythms.Game;
import elka.pw.edu.pl.projects.Algorythms.MinMax;
import elka.pw.edu.pl.projects.Enums.FieldType;

public class App {

    public static void main(String[] args) {
        Board actualBoard = new Board();

        actualBoard.setField(1,1, FieldType.O);
        actualBoard.setField(0,0, FieldType.X);
        actualBoard.setField(0,1, FieldType.X);
        actualBoard.setField(1,0, FieldType.O);


        Game actualGame = new Game (actualBoard, FieldType.O);
        MinMax minMax = new MinMax (actualGame);
        int index = minMax.chooseMove( 3);
        System.out.println(index);
        minMax.possibleMoves[index].getGame().getBoard().print();




    }
}
