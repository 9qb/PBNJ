import java.util.LinkedList;

public class Hero extends Character {
  private String input;

  public Hero() {
    super();
  }

  public Hero(int newHealth, int newAttack, int newSpeed, int newR, int newC, Maze maze) {
   super(newHealth, newAttack, newSpeed, newR, newC, maze);
  }

  public Hero(int newHealth, int newAttack, int newSpeed, int newR, int newC, LinkedList<Weapon> newInventory, Maze maze) {
   super(newHealth, newAttack, newSpeed, newR, newC, newInventory, maze);
  }

  public String getName() {
    return "Hero";
  }
}
