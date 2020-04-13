package distribution;

import math.MathEx;

public abstract class AbstractDistribution implements Distribution {
    /**
     * Use the rejection technique to draw a sample from the given
     * distribution. <em>WARNING</em>: this simulation technique can
     * take a very long time. Rejection sampling is also commonly
     * called the acceptance-rejection method or "accept-reject algorithm".
     * It generates sampling values from an arbitrary probability distribution
     * function f(x) by using an instrumental distribution g(x), under the
     * only restriction that <code>f(x) &lt; M g(x)</code> where
     * <code>M &gt; 1</code> is an appropriate bound on
     * <code>f(x) / g(x)</code>.
     * <p>
     * Rejection sampling is usually used in cases where the form of
     * <code>f(x)</code> makes sampling difficult. Instead of sampling
     * directly from the distribution <code>f(x)</code>, we use an envelope
     * distribution <code>M g(x)</code> where sampling is easier. These
     * samples from <code>M g(x)</code> are probabilistically
     * accepted or rejected.
     * <p>
     * This method relates to the general field of Monte Carlo techniques,
     * including Markov chain Monte Carlo algorithms that also use a proxy
     * distribution to achieve simulation from the target distribution
     * <code>f(x)</code>. It forms the basis for algorithms such as
     * the Metropolis algorithm.
     */
    protected double rejection(double pmax, double xmin, double xmax) {
        double x;
        double y;
        do {
            x = xmin + MathEx.random() * (xmax - xmin);
            y = MathEx.random() * pmax;
        } while (p(x) < y);

        return x;
    }

    /**
     * Use inverse transform sampling (also known as the inverse probability
     * integral transform or inverse transformation method or Smirnov transform)
     * to draw a sample from the given distribution. This is a method for
     * generating sample numbers at random from any probability distribution
     * given its cumulative distribution function (cdf). Subject to the
     * restriction that the distribution is continuous, this method is
     * generally applicable (and can be computationally efficient if the
     * cdf can be analytically inverted), but may be too computationally
     * expensive in practice for some probability distributions. The
     * Box-Muller transform is an example of an algorithm which is
     * less general but more computationally efficient. It is often the
     * case that, even for simple distributions, the inverse transform
     * sampling method can be improved on, given substantial research
     * effort, e.g. the ziggurat algorithm and rejection sampling.
     */
    protected double inverseTransformSampling() {
        double u = MathEx.random();
        return quantile(u);
    }
    
    /**
     * Inversion of CDF by bisection numeric root finding of "cdf(x) = p"
     * for continuous distribution.
     */
    protected double quantile(double p, double xmin, double xmax, double eps) {
        if (eps <= 0.0) {
            throw new IllegalArgumentException("Invalid epsilon: " + eps);            
        }
        
        while (Math.abs(xmax - xmin) > eps) {
            double xmed = (xmax + xmin) / 2;
            if (cdf(xmed) > p) {
                xmax = xmed;
            } else {
                xmin = xmed;
            }
        }

        return xmin;
    }

    /**
     * Inversion of CDF by bisection numeric root finding of "cdf(x) = p"
     * for continuous distribution. The default epsilon is 1E-6.
     */
    protected double quantile(double p, double xmin, double xmax) {
        return quantile(p, xmin, xmax, 1.0E-6);
    }
}
