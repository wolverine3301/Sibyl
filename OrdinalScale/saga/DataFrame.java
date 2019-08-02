package saga;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
	
	public String[] columnNames;
	public int numRows;
	public ArrayList<Column> dataframe;
	
	/**
	 * Constructor
	 */
	public DataFrame() {
		this.dataframe = new ArrayList<Column>();
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
        	this.columnNames = line.split(cvsSplitBy);
        	// initializing column objects
            for (int i = 0; i < columnNames.length; i++) {
            	Column<T> c = new Column<T>(columnNames[i],types[i]);
            	dataframe.add(c);
            } //end initializing
            int count = 0;
            //read lines
        	while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] row = line.split(cvsSplitBy);
                //load data into array list
                for(int i=0;i<columnNames.length;i++) {
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
	 * getColumn returns a single column from dataframe
	 * @param name - name of column
	 * @return
	 */
	public Column<T> getColumn(String name){
		int index = 0;
		for(int i = 0; i < columnNames.length; i++) {
			if(columnNames[i].contentEquals(name)){
				index = i;
				break;
			}
		}
		return dataframe.get(index);
	}
	/**
	 * addColumn - adds a new empty column to dataframe
	 * @param name
	 * @param type
	 */
	public void addColumn(String name, String type) {
		Column c = new Column(name,type);
		dataframe.add(c);
		
	}
	/**
	 * getColumnNames - returns array of column names
	 * @return
	 */
	public String[] getColumnNames() {
		return columnNames;
	}
	/**
	 * getLength - returns number of rows in dataframe
	 * @return
	 */
	public int getLength() {
		return numRows;
	}

}
