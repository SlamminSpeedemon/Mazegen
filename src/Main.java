import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.util.Scanner;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class Main {
    public void bruh() throws InterruptedException {
        //use lucid chart to map out
//        MazegenOld generator1 = new MazegenOld(50,50);
//        generator1.generate();
//        generator1.printMazeArray();

//        MazeGenerator_RDS_noWork generator = new MazeGenerator_RDS_noWork(10,10);
//        generator.generate();
//        generator.printMaze();

        //generator.test();











        //Scanner moveInput = new Scanner(System.in);
        //String moveCommand = "oof";




        //int counter = 0;

//        while (!moveCommand.equals("")) {
//            System.out.println("\t\tRunning loop, counter is "+counter);
//            Thread.sleep(500);
//            switch (moveCommand) {
//                case "w":
//                    System.out.println("\t\t\t\tCalling North\t\tmove command is"+moveCommand);
//                    mazeUI.updateComponents(new int[] {-1,0}, "o-N");
//                    break;
//                case "a":
//                    System.out.println("\t\t\t\tCalling a\\t\\tmove command is"+moveCommand);
//                    mazeUI.updateComponents(new int[] {0,-1}, "o-W");
//                    break;
//                case "s":
//                    System.out.println("\t\t\t\tCalling s\\t\\tmove command is"+moveCommand);
//                    mazeUI.updateComponents(new int[] {1,0}, "o-S");
//                    break;
//                case "d":
//                    System.out.println("\t\t\t\tCalling d\\t\\tmove command is"+moveCommand);
//                    mazeUI.updateComponents(new int[] {0,1}, "o-E");
//                    break;
//                default:
//                    System.out.println("Invalid input");
//            }
//
//
//            moveCommand = moveInput.nextLine();
//            Thread.sleep(500);
//
//            counter+=1;
//        }
    }



    public static void main(String[]args) {
        InfoGetUI infoUI = new InfoGetUI();
    }
}
