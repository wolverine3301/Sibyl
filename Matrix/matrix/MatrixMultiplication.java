package matrix;

public interface MatrixMultiplication<A, B> {
    /**
     * Returns the result of matrix multiplication A * B.
     */
    A abmm(B b);

    /**
     * Returns the result of matrix multiplication A * B'.
     */
    A abtmm(B b);

    /**
     * Returns the result of matrix multiplication A' * B.
     */
    A atbmm(B b);

    /**
     * Returns the result of matrix multiplication A' * B'.
     */
    A atbtmm(B b);
}
