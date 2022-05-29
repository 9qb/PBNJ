public class Map{

    private String hero = "X";
    private int heroX = 1;
    private int heroY = 1;

    private static void clear()
    {
      System.out.println("\033[2J");
      System.out.println("\033[" + 1 + ";" + 1 + "H");
    }

    private Maze maze;

    // will determine which frame (aka Maze) will be shown on screen
    private Maze currentFrame;

    public Map(){
        MazeGenerator troll = new MazeGenerator(27, 48);
        maze = new Maze(troll.getGeneratedMaze());
        currentFrame = maze;
        currentFrame.setPos(heroX, heroY, hero);
    }

    /*
    Methods to change Frames
    */
    // public void changeNorth(){
    //     if(currentFrame.getNorth() != null){
    //         currentFrame = currentFrame.getNorth();
    //     }
    // }
    // public void changeEast(){
    //     if(currentFrame.getEast() != null){
    //         currentFrame = currentFrame.getEast();
    //     }
    // }
    // public void changeSouth(){
    //     if(currentFrame.getSouth() != null){
    //         currentFrame = currentFrame.getSouth();
    //     }
    // }
    // public void changeWest(){
    //     if(currentFrame.getWest() != null){
    //         currentFrame = currentFrame.getWest();
    //     }
    // }
    //
    // public void setCurrentFrame(Maze currentMaze){
    //     this.currentFrame = currentMaze;
    // }

    public String toString(){
        clear();
        return currentFrame.toString();
    }

    public void moveUp(){
        if(currentFrame.getPos(heroX-1, heroY).equals(" ")){
            currentFrame.setPos(heroX, heroY," ");
            heroX -= 1;
            currentFrame.setPos(heroX, heroY, hero);
        }
    }
    public void moveRight(){
        if(currentFrame.getPos(heroX, heroY+1).equals(" ")){
            currentFrame.setPos(heroX, heroY,".");
            heroY += 1;
            currentFrame.setPos(heroX, heroY, hero);
        }
    }
    public void moveDown(){
        if(currentFrame.getPos(heroX+1, heroY).equals(" ")){
            currentFrame.setPos(heroX, heroY,".");
            heroX += 1;
            currentFrame.setPos(heroX, heroY, hero);
        }
    }
    public void moveLeft(){
        if(currentFrame.getPos(heroX, heroY-1).equals(" ")){
            currentFrame.setPos(heroX, heroY,".");
            heroY -= 1;
            currentFrame.setPos(heroX, heroY, hero);
        }
    }
    
    public String[][] getMaze(){
      return maze.getMaze();
    }

    public static void main(String[] args) {
        Map test = new Map();
        System.out.println(test);
        System.out.println("______________");

        // test.changeEast();
        // System.out.println(test);
        // System.out.println("______________");
        //
        // test.changeNorth();
        // System.out.println(test);
        // System.out.println("______________");
        //
        // test.changeSouth();
        // System.out.println(test);
        // System.out.println("______________");
        //
        // test.changeEast();
        // System.out.println(test);
        // System.out.println("______________");
        //
        // test.changeWest();
        // System.out.println(test);
        // System.out.println("______________");
        //
        // test.changeSouth();
        // System.out.println(test);
        // System.out.println("______________");
        //
        // test.changeNorth();
        // System.out.println(test);
        // System.out.println("______________");
    }

}
