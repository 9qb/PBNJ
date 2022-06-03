public class Monster extends Character {

 public Monster() {
   super();
 }

 public Monster(int newHealth, int newAttack, int newSpeed, int newR, int newC, String[][] maze) {
   super(newHealth, newAttack, newSpeed, newR, newC, maze);
 }

 public String getName() {
   return "Monster";
 }
}
