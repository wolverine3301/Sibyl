package dataframe;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import particles.DoubleParticle;
import particles.IntegerParticle;
import particles.NANParticle;
import particles.Particle;
/**
 * DataFrame
 * the main object for data manipulation, most functions and all models will contructed with his object as input
 * Its structure is a array list of column objects which themselves are generic array list representing columns in a table
 * 
 * @author logan.collier
 * @author Cade Reynoldson
 */
public class DataFrame {
	
    /** The names of the columns */
	private ArrayList<String> columnNames;
	
	/** The type of each column */
	private ArrayList<Character> columnTypes;
	
	/** The ArrayList of columns */
	private ArrayList<Column> columns;
	
	/** The ArrayList of rows */
	private ArrayList<Row> rows;
	
	/** The number of rows in the data frame */
	private int numRows;
	
	/** The number of columns in the data frame */
	private int numColumns;
	
	/**
	 * Create a new, empty data frame.
	 */
	public DataFrame() {
		this.columns = new ArrayList<Column>();
		this.rows = new ArrayList<Row>();
		this.columnNames = new ArrayList<String>();
		this.columnTypes = new ArrayList<Character>();
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
                	columns.get(i).setType(p.getType());
                	columns.get(i).add(p);
                	row.addToRow(p);
                }
                rows.add(row);
        	}
        	for(int i = 0;i < columnNames.size();i++) {
        	    getColumn_byIndex(i).resolveType();
                columnTypes.add(columns.get(i).getType());
        	}
        	this.numRows = rows.size();
        	this.numColumns = columns.size();
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}
	
	/**
	 * Converts all NANS in the data frame to their mean value, excluding non numeric values, which will
	 * be converted to the most occouring value in the data frame.
	 */
	public void convertNANS_mean() {
        for (int i = 0; i < numColumns; i++) {
            Column c = getColumn_byIndex(i);
            for (int j = 0; j < numRows; j++) {
                Particle p = c.getParticle(j);
                if (p instanceof NANParticle) {
                    if (c.getType() == 'i')
                        p = Particle.resolveType((int) Math.round(ColumnTools.mean(c)));
                    else if (getColumn_byIndex(i).getType() == 'd')
                        p = Particle.resolveType(ColumnTools.mean(c));
                    else
                        p = Particle.resolveType(ColumnTools.mode(c));
                    getColumn_byIndex(i).changeValue(j, p);
                    getRow_byIndex(j).changeValue(i, p);
                }
            }
        }
	}
	
	/**
	 * Converts all NANS in the data frame to their mode value.
	 */
	public void convertNANS_mode() {
	    for (int i = 0; i < numColumns; i++) {
	        for (int j = 0; j < numRows; j++) {
	            Particle p = getColumn_byIndex(i).getParticle(j);
	            if (p instanceof NANParticle) {
	                p = Particle.resolveType(ColumnTools.mode(getColumn_byIndex(i)));
	                getColumn_byIndex(i).changeValue(j, p);
	                getRow_byIndex(j).changeValue(i, p);
	            }
	        }
	    }
	}
    
    /**
     * Updates the number of rows in this column. Note: this does not create new rows when changing the size.
     */
    public void updateNumRows() {
        if (numRows == rows.size())
            return;
        else if (numRows < rows.size())
            numRows = rows.size();
        else if (numRows == 0 && numColumns != 0)
            numRows = getColumn_byIndex(0).getLength();
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
    	c.add(p);
    	rows.get(0).addToRow(p);
    	for(int i = 1; i < arr.length;i++) {
    		p = Particle.resolveType(arr[i]);
            rows.get(i).addToRow(p);
    		c.add(p);
    	}
    	columnNames.add(name);
    	columnTypes.add(c.getType());
    	columns.add(c);
    	numColumns++;
    }
    
    public void addColumnName(String name) {
        if (columnNames.size() == columns.size() - 1)
            columnNames.add(name);
        else
            throw new IllegalArgumentException("Addition of new column name would cause uneven column names length vs column length.");
    }
    
    public void addColumnType(char type) {
        if (columnTypes.size() == columns.size() - 1)
            columnTypes.add(type);
        else
            throw new IllegalArgumentException("Addition of new column name would cause uneven column types length vs column length.");
    }
    
    /**
     * Adds a row to the data frame from an array. Mostly used by the distance matrix method.
     * @param arr the new row to be added. 
     */
    public void addRowFromArray(Object arr[]) { //THIS NEEDS WORK! 
        Particle p = Particle.resolveType(arr[0]);
        Row r = new Row();
        r.addToRow(p);
        columns.get(0).add(p);
        for (int i = 1; i < arr.length; i++) {
            p = Particle.resolveType(arr[i]);
            columns.get(i).add(p);
            r.addToRow(p);
        }
        rows.add(r);
        numRows++;
        
    }
    
    /**
     * Adds a row to the data frame. Assumes columns for the row to initialize have already been created.
     * @param r the row to be added.
     */
    public void addRow(Row r) {
        rows.add(r);
        numRows++;
        for (int i = 0; i < r.getLength(); i++) 
            columns.get(i).add(r.getParticle(i));
    }
    
    /**
     * Adds a column to the data frame.
     * @param c the column to be added.
     */
    public void addColumn(Column c) {
        columns.add(c);
        columnNames.add(c.getName());
        columnTypes.add(c.getType());
        numColumns++;
        for (int i = 0; i < c.getLength(); i++) {
            try {
                rows.get(i).addToRow(c.getParticle(i));
            } catch (Exception e) {
                rows.add(new Row());
                numRows++;
                rows.get(i).addToRow(c.getParticle(i));
            }
        }
    }

    /**
	 * Adds a new empty column to the data frame.
	 * @param name The name of the new column.
	 */
	public void addBlankColumn(String name) {
		Column c = new Column(name);
		columnNames.add(name);
		columnTypes.add('n');
		columns.add(c);
		numColumns++;
	}
	
	/**
	 * Adds a new empty column of a certain data type to the data frame.
	 * @param name the name of the new column.
	 * @param dataType the data type of the new column.
	 */
	public void addBlankColumn(String name, char dataType) {
	    Column c = new Column(name, dataType);
	    columnNames.add(name);
	    columnTypes.add(dataType);
	    columns.add(c);
	    numColumns++;
	}
	
	/**
	 * Replaces a particle at a given row index (position in row) and column index (position in column).
	 * @param rowIndex the position of the particle in the row being replaced.
	 * @param columnIndex the position of the particle in the column being replaced.
	 * @param p the new particle to be inserted.
	 */
	public void replace(int rowIndex, int columnIndex, Particle p) {
	    if ((rowIndex >= numColumns) || (rowIndex < 0) || ((columnIndex >= numRows) || (columnIndex < 0)))
	        throw new IllegalArgumentException("Invalid indexes to be replaced - \nRow Index: " + rowIndex + 
	                        "\nLength of rows: " + numColumns + "\nColumn Index: " + columnIndex + "Length of columns: " + numRows);
	    else if ((p instanceof DoubleParticle || p instanceof IntegerParticle)
	            && (columns.get(columnIndex).getType() == 'i') || columns.get(columnIndex).getType() == 'd') {
	        rows.get(columnIndex).changeValue(rowIndex, p);
	        columns.get(rowIndex).changeValue(columnIndex, p);
	    } else if (columns.get(columnIndex).getType() == p.type) {
	        throw new IllegalArgumentException("Particle to be replaced does not match the column's type.\nColumn type: " 
	                            + columns.get(rowIndex).getType() + "\nParticle Type: " + p.type);
	    } 
	}
	
	/**
	 * Returns an array of the column names from the data frame.
	 * @return an array of the column names from the data frame.
	 */
	public ArrayList<String> getColumnNames() {
		return columnNames;
	}
	
	/**
	 * Returns the amount of rows in the data frame.
	 * @return the amount of rows in the data frame.
	 */
	public int getNumRows() {
		return numRows;
	}
	
	/**
	 * Returns the number of columns in the data frame.
	 * @return the amount of columns in the data frame.
	 */
	public int getNumColumns() {
	    return numColumns;
	}
	
	/**
	 * Returns the names of the columns in the data frame in a single string.
	 * @return the names of the columns in the data frame in a single string.
	 */
	public String columnNamesToString() {
		return columnNames.toString();
	}
	
	/**
	 * Set column to certain type given the column's name.
	 * @param columnName the name of the column.
	 * @param newType the new data type of the column.
	 */
	public void setColumnType(String columnName, char newType) {
		int index = 0;
		for(int i = 0; i < columnNames.size(); i++) {
			if(columnNames.get(i).contentEquals(columnName)){
				index = i;
				break;
			}
		}
		getColumn_byIndex(index).setType(newType);
		columnTypes.set(index, newType);
	}
	
	/**
	 * Set a column at a specified index's data type. 
	 * @param columnIndex the index of the column.
	 * @param newType the new data type of the column.
	 */
	public void setColumnType(int columnIndex, char newType) {
        getColumn_byIndex(columnIndex).setType(newType);
        columnTypes.set(columnIndex, newType);
    }
    
	/**
	 * returns a list of columns of the specified type
	 * @param String type
	 * @return List<Column>
	 */
	public List<Column> getColumnByTypes(char type){
		List<Column> cols = new ArrayList<Column>();
		for(Column i : columns) {
			if(i.getType() == type) {
				cols.add(i);
			}
		}
		return cols;
	}
	
    /**
     * Prints the data frame.
     */
    public void printDataFrame() {
        for(int i = 0; i < columnNames.size(); i++) {
            System.out.print(columnNames.get(i) + " ");
        }
        System.out.println();
        for(int z = 0 ; z < numRows; z++) {
            rows.get(z).printRow();
            System.out.println();
        }
    }
    
}
