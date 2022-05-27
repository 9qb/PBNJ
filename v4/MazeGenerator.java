import java.util.ArrayList;
import java.util.Stack;

public class MazeGenerator {

  private final String SPACE = " ";
  private final String WALL = "#";

 /*
 Advised by students from Mr K's class while writing this class
 */

  String[][] _maze;
  int _rows, _cols;

  public MazeGenerator(int x, int y){
    _maze = new String[x][y];
    for(int i = 0; i <_maze.length; i++){
      for(int e = 0; e <_maze[0].length; e++){
        _maze[i][e] = WALL;
      }
    }
    _rows = x;
    _cols = y;

    buildRooms(randNum(8, 10));
    buildBorders();
    carveMaze();
  }

  public void buildRooms(int numRooms){
    for (int i = 0; i < numRooms; i++){
      // choose random dimensions of room
      int randBase = randNum(7, 12);
      int randHeight = randNum(5, 12);

      // choose random tile
      int randRow = randNum(0, _rows - 1);
      int randCol = randNum(0, _cols - 1);

      // System.out.println("randRow: " + randRow); // diag
      // System.out.println("randHeight:" + randHeight); // diag
      // System.out.println("randCol: " + randCol); // diag
      // System.out.println("randBase: " + randBase); // diag

      // check if bottom right corner still fits within map
      // if not, resize
      while (randRow + randHeight >= _rows && randCol + randBase >= _cols){
        randBase = randNum(7, 12);
        randHeight = randNum(5, 12);
        randRow = randNum(0, _rows);
        randCol = randNum(0, _cols);
      }

      // passes check, so build out the space
      for (int b = 0; b < randBase; b++){
        for (int h = 0; h < randHeight; h++){
          // System.out.println("randRow: " + randRow + " ~~ b: " + b); // diag
          // System.out.println("randCol: " + randCol + " ~~ h: " + h); // diag
          if (randRow + b < _rows && randCol + h < _cols && _maze[randRow+b][randCol+h].equals(WALL)){
            _maze[randRow + b][randCol + h] = SPACE;
          }
        }
      }

    } // end for loop
  } // end method

  // returns [lowerLimit, upperLimit)
  public int randNum(int lowerLimit, int upperLimit){
    return (int)(Math.random() * (upperLimit - lowerLimit) + lowerLimit);
  }

  public void buildBorders(){
    for (int i = 0; i < _cols - 1; i++){
      _maze[0][i] = WALL;
      _maze[_rows - 1][i] = WALL;
    }
    for (int j = 0; j < _rows - 1; j++){
      _maze[j][0] = WALL;
      _maze[j][_cols - 1] = WALL;
    }
    _maze[_rows-1][_cols - 1] = WALL;
  }

  public void carveMaze(){
    for (int i = 0; i < _rows - 1; i ++){
      for (int j = 0; j < _cols - 1; j++){
        if (_maze[i][j].equals(WALL)){
          carve(i,j);
        }
      }
    }
  }

  public void generate(int startrow, int startcol){
    _maze[startrow][startcol] = "S";
    int endRow = Math.abs(_maze.length - 1 - startrow);
    int endCol = Math.abs(_maze[startrow].length - 1 - startcol);
    _maze[endRow][endCol] = "E";
  }

  private void carve(int row, int col){
      //can carve: not on border, not a space, fewer than 2 neighboring spaces
    if(canCarve(row,col)){
      _maze[row][col] = SPACE;

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

    int count = 0;
    if (_maze[row+1][col].equals(SPACE))
      count++;

    if (_maze[row-1][col].equals(SPACE))
      count++;

    if (_maze[row][col-1].equals(SPACE))
      count++;

    if (_maze[row][col+1].equals(SPACE))
      count++;

    if (count >= 2) {
      return false;
    }

    if (_maze[row][col].equals(SPACE)) {
      return false;
    }

    return true;
  }

  private boolean canBeGate(int row, int col) {
    if (row <= 0 || col <= 0 || row >= _maze.length - 1 || col >= _maze[row].length - 1) {
      return false;
    }

    int count = 0;
    if (_maze[row+1][col].equals(SPACE))
      count++;

    if (_maze[row-1][col].equals(SPACE))
      count++;

    if (_maze[row][col-1].equals(SPACE))
      count++;

    if (_maze[row][col+1].equals(SPACE))
      count++;

    if (count == 2) {
      return true;
    }

    if (_maze[row][col].equals(SPACE)) {
      return false;
    }

    return false;
  }

  public String[][] getGeneratedMaze(){
      generate(0,0);
      carve(1,1);
      return _maze;
  }

  // inner class
  class Room{
    int _tlr, _tlc, _brr, _brc, _maze;
    ArrayList<Tile> gates = new ArrayList();
    RoomBorders(int TLr, int TLc, int BRr, int BRc, String[][] parentMaze){
      _tlr = TLr;
      _tlc = TLc;
      _brr = BRr;
      _brc = BRc;
      _maze = parentMaze;
    }

    void removeGate(){ // choose random gate to the maze, remove it.
      int choices = 0;
      int randR, randC;
      // iterate thru all borders of the room, if they can be a gate, add to arraylist
      for (int i = _tlr; i <= _brr; i++){
        if (canBeGate(i, _tlc-1)){ gates.add(new Tile(i, _tlc-1)); }
        if (canBeGate(i, _brc+1)){ gates.add(new Tile(i, _brc+1)); }
      }
      for (int j = _tlc; j <= _brc; j++){
        if (canBeGate(_tlr-1, j)){ gates.add(new Tile(_tlr-1, j)); }
        if (canBeGate(_blr+1, j)){ gates.add(new Tile(_blr+1, j)); }
      }

      // choose random tile from gates, turn that tile into a space


    }

    class Tile{
      int r, c;
      Tile(int row, int col){
        r = row; c = col;
      }
      int getRow(){ return r; }
      int getCol(){ return c; }
    }
    // if (_tlc - 1 >= 0){
      //   // [_tlr, _brr + 1)
      //   while (int attempts = 0; attempts < 100; attempts++){ // cap number of attempts
        //     randR = randNum(_tlr, _brr+1);
        //     if (canBeGate(randR, _tlc - 1)){
          //       _maze[randR][_tlc - 1] = SPACE;
          //       return;
          //     }

      // for (int i = _tlr; i <= _brr; i++){
      //   if (i + 1 <= _rows){
      //     for (int j = _tlc; j <= _brc; j++){
      //       if (canBeGate(i+1, j)){
      //
      //       }
      //     }
      //   }
      // }
    // }

  }


  public String toString(){
      String retVal = "";
      for(int i = 0; i<_maze.length; i++){
          for(int e=0; e<_maze[0].length; e++){
            if(_maze[i][e].equals("#"))
              retVal = retVal + "\u001b[42m" + "\u001b[32m" + " " + "\u001b[30m" + "\u001b[40m" ;
            else{
              retVal += _maze[i][e];
            }
          }
          retVal += "\n";
      }
      return retVal;
  }

  public static void main(String[] args) {
      MazeGenerator test = new MazeGenerator(27,48);
      System.out.println(test);
      System.out.println("");
      // test.carve(1,1);
      // System.out.println(test);
  }


}
