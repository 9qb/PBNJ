public class Monster extends Character {

 public Monster() {
   super();
 }

 public Monster(int newHealth, int newAttack, int newSpeed, int newC, int newR, String[][] maze) {
   super(newHealth, newAttack, newSpeed, newC, newR, maze);
 }

 public String getName() {
   return "Monster";
 }
}
