package elka.pw.edu.pl.projects;

import javax.swing.*;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        final int mode;
        final int depthX;
        final int depthO;
        System.out.println("choose mode. 1 - you vs computer, 2 - computer vs computer\n");
        Scanner scanner = new Scanner(System.in);
        String tmode = scanner.next();
        mode = Integer.parseInt(tmode);
        if (mode == 1) {
            System.out.println("choose depth for AI player\n");
            String tdO = scanner.next();
            depthO = Integer.parseInt(tdO);

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new TGraphics(0, depthO, mode); // Let the constructor do the job
                }
            });
        } else {
            System.out.println("choose depth for X player\n");
            String tdX = scanner.next();
            depthX = Integer.parseInt(tdX);
            System.out.println("choose depth for O player\n");
            String tdO = scanner.next();
            depthO = Integer.parseInt(tdO);

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new TGraphics(depthX, depthO, mode); // Let the constructor do the job
                }
            });

        }
    }
}
