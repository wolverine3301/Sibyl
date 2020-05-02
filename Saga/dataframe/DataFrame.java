package dataframe;
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
	
    /** The name of the data frame. */
    protected String dataFrameName;
    
	/** The names of the columns */
	protected ArrayList<String> columnNames;
	
	/** The ArrayList of columns */
	public ArrayList<Column> columns;
	
	   /** The ArrayList of rows */
    protected ArrayList<Row> rows;
    
    /** The number of rows in the data frame */
    protected int numRows;
    
    /** The number of columns in the data frame */
    protected int numColumns;
	
	/** ArrayList of only numeric columns */
	public ArrayList<Column> numeric_columns;
	
	/** ArrayList of Categorical Columns */
	public ArrayList<Column> categorical_columns;
	
	/** ArrayList of Target Columns */
	public ArrayList<Column> target_columns;
	
	/** ArrayList of meta columns */
	public ArrayList<Column> meta_columns;
	
	/** The number of numeric columns contained in this data frame. */ 
	public int numNumeric;
	
	/** The number of categorical columns contained in this data frame. */ 
	public int numCategorical;
	
	/** The number of target columns contained in this data frame. */ 
	public int numTargets;
	
	/** The number of meta columns contained in this data frame. */ 
	public int numMeta;
	
	/**
	 * Create a new, empty data frame.
	 */
	public DataFrame() {
		this.columns = new ArrayList<Column>();
	    this.rows = new ArrayList<Row>();
		this.numeric_columns = new ArrayList<Column>();
		this.categorical_columns = new ArrayList<Column>();
		this.target_columns = new ArrayList<Column>();
		this.meta_columns = new ArrayList<Column>();
		this.rows = new ArrayList<Row>();
		this.columnNames = new ArrayList<String>();
		numRows = 0;
		numColumns = 0;
		numNumeric = 0;
		numCategorical = 0;
		numTargets = 0;
		numMeta = 0;
	}
	
	/*
	 * ##################################################################
	 * 
	 * 					DATAFRAME SETTERS
	 * 
	 * ##################################################################
	 */
	
	/**
	 * Changes the name of the dataframe. 
	 * @param name the new name of the dataframe. 
	 */
	public void setName(String name) {
		this.dataFrameName = name;
	}
	
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
	 * NOTE: DO NOT SET COLUMN TYPES WHILE INITIALIZING A DATAFRAME MANUALLY! USE SET STATISTICS!
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
		changeColumnReferences(getColumn(index), newType);
		getColumn(index).setType(newType);
	}
	
	/**
	 * Set a column at a specified index's data type. 
	 * NOTE: DO NOT SET COLUMN TYPES WHILE INITIALIZING A DATAFRAME MANUALLY! USE SET STATISTICS!
	 * @param columnIndex the index of the column.
	 * @param newType the new data type of the column.
	 */
	public void setColumnType(int columnIndex, char newType) {
	    changeColumnReferences(getColumn(columnIndex), newType);
	    getColumn(columnIndex).setType(newType);
    }
	
	/**
	 * Updates/Initializes all of the Columns contained in the DataFrame statistics. 
	 */
    public void setStatistics() {
        for (int i = 0; i < columns.size(); i++) {
            setStatistics(i);
        }
    }
    
    /** 
     * Sets the statistics of a single column at the parameterized index. 
     * @param index the index of the column who's statistics are to be changed. 
     */
    public void setStatistics(int index) {
        Column c = columns.get(index);
        if (c.type == 'U') 
            c.resolveType();
        if (!c.readyForStats)
            c.setStatistics();
        char columnType = c.getType();
        if (columnType == 'T') {
            target_columns.add(c);
            numTargets++;
        } else if (columnType == 'N') {
            numeric_columns.add(c);
            numNumeric++;
        } else if (columnType == 'C') {
            categorical_columns.add(c);
            numCategorical++;
        } else if (columnType == 'M') {
            meta_columns.add(c);
            numMeta++;
        }
    }
    
    /**
     * Updates the statistics of the entire dataframe. 
     */
    public void updateStatistics() {
        for (int i = 0; i < numColumns; i++) {
            updateStatistics(i);
        }
    }
    
    /**
     * Updates the statistics of a single column at the parameterized index. 
     * @param index the index to update. 
     */
    public void updateStatistics(int index) {
        Column c = columns.get(index);        
        char oldType = c.getType();
        c.setStatistics();
        if (oldType != c.getType()) { //If the type of the column just happened to change. 
            if (oldType == 'T') {
                target_columns.remove(c);
                numTargets--;
            } else if (oldType == 'N') {
                numeric_columns.remove(c);
                numNumeric--;
            } else if (oldType == 'C') {
                categorical_columns.remove(c);
                numCategorical--;
            } else if (oldType == 'M') {
                meta_columns.remove(c);
                numMeta--;
            }
            c.setStatistics();
            char columnType = c.getType();
            if (columnType == 'T') {
                target_columns.add(c);
                numTargets++;
            } else if (columnType == 'N') {
                numeric_columns.add(c);
                numNumeric++;
            } else if (columnType == 'C') {
                categorical_columns.add(c);
                numCategorical++;
            } else if (columnType == 'M') {
                meta_columns.add(c);
                numMeta++;
            }
        }
    }
    
    /**
     * Adds a **NEW** column to the statisics contained within the dataframe.
     * Do not use this method unless you're cade handing the saga code or 
     * some fool who doesn't know the reprocussions of non properly formatted data.
     * There is a reason this is private. You fool. Dont use it. Ill find out if you did. 
     * @param c The column to add to the statistics contained in the dataframe. 
     * @param isEmpty if the column is empty, mark this as true so that statistics are 
     * not automatically calculated. 
     */
    private void addColumnStatistics(Column c, boolean isEmpty) {
        char columnType = c.getType();
        if (!isEmpty && !c.readyForStats)
            c.setStatistics();
        if (columnType == 'T') {
            target_columns.add(c);
            numTargets++;
        } else if (columnType == 'N') {
            numeric_columns.add(c);
            numNumeric++;
        } else if (columnType == 'C') {
            categorical_columns.add(c);
            numCategorical++;
        } else if (columnType == 'M') {
            meta_columns.add(c);
            numMeta++;
        }
    }
    
	/**
	 * Handles swapping column storage when changing the type of a column. Helper method for set column type method.
	 * Not reccommended to use unless you're very familiar with saga
	 * @param c 
	 * @param newType
	 */
	private void changeColumnReferences(Column c, char newType) {
        char oldType = c.getType();
        if (oldType == 'T') {
            target_columns.remove(c);
            numTargets--;
        } else if (oldType == 'N') {
            numeric_columns.remove(c);
            numNumeric--;
        } else if (oldType == 'C') {
            categorical_columns.remove(c);
            numCategorical--;
        } else if (oldType == 'M') {
            meta_columns.remove(c);
            numMeta--;
        }
 
        if (newType == 'T') {
            target_columns.add(c);
            numTargets++;
        } else if (newType == 'N') {
            numeric_columns.add(c);
            numNumeric++;
        } else if (newType == 'C') {
            categorical_columns.add(c);
            numCategorical++;
        } else if (newType == 'M') {
            meta_columns.add(c);
            numMeta++;
        }
	}
	
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$//
	/*
	 * ##################################################################
	 * 
	 * 					DATAFRAME GETTERS
	 * 
	 * ##################################################################
	 */
	
	
    public String getName() {
    	return this.dataFrameName;
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
    public Column getColumn(int index) {
        return columns.get(index);
    }
    
    /**
     * Returns a shallow copy of the columns of a certain type. 
     * @param type the type of columns to fetch.
     * @return a shallow copy of the columns of a certian type. 
     */
    public ArrayList<Column> getColumns(char type) {
        if (type == 'T') {
            return target_columns;
        } else if (type == 'N') {
            return numeric_columns;
        } else if (type == 'C') {
            return categorical_columns;
        } else if (type == 'M') {
            return meta_columns;
        }
        //If acquiring a type of column which is not stored as a field. 
        ArrayList<Column> cols = new ArrayList<Column>();
        for (Column c : columns) {
            if (c.type == type)
                cols.add(c);
        }
        return cols;
    }

	
	/**
	 * Returns an array of the column names from the data frame.
	 * @return an array of the column names from the data frame.
	 */
	public ArrayList<String> getColumnNames() {
		return columnNames;
	}
	
	/**
	 * Returns the index of the column with a given name.
	 * @param columnName the name of the column whos index to return. 
	 * @return the index of the column with a given name. Returns -1 if the column name is not contained. 
	 */
	public int getColumnIndex(String columnName) {
	    for (int i = 0; i < columnNames.size(); i++) {
	        if (columnNames.get(i).equals(columnName))
	            return i;
	    }
	    return -1;
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
    	c = new Column(name, p.getType());
    	c.add(p);
    	rows.get(0).add(p);
    	for(int i = 1; i < arr.length;i++) {
    		p = Particle.resolveType(arr[i]);
            rows.get(i).add(p);
    		c.add(p);
    	}
    	columnNames.add(name);
    	addColumnStatistics(c, false);
    	columns.add(c);
    	numColumns++;
    }
    
    public void addColumnName(String name) {
        if (columnNames.size() == columns.size() - 1)
            columnNames.add(name);
        else
            throw new IllegalArgumentException("Addition of new column name would cause uneven column names length vs column length.");
    }
    
    /**
     * Adds a row to the data frame from an array. Does NOT update the statistics of the already contained columns.
     * @param arr the new row to be added. 
     */
    public void addRowFromArray(Object arr[]) { 
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
     * Does NOT update the statistics of the columns already contained.
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
	 * Does NOT update the statistics of the columns already contained. 
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
	 * Removes a row in the dataframe. 
	 * Does NOT update the 
	 * @param index the index of the row to remove. 
	 */
	public void removeRow(int index) {
	    rows.remove(index);
	    for (Column c : columns) {
	        c.removeIndex(index);
	    }
	}
	
	
    /**
     * Adds a column to the data frame.
     * It's statistics will automatically be added. 
     * @param c the column to be added.
     */
    public void addColumn(Column c) {
        columns.add(c);
        columnNames.add(c.getName());
        addColumnStatistics(c, false);
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
        if (c.getType() == 'T') {
            target_columns.add(c);
            numTargets++;
        } else if (c.getType() == 'N') {
            numeric_columns.add(c);
            numNumeric++;
        } else if (c.getType() == 'C') {
            categorical_columns.add(c);
            numCategorical++;
        } else if (c.getType() == 'M') {
            meta_columns.add(c);
            numMeta++;
        }
    }
    //Adds a column at a specified index
    public void addColumnAtIndex(int index, Column c) {
        columns.add(index,c);
        columnNames.add(index,c.getName());
        addColumnStatistics(c, false);
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
        if (c.getType() == 'T') {
            target_columns.add(c);
            numTargets++;
        } else if (c.getType() == 'N') {
            numeric_columns.add(c);
            numNumeric++;
        } else if (c.getType() == 'C') {
            categorical_columns.add(c);
            numCategorical++;
        } else if (c.getType() == 'M') {
            meta_columns.add(c);
            numMeta++;
        }
    }
    /**
	 * Adds a new empty column to the data frame. Using this method will not initialize the statistics of the 
	 * column, as it is empty. DataFrame.setStatistics(int index) is what you will need to use to update. 
	 * @param name The name of the new column.
	 */
	public void addBlankColumn(String name) {
		Column c = new Column(name);
		columnNames.add(name);
		columns.add(c);

		numColumns++;
	}
	
	/**
	 * Adds a new empty column of a certain data type to the data frame.
	 * Will add the column to the dataframes statistics, however it will not initialize the final values
	 * as the column is empty. 
	 * @param name the name of the new column.
	 * @param dataType the data type of the new column.
	 */
	public void addBlankColumn(String name, char dataType) {
		Column c;
		c = new Column(name, dataType);
	    columnNames.add(name);
	    columns.add(c);
	    addColumnStatistics(c, true);
	    numColumns++;
	}
	
	/**
	 * Adds a blank row to the dataframe.
	 */
	public void addBlankRow() {
	    rows.add(new Row());
	    numRows++;
	}
	
	/**
	 * Replaces a column in the dataframe. 
	 * @param index
	 * @param newColumn
	 */
	public void replaceColumn(int index, Column newColumn) {
	    Column c = columns.get(index);
	    columnNames.set(index, newColumn.name);
	    columns.set(index, newColumn);
	    char oldType = c.getType();
        if (oldType == 'T') {
            target_columns.remove(c);
            numTargets--;
        } else if (oldType == 'N') {
            numeric_columns.remove(c);
            numNumeric--;
        } else if (oldType == 'C') {
            categorical_columns.remove(c);
            numCategorical--;
        } else if (oldType == 'M') {
            meta_columns.remove(c);
            numMeta--;
        }
        for (Row r : rows) {
            r.removeParticle(index);
        }
        if (newColumn.getType() == 'T') {
            target_columns.add(newColumn);
            numTargets++;
        } else if (newColumn.getType() == 'N') {
            numeric_columns.add(newColumn);
            numNumeric++;
        } else if (newColumn.getType() == 'C') {
            categorical_columns.add(newColumn);
            numCategorical++;
        } else if (newColumn.getType() == 'M') {
            meta_columns.add(newColumn);
            numMeta++;
        }
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
	
	/**
	 * Removes the column at a given index from the dataframe. 
	 * @param index the index of the column to remove. 
	 */
	public void removeColumn(int index) {
	    columnNames.remove(index);
	    Column c = columns.get(index);
        char oldType = c.getType();
        if (oldType == 'T') {
            target_columns.remove(c);
            numTargets--;
        } else if (oldType == 'N') {
            numeric_columns.remove(c);
            numNumeric--;
        } else if (oldType == 'C') {
            categorical_columns.remove(c);
            numCategorical--;
        } else if (oldType == 'M') {
            meta_columns.remove(c);
            numMeta--;
        }
        numColumns--;
        for (Row r : rows) {
            r.removeParticle(index);
        }
	}
	
	/**
	 * Removes a column with the first instance of a given column name in the dataframe. 
	 * @param columnName the name of the column to remove. 
	 */
	public void removeColumn(String columnName) {
	    int index = getColumnIndex(columnName);
	    removeColumn(index);
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
	
	
	public DataFrame deepCopy() {
		return DataFrame_Copy.deepCopy(this);
	}
	public DataFrame shallowCopy() {
		return DataFrame_Copy.shallowCopy(this);
	}
    public DataFrame acquire(String[] args) {
    	return DataFrame_Copy.acquire(this, args);
    }
    public DataFrame deepCopy_columnIndexes(Collection<Integer> columnIndexes) {
    	return DataFrame_Copy.deepCopy_columnIndexes(this, columnIndexes);
    }
    
    public DataFrame deepCopy_columnNames(Collection<String> columnNames) {
    	return DataFrame_Copy.deepCopy_columnNames(this, columnNames);
    }
    
    public DataFrame deepCopy_columnType(char columnType) {
        return DataFrame_Copy.deepCopy_columnType(this, columnType);
    }
    
    public DataFrame deepCopy_columnTypes(Collection<Character> columnTypes){
        return DataFrame_Copy.shallowCopy_columnTypes(this, columnTypes);
    }
    
    public DataFrame deepCopy_rowIndexes(Collection<Integer> rowIndexes) {
    	return DataFrame_Copy.deepCopy_rowIndexes(this, rowIndexes);
    }
    
    public DataFrame exclude(Collection<Integer> columnIndexes) {
    	return DataFrame_Copy.exclude(this, columnIndexes);
    }
    
    public DataFrame shallowCopy_columnIndexes(Collection<Integer> columnIndexes) {
    	return DataFrame_Copy.shallowCopy_columnIndexes(this, columnIndexes);
    }
    
    public DataFrame shallowCopy_columnNames(Collection<String> columnNames){
    	return DataFrame_Copy.shallowCopy_columnNames(this, columnNames);
    }
    
    public DataFrame shallowCopy_columnType(char columnType) {
        return DataFrame_Copy.shallowCopy_columnType(this, columnType);
    }
    
    public DataFrame shallowCopy_columnTypes(Collection<Character> columnTypes){
    	return DataFrame_Copy.shallowCopy_columnTypes(this, columnTypes);
    }
    
    public DataFrame shallowCopy_rowIndexes(Collection<Integer> rowIndexes){
    	return DataFrame_Copy.shallowCopy_rowIndexes(this, rowIndexes);
    }
    
    public void sortByColumn(int columnIndex){
    	DataFrame_Copy.sortByColumn(this, columnIndex);
    }
    
    public ArrayList<DataFrame> split(int n) {
    	return DataFrame_Copy.split(this, n);
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
    
    /*
     * ##################################################################
     * 
     * 						DATAFRAME UTIL CALLS
     * 
     * ##################################################################
     * 
     */
    public void saveDataFrame(DataFrame df, String name) {
    	DataFrame_Utilities.saveDataFrame(df, name);
    }
    public DataFrame saveDataFrame(String filename) {
    	return DataFrame_Utilities.loadDataFrame(filename);
    }
	/**
	 * Converts all NANS in the data frame to their mean value, excluding non numeric values, which will
	 * be converted to the most occouring value in the data frame.
	 */
	public void convertNANS_mean() {
        for (int i = 0; i < numColumns; i++) {
            Column c = columns.get(i);
            if (!c.isReadyForStats())
                c.setStatistics();
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
	            c.setStatistics();
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
	
    /**
     * Prints ALL of the data contained in the dataframe. 
     */
    public void printAllData(boolean printStats) {
        System.out.println("Dataframe: " + dataFrameName);
        for(int i = 0; i < columnNames.size(); i++) {
            System.out.print(columnNames.get(i) + " ");
        }
        System.out.println();
        for(int z = 0 ; z < numRows; z++) {
            rows.get(z).printRow();
            System.out.println();
        }
        
        System.out.println("\nNumeric Columns: (count = " + numNumeric + "): ");
        for (Column c : numeric_columns) {
            System.out.println(c.toString());
            if (printStats) {
                System.out.println(c.toStringStatistics());
            }
        }
        
        System.out.println("\nCategorical Columns (count = " + numCategorical + "): ");
        for (Column c : categorical_columns) {
            System.out.println(c.toString());
            if (printStats) {
                System.out.println(c.toStringStatistics());
            }
        }
        
        System.out.println("\nTarget Columns (count = " + numTargets + "): ");
        for (Column c : target_columns) {
            System.out.println(c.toString());
            if (printStats) {
                System.out.println(c.toStringStatistics());
            }
        }
        
        System.out.println("\nMeta Columns (count = " + numMeta + "): ");
        for (Column c : meta_columns) {
            System.out.println(c.toString());
            if (printStats) {
                System.out.println(c.toStringStatistics());
            }
        }
    }

}
