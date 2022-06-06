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
    play = new Hitbox(width/2+16, height/2 +22, 15, "Play", 240, 80, new Game());
    howToPlay = new Hitbox(width/2 +16, height/2 + 122, 15, "How To Play", 240, 80, new HowToPlay());
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
  
  void setup(){
   map = new Map();
   SIZE = 32;
   background(0);
   mapTiles = new PImage[4];
   mapTiles[0] = loadImage("cobblestone.png");
   mapTiles[1] = loadImage("hero.png");
   mapTiles[2] = loadImage("water.png");
   mapTiles[3] = loadImage("Escape.png");
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
        else{image(mapTiles[0],currentX,currentY);}
        currentX += SIZE;
        
      }
      currentX = 0;
      currentY += SIZE;
      //println("");
      int randNum = int(random(60));
      if(randNum == 1){
        randNum = int(random(4));
        if(randNum == 0){map.moveUp();}
        if(randNum == 1){map.moveRight();}
        if(randNum == 2){map.moveDown();}
        if(randNum == 3){map.moveLeft();}
      }
    }
    
  }
  
 void keyPressed(){
    if(key == 'W' || key == 'w'){
      map.moveUp();
  }
    else if(key == 'A' || key == 'a'){
      map.moveLeft();
    }
    else if(key == 'S' || key =='s'){
      map.moveDown();
  }
    else if(key == 'D' || key == 'd'){
      map.moveRight();
  }
  }

}

class BattlePage extends Page
{
  BattlePage thisPage;
  Button Attack;
  Button Flee;
  Button ChangeWeapon;
  
  void setup(){
    thisPage = new BattlePage();
    Attack = new Button(width/2 - 150,height-200,32.0,"Attack",240, 90, thisPage);
    Flee = new Button(width/2 +150,height-200,32.0,"Flee",240, 90, thisPage);
    ChangeWeapon = new Button(width/2,height-75,32.0,"Change Weapon", 240, 90, thisPage);
  }
  
  void draw(){
    background(#422840);
    Attack.draw();
    Flee.draw();
    ChangeWeapon.draw();
    
    image(loadImage("battleScreenHero.png"), 100,150 );
    image(loadImage("battleScreenMonster.png"), 425, 150);
    image(loadImage("battleSword.png"),290,200);
  }
  
  void process(){
    Attack.process();
    Flee.process();
    ChangeWeapon.process();
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
