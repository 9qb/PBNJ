// PBNJ - Brian Li, Nakib Abedin, Jefford Shau
// APCS pd07
// Final Project -- Dungeon Crawler
// 2022-06-10

import java.util.Scanner;
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
  protected LinkedList<Weapon> inventory;
  protected Weapon lastUsedWeapon;

  // for terminal testing
  protected Scanner sc = new Scanner(System.in);

  // default const, should not be used
  public Character() {
   health = 4;
   baseDamage = 3;
   defense = 0;
  }

  // overloaded const, used in initial generation of the first level
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
   inventory.add(new Weapon("Fist", -1, 1));
   lastUsedWeapon = inventory.getFirst();
  }

  // overloaded const, used in generation of following levels
  public Character(int newHealth, int newAttack, int newSpeed, int newR, int newC, LinkedList<Weapon> newInventory, Weapon lastUsedWpn, Maze maze){
    health = newHealth;
    attack = newAttack;
    speed = newSpeed;
    currentR = newR;
    currentC = newC;
    dungeon = maze;
    map = dungeon.getMaze();
    lastTile = " ";
    inventory = newInventory;
    lastUsedWeapon = lastUsedWpn;
  }

  // ACCESSOR & MODIFIER METHODS
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

  public LinkedList<Weapon> getInventory(){
    return inventory;
  }

  // return if alive
  public boolean isAlive() {
   return health > 0;
  }

  // sets tile on the Dungeon to the original tile before the character steps on it
  public void replaceTile(int r, int c) {
    dungeon.setPos(currentR, currentC, lastTile);
    lastTile = dungeon.getPos(r, c);
  }

  public String lastTile(){
    return lastTile;
  }

  // sets lastTile to space, used when the character steps onto a tile with a one-time use
  public void lastTileToSpace(){
    lastTile = " ";
  }

  public void addHealth(int value) {
   health += value;
  }

  public void subtractHealth(int value) {
   health -= value;
   if (health < 0){
     health = 0;
   }
  }

  // adds weapon to inventory
  public void addWeapon(Weapon weapon){
    inventory.add(weapon);
    System.out.println("You found a " + weapon.getName() + ". Enjoy it!");
  }

  // charater movement methods
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
    return "Hero";
  }

  // character attacking system,
  // changes weapon Durability
  public void characterAttack(Character attacked, Weapon weapon){
    int dmg = attack + weapon.getPower();
    weapon.reduceDurability(1);
    if (dmg < 0){
      dmg = 0;
    }
    attacked.subtractHealth(dmg);
    System.out.println("Attacked using " + lastUsedWeapon.getName() + "! Dealt " + dmg + " damage.");
    System.out.println(attacked.getName() + "\tHealth: " + attacked.getHealth() + "\tAttack: " + attacked.getAtk());

    if (weapon.getDurability() == 0){
      System.out.println("Snap! Your " + weapon.getName() + " has broken! Unlucky.");
      inventory.remove(weapon);
      lastUsedWeapon = inventory.getFirst();
    }
  }

  // hero move selection system
  // returns true if you manage to flee, false otherwise.
  public boolean chooseMove(Character attacked){ // returns whether player escapes successfully
    int i = 1;
    System.out.println( "\nWhat is your choice?" );
    System.out.println( "\t1: Attack\n\t2: Flee\n\t3: Change Weapon \nSelection: " );

    try{
      i = Integer.parseInt( sc.nextLine() );
    }
    catch(Exception e){
      System.out.println("Invalid input. Please enter an integer.");
      return chooseMove(attacked);
    }

    if ( i == 1 ) {
      characterAttack(attacked, lastUsedWeapon);
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
      if (inventoryIdx == -1){
        return chooseMove(attacked);
      }
      characterAttack(attacked, inventory.get(inventoryIdx));
    }
    return false;
  }

  public Weapon getLastWeapon(){
    return lastUsedWeapon;
  }

  // hero weapon selection system
  // returns index of chosen inventory weapon
  private int weapon() {
    int weaponChoice = 0;
    String s = "Which weapon will you use?\n";
    s += "0: Go back\n";
    for (int i = 0; i < inventory.size(); i++) {
      if (inventory.get(i) instanceof Weapon) {
        if (inventory.get(i).getDurability() < 0){
          s += (i + 1) + ": " + inventory.get(i).getName() + " // Attack Power: " + inventory.get(i).getPower() + ", Durability: UNBREAKABLE \n";
        }
        else{
          s += (i + 1) + ": " + inventory.get(i).getName() + " // Attack Power: " + inventory.get(i).getPower() + ", Durability: " + inventory.get(i).getDurability() + "\n";
        }
      }
    }
    s += "Selection: ";
    System.out.println(s);

    try{
      weaponChoice = Integer.parseInt( sc.nextLine() );
    }
    catch(Exception e){
      System.out.println("Invalid input. Please enter an integer.");
      return weapon();
    }

    if (weaponChoice == 0){
      return -1;
    }
    else if (weaponChoice == 1) {
      lastUsedWeapon = inventory.getFirst();
      return 0;
    }
    else if (weaponChoice > 1 && weaponChoice < inventory.size()+1) {
      lastUsedWeapon = inventory.get(weaponChoice - 1);
      return weaponChoice - 1;
    }
    else{
      System.out.println("Invalid input. Please enter an integer.");
      return weapon();
    }
  }

}
