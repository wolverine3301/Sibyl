package matrix;

/**
 * The matrix of A' * A. For SVD, we compute eigen decomposition of A' * A
 * when m >= n, or that of A * A' when m < n.
 */
class ATA implements Matrix {

    private Matrix A;
    private Matrix AtA;
    double[] buf;

    public ATA(Matrix A) {
        this.A = A;

        if (A.nrows() >= A.ncols()) {
            buf = new double[A.nrows()];

            if ((A.ncols() < 10000) && (A instanceof DenseMatrix)) {
                AtA = A.ata();
            }
        } else {
            buf = new double[A.ncols()];

            if ((A.nrows() < 10000) && (A instanceof DenseMatrix)) {
                AtA = A.aat();
            }
        }
    }

    @Override
    public int nrows() {
        if (A.nrows() >= A.ncols()) {
            return A.ncols();
        } else {
            return A.nrows();
        }
    }

    @Override
    public int ncols() {
        return nrows();
    }

    @Override
    public ATA transpose() {
        return this;
    }

    @Override
    public ATA ata() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ATA aat() {
        throw new UnsupportedOperationException();
    }

    @Override
    public double[] ax(double[] x, double[] y) {
        if (AtA != null) {
            AtA.ax(x, y);
        } else {
            if (A.nrows() >= A.ncols()) {
                A.ax(x, buf);
                A.atx(buf, y);
            } else {
                A.atx(x, buf);
                A.ax(buf, y);
            }
        }

        return y;
    }

    @Override
    public boolean isSymmetric() {
        return true;
    }

    @Override
    public double[] atx(double[] x, double[] y) {
        return ax(x, y);
    }

    @Override
    public double[] axpy(double[] x, double[] y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double[] axpy(double[] x, double[] y, double b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double get(int i, int j) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double apply(int i, int j) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double[] atxpy(double[] x, double[] y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double[] atxpy(double[] x, double[] y, double b) {
        throw new UnsupportedOperationException();
    }
}
