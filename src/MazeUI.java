import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import javax.swing.*;

public class MazeUI extends JFrame {

    private String[][] array;
    private Dictionary fileDirectory;
    private JLabel[][] jLabels;
    private int rowSize;
    private int colSize;


    private String[][] displayArray; //send this to the UI to put on screen
    private int displayRowTileSize;
    private int displayColTileSize;

    private int maxDisplay; //the maximum n x n matrix size of tiles that the user can see at once
    //private int width;
    //private int height;
    private JFrame displayFrame;
    private JPanel keyListener;

    //player movements
    private int playerRow;
    private int playerCol;

    private boolean active = false;
    private boolean optimization = false;
    private boolean scrollingView = false;



    public MazeUI(String[][] maze, boolean scroll, boolean optimize) {
//        KeyListener listener = new MyKeyListener();
//        addKeyListener(listener);
//        setFocusable(true);

        array = maze;
        this.fileDirectory = new Hashtable();
        String fileFolder = "C:\\Users\\patelhar\\IdeaProjects\\Mazegen\\ResourceFiles\\";
        fileDirectory.put("e", fileFolder + "32bitBlackTile.png");
        fileDirectory.put(" ", fileFolder + "32bitWhiteTile.png");
        fileDirectory.put("o-E", fileFolder + "32bitCharacter-East.png");
        fileDirectory.put("o-N", fileFolder + "32bitCharacter-North.png");
        fileDirectory.put("o-S", fileFolder + "32bitCharacter-South.png");
        fileDirectory.put("o-W", fileFolder + "32bitCharacter-West.png");
        fileDirectory.put("w", fileFolder + "win.png");

        //ImageIcon tempCheck = new javax.swing.ImageIcon((String) fileDirectory.get("e"));
        //System.out.println(fileDirectory.get("e"));

        //analyze maze to get dimensions
        rowSize = maze.length;
        colSize = maze[0].length;

        if (scroll) {
            scrollingView = true;
            displayRowTileSize = 5;
            displayColTileSize = 5;
            //really the max it can handle before it becomes too laggy

            optimization = false; //can't optimize in scrolling view --> well I can but its too much work
            //could use the same method, only update tiles that actually change, minor performance
            //increases

        } else {
            scrollingView = false;
            optimization = optimize;
            displayRowTileSize = rowSize/2;
            displayColTileSize = colSize/2;

            if (rowSize > 30 || colSize > 60) {
                System.out.println("The screen may be too small to see the whole maze. Consider using scrolling view.");
            }
        }


    }


    public void updateComponents(int[] vectorChange, String directionCode) throws InterruptedException {
        if (!active) {
            System.out.println("Moves are inactive");
            return;
        }

        //for optimization --> only the cords that change are updated
        ArrayList<Integer> changedCords = new ArrayList<>(); //evens are row, and odds are col --> 0,1 [row1,col1]

        //System.out.println("\t\t\t\tRunning command: "+directionCode);
        //vector change is where vectorChange[0] is row, [1] is col
        //System.out.println("BEFORE\tPlayer row is "+playerRow+"\tPlayer col is "+playerCol);

        changedCords.add(playerRow);
        changedCords.add(playerCol);

        playerRow += vectorChange[0];
        playerCol += vectorChange[1];

        //System.out.println("Player row is "+playerRow+"\tPlayer col is "+playerCol);

        if (playerCol == array[0].length) {
            active = false;
            winSequence();
            return;
        }


        if (playerRow >= 0 && playerCol >= 0 && playerRow < array.length && playerCol < array[0].length) {
            if (array[playerRow][playerCol].equals(" ")) {
                //move player
                array[playerRow-vectorChange[0]][playerCol-vectorChange[1]] = " ";
                array[playerRow][playerCol] = directionCode;

                //System.out.println("Moved player");
            } else {
                playerRow -= vectorChange[0];
                playerCol -= vectorChange[1];
                array[playerRow][playerCol] = directionCode;
                //System.out.println("reverted");
            }
        } else {
           // System.out.println("Spot is out of bonds");
            if (playerCol < 0) {
                playerCol = 0;
            }
            if (playerRow < 0) {
                playerRow = 0;
            }

            array[playerRow][playerCol] = directionCode;
        }

        changedCords.add(playerRow);
        changedCords.add(playerCol);


        setDisplayArray();


        if (optimization) {
            for (int i = 0; i < changedCords.size(); i += 2) {
                jLabels[changedCords.get(i)][changedCords.get(i+1)].setIcon(new javax.swing.ImageIcon((String) fileDirectory.get(displayArray[changedCords.get(i)][changedCords.get(i+1)])));
            }



        } else {
            for (int i = 0; i < displayArray.length; i++) {
                //System.out.println();
                for (int j = 0; j < displayArray[i].length; j++) {
                    //System.out.print(displayArray[i][j] + "\t");

                    jLabels[i][j].setIcon(new javax.swing.ImageIcon((String) fileDirectory.get(displayArray[i][j])));

                }

            }
        }




    }

