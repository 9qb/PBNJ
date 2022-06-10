// PBNJ - Brian Li, Nakib Abedin, Jefford Shau
// APCS pd07
// Final Project -- Dungeon Crawler
// 2022-06-10

public class Monster extends Character {
  public Monster() {
    super();
  }

  public Monster(int newHealth, int newAttack, int newSpeed, int newR, int newC, Maze maze) {
    super(newHealth, newAttack, newSpeed, newR, newC, maze);
  }

  public String getName() {
    return "Monster";
  }

  // if return true, that means hero and monster possess the same tile. we should battle
  public boolean playTurn(){
    // monster movement
    // random direction
    int choice;
    boolean moved = false;
    while (!moved) {
      choice = (int)(Math.random() * 5);

      if (choice == 0 && (dungeon.getPos(currentR-1, currentC) == " " || dungeon.getPos(currentR-1, currentC) == "X")) {
        if (dungeon.getPos(currentR-1, currentC) == "X"){
          moveUp();
          dungeon.setPos(currentR, currentC, "M");
          moved = true;
          lastTile = " ";
          return true;
        }

        moveUp();
        dungeon.setPos(currentR, currentC, "M");
        moved = true;
      }
      else if (choice == 1 && (dungeon.getPos(currentR, currentC-1) == " " || dungeon.getPos(currentR, currentC-1) == "X")) {
        if (dungeon.getPos(currentR, currentC-1) == "X"){
          moveLeft();
          dungeon.setPos(currentR, currentC, "M");
          moved = true;
          lastTile = " ";
          return true;
        }

        moveLeft();
        dungeon.setPos(currentR, currentC, "M");
        moved = true;
      }
      else if (choice == 2 && (dungeon.getPos(currentR+1, currentC) == " " || dungeon.getPos(currentR+1, currentC) == "X")) {
        if (dungeon.getPos(currentR+1, currentC) == "X"){
          moveDown();
          dungeon.setPos(currentR, currentC, "M");
          moved = true;
          lastTile = " ";
          return true;
        }

        moveDown();
        dungeon.setPos(currentR, currentC, "M");
        moved = true;
      }
      else if (choice == 3 && (dungeon.getPos(currentR, currentC+1) == " " || dungeon.getPos(currentR, currentC+1) == "X")) {
        if (dungeon.getPos(currentR, currentC+1) == "X"){
          moveRight();
          dungeon.setPos(currentR, currentC, "M");
          moved = true;
          lastTile = " ";
          return true;
        }

        moveRight();
        dungeon.setPos(currentR, currentC, "M");
        moved = true;
      }
      else {
        // do nothing
        return false;
      }
    }
    return false;
  }

  // overrides character move selection system
  // monster attacks do not need player input
  @Override
  public boolean chooseMove(Character attacked){
    // Map.clear();
    System.out.println("The monster attacked you!");
    characterAttack(attacked, lastUsedWeapon);
    return false;
  }

  // overrides character attacking System
  // monster does not use weapons
  @Override
  public void characterAttack(Character attacked, Weapon weapon){
    int dmg = attack + weapon.getPower();
    weapon.reduceDurability(1);
    if (dmg < 0){
      dmg = 0;
    }
    attacked.subtractHealth(dmg);
    System.out.println("The monster dealt " + dmg + " damage.");
    System.out.println(attacked.getName() + "\tHealth: " + attacked.getHealth() + "\tAttack: " + attacked.getAtk());
  }

}
