package scout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import bayes.NaiveBayes;
import bayes.NaiveBayes2;
import dataframe.DataFrame;
import dataframe.DataFrame_Copy;
import knn.KNN;
import log.Loggers;
import machinations.Constant;
import machinations.Model;
import scorer.CrossValidation;

public class UFC {
	//behold, my will creates your body and your sword my destiny
	private static CategoryRanker CR;
	//
	private static NumericRanker NR; 
	private static DataFrame  df;
	
	public static void main(String[] args) {
		Loggers.logHTML(Loggers.df_Logger, Level.FINE);
		Loggers.logHTML(Loggers.cm_Logger, Level.FINEST);
		Loggers.logHTML(Loggers.cv_Logger,Level.ALL);
		Loggers.logHTML(Loggers.nb_Logger,Level.ALL);
		Loggers.logHTML(Loggers.score_Logger,Level.ALL);
		String file = "testfiles/preprocessed_data.csv";
        df = DataFrame.read_csv(file);
        String[] arg = {"no_of_rounds", "!=","4"};
        System.out.println(df.getNumColumns());
        df = DataFrame_Copy.acquire(df, arg);
        df.setColumnType("Winner", 'T');//set target column
        df.setColumnType("no_of_rounds", 'C');
        //df.setColumnType("no_of_rounds", 'C');
        //df.setStatistics(2);
        df.setColumnType("no_of_rounds", 'T');
        //df.convertNANS_mean(); // conevert any NAN's to the mean of column 
        //df = Standardize.standardize_df(df); //Standardize the DF into z scores
        //df = df.shuffle(df);
        System.out.println(df.getNumColumns());
		NaiveBayes2 nb = new NaiveBayes2();
		Model model = new KNN();
		Model con = new Constant();
        //NR = new NumericRanker(df,0);
        //System.out.println(NR.getSpearman());
        //CR = new CategoryRanker(df, 0);
		//ArrayList<DataFrame> ev = generateRecollection(10,15);
		//for(DataFrame i : ev) {
			 CrossValidation cv = new CrossValidation(df,5, nb);

		//}
       

        cv.printOverAllScore();
        cv.printOverAllMatrix();
        System.out.println();

	}
	/**
	 * evocation
	 * creates an arraylist of dataframes with varying number of columns ranked by various measures in Ranker classes
	 * the first column will always be the best ranked column for a particular ranking method.
	 * @param initialNumColumns - the number of columns to start with
	 * @param terminate - number of columns to terminate on
	 * @return
	 */
	private static ArrayList<DataFrame> generateRecollection(int initialNumColumns, int terminate) {
		ArrayList<DataFrame> recollection = new ArrayList<DataFrame>();
		List<String> variableColumns = new ArrayList<String>();
		for(int cnt = initialNumColumns; cnt < terminate; cnt++) {
			if(CR.ranked) {
				// INFO GAIN
				for(int i = 0; i < cnt; i++) {
					if(i == CR.GAIN.size()) break;
					variableColumns.add(CR.GAIN.get(i).getName());
				}
				recollection.add(DataFrame_Copy.shallowCopy_columnNames(df, variableColumns));
				variableColumns.clear();
				// GAIN RATIO
				for(int i = 0; i < cnt; i++) {
					if(i == CR.GAIN_RATIO.size()) break;
					variableColumns.add(CR.GAIN_RATIO.get(i).getName());
				}
				recollection.add(DataFrame_Copy.shallowCopy_columnNames(df, variableColumns));
				variableColumns.clear();
				// GINI
				for(int i = 0; i < cnt; i++) {
					if(i == CR.GINI.size()) break;
					variableColumns.add(CR.GINI.get(i).getName());
				}
				recollection.add(DataFrame_Copy.shallowCopy_columnNames(df, variableColumns));
				variableColumns.clear();
				// CHI SQUARED
				for(int i = 0; i < cnt; i++) {
					if(i == CR.CHI2.size()) break;
					variableColumns.add(CR.CHI2.get(i).getName());
				}
				recollection.add(DataFrame_Copy.shallowCopy_columnNames(df, variableColumns));
			}
			for(int i = 0; i < cnt; i++) {
				if(i == NR.getPearson().size()) break;
			}
		}
		//System.out.println(recollection.size());
		return recollection;
	}

}
