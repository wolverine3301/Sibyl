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
public class DataFrame {
	
    /** The names of the columns */
	public List<String> columnNames;
	
	/** The type of each column */
	public List<String> columnTypes;
	
	/** The ArrayList of columns */
	public ArrayList<Column> columns;
	
	/** The ArrayList of rows */
	public ArrayList<Row> rows;
	
	/** The number of rows in the data frame */
	public int numRows;
	
	/** The number of columns in the data frame */
	public int numColumns;
	
	/**
	 * Create a new, empty data frame.
	 */
	public DataFrame() {
		this.columns = new ArrayList<Column>();
		this.rows = new ArrayList<Row>();
		this.columnNames = new ArrayList<String>();
		this.columnTypes = new ArrayList<String>();
		numRows = 0;
		numColumns = 0;
		
	}
	
	/**
	 * Construct a dataframe directly from a csv file, auto assumes there is a header line which it uses as the column names.
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
        	for(int i = 0;i < colNames.length;i++) //Initialize column names.
        		columnNames.add(colNames[i]);
            for (int i = 0; i < columnNames.size(); i++) { // initializing column objects
            	Column c = new Column(columnNames.get(i));
            	columns.add(c);
            }
        	while ((line = br.readLine()) != null) { //Read in each line, create row objects and initialize data.
                String[] lines = line.split(cvsSplitBy);
                Row row = new Row();
                for(int i=0;i<columnNames.size();i++) { //load data into columns and rows
                	Particle p = Particle.resolveType(lines[i]);
                	columns.get(i).type = p.getType();
                	columns.get(i).addToColumn(p);
                	row.addToRow(p);
                }
                rows.add(row);
        	}
        	for(int i = 0;i < columnNames.size();i++) //Initialize column types.
            	columnTypes.add(columns.get(i).type);
        	this.numRows = rows.size();
        	this.numColumns = columns.size();
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}
	
	/**
	 * Creates a new data frame internally from a list of column names.
	 * @param columnNames the list of column name.
	 * @return the newly created data frame.
	 */
	public DataFrame dataFrameFromColumns(List<String> columnNames) {
	    DataFrame newDataFrame = new DataFrame();
	    for (String name : columnNames) { // Create the columns
	        Column currentColumn = getColumn_byName(name);
	        newDataFrame.columnNames.add(name);
	        newDataFrame.columnTypes.add(currentColumn.type);
	        newDataFrame.columns.add(new Column(currentColumn));
	    }	    
	    newDataFrame.numColumns = newDataFrame.columns.size();
	    newDataFrame.numRows = newDataFrame.columns.get(0).getLength();
	    for (int i = 0; i < newDataFrame.numRows; i++) {
	        Row row = new Row(); 
	        for (int j = 0; j < newDataFrame.numColumns; j++) {
	            row.addToRow(newDataFrame.columns.get(j).getParticle_atIndex(i));
	        }
	        newDataFrame.rows.add(row);
	    }
	    return newDataFrame;
	}
	
	/**
	 * Returns an indexed row from the data frame.
	 * @param index the index of the row.
	 * @return A row at a specified index.
	 */
	public Row getRow_byIndex(int index) {
	    return rows.get(index);
	}
	
	
	/**
	 * getColumn returns a single column by name from the dataframe.
	 * @param name The name of the desired column.
	 * @return
	 */
	public Column getColumn_byName(String name){
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
    public Column getColumn_byIndex(int index) {
        return columns.get(index);
    }
    
    
    /**
     * Add a new column from an array.
     * @param name The name of the column.
     * @param arr The array to be added into a column.
     */
    public void addColumnFromArray(String name, Object arr[]) {
    	Particle p = Particle.resolveType(arr[0]);
    	Column c = new Column(name, p.type);
    	c.addToColumn(p);
    	rows.get(0).addToRow(p);
    	for(int i = 1; i < arr.length;i++) {
    		p = Particle.resolveType(arr[i]);
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
     * @param arr the new row to be added. 
     */
    public void addRowFromArray(Object arr[]) { //THIS NEEDS WORK! 
        Particle p = Particle.resolveType(arr[0]);
        Row r = new Row();
        r.addToRow(p);
        columns.get(0).addToColumn(p);
        for (int i = 1; i < arr.length; i++) {
            p = Particle.resolveType(arr[i]);
            columns.get(i).addToColumn(p);
            r.addToRow(p);
        }
        System.out.print("ROW CREATED: ");
        r.printRow();
        rows.add(r);
        numRows++;
        
    }

    /**
	 * Adds a new empty column to the data frame.
	 * @param name The name of the new column.
	 */
	public void add_blank_Column(String name) {
		Column c = new Column(name);
		columnNames.add(name);
		columnTypes.add("NAN");
		columns.add(c);
	}
	
	/**
	 * Returns an array of the column names from the data frame.
	 * @return an array of the column names from the data frame.
	 */
	public String[] getColumnNames() {
		String[] names = new String[columnNames.size()];
		for(int i = 0; i < columnNames.size();i++) {
			names[i] = columnNames.get(i);
		}
		return names;
	}
	
	/**
	 * Returns the amount of rows in the data frame.
	 * @return the amount of rows in the data frame.
	 */
	public int getLength() {
		return numRows;
	}
	
	/**
	 * Returns the names of the columns in the data frame in a single string.
	 * @return the names of the columns in the data frame in a single string.
	 */
	public String columnNamesToString() {
		return columnNames.toString();
	}
	
    /**
     * Prints the data frame.
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
