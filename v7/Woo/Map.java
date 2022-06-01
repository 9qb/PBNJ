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
    private int width;
    private int length;

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
        width = 81;
        length = 144;
        MazeGenerator troll = new MazeGenerator(width, length);
        maze = new Maze(troll.getGeneratedMaze());
        // boolean isTrue = false;
        // for(int i =0; i < heroPosFinder.length-1;i++){
        //   for(int e =0; e < heroPosFinder[0].length-1; e++){
        //     if(heroPosFinder[i][e].equals(" ") || heroPosFinder[i][e].equals("!")){
        //         isTrue = true;
        //         mc.getC() = i;
        //         mc.getR() = e;
        //       break;
        //     }
        //     if(isTrue){break;}
        //   }
        // }
        currentFrame = maze;

        // creates the hero in a room
        while (mc == null) {
          int heroC = troll.randNum(1, width);
          int heroR = troll.randNum(1, length);
          if(isRoom(mc.getC(), mc.getR())) {
            // isTrue = true;
            mc = new Hero(100, 10, 1, mc.getC(), mc.getR());
            break;
          }
        }

        // creates the monsters in a room
        while (monsters.size() != monsterCount) {
          int monsterC = troll.randNum(1, width);
          int monsterR = troll.randNum(1, length);
          if(isRoom(monsterX, monsterY)) {
            monster = new Monster(100, 10, 1, mc.getC(), mc.getR());
            monsters.add(monster);
          }
        }

        //for(int i =0; i < heroPosFinder.length-1;i++){
        //  for(int e =0; e < heroPosFinder[0].length-1; e++){
        //    if((heroPosFinder[i][e].equals(" ") || heroPosFinder[i][e].equals("!") && !isTrue)){
        //        isTrue = true;
        //        mc.getC() = i;
        //        mc.getR() = e;
        //      break;
        //    }
        //    if(isTrue){break;}
        //  }
        //}
        currentFrame.setPos(mc.getC(), mc.getR(), hero);
    }

    // player move
    public void playerTurn(String key) {
      int mcC = mc.getC(); // stores current C tile
      int mcR = mc.getR(); // stores current R tile
      // player movement
      if (key == "W") {
        if (moveUp()) {
          mc.updLastTile(mcC, mcR);
          mc.moveUp();
        }
      }
      else if (key == "A") {
        if (moveLeft()) {
          mc.updLastTile(mcC, mcR);
          mc.moveLeft();
        }
      }
      else if (key == "S") {
        if (moveDown()) {
          mc.updLastTile(mcC, mcR);
          mc.moveDown();
        }
      }
      else if (key == "D") {
        if (moveRight()) {
          mc.updLastTile(mcC, mcR);
          mc.moveRight();
        }
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
        Character temp = current;
        battleChoice(mc, temp);
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
        int choice = (int) Math.random() * 4;
        boolean moved = false;
        while (!moved) {
          if (choice = 0 && maze.getPos(monC-1, monR)) {
            mon.updLastTile(monC, monR);
            mc.moveUp();
            moved = true;
          }
          else if (choice = 1 && maze.getPos(monC, monR-1)) {
            mmon.updLastTile(monC, monR);
            mc.moveLeft();
            moved = true;
          }
          else if (choice = 2 && maze.getPos(monC+1, monR)) {
            mmon.updLastTile(monC, monR);
            mc.moveDown();
            moved = true;
          }
          else if (choice = 3 && maze.getPos(monC, monR+1)) {
            mmon.updLastTile(monC, monR);
            mc.moveRight();
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
        Character temp = current;
        battleChoice(mc, temp);
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

    public void battleChoice(int key) {
      if (battlePhase == true) {
        while( mc.isAlive() && mon.isAlive() ) {
          System.out.println( "\nWhat is your choice?" );
          System.out.println( "\t1: Attack\n\t2: Use Item\n\t3: Flee\nSelection: " );
          if ( key == 1 ) {
            weaponChoice();
          }
          else if ( key == 2 ) {
            if (!(useItem())) {
              battleChoice();
            }
            else {
            characterAttack(mon, hero); // no attack weapon
            }
          }
          else if ( key == 3 ) {
            int fleeChance = (int) (Math.random() * 10);
            if (fleeChance < 3) {
              System.out.println("\nThe " + mon.getName() + " swings down on you, but you quickly dodge to the side. You escape in time before the " + mon.getName() + "can land another hit.");
              return;
            }
            else {
              System.out.println("\nYou begin to escape, but the " + mon.getName() + " slashes down at you one time and lands a hit before you escape.");
              characterAttack(mon, hero, 1, 0, useShield());
              return;
            }
          }
        }
        if (hero.isAlive()) {
          return true;
        }
      }
    }

    public boolean attackOrder(Character hero, Character mon) { // manages attack order
      int order = troll.randNum(0, 2);
      if (order == 0) { // player attacks first
          characterAttack(hero, mon);
          if (mon.isAlive()) {
            characterAttack(mon, hero);
          }
      }
      else if (order == 1) { // monster attacks first
          characterAttack(mon, hero);
          if (mon.isAlive()) {
            characterAttack(hero, mon);
          }
      }
      if (hero.isAlive()) {
        return true;
      }
      return false;
    }

    public void round(String key) {
      if (battlePhase == false) {
        playerTurn(key);
        if (mc.isAlive()) {
          monsterTurn();
        }
        if (mc.isAlive() && !ifEnd()) { // generate another floor
          nextFloor();
        }
        else if (!mc.isAlive()) {
          dead();
        }
      }
    }

    // helper method for play()
    public boolean ifEnd() { // if hero is on end tile
      return maze.getPos(mc.getC(), mc.getR()).equals("*");
    }

    public void nextFloor() { // how to generate next floor
      System.out.println("You cleared the floor!");
      System.out.println("Current score: " + score);
      System.out.println("Generating next stage ...");
      System.out.println("...");
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
        if(currentFrame.getPos(mc.getC()-1, mc.getR()).equals(" ") || currentFrame.getPos(mc.getC(), mc.getR()).equals("!")){
            currentFrame.setPos(mc.getC(), mc.getR()," ");
            mc.changeX(1);
            currentFrame.setPos(mc.getC(), mc.getR(), hero);
            return true;
        } else {
          return false;
        }
    }
    public boolean moveRight() {
        if(currentFrame.getPos(mc.getC(), mc.getR()+1).equals(" ") || currentFrame.getPos(mc.getC(), mc.getR()+1).equals("!")){
            currentFrame.setPos(mc.getC(), mc.getR()," ");
            mc.changeY(1);
            currentFrame.setPos(mc.getC(), mc.getR(), hero);
            return true;
        } else {
          return false;
        }
    }
    public boolean moveDown() {
        if(currentFrame.getPos(mc.getC()+1, mc.getR()).equals(" ") || currentFrame.getPos(mc.getC()+1, mc.getR()).equals("!")){
            currentFrame.setPos(mc.getC(), mc.getR()," ");
            mc.changeX(1);
            currentFrame.setPos(mc.getC(), mc.getR(), hero);
            return true;
        } else {
          return false;
        }
    }
    public boolean moveLeft() {
        if(currentFrame.getPos(mc.getC(), mc.getR()-1).equals(" ") || currentFrame.getPos(mc.getC(), mc.getR()-1).equals("!")){
            currentFrame.setPos(mc.getC(), mc.getR()," ");
            mc.changeY(1);
            currentFrame.setPos(mc.getC(), mc.getR(), hero);
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
        int topLeftX = mc.getC() - 10;
        int topLeftY = mc.getR() - 10;

        int currX = 0;
        int currY = 0;

        topLeftX = Math.max(0, topLeftX);
        topLeftY = Math.max(0, topLeftY);

        topLeftX = Math.min(topLeftX, width-21);
        topLeftY = Math.min(topLeftY, length-21);

        String[][] output = new String[21][21];
        for(int i = topLeftX; i < topLeftX + 21; i++){
            for(int e = topLeftY; e < topLeftY + 21; e++){
                output[currX][currY] = currentFrame.getPos(i,e);
                //System.out.print(output[currX][currY]);
                currY++;
            }
            //System.out.println("");
            currY = 0;
            currX++;
        }

      return output;

    }

    public boolean isRoom(int x, int y) {
        // returns true if coordinate is in a room
        return currentFrame.getMaze()[x-1][y-1].equals(" ") &&
               currentFrame.getMaze()[x][y-1].equals(" ") &&
               currentFrame.getMaze()[x+1][y-1].equals(" ") &&
               currentFrame.getMaze()[x-1][y].equals(" ") &&
               currentFrame.getMaze()[x][y].equals(" ") &&
               currentFrame.getMaze()[x+1][y].equals(" ") &&
               currentFrame.getMaze()[x-1][y+1].equals(" ") &&
               currentFrame.getMaze()[x][y+1].equals(" ") &&
               currentFrame.getMaze()[x+1][y+1].equals(" ");

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
