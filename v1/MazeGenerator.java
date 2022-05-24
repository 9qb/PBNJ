// import java.util.ArrayList;

import java.util.ArrayList;

public class MazeGenerator {

 /*
 Advised by students from Mr K's class while writing this class
 */

  String[][] _maze;

  public MazeGenerator(int x, int y){
      _maze = new String[x][y];
      for(int i = 0; i <_maze.length; i++){
        for(int e = 0; e <_maze[0].length; e++){
          _maze[i][e] = "#";
        }
    }
  }

  public void generate(int startrow, int startcol){
    carve(startrow, startcol);
    
    _maze[startrow][startcol] = "#";

    int eRow = Math.abs(_maze.length - 1 - startrow);
    int eCol = Math.abs(_maze[startrow].length - 1 - startcol);
    _maze[eRow][eCol] = "#";
  }


  private void carve(int row, int col){
      //can carve: not on border, not a space, fewer than 2 neighboring spaces
    if(canCarve(row,col)){
      //change to a space
      _maze[row][col] = " ";
      //make an arrayList of directions i made the directions: [row offset , col offset]
      ArrayList<int[]>directions = new ArrayList<int[]>();
      //fill up the arrayList Here
      directions.add(new int[]{0, -1});
      directions.add(new int[]{0, 1});
      directions.add(new int[]{-1, 0});
      directions.add(new int[]{1, 0});

      while(directions.size() > 0){
        //choose a direction randomly:
        int randDirection = (int)(Math.random() * (directions.size()));
        int[] direction = directions.remove(randDirection);
        carve(row + direction[0], col + direction[1]);
      }
    }

  }

  private boolean canCarve(int row, int col) {
    if (row <= 0 || col <= 0 || row >= _maze.length - 1 || col >= _maze[row].length - 1) {
      return false;
    }

    int neighbors = 0;
    if (_maze[row+1][col].equals(" ")){
      neighbors++;
    }
    if (_maze[row-1][col].equals(" ")){
      neighbors++;
    }
    if (_maze[row][col-1].equals(" ")){
      neighbors++;
    }
    if (_maze[row][col+1].equals(" ")){
      neighbors++;
    }

    if (neighbors >= 2) {
      return false;
    }

    if (_maze[row][col].equals(" ")) {
      return false;
    }

    return true;
  }

  public String[][] getGeneratedMaze(){
      generate(0,0);
      carve(1,1);
      return _maze;
  }

  /*
  // For testing purposes


  public String toString(){
      String retVal = "";
      for(int i = 0; i<_maze.length; i++){
          for(int e=0; e<_maze[0].length; e++){
            retVal += _maze[i][e];
          }
          retVal += "\n";
      }
      return retVal;
  }
  public static void main(String[] args) {
      MazeGenerator test = new MazeGenerator(17,30);
      System.out.println(test);
      System.out.println("");
      test.carve(1,1);
      System.out.println(test);
  }
  */

}
