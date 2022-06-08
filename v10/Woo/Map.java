import java.util.ArrayList;
import java.util.LinkedList;

public class Map{

    private String hero = "X";
    private String enemy = "M";

    private static void clear()
    {
      System.out.println("\033[2J");
      System.out.println("\033[" + 1 + ";" + 1 + "H");
    }

    // maze instance variables
    private Maze maze;
    private Maze currentFrame;
    private int rows;
    private int cols;
    private boolean exitPlaced;

    // RPG instance variables
    private Character mc;
    private Monster monster;
    private ArrayList<Monster> monsters = new ArrayList<Monster>();
    private int monsterCount = 15; // total amount of monsters per floor
    private int healTiles = 0;
    private boolean battlePhase = false;

    // system instance variables
    private int score; // accumulates after each floor

    public Map(){
        rows = 81;
        cols = 144;
        MazeGenerator troll = new MazeGenerator(rows, cols);
        maze = new Maze(troll.getGeneratedMaze());
        currentFrame = maze;

        // creates the hero in a room
        while (true) {
          int heroR = troll.randNum(1, rows-1);
          int heroC = troll.randNum(1, cols-1);
          if(isRoom(heroR, heroC)) {
            // isTrue = true;
            mc = new Hero(150, 10, 1, heroR, heroC, currentFrame);
            break;
          }
        }

        // creates the monsters in a room
        while (monsters.size() != monsterCount) {
          int monsterR = (int) (Math.random() * (rows - 1)) + 1;
          int monsterC = (int) (Math.random() * (cols - 1)) + 1;
          if(isRoom(monsterR, monsterC) && Math.sqrt(Math.pow(monsterC - mc.getC(), 2) + Math.pow(monsterR - mc.getR(), 2)) > 10) { // monster spawns distance of at least 10
            monster = new Monster(100, 10, 1, monsterR, monsterC, currentFrame);
            monsters.add(new Monster(100, 10, 1, monsterR, monsterC, currentFrame));
            currentFrame.setPos(monsterR, monsterC, enemy);
          }
        }

        // spawn 2 healing tiles
        while (healTiles != 2){
          int healR = (int) (Math.random() * (rows - 1)) + 1;
          int healC = (int) (Math.random() * (cols - 1)) + 1;
          if (isRoom(healR, healC) && Math.sqrt(Math.pow(healC - mc.getC(), 2) + Math.pow(healR - mc.getR(), 2)) > 50){
            currentFrame.setPos(healR, healC, "H");
            healTiles++;
          }
        }

        // spawn one treasure chest
        while (true){
          int treasureR = (int) (Math.random() * (rows - 1)) + 1;
          int treasureC = (int) (Math.random() * (cols - 1)) + 1;
          if (isRoom(treasureR, treasureC) && Math.sqrt(Math.pow(treasureC - mc.getC(), 2) + Math.pow(treasureR - mc.getR(), 2)) > 50){
            currentFrame.setPos(treasureR, treasureC, "T");
            break;
          }
        }

        // creates an exit tile
        while (true) {
          int exitR = (int) (Math.random() * (rows - 1)) + 1;
          int exitC = (int) (Math.random() * (cols - 1)) + 1;
          if (isRoom(exitR, exitC) && Math.sqrt(Math.pow(exitC - mc.getC(), 2) + Math.pow(exitR - mc.getR(), 2)) > 50) {
            currentFrame.setPos(exitR, exitC, "E");
            break;
          }
        }

        // place hero
        currentFrame.setPos(mc.getR(), mc.getC(), hero);
    }

    // player move
    public void playerTurn(String key) {
      // player movement
      if (key == "W") {
        moveUp();
      }
      else if (key == "A") {
        moveLeft();
      }
      else if (key == "S") {
        moveDown();
      }
      else if (key == "D") {
        moveRight();
      }
    }

    // // monster move
    public void monsterTurn() {
      // each monster will play their turn
      for (int i = 0; i < monsters.size(); i++){
        if ((monsters.get(i)).playTurn()){
          // battle method goes here
          System.out.println("The monster has initiated a battle with you!");
          battle(monsters.get(i), mc);
          battlePhase = false;
          System.out.println("You defeated a monster!");
          System.out.println("Current score: " + score);
        }
      }
    }

