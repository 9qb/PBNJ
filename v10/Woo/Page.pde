class Page{
  void setup(){}
  void draw(){}
  void keyPressed(){}
  
  void process(){}
  
}

class HomePage extends Page
{
  Hitbox play;
  Hitbox howToPlay;
  void setup(){
    background(0);
    image(loadImage("HomePagev4.png"),0,0);
    play = new Hitbox(width/2+16, height/2 +22, 15, "Play", 240, 80, gamePage);
    howToPlay = new Hitbox(width/2 +16, height/2 + 122, 15, "How To Play", 240, 80, howToPlayPage);
  }
  
  void draw(){
    play.draw();
    howToPlay.draw();
  }
  
  void process(){
    play.process();
    howToPlay.process();
  }
}

class Game extends Page{
  
  Map map;
  PImage[] mapTiles;
  int SIZE;
  boolean battlePhase;
  
  void setup(){
   map = new Map();
   SIZE = 32;
   battlePhase = false;
   background(0);
   mapTiles = new PImage[5];
   mapTiles[0] = loadImage("cobblestone.png");
   mapTiles[1] = loadImage("hero.png");
   mapTiles[2] = loadImage("water.png");
   mapTiles[3] = loadImage("Escape.png");
   mapTiles[4] = loadImage("Monster.png");
   
  }
  
  
  void draw(){
    int currentX = 0;
    int currentY = 0;
    
    
    for( String[] row : map.displayZone() ){
      for( String col : row ){
        //print(col);
        if( col.equals("#") ) image(mapTiles[2],currentX,currentY);
        else if (col.equals("@") ) {image(mapTiles[2],currentX,currentY);}
        else if (col.equals("X")){
          image(mapTiles[0],currentX,currentY);
          image(mapTiles[1],currentX,currentY);
        }
        else if (col.equals("E")){image(mapTiles[3], currentX, currentY);}
        else if (col.equals("M")){
          image(mapTiles[0],currentX,currentY);
          image(mapTiles[4], currentX, currentY);  
      }
        else{image(mapTiles[0],currentX,currentY);}
        currentX += SIZE;
        
      }
      currentX = 0;
      currentY += SIZE;
    }
    
    battlePhase = map.battlePhase;

  }
  
 void keyPressed(){
    if(key == 'W' || key == 'w'){
      map.round("W");
  }
    else if(key == 'A' || key == 'a'){
      map.round("A");
    }
    else if(key == 'S' || key =='s'){
      map.round("S");
  }
    else if(key == 'D' || key == 'd'){
      map.round("D");
  }
  }

}

class BattlePage extends Page
{
  BattlePage thisPage;
  Button Attack;
  Button Flee;
  Button ChangeWeapon;
  Character mc = gamePage.map.mc;
  Monster monster;
  
  BattlePage(Character hero, Monster mons){
    mc = hero;
    this.monster = mons;
  }
  
  void setup(){
    thisPage = new BattlePage(mc, monster);
    Attack = new Button(width/2 - 150,height-200,32.0,"Attack",240, 90, thisPage);
    Flee = new Button(width/2 +150,height-200,32.0,"Flee",240, 90, thisPage);
    ChangeWeapon = new Button(width/2,height-75,32.0,"Change Weapon", 240, 90, thisPage);
    
    image(loadImage("BattleScreenBackground.png"), -1, -1);
    image(loadImage("battleScreenHero.png"), 100,150 );
    image(loadImage("battleScreenMonster.png"), 425, 150);
    image(loadImage("battleSword.png"),290,215);
  }
  
  void draw(){
    Attack.draw();
    Flee.draw();
    ChangeWeapon.draw();
    
    battle(mc, monster);
  }
  
  void process(){
    Attack.process();
    Flee.process();
    ChangeWeapon.process();
  }
  
  void battle(Character first, Character second){
   if(!(first.isAlive())  || !(second.isAlive())){
   if (first.chooseMove(second)){ // if true, then hero has fleed
          System.out.println("Your act of cowardice is sad. Your score has been reduced.");
          gamePage.map.score -= 200;
          gamePage.map.currentFrame.setPos(mc.getR(), mc.getC(), "X");
          gamePage.map.monsters.remove(second);
          return;
        }
        if (second.isAlive()){
          if (second.chooseMove(first)){ // if true, then hero has fleed
            System.out.println("Your act of cowardice is sad. Your score has been reduced.");
            gamePage.map.score -= 200;
            gamePage.map.currentFrame.setPos(mc.getR(), mc.getC(), "X");
            gamePage.map.monsters.remove(first);
            return;
          }
        } 
  }
  }

}

class HowToPlay extends Page{
  void setup()
  {
    background(0);
  }
  
  void draw()
  {
    background(0);
  }

}
