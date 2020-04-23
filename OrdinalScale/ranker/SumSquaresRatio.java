package ranker;

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
    	Column y = df.getColumn(df.targetIndexes.get(0));
        int k = y.getTotalUniqueValues();
        

        int n = df.getNumRows();
        int p = df.ge;
        int[] nc = new int[k];
        double[] mu = new double[p];
        double[][] condmu = new double[k][p];

        for (int i = 0; i < n; i++) {
            int yi = y[i];
            nc[yi]++;
            for (int j = 0; j < p; j++) {
                mu[j] += x[i][j];
                condmu[yi][j] += x[i][j];
            }
        }

        for (int j = 0; j < p; j++) {
            mu[j] /= n;
            for (int i = 0; i < k; i++) {
                condmu[i][j] /= nc[i];
            }
        }

        double[] wss = new double[p];
        double[] bss = new double[p];

        for (int i = 0; i < n; i++) {
            int yi = y[i];
            for (int j = 0; j < p; j++) {
                bss[j] += MathEx.sqr(condmu[yi][j] - mu[j]);
                wss[j] += MathEx.sqr(x[i][j] - condmu[yi][j]);
            }
        }

        for (int j = 0; j < p; j++) {
            bss[j] /= wss[j];
        }

        return bss;
    }
}
