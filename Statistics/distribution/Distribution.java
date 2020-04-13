package distribution;

import java.io.Serializable;

public interface Distribution extends Serializable {

    /**
     * The number of parameters of the distribution.
     * The "length" is in the sense of the minimum description
     * length principle.
     */
    int length();

    /**
     * The mean of distribution.
     */
    double mean();
    
    /**
     * The variance of distribution.
     */
    double variance();
    
    /**
     * The standard deviation of distribution.
     */
    default double sd() {
        return Math.sqrt(variance());
    }

    /**
     * Shannon entropy of the distribution.
     */
    double entropy();

    /**
     * Generates a random number following this distribution.
     */
    double rand();

    /**
     * Generates a set of random numbers following this distribution.
     */
    default double[] rand(int n) {
        double[] data = new double[n];
        for (int i = 0; i < n; i++) {
            data[i] = rand();
        }
        return data;
    }

    /**
     * The probability density function for continuous distribution
     * or probability mass function for discrete distribution at x.
     */
    double p(double x);

    /**
     * The density at x in log scale, which may prevents the underflow problem.
     */
    double logp(double x);

    /**
     * Cumulative distribution function. That is the probability to the left of x.
     */
    double cdf(double x);

    /**
     * The quantile, the probability to the left of quantile is p. It is
     * actually the inverse of cdf.
     */
    double quantile(double p);

    /**
     * The likelihood of the sample set following this distribution.
     */
    default double likelihood(double[] x) {
        return Math.exp(logLikelihood(x));
    }
    
    /**
     * The log likelihood of the sample set following this distribution.
     */
    default double logLikelihood(double[] x) {
        double L = 0.0;

        for (double xi : x)
            L += logp(xi);

        return L;
    }
}
