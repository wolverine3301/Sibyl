package correlation;

import dataframe.Column;
import dataframe.DataFrame;

/**
 * correlation example
 * @author logan.collier
 *
 */
public class corrBench {

	public static void main(String[] args) {
		String file = "testfiles/iris.txt";
        DataFrame df = DataFrame.read_csv(file);
        //df.setColumnType(3, 'T');
        
        int[] a = {106,86,100,101,99,103,97,113,112,110};
        int[] b = {7,0,27,50,28,29,20,12,6,17};
        Column x = new Column(a);
        Column y = new Column(b);
        DataFrame d = new DataFrame();
        d.addColumn(x);
        d.addColumn(y);
        Pearson p = new Pearson();

        Correlations c = new Correlations(df,p);
        c.printCorrelations();
        Spearman s = new Spearman();
        Correlations v = new Correlations(df,s);
        v.printCorrelations();

	}

}
