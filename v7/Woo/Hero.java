public class Hero extends Character {
  private String input;

  public Hero() {
    super();
  }

  public Hero(int newHealth, int newAttack, int newSpeed, int newC, int newR, String[][] maze) {
   super(newHealth, newAttack, newSpeed, newC, newR, maze);
  }

  public String getName() {
    return "Hero";
  }
}
