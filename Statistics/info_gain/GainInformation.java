package info_gain;

/**
 * Used for optimizing entropy calculations & fetching columns.
 * @author Cade Reynoldson
 * @version 1.0
 */
public class GainInformation {
    
    /** The index of the column in which the entropy was calculated to. */
    private int columnIndex;
    
    /** The information gained */
    private double infoGain;
    
    /**
     * Creates a new instance of this (no fucking shit)
     * @param theColumnIndex COLUMN INDEX
     * @param theInfoGain THE INFO GAIN??!?!?!?!?
     */
    public GainInformation(int theColumnIndex, double theInfoGain) {
        columnIndex = theColumnIndex;
        infoGain = theInfoGain;
    }
    
    /**
     * Returns the index of the column.
     * @return the index of the column.
     */
    public int getIndex() {
        return columnIndex;
    }
    
    /**
     * Returns the info gain.
     * @return the info gain.
     */
    public double getInfoGain() {
        return infoGain;
    }
    
    /**
     * Creates a string representation of the entropy data.
     * @return a string representation of the entropy data.
     */
    @Override
    public String toString() {
        return "Column Index: " + columnIndex + " - Info Gain: " + infoGain;
    }
}
