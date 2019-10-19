package dataframe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import particles.Particle;

public class DataFrame_Read {
	/**
	 * Construct a dataframe directly from a csv file, auto assumes there is a header line which it uses as the column names.
	 * @param file  - csv file name or path
	 * @param types - an array of strings to set the type attribute of the column, needs work and automization but will be usefully
	 * for managing which functions to use on which type of column
	 * 
	 */
	public static DataFrame loadcsv(String file) {
		DataFrame df = new DataFrame();
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        	line =  br.readLine(); //get column names
        	String[] colNames = line.split(cvsSplitBy);
        	for(int i = 0;i < colNames.length;i++) //Initialize column names.
        		df.columnNames.add(colNames[i]);

            // initializing column objects
            line = br.readLine();
            String[] lines = line.split(cvsSplitBy);
            Row row = new Row();
            for(int i=0;i<df.columnNames.size();i++) {
            	Particle p = Particle.resolveType(lines[i]);
            	if(p.type == 'i' || p.type == 'd') {
            		df.numericIndexes.add(i);
            		Column c = new Column(df.columnNames.get(i));
            		c.add(p);
            		c.setType('N');
            		df.columns.add(c);

            	}else if(p.type == 'n') {
            		Column c = new Column(df.columnNames.get(i));
            		c.add(p);
            		c.setType('M');
            		df.columns.add(c);
            			
            	}else {
            		Column c = new Column(df.columnNames.get(i));
            		c.add(p);
            		c.setType('C');
            		df.columns.add(c);

            	}
            	row.add(p);
            }
            df.rows.add(row);
            
        	while ((line = br.readLine()) != null) { //Read in each line, create row objects and initialize data.
                lines = line.split(cvsSplitBy);
                row = new Row();
                for(int i=0;i<df.columnNames.size();i++) { //load data into columns and rows
                	Particle p = Particle.resolveType(lines[i]);
                	df.columns.get(i).add(p);
                	row.add(p);
                }
                df.rows.add(row);
        	}
        	for(int i = 0;i < df.columnNames.size();i++) {
        	      df.getColumn(i).setStatistics();
        	      df.columnTypes.add(df.columns.get(i).getType());
        	}
        	for(int i = 0; i < df.getNumColumns() ;i++) {
        	    df.getColumn(i).setStatistics();
        	}
        	df.numRows = df.rows.size();
        	df.numColumns = df.columns.size();
        } catch (IOException e) {
        	e.printStackTrace();
        }
		return df;
	}
}
