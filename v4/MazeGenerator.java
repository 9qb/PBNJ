import java.util.ArrayList;
import java.util.Stack;

public class MazeGenerator {

  private final String SPACE = " ";
  private final String WALL = "#";
  private final String WORLD_BORDER = "@";
  private final String GATE = "!";
  private final String MAZEPATH = "$";

 /*
 Advised by students from Mr K's class while writing this class
 */

  String[][] _maze;
  ArrayList<Room> _rooms;
  ArrayList<ArrayList<Tile>> _mazes;
  int _rows, _cols;

  public MazeGenerator(int x, int y){
    _maze = new String[x][y];
    _rooms = new ArrayList();
    _mazes = new ArrayList();
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
    openGates();
    openGates();
    mazeToSpace();
    uncarveMaze();
    cleanup();
  }

  public void buildRooms(int numRooms){
    for (int i = 0; i < numRooms; i++){
      // choose random dimensions of room
      int randBase = randNum(7, 12);
      int randHeight = randNum(5, 12);

      // choose random tile
      int randRow = randNum(2, _rows - 3);
      int randCol = randNum(2, _cols - 3);

      // System.out.println("randRow: " + randRow); // diag
      // System.out.println("randHeight:" + randHeight); // diag
      // System.out.println("randCol: " + randCol); // diag
      // System.out.println("randBase: " + randBase); // diag

      // check if bottom right corner still fits within map
      // if not, resize
      while (randRow + randHeight >= _rows-2 && randCol + randBase >= _cols-2){
        randBase = randNum(7, 12);
        randHeight = randNum(5, 12);
        randRow = randNum(2, _rows - 2);
        randCol = randNum(2, _cols - 2);
      }

      // passes check, so build out the space && add to stack
      _rooms.add(new Room(randRow, randCol, randRow+randHeight, randCol+randBase));

      for (int b = 0; b < randBase && randCol+b < _cols-2; b++){
        for (int h = 0; h < randHeight && randRow+h < _rows-2; h++){
          // System.out.println("randRow: " + randRow + " ~~ h: " + h); // diag
          // System.out.println("randCol: " + randCol + " ~~ b: " + b); // diag
          if (_maze[randRow+h][randCol+b].equals(WALL)){
            // System.out.println("set r=" + (randRow+h) + ", c=" + (randCol+b) + " to space"); // diag
            _maze[randRow+h][randCol+b] = SPACE;
          }
        }
      }

    } // end for loop
  } // end method

  // returns [lowerLimit, upperLimit)
  public int randNum(int lowerLimit, int upperLimit){
    return (int)(Math.random() * (upperLimit - lowerLimit) + lowerLimit);
  }

  public void cleanup(){
    for (int i = 1; i < _rows - 2; i ++){
      for (int j = 1; j < _cols - 2; j++){
        cleanup(i, j);
      }
    }
    openGates(); openGates();
  }

  public void cleanup(int row, int col){
    if (row > 1 && col > 1 && row < _maze.length - 2 && col < _maze[row].length - 2 && _maze[row][col].equals(WALL)) {
      // if has 3 adjacent spaces, might as well just remove to declutter the map
      int count = 0;
      if (_maze[row+1][col].equals(SPACE) ){
        count++;
      }

      if (_maze[row-1][col].equals(SPACE)){
        count++;
      }

      if (_maze[row][col+1].equals(SPACE)){
        count++;
      }

      if (_maze[row][col-1].equals(SPACE)){
        count++;
      }

      if (count >= 3) {
        _maze[row][col] = SPACE;
      }

      uncarve(row+1, col);
      uncarve(row-1, col);
      uncarve(row, col+1);
      uncarve(row, col-1);
    }
  }

