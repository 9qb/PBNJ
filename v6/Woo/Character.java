public class Character {
  protected int health;
  protected int baseDamage;
  protected int defense;

  public Character() {
    health = 4;
    baseDamage = 3;
    defense = 0;
  }

  public Character(int newHealth, int newBaseDamage, int newDefense) {
    health = newHealth;
    baseDamage =  newBaseDamage;
    defense = newDefense;
  }

  public int getHealth() {
    return health;
  }

  public int getDefense() {
    return defense;
  }

  public int getAttack() {
    return baseDamage;
  }

  public boolean isAlive() {
    return health > 0;
  }

  public void addHealth(int value) {
    health += value;
  }

  public void subtractHealth(int value) {
    health -= value;
  }
}
