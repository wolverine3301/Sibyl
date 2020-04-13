package math;

/**
 * A differentiable function is a function whose derivative exists at each point
 * in its domain.
 *
 * @author Haifeng Li
 */
public interface DifferentiableFunction extends Function {
    /**
     * Computes the gradient/derivative at x.
     */
    double g(double x);

    /**
     * Compute the second-order derivative at x.
     */
    default double g2(double x) {
        throw new UnsupportedOperationException();
    }
}