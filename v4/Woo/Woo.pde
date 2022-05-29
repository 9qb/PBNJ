Map map = new Map();
PImage[] mapTiles;

int SIZE = 32;

void setup(){
 size(900, 900);
 background(0);
 mapTiles = new PImage[3];
 mapTiles[0] = loadImage("ground.png");
 mapTiles[1] = loadImage("water.png");
 mapTiles[2] = loadImage("lava.png");

}


void draw(){
  String[][] _maze = map.getMaze();
  
  int currentX = 0;
  int currentY = 0;
  
  for( String[] row : _maze ){
    
    for( String col : row ){
      if( col.equals("#") ) image(mapTiles[2],currentX,currentY);
      else if (col.equals("X")){image(mapTiles[1],currentX,currentY);}
      else{image(mapTiles[0],currentX,currentY);}
      currentX += SIZE;
    }

    currentX = 0;
    currentY += SIZE;
  }
  
}

void keyPressed(){
  if(key == 'W'){map.moveUp();}
  else if(key == 'A'){map.moveLeft();}
  else if(key == 'S'){map.moveDown();}
  else if(key == 'D'){map.moveRight();}
}
