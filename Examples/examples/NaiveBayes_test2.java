package examples;

import java.util.ArrayList;

import dataframe.DataFrame;
import dataframe.DataFrame_Utilities;
import discreteize.EqualFrequencyBinning;
import machinations.NaiveBayes2;
import scorer.CrossValidation;

public class NaiveBayes_test2 {

	public static void main(String[] args) {
		String file = "testfiles/knockoutset.csv";
        DataFrame df = DataFrame.read_csv(file);
        System.out.println(df.columnNamesToString());
        EqualFrequencyBinning a = new EqualFrequencyBinning(8, df.getColumn_byName("percentProfit"));
        df.replaceColumn(11, a.binColumn());
        df.setColumnType(11, 'T');
        /*
        df.setColumnType(4, 'T');
        EqualFrequencyBinning a = new EqualFrequencyBinning(8, df.getColumn(0));
        df.replaceColumn(0, a.binColumn());
        EqualFrequencyBinning b = new EqualFrequencyBinning(8, df.getColumn(1));
        df.replaceColumn(1, b.binColumn());
        EqualFrequencyBinning c = new EqualFrequencyBinning(8, df.getColumn(2));
        df.replaceColumn(2, c.binColumn());
        EqualFrequencyBinning d = new EqualFrequencyBinning(8, df.getColumn(3));

        df.replaceColumn(3, d.binColumn());
        */
        NaiveBayes2 nb  = new NaiveBayes2();
        //nb.printCategorical_probabilityTable();
        CrossValidation cv = new CrossValidation(df, 2, nb);
        cv.avgScores();
        cv.sumConfusionMatrix();
        cv.printOverAllScore();
        cv.printOverAllMatrix();
        
        NaiveBayes2 nb1  = new NaiveBayes2(df);
        //nb1.saveModel("NaiveBayes");

	}

}
