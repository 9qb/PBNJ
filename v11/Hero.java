// PBNJ - Brian Li, Nakib Abedin, Jefford Shau
// APCS pd07
// Final Project -- Dungeon Crawler
// 2022-06-10

import java.util.LinkedList;

public class Hero extends Character {

  public Hero() {
    super();
  }

  public Hero(int newHealth, int newAttack, int newSpeed, int newR, int newC, Maze maze) {
   super(newHealth, newAttack, newSpeed, newR, newC, maze);
  }

  public Hero(int newHealth, int newAttack, int newSpeed, int newR, int newC, LinkedList<Weapon> newInventory, Weapon lastUsedWpn, Maze maze) {
   super(newHealth, newAttack, newSpeed, newR, newC, newInventory, lastUsedWpn, maze);
  }

  public String getName() {
    return "Hero";
  }
}