    public void battle(Character first, Character second){
      LinkedList<Character> turnOrder = new LinkedList();
      turnOrder.offerFirst(first); turnOrder.offerLast(second);
      battlePhase = true;

      // play battle
      System.out.println("A battle has started!");
      while (first.isAlive() && second.isAlive()){
        if (first.chooseMove(second)){ // if true, then hero has fleed
          System.out.println("Your act of cowardice is sad. Your score has been reduced.");
          score -= 200;
          currentFrame.setPos(mc.getR(), mc.getC(), "X");
          monsters.remove(second);
          return;
        }
        if (second.isAlive()){
          if (second.chooseMove(first)){ // if true, then hero has fleed
            System.out.println("Your act of cowardice is sad. Your score has been reduced.");
            score -= 200;
            currentFrame.setPos(mc.getR(), mc.getC(), "X");
            monsters.remove(first);
            return;
          }
        }
      }

      // check if character is dead
      if (!(mc.isAlive())){
        System.out.println("Sorry, you have died.");
        System.out.println("Score: " + score);
        System.exit(0);
      }

      // check which is dead
      if (!(turnOrder.getFirst().isAlive())){
        if (turnOrder.getFirst() instanceof Monster){
          // you kill the monster
          monsters.remove(turnOrder.getFirst());
          return;
        }
        else {
          // you died
          dead();
          return;
        }
      }
      else{ // whoever was 2nd has died
        if (turnOrder.getLast() instanceof Monster){
          // you killed the monster
          monsters.remove(turnOrder.getLast());
          return;
        }
        else {
          // you died
          dead();
          return;
        }
      }
    }

    public void round(String key) {
      if (battlePhase == false) {
        playerTurn(key);
        processTile();

        if (mc.isAlive()) {
          monsterTurn(); // let monsters play a turn
        }
        // player died
        else if (!mc.isAlive()) {
          dead();
        }
      }
    }

    public void processTile(){
      if (mc.lastTile().equals("E")){ // escape the floor, go to next floor
        nextFloor();
      }

      if (mc.lastTile().equals("T")){ // give random weapon
        mc.addWeapon(new Weapon());
        mc.lastTileToSpace();
      }

      if (mc.lastTile().equals("H")){ // restore health to max
        mc.addHealth(150 - mc.getHealth());
      }

      if (mc.lastTile().equals("M")){
        // itertate thru, find the monster, initiate the battle
        for (int i = 0; i < monsters.size(); i++){
          if (monsters.get(i).getR() == mc.getR() && monsters.get(i).getC() == mc.getC()){
            System.out.println("You initiated a battle with a monster!");
            battle(mc, monsters.get(i));
            battlePhase = false;
            mc.lastTileToSpace();
            score += 50;
            System.out.println("You defeated a monster!");
            System.out.println("Current score: " + score);
            return;
          }
        }
      }


    }

    public void nextFloor() { // how to generate next floor
      score += 100;
      System.out.println("You cleared the floor!");
      System.out.println("Current score: " + score);
      System.out.println("Generating next stage ...");
      System.out.println("...");

      MazeGenerator temp = new MazeGenerator(rows, cols);
      maze = new Maze(temp.getGeneratedMaze());
      currentFrame = maze;
      monsters.clear();
      exitPlaced = false;

      // creates the hero in a room
      while (true) {
        int heroR = temp.randNum(1, rows-1);
        int heroC = temp.randNum(1, cols-1);
        if(isRoom(heroR, heroC)) {
          // isTrue = true;
          mc = new Hero(mc.getHealth(), mc.getAtk(), mc.getSpeed(), heroR, heroC, mc.getInventory(), mc.getLastWeapon(), currentFrame);
          break;
        }
      }

      // creates the monsters in a room
      while (monsters.size() != monsterCount) {
        int monsterR = (int) (Math.random() * (rows - 1)) + 1;
        int monsterC = (int) (Math.random() * (cols - 1)) + 1;
        if(isRoom(monsterR, monsterC) && Math.sqrt(Math.pow(monsterC - mc.getC(), 2) + Math.pow(monsterR - mc.getR(), 2)) > 10) { // monster spawns distance of at least 10
          monster = new Monster(100, 10, 1, monsterR, monsterC, currentFrame);
          monsters.add(monster);
          currentFrame.setPos(monsterR, monsterC, enemy);
        }
      }

      // spawn 2 healing tiles
      while (healTiles != 2){
        int healR = (int) (Math.random() * (rows - 1)) + 1;
        int healC = (int) (Math.random() * (cols - 1)) + 1;
        if (isRoom(healR, healC) && Math.sqrt(Math.pow(healC - mc.getC(), 2) + Math.pow(healR - mc.getR(), 2)) > 50){
          currentFrame.setPos(healR, healC, "H");
          healTiles++;
        }
      }

      // spawn one treasure chest
      while (true){
        int treasureR = (int) (Math.random() * (rows - 1)) + 1;
        int treasureC = (int) (Math.random() * (cols - 1)) + 1;
        if (isRoom(treasureR, treasureC) && Math.sqrt(Math.pow(treasureC - mc.getC(), 2) + Math.pow(treasureR - mc.getR(), 2)) > 50){
          currentFrame.setPos(treasureR, treasureC, "T");
          break;
        }
      }

      // creates an exit tile
      while (!exitPlaced) {
        int exitR = (int) (Math.random() * (rows - 1)) + 1;
        int exitC = (int) (Math.random() * (cols - 1)) + 1;
        if (isRoom(exitR, exitC) && Math.sqrt(Math.pow(exitC - mc.getC(), 2) + Math.pow(exitR - mc.getR(), 2)) > 50) {
          currentFrame.setPos(exitR, exitC, "E");
          exitPlaced = true;
        }
      }

      // place hero
      currentFrame.setPos(mc.getR(), mc.getC(), hero);
    }

