public class Item {
  protected String name;
  protected int itemId;
  protected int durability;
  protected int power;

  public Item() {
    this.name = "Item";
    this.itemId = 100;
    this.durability = 100;
    this.power = 1;
  }

  public Item(String name, int id, int durability, int power) {
    this.name = name;
    this.itemId = id;
    this.durability = durability;
    this.power = power;
  }

  public String getName () {
    return name;
  }

  public int getId () {
    return itemId;
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
