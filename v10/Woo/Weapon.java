public class Weapon {
  protected String name;
  protected int weaponId;
  protected int durability;
  protected int power;

  public Weapon() {
    this.name = "Weapon";
    this.durability = 100;
    this.power = 1;
  }

  public Weapon(String name, int durability, int power) {
    this.name = name;
    this.durability = durability;
    this.power = power;
  }

  public String getName () {
    return name;
  }

  public int getId () {
    return weaponId;
  }

  public int getDurability () {
    return durability;
  }

  public void reduceDurability (int num) {
    durability -= num;
  }

  public int getPower () {
    return power;
  }
}
