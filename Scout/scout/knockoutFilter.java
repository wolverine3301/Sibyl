package scout;

import java.io.IOException;

import bayes.NaiveBayes;
import bayes.NaiveBayes2;
import bayes.NaiveBayes_A;
import dataframe.Column;
import dataframe.DataFrame;
import discreteize.EqualFrequencyBinning;
import scorer.CrossValidation;

public class knockoutFilter {

	public static void main(String[] args) throws IOException {
		
		String file = "testfiles/test_data.txt";
        DataFrame df = DataFrame.read_csv(file);
        df.setColumnType(7, 'T');
        //df.printDataFrame();
        /*
        //EqualFrequencyBinning e = new EqualFrequencyBinning(8, df.getColumn(11));
        //e.printBins();
        //df.replaceColumn(11, e.binColumn());
        System.out.println(df.columnNamesToString());
        System.out.println(df.getNumColumns());
        
        df.setColumnType(11, 'T');
        //df.setColumnType(10, 'C');
		for(Column i: df.columns) {
        	System.out.print(i.getType()+"\t");
        }
        
		System.out.println();
        System.out.println(df.getColumn(10).getUniqueValueCounts());
        */
        NaiveBayes2 nb = new NaiveBayes2(df);
        nb.printCategorical_probabilityTable();
        nb.saveModel("MODEL");
        NaiveBayes_A a = NaiveBayes_A.loadModel("MODEL.ser");
        //CrossValidation cv = new CrossValidation(df, 2, nb);
        /*
        CrossValidation cv = new CrossValidation(df, 2, nb);
        cv.avgScores();
        cv.sumConfusionMatrix();
        cv.printOverAllScore();
        cv.printOverAllMatrix();
        */

	}

}
