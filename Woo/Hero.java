public class Hero extends Character {
  private String input;

  public Hero() {
    super();
  }

  public Hero(int newHealth, int newAttack, int newSpeed, int newR, int newC, String[][] maze) {
   super(newHealth, newAttack, newSpeed, newR, newC, maze);
  }

  public String getName() {
    return "Hero";
  }
}
