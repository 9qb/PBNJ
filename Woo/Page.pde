class Page{
  void setup(){}
  void draw(){}
  void keyPressed(){}
  
  void process(){}
  
}

class HomePage extends Page
{
  Button play;
  Button howToPlay;
  void setup(){
    background(0);
    image(loadImage("HomePagev4.png"),0,0);
    play = new Button(width/2+16, height/2 +22, 15, "Play", 240, 80, new Game());
    howToPlay = new Button(width/2 +16, height/2 + 122, 15, "How To Play", 240, 80, new HowToPlay());
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
   mapTiles = new PImage[3];
   mapTiles[0] = loadImage("cobblestone.png");
   mapTiles[1] = loadImage("hero.png");
   mapTiles[2] = loadImage("water.png");
  
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
        else{image(mapTiles[0],currentX,currentY);}
        currentX += SIZE;
        
      }
      currentX = 0;
      currentY += SIZE;
      //println("");
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
