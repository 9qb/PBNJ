// ______________________________________________

// move this to Map.java? We need a place for all the instance variables

// _____________________________________________
public class RPG {
  private Hero mc;
  private Monster monster;
  private int score;

  private boolean victory = false;

  private ArrayList<Item> inventory = new ArrayList<Item>();
  private ArrayList<Monster> monsters = new ArrayList<Monster>();

  public boolean roundTurns() {
    mc = new Hero(100, 10, 0);
    monster = new Monster(100, ((int) Math.random() * 5) + 10 , 0);
    while(mc.isAlive() ) { // add if mc not on end tile
      playerTurn();
      if (playerTurn()) {
        monsterTurn();
      }
    // set victory to true if on end tile, false otherwise
  }

  public boolean playerTurn() { // returns true if player alive

  }


  public boolean monsterTurn() { //starts when player is in a room


  }


  public static void main(String[] args) {
    RPG stage = new RPG();
    if (roundTurns()) {
      System.out.println("You cleared the floor!");
      System.out.println("Current score: " + score);
      System.out.println("Generating next stage ...");
      System.out.println("...");
    } else {
      System.out.println("You Died!");
      System.out.println("Final Score: " + score);
    }
  }




}