    public void dead() {
      System.out.println("You have died. Better luck next time.");
      System.out.println("Final Score: " + score);
    }

    public String toString() {
         clear();
         return currentFrame.toString();
     }

    // player movement
    public boolean moveUp() {
      if( (!(currentFrame.getPos(mc.getR()-1, mc.getC()).equals("#"))) && (!(currentFrame.getPos(mc.getR()-1, mc.getC()).equals("@"))) ){
        currentFrame.setPos(mc.getR(), mc.getC(), mc.lastTile());
        mc.moveUp();
        processTile();
        currentFrame.setPos(mc.getR(), mc.getC(), hero);
        return true;
      } else {
        return false;
      }
    }
    public boolean moveRight() {
        if( (!(currentFrame.getPos(mc.getR(), mc.getC()+1).equals("#"))) && (!(currentFrame.getPos(mc.getR(), mc.getC()+1).equals("@"))) ){
            currentFrame.setPos(mc.getR(), mc.getC(), mc.lastTile());
            mc.moveRight();
            processTile();
            currentFrame.setPos(mc.getR(), mc.getC(), hero);
            return true;
        } else {
          return false;
        }
    }
    public boolean moveDown() {
        if( (!(currentFrame.getPos(mc.getR()+1, mc.getC()).equals("#"))) && (!(currentFrame.getPos(mc.getR()+1, mc.getC()).equals("@"))) ){
            currentFrame.setPos(mc.getR(), mc.getC(), mc.lastTile());
            mc.moveDown();
            processTile();
            currentFrame.setPos(mc.getR(), mc.getC(), hero);
            return true;
        } else {
          return false;
        }
    }
    public boolean moveLeft() {
        if( (!(currentFrame.getPos(mc.getR(), mc.getC()-1).equals("#"))) && (!(currentFrame.getPos(mc.getR(), mc.getC()-1).equals("@"))) ){
            currentFrame.setPos(mc.getR(), mc.getC(), mc.lastTile());
            mc.moveLeft();
            processTile();
            currentFrame.setPos(mc.getR(), mc.getC(), hero);
            return true;
        } else {
          return false;
        }
    }

    public String[][] getMaze() {
      return maze.getMaze();
    }

    public String[][] displayZone() {
        int topLeftR = mc.getR() - 10;
        int topLeftC = mc.getC() - 10;

        int currR = 0;
        int currC = 0;

        topLeftR = Math.max(0, topLeftR);
        topLeftC = Math.max(0, topLeftC);

        topLeftR = Math.min(topLeftR, rows-21);
        topLeftC = Math.min(topLeftC, cols-21);

        String[][] output = new String[21][21];
        for(int i = topLeftR; i < topLeftR + 21; i++){
            for(int e = topLeftC; e < topLeftC + 21; e++){
                output[currR][currC] = currentFrame.getPos(i,e);
                //System.out.print(output[currX][currY]);
                currC++;
            }
            //System.out.println("");
            currC = 0;
            currR++;
        }
      return output;
    }

    public boolean isRoom(int r, int c) {
        // returns true if coordinate is in a room
        return currentFrame.getMaze()[r-1][c-1].equals(" ") &&
               currentFrame.getMaze()[r][c-1].equals(" ") &&
               currentFrame.getMaze()[r+1][c-1].equals(" ") &&
               currentFrame.getMaze()[r-1][c].equals(" ") &&
               currentFrame.getMaze()[r][c].equals(" ") &&
               currentFrame.getMaze()[r+1][c].equals(" ") &&
               currentFrame.getMaze()[r-1][c+1].equals(" ") &&
               currentFrame.getMaze()[r][c+1].equals(" ") &&
               currentFrame.getMaze()[r+1][c+1].equals(" ");

    }

}
