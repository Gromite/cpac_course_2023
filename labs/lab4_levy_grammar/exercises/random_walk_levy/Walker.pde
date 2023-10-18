float SCALE_STEP = 25; 
    
class Walker {
  PVector position;
  PVector prevPosition;
  float freq, amp, cutoff, vibrato;
  float stepsize=0;
  Walker() {
    this.position=new PVector(width/2, height/2);
    this.prevPosition=this.position.copy();    
    this.amp=0; this.vibrato=0;
    this.freq=0;
    this.cutoff=0;
  }
  
  void draw() {        
    /* Ex 1: your code here to draw*/
    stroke(255);
    strokeWeight(5);
    line(this.prevPosition.x, this.prevPosition.y,
         this.position.x, this.position.y);
    ;
  }
  void computeEffect(){
    /* Ex 3: your code here*/
    
    this.amp = map(this.position.x, 0, width, 0, 1);
    this.freq = map(this.position.y, 0, height, 10, 100);
    this.cutoff= map(this.position.x, 0, width, 0, 10000);
    this.vibrato=map(this.position.y, 0, height, -1, 1);
  }
  
  void update() {    
    
    PVector step = new PVector(random(-1,1), random(-1,1));
    step.normalize();
    
    float stepsize=montecarlo()*SCALE_STEP;
    /* your code here, remember to:
    - update prevPosition
    - constrain position inside the screen
    */
    this.prevPosition = this.position.copy();
    this.position = this.position.add(step.mult(stepsize));
    this.position.x=constrain(this.position.x, 0, width);    
    this.position.y=constrain(this.position.y, 0, height);
    
    
      }
}

float montecarlo() {
  float r1 = random(1);
  float r2 =random(1);
  float p = 0;
  if(MONTECARLO_STEPS==2){
    ;/* Your code here: ex. 1*/
    
    p =random(1);
    
    
  }
  else if(MONTECARLO_STEPS==1){
    /* your code here: ex. 2  */
    
    p = 0.1 + 0.9*abs(cos(r1*PI));
    
    
    ; 
  }
  
  if (r2<p){
    return r1;
    }
    else{
    montecarlo();
    }
  return 0;
}
