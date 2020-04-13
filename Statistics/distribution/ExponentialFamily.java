package distribution;

public interface ExponentialFamily {

    /**
     * The M step in the EM algorithm, which depends the specific distribution.
     *
     * @param x the input data for estimation
     * @param posteriori the posteriori probability.
     * @return the (unnormalized) weight of this distribution in the mixture.
     */
    Mixture.Component M(double[] x , double[] posteriori);
}
