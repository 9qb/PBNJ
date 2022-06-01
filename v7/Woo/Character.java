public class Character {
  protected int health;
  protected int attack;
  protected int speed;
  protected int currentR;
  protected int currentC;
  protected int baseDamage;
  protected int defense;
  protected int[][] lastTile;

  public Character() {
   health = 4;
   baseDamage = 3;
   defense = 0;
  }

  public Character(int newHealth, int newAttack, int newSpeed, int newC, int newR) {
   health = newHealth;
   attack =  newAttack;
   speed = newSpeed;
   currentC = newC;
   currentR = newR;
   lastTile = null;
  }

  public int getR() { // Y cord
    return currentR;
  }

  public int getC() { // X cord
    return currentC;
  }

  public void changeX(int value) {
    currentR -= value;
  }

  public void changeY(int value) {
    currentC -= value;
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

  public void updLastTile(int x, int y) {
    lastTile = new int[x][y];
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
    currentC -= 1;
  }

  public void moveRight() {
    currentR += 1;
  }

  public void moveDown() {
    currentC += 1;
  }

  public void moveLeft() {
    currentR -= 1;
  }

  // private void processTile() {
  //
  // }
}
