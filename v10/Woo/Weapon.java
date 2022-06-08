public class Weapon {
  protected String name;
  protected int weaponId;
  protected int durability;
  protected int power;

  protected final String[] ADJECTIVES = {"Amazing", "Huge", "Big", "Overwhelming", "Powerful", "Overpowered", "Kinda Good", "Great", "S Tier", "Good", "Agile", "Adept", "PagMan", "Cool", "Fine"};
  protected final String[] NOUNS = {"Axe", "Sword", "Battleaxe", "Stick", "Meatstick", "Rock", "Staff", "Hatchet", "Knife", "Shank", "Slipper", "Shoe"};

  public Weapon() {
    this.name = ADJECTIVES[MazeGenerator.randNum(0, ADJECTIVES.length)] + " " + NOUNS[MazeGenerator.randNum(0, NOUNS.length)];
    this.durability = MazeGenerator.randNum(20,30);
    this.power = MazeGenerator.randNum(5, 25);
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
