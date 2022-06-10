// PBNJ - Brian Li, Nakib Abedin, Jefford Shau
// APCS pd07
// Final Project -- Dungeon Crawler
// 2022-06-10

import java.util.Scanner;

public class Woo{
  private void opener(Scanner sc){
    TerminallyIll animations = new TerminallyIll();
    animations.go(1,1);
    System.out.println(animations.CLEAR_SCREEN);
    Map.clear();

    System.out.println(" ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______");
    animations.wait(50);
    System.out.println("|______|______|______|______|______|______|______|______|______|______|______|______|______|");
    animations.wait(50);
    System.out.println("    /\\                       (_)             |  __ \\                                       ");
    animations.wait(50);
    System.out.println("   /  \\   _ __ ___   __ _ _____ _ __   __ _  | |  | |_   _ _ __   __ _  ___  ___  _ __     ");
    animations.wait(50);
    System.out.println("  / /\\ \\ | '_ ` _ \\ / _` |_  / | '_ \\ / _` | | |  | | | | | '_ \\ / _` |/ _ \\/ _ \\| '_ \\    ");
    animations.wait(50);
    System.out.println(" / ____ \\| | | | | | (_| |/ /| | | | | (_| | | |__| | |_| | | | | (_| |  __/ (_) | | | |   ");
    animations.wait(50);
    System.out.println("/_/    \\_\\_| |_| |_|\\__,_/___|_|_| |_|\\__, | |_____/ \\__,_|_| |_|\\__, |\\___|\\___/|_| |_|   ");
    animations.wait(50);
    System.out.println("                                       __/ |                      __/ |                      ");
    animations.wait(50);
    System.out.println(" ______ ______ ______ ______ ______ ___|___/_____ ______ ______ __|___/______ ______ ______ ");
    animations.wait(50);
    System.out.println("|______|______|______|______|______|______|______|______|______|______|______|______|______|");

    animations.wait(100);
    System.out.println("\nPress 1 to play");
    System.out.println("Press 2 to read instructions");

    int motion;
    while(sc.hasNextLine()){
      try{
        motion = Integer.parseInt(sc.nextLine());
        if(motion == 1){
          play(sc);
          System.out.println(animations.CLEAR_SCREEN);
          animations.go(1,1);
        }
        else if(motion == 2){instructions(sc);}
        else{
          System.out.println("Sorry, invalid input.");
        }
      }
      catch(Exception e){
        System.out.println("Sorry, invalid input.");
      }
    }
  }

  private void instructions(Scanner sc){

    System.out.println("\n===================================================");
    System.out.println("INSTRUCTIONS\n");
    System.out.println("The rules are simple: survive as long as you can.");
    System.out.println();
    System.out.println("Use your WASD keys to move around the screen.");
    System.out.println();
    System.out.println("If you're looking for help, here are some tips:");
    System.out.println("- The T tiles represent treasure. Look to them to find some loot with your journey.");
    System.out.println("- The H tiles represent magical healing tiles. Seems like they help you regenerate your HP.");
    System.out.println("- The M tiles are spooky monsters. Step to them and see yourself enter a battle.");
    System.out.println("- The E tile is what you're looking for: escape. But maybe you'll only end up in another floor?");
    System.out.println();
    System.out.println("Play and see your score increase. When you perish, that is what you will be known for.");
    System.out.println();
    System.out.println("Do you understand? Press 0 to go back.");
    System.out.println("===================================================");

    String motion = sc.nextLine();

    if(motion.equals("0")){
      opener(sc);
    }
    else{
      System.out.println("Sorry, please press 0 to go back.");
      instructions(sc);
    }
  }

  private void play(Scanner sc){
    // Scanner used for input
    Map map = new Map();
    map.clear();
    System.out.println(map);

    // takes in player input for hero movement
    while(sc.hasNextLine()){
      String motion = sc.nextLine();
      if(motion.equalsIgnoreCase("W")){
        map.round("W");
        map.clear();
        System.out.println(map);
        //System.out.println(map.getR() + " , " + map.getC());
      }else if(motion.equalsIgnoreCase("A")){
        map.round("A");
        map.clear();
        System.out.println(map);
        //System.out.println(map.getR() + " , " + map.getC());
      }else if(motion.equalsIgnoreCase("S")){
        map.round("S");
        map.clear();
        System.out.println(map);
        //System.out.println(map.getR() + " , " + map.getC());
      }else if(motion.equalsIgnoreCase("D")){
        map.round("D");
        map.clear();
        System.out.println(map);
        //System.out.println(map.getR() + " , " + map.getC());
      }else{
        map.clear();
        System.out.println(map);
        System.out.println("invalid input");
      }
    }
  }


  public static void main(String[] args){
    Woo game = new Woo();
    Scanner scanner = new Scanner(System.in);
    game.opener(scanner);
  }
}
