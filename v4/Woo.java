// (P)BNJ – Brian Li, Nakib Abedin, Jefford Shau
// APCS pd07
// FP – Dungeon Crawler
// 2022-05-28

import java.util.Scanner;

public class Woo {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Map map = new Map();
        System.out.println(map);

        while(sc.hasNextLine()){
            String motion = sc.nextLine();
            if(motion.equalsIgnoreCase("W")){
                map.moveUp();
                System.out.println(map);
            }else if(motion.equalsIgnoreCase("A")){
                map.moveLeft();
                System.out.println(map);
            }else if(motion.equalsIgnoreCase("S")){
                map.moveDown();
                System.out.println(map);
            }else if(motion.equalsIgnoreCase("D")){
                map.moveRight();
                System.out.println(map);
            }else{
                System.out.println(map);
                System.out.println("invalid input");
            }
        }




    }
}
