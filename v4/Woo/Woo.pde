MazeGenerator gen = new MazeGenerator( 27,48 );
String[][] maze = gen.getGeneratedMaze();
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
  
  int currentX = 0;
  int currentY = 0;
  
  for( String[] row : maze ){
    
    for( String col : row ){
      if( col.equals("#") ) image(mapTiles[2],currentX,currentY);
      else{image(mapTiles[0],currentX,currentY);}
      currentX += SIZE;
    }

    currentX = 0;
    currentY += SIZE;
  }
  
  
}
