import java.util.ArrayList;

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
    private Character monster;
    private ArrayList<Character> currentMonster = new ArrayList<Character>(); // monsters in the frame
    private ArrayList<Character> monsters = new ArrayList<Character>();
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
      // hero trigger monster combat
      currentMonster.clear();
      for (Character mon : monsters) {
        if (mc.getC() == mon.getC() && mc.getR() == mon.getR()) {
          currentMonster.add(mon);
          battlePhase = true;
          break;
        }
      }

    }

    // monster move
    public void monsterTurn() {
      // moves all monsters on the map
      for (Character mon : monsters) {
        int monC = mon.getC(); // stores current C tile
        int monR = mon.getR(); // stores current R tile
        // monster movement
        int choice = (int)(Math.random() * 4);
        boolean moved = false;
        while (!moved) {
          if (choice == 0 && maze.getPos(monR-1, monC) != "#") {
            // currentFrame.setPos(mon.getR(), mon.getC(), mon.lastTile());
            mon.moveUp();
            currentFrame.setPos(mon.getR(), mon.getC(), enemy);
            moved = true;
          }
          else if (choice == 1 && maze.getPos(monR, monC-1) != "#") {
            // currentFrame.setPos(mon.getR(), mon.getC(), mon.lastTile());
            mon.moveLeft();
            currentFrame.setPos(mon.getR(), mon.getC(), enemy);
            moved = true;
          }
          else if (choice == 2 && maze.getPos(monR+1, monC) != "#") {
            // currentFrame.setPos(mon.getR(), mon.getC(), mon.lastTile());
            mon.moveDown();
            currentFrame.setPos(mon.getR(), mon.getC(), enemy);
            moved = true;
          }
          else if (choice == 3 && maze.getPos(monR, monC+1) != "#") {
            // currentFrame.setPos(mon.getR(), mon.getC(), mon.lastTile());
            mon.moveRight();
            currentFrame.setPos(mon.getR(), mon.getC(), enemy);
            moved = true;
          }
        }
      }
      // monster trigger hero combat
      currentMonster.clear();
      for (Character mon : monsters) {
        if (mc.getC() == mon.getC() && mc.getR() == mon.getR()) {
          currentMonster.add(mon);
        }
      }
      battlePhase = true;
      for (Character current : currentMonster) {
        monster = current;
        //battleChoice(0);
        if (!mc.isAlive()) { // hero died
          battlePhase = false;
          return;
        }
        else { // monster died
          monsters.remove(current);
        }
      }
      battlePhase = false;
    }

    public void combat(int key) {
      Character current = currentMonster.get(0);
      Character temp = current;
      if (battlePhase) {
        // player attack
        if (key == 1) {
          characterAttack(mc, temp, 10, 0);
        }
        // use potion (not implemented)
        else if (key == 2) {
        }
        // flee (not implemented)
        else if (key == 3) {
        }
      }
      if (!temp.isAlive()) {
        monsters.remove(current);
        battlePhase = false;
      // monster attack
      if (mc.isAlive() && battlePhase) {
        characterAttack(temp, mc, 15, 0);
        }
      }
      else if (!mc.isAlive()){
        // player died
        dead();
      }
    }

    public void characterAttack(Character attacker, Character attacked, int weaponPower, int shieldPower) {
      int dmg = attacker.getAtk() + weaponPower - shieldPower;
      if (dmg < 0) {
        dmg = 0;
      }
      attacked.subtractHealth(dmg);
      System.out.println( "\n" + attacker.getName() + " dealt " + dmg + " damage.");
      System.out.println(attacked.getName() + "\tHealth: " + attacked.getHealth() + "\tAttack: " + attacked.getAtk());
    }

    public void round(String key) {
      if (battlePhase == false) {
        playerTurn(key);
        if (mc.isAlive()) {
          monsterTurn();
        }
        // generate another floor
        if (mc.isAlive()) {
          processTile();
        }
        // player died
        else if (!mc.isAlive()) {
          dead();
        }
      }
    }

    // helper method for processTile
    public boolean ifEnd() { // if hero is on end tile
      return mc.lastTile().equals("E");
    }

    public void processTile(){
      if (ifEnd()){
        nextFloor();
      }
    }

    public void nextFloor() { // how to generate next floor
      System.out.println("You cleared the floor!");
      System.out.println("Current score: " + score);
      System.out.println("Generating next stage ...");
      System.out.println("...");

      MazeGenerator temp = new MazeGenerator(rows, cols);
      maze = new Maze(temp.getGeneratedMaze());
      currentFrame = maze;

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
