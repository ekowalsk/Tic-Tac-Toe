package elka.pw.edu.pl.projects;

import elka.pw.edu.pl.projects.Algorythms.Game;
import elka.pw.edu.pl.projects.Algorythms.MinMax;
import elka.pw.edu.pl.projects.Enums.FieldType;

import javax.swing.*;

public class App {

    public static void main(String[] args) {
        /*actualBoard.setField(new Position(0, 0), FieldType.O);
        actualBoard.setField(new Position(1, 1), FieldType.X);
        actualBoard.setField(new Position(1, 0), FieldType.O);
        actualBoard.setField(new Position(2, 0), FieldType.O);
        actualBoard.setField(new Position(0, 2), FieldType.X);
        actualBoard.setField(new Position(0, 1), FieldType.O);
        actualBoard.setField(new Position(2, 1), FieldType.X);*/


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TGraphics(); // Let the constructor do the job
            }
        });
        FieldType [][] actualBoard = new FieldType[3][3];
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++) {
                actualBoard[x][y] = FieldType.E;
            }
        actualBoard[2][0] = FieldType.O;
        actualBoard[2][1] = FieldType.O;
        actualBoard[1][1] = FieldType.X;
        actualBoard[0][0] = FieldType.O;
        actualBoard[2][2] = FieldType.X;


        Game actualGame = new Game(actualBoard, FieldType.X);
        MinMax minMax = new MinMax(actualGame);
        //minMax.comp2comp();
        int index = minMax.chooseMove(2);
        MinMax.possibleMoves[0].printMove();

    }
}
