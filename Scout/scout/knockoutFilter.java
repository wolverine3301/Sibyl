package scout;

import bayes.NaiveBayes;
import dataframe.Column;
import dataframe.DataFrame;
import discreteize.EqualFrequencyBinning;
import scorer.CrossValidation;

public class knockoutFilter {

	public static void main(String[] args) {
		
		String file = "testfiles/knockoutset.csv";
        DataFrame df = DataFrame.read_csv(file);
        EqualFrequencyBinning e = new EqualFrequencyBinning(8, df.getColumn(11));
        df.replaceColumn(11, e.binColumn());
        System.out.println(df.columnNamesToString());
        System.out.println(df.getNumColumns());
        df.setColumnType(11, 'T');
        df.setColumnType(10, 'C');
		for(Column i: df.columns) {
        	System.out.print(i.getType()+"\t");
        }
		System.out.println();
        System.out.println(df.getColumn(10).getUniqueValueCounts());
        NaiveBayes nb = new NaiveBayes();
        CrossValidation cv = new CrossValidation(df, 4, nb);
        cv.avgScores();
        cv.sumConfusionMatrix();
        cv.printOverAllScore();
        cv.printOverAllMatrix();

	}

}
