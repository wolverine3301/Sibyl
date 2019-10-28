package anova;

import dataframe.DataFrame;

public class ANOVA_example {

	public static void main(String[] args) {
		String file = "testfiles/iris.txt";
        DataFrame df = DataFrame.read_csv(file);
        df.getColumn_byName("species").setType('T'); //set target column
        System.out.println(df.getColumn_byName("species").getUniqueValues());
        ANOVA anova = new ANOVA(df);
        anova.initiallize_ANOVA(4);

	}

}
