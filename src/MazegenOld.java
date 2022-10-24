public class MazegenOld {
    private int xDim;
    private int yDim;
    private int[][] array;

    public MazegenOld(int xSize, int ySize) {
        //fixed gen, squares or rectangles only
        //automatically chooses start point on left column and end point on right column
        xDim = xSize;
        yDim = ySize;
        array = new int[ySize][xSize]; //check lucid chart

        //has various issues and will require a lot of if statements to make this algorythim actually work
    }

    public void generate() {
        //legend: 0 = open door, 1 = wall, -1 = intersection (temp)

        setAll(1); //everything is walls
        setTrace(); //makes the course to the finish
        //sets up intersections
        //intersections lead to dead ends
        //make more intersections in added lanes
        //replace all -1's with 0's

    }

    public void setAll(int num) {
        for (int i = 0; i < yDim; i++) {
            for (int j = 0; j < xDim; j++) {
                array[i][j] = num;
            }
        }
    }

    public void setTrace() {
        int yStart = (int)(Math.random() * (0 + yDim)); //gets value from 0 to xDim of column
        int yEnd = (int)(Math.random() * (0 + yDim)); //gets value from 0 to xDim of column
        int direction = 2; // 1 = north, 2 = east, 3 = south, 4 = west
        int iterator = 2;

        int y = yStart, x = 0;

        System.out.println("yStart is: " + yStart + "\t and yEnd is: " + yEnd );

        //generate main trace with points for intersection
        //legend
        while(!(y == yEnd && x == xDim)) {
            //System.out.println("\t\t recieved direction is " + direction);
            switch (direction) {
                case 1: { //north
                    y+=1;
                    if (!(x >= xDim || y >= yDim || x < 0 || y < 0|| array[y][x] != 1)) {
                        //System.out.println("moving north, the y value is " + y);
                    } else { //direction is invalid
                        System.out.println("direction invalid for " + direction);
                        //System.out.println("\t\tx is " + x + "\ty is " + y + "\t\tarray is " + array[y][x]);
                        y-=1;
                        System.out.println("\t\tx is " + x + "\ty is " + y + "\t\tarray is " + array[y][x]);
                        validDirectionToGo(x,y,xDim,yEnd, 1.9);
                    }

                } default: {//east
                    x+=1;
                    if (!(x >= xDim || y >= yDim || x < 0 || y < 0|| array[y][x] != 1)) {

                    } else {
                        System.out.println("direction invalid for " + direction);
                        //System.out.println("\t\tx is " + x + "\ty is " + y + "\t\tarray is " + array[y][x]);
                        x-=1;
                        System.out.println("\t\tx is " + x + "\ty is " + y + "\t\tarray is " + array[y][x]);
                    }

                } case 3: {//south
                    y-=1;
                    if (!(x >= xDim || y >= yDim || x < 0 || y < 0|| array[y][x] != 1)) {

                    } else {
                        System.out.println("direction invalid for " + direction);
                        //System.out.println("\t\tx is " + x + "\ty is " + y + "\t\tarray is " + array[y][x]);
                        y+=1;
                        System.out.println("\t\tx is " + x + "\ty is " + y + "\t\tarray is " + array[y][x]);
                    }

                } case 4: {//west
                    x-=1;
                    if (!(x >= xDim || y >= yDim || x < 0 || y < 0 || array[y][x] != 1)) {

                    } else {
                        System.out.println("direction invalid for " + direction);

                        x+=1;
                        System.out.println("\t\tx is " + x + "\ty is " + y + "\t\tarray is " + array[y][x]);
                    }

                }
            }


            if (iterator < 1 && distanceToPoint(x,y,xDim,yEnd) > 0.5) {//time for direction change
                //need to weight it so that it eventually funnels to the end
                int temp = direction;
                while (temp == direction) {
                    direction = (int)(Math.random() * 4) + 1;
                }

                iterator = (int) (Math.pow(distanceToPoint(x,y,xDim,yEnd), 0.1) * Math.random() * 4) + 0;
                //System.out.println("iterator is " + iterator + "\t and direction is now "+ direction);

            } else if (distanceToPoint(x,y,xDim,yEnd) < 0.5) {//we are close to the finish, lets wrap it up
                System.out.println("wrapping it up");
                //manually go to the end
                break;
            }


            iterator -= 1;
            System.out.println("the new x and y are: " + x + "," + y);
            array[y][x] = 0;
        }

        //manually go to the end
        while(y != yEnd) {
            if (y > yEnd) {
                y -= 1;
            } else {
                y+= 1;
            }
            array[y][x] = 0;
        }

        while(x < xDim) {
            array[y][x] = 0;
            x+= 1;
        }
    }

    public double distanceToPoint(int x1, int y1, int x2, int y2) {
        return ((double) x2 - x1)/(y2 - y1);
    }

    public double[] validDirectionToGo(int x1, int y1, int x2, int y2, double noiseChance) { //returns an array of weights
        double defaultChance = noiseChance; //chance it will go the wrong direction

        double[] weights = new double[4];
        double xLen = x2 - x1;
        double yLen = y2 - y1;
        //make triangle
        double angle = Math.atan2(yLen, xLen);

        //use angle to get weights, for example angle of 45 deg gives even split for 2 choices --> make hypotenuse = 1
        weights[0] = Math.sin(angle); //north
        weights[1] = Math.cos(angle); //east
        weights[2] = -Math.sin(angle); //south
        weights[3] = -Math.cos(angle); //west

        for (int i = 0; i < 4; i++) {
            if(weights[i] < 0) {
                weights[i] = defaultChance;
            }
        }

        return weights;
    }

    public void printMazeArray() {
        for (int y = 0; y < yDim; y++) {
            for (int x = 0; x < xDim; x++) {
                System.out.print(" " + array[y][x]);
            }
            System.out.println();
        }
    }

}