  public void buildBorders(){
    for (int i = 0; i < _cols - 1; i++){
      _maze[0][i] = WORLD_BORDER;
      _maze[_rows - 1][i] = WORLD_BORDER;
    }
    for (int j = 0; j < _rows - 1; j++){
      _maze[j][0] = WORLD_BORDER;
      _maze[j][_cols - 1] = WORLD_BORDER;
    }
    _maze[_rows-1][_cols - 1] = WORLD_BORDER;
  }

  public void carveMaze(){
    for (int i = 1; i < _rows - 2; i ++){
      for (int j = 1; j < _cols - 2; j++){
        carve(i,j);
      }
    }

    // carve(1,1);
  }

  public void mazeToSpace(){
    for (int i = 1; i < _rows - 1; i ++){
      for (int j = 1; j < _cols - 1; j++){
        if (_maze[i][j].equals(MAZEPATH)){ _maze[i][j] = SPACE; }
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
    // can carve: not on border, not a space, fewer than 2 neighboring spaces
    if(canCarve(row,col) && _maze[row][col].equals(WALL)){
      _maze[row][col] = MAZEPATH;

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

  private void carve(int row, int col, ArrayList<Tile> al){
    // can carve: not on border, not a space, fewer than 2 neighboring spaces
    if(canCarve(row,col) && _maze[row][col].equals(WALL)){
      al.add(new Tile(row,col));
      _maze[row][col] = MAZEPATH;

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
        carve(row + direction[0], col + direction[1], al);
      }
    }
  }

  private boolean canCarve(int row, int col) {
    if (row <= 0 || col <= 0 || row >= _maze.length - 1 || col >= _maze[row].length - 1) {
      return false;
    }

    int count = 0;
    if (_maze[row+1][col].equals(MAZEPATH)){
      count++;
    }
    else if (_maze[row+1][col].equals(SPACE)){
      return false;
    }

    if (_maze[row-1][col].equals(MAZEPATH)){
      count++;
    }
    else if (_maze[row-1][col].equals(SPACE)){
      return false;
    }

    if (_maze[row][col+1].equals(MAZEPATH)){
      count++;
    }
    else if (_maze[row][col+1].equals(SPACE)){
      return false;
    }

    if (_maze[row][col-1].equals(MAZEPATH)){
      count++;
    }
    else if (_maze[row][col-1].equals(SPACE)){
      return false;
    }

    if (count >= 2) {
      return false;
    }

    if (_maze[row][col].equals(SPACE)) {
      return false;
    }

    return true;
  }

  public void uncarveMaze(){
    for (int n = 0; n < 2; n++){
      for (int i = 1; i < _rows - 2; i ++){
        for (int j = 1; j < _cols - 2; j++){
          uncarve(i,j);
        }
      }
    }
  }

  public void uncarve(int row, int col){
    if(canUncarve(row, col) && _maze[row][col].equals(SPACE)){
      System.out.println("(" + row + ", " + col + ")" + " has been uncarved");
      _maze[row][col] = WALL;

      uncarve(row+1, col);
      uncarve(row-1, col);
      uncarve(row, col+1);
      uncarve(row, col-1);
    }
  }

  public boolean canUncarve(int row, int col){
    if (row <= 0 || col <= 0 || row >= _maze.length - 1 || col >= _maze[row].length - 1) {
      return false;
    }

    int count = 0;
    if (_maze[row+1][col].equals(WALL) || _maze[row+1][col].equals(WORLD_BORDER) )
      count++;

    if (_maze[row-1][col].equals(WALL) || _maze[row-1][col].equals(WORLD_BORDER) )
      count++;

    if (_maze[row][col-1].equals(WALL) || _maze[row][col-1].equals(WORLD_BORDER) )
      count++;

    if (_maze[row][col+1].equals(WALL) || _maze[row][col+1].equals(WORLD_BORDER) )
      count++;

    // System.out.println("count: " + count);

    if (count >= 3) {
      // System.out.println(true);
      return true;
    }

    if (!(_maze[row][col].equals(SPACE))) {
      return false;
    }

    return false;
  }

  public void openGates(){
    markGates();
    for (int i = 0; i < _rooms.size(); i++){
      _rooms.get(0).removeGates(randNum(1,6));
    }
  }

  public void markGates(){
    for (int i = 0; i < _rooms.size(); i++){
      _rooms.get(i).markGates();
    }
  }

  private boolean isGate(int row, int col) {
    if (row <= 0 || col <= 0 || row >= _maze.length - 1 || col >= _maze[row].length - 1) {
      return false;
    }

    if (!(_maze[row][col].equals(WALL))){ return false; }

    int count = 0;
    if (_maze[row+1][col].equals(MAZEPATH) || (_maze[row+1][col].equals(SPACE))){
      count++;
    }

    if (_maze[row-1][col].equals(MAZEPATH) || (_maze[row-1][col].equals(SPACE))){
      count++;
    }

    if (_maze[row][col+1].equals(MAZEPATH) || (_maze[row][col+1].equals(SPACE))){
      count++;
    }

    if (_maze[row][col-1].equals(MAZEPATH) || (_maze[row][col-1].equals(SPACE))){
      count++;
    }

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
    int _tlr, _tlc, _brr, _brc;
    ArrayList<Tile> gates = new ArrayList();
    Room(int TLr, int TLc, int BRr, int BRc){
      _tlr = TLr;
      _tlc = TLc;
      _brr = BRr;
      _brc = BRc;
    }

    // intermediate method, marks surrounding tiles of all rooms
    void markGates(){
      // iterate thru all borders of the room, if it can be a gate, mark as so
      for (int i = _tlr; i <= _brr; i++){
        // System.out.println("is " + i + ", " + (_tlc-1) + " a gate? " + isGate(i, _tlc-1)); // diag
        if (isGate(i, _tlc-1)){
          _maze[i][_tlc-1] = GATE;
          gates.add(new Tile(i, _tlc-1));
        }
        // System.out.println("is " + i + ", " + (_brc+1) + " a gate? " + isGate(i, _brc+1)); // diag
        if (isGate(i, _brc+1)){
          _maze[i][_brc+1] = GATE;
          gates.add(new Tile(i, _brc+1));
        }
      }
      for (int j = _tlc; j <= _brc; j++){
        // System.out.println("is " + (_tlr-1) + ", " + j + " a gate? " + isGate(_tlr-1, j)); // diag
        if (isGate(_tlr-1, j)){
          _maze[_tlr-1][j] = GATE;
          gates.add(new Tile(_tlr-1, j));
         }
        // System.out.println("is " + (_brr+1) + ", " + j + " a gate? " + isGate(_tlr+1, j)); // diag
        if (isGate(_brr+1, j)){
          _maze[_brr+1][j] = GATE;
          gates.add(new Tile(_brr+1, j));
        }
      }
    }

    void removeGates(int numGates){
      for (int i = 0; i < numGates && !(gates.isEmpty()); i++){
        gates.remove(randNum(0, gates.size())).setTile(SPACE);
      }

      // for all remaining gates, set them back to normal walls
      while (!(gates.isEmpty())){
        gates.remove(0).setTile(WALL);
      }
    }
  }

  // helper class
  class Tile{
    int r, c;
    Tile(int row, int col){
      r = row; c = col;
    }
    int getRow(){ return r; }
    int getCol(){ return c; }
    void setTile(String tileType){ _maze[r][c] = tileType; }
    boolean equals(Tile otherTile){ return this.r == otherTile.getRow() && this.c == otherTile.getCol(); }
  }


  public String toString(){
      String retVal = "";
      for(int i = 0; i<_maze.length; i++){
          for(int e=0; e<_maze[0].length; e++){
            if(_maze[i][e].equals("#") || _maze[i][e].equals("@"))
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
      // System.out.println(test._mazes.size());
      System.out.println(test);
      System.out.println("");
      // test.carve(1,1);
      // System.out.println(test);
  }


}
