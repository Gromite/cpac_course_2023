# %% Import libraries
import os
os.chdir(os.path.dirname(os.path.abspath(__file__)))
from classes import Sonifier, Grammar_Sequence, metronome_grammar

# %%
ex_i_grammar={
    "S":["M", "SM"],
    "M": [["H","H"], ["q", "h", "q"], ["pth","th","th"]],    
    #"H": ["h", ["q","q"],  ["tq","tq","tq"]],
    "H": [  ["tq","ptq","tq"], ["Q","Q"], ["ph"]],

    #"Q": [["q"], ["o","o"],  ["to","to","to"]],
    "Q": [ ["to","to","pto"], ["q"], ["pq"]]

}
octave_grammar = {
    "S":["MS", "SM", "M"],
    "M": [["H","H"], ["Q", "H", "q"]],    
    "H": ["h", ["o","o", "O","o"]],
    "Q": [["q"], ["o","O"]],
    "O": [["o"], ["0", "0"]]
}

o_word_dur = {"h":0.5, # half-measure
              "q":0.25, # quarter-measure
              "o": 0.125,
              "0": 0.0675,
              
              }
ex_i_word_dur={"h":0.5, # half-measure
              "q":0.25, # quarter-measure
              "o": 0.125,
              "pq": 0.25, 
              "ph": 0.5, 
              "po": 0.125,
              "tq": 0.25/3,                
              "th": 0.5/3,                                
              "to": 0.125/3,
              "ptq": 0.25/3,                
              "pth": 0.5/3,                                
              "pto": 0.125/3,
              
}


if __name__=="__main__":
    fn_out="basic_composition.wav"

    NUM_M=8
    START_SEQUENCE=["M",]*NUM_M
    G=Grammar_Sequence(ex_i_grammar)        
        
    final_sequence, seqs=G.create_sequence(START_SEQUENCE)
    for seq in seqs:
        print(" ".join(seq))
    print(f"Final sequence: {' '.join(final_sequence)}")    
    
    S= Sonifier("sounds/metronome.wav", BPM=166, word_dur=ex_i_word_dur)
    audio_array=S.create_audio(final_sequence, add_metronome=True)

    # S= Sonifier("sounds/cough.wav", BPM=78, word_dur=o_word_dur)
    # audio_array1=S.create_audio(final_sequence, add_metronome=False)

    S.write("out/1"+fn_out, audio_sequence=audio_array)

# %%
