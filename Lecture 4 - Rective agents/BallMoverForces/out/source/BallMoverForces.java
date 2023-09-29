/* autogenerated by Processing revision 1293 on 2023-09-29 */
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class BallMoverForces extends PApplet {

Mover m;
Mover m2;

public void setup() {
  /* size commented out by preprocessor */;
  /* smooth commented out by preprocessor */;
  m = new Mover();
  m2 = new Mover();
}

public void draw() {
  background(255);

  PVector wind = new PVector(0.9f,0.3f);
  PVector gravity = new PVector(0,0.01f);
  

  m.applyForce(wind);
  m.applyForce(gravity);
  
  m2.applyForce(wind);
  m2.applyForce(gravity);

  m.run();
  m2.run();

}
class Mover {

  PVector location;
  PVector velocity;
  PVector acceleration;
  float mass;

  Mover() {
    location = new PVector(random(0,width),random(0,height));
    velocity = new PVector(0,0);
    acceleration = new PVector(0,0);
    mass = 3;
  }
  
  public void applyForce(PVector force) {
    PVector f = PVector.div(force,mass);  // Force/Mass
    acceleration.add(f);
  }


  public void run(){
    update();
    checkEdges();
    render(); 
  }


  private void update() {
    velocity.add(acceleration);
    location.add(velocity);
    //acceleration.mult(0);  // Since the acceleration corresponds to the force, need to be set up to 0 each cycle
  }

  private void render() {
    stroke(0);
    strokeWeight(2);
    fill(127);
    ellipse(location.x,location.y,48,48);
  }
  
 
  public void checkEdges() {

    if (location.x > width) {
      location.x = width;
      velocity.x *= -1;    // bouncing
    } else if (location.x < 0) {
      velocity.x *= -1;
      location.x = 0;
    }

    if (location.y > height) {
      velocity.y *= -1;    // bouncing
      location.y = height;
    }
  }
}


  public void settings() { size(800, 200);
smooth(); }

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "BallMoverForces" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
