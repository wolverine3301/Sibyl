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
 * @author Cade Reynoldson
 * @param <T>
 */
public class DataFrame <T>{
	
	public List<String> columnNames;
	public List<String> columnTypes;
	public ArrayList<Column<OldParticle<T>>> columns;
	public ArrayList<Row<OldParticle<T>>> rows;
	public int numRows;
	public int numColumns;
	
	
	/**
	 * Constructor
	 */
	public DataFrame() {
		this.columns = new ArrayList<Column<OldParticle<T>>>();
		this.rows = new ArrayList<Row<OldParticle<T>>>();
		this.columnNames = new ArrayList<String>();
		this.columnTypes = new ArrayList<String>();
		numRows = 0;
		numColumns = 0;
		
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
	public void loadcsv(String file) {
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        	line =  br.readLine(); //get column names
        	String[] colNames = line.split(cvsSplitBy);
        	//fill column names and types
        	for(int i = 0;i < colNames.length;i++) {
        		columnNames.add(colNames[i]);
        	}
        	// initializing column objects
            for (int i = 0; i < columnNames.size(); i++) {
            	Column<OldParticle<T>> c = new Column<OldParticle<T>>(columnNames.get(i));
            	columns.add(c);
            } //end initializing
            int count = 0;
            //read lines
        	while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] lines = line.split(cvsSplitBy);
                
                Row<OldParticle<T>> row = new Row<OldParticle<T>>();
                
                //load data into columns and rows
                for(int i=0;i<columnNames.size();i++) {
                	OldParticle<T> p = new OldParticle<T>( (T) lines[i]);
                	columns.get(i).addToColumn(p);
                	row.addToRow(p);
                	
                }//end for loop
                rows.add(row);
                count++;
        	}//end while read lines
        	//fill column types
        	for(int i = 0;i < columnNames.size();i++) {
            	columnTypes.add(columns.get(i).type);
            }//end for loop
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
	    for (String name : columnNames) { // Create the columns
	        Column<OldParticle<T>> currentColumn = getColumn_byName(name);
	        newDataFrame.columnNames.add(name);
	        newDataFrame.columnTypes.add(currentColumn.type);
	        newDataFrame.columns.add(new Column<OldParticle<T>>(currentColumn));
	    }	    
	    newDataFrame.numColumns = newDataFrame.columns.size();
	    newDataFrame.numRows = newDataFrame.columns.get(0).getLength();
	    for (int i = 0; i < newDataFrame.numRows; i++) {
	        Row<OldParticle<T>> row = new Row<OldParticle<T>>(); 
	        for (int j = 0; j < newDataFrame.numColumns; j++) {
	            row.addToRow(newDataFrame.columns.get(j).getParticle_atIndex(i));
	        }
	        newDataFrame.rows.add(row);
	    }
	    return newDataFrame;
	}
	
	/**
	 * NOT WORKING
	 * @param columnIndex
	 * @param rowIndex
	 * @param newValue
	 */
	public void changeParticleValue(int columnIndex, int rowIndex, T newValue) {
	    OldParticle<T> temp = (OldParticle<T>) rows.get(rowIndex).row.get(columnIndex);
	    temp.changeValue(newValue);
	}
	
	public Row<OldParticle<T>> getRow_byIndex(int index) {
	    return rows.get(index);
	}
	
	
	/**
	 * getColumn returns a single column from dataframe
	 * @param name - name of column
	 * @return
	 */
	public Column<OldParticle<T>> getColumn_byName(String name){
		int index = 0;
		for(int i = 0; i < columnNames.size(); i++) {
			if(columnNames.get(i).contentEquals(name)){
				index = i;
				break;
			}
		}
		return columns.get(index);
	}
	
   /**
     * Returns the column at a specified index.
     * @param index the index of the column.
     * @return the column at the index.
     */
    public Column<OldParticle<T>> getColumn_byIndex(int index) {
        return columns.get(index);
    }
    
    /**
     * add a column from an array
     * 
     * @param name
     * @param type
     * @param arr
     */
    public void addColumnFromArray(String name, T arr[]) {
    	OldParticle<T> p = new OldParticle<T>(arr[0]);
    	Column<OldParticle<T>> c = new Column<OldParticle<T>>(name, p.type);
    	c.addToColumn(p);
    	rows.get(0).addToRow(p);
    	for(int i = 1; i < arr.length;i++) {
    		p = new OldParticle<T>(arr[i]);
            rows.get(i).addToRow(p);
    		c.addToColumn(p);
    	}
    	columnNames.add(name);
    	columnTypes.add(c.type);
    	columns.add(c);
    	numColumns++;
    }
    
    
    /**
     * Adds a row to the data frame from an array. Mostly used by the distance matrix method.
     * @param arr 
     */
    public void addRowFromArray(T arr[]) {
        System.out.print("ROW CREATED: ");
        OldParticle<T> p = new OldParticle<T>(arr[0]);
        Row<OldParticle<T>> r = new Row<OldParticle<T>>();
        r.addToRow(p);
        columns.get(0).addToColumn(p);
        for (int i = 1; i < arr.length; i++) {
            p = new OldParticle<T>(arr[i]);
            columns.get(i).addToColumn(p);
            r.addToRow(p);
        }
        System.out.print("ROW CREATED: ");
        r.printRow();
        rows.add(r);
        numRows++;
        
    }

    /**
	 * add_blank_Column - adds a new empty column to dataframe
	 * @param name
	 * @param type
	 */
	public void add_blank_Column(String name) {
		Column<OldParticle<T>> c = new Column<OldParticle<T>>(name);
		columnNames.add(name);
		columnTypes.add("NAN");
		columns.add(c);
		
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
	
	
	public String columnNamesToString() {
		return columnNames.toString();
	}
	
	/**
	 * print the dataframe
	 */
	public void printDataFrame() {
		for(int i = 0; i < columnNames.size(); i++) {
			System.out.print(columnNames.get(i) + " ");
		}
		System.out.println();
		for(int z = 0 ;z < numRows; z++) {
			rows.get(z).printRow();
			System.out.println();
		}
	}

}
