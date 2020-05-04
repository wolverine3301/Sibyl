package examples;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Read;
import dataframe.Util;

public class SplitOnTargetTest {

	public static void main(String[] args) {
		String file = "testfiles/catTest.txt";
		DataFrame  df = DataFrame_Read.loadcsv(file);
        df.setColumnType("Color", 'T');//set target column
        
        
        DataFrame[] df_i = Util.splitOnTarget(df, df.target_columns.get(0));
        System.out.println("number of dataframes = "+df_i.length);
        for(int i = 0; i < df_i.length;i++) {
        	System.out.println("DF NAME: "+df_i[i].getName());
        	System.out.println("NUM COLS: "+df_i[i].getNumColumns());
        	System.out.println("Column names");
        	System.out.println(df_i[i].columnNamesToString());
        	System.out.println("NUM TRAGETS: "+df_i[i].numTargets);
        	System.out.println("Target names:");
        	for(Column j : df_i[i].target_columns) {
        		System.out.println(j.getName()+" "+j.getTotalUniqueValues());
        	}
        	
        }
        
	}

}
