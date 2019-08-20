package saga;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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
	 * Converts all NANS in the data frame to their mean value, excluding non numeric values, which will
	 * be converted to the most occouring value in the data frame.
	 */
	public void convertNANS_mean() {
	    for (Column c : columns) { //Iterate through columns
	        for (Particle p : c.column) { //Iterate through particles
	            if (p instanceof NANParticle) {
	                if (c.type.contains("Integer"))
	                    p = new IntegerParticle((int) Math.round(c.mean()));
	                else if (c.type.contains("Double"))
	                    p = new DoubleParticle(c.mean());
	                else
	                    p = new StringParticle((String) c.mode());
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
	            Particle p = get
	            if ( instanceof NANParticle) 
                    p = Particle.resolveType(c.mode());
	        }
	    }
	}
	
	/**
	 * Creates a new deep copied data frame internally from a list of column names.
	 * @param columnNames the list of column name.
	 * @return the newly created data frame.
	 */
	public DataFrame dataFrameFromColumns_DeepCopy(List<String> columnNames) {
	    DataFrame newDataFrame = new DataFrame();
	    for (String name : columnNames) { // Create the columns
	        Column c = new Column(getColumn_byName(name));
	        newDataFrame.columnNames.add(c.name);
	        newDataFrame.columnTypes.add(c.type);
	        newDataFrame.numColumns++;
	        newDataFrame.columns.add(new Column(getColumn_byName(name)));
	    }
	    newDataFrame.numRows = newDataFrame.columns.get(0).getLength();
	    for (int i = 0; i < newDataFrame.numRows; i++) { // Initialize row pointers
	        Row row = new Row(); 
	        for (int j = 0; j < newDataFrame.numColumns; j++) {
	            row.addToRow(newDataFrame.columns.get(j).getParticle_atIndex(i));
	        }
	        newDataFrame.rows.add(row);
	    }
	    return newDataFrame;
	}
	
	/**
	 * Creates a new shallow copied data frame internally from a list of column names.
	 * @param columnNames the names of the columns to be added to the new data frame
	 * @return a new DataFrame consisting of the columns passed to the method.
	 */
	public DataFrame dataFrameFromColumns_ShallowCopy(List<String> columnNames) {
	    DataFrame newDataFrame = new DataFrame();
	    for (String name : columnNames) {
	        Column c = getColumn_byName(name);
            newDataFrame.columnNames.add(c.name);
            newDataFrame.columnTypes.add(c.type);
            newDataFrame.numColumns++;
	        newDataFrame.columns.add(c);
	    }
	    newDataFrame.numRows = newDataFrame.columns.get(0).getLength();
	    for (int i = 0; i < newDataFrame.numRows; i++) {
	        Row row = new Row();
	        for (int j = 0; j < newDataFrame.numColumns; j++)
	            row.addToRow(newDataFrame.columns.get(j).getParticle_atIndex(i));
	        newDataFrame.rows.add(row);
	    }
	    return newDataFrame;
	}
	
	/**
	 * Creates a new deep copied data frame internally from a list of row indexes.
	 * @param rowIndexes the set of row indexes to create a new data frame with.
	 * @return a new DataFrame consisting of the rows passed to the method.
	 */
	public DataFrame dataFrameFromRows_DeepCopy(Set<Integer> rowIndexes) {
	    DataFrame newDataFrame = new DataFrame();
	    for (Column c : columns) { //Initialize blank columns in new data frame.
	        newDataFrame.add_blank_Column(c.name, c.type);
	    }
	    for (Integer rowIndex : rowIndexes) {
	        Row r = new Row(rows.get(rowIndex));
	        newDataFrame.rows.add(r);
	        for (int i = 0; i < r.getlength(); i++)
	            newDataFrame.columns.get(i).addToColumn(r.getParticle(i));
	    }
	    newDataFrame.numRows = rowIndexes.size();
	    return newDataFrame;
	}
	
	/**
	 * Creates a new shallow copied data frame internally from a list of row indexes.
	 * @param rowIndexes the set for row indexes to create a new data frame with.
	 * @return a new DataFrame consisting of the rows passed to the method.
	 */
	public DataFrame dataFrameFromRows_ShallowCopy(Set<Integer> rowIndexes) {
	    DataFrame newDataFrame = new DataFrame();
	    for (Column c : columns) 
	        newDataFrame.add_blank_Column(c.name, c.type);
	    for (Integer rowIndex : rowIndexes) {
	        Row r = rows.get(rowIndex);
	        newDataFrame.rows.add(r);
	        for (int i = 0; i < r.getlength(); i++) {
	            newDataFrame.columns.get(i).addToColumn(r.getParticle(i));
	        }
	    }
	    newDataFrame.numRows = rowIndexes.size();
	    return newDataFrame;
	    
	}
	
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
    public DataFrame acquire(String[] args) {
        Set<Integer> rowIndexes = new TreeSet<Integer>();
        for (int i = 0; i < args.length; i += 3) {
            Column column = getColumn_byName(args[i]);
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
        return dataFrameFromRows_ShallowCopy(rowIndexes);
    }
    
    /**
     * Split dataframe into n equal sections
     * @param n - number of new dataframes 
     * @return dataframe[] of shallow copiesshallow copies
     */
	public DataFrame[] split(int n) {
		int interval = Math.floorDiv(numRows, n-1);
		DataFrame[] partitions = new DataFrame[n-1];
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < numRows-1; i += interval-1) {
			set.clear();
			for(int j = i+1;j <= i+interval-1; j++) {
				if(i == 0) {set.add(0);}
				set.add(j);	
			}
		}
		return partitions;
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
        rows.add(r);
        numRows++;
        
    }
    
    /**
     * Adds a row to the data frame.
     * @param r the row to be added.
     */
    public void addRow(Row r) {
        rows.add(r);
        numRows++;
        for (int i = 0; i < r.getlength(); i++) { //initialize position in column.
            columns.get(i).addToColumn(r.getParticle(i));
        }
    }
    
    /**
     * Adds a column to the data frame.
     * @param c the column to be added.
     */
    public void addColumn(Column c) {
        columns.add(c);
        columnNames.add(c.name);
        columnTypes.add(c.type);
        numColumns++;
        for (int i = 0; i < c.getLength(); i++) {
            rows.get(i).addToRow(c.getParticle_atIndex(i));;
        }
        
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
		numColumns++;
	}
	
	public void add_blank_Column(String name, String dataType) {
	    Column c = new Column(name, dataType);
	    columnNames.add(name);
	    columnTypes.add(dataType);
	    columns.add(c);
	    numColumns++;
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
	 * set column to certain type
	 * @param columnName
	 * @param newType
	 */
	public void setColumnType(String columnName, String newType) {
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
	
	public boolean isSquare() {
	    Set<Integer> rowLengths = new TreeSet<Integer>();
	    Set<Integer> columnLengths = new TreeSet<Integer>();
	    for (int i = 0; i < numRows; i++)
	        rowLengths.add(rows.get(i).rowLength);
	    for (int i = 0; i < numColumns; i++)
	        columnLengths.add(columns.get(i).columnLength);
	    return (rowLengths.equals(columnLengths)) && numRows == numColumns;
	}
	/**
	 * Method to get a dataframe with only specified types
	 * @param List<String> types
	 * @return shallow copy
	 */
	public DataFrame include(List<String> types) {
		List<String> cols = new ArrayList<String>();
		for(Column i : columns) {
			if(types.contains(i.type)) {
				cols.add(i.name);
			}
		}
		return dataFrameFromColumns_ShallowCopy(cols);
	}
	/**
	 * returns a list of columns of the specified type
	 * @param String type
	 * @return List<Column>
	 */
	public List<Column> getColumnByTypes(String type){
		List<Column> cols = new ArrayList<Column>();
		for(Column i : columns) {
			if(i.type == type) {
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
