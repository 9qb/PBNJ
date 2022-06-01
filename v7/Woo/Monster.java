public class Monster extends Character {

 public Monster() {
   super();
 }

 public Monster(int newHealth, int newAttack, int newSpeed, int newC, int newR) {
   super(newHealth, newAttack, newSpeed, newC, newR);
 }

 public String getName() {
   return "Monster";
 }
}
