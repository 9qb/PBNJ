public class Character {
  protected int health;
  protected int attack;
  protected int speed;
  protected int currentR;
  protected int currentC;
  protected int baseDamage;
  protected int defense;
  protected String lastTile;
  protected String[][] map;

  public Character() {
   health = 4;
   baseDamage = 3;
   defense = 0;
  }

  public Character(int newHealth, int newAttack, int newSpeed, int newC, int newR, String[][] maze) {
   health = newHealth;
   attack = newAttack;
   speed = newSpeed;
   currentC = newC;
   currentR = newR;
   map = maze;
   lastTile = map[currentC][currentR];
  }

  public int getR() { // Y cord
    return currentR;
  }

  public int getC() { // X cord
    return currentC;
  }

  public void changeX(int value) {
    currentR += value;
  }

  public void changeY(int value) {
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

  public void updLastTile() {
    map[currentR][currentC] = lastTile;
  }
  
  public String lastTile(){
    return lastTile;
  }

  // public void attack() {
  //
  // }
  //
  // public void attack(int r, int c) {
  //
  // }
  //
  // public void playTurn() {
  //
  // }

  public void addHealth(int value) {
   health += value;
  }

  public void subtractHealth(int value) {
   health -= value;
  }

  public void moveUp() {
    updLastTile();
    currentR -= 1;
  }

  public void moveRight() {
    updLastTile();
    currentC += 1;
  }

  public void moveDown() {
    updLastTile();
    currentR += 1;
  }

  public void moveLeft() {
    updLastTile();
    currentC -= 1;
  }

  public String getName() {
    return "";
  }

  // private void processTile() {
  //
  // }
}
