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
    private ArrayList<Item> inventory = new ArrayList<Item>(); // hero inventory
    private Monster monster;
    private ArrayList<Monster> monsters = new ArrayList<Monster>();
    private int monsterCount = 8; // total amount of monsters per floor
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
        while (mc == null) {
          int heroR = troll.randNum(1, rows-1);
          int heroC = troll.randNum(1, cols-1);
          if(isRoom(heroR, heroC)) {
            // isTrue = true;
            mc = new Hero(100, 10, 1, heroR, heroC, currentFrame);
            break;
          }
        }

        // creates the monsters in a room
        while (monsters.size() != monsterCount) {
          int monsterR = troll.randNum(1, rows);
          int monsterC = troll.randNum(1, cols);
          if(isRoom(monsterR, monsterC) && Math.sqrt(Math.pow(monsterC - mc.getC(), 2) + Math.pow(monsterR - mc.getR(), 2)) > 10) { // monster spawns distance of at least 10
            monster = new Monster(100, 10, 1, monsterR, monsterC, currentFrame);
            monsters.add(monster);
            currentFrame.setPos(monsterR, monsterC, enemy);
          }
        }

        // creates an exit tile
        while (!exitPlaced) {
          int exitR = troll.randNum(1, rows);
          int exitC = troll.randNum(1, cols);
          if (isRoom(exitR, exitC) && Math.sqrt(Math.pow(exitC - mc.getC(), 2) + Math.pow(exitR - mc.getR(), 2)) > 50) {
            currentFrame.setPos(exitR, exitC, "E");
            exitPlaced = true;
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

      // // check if we should start a battle
      // for (Character mon : monsters) {
      //   if (mc.getC() == mon.getC() && mc.getR() == mon.getR()) {
      //     battleOrder.add(mon);
      //     battlePhase = true;
      //     break;
      //   }
      // }
      //
      // while (!(battleOrder.isEmpty())){
      //   battlePhase = true;
      //   for (Character current : battleOrder) {
      //     monster = current;
      //     //battleChoice(0);
      //     if (!mc.isAlive()) { // hero died
      //       battlePhase = false;
      //       return;
      //     }
      //     else { // monster died
      //       monsters.remove(current);
      //     }
      //   }
      //   battlePhase = false;
      // }
      //
    }

    // // monster move
    public void monsterTurn() {
      // each monster will play their turn
      for (int i = 0; i < monsters.size(); i++){
        if ((monsters.get(i)).playTurn()){
          // battle method goes here
          battle(monsters.get(i), mc);
        }
      }


    //   // check if we should start a battle
    //   for (int j = 0; j < monsters.size(); j++) {
    //     if (mc.getC() == monsters.get(j).getC() && mc.getR() == monsters.get(j).getR()) {
    //       battleOrder.add(monsters.get(j));
    //     }
    //   }
    //
    //   while (!(battleOrder.isEmpty())){
    //     battlePhase = true;
    //     for (Character current : battleOrder) {
    //       monster = current;
    //       //battleChoice(0);
    //       if (!mc.isAlive()) { // hero died
    //         battlePhase = false;
    //         return;
    //       }
    //       else { // monster died
    //         monsters.remove(current);
    //       }
    //     }
    //     battlePhase = false;
    //   }
    }

    // public void combat(int key) {
    //   Character current = battleOrder.peek();
    //   Character temp = current;
    //   if (battlePhase) {
    //     // player attack
    //     if (key == 1) {
    //       characterAttack(mc, temp, 10, 0);
    //     }
    //     // use potion (not implemented)
    //     else if (key == 2) {
    //     }
    //     // flee (not implemented)
    //     else if (key == 3) {
    //     }
    //   }
    //   if (!temp.isAlive()) {
    //     monsters.remove(current);
    //     battleOrder.poll();
    //     battlePhase = false;
    //   // monster attack
    //   if (mc.isAlive() && battlePhase) {
    //     characterAttack(temp, mc, 15, 0);
    //     }
    //   }
    //   else if (!mc.isAlive()){
    //     // player died
    //     dead();
    //   }
    // }

    public void battle(Character first, Character second){
      LinkedList<Character> turnOrder = new LinkedList();
      turnOrder.offerFirst(first); turnOrder.offerLast(second);

      // while both are still alive, play until one dead
      while (turnOrder.getFirst().isAlive() && turnOrder.getLast().isAlive()){
        if (turnOrder.getFirst().chooseMove()){ break; }
        if (turnOrder.getLast().isAlive()){
          if (turnOrder.getLast().chooseMove()){ break; }
        }
      }

      // check which is dead
      if (!(turnOrder.getFirst().isAlive())){
        if (turnOrder.getFirst() instanceof Monster){
          // you kill the monster
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
          return;
        }
        else {
          // you died
          dead()
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
      if (mc.lastTile().equals("E")){
        nextFloor();
      }

      if (mc.lastTile().equals("M")){
        // itertate thru, find the monster, initiate the battle

        mc.lastTileToSpace();
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
          mc = new Hero(mc.getHealth(), mc.getAtk(), mc.getSpeed(), heroR, heroC, currentFrame);
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

    public static void main(String[] args) {
        Map test = new Map();
        System.out.println(test);

        test.displayZone();

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
