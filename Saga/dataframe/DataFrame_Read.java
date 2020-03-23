package dataframe;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

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
        	for (int i = 0; i < colNames.length; i++) { //Initialize column names.
        		df.addBlankColumn(colNames[i]);
        	}
            // initializing column objects
            line = br.readLine();
            String[] lines = line.split(cvsSplitBy);
            Row row = new Row();
            //initiallizes row 0
            for(int i=0;i<df.columnNames.size();i++) {
            	Particle p = Particle.resolveType(lines[i]);
            	p.setIndex(0);
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
            // end row 0
            //fill rest of rows
            int cnt = 0;
        	while ((line = br.readLine()) != null) { //Read in each line, create row objects and initialize data.
                lines = line.split(cvsSplitBy);
                row = new Row();
                for(int i=0;i<df.columnNames.size();i++) { //load data into columns and rows
                	Particle p = Particle.resolveType(lines[i]);
                	p.setIndex(cnt);
                	if(p.getType() == 's' && df.getColumn(i).getType() == 'N') {
                		p = Particle.resolveType("NAN");
                		p.setIndex(cnt);
                	}
                	df.columns.get(i).add(p);
                	row.add(p);
                }
                cnt++;
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
	
	public static DataFrame loadcsv_new(String filePath) {
	    DataFrame df = new DataFrame();
	    try {
	        BufferedReader br = new BufferedReader(new FileReader(filePath));
	        String[] columnNames = br.readLine().split(",");
	        for (int i = 0; i < columnNames.length; i++) { //Initialize blank columns with column names.
	            df.addBlankColumn(columnNames[i]);
	        }
	        String currentLine = "";
	        int count = 0;
	        while ((currentLine = br.readLine()) != null) { //For each other line inside the file, add them to the dataframe.
	            Row currentRow = new Row();
	            String[] values = currentLine.split(",");
	            for (int i = 0; i < values.length; i++) { //Create a row object out of the current read in line.
	                Particle currentParticle = Particle.resolveType(values[i]);
	                currentParticle.setIndex(count); //Set the index of this particle in the column. 
	                currentRow.add(currentParticle);
	            }
	            df.addRow(currentRow); //Add the current row to the dataframe. This method handles initializing position in columns too.
	            count++;
	        }
            df.numRows = df.rows.size();
            df.numColumns = df.columns.size();
	        for (int i = 0; i < df.numColumns; i++) { //For each column in the row, initialize it's type and it's statistics. 
	            Column c = df.getColumn(i);
	            c.resolveType();
	            df.columnTypes.add(c.getType());
	            if (c.getType() == 'N')
	                df.numericIndexes.add(i);
	            c.setStatistics();
	        }
	        br.close();
	    } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return df;
	}
}
