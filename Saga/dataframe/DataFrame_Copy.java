package dataframe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import particles.Particle;

/**
 * Static class for performing various operations with a data frame.
 * @author Cade Reynoldson
 * @version 1.0
 */
public final class DataFrame_Copy {
    
    /**
     * Versitility function, arguments in the string array passed to the method will be parsed, and a shallow copy of the data frame with rows
     * that meet the specified arguments will be returned, can take multiple arguments.
     * ARGUMENT REQUIREMENTS: Should be in groups of three indexes, with index 0 being the name of the specified column, index 1 being the 
     * specified logical operator, and index 2 being the object to make comparisons on, which can then be parsed and have it's own particle created
     * (refer to particle heiarchy). 
     * Example arguments: 
     * One total argument will consist of: {"ColumnName", "LogicalOperator", "ObjectToCompare"} or {"Column A", "<", "40"}.
     * Two arguments will conisit of:  {"ColumnName1", "LogicalOperator1", "ObjectToCompare1", "ColumnName2", "LogicalOperator2", "ObjectToCompare2"}
     * @param args the arguments for the function ({"ColumnName", "LogicalOperator", ObjectToCompare"}). 
     * @return a new data frame with rows fitting the arguments.
     */
    public static DataFrame acquire(DataFrame theDataFrame, String[] args) {
        Set<Integer> rowIndexes = new TreeSet<Integer>();
        for (int i = 0; i < args.length; i += 3) {
            Column column = theDataFrame.getColumn_byName(args[i]);
            String operator = args[i + 1];
            Particle particle = Particle.resolveType(args[i + 2]);
            
            for (int j = 0; j < column.getLength(); j++) {
                int compare = column.getParticle(j).compareTo(particle);
                switch (operator) {
                    case "<":
                        if (compare < 0)
                            rowIndexes.add(j);
                        break;
                    case "<=":
                        if (compare <= 0)
                            rowIndexes.add(j);
                        break;
                    case ">":
                        if (compare > 0)
                            rowIndexes.add(j);
                        break;
                    case ">=":
                        if (compare >= 0)
                            rowIndexes.add(j);
                        break;
                    case "==":
                        if (compare == 0)
                            rowIndexes.add(j);
                        break;
                }
            }
        }
        return DataFrame_Copy.shallowCopy_rowIndexes(theDataFrame, rowIndexes);
    }
    /**
     * Deep copy of a dataframe
     * @param theDataFrame
     * @return
     */
    public static DataFrame deepCopy(DataFrame df) {
        DataFrame newdf = new DataFrame();
        for (int i = 0; i < df.numColumns; i++) {
       		newdf.addColumn(new Column(df.getColumn(i)));
        }
        return newdf;
    }
    /**
     * Shallow copy of a dataframe
     * @param theDataFrame
     * @return
     */
    public static DataFrame shallowCopy(DataFrame theDataFrame) {
        DataFrame newDataFrame = new DataFrame();
        for (String name : theDataFrame.columnNames) {
            Column c = theDataFrame.getColumn_byName(name);
            newDataFrame.addColumn(c);
        }
        return newDataFrame;
    }
  //TODO for some reason calling this does not include the first row in the new copied dataframe
    
    /**
     * Creates a new shallow copied data frame internally from a list of column names.
     * @param theDataFrame the data frame to copy.
     * @param columnNames the names of the columns to be added to the new data frame
     * @return a new DataFrame consisting of the columns passed to the method.
     */
     public static DataFrame deepCopy_columnIndexes(DataFrame theDataFrame, Collection<Integer> columnIndexes) {
         DataFrame newDataFrame = new DataFrame();
         for (Integer i : columnIndexes) {
        		newDataFrame.addColumn(new Column(theDataFrame.getColumn(i)));
         }
         return newDataFrame;
     }
    
    /**
     * Creates a new deep copied data frame internally from a list of column names.
     * @param theDataFrame the data frame to copy.
     * @param columnNames the list of column name.
     * @return the newly created data frame.
     */
    public static DataFrame deepCopy_columnNames(DataFrame theDataFrame, Collection<String> columnNames) {
        DataFrame newDataFrame = new DataFrame();
        for (String name : columnNames) { // Create the columns
        	newDataFrame.addColumn(new Column(theDataFrame.getColumn_byName(name)));
        }
        return newDataFrame;
    }
    
