public class Map{

    private String hero = "X";
    private int heroX;
    private int heroY;
    private int width;
    private int length;

    private static void clear()
    {
      System.out.println("\033[2J");
      System.out.println("\033[" + 1 + ";" + 1 + "H");
    }

    private Maze maze;
    private Maze currentFrame;

    public Map(){
        width = 81;
        length = 144;
        MazeGenerator troll = new MazeGenerator(width, length);
        maze = new Maze(troll.getGeneratedMaze());
        String[][] heroPosFinder = maze.getMaze();
        boolean isTrue = false;
        // for(int i =0; i < heroPosFinder.length-1;i++){
        //   for(int e =0; e < heroPosFinder[0].length-1; e++){
        //     if(heroPosFinder[i][e].equals(" ") || heroPosFinder[i][e].equals("!")){
        //         isTrue = true;
        //         heroX = i;
        //         heroY = e;
        //       break;
        //     }
        //     if(isTrue){break;}
        //   } 
        // }
        currentFrame = maze;
        
        int b = 0;
        while (true){
            heroX = troll.randNum(1,width);
            heroY = troll.randNum(1,length);
            if(isRoom(heroX, heroY)){
                isTrue = true;
                break;
            }
            b++;
        }

        //for(int i =0; i < heroPosFinder.length-1;i++){
        //  for(int e =0; e < heroPosFinder[0].length-1; e++){
        //    if((heroPosFinder[i][e].equals(" ") || heroPosFinder[i][e].equals("!") && !isTrue)){
        //        isTrue = true;
        //        heroX = i;
        //        heroY = e;
        //      break;
        //    }
        //    if(isTrue){break;}
        //  } 
        //}

        
        currentFrame.setPos(heroX, heroY, hero);
    }

    public String toString(){
        clear();
        return currentFrame.toString();
    }

    public void moveUp(){
        if(currentFrame.getPos(heroX-1, heroY).equals(" ") || currentFrame.getPos(heroX-1, heroY).equals("!")){
            currentFrame.setPos(heroX, heroY," ");
            heroX -= 1;
            currentFrame.setPos(heroX, heroY, hero);
        }
    }
    public void moveRight(){
        if(currentFrame.getPos(heroX, heroY+1).equals(" ") || currentFrame.getPos(heroX, heroY+1).equals("!")){
            currentFrame.setPos(heroX, heroY," ");
            heroY += 1;
            currentFrame.setPos(heroX, heroY, hero);
        }
    }
    public void moveDown(){
        if(currentFrame.getPos(heroX+1, heroY).equals(" ") || currentFrame.getPos(heroX+1, heroY).equals("!")){
            currentFrame.setPos(heroX, heroY," ");
            heroX += 1;
            currentFrame.setPos(heroX, heroY, hero);
        }
    }
    public void moveLeft(){
        if(currentFrame.getPos(heroX, heroY-1).equals(" ") || currentFrame.getPos(heroX, heroY-1).equals("!")){
            currentFrame.setPos(heroX, heroY," ");
            heroY -= 1;
            currentFrame.setPos(heroX, heroY, hero);
        }
    }
    
    public String[][] getMaze(){
      return maze.getMaze();
    }
    
    //public String[][] displayZone(){
    //  int left = heroX - 10;
    //  int right = heroX + 10;
    //  int up = heroY - 10;
    //  int down = heroY + 10;
     
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
        int topLeftX = heroX - 10;
        int topLeftY = heroY - 10;

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
