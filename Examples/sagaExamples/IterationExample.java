package sagaExamples;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.Row;
import dataframe_utilities.ColumnIterator;
import dataframe_utilities.DataFrame_Iterable;
import dataframe_utilities.RowIterator;

public class IterationExample implements Runnable {
    
    private static DataFrame df = DataFrame.read_csv("testfiles/iris.txt");
    
    private static ColumnIterator columnIterator = new ColumnIterator(df);
    
    private static RowIterator rowIterator = new RowIterator(df);
    
    public static void main(String[] args) {
        //thread 1
        IterationExample e1 = new IterationExample();
        //thread 2
        IterationExample e2 = new IterationExample();
        new Thread(e1).start();
        new Thread(e2).start();
    }

    
    @Override
    public void run() {
        DataFrame_Iterable current = columnIterator.next();
        long threadId = Thread.currentThread().getId();
        while (current != null) { //Iterate over columns. 
            System.out.println("Thread " + threadId + " Consumed Column: " + ((Column) current.getValue()).getName());
            current = columnIterator.next();
        }
        System.out.println("Thread " + threadId + " completed consumption of columns.\nConsuming rows: ");
        current = rowIterator.next();
        while (current != null) {
            System.out.println("Thread " + threadId + " Consumed Row " + current.getValueIndex() + ": " + ((Row) current.getValue()).toString());
            current = rowIterator.next();
        }
        System.out.println("Thread " + threadId +  "Completed consumption of rows.");
    }
}