    /**
     * Creates a new shallow copied data frame internally from a list of column names.
     * @param theDataFrame the data frame to copy.
     * @param columnNames the names of the columns to be added to the new data frame
     * @return a new DataFrame consisting of the columns passed to the method.
     */
    public static DataFrame deepCopy_columnTypes(DataFrame theDataFrame, Collection<Character> columnTypes) {
        DataFrame newDataFrame = new DataFrame();
        for (int i = 0; i < theDataFrame.getNumColumns(); i++) {
            	newDataFrame.addColumn(new Column(theDataFrame.getColumn(i)));
        }
        return newDataFrame;
    }

    /**
     * Creates a new deep copied data frame internally from a list of row indexes.
     * @param theDataFrame the data frame to copy.
     * @param rowIndexes the set of row indexes to create a new data frame with.
     * @return a new DataFrame consisting of the rows passed to the method.
     */
    public static DataFrame deepCopy_rowIndexes(DataFrame theDataFrame, Collection<Integer> rowIndexes) {
        DataFrame newDataFrame = new DataFrame();
        for (int i = 0; i < theDataFrame.getNumColumns(); i++) { //Initialize blank columns in new data frame.
            newDataFrame.addBlankColumn(theDataFrame.getColumn(i).getName(), 
                    theDataFrame.getColumn(i).getType());
        }
        for (Integer rowIndex : rowIndexes) {
            newDataFrame.addRow(new Row(theDataFrame.getRow_byIndex(rowIndex)));
        }
        newDataFrame.setStatistics();
        return newDataFrame;
    }
    
    /**
     * Method to get a data frame with all columns excluding the specified column indexes in the parameter's list.
     * Uses a set (prefferably treeset) for nlog(n) element access time (contains()).
     * @param columnIndex the list of column indexes to exclude.
     * @return a new (shallow copied) data frame with the specified columns excluded.
     */
    public static DataFrame exclude(DataFrame theDataFrame, Collection<Integer> columnIndexes) {
        Set<Integer> indexSet = new TreeSet<Integer>();
        for (int i = 0; i < theDataFrame.getNumColumns(); i++) {
            if (!columnIndexes.contains(i))
                  indexSet.add(i);
          }
        return shallowCopy_columnIndexes(theDataFrame, indexSet);
    }
     
    /**
      * Method to get a data frame with all columns excluding the specified column index.
      * @param columnIndex the list of column indexe to exclude.
      * @return a new (shallow copied) data frame with the specified column excluded.
      */
    public DataFrame exclude(DataFrame theDataFrame, int columnIndex) {
        Set<Integer> indexSet = new TreeSet<Integer>();
        for (int i = 0; i < theDataFrame.getNumColumns(); i++) {
            if (i != columnIndex)
                indexSet.add(i);
        }
        return shallowCopy_columnIndexes(theDataFrame, indexSet);
    }
    
    /**
     * Loops through the entire data frame to check if the data frame is square (could be optimized).
     * @return true if the data frame is "square" (n x n), false otherwise.
     */
    public static boolean isSquare(DataFrame theDataFrame) {
        Set<Integer> rowLengths = new TreeSet<Integer>();
        Set<Integer> columnLengths = new TreeSet<Integer>();
        for (int i = 0; i < theDataFrame.getNumRows(); i++)
            rowLengths.add(theDataFrame.getRow_byIndex(i).getLength());
        for (int i = 0; i < theDataFrame.getNumColumns(); i++)
            columnLengths.add(theDataFrame.getColumn(i).getLength());
        return (rowLengths.equals(columnLengths)) && theDataFrame.getNumRows() == theDataFrame.getNumColumns();
    }
    

    
    /**
     * Creates a new shallow copied data frame internally from a list of column names.
     * @param theDataFrame the data frame to copy.
     * @param columnNames the names of the columns to be added to the new data frame
     * @return a new DataFrame consisting of the columns passed to the method.
     */
    public static DataFrame shallowCopy_columnIndexes(DataFrame theDataFrame, Collection<Integer> columnIndexes) {
        DataFrame newDataFrame = new DataFrame();
        for (Integer i : columnIndexes) {
            newDataFrame.addColumn(theDataFrame.getColumn(i));
        }
        return newDataFrame;
    }
    
