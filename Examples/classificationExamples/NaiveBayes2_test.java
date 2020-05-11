package classificationExamples;

import bayes.NaiveBayes2;
import dataframe.Column;
import dataframe.DataFrame;
import dataframe.Row;
import particles.Particle;

public class NaiveBayes2_test {

	public static void main(String[] args) {
		String file = "testfiles/catTest.txt";
        DataFrame df = DataFrame.read_csv(file);
        df.setColumnType(0, 'T');
        NaiveBayes2 nb  = new NaiveBayes2(df);
        for(Column i : df.columns) {
        	System.out.println(i.getType());
        }
        nb.printCategorical_probabilityTable();
        Row row = new Row();
        Particle p1 = Particle.resolveType("sad");
        Particle p2 = Particle.resolveType("f");
        Particle p3 = Particle.resolveType("w");
        
        row.add(p1);
        row.add(p2);
        row.add(p3);
        System.out.println(nb.predict(row));
        nb.printCategorical_probabilityTable();
	}

}
