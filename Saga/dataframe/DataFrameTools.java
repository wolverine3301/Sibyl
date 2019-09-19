package dataframe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import particles.Particle;

/**
 * Static class for performing various operations with a data frame.
 * @author Cade
 *
 */
public final class DataFrameTools {
    
    
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
    public DataFrame acquire(DataFrame theDataFrame, String[] args) {
        Set<Integer> rowIndexes = new TreeSet<Integer>();
        for (int i = 0; i < args.length; i += 3) {
            Column column = theDataFrame.getColumn_byName(args[i]);
            String operator = args[i + 1];
            Particle particle = Particle.resolveType(args[i + 2]);
            for (int j = 0; j < column.getLength(); j++) {
                int compare = column.getParticle_atIndex(j).compareTo(particle);
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
        return DataFrameTools.dataFrameFromRows_ShallowCopy(theDataFrame, rowIndexes);
    }
    
    /**
     * Creates a new deep copied data frame internally from a list of column names.
     * @param theDataFrame the data frame to copy.
     * @param columnNames the list of column name.
     * @return the newly created data frame.
     */
    public static DataFrame dataFrameFromColumns_DeepCopy(DataFrame theDataFrame, List<String> columnNames) {
        DataFrame newDataFrame = new DataFrame();
        for (String name : columnNames) { // Create the columns
            Column c = new Column(theDataFrame.getColumn_byName(name));
            newDataFrame.addColumn(c);
        }
        newDataFrame.updateNumRows();
        for (int i = 0; i < newDataFrame.getNumRows(); i++) { // Initialize row pointers
            Row row = new Row(); 
            for (int j = 0; j < newDataFrame.getNumColumns(); j++) {
                row.addToRow(newDataFrame.getColumn_byIndex(j).getParticle_atIndex(i));
            }
            newDataFrame.addRow(row);
        }
        return newDataFrame;
    }
    
    /**
     * Creates a new shallow copied data frame internally from a list of column names.
     * @param theDataFrame the data frame to copy.
     * @param columnNames the names of the columns to be added to the new data frame
     * @return a new DataFrame consisting of the columns passed to the method.
     */
    public static DataFrame dataFrameFromColumns_ShallowCopy(DataFrame theDataFrame, List<String> columnNames) {
        DataFrame newDataFrame = new DataFrame();
        for (String name : columnNames) {
            Column c = theDataFrame.getColumn_byName(name);
            newDataFrame.addColumn(c);
        }
        newDataFrame.updateNumRows();
        for (int i = 0; i < newDataFrame.getNumRows(); i++) {
            Row row = new Row();
            for (int j = 0; j < newDataFrame.getNumColumns(); j++)
                row.addToRow(newDataFrame.getColumn_byIndex(j).getParticle_atIndex(i));
            newDataFrame.addRow(row);
        }
        return newDataFrame;
    }
    
   /**
     * Creates a new shallow copied data frame internally from a list of column names.
     * @param theDataFrame the data frame to copy.
     * @param columnNames the names of the columns to be added to the new data frame
     * @return a new DataFrame consisting of the columns passed to the method.
     */
    public static DataFrame dataFrameFromColumns_ShallowCopy(DataFrame theDataFrame, TreeSet<Character> columnTypes) {
        DataFrame newDataFrame = new DataFrame();
        for (Column c : theDataFrame.getColumns()) {
            if (columnTypes.contains(c.type)) {
                newDataFrame.addColumn(c);
            }
        }
        newDataFrame.updateNumRows();
        for (int i = 0; i < newDataFrame.getNumRows(); i++) {
            Row row = new Row();
            for (int j = 0; j < newDataFrame.getNumColumns(); j++)
                row.addToRow(newDataFrame.getColumn_byIndex(j).getParticle_atIndex(i));
            newDataFrame.addRow(row);
        }
        return newDataFrame;
    }
    
    /**
     * Creates a new shallow copied data frame internally from a list of column names.
     * @param theDataFrame the data frame to copy.
     * @param columnNames the names of the columns to be added to the new data frame
     * @return a new DataFrame consisting of the columns passed to the method.
     */
     public static DataFrame dataFrameFromColumns_ShallowCopy(DataFrame theDataFrame, List<Integer> columnInedexes) {
         DataFrame newDataFrame = new DataFrame();
         for (Integer i : columnIndexes) {
             
         }
         newDataFrame.updateNumRows();
         for (int i = 0; i < newDataFrame.getNumRows(); i++) {
             Row row = new Row();
             for (int j = 0; j < newDataFrame.getNumColumns(); j++)
                 row.addToRow(newDataFrame.getColumn_byIndex(j).getParticle_atIndex(i));
             newDataFrame.addRow(row);
         }
         return newDataFrame;
     }
    
    /**
     * Creates a new deep copied data frame internally from a list of row indexes.
     * @param theDataFrame the data frame to copy.
     * @param rowIndexes the set of row indexes to create a new data frame with.
     * @return a new DataFrame consisting of the rows passed to the method.
     */
    public static DataFrame dataFrameFromRows_DeepCopy(DataFrame theDataFrame, Set<Integer> rowIndexes) {
        DataFrame newDataFrame = new DataFrame();
        for (Column c : theDataFrame.getColumns()) { //Initialize blank columns in new data frame.
            newDataFrame.add_blank_Column(c.name, c.type);
        }
        for (Integer rowIndex : rowIndexes) {
            Row r = new Row(theDataFrame.getRow_byIndex(rowIndex));
            newDataFrame.getRows().add(r);
            for (int i = 0; i < r.getlength(); i++)
                newDataFrame.getColumn_byIndex(i).addToColumn(r.getParticle(i));
        }
        newDataFrame.updateNumRows();
        return newDataFrame;
    }
    
    /**
     * Creates a new shallow copied data frame internally from a list of row indexes.
     * @param theDataFrame the data frame to copy.
     * @param rowIndexes the set for row indexes to create a new data frame with.
     * @return a new DataFrame consisting of the rows passed to the method.
     */
    public static DataFrame dataFrameFromRows_ShallowCopy(DataFrame theDataFrame, Set<Integer> rowIndexes) {
        DataFrame newDataFrame = new DataFrame();
        for (Column c : theDataFrame.getColumns()) 
            newDataFrame.add_blank_Column(c.name, c.type);
        for (Integer rowIndex : rowIndexes) {
            Row r = theDataFrame.getRow_byIndex(rowIndex);
            newDataFrame.getRows().add(r);
            for (int i = 0; i < r.getlength(); i++) {
                newDataFrame.getColumn_byIndex(i).addToColumn(r.getParticle(i));
            }
        }
        newDataFrame.updateNumRows();
        return newDataFrame;
    }
    
    
    /**
     * Method to get a data frame with all columns excluding the specified column indexes in the parameter's list.
     * Uses a set (prefferably treeset) for nlog(n) element access time (contains()).
     * @param columnIndex the list of column indexes to exclude.
     * @return a new (shallow copied) data frame with the specified columns excluded.
     */
    public static DataFrame exclude(DataFrame theDataFrame, Set<Integer> columnIndexes) {
        Set<Integer> columnNames = new TreeSet<Integer>();
        for (int i = 0; i < theDataFrame.getNumColumns(); i++) {
            if (!columnIndexes.contains(i))
                columnNames.add(columns.get(i).name);
        }
        return dataFrameFromColumns_ShallowCopy(theDataFrame, columnNames);
    }
    
   /**
     * Method to get a data frame with all columns excluding the specified column index.
     * @param columnIndex the list of column indexe to exclude.
     * @return a new (shallow copied) data frame with the specified column excluded.
     */
    public DataFrame exclude(int columnIndex) {
        List<String> columnNames = new ArrayList<String>();
        for (int i = 0; i < numColumns; i++) {
            if (i != columnIndex)
                columnNames.add(columns.get(i).name);
        }
        return dataFrameFromColumns_ShallowCopy(columnNames);
    }
    
    /**
     * Split dataframe into n equal sections
     * @param n - number of new dataframes 
     * @return dataframe[] of shallow copiesshallow copies
     */
    public DataFrame[] split(DataFrame theDataFrame, int n) {
        int interval = Math.floorDiv(theDataFrame.getNumRows(), n-1);
        DataFrame[] partitions = new DataFrame[n-1];
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < theDataFrame.getNumRows() -1; i += interval-1) {
            set.clear();
            for(int j = i+1;j <= i+interval-1; j++) {
                if(i == 0) {set.add(0);}
                set.add(j); 
            }
        }
        return partitions;
    }
}
