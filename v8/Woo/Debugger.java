import java.util.Scanner;

public class Debugger{
  public static void main(String[] args){

    Scanner sc = new Scanner(System.in);
    Map map = new Map();
    System.out.println(map);

    map.processTile();

    while(sc.hasNextLine()){
      String motion = sc.nextLine();
      if(motion.equalsIgnoreCase("W")){
        map.moveUp();
        System.out.println(map);
        //System.out.println(map.getR() + " , " + map.getC());
      }else if(motion.equalsIgnoreCase("A")){
        map.moveLeft();
        System.out.println(map);
        //System.out.println(map.getR() + " , " + map.getC());
      }else if(motion.equalsIgnoreCase("S")){
        map.moveDown();
        System.out.println(map);
        //System.out.println(map.getR() + " , " + map.getC());
      }else if(motion.equalsIgnoreCase("D")){
        map.moveRight();
        System.out.println(map);
        //System.out.println(map.getR() + " , " + map.getC());
      }else{
        System.out.println(map);
        System.out.println("invalid input");
      }
    }

  }
}
