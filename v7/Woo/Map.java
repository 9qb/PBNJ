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
        //         mc.getX() = i;
        //         mc.getY() = e;
        //       break;
        //     }
        //     if(isTrue){break;}
        //   }
        // }
        currentFrame = maze;

        // creates the hero in a room
        while (mc == null) {
          int heroX = troll.randNum(1, width);
          int heroY = troll.randNum(1, length);
          if(isRoom(mc.getX(), mc.getY())) {
            // isTrue = true;
            mc = new Hero(100, 10, 1, mc.getX(), mc.getY());
            break;
          }
        }

        // creates the monsters in a room
        while (monsters.size() != monsterCount) {
          int monsterX = troll.randNum(1, width);
          int monsterY = troll.randNum(1, length);
          if(isRoom(monsterX, monsterY)) {
            monster = new Monster(100, 10, 1, mc.getX(), mc.getY());
            monsters.add(monster);
          }
        }

        //for(int i =0; i < heroPosFinder.length-1;i++){
        //  for(int e =0; e < heroPosFinder[0].length-1; e++){
        //    if((heroPosFinder[i][e].equals(" ") || heroPosFinder[i][e].equals("!") && !isTrue)){
        //        isTrue = true;
        //        mc.getX() = i;
        //        mc.getY() = e;
        //      break;
        //    }
        //    if(isTrue){break;}
        //  }
        //}
        currentFrame.setPos(mc.getX(), mc.getY(), hero);
    }

    // player move
    public void playerTurn(String key) {
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

    // monster move
    // public void monsterTurn() {
    //
    // }


    public void round(String key) {
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

    // helper method for play()
    public boolean ifEnd() { // if hero is on end tile
      return maze.getPos(mc.getX(), mc.getY()).equals("*");
    }

    public void nextFloor() { // how to generate next floor
      System.out.println("You cleared the floor!");
      System.out.println("Current score: " + score);
      System.out.println("Generating next stage ...");
      System.out.println("...");
    }

    public void dead() {
      System.out.println("You Died!");
      System.out.println("Final Score: " + score);
    }

    public String toString(){
        clear();
        return currentFrame.toString();
    }

    // player movement
    public void moveUp(){
        if(currentFrame.getPos(mc.getX()-1, mc.getY()).equals(" ") || currentFrame.getPos(mc.getX(), mc.getY()).equals("!")){
            currentFrame.setPos(mc.getX(), mc.getY()," ");
            mc.changeX(1);
            currentFrame.setPos(mc.getX(), mc.getY(), hero);
        }
    }
    public void moveRight(){
        if(currentFrame.getPos(mc.getX(), mc.getY()+1).equals(" ") || currentFrame.getPos(mc.getX(), mc.getY()+1).equals("!")){
            currentFrame.setPos(mc.getX(), mc.getY()," ");
            mc.changeY(1);
            currentFrame.setPos(mc.getX(), mc.getY(), hero);
        }
    }
    public void moveDown(){
        if(currentFrame.getPos(mc.getX()+1, mc.getY()).equals(" ") || currentFrame.getPos(mc.getX()+1, mc.getY()).equals("!")){
            currentFrame.setPos(mc.getX(), mc.getY()," ");
            mc.changeX(1);
            currentFrame.setPos(mc.getX(), mc.getY(), hero);
        }
    }
    public void moveLeft(){
        if(currentFrame.getPos(mc.getX(), mc.getY()-1).equals(" ") || currentFrame.getPos(mc.getX(), mc.getY()-1).equals("!")){
            currentFrame.setPos(mc.getX(), mc.getY()," ");
            mc.changeY(1);
            currentFrame.setPos(mc.getX(), mc.getY(), hero);
        }
    }

    public String[][] getMaze(){
      return maze.getMaze();
    }

    //public String[][] displayZone(){
    //  int left = mc.getX() - 10;
    //  int right = mc.getX() + 10;
    //  int up = mc.getY() - 10;
    //  int down = mc.getY() + 10;

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

    public String[][] displayZone(){
        int topLeftX = mc.getX() - 10;
        int topLeftY = mc.getY() - 10;

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

    public boolean isRoom(int x, int y)
    {
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
