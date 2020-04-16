package forensics;

import dataframe.Column;
import forensics.Beta_f;

/**
 * 
 * @author logan
 * A t-test is a type of inferential statistic used to determine if there is a significant difference 
 * between the means of two groups
 * 
 * Mathematically, the t-test takes a sample from each of the two sets and establishes the problem 
 * statement by assuming a null hypothesis that the two means are equal. 
 * Based on the applicable formulas, certain values are calculated and compared against the standard values,
 *  and the assumed null hypothesis is accepted or rejected accordingly.
 *
 */
public class T_Test {
	
	private Column x;
	private Column y;
    
    public final String method;/**string indicating what type of test was performed. */
    public double df;/* The degree of freedom of t-statistic.*/
    public final double t;
    public final double pvalue;
    

    /**
     * Constructor.
     */
    private T_Test(String method, double t, double df, double pvalue) {
        this.method = method;
        this.t = t;
        this.df = df;
        this.pvalue = pvalue;
    }
    /**
     * Independent one-sample t-test whether the mean of a normally distributed
     * population has a value specified in a null hypothesis. Small values of
     * p-value indicate that the array has significantly different mean.
     */
    public static T_Test test(Column x, double mean) {
        int n = x.getLength();

        double mu = x.mean;
        double var = x.variance;

        int df = n - 1;

        double t = (mu - mean) / Math.sqrt(var/n);
        double p = Beta_f.regularizedIncompleteBetaFunction(0.5 * df, 0.5, df / (df + t * t));

        return new T_Test("One Sample", t, df, p);
    }
    /**
     * Test if the arrays x and y have significantly different means. The data
     * arrays are assumed to be drawn from populations with unequal variances.
     * Small values of p-value indicate that the two arrays have significantly
     * different means.
     */
    public static T_Test test(Column x, Column y) {
        return test(x, y, false);
    }
    /**
     * Test if the arrays x and y have significantly different means.  Small
     * values of p-value indicate that the two arrays have significantly
     * different means.
     * @param equalVariance true if the data arrays are assumed to be
     * drawn from populations with the same true variance. Otherwise, The data
     * arrays are allowed to be drawn from populations with unequal variances.
     */
    public static T_Test test(Column x, Column y, boolean equalVariance) {
        if (equalVariance) {
            int n1 = x.getLength();
            int n2 = y.getLength();

            double mu1 = x.mean;
            double var1 = x.variance;

            double mu2 = y.mean;
            double var2 = y.variance;

            int df = n1 + n2 - 2;

            double svar = ((n1 - 1) * var1 + (n2 - 1) * var2) / df;

            double t = (mu1 - mu2) / Math.sqrt(svar * (1.0 / n1 + 1.0 / n2));
            double p = Beta_f.regularizedIncompleteBetaFunction(0.5 * df, 0.5, df / (df + t * t));

            return new T_Test("Equal Variance Two Sample", t, df, p);
        } else {
            int n1 = x.getLength();
            int n2 = y.getLength();

            double mu1 = x.mean;
            double var1 = x.variance;

            double mu2 = y.mean;
            double var2 = y.variance;

            double df = sqr(var1 / n1 + var2 / n2) / (sqr(var1 / n1) / (n1 - 1) + sqr(var2 / n2) / (n2 - 1));

            double t = (mu1 - mu2) / Math.sqrt(var1 / n1 + var2 / n2);
            double p = Beta_f.regularizedIncompleteBetaFunction(0.5 * df, 0.5, df / (df + t * t));

            return new T_Test("Unequal Variance Two Sample", t, df, p);
        }
    }
    /**
     * Given the paired arrays x and y, test if they have significantly
     * different means. Small values of p-value indicate that the two arrays
     * have significantly different means.
     */
    public static T_Test testPaired(Column x, Column y) {
        if (x.getLength() != y.getLength()) {
            throw new IllegalArgumentException("Input vectors have different size");
        }

        double mu1 = x.mean;
        double var1 = x.variance;

        double mu2 = y.mean;
        double var2 = y.variance;

        int n = x.getLength();
        int df = n - 1;

        double cov = 0.0;
        for (int j = 0; j < n; j++) {
            cov += (x.getDoubleValue(j) - mu1) * (y.getDoubleValue(j) - mu2);
        }
        cov /= df;

        double sd = Math.sqrt((var1 + var2 - 2.0 * cov) / n);
        double t = (mu1 - mu2) / sd;
        double p = Beta_f.regularizedIncompleteBetaFunction(0.5 * df, 0.5, df / (df + t * t));

        return new T_Test("Paired", t, df, p);
    }
    /**
     * Test whether the Pearson correlation coefficient, the slope of
     * a regression line, differs significantly from 0. Small values of p-value
     * indicate a significant correlation.
     * @param r the Pearson correlation coefficient.
     * @param df the degree of freedom. df = n - 2, where n is the number of samples
     * used in the calculation of r.
     */
    public static T_Test test(double r, int df) {
        final double TINY = 1.0e-16;

        double t = r * Math.sqrt(df / ((1.0 - r + TINY) * (1.0 + r + TINY)));
        double p = Beta_f.regularizedIncompleteBetaFunction(0.5 * df, 0.5, df / (df + t * t));

        return new T_Test("Pearson correlation coefficient", t, df, p);
    }
    
	private void equal_variance_DegreesFreedom() {
		this.df = (2* x.getLength()) - 2;
	}
	private void unequal_variance_DegreesFreedom() {
		this.df = Math.pow(( (Math.pow(x.variance, 2) / x.getLength()) +  (Math.pow(y.variance, 2) / y.getLength())) , 2)  / ( (Math.pow( (Math.pow(x.variance, 2) / x.getLength()), 2) / (x.getLength() - 1)) + (Math.pow( (Math.pow(y.variance, 2) / y.getLength()), 2) / (y.getLength() - 1)) );
	}

	/**
	 * LOOK! MATH!
	 * @return the p-value
	 
	public double equal_variance_tValue() {
		equal_variance_DegreesFreedom();
		return (x.mean- y.mean) / (Math.sqrt( (((x.getLength()-1) * Math.pow(x.variance,2)) + ((y.getLength()-1) * Math.pow(y.variance,2))) / ((2 * x.getLength()-2)) ) * Math.sqrt( (1/x.getLength()) + (1/y.getLength())));
	}
	public double unequal_variance_tValue() {
		unequal_variance_DegreesFreedom();
		return (x.mean - y.mean) / Math.sqrt( (Math.pow(x.variance, 2) / x.getLength()) + (Math.pow(y.variance, 2) / y.getLength()) );
	}
	public double get_Tvalue() {
		System.out.println((double)1/x.getLength());
		System.out.println((1/x.getLength()) + (1/y.getLength()));
		System.out.println(Math.sqrt( (1/x.getLength()) + (1/y.getLength())));
		System.out.println(  (x.mean-y.mean) / ( (( (x.getLength()-1) * Math.pow(x.variance, 2) )) + ( (y.getLength() -1) * Math.pow(y.variance, 2) )) /((x.getLength()+y.getLength()-2)) * Math.sqrt( (1/x.getLength()) + (1/y.getLength())) );
		return (x.mean-y.mean) / ((( (x.getLength()-1) * Math.pow(x.variance, 2) ) + ( (y.getLength() -1) * Math.pow(y.variance, 2) )) / ((x.getLength()+y.getLength()-2)) * Math.sqrt( ((double)1/x.getLength()) + ((double)1/y.getLength())) );
	}
    /**
     * Returns x * x.
     */
    public static double sqr(double x) {
        return x * x;
    }

}
