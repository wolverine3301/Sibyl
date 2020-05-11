package hypothesisExamples;

import java.util.Set;

import anova.OneWay_ANOVA;
import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Read;

public class ANOVA_example {

	public static void main(String[] args) {
		String file = "testfiles/iris.txt";
        DataFrame df = DataFrame_Read.loadcsv(file);
        df.setColumnType("species", 'T');//set target column

		OneWay_ANOVA anova = new OneWay_ANOVA(df);
        anova.invokeANOVA();
        anova.printResults();

	}

}
