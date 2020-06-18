package dataframe_utilities;

/**
 * Stores the data of an object contained in a dataframe.
 * - Generally used for rows or columns. 
 * @author Cade Reynoldson
 * @version 1.0
 */
public class DataFrame_Iterable {
    
    /** The value contained. Usually a row or column. */
    private Object value;
    
    /** The index of the value contained. */
    private int valueIndex;
    
    /**
     * Creates a new instance of a dataframe object to iterate over. 
     * @param value the value to store. 
     * @param valueIndex the index of the value being stored. 
     */
    public DataFrame_Iterable(Object value, int valueIndex) {
        this.value = value;
        this.valueIndex = valueIndex;
    }

    /**
     * Returns the row, column, or other object stored. 
     * @return the row, column, or other object stored. 
     */
    public Object getValue() {
        return value;
    }
    
    /**
     * Changes the values stored.
     * @param value the new value to store. 
     * @param valueIndex the new value index to store. 
     */
    public void changeValues(Object value, int valueIndex) {
        this.value = value;
        this.valueIndex = valueIndex;
    }

    /**
     * Returns the index of the value. 
     * @return the index of the value. 
     */
    public int getValueIndex() {
        return valueIndex;
    }    
}
