
// The "Vehicle" class

class Vehicle {
  
  ArrayList<PVector> history = new ArrayList<PVector>();

  PVector location;
  PVector velocity;
  PVector acceleration;
  float r;
  float mass;
  float maxforce;    // Maximum steering force
  float maxspeed;    // Maximum speed
  
  float d;
  float c1 = random(0, 255);
  float c2 = random(0, 255);
  float c3 = random(0, 255);
  color col = color(c1,c2,c3,200); 


  Vehicle(float x, float y) {
    acceleration = new PVector(0,0);
    velocity = new PVector(0,0);
    location = new PVector(x,y);
    r = 6;
    mass=1;
    maxspeed = 4;
    maxforce = 0.1;
  }

  
  void applyForce(PVector force) {
    PVector f = PVector.div(force,mass);  // Force/Mass
    acceleration.add(f);
  }

  // A method that calculates a steering force towards a target
  // STEER = DESIRED MINUS VELOCITY
  void arrive(PVector target) {
    PVector desired = PVector.sub(target,location);  // A vector pointing from the location to the target
    //float d = desired.mag();
    d = desired.mag();
    // Normalize desired and scale with arbitrary damping within 100 pixels
    desired.normalize();
    if (d < 100) {
      float m = map(d,0,100,0,maxspeed);
      desired.mult(m);
    } else {
      desired.mult(maxspeed);
    }

    // Steering = Desired minus Velocity
    PVector steer = PVector.sub(desired,velocity);
    steer.limit(maxforce);  // Limit to maximum steering force
    applyForce(steer);
  }
  
  void run(){
    update();
    render();
  }
  
  // Method to update location
  void update() {
    velocity.add(acceleration);
    velocity.limit(maxspeed);
    location.add(velocity);
    acceleration.mult(0);
    c1 = random(0, 255);
    c2 = random(0, 255);
    c3 = random(0, 255);
    col = color(c1,c2,c3,200); 
    history.add(location.get());
    if (history.size() > 1000) {
      history.remove(0);
    }
  }
  
  void render() {
    
    beginShape();
    stroke(0);
    strokeWeight(1);
    //noFill();
    float c = map(d,0,width,0.2,1);  // d = distance to the target
                                                      
    fill(c1*c,c2*c,c3*c,200);
    for(PVector v: history) {
      vertex(v.x,v.y);
    }
    endShape();
    
    // Draw a triangle rotated in the direction of velocity
    float theta = velocity.heading2D() + PI/2;
    fill(127);
    stroke(0);
    strokeWeight(1);
    pushMatrix();
    translate(location.x,location.y);
    rotate(theta);
    beginShape();
    vertex(0, -r*2);
    vertex(-r, r*2);
    vertex(r, r*2);
    endShape(CLOSE);
    popMatrix();
    
    
  }
}
