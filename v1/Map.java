public class Map{

    /*

    Map Layout (Subject to change):

               maze4
                 ^
                 |
                 |
                 V
    maze1 <--> maze2 <---> maze3
                 ^
                 |
                 |
                 V
               maze5

    */
    String hero = "X";
    int heroX = 1;
    int heroY = 0;


    String[][] arr1 = {{"#","#", "#"},
                       {hero, " ", "#"},
                       {" ", "#", "#"}
                      };
    
    String[][] arr2 = {{"#","#", "#"},
                      {" ", "#", " "},
                      {" ", "#", " "}
                     }; 
    
    String[][] arr3 = {{"#","#", "#"},
                      {"#", " ", " "},
                      {"#", "#", " "}
                    };
    
    String[][] arr4 = {{" ","#", "#"},
                       {" ", "#", " "},
                       {" ", "#", " "}
                     };

    String[][] arr5 = {{" ","#", "#"},
                        {" ", "#", " "},
                        {" ", "#", " "}
                      };

    Maze maze1 = new Maze(arr1);
    Maze maze2 = new Maze(arr2);
    Maze maze3 = new Maze(arr3);
    Maze maze4 = new Maze(arr4);
    Maze maze5 = new Maze(arr5);

    // will determine which frame (aka Maze) will be shown on screen
    private Maze currentFrame =  maze1; 

    public Map(){
        // Link the Maps

        maze1.setEast(maze2);

        maze2.setWest(maze1);
        maze2.setNorth(maze4);
        maze2.setEast(maze3);
        maze2.setSouth(maze5);

        maze3.setWest(maze2);

        maze4.setSouth(maze2);

        maze5.setNorth(maze2);
    }

    /*
    Methods to change Frames
    */
    public void changeNorth(){
        if(currentFrame.getNorth() != null){
            currentFrame = currentFrame.getNorth();
        }
    }
    public void changeEast(){
        if(currentFrame.getEast() != null){
            currentFrame = currentFrame.getEast();
        }
    }
    public void changeSouth(){
        if(currentFrame.getSouth() != null){
            currentFrame = currentFrame.getSouth();
        }
    }
    public void changeWest(){
        if(currentFrame.getWest() != null){
            currentFrame = currentFrame.getWest();
        }
    }

    public void setCurrentFrame(Maze currentMaze){
        this.currentFrame = currentMaze;
    }

    public String toString(){
        System.out.println("\033[H");
        System.out.println("\033[2J");
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
            currentFrame.setPos(heroX, heroY," ");
            heroY += 1;
            currentFrame.setPos(heroX, heroY, hero);
        }
    }
    public void moveDown(){
        if(currentFrame.getPos(heroX+1, heroY).equals(" ")){
            currentFrame.setPos(heroX, heroY," ");
            heroX += 1;
            currentFrame.setPos(heroX, heroY, hero);
        }
    }
    public void moveLeft(){
        if(currentFrame.getPos(heroX, heroY-1).equals(" ")){
            currentFrame.setPos(heroX, heroY," ");
            heroY -= 1;
            currentFrame.setPos(heroX, heroY, hero);
        }
    }

    public static void main(String[] args) {
        Map test = new Map();
        System.out.println(test);
        System.out.println("______________");
        
        test.changeEast();
        System.out.println(test);
        System.out.println("______________");

        test.changeNorth();
        System.out.println(test);
        System.out.println("______________");

        test.changeSouth();
        System.out.println(test);
        System.out.println("______________");

        test.changeEast();
        System.out.println(test);
        System.out.println("______________");

        test.changeWest();
        System.out.println(test);
        System.out.println("______________");

        test.changeSouth();
        System.out.println(test);
        System.out.println("______________");

        test.changeNorth();
        System.out.println(test);
        System.out.println("______________");
    }
    
}
