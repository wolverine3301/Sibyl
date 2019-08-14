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
	
	public List<String> columnNames;
	public List<String> columnTypes;
	public ArrayList<Column> columns;
	public ArrayList<Row<Particle>> rows;
	public int numRows;
	public int numColumns;
	
	
	/**
	 * Constructor
	 */
	public DataFrame() {
		this.columns = new ArrayList<Column>();
		this.rows = new ArrayList<Row<Particle>>();
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
            	Column c = new Column(columnNames.get(i));
            	columns.add(c);
            } //end initializing
            int count = 0;
            //read lines
        	while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] lines = line.split(cvsSplitBy);
                
                Row<Particle> row = new Row<Particle>();
                
                //load data into columns and rows
                for(int i=0;i<columnNames.size();i++) {
                	Particle p = new Particle(lines[i]);
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
	        Column currentColumn = getColumn_byName(name);
	        newDataFrame.columnNames.add(name);
	        newDataFrame.columnTypes.add(currentColumn.type);
	        newDataFrame.columns.add(new Column(currentColumn));
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
	
	public Row<Particle> getRow_byIndex(int index) {
	    return rows.get(index);
	}
	
	
	/**
	 * getColumn returns a single column from dataframe
	 * @param name - name of column
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
     * add a column from an array
     * @param name
     * @param type
     * @param arr
     */
    public void addColumnFromArray(String name, T arr[]) { //UPDATE
    	Particle p = new Particle(arr[0]);
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
    public void addRowFromArray(T arr[]) { //UPDATE
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
		Column c = new Column(name);
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
	 * TO DO: UPDATE FOR SUPPORT WITH ORDINAL & OBJECT PARTICLES.
	 * Resolves the type of a value from a string.
	 * @param value 
	 */
	private Particle resolveType(String value) {
        Particle newParticle;
        if(isNumeric(value)) { //If the passed string is numeric.
            if(isInteger(value))
                newParticle = new IntegerParticle(Integer.parseInt(value));
            else
                newParticle = new DoubleParticle(Double.parseDouble(value));
        } else { //If the passed string is just a string.
            String s = (String) value;
            if(value.isBlank() || value.toUpperCase().contentEquals("NAN") || value.toUpperCase().contentEquals("NULL")) 
                newParticle = new NANParticle(value);
            else 
                newParticle = new StringParticle(value);
        }
        return newParticle;
    }
	
	/**
     * is it an integer
     * @param strNum
     * @return
     */
    private boolean isInteger(String strNum) {
        try {
            Integer.parseInt( strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
    
    /**
     * is it a double
     * @param strNum
     * @return
     */
    private boolean isDouble(String strNum) {
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
    
    /**
     * is it a number
     * @param strNum
     * @return
     */
    private boolean isNumeric(String strNum) {
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
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
