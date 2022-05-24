public class Maze{
    /*
    Contains Maze
    Contains links to adjacent Mazes
    */
    
    String[][] _maze;

    private Maze northMaze;
    private Maze eastMaze;
    private Maze southMaze;
    private Maze westMaze;



    public Maze(String[][] arr /*, Maze north, Maze east, Maze south, Maze west*/){
        this._maze = arr;
        // this.northMaze = north;
        // this.eastMaze = east;
        // this.southMaze = south;
        // this.westMaze = west;
    }

    public void setNorth(Maze north){
        this.northMaze = north;
    }

    public void setEast(Maze east){
        this.eastMaze = east;
    }

    public void setSouth(Maze south){
        this.southMaze = south;
    }

    public void setWest(Maze west){
        this.westMaze = west;
    }

    public Maze getNorth(){
        return northMaze;
    }
    public Maze getEast(){
        return eastMaze;
    }
    public Maze getSouth(){
        return southMaze;
    }
    public Maze getWest(){
        return westMaze;
    }

    public String toString(){
        // Stringifies the maze
        String retVal = "";
        for(int i = 0; i < _maze.length; i++){
            for(int e = 0; e < _maze[0].length; e++){
                retVal += _maze[i][e];
            }
            retVal += "\n";
        }
        return retVal;
    }

}