    /**
     * Creates a new shallow copied data frame internally from a list of column names.
     * @param theDataFrame the data frame to copy.
     * @param columnNames the names of the columns to be added to the new data frame
     * @return a new DataFrame consisting of the columns passed to the method.
     */
    public static DataFrame shallowCopy_columnNames(DataFrame theDataFrame, Collection<String> columnNames) {
        DataFrame newDataFrame = new DataFrame();
        for (String name : columnNames) {
            Column c = theDataFrame.getColumn_byName(name);
            newDataFrame.addColumn(c);
        }
        return newDataFrame;
    }
    
    /**
      * Creates a new shallow copied data frame internally from a list of column names.
      * @param theDataFrame the data frame to copy.
      * @param columnNames the names of the columns to be added to the new data frame
      * @return a new DataFrame consisting of the columns passed to the method.
      */
    public static DataFrame shallowCopy_columnTypes(DataFrame theDataFrame, Collection<Character> columnTypes) {
        DataFrame newDataFrame = new DataFrame();
        for (int i = 0; i < theDataFrame.getNumColumns(); i++) {
            if (columnTypes.contains(theDataFrame.getColumn(i).getType())) {
                newDataFrame.addColumn(theDataFrame.getColumn(i));
            }
        }
        return newDataFrame;
    }
    
    /**
     * Creates a new shallow copied data frame internally from a list of row indexes.
     * @param theDataFrame the data frame to copy.
     * @param rowIndexes the set for row indexes to create a new data frame with.
     * @return a new DataFrame consisting of the rows passed to the method.
     */
    public static DataFrame shallowCopy_rowIndexes(DataFrame theDataFrame, Collection<Integer> rowIndexes) {
        DataFrame newDataFrame = new DataFrame();
        for (int i = 0; i < theDataFrame.getNumColumns(); i++) 
            newDataFrame.addBlankColumn(theDataFrame.getColumn(i).getName(),
                    theDataFrame.getColumn(i).getType());
        for (Integer rowIndex : rowIndexes) {
            newDataFrame.addRow(theDataFrame.getRow_byIndex(rowIndex));
        }
        newDataFrame.setNumRows();
        newDataFrame.setStatistics();
        newDataFrame.targetIndexes = theDataFrame.targetIndexes;
        return newDataFrame;
    } 
    
    /**
     * Sorts a column by natural ordering a column.
     * @param columnIndex the index of the column to base sorting off of.
     * @return a data frame with sorted rows corresponding to the column indexes. 
     */
    public static void sortByColumn(DataFrame theDataFrame, int columnIndex) {
        PriorityQueue<Row> sortedRows = new PriorityQueue<Row>(theDataFrame.getNumRows(), new Comparator<Row>() {
            @Override
            public int compare(Row o1, Row o2) {
                return o1.getParticle(columnIndex).compareTo(o2.getParticle(columnIndex));
            }
        });
        for (int i = 0; i < theDataFrame.getNumRows(); i++) 
            sortedRows.add(theDataFrame.getRow_byIndex(i));
        for (int i = 0; i < theDataFrame.getNumRows(); i++)
            theDataFrame.replaceRow(i, sortedRows.remove());
    }
     
    /**
     * Split dataframe into n equal sections
     * @param n - number of new dataframes 
     * @return dataframe[] of shallow copiesshallow copies
     */
    public static ArrayList<DataFrame> split(DataFrame df, int n) {
        int interval = Math.floorDiv(df.getNumRows(), n);
        //shuffle(df);
        ArrayList<DataFrame> partitions = new ArrayList<DataFrame>();
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < df.getNumRows() -1; i += interval) {
            set.clear();
            for(int j = i; j < i + interval; j++) {
            	if(j >= df.getNumRows()) break;
                set.add(j); 
            }
            DataFrame temp = shallowCopy_rowIndexes(df, set);
            temp.setStatistics();
            partitions.add(temp);
        }
        return partitions;
    }
    public static void shuffle(DataFrame df) {
		Collections.shuffle(df.rows);
	}
}
