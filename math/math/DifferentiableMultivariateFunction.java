package math;

public interface DifferentiableMultivariateFunction extends MultivariateFunction {

    /**
     * Computes the value and gradient at x.
     */
    double g(double[] x, double[] gradient);
}
