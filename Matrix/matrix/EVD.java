package matrix;

public class EVD {
    /**
     * Array of (real part of) eigenvalues.
     */
    private double[] d;
    /**
     * Array of imaginary part of eigenvalues.
     */
    private double[] e;
    /**
     * Array of eigen vectors.
     */
    private DenseMatrix V;

    /**
     * Private constructor.
     * @param V eigenvectors.
     * @param d eigenvalues.
     */
    public EVD(DenseMatrix V, double[] d) {
        this.V = V;
        this.d = d;
    }

    /**
     * Private constructor.
     * @param V eigenvectors.
     * @param d real part of eigenvalues.
     * @param e imaginary part of eigenvalues.
     */
    public EVD(DenseMatrix V, double[] d, double[] e) {
        this.V = V;
        this.d = d;
        this.e = e;
    }

    /**
     * Returns the eigenvector matrix, ordered by eigen values from largest to smallest.
     */
    public DenseMatrix getEigenVectors() {
        return V;
    }

    /**
     * Returns the eigenvalues, ordered from largest to smallest.
     */
    public double[] getEigenValues() {
        return d;
    }

    /**
     * Returns the real parts of the eigenvalues, ordered in real part from
     * largest to smallest.
     */
    public double[] getRealEigenValues() {
        return d;
    }

    /**
     * Returns the imaginary parts of the eigenvalues, ordered in real part
     * from largest to smallest.
     */
    public double[] getImagEigenValues() {
        return e;
    }

    /**
     * Returns the block diagonal eigenvalue matrix whose diagonal are the real
     * part of eigenvalues, lower subdiagonal are positive imaginary parts, and
     * upper subdiagonal are negative imaginary parts.
     */
    public DenseMatrix getD() {
        int n = V.nrows();
        DenseMatrix D = Matrix.zeros(n, n);
        for (int i = 0; i < n; i++) {
            D.set(i, i, d[i]);
            if (e != null) {
                if (e[i] > 0) {
                    D.set(i, i + 1, e[i]);
                } else if (e[i] < 0) {
                    D.set(i, i - 1, e[i]);
                }
            }
        }
        return D;
    }
}
