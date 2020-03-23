package discreteize;

import info_gain.Gain;

public class BinAll {
    
    /** The binning method to use for binning all of a data frame. */
    private Bin binType;
    
    /** The gain method to use for calculating the "gain" of the data frame */
    private Gain gainType; 
    
    
    public BinAll(Bin binType, Gain gainType) {
        this.binType = binType;
        this.gainType = gainType;
    }
    
    
    
    
    
    
}
