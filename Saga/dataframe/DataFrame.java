package dataframe;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
	protected ArrayList<String> columnNames;
	
	/** The type of each column */
	protected ArrayList<Character> columnTypes;
	protected ArrayList<Integer> numericIndexes;
	
	/** The ArrayList of columns */
	protected ArrayList<Column> columns;
	
	/** The ArrayList of rows */
	protected ArrayList<Row> rows;
	
	/** The number of rows in the data frame */
	protected int numRows;
	
	/** The number of columns in the data frame */
	protected int numColumns;
	
	/**
	 * Create a new, empty data frame.
	 */
	public DataFrame() {
		this.columns = new ArrayList<Column>();
		this.rows = new ArrayList<Row>();
		this.columnNames = new ArrayList<String>();
		this.columnTypes = new ArrayList<Character>();
		this.numericIndexes = new ArrayList<Integer>();
		numRows = 0;
		numColumns = 0;
		
	}
	/*
	 * ##################################################################
	 * 
	 * 					DATAFRAME SETTERS
	 * 
	 * ##################################################################
	 */
    /**
     * Updates the number of rows in this column. Note: this does not create new rows when changing the size.
     */
    public void setNumRows() {
        if (numRows == rows.size())
            return;
        else if (numRows < rows.size())
            numRows = rows.size();
        else if (numRows == 0 && numColumns != 0)
            numRows = getColumn(0).getLength();
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
		getColumn(index).setType(newType);
		columnTypes.set(index, newType);
	}
	
	/**
	 * Set a column at a specified index's data type. 
	 * @param columnIndex the index of the column.
	 * @param newType the new data type of the column.
	 */
	public void setColumnType(int columnIndex, char newType) {
        getColumn(columnIndex).setType(newType);
        columnTypes.set(columnIndex, newType);
    }
	
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$//
	/*
	 * ##################################################################
	 * 
	 * 					DATAFRAME GETTERS
	 * 
	 * ##################################################################
	 */
    
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
    public Column getColumn(int index) {
        return columns.get(index);
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
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$//
	/*
	 * ##################################################################
	 * 
	 * 					DATAFRAME MANIPULATION FUNCTIONS
	 * 
	 * ##################################################################
	 */
    /**
     * Add a new column from an array.
     * @param name The name of the column.
     * @param arr The array to be added into a column.
     */
    public void addColumnFromArray(String name, Object arr[]) {
    	Particle p = Particle.resolveType(arr[0]);
    	Column c;
    	c = new Column(name,p.getType());
    	c.add(p);
    	rows.get(0).add(p);
    	for(int i = 1; i < arr.length;i++) {
    		p = Particle.resolveType(arr[i]);
            rows.get(i).add(p);
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
        r.add(p);
        columns.get(0).add(p);
        for (int i = 1; i < arr.length; i++) {
            p = Particle.resolveType(arr[i]);
            columns.get(i).add(p);
            r.add(p);
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
	 * Replaces a row in the data frame.
	 * @param index the index of the row to be replaced
	 * @param row the row to be added in place of the row at the passed index.
	 */
	public void replaceRow(int index, Row row) {
	    rows.set(index, row);
	    for (int i = 0; i < row.getLength(); i++) {
	        columns.get(i).changeValue(index, row.getParticle(i));
	    }
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
                rows.get(i).add(c.getParticle(i));
            } catch (Exception e) {
                rows.add(new Row());
                numRows++;
                rows.get(i).add(c.getParticle(i));
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
		Column c;
		c = new Column(name, dataType);
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
	public void replaceParticle(int rowIndex, int columnIndex, Particle p) {
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
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$//
	/*
	 * ##################################################################
	 * 
	 * 						DATAFRAME_ READ CALLS
	 * 
	 * ##################################################################
	 */
	/**
	 * load a csv into a data frame
	 * @param file
	 * @return
	 */
	public static DataFrame read_csv(String file) {
		DataFrame df = DataFrame_Read.loadcsv(file);
		return df;
	}
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$//
	/*
	 * ##################################################################
	 * 
	 * 						DATAFRAME_ COPY CALLS
	 * 
	 * ##################################################################
	 */
    public static DataFrame acquire(DataFrame theDataFrame, String[] args) {
    	return DataFrame_Copy.acquire(theDataFrame, args);
    }
    public static DataFrame deepCopy_columnIndexes(DataFrame theDataFrame, Collection<Integer> columnIndexes) {
    	return DataFrame_Copy.deepCopy_columnIndexes(theDataFrame, columnIndexes);
    }
    public static DataFrame deepCopy_columnNames(DataFrame theDataFrame, Collection<String> columnNames) {
    	return DataFrame_Copy.deepCopy_columnNames(theDataFrame, columnNames);
    }
    public static DataFrame deepCopy_rowIndexes(DataFrame theDataFrame, Collection<Integer> rowIndexes) {
    	return DataFrame_Copy.deepCopy_rowIndexes(theDataFrame, rowIndexes);
    }
    public static DataFrame exclude(DataFrame theDataFrame, Collection<Integer> columnIndexes) {
    	return DataFrame_Copy.exclude(theDataFrame, columnIndexes);
    }
    public static DataFrame shallowCopy_columnIndexes(DataFrame theDataFrame, Collection<Integer> columnIndexes) {
    	return DataFrame_Copy.shallowCopy_columnIndexes(theDataFrame, columnIndexes);
    }
    public static DataFrame shallowCopy_columnNames(DataFrame theDataFrame, Collection<String> columnNames){
    	return DataFrame_Copy.shallowCopy_columnNames(theDataFrame, columnNames);
    }
    public static DataFrame shallowCopy_columnTypes(DataFrame theDataFrame, Collection<Character> columnTypes){
    	return DataFrame_Copy.shallowCopy_columnTypes(theDataFrame, columnTypes);
    }
    public static DataFrame shallowCopy_rowIndexes(DataFrame theDataFrame, Collection<Integer> rowIndexes){
    	return DataFrame_Copy.shallowCopy_rowIndexes(theDataFrame, rowIndexes);
    }
    public static void sortByColumn(DataFrame theDataFrame, int columnIndex){
    	DataFrame_Copy.sortByColumn(theDataFrame, columnIndex);
    }
    public static ArrayList<DataFrame> split(DataFrame theDataFrame, int n) {
    	return DataFrame_Copy.split(theDataFrame, n);
    }
    public DataFrame shuffle(DataFrame df) {
    	DataFrame newdf = df;
    	DataFrame_Copy.shuffle(newdf);
    	return newdf;
    }
    /*
	 * ##################################################################
	 * 
	 * 						DATAFRAME_ COPY CALLS
	 * 
	 * ##################################################################
	 */
	/**
	 * Converts all NANS in the data frame to their mean value, excluding non numeric values, which will
	 * be converted to the most occouring value in the data frame.
	 */
	public void convertNANS_mean() {
        for (int i = 0; i < numColumns; i++) {
            Column c = columns.get(i);
            if (!c.isReadyForStats())
                c.prepareForStatistics();
            for (int j = 0; j < numRows; j++) {
                Particle p = c.getParticle(j); 
                if (p instanceof NANParticle) {
                    if (c.getType() == 'N')
                        p = Particle.resolveType(c.mean);
                    else
                        p = Particle.resolveType(c.mode);
                    getColumn(i).changeValue(j, p);
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
	        Column c = columns.get(i);
	        if (!c.isReadyForStats())
	            c.prepareForStatistics();
	        for (int j = 0; j < numRows; j++) {
	            Particle p = getColumn(i).getParticle(j);
	            if (p instanceof NANParticle) {
	                p = Particle.resolveType(getColumn(i).mode);
	                getColumn(i).changeValue(j, p);
	                getRow_byIndex(j).changeValue(i, p);
	            }
	        }
	    }
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
    public void setStuff() {
    	for(Column i : columns) {
    		i.setUniqueValues();
    		i.setUniqueValCnt();
    		i.setMode();
    		if(i.type == 'N') {
	    		i.setSum();
	    		i.setMean();
	    		i.setVariance();
	    		i.setStandardDeviation();
	    		i.setEntropy();
    		}
    		//i.setFeatureStats();
		
    	}
    }

}
