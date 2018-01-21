package elka.pw.edu.pl.projects;

import javax.swing.*;

public class App {

    public static void main(String[] args) {
        final int mode = Integer.parseInt(args[0]);
        final int depthX = Integer.parseInt(args[1]);
        final int depthO = Integer.parseInt(args[2]);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TGraphics(depthX, depthO, mode); // Let the constructor do the job
            }
        });

    }
}
