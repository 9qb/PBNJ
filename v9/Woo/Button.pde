/*
Special thanks to Salaj Rijal for posting code for buttons on Piazza.
This code refactors his code to suit our needs.
*/


//defaults
color STANDARD = #ffffff;
color HOVER = #a8a8a8;
color OUTLINE = #000000;
color TEXTCOLOR = #ffffff;

class Element{
  float x, y;
  Element(float x, float y){this.x = x; this.y = y;}
  void draw(){};
  void process(){};
}

//for text elements
class Label extends Element{
  String text;
  float size;
  Label(float x, float y, float size, String text){
    super(x,y);
    this.size = size; this.text = text;
  }
  void draw(){
    super.draw();
    textAlign(CENTER);
    fill(TEXTCOLOR);
    textSize(size);
    text( text, x, y+4);
  }
}

//for interactable buttons that take you to other pages
class Button extends Label{
  color current;
  float w, h;
  Page nextPage;
  Button(float x, float y, float size, String text, float w, float h, Page next){
    super(x, y, size, text);
    this.w = w; this.h = h;
    this.text = text; nextPage = next;
    current = STANDARD;
  }
  //check mouse position and if mouse was clicked, act accordingly
  void process(){
    super.process();
    boolean onButton = mouseX > x-w/2 && mouseX < x+w/2 && mouseY > y-h/2 && mouseY < y+h/2;
    //color is standard
    if( !onButton ) current = STANDARD;
    //if there is nextPage, go to it. Else print no connection
    else if( mousePressed ) onClick();
    //mouse is hovering over button
    else current = HOVER;
  }
  //what happens when clicked
  void onClick(){ 
    if( nextPage == null ) println("NO PAGE CONNECTED"); 
    else{
      currentPage = nextPage; 
      currentPage.setup(); 
      mousePressed = false;
    }
  }
  void draw(){
    rectMode(CENTER);
    //make outline first
    fill(OUTLINE);
    rect( x, y, w, h);
    //then make inside
    fill(current);
    rect( x, y, w-4, h-4);
    //render the label
    
    rectMode(CENTER);
    fill(OUTLINE);
    rect(x,y,240,80);
    super.draw();
  }
}

class Hitbox extends Button
{
  Hitbox(float x, float y, float size, String text, float w, float h, Page next){
    super(x, y, size, text, w, h, next);
  }
 
  void draw(){}
  
  
}
