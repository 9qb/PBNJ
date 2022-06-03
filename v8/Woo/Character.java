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
    // map[currentR][currentC] = lastTile;
    dungeon.setPos(currentR, currentC, lastTile);
    // lastTile = map[r][c];
    lastTile = dungeon.getPos(r, c);
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

  // private void processTile() {
  //   if (lastTile.equals("E")){
  //
  //   }
  // }
}
