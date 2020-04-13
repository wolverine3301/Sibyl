package math;

import java.io.Serializable;
import java.util.function.ToDoubleFunction;

public interface MultivariateFunction extends ToDoubleFunction<double[]>, Serializable {
    /**
     * Computes the value of the function at x.
     */
    double f(double[] x);

    /**
     * Computes the value of the function at x.
     * It delegates the computation to f().
     * This is simply for Scala convenience.
     */
    default double apply(double... x) {
        return f(x);
    }

    @Override
    default double applyAsDouble(double[] x) {
        return f(x);
    }
}