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

    String[][] arr1 = {{"#","#", "#"},
                       {" ", " ", "#"},
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
        return currentFrame.toString();
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
