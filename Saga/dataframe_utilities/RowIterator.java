package dataframe_utilities;

import java.util.Iterator;

import dataframe.DataFrame;

public class RowIterator implements Iterator<DataFrame_Iterable> {

    /** The data frame to iterate over. */
    private DataFrame dataFrame;
    
    /** The index of the current row being iterated over. */ 
    private int currentRow;
    
    /**
     * Creates a new instance of a row iterator on the given data frame. 
     * @param dataFrame
     */
    public RowIterator(DataFrame dataFrame) {
        super();
        currentRow = 0;
        this.dataFrame = dataFrame;
    }
    
    
    /**
     * Returns if the there is another row to iterate over.
     * Unless of a specific reason, NEVER use this method in a multithreaded application. 
     */
    @Override
    public boolean hasNext() {
        if (currentRow < dataFrame.getNumRows()) 
            return true; 
        else
            return false; 
    }

    /**
     * Returns the next row in the form of a DataFrame_Iterable object. 
     */
    @Override
    public synchronized DataFrame_Iterable next() {
        if (currentRow < dataFrame.getNumRows())
            return new DataFrame_Iterable(dataFrame.getRow_byIndex(currentRow), currentRow++);
        else
            return null;
    }

}
