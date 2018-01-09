package elka.pw.edu.pl.projects;
import elka.pw.edu.pl.projects.Algorythms.Game;
import elka.pw.edu.pl.projects.Enums.FieldType;

public class App {

    public static void main(String[] args) {
        Board newBoard = new Board();
        newBoard.setField(1,1, FieldType.CROSS);
        newBoard.setField(0,0,FieldType.CIRCLE);
        Game pick = new Game(newBoard, FieldType.CIRCLE);
        pick.findAllMoves();
        pick.printMoves();

    }
}
