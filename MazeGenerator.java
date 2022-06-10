// PBNJ - Brian Li, Nakib Abedin, Jefford Shau
// APCS pd07
// Final Project -- Dungeon Crawler
// 2022-06-10

import java.util.ArrayList;

public class MazeGenerator {

  /*
    Randomly generates a dungeon
    Uses a refactored version of the maze generation algorithm taught in Mr K's class
  */

  private final String SPACE = " ";
  private final String WALL = "#";
  private final String WORLD_BORDER = "@";
  private final String GATE = "!";
  private final String MAZEPATH = "$";

  private String[][] _maze;
  private ArrayList<Room> _rooms;
  private ArrayList<ArrayList<Tile>> _mazes;
  private int _rows, _cols;

  // default constructor, generates the maze
  public MazeGenerator(int r, int c){
    // sets all tiles to walls
    _maze = new String[r][c];
    _rooms = new ArrayList();
    _mazes = new ArrayList();
    for(int i = 0; i <_maze.length; i++){
      for(int e = 0; e <_maze[0].length; e++){
        _maze[i][e] = WALL;
      }
    }
    _rows = r;
    _cols = c;

    // changes tiles in the maze
    buildRooms(randNum(8, 10));
    buildBorders();
    carveMaze();
    openGates();
    openGates();
    mazeToSpace();
    uncarveMaze();
    cleanup();
  }

  // generate rooms randomly in the 2D array
  private void buildRooms(int numRooms){
    for (int i = 0; i < numRooms; i++){
      // choose random dimensions of room
      int randBase = randNum(7, 12);
      int randHeight = randNum(5, 12);

      // choose random tile
      int randRow = randNum(2, _rows - 3);
      int randCol = randNum(2, _cols - 3);

      // check if bottom right corner still fits within map
      // if not, resize
      while (randRow + randHeight >= _rows-2 && randCol + randBase >= _cols-2){
        randBase = randNum(7, 12);
        randHeight = randNum(5, 12);
        randRow = randNum(2, _rows - 2);
        randCol = randNum(2, _cols - 2);
      }

      // passes check, so build out the space && add to al
      _rooms.add(new Room(randRow, randCol, randRow+randHeight, randCol+randBase));

      for (int b = 0; b < randBase && randCol+b < _cols-2; b++){
        for (int h = 0; h < randHeight && randRow+h < _rows-2; h++){
          if (_maze[randRow+h][randCol+b].equals(WALL)){
            _maze[randRow+h][randCol+b] = SPACE;
          }
        }
      }

    } // end for loop
  } // end method

  // returns [lowerLimit, upperLimit) integer
  // helper method
  public static int randNum(int lowerLimit, int upperLimit){
    return (int)(Math.random() * (upperLimit - lowerLimit) + lowerLimit);
  }

  private void cleanup(){
    openGates(); openGates();
    for (int i = 1; i < _rows - 2; i ++){
      for (int j = 1; j < _cols - 2; j++){
        cleanup(i, j);
      }
    }
    returnGates();
  }

  // sets gates back to normal spaces
  private void returnGates(){
    for (int i = 0; i < _rows; i ++){
      for (int j = 0; j < _cols; j++){
        if(_maze[i][j].equals(GATE)){
          _maze[i][j] = SPACE;
        }
      }
    }
  }

  // final run through the maze, makes maze generation look more spacious and less rigid
  private void cleanup(int row, int col){
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

        // check if we can uncarve adjacent tiles as a result of this tile being turned to space
        uncarve(row+1, col);
        uncarve(row-1, col);
        uncarve(row, col+1);
        uncarve(row, col-1);
      }

    }
  }

  // creates map border
  private void buildBorders(){
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

  // tries to carve a maze on every tile within the 2D array
  private void carveMaze(){
    for (int i = 1; i < _rows - 2; i ++){
      for (int j = 1; j < _cols - 2; j++){
        carve(i,j);
      }
    }
  }

  // turns mazepath tiles to space tiles
  private void mazeToSpace(){
    for (int i = 1; i < _rows - 1; i ++){
      for (int j = 1; j < _cols - 1; j++){
        if (_maze[i][j].equals(MAZEPATH)){ _maze[i][j] = SPACE; }
      }
    }
  }

  // tries to carve a maze recursively through the 2D array
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

  // checks if this tile is possible to be carved:
  // criteria: not on border, not a space, fewer than 2 neighboring spaces
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

  // "uncarves" the maze by iterating through each tile to see if it is possible to uncarve
  private void uncarveMaze(){
    for (int n = 0; n < 2; n++){
      for (int i = 1; i < _rows - 2; i ++){
        for (int j = 1; j < _cols - 2; j++){
          uncarve(i,j);
        }
      }
    }
  }

  // "uncarves" a tile by filling in the space to be a wall again
  // criteria: has 3 adjacent wall tiles. (i.e. this tile is a dead end)
  private void uncarve(int row, int col){
    if(canUncarve(row, col) && _maze[row][col].equals(SPACE)){
      // System.out.println("(" + row + ", " + col + ")" + " has been uncarved"); // diag
      _maze[row][col] = WALL;

      // checks if we can uncarve adjacent tiles as a result of this tile being uncarved
      uncarve(row+1, col);
      uncarve(row-1, col);
      uncarve(row, col+1);
      uncarve(row, col-1);
    }
  }

  // checks if we can uncarve a certain tile
  // criteria: has 3 adjacent wall tiles. (i.e. this tile is a dead end)
  private boolean canUncarve(int row, int col){
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

    // System.out.println("count: " + count); //diag

    if (count >= 3) {
      return true;
    }

    if (!(_maze[row][col].equals(SPACE))) {
      return false;
    }

    return false;
  }

  // opens up [3,6) "gate" tiles
  // a gate tile is a tile that is between a room and the carved maze
  private void openGates(){
    // intermediate method: marks possible gate tiles
    markGates();
    for (int i = 0; i < _rooms.size(); i++){
      _rooms.get(0).removeGates(randNum(3,6));
    }
  }

  // marks possible gate tiles (tiles separating the room from the outer maze)
  private void markGates(){
    for (int i = 0; i < _rooms.size(); i++){
      _rooms.get(i).markGates();
    }
  }

  // determines if a tile is a gate tile
  // criteria: a tile has only two adjacent "MAZEPATH" tiles or "SPACE" tiles
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
      return _maze;
  }

  // inner class
  class Room{
    private int _tlr, _tlc, _brr, _brc;
    private ArrayList<Tile> gates = new ArrayList();
    // constructor: supplies the rows and columns of the top left of the room and bottom right of the room
    Room(int TLr, int TLc, int BRr, int BRc){
      _tlr = TLr;
      _tlc = TLc;
      _brr = BRr;
      _brc = BRc;
    }

    // intermediate method, marks surrounding tiles of all rooms
    void markGates(){
      // iterate thru all borders of the room, if it can be a gate, mark as so
      // and add to the gates arraylist within the room
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

    // removes supplied number of gates within the room
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
  // simple class to record coordinates of a tile
  class Tile{
    int r, c;
    Tile(int row, int col){
      r = row; c = col;
    }
    int getRow(){ return r; }
    int getCol(){ return c; }
    void setTile(String tileType){ _maze[r][c] = tileType; }
  }

}
