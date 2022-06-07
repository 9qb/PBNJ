import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.LinkedList;

public class Character {
  protected int health;
  protected int attack;
  protected int speed;
  protected int currentR;
  protected int currentC;
  protected int baseDamage;
  protected int defense;
  protected String lastTile;
  protected Maze dungeon;
  protected String[][] map;
  protected LinkedList<Weapon>;

  // for terminal testing
  protected Scanner sc = new Scanner(System.in);

  public Character() {
   health = 4;
   baseDamage = 3;
   defense = 0;
  }

  public Character(int newHealth, int newAttack, int newSpeed, int newR, int newC, Maze maze) {
   health = newHealth;
   attack = newAttack;
   speed = newSpeed;
   currentR = newR;
   currentC = newC;
   dungeon = maze;
   map = dungeon.getMaze();
   lastTile = " ";
   inventory = new LinkedList<Weapon>();
   inventory.add("Fist", -1, 1);
  }

  public int getR() {
    return currentR;
  }

  public int getC() {
    return currentC;
  }

  public void changeR(int value) {
    currentR += value;
  }

  public void changeC(int value) {
    currentC += value;
  }

  public int getHealth() {
   return health;
  }

  public int getAtk() {
   return attack;
  }

  public int getSpeed() {
   return speed;
  }

  public boolean isAlive() { // character is alive
   return health > 0;
  }

  public void replaceTile(int r, int c) {
    dungeon.setPos(currentR, currentC, lastTile);
    lastTile = dungeon.getPos(r, c);
  }

  public String lastTile(){
    return lastTile;
  }

  public void lastTileToSpace(){
    lastTile = " ";
  }

  public void addHealth(int value) {
   health += value;
  }

  public void subtractHealth(int value) {
   health -= value;
  }

  public void moveUp() {
    if (currentR > 0 && currentR < map.length-1 && currentC > 0 && currentC < map[0].length){
      replaceTile(currentR-1, currentC);
      currentR -= 1;
    }
  }

  public void moveRight() {
    if (currentR > 0 && currentR < map.length-1 && currentC > 0 && currentC < map[0].length){
      replaceTile(currentR, currentC+1);
      currentC += 1;
    }
  }

  public void moveDown() {
    if (currentR > 0 && currentR < map.length-1 && currentC > 0 && currentC < map[0].length){
      replaceTile(currentR+1, currentC);
      currentR += 1;
    }
  }

  public void moveLeft() {
    if (currentR > 0 && currentR < map.length-1 && currentC > 0 && currentC < map[0].length){
      replaceTile(currentR, currentC-1);
      currentC -= 1;
    }
  }

  public String getName() {
    return "";
  }

  public void characterAttack(Character attacked, int weaponPower, in durability) {
    int dmg = attack + weaponPower;
    if (dmg < 0) {
      dmg = 0;
    }
    attacked.subtractHealth(dmg);
    //System.out.println( "\n" + attacker.getName() + " dealt " + dmg + " damage.");
    System.out.println(attacked.getName() + "\tHealth: " + attacked.getHealth() + "\tAttack: " + attacked.getAtk());
  }

  public boolean chooseMove(Character attacked){ // returns whether player escapes successfully
    int i = 1;
    System.out.println( "\nWhat is your choice?" );
    System.out.println( "\t1: Attack\n\t2: Flee\n\t3: Change Weapon: Selection: " );

    try{
      i = Integer.parseInt( sc.nextLine() );
    }
    catch(Exception e){
      System.out.println("Invalid input. Please enter an integer.");
      chooseMove(attacked);
    }

    if ( i == 1 ) {
      characterAttack(attacked, 1);
    }
    else if ( i == 2 ) {
      int fleeChance = (int) (Math.random() * 2);
      if (fleeChance == 0) {
        System.out.println("\nThe Monster swings down on you, but you quickly dodge to the side. You escape in time before the Monster can land another hit.");
        return true;
      }
      else {
        System.out.println("\nYou begin to escape, but the Monster stops you in your tracks.");
        return false;
      }
    }
    else if ( i == 3 ) {
      int inventoryIdx = weapon();
      characterAttack (attacked, inventory.get(inventoryIdx).getPower(), inventory.get(inventoryIdx).getDurability());
    }
    return false;
  }

  public int weapon() { // returns index of chosen inventory weapon
    weaponChoice = 0;
    String s = "Which weapon will you use?";
    for (int i = 0; i < inventory.size(); i++) {
      if (inventory.get(i) instaceOf Weapon) {
        s += (i + 1) + ": " + inventory.get(i).getName() + ", Attack Power: " + inventory.get(i).getPower() + ", Durability: " + inventory.get(i).getDurability() + "\n";
      }
    }
    s += "Selection: ";
    System.out.println(s);

    try{
      weaponChoice = Integer.parseInt( sc.nextLine() );
    }
    catch(Exception e){
      System.out.println("Invalid input. Please enter an integer.");
      weapon();
    }

    if (weaponChoice == 1) {
      return 0;
    }
    if (weaponChoice > 1 && weaponChoice < 5) { // selection between integers 1 - 4
      retun weaponChoice - 1;
    }
  }
}
