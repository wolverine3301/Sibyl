package examples;

import java.util.Set;

import anova.OneWay_ANOVA;
import dataframe.Column;
import dataframe.DataFrame;

public class ANOVA_example {

	public static void main(String[] args) {
		String file = "testfiles/iris.txt";
        DataFrame df = DataFrame.read_csv(file);
        df.setColumnType("species", 'T');//set target column
        String[] arg  = new String[3];
        arg[0] = df.getColumn(df.targetIndexes.get(0)).getName();
		arg[1] = "==";
		Set<Object> targets = df.getColumn(df.targetIndexes.get(0)).getUniqueValues();
		DataFrame[] classes = new DataFrame[3];
		int cnt = 0;
		for(Object i : targets) {
			arg[2] = i.toString();
			classes[cnt] = df.acquire(arg);
			cnt++;
		}
		
		OneWay_ANOVA anova = new OneWay_ANOVA(df);
        anova.invokeANOVA();
        anova.printResults();

	}

}
