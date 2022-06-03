import java.util.ArrayList;

public class Map{

    private String hero = "X";

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
          if(isRoom(monsterR, monsterC)) {
            monster = new Monster(100, 10, 1, monsterR, monsterC, currentFrame);
            monsters.add(monster);
          }

        }

        // creates an exit tile
        while (!exitPlaced){
          int exitR = troll.randNum(1, rows);
          int exitC = troll.randNum(1, cols);
          if (isRoom(exitR, exitC)){
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

    // monster move
    public void monsterTurn() {
      for (Character mon : monsters) {
        int monC = mon.getC(); // stores current C tile
        int monR = mon.getR(); // stores current R tile
        // monster movement
        int choice = (int)(Math.random() * 4);
        boolean moved = false;
        while (!moved) {
          if (choice == 0 && maze.getPos(monR-1, monC) != "#") {
            mon.moveUp();
            moved = true;
          }
          else if (choice == 1 && maze.getPos(monR, monC-1) != "#") {
            mon.moveLeft();
            moved = true;
          }
          else if (choice == 2 && maze.getPos(monR+1, monC) != "#") {
            mon.moveDown();
            moved = true;
          }
          else if (choice == 3 && maze.getPos(monR, monC+1) != "#") {
            mon.moveRight();
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

    //public void battleChoice(int key) {
    //  if (battlePhase == true) {
    //    while( mc.isAlive() && monster.isAlive() ) {
    //      System.out.println( "\nWhat is your choice?" );
    //      System.out.println( "\t1: Attack\n\t2: Use Item\n\t3: Flee\nSelection: " );
    //      // use weapon
    //      if ( key == 1 ) {
    //        weaponChoice();
    //      }
    //      // use item
    //      else if ( key == 2 ) {
    //        if (!(useItem())) {
    //          battleChoice();
    //        }
    //        else {
    //          characterAttack(mon, hero); // no attack weapon or item
    //        }
    //      }
    //      // escape
    //      else if ( key == 3 ) {
    //        int fleeChance = (int) (Math.random() * 10);
    //        if (fleeChance < 2) {
    //          System.out.println("\nThe " + mon.getName() + " swings down on you, but you quickly dodge to the side. You escape in time before the " + mon.getName() + "can land another hit.");
    //          return;
    //        }
    //        else if (fleeChance < 5) {
    //          System.out.println("\nYou begin to escape, but the " + mon.getName() + " slashes down at you one time and lands a hit before you escape.");
    //          characterAttack(mon, hero, 1, 0, useShield());
    //          return;
    //        }
    //        else {
    //          System.out.println("You failed to escape!");
    //        }
    //      }
    //    }
    //    if (mc.isAlive()) {
    //      return true;
    //    }
    //  }
    //}

    // public void weaponChoice(int key) {
    //   int weaponCount = 3;
    //   String s = "\nWhich weapon will you use?\n";
    //   s += "\t1: Back\n";
    //   s += "\t2: Fist\tPower: 1\n";
    //   issaSword.clear();
    //   for (int j = 0; j < inventory.size(); j++) {
    //   if (inventory.get(j) instanceof Sword) {
    //     issaSword.add(j); // adds inventory index
    //     s += "\t" + weaponCount + ": " + displayInventoryItem(j) + "\n";
    //     weaponCount += 1;
    //   }
    // }
    //   if (key == 1) {
    //     return;
    //   }
    //   else if (key == 2) {
    //     attackOrder(0);
    //   }
    //   else if (itemChoice > 2 && itemChoice < 7) {
    //   attack(inventory.get(issaSword.get(itemChoice - 3)).getPower()); // deal damage
    //   useItem(issaSword.get(itemChoice - 3)); // reduce durability
    // }
    // }

    public boolean attackOrder(Character hero, Character mon) { // manages attack order
      int order = (int) (Math.random() * 2);
      if (order == 0) { // player attacks first
          //characterAttack(hero, mon);
          if (mon.isAlive()) {
            //characterAttack(mon, hero);
          }
      }
      else if (order == 1) { // monster attacks first
          //characterAttack(mon, hero);
          if (mon.isAlive()) {
            //characterAttack(hero, mon);
          }
      }
      if (hero.isAlive()) {
        return true;
      }
      return false;
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
        if (mc.isAlive() && ifEnd()) { // generate another floor
          nextFloor();
        }
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

      // // creates the monsters in a room
      // while (monsters.size() != monsterCount) {
      //   // do we need to reset monsters.size()?
      //   int monsterR = temp.randNum(1, rows);
      //   int monsterC = temp.randNum(1, cols);
      //   if(isRoom(monsterR, monsterC)) {
      //     monster = new Monster(100, 10, 1, monsterR, monsterC, currentFrame);
      //     monsters.add(monster);
      //   }

      // }

      // creates an exit tile
      while (!exitPlaced){
        int exitR = temp.randNum(1, rows);
        int exitC = temp.randNum(1, cols);
        if (isRoom(exitR, exitC)){
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

    //public String[][] displayZone(){
    //  int left = mc.getC() - 10;
    //  int right = mc.getC() + 10;
    //  int up = mc.getR() - 10;
    //  int down = mc.getR() + 10;

    //  String[][] output = new String[21][21];

    //  int counterL = 0;
    //  int counterR = 0;
    //  int counterU = 0;
    //  int counterD = 0;

    //  if(left < 0){
    //      counterL = Math.abs(left);
    //      for(int i = 0; i < output.length; i++){
    //        for(int h = 0; h < counterL; h++){
    //          output[i][h] = "#";
    //        }
    //      }
    //    }
    //    else if(right > output.length){
    //      counterR = right-output.length-1;
    //      for(int i = 0; i < output.length; i++){
    //        for(int h = output.length; h > output[0].length-counterR; h--){
    //          output[i][h] = "#";
    //        }
    //      }
    //    }
    //    if(up < 0){
    //      counterU = Math.abs(up);
    //      for(int j = 0; j < counterU; j++){
    //        for(int z = 0; z < output[0].length; z++){
    //          output[j][z] = "#";
    //        }
    //      }
    //    }
    //    else if(down > output[0].length){
    //      counterD = down - output[0].length-1;
    //      for(int j = output.length; j > output.length-counterD; j--){
    //        for(int z = 0; z < output[0].length; z++){
    //          output[j][z] = "#";
    //        }
    //      }
    //    }

    //  for(int x = left+counterL; x < right-counterR; x++){
    //    for(int y = up+counterU; y < down-counterD; y++){
    //      output[x][y] = currentFrame.getPos(x,y);
    //    }
    //  }
    //  return output;
    //}

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
