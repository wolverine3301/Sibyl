package math;

import java.io.Serializable;

/**
 * An interface representing a univariate real function.
 */
public interface Function extends Serializable {
    /**
     * Computes the value of the function at x.
     */
    double f(double x);

    /**
     * Computes the value of the function at x.
     * It delegates the computation to f().
     * This is simply for Scala convenience.
     */
    default double apply(double x) {
        return f(x);
    }
}