    private void winSequence() throws InterruptedException {
        System.out.println("win sequence");

        for (int i = 0; i < displayArray.length; i++) {
            for (int j = 0; j < displayArray[i].length; j++) {
                jLabels[i][j].setIcon(new javax.swing.ImageIcon((String) fileDirectory.get("w")));

            }

        }
    }

    public void setDisplayArray() {
        int rMin = playerRow - displayRowTileSize;
        int rMax = playerRow + displayRowTileSize;
        int cMin = playerCol - displayColTileSize;
        int cMax = playerCol + displayColTileSize;

        //System.out.println("started --> rmax: "+rMax+"\trmin: "+rMin+"\t\tcmax: "+cMax+"\tcMin: "+cMin);

        while (rMin < 0) {
            rMin += 1;
            rMax += 1;
        }
        while (rMax > array.length) {
            rMin -= 1;
            rMax -= 1;
        }
        while (cMin < 0) {
            cMin += 1;
            cMax += 1;
        }
        while (cMax > array[0].length) {
            cMin -= 1;
            cMax -= 1;
        }

        //System.out.println("Finished --> rmax: "+rMax+"\trmin: "+rMin+"\t\tcmax: "+cMax+"\tcMin: "+cMin);

        displayArray = new String[rMax-rMin][cMax-cMin];

        //System.out.println("\n\nMaking display array of row size: "+(rMax-rMin+1)+"\tand col size: "+(cMax-cMin+1));

        for (int i = rMin; i < rMax; i++) {
            for (int j = cMin; j < cMax; j++) {
                //System.out.println("i is " + i + "\t j is " + j + "\t\ti-rMin is " + (i-rMin) + "\tj-cMin is " + (j-cMin));
                displayArray[i-rMin][j-cMin] = array[i][j];

            }
        }
    }

    public void setDisplayArray(int rMin, int rMax, int cMin, int cMax) {
        displayArray = new String[rMax-rMin][cMax-cMin];

        //System.out.println("\n\nMaking display array of row size: "+(rMax-rMin+1)+"\tand col size: "+(cMax-cMin+1));

        for (int i = rMin; i < rMax; i++) {
            for (int j = cMin; j < cMax; j++) {
                //System.out.println("i is " + i + "\t j is " + j + "\t\ti-rMin is " + (i-rMin) + "\tj-cMin is " + (j-cMin));
                displayArray[i-rMin][j-cMin] = array[i][j];

            }
        }
    }



    public void initComponents(JPanel listener) {
        keyListener = listener;
        //find player, and set his position
        for (int i = 0; i < array.length; i++) {
            if (array[i][0].equals("_")) {
                playerRow = i;
                playerCol = 0;

                array[i][0] = "o-N";
            }
        }

        displayFrame = new JFrame();

        populateJLabels();

        //loop to set size and icon of all tiles
        //System.out.println("Display Row size is "+displayRowTileSize+"\t\tjLabels.length is "+jLabels.length);
        //System.out.println("Display Col size is "+displayColTileSize+"\t\tjLabels[0].length is "+jLabels[0].length);

        for (int i = 0; i < jLabels.length ; i++) {
            //System.out.println("I iterator is "+i);
            for (int j = 0; j < jLabels[i].length; j++) {
                //System.out.println("J iterator is "+j);
                jLabels[i][j].setIcon(new javax.swing.ImageIcon((String) fileDirectory.get(array[i][j])));

                jLabels[i][j].setBounds(1+j*32,1+i*32,32,32);

                displayFrame.add(jLabels[i][j]);
            }
        }

        displayFrame.setSize((displayColTileSize * 2 * 32 + 18),(displayRowTileSize* 2 * 32 + 43));
        displayFrame.setLayout(null);
        displayFrame.setVisible(true);

        displayFrame.add(keyListener);

        active = true;

    }// </editor-fold>





    private void populateJLabels() {

            jLabels = new JLabel[displayRowTileSize*2][displayColTileSize*2];

            for (int i = 0; i < displayColTileSize*2 ; i++) {
                for (int j = 0; j < displayRowTileSize*2; j++) {
                    jLabels[j][i] = new javax.swing.JLabel();
                }
            }

    }



    public class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
        }

        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
        }
    }


}
