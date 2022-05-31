public class Hero extends Character {
  public int gems;

  public Hero() {
    super();
  }

  public Hero(int newHealth, int newBaseDamage, int newDefense) {
    super(newHealth, newBaseDamage, newDefense);
  }

  public void setHealth(int fullHealth) {
    health = fullHealth;
  }
}
