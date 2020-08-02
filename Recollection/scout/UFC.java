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
import ranker.Chi2Ranker;
import scorer.CrossValidation;
import scorer.Evaluate;
import scorer.Metric;

public class UFC {
	//behold, my will creates your body and your sword my destiny
	private static CategoryRanker CR;
	//
	private static NumericRanker NR; 
	private static DataFrame  df;
	
	public static void main(String[] args) {
		Loggers.logHTML(Loggers.df_Logger, Level.FINE);
		Loggers.logHTML(Loggers.cm_Logger, Level.FINE);
		Loggers.logHTML(Loggers.cv_Logger,Level.FINE);
		Loggers.logHTML(Loggers.nb_Logger,Level.FINE);
		Loggers.logHTML(Loggers.score_Logger,Level.FINE);
		String file = "testfiles/preprocessed_data.csv";
        df = DataFrame.read_csv(file);
        String[] arg = {"no_of_rounds", "!=","4"};
        df = DataFrame_Copy.acquire(df, arg);
        df.setColumnType("Winner", 'T');//set target column
        df.setColumnType("no_of_rounds", 'C');
        //df.setColumnType("no_of_rounds", 'C');
        //df.setStatistics(2);
        df.setColumnType("no_of_rounds", 'T');
        //df.convertNANS_mean(); // conevert any NAN's to the mean of column 
        //df = Standardize.standardize_df(df); //Standardize the DF into z scores
        //df = df.shuffle(df);
		NaiveBayes2 nb = new NaiveBayes2();
		Model model = new KNN();
		Model con = new Constant();
        //NR = new NumericRanker(df,0);
        //System.out.println(NR.getSpearman());
        //CR = new CategoryRanker(df, 0);
		//ArrayList<DataFrame> ev = generateRecollection(10,15);
		//for(DataFrame i : ev) {
		//CrossValidation cv = new CrossValidation(df,5, model);
		Evaluate ev = new Evaluate(df.target_columns);
		ev.setMetric(Metric.MCC);
		ArrayList<DataFrame> re = reco(df,10,14,1);
		for(DataFrame i : re) {
			CrossValidation cv = new CrossValidation(df,10, nb);
			System.out.println(i.getNumColumns());
			ev.evaluation(cv);
			ev.getBest();
	        cv.printOverAllScore();
	        //cv.printOverAllMatrix();
	        //System.out.println();
		}
		//}
       



	}
	private static ArrayList<DataFrame> reco(DataFrame df,int initialNumColumns, int terminate,int stepSize){
		ArrayList<DataFrame> recollection = new ArrayList<DataFrame>();
		for(int i = initialNumColumns; i < terminate; i+=stepSize) {
			recollection.add(Chi2Ranker.chi2Rank(df, i));
		}
		return recollection;
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
