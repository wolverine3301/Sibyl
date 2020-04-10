package matrix;

public class SVD {

    /**
     * Arrays for internal storage of left singular vectors U.
     */
    protected DenseMatrix U;
    /**
     * Arrays for internal storage of right singular vectors V.
     */
    protected DenseMatrix V;
    /**
     * Array for internal storage of singular values.
     */
    protected double[] s;
    /**
     * Is this a full decomposition?
     */
    protected boolean full;
    /**
     * The number of rows.
     */
    protected int m;
    /**
     * The number of columns.
     */
    protected int n;
    /**
     * Threshold of estimated roundoff.
     */
    protected double tol;

    /**
     * Constructor.
     */
    public SVD(DenseMatrix U, DenseMatrix V, double[] s) {
        this.U = U;
        this.V = V;
        this.s = s;

        m = U.nrows();
        n = V.nrows();
        full = s.length == Math.min(m, n);
        tol = 0.5 * Math.sqrt(m + n + 1.0) * s[0] * MathEx.EPSILON;
    }

    /**
     * Returns the left singular vectors
     */
    public DenseMatrix getU() {
        return U;
    }

    /**
     * Returns the right singular vectors
     */
    public DenseMatrix getV() {
        return V;
    }

    /**
     * Returns the one-dimensional array of singular values, ordered by
     * from largest to smallest.
     */
    public double[] getSingularValues() {
        return s;
    }

    /**
     * Returns the diagonal matrix of singular values
     */
    public DenseMatrix getS() {
        DenseMatrix S = Matrix.zeros(U.nrows(), V.nrows());

        for (int i = 0; i < s.length; i++) {
            S.set(i, i, s[i]);
        }

        return S;
    }

    /**
     * Returns the L2 matrix norm. The largest singular value.
     */
    public double norm() {
        return s[0];
    }

    /**
     * Returns the effective numerical matrix rank. The number of non-negligible
     * singular values.
     */
    public int rank() {
        if (!full) {
            throw new IllegalStateException("This is not a FULL singular value decomposition.");
        }

        int r = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i] > tol) {
                r++;
            }
        }
        return r;
    }

    /**
     * Returns the dimension of null space. The number of negligible
     * singular values.
     */
    public int nullity() {
        if (!full) {
            throw new IllegalStateException("This is not a FULL singular value decomposition.");
        }

        int r = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i] <= tol) {
                r++;
            }
        }
        return r;
    }

    /**
     * Returns the L<sub>2</sub> norm condition number, which is max(S) / min(S).
     * A system of equations is considered to be well-conditioned if a small
     * change in the coefficient matrix or a small change in the right hand
     * side results in a small change in the solution vector. Otherwise, it is
     * called ill-conditioned. Condition number is defined as the product of
     * the norm of A and the norm of A<sup>-1</sup>. If we use the usual
     * L<sub>2</sub> norm on vectors and the associated matrix norm, then the
     * condition number is the ratio of the largest singular value of matrix
     * A to the smallest. Condition number depends on the underlying norm.
     * However, regardless of the norm, it is always greater or equal to 1.
     * If it is close to one, the matrix is well conditioned. If the condition
     * number is large, then the matrix is said to be ill-conditioned. A matrix
     * that is not invertible has the condition number equal to infinity.
     */
    public double condition() {
        if (!full) {
            throw new IllegalStateException("This is not a FULL singular value decomposition.");
        }

        return (s[0] <= 0.0 || s[n - 1] <= 0.0) ? Double.POSITIVE_INFINITY : s[0] / s[n - 1];
    }

    /**
     * Returns a matrix of which columns give an orthonormal basis for the range space.
     */
    public DenseMatrix range() {
        if (!full) {
            throw new IllegalStateException("This is not a FULL singular value decomposition.");
        }

        int nr = 0;
        DenseMatrix rnge = Matrix.zeros(m, rank());
        for (int j = 0; j < n; j++) {
            if (s[j] > tol) {
                for (int i = 0; i < m; i++) {
                    rnge.set(i, nr, U.get(i, j));
                }
                nr++;
            }
        }
        return rnge;
    }

    /**
     * Returns a matrix of which columns give an orthonormal basis for the null space.
     */
    public DenseMatrix nullspace() {
        if (!full) {
            throw new IllegalStateException("This is not a FULL singular value decomposition.");
        }

        int nn = 0;
        DenseMatrix nullsp = Matrix.zeros(n, nullity());
        for (int j = 0; j < n; j++) {
            if (s[j] <= tol) {
                for (int jj = 0; jj < n; jj++) {
                    nullsp.set(jj, nn, V.get(jj, j));
                }
                nn++;
            }
        }
        return nullsp;
    }

    /**
     * Solve the least squares A*x = b.
     * @param b   right hand side of linear system.
     * @param x   the output solution vector that minimizes the L2 norm of A*x - b.
     * @exception  RuntimeException if matrix is rank deficient.
     */
    public void solve(double[] b, double[] x) {
        if (!full) {
            throw new IllegalStateException("This is not a FULL singular value decomposition.");
        }

        if (b.length != m || x.length != n) {
            throw new IllegalArgumentException("Dimensions do not agree.");
        }

        double[] tmp = new double[n];
        for (int j = 0; j < n; j++) {
            double r = 0.0;
            if (s[j] > tol) {
                for (int i = 0; i < m; i++) {
                    r += U.get(i, j) * b[i];
                }
                r /= s[j];
            }
            tmp[j] = r;
        }

        for (int j = 0; j < n; j++) {
            double r = 0.0;
            for (int jj = 0; jj < n; jj++) {
                r += V.get(j, jj) * tmp[jj];
            }
            x[j] = r;
        }
    }

    /**
     * Solve the least squares A * X = B. B will be overwritten with the solution
     * matrix on output.
     * @param B    right hand side of linear system. B will be overwritten with
     * the solution matrix on output.
     * @exception  RuntimeException  Matrix is rank deficient.
     */
    public void solve(DenseMatrix B) {
        if (!full) {
            throw new IllegalStateException("This is not a FULL singular value decomposition.");
        }

        if (B.nrows() != m) {
            throw new IllegalArgumentException("Dimensions do not agree.");
        }

        double[] b = new double[m];
        double[] x = new double[n];
        int p = B.ncols();
        for (int j = 0; j < p; j++) {
            for (int i = 0; i < m; i++) {
                b[i] = B.get(i, j);
            }

            solve(b, x);
            for (int i = 0; i < n; i++) {
                B.set(i, j, x[i]);
            }
        }
    }
}