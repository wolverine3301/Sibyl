package anova;

import java.util.Set;

import dataframe.Column;
import dataframe.DataFrame;

public class ANOVA_example {

	public static void main(String[] args) {
		String file = "testfiles/iris.txt";
        DataFrame df = DataFrame.read_csv(file);
        df.setColumnType("species", 'T');//set target column
        System.out.println(df.targetIndexes.toString()); 
        String[] arg  = new String[3];
        arg[0] = df.getColumn(df.targetIndexes.get(0)).getName();
		arg[1] = "==";
		Set<Object> targets = df.getColumn(df.targetIndexes.get(0)).getUniqueValues();
		DataFrame[] classes = new DataFrame[3];
		int cnt = 0;
		for(Object i : targets) {
			System.out.println(i.toString());
			arg[2] = i.toString();
			classes[cnt] = df.acquire(arg);
			for(Column j : classes[cnt].columns) {
				j.setUniqueValueCount();
				System.out.println(j.getUniqueValueCounts());
				
			}
			cnt++;
		}
		
		//classes[0].printDataFrame();
      
        ANOVA anova = new ANOVA(df);
        anova.initiallize_ANOVA(4);

	}

}
