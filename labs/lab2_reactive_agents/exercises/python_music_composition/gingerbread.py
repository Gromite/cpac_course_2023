import sys
import time
import numpy as np

    
from classes import Agent, Composition, ID_START

class Gingerbread(Composition):
    def __init__(self, BPM=60):
        Composition.__init__(self,BPM=BPM)
        self.min=-3
        self.max=8
        self.range=self.max-self.min 
        
        self.x = -0.1
        self.y = 0.1
        # your code here
        
    def map(self, value_in, min_out, max_out):
        value_out = (value_in-self.min)/(self.range)
        value_out = min_out+ value_out * (max_out-min_out)
        return np.clip(value_out, min_out, max_out)

    def next(self):
        # your code here
        self.x_old = self.x
        self.x = 1 - self.y + abs(self.x)
        self.y = self.x_old
        self.amp = float(np.random.choice([0.8,1,0.7]))
        self.dur = self.map(self.x, 0.1 , 0.5) 
        self.midinote = int(self.map(self.y, 50 , 60) + np.random.choice([0,2,4])) 

        print(self.x, self.y)
        pass
    
if __name__=="__main__":
    n_agents=1
    composer=Gingerbread()
    agents=[_ for _ in range(n_agents)]
    agents[0] = Agent(57120, "/note_effect", composer)

    input("Press any key to start \n")
    for agent in agents:
        agent.start()
    try: # USE CTRL+C to exit     
        while True:
            time.sleep(10)
    except:         
        for agent in agents:              
            agent.kill()
            agent.join()
        sys.exit()

# %%