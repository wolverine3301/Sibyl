package scout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import bayes.NaiveBayes;
import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Copy;
import logan.sybilGUI.TextAreaOutputStream;
import scorer.CrossValidation;
import transform.Standardize;
/**
 * @author logan.collier
 *
 */
public class Invoke {
	//behold, my will creates your body and your sword my destiny
	private static CategoryRanker CR;
	//
	private static NumericRanker NR; 
	private static DataFrame  df;
	public void start() {
		String file = "testfiles/iris.txt";
        df = DataFrame.read_csv(file);
        df.setColumnType("species", 'T');//set target column
        df.convertNANS_mean(); // conevert any NAN's to the mean of column 
        df = Standardize.standardize_df(df); //Standardize the DF into z scores
        df = df.shuffle(df);
		NaiveBayes nb = new NaiveBayes();
        CrossValidation cv = new CrossValidation(df, 2, nb);
        //cv.avgScores();
        //cv.printScores();
        //cv.printMatrixs();
        //System.out.println(df.columnNamesToString());
        NR = new NumericRanker(df,0);
        //System.out.println(NR.getSpearman());
        CR = new CategoryRanker(df, 0);
		
		//CR.printRankings();
		generateRecollection(2, 4);
	}
	/*
	public static void main(String[] args) {
		
		
        JFrame frame = new JFrame();
        frame.add( new JLabel(" out " ), BorderLayout.NORTH );

        JTextArea ta = new JTextArea();
        ta.setBackground(Color.black);
        ta.setForeground(Color.gray);
        TextAreaOutputStream taos = new TextAreaOutputStream( ta, 60 );
        PrintStream ps = new PrintStream( taos );
        System.setOut( ps );
        System.setErr( ps );


        frame.add( new JScrollPane( ta )  );

        frame.pack();
        frame.setVisible( true );
        frame.setSize(800,600);

		String file = "testfiles/iris.txt";
        df = DataFrame.read_csv(file);
        df.setColumnType("species", 'T');//set target column
        df.convertNANS_mean(); // conevert any NAN's to the mean of column 
        df = Standardize.standardize_df(df); //Standardize the DF into z scores
        df = df.shuffle(df);
		NaiveBayes nb = new NaiveBayes();
        CrossValidation cv = new CrossValidation(df, 2, nb);
        //cv.avgScores();
        //cv.printScores();
        //cv.printMatrixs();
        //System.out.println(df.columnNamesToString());
        NR = new NumericRanker(df,0);
        //System.out.println(NR.getSpearman());
        CR = new CategoryRanker(df, 0);
		
		//CR.printRankings();
		generateRecollection(2, 4);

	}
	*/
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
		System.out.println(recollection.size());
		return recollection;
	}
	

}
