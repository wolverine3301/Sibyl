package hypothesisExamples;

import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	  Logger logger = Logger.getLogger(OneWay_ANOVA.class.getName());
	  
	  
	  // Add ConsoleHandler
	 
	  ConsoleHandler consoleHandler = new ConsoleHandler();
	 
	  logger.addHandler(consoleHandler);

        anova.invokeANOVA();
        anova.printResults();

	}

}
