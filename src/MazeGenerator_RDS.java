import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MazeGenerator_RDS {

    private int rowSize;
    private int colSize;
    private String[][] maze;

    public MazeGenerator_RDS(int rows, int cols) {
        //all this code does is implement an algorithim I found on wikipedia that actually works
        rowSize = rows;
        colSize = cols;
        maze = new String[rowSize][colSize];
    }

    public void setAll(String str) {
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                maze[row][col] = str;
            }
        }
    }

    public void generate() { //generates a maze using the given parameters set in the constructor
        ArrayList<Integer> startPointSelector = new ArrayList<>();
        for (int i = 1; i < rowSize-1; i++) {
            startPointSelector.add(i);
        }

        int selector1 = (int) (Math.random() * (startPointSelector.size()));
        System.out.println("Start selector is " + startPointSelector.get(selector1));

        setAll("e");
        int row = startPointSelector.get(selector1);
        int col = 0;
        int[][] validNodes;
        int randomizer;
        boolean backTrack = false;
        boolean valid;

        //Scanner waiter = new Scanner(System.in);

        while (true) {
            valid = true;
            maze[row][col] = "_";
            validNodes = getValidNodes(row,col);
            randomizer = (int) (Math.random() * 4);

            if (validNodes[randomizer][0]>0 && validNodes[randomizer][1]>0) {
                //use the valid coordinate
                row = validNodes[randomizer][0];
                col = validNodes[randomizer][1];
                valid = true;
            } else {
                //check to see if something is >0
                backTrack = true;
                for (int i = 0; i < 4; i++) {
                    if(validNodes[i][0] < 0) {

                    } else {
                        //there is some valid coordiante
                        backTrack = false;
                        valid = true;
                    }
                }
            }

            if (backTrack) {
                //backtrack is necessary
                //printMaze();

                //waiter.nextLine(); debug only

                int[] backtrackCord = getBackTrack(row,col);
                if(backtrackCord[0] != -1) {
                    maze[row][col] = " ";
                    row = backtrackCord[0];
                    col = backtrackCord[1];

                } else {
                    valid = false;
                    //no backtrack possible
                }

            }

            if (valid) {
                //maze[row][col] = "x"; //for debug purposes only
            } else {
                //no backtrack possible
                break;
            }
        }

        printMaze();

        //add end point on last column so player can exit maze
        ArrayList<Integer> endPointSelector = new ArrayList<>();
        for (int i = 0; i < rowSize; i++) {
            endPointSelector.add(i);
        }
        while (endPointSelector.size() > 1) {
            int selector = (int)(Math.random()*(endPointSelector.size()));
            //System.out.println("Selector is " + selector);
            if (maze[endPointSelector.get(selector)][colSize-2].equals(" ")) {
                maze[endPointSelector.get(selector)][colSize-1] = " ";
                break;
            } else {
                endPointSelector.remove(selector);
            }

        }

    }

    public void printMaze() {
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                System.out.print(maze[row][col] + "\t");
            }
            System.out.println();
        }
    }

    public int[][] getValidNodes(int row, int col) {
        //returns the valid nodes the current generating path can go to
        int[][] returner = new int[4][2];

        for (int i = 0; i<returner.length; i++) {
            Arrays.fill(returner[i], -1);
        }
        //check[direction] really just calls cardinalCount with the proper changes
        //could just replace checkNorth with cardinalCount(row-1,col)
            if (checkNorth(row,col)) {
                returner[0][0] = row-1;
                returner[0][1] = col;
            }
            if (checkEast(row,col)) {
                returner[1][0] = row;
                returner[1][1] = col+1;
            }
            if (checkSouth(row,col)) {
                returner[2][0] = row+1;
                returner[2][1] = col;
            }
            if (checkWest(row,col)) {
                returner[3][0] = row;
                returner[3][1] = col-1;
            }

        return returner;
    }

    public int[] getBackTrack(int row, int col) {
        //since no valid nodes were avilable, go backwards from dead end
        //finds the last tile you moved from and returns it
        //markes the current tile as a dead end
        int[] returner = new int [2];
        returner[0] = -1;

        if (row > 0 && col > 0 && row < rowSize && col < colSize) {
            if (maze[row-1][col].equals("_")) {
                returner[0] = row-1;
                returner[1] = col;
            } else if (maze[row][col+1].equals("_")) {
                returner[0] = row;
                returner[1] = col+1;
            } else if (maze[row+1][col].equals("_")) {
                returner[0] = row+1;
                returner[1] = col;
            } else if (maze[row][col-1].equals("_")) {
                returner[0] = row;
                returner[1] = col-1;
            }
        }


        return returner;
    }

    public boolean checkNorth(int row, int col) {
        return cardinalCount(row-1,col);
    }

    public boolean checkEast(int row, int col) {
        return cardinalCount(row,col+1);
    }

    public boolean checkSouth(int row, int col) {
        return cardinalCount(row+1,col);
    }

    public boolean checkWest(int row, int col) {
        return cardinalCount(row,col-1);
    }

    public boolean cardinalCount(int row, int col) {
        int eCount = 0;
        int _Count = 0;

        //goes in all 4 directions counting e's and _'s
        //if it is 3 e's and 1 _ then we know it is valid and return true

        if (row+1<rowSize && row-1>=0 && col+1<colSize && col-1>=0) {
            //in bounds
            if (maze[row+1][col].equals("e")) eCount++;
            if (maze[row-1][col].equals("e")) eCount++;
            if (maze[row][col+1].equals("e")) eCount++;
            if (maze[row][col-1].equals("e")) eCount++;

            if (maze[row+1][col].equals("_")) _Count++;
            if (maze[row-1][col].equals("_")) _Count++;
            if (maze[row][col+1].equals("_")) _Count++;
            if (maze[row][col-1].equals("_")) _Count++;
        } //WILL NOT CHECK BOUNDARIES, will just return false

        if (eCount == 3 && _Count == 1 && maze[row][col].equals("e")) {
            return true;
        } else {
            return false;
        }
    }


    public String[][] getMaze() {
        return maze;
    }
}
