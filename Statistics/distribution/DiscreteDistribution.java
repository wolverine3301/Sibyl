package distribution;

import math.MathEx;

public abstract class DiscreteDistribution extends AbstractDistribution {
    /**
     * Generates an integer random numbers following this discrete distribution.
     */
    public int randi() {
        return (int) rand();
    }

    /**
     * Generates a set of integer random numbers following this discrete distribution.
     */
    public int[] randi(int n) {
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = randi();
        }
        return data;
    }

    /**
     * The probability mass function.
     */
    public abstract double p(int x);

    @Override
    public double p(double x) {
        if (!MathEx.isInt(x)) {
            throw new IllegalArgumentException("x is not an integer");
        }

        return p((int)x);
    }

    /**
     * The probability mass function in log scale.
     */
    public abstract double logp(int x);
    
    @Override
    public double logp(double x) {
        if (!MathEx.isInt(x)) {
            throw new IllegalArgumentException("x is not an integer");
        }

        return logp((int)x);
    }
    
    /**
     * The likelihood given a sample set following the distribution.
     */
    public double likelihood(int[] x) {
        return Math.exp(logLikelihood(x));        
    }    
    
    /**
     * The likelihood given a sample set following the distribution.
     */
    public double logLikelihood(int[] x) {
        double L = 0.0;
        
        for (double xi : x)
            L += logp(xi);
        
        return L;        
    }

    /**
     * Invertion of cdf by bisection numeric root finding of
     * <code>cdf(x) = p</code> for discrete distribution.
     * @return an integer <code>n</code> such that
     *         <code>P(&lt;n) &le; p &le; P(&lt;n+1)</code>.
     */
    protected double quantile(double p, int xmin, int xmax) {
        while (xmax - xmin > 1) {
            int xmed = (xmax + xmin) / 2;
            if (cdf(xmed) > p) {
                xmax = xmed;
            } else {
                xmin = xmed;
            }
        }

        if (cdf(xmin) >= p)
            return xmin;
        else
            return xmax;
    }
}
