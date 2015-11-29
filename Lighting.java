//Lighting created by Justin Lepard, 2015
//Creative commons, please use with credit

//connects to arduino Fadecandy processor
OPC opc;

//imports sound library
import processing.sound.*;
AudioIn input;
Amplitude rms;
int scale=1;
int colorScale=1;

//"a" will be for rotating, smooth will smooth out scale, other variables for color
float a = 0.0;
float smooth;
float r,g,b,d,f;

void setup() {
  size(500, 500, P3D);
  
  input = new AudioIn(this, 0);
    input.start();
    rms = new Amplitude(this);
    rms.input(input);
  
  //setup connection to OPC code
  opc = new OPC(this, "127.0.0.1", 7890);
  opc.ledGrid8x8(0, width/2, height/2, height / 12.0, 0, false);
}

void draw() {  
  background(0);
  
  //setup amplitude analysis
  input.amp(map(mouseY, 0, height, 0.0, 1.0));
  //the the second parameter controls how extreme the size differential is,
  //the third and fourth parameters control the start and end size of the objects
  scale=int(map(rms.analyze(), 0, .05, 80, 1000));
    noStroke();
  
  //smooths the output from scale
  //the smaller the decimal number, the longer it takes to change size
  smooth += (1*scale - smooth) * 0.005;
  
  //define rotation speed by amplitude
  a += smooth*0.00005;
  if(a > TWO_PI) { 
    a = 0.0; 
  }
  
  //setup color variables by amplitude
  r = scale;
  if(r > 200){
    r = 200-smooth;
  }
  if(smooth < 100) {
    r = smooth;
  }
  
  g = smooth-100;
  if(g < 0){
    g = smooth;
    }
  if(smooth < 100) {
    g = smooth+50;
  }
  
  b = smooth+100;
  if(b > 200){
    b = 200-smooth-100;
    }
  if(smooth < 100) {
    b = smooth+50;
  }
  
  //these variables are to achieve bright colors
  d = 0;
  f = 255;
  
  //setup directional light unless volume is at the loudest range
  if(scale < 200){
  directionalLight(204, 204, 204, 0, 0, -1);
  }
  
  translate(width/4, height/4);
  
  //create rotating ellipses with size based on sound
  rotateX(a);
  rotateY(a * 2.0);
  fill(b,g,d);
  ellipse(width, height, smooth, smooth/2);
  
  rotateX(a);
  rotateY(a * 2.0);
  fill(d,g,r);
  ellipse(width/4, height/4, smooth, smooth/2);
 
  rotateX(a);
  rotateY(a * 2.0);
  fill(d,b,g);
  ellipse(width/8, width/8, smooth, smooth/2);
  
  rotateX(a * 1.001);
  rotateY(a * 2.002);
  fill(f,g,r);
  ellipse(width/2, height/2, smooth, smooth/2);
  
  rotateX(a * 1.001);
  rotateY(a * 2.002);
  fill(g,b,f);
  ellipse(width/2, height/2, smooth, smooth/2);
  
}
