package saga;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
/**
 * DataFrame
 * the main object for data manipulation, most functions and all models will contructed with his object as input
 * Its structure is a array list of column objects which themselves are generic array list representing columns in a table
 * 
 * @author logan.collier
 *
 * @param <T>
 */
public class DataFrame <T>{
	
	public List<String> columnNames;
	public List<String> columnTypes;
	public int numRows;
	public ArrayList<Column> dataframe;
	
	/**
	 * Constructor
	 */
	public DataFrame() {
		this.dataframe = new ArrayList<Column>();
		this.columnNames = new ArrayList<String>();
		this.columnTypes = new ArrayList<String>();
	}
	
	/**
	 * loadcsv
	 * construct a dataframe directly from a csv file, auto assumes there is a header line which it uses as the column names
	 * inputs:
	 *
	 * @param file  - csv file name or path
	 * @param types - an array of strings to set the type attribute of the column, needs work and automization but will be usefully
	 * for managing which functions to use on which type of column
	 * 
	 */
	public void loadcsv(String file,String[] types) {
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        	line =  br.readLine(); //get column names
        	String[] colNames = line.split(cvsSplitBy);
        	//fill column names and types
        	for(int i = 0;i < colNames.length;i++) {
        		columnNames.add(colNames[i]);
        		columnTypes.add(types[i]);
        	}
        	// initializing column objects
            for (int i = 0; i < columnNames.size(); i++) {
            	Column<T> c = new Column<T>(columnNames.get(i),types[i]);
            	dataframe.add(c);
            } //end initializing
            int count = 0;
            //read lines
        	while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] row = line.split(cvsSplitBy);
                //load data into array list
                for(int i=0;i<columnNames.size();i++) {
                	dataframe.get(i).add((T) row[i]);
                }//end for loop
                count++;
        	}//end while
        	this.numRows = count;
        	
        } catch (IOException e) {
        	e.printStackTrace();
        }

	}
	
	/**
	 * Creates a new data frame internally from a list of column names.
	 * @param columnNames the list of column name.
	 * @return the newly created data frame.
	 */
	public DataFrame<T> dataFrameFromColumns(List<String> columnNames) {
	    DataFrame<T> newDataFrame = new DataFrame<T>();
	    for (String name : columnNames) {
	        Column<T> currentColumn = getColumn_byName(name);
	        newDataFrame.columnNames.add(name);
	        newDataFrame.columnTypes.add(currentColumn.type);
	        newDataFrame.dataframe.add(new Column<T>(currentColumn));
	    }
	    return newDataFrame;
	}
	
	/**
	 * getColumn returns a single column from dataframe
	 * @param name - name of column
	 * @return
	 */
	public Column<T> getColumn_byName(String name){
		int index = 0;
		for(int i = 0; i < columnNames.size(); i++) {
			if(columnNames.get(i).contentEquals(name)){
				index = i;
				break;
			}
		}
		return dataframe.get(index);
	}
	
   /**
     * Returns the column at a specified index.
     * @param index the index of the column.
     * @return the column at the index.
     */
    public Column<T> getColumn_byIndex(int index) {
        return dataframe.get(index);
    }
    /**
     * add a column from an array
     * 
     * @param name
     * @param type
     * @param arr
     */
    public void addColumnFromArray(String name,String type, T arr[]) {
    	Column c = new Column(name,type);
    	c.concatArray(arr);

    	//update list
    	columnNames.add(name);
    	columnTypes.add(type);
    	dataframe.add(c);
    }

    /**
	 * add_blank_Column - adds a new empty column to dataframe
	 * @param name
	 * @param type
	 */
	public void add_blank_Column(String name, String type) {
		Column<T> c = new Column<T>(name,type);
		columnNames.add(name);
		columnTypes.add(type);
		dataframe.add(c);
		
	}
	
	/**
	 * getColumnNames - returns column names in a string
	 * @return
	 */
	public String[] getColumnNames() {
		String[] names = new String[columnNames.size()];
		for(int i = 0; i < columnNames.size();i++) {
			names[i] = columnNames.get(i);
		}
		return names;
	}
	
	/**
	 * getLength - returns number of rows in dataframe
	 * @return
	 */
	public int getLength() {
		return numRows;
	}

}
