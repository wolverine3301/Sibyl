package ranker;

import java.util.ArrayList;
import java.util.HashMap;

import dataframe.Column;
import dataframe.DataFrame;
import math.MathEx;

public class SumSquaresRatio implements FeatureRanking {
    public static final SumSquaresRatio instance = new SumSquaresRatio();

    @Override
    public double[] rank(DataFrame df) {
        return of(df);
    }

    /**
     * Univariate feature ranking. Note that this method actually does NOT rank
     * the features. It just returns the metric values of each feature. The
     * use can then rank and select features.
     *
     * @param x a n-by-p matrix of n instances with p features.
     * @param y class labels.
     * @return the sum of squares ratio of between-groups to within-groups.
     */
    public static double[] of(DataFrame df) {
    	Column y = df.getColumn(4);
        int k = y.getTotalUniqueValues(); // number of classes
        
        int n = df.getNumRows();
        int p = df.numNumeric;
        HashMap<Object,Integer> nc = new HashMap<Object,Integer>(); //classes
        double[] mu = new double[p]; //probability
        //double[][] condmu = new double[k][p]; //conditional probability
        HashMap<Object,Double[]> condmu = new HashMap<Object,Double[]>();
        //initiallize
        for(Object i : y.getUniqueValues()) {
        	nc.put(i, 0);
        	Double[] tmp = new Double[p];
        	condmu.put(i,tmp);
        }
        //fill fill fill rows
        for (int i = 0; i < n; i++) {
            Object yi = y.getParticle(i).getValue();
            nc.replace(yi, nc.get(yi)+1);
            //columns
            for (int j = 0; j < p; j++) {
                mu[j] += df.numeric_columns.get(j).getDoubleValue(i);
                condmu.get(yi)[j] += df.numeric_columns.get(j).getDoubleValue(i);
            }
        }
        //columns
        for (int j = 0; j < p; j++) {
            mu[j] /= n;
            //classes
            for (Object i : y.getUniqueValues()){
                condmu.get(i)[j] /= nc.get(i);
            }
        }

        double[] wss = new double[p];
        double[] bss = new double[p];
        //rows
        for (int i = 0; i < n; i++) {
            Object yi = y.getParticle(i).getValue();
            //columns
            for (int j = 0; j < p; j++) {
                bss[j] += MathEx.sqr(condmu.get(yi)[j] - mu[j]);
                wss[j] += MathEx.sqr(df.numeric_columns.get(j).getDoubleValue(i)- condmu.get(yi)[j]);
            }
        }

        for (int j = 0; j < p; j++) {
            bss[j] /= wss[j];
        }

        return bss;
    }
}
