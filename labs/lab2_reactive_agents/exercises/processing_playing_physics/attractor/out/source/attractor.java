/* autogenerated by Processing revision 1293 on 2023-10-11 */
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import oscP5.*;
import netP5.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class attractor extends PApplet {



int PORT = 57120;
OscP5 oscP5;
NetAddress ip_port;

AgentMover mover;
int MASS_TO_PIXEL=10;
float mass_mover = 10;
float mass_attractor;
PVector pos_attractor;
float radius_attractor;
float area;
float dist=0;
public void setup(){
  mover=new AgentMover(10);
  mass_attractor=random(800, 1200);
  pos_attractor = new PVector(width/2.f, height/2.f);  
  radius_attractor = sqrt(mass_attractor/PI)*MASS_TO_PIXEL;
  
  oscP5 = new OscP5(this,55000);
  ip_port = new NetAddress("127.0.0.1",PORT);
  /* size commented out by preprocessor */;
  background(0);  
}

public PVector computeGravityForce(AgentMover mover){
  PVector attr_force = mover.position.copy();
  attr_force.sub(mover.position);
  dist = attr_force.mag();
  dist = constrain(dist, dist_min, dist_max);
  attr_force.normalize();
  attr_force.mult(mass_attractor*mover.mass/(dist*dist));
return attr_force;


}



public void sendEffect(float cutoff, float vibrato){
    OscMessage effect = new OscMessage("/note_effect");    
    effect.add("effect");       
    effect.add(cutoff);
    effect.add(vibrato);
    
    oscP5.send(effect, ip_port);
}
    
public void draw(){
  rectMode(CORNER);
  fill(0,20);
  rect(0,0,width, height);
  fill(200, 0, 200, 40);
  ellipse(pos_attractor.x, pos_attractor.y, 
          radius_attractor, radius_attractor);
  
  PVector force = computeGravityForce(mover);
  mover.applyForce(force);
  mover.update();
  mover.computeEffect(dist);
  sendEffect(mover.cutoff, mover.vibrato);
  mover.draw();
}

float alpha=0.1f;  
float dist_min=50;
float dist_max=100;

class AgentMover{
  PVector position, velocity, acceleration;
  float mass, radius;
  float vibrato=0;
  float cutoff=0.5f;
  AgentMover(float mass){
    this.position = new PVector(random(0, width), random(0, height));
    this.velocity = new PVector(random(-2, 2), random(-2, 2));
    this.acceleration = new PVector(random(2), random(2));
    this.mass=mass;
    this.radius=sqrt(this.mass/PI)*MASS_TO_PIXEL;    
  }
  public void update(){    
    this.velocity.add(this.acceleration);
    this.position.add(this.velocity);
    this.acceleration.mult(0);
  }
  public void computeEffect(float dist){
    this.cutoff=map(dist,dist_min,dist_max,0,1);
  }
  public void applyForce(PVector force){    
    PVector f = force.copy();
    f.div(this.mass);
    this.acceleration.add(f);
    
  }
  public void draw(){
    fill(200);
    noStroke();
    ellipse(this.position.x, this.position.y, this.radius, this.radius);    
  }
}


  public void settings() { size(1280, 720); }

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "attractor" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
