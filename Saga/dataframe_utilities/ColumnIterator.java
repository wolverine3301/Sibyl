package dataframe_utilities;

import java.util.Iterator;

import dataframe.DataFrame;

/**
 * Allows for synchronized iteration over the columns in a dataframe. 
 * Under multithreaded circumstances, ALWAYS use the next() method 
 * and check its value for null instead of the hasNext() method. 
 * @author Cade Reynoldson
 * @version 1.0
 */
public class ColumnIterator implements Iterator<DataFrame_Iterable>{

    /** The dataframe to iterate over. */
    private DataFrame dataFrame;
    
    /** The index of the current column being iterated over. */
    private int currentColumn;
    
    /**
     * Creates a new instance a column iterator on the given data frame.
     * @param dataFrame the DataFrame to iterate over. 
     */
    public ColumnIterator(DataFrame dataFrame) {
        super();
        this.dataFrame = dataFrame; 
        currentColumn = 0;
        
    }
    
    /**
     * Returns if the there is another column to iterate over.
     * Unless of a specific reason, NEVER use this method in a multithreaded application. 
     */
    @Override
    public synchronized boolean hasNext() {
        if (currentColumn < dataFrame.getNumColumns())
            return true; 
        else
            return false; 
    }

    /**
     * Returns the next column in the form of a DataFrame_Iterable object. 
     */
    @Override
    public synchronized DataFrame_Iterable next() {
        if (currentColumn < dataFrame.getNumColumns()) {
            return new DataFrame_Iterable(dataFrame.getColumn(currentColumn), currentColumn++);
        } else {
            return null; 
        }
    }

}
