package regressionFunctions;


import java.util.Arrays;

import dataframe.Column;
import forensics.Stats;
import particles.Particle;
/**
 * step 1: calculate a weird matrix made by math
 * step 2: do some kind of fuckin witchcraft made by a mathematician
 * step 3: done
 * http://polynomialregression.drque.net/math.html
 * @author logan.collier
 *
 */
public class PolyRegression extends Regression{
	private Column x;
	private Column y;
	private double[][] matrix_x;
	private double[][] matrix_xy;
	private double[] coefficent_matrix;
	private double[] coefficent_se; //std err of each coefficent
	private double[] coefficent_t_scores;
	private int[] coefficent_df; //degrees of freedom for coefficents
	int degree; // degree of the polynomial
	/**
	 * 
	 * @param x
	 * @param y
	 * @param degree - max degree of polynomial
	 */
	public PolyRegression(Column x, Column y, int degree) {
		super(x,y);
		this.degree = degree;
		setRegression();
		setMeasures();
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}
	/**
	 * The coefficent matrix is the constant infront of each X^degree
	 * the degree to raise an x value to is the index number+1 that the coefficent is in
	 */
	private void setCoefficentMatrix() {
		double[][] m = multiply(inverse(this.matrix_x), this.matrix_xy);
		this.coefficent_matrix = new double[m.length];
		for(int i = m.length-1; i>= 0; i--) {
			this.coefficent_matrix[i] = m[i][0];
		}
	}
	@Override
	protected void setRegression() {
		degree_sums();
		setCoefficentMatrix();
	}
	@Override
	public String getEquation() {
		String eq = "y = ";
		for (int i = this.coefficent_matrix.length-1; i >= 0; i--) {
			if(i == 1) {
				eq = eq.concat(String.valueOf(this.coefficent_matrix[i]));
				eq = eq.concat("X");
				if(this.coefficent_matrix[i-1] >= 0) {
					eq = eq.concat(" + ");
				}
			}else if(i == 0) {
				eq = eq.concat(String.valueOf(this.coefficent_matrix[i]));
				return eq;
			}else {
				eq = eq.concat(String.valueOf(this.coefficent_matrix[i]));
				eq = eq.concat("X");
				eq = eq.concat("^");
				eq = eq.concat(String.valueOf(i));
				if(this.coefficent_matrix[i-1] >= 0) {
					eq = eq.concat(" + ");
				}
			}
		}
		return eq;
	}

	public void print_coefficentMatrix() {
		for (double i : this.coefficent_matrix)
			System.out.print(i + " ");
		System.out.println();
	}
	/**
	 * find the determinant of the matrix
	 * @param matrix
	 * @return
	 */
	private double determinant(double[][] matrix) {
		if (matrix.length != matrix[0].length)
			throw new IllegalStateException("invalid dimensions");

		if (matrix.length == 2)
			return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

		double det = 0;
		for (int i = 0; i < matrix[0].length; i++)
			det += Math.pow(-1, i) * matrix[0][i] * determinant(minor(matrix, 0, i));
		return det;
	}
	/**
	 * return the inverse of a matrix
	 * @param matrix
	 * @return
	 */
	private double[][] inverse(double[][] matrix) {
		double[][] inverse = new double[matrix.length][matrix.length];

		// minors and cofactors
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++)
				inverse[i][j] = Math.pow(-1, i + j)
						* determinant(minor(matrix, i, j));

		// adjugate and determinant
		double det = 1.0 / determinant(matrix);
		for (int i = 0; i < inverse.length; i++) {
			for (int j = 0; j <= i; j++) {
				double temp = inverse[i][j];
				inverse[i][j] = inverse[j][i] * det;
				inverse[j][i] = temp * det;
			}
		}

		return inverse;
	}
	/**
	 * creat a minor of a larger matrix
	 * @param matrix
	 * @param row
	 * @param column
	 * @return
	 */
	private double[][] minor(double[][] matrix, int row, int column) {
		double[][] minor = new double[matrix.length - 1][matrix.length - 1];

		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; i != row && j < matrix[i].length; j++)
				if (j != column)
					minor[i < row ? i : i - 1][j < column ? j : j - 1] = matrix[i][j];
		return minor;
	}
	/**
	 * matrix multiplication
	 * @param a
	 * @param b
	 * @return
	 */
	private double[][] multiply(double[][] a, double[][] b) {
		if (a[0].length != b.length)
			throw new IllegalStateException("invalid dimensions");

		double[][] matrix = new double[a.length][b[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				double sum = 0;
				for (int k = 0; k < a[i].length; k++)
					sum += a[i][k] * b[k][j];
				matrix[i][j] = sum;
			}
		}

		return matrix;
	}
	/**
	 * using reduced row echelon form
	 * @param matrix
	 * @return
	 */
	private double[][] rref(double[][] matrix) {
		double[][] rref = new double[matrix.length][];
		for (int i = 0; i < matrix.length; i++)
			rref[i] = Arrays.copyOf(matrix[i], matrix[i].length);

		int r = 0;
		for (int c = 0; c < rref[0].length && r < rref.length; c++) {
			int j = r;
			for (int i = r + 1; i < rref.length; i++)
				if (Math.abs(rref[i][c]) > Math.abs(rref[j][c]))
					j = i;
			if (Math.abs(rref[j][c]) < 0.00001)
				continue;

			double[] temp = rref[j];
			rref[j] = rref[r];
			rref[r] = temp;

			double s = 1.0 / rref[r][c];
			for (j = 0; j < rref[0].length; j++)
				rref[r][j] *= s;
			for (int i = 0; i < rref.length; i++) {
				if (i != r) {
					double t = rref[i][c];
					for (j = 0; j < rref[0].length; j++)
						rref[i][j] -= t * rref[r][j];
				}
			}
			r++;
		}
		System.out.println("INVERSING");
		for (double[] i : matrix)
			System.out.println(Arrays.toString(i));
		System.out.println();
		return rref;
	}
	/**
	 * Method to carry out the partial-pivoting Gaussian elimination. Here index[] stores pivoting order.
	 * @param a double[][] - array to pivot
	 * @param index int[] - pivoting order
	 */
    public static void gaussian(double a[][], int index[]) {
        int n = index.length;
        double c[] = new double[n];
 
        // Initialize the index
        for (int i=0; i<n; ++i) 
            index[i] = i;
 
        // Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i) {
            double c1 = 0;
            for (int j=0; j<n; ++j) {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }
 
        // Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j) {
            double pi1 = 0;
            for (int i=j; i<n; ++i) {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }
 
            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i) {
                double pj = a[index[i]][j]/a[index[j]][j];
 
                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;
 
                // Modify other elements accordingly
                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] -= pj*a[index[j]][l];
            }
        }
    }

    /**
     * set the x matrix
     */
	private void degree_sums() {
		double[] poly_x = new double[this.degree*2];
		poly_x[0] = super.x.sum;
		
		double[][] poly_xy = new double[this.degree+1][1];
		poly_xy[0][0] = super.y.sum;
		poly_xy[1][0] = Stats.sumMultiple_Columns(super.x, super.y);
		int cnt_xy = 2;
		int cnt_x = 1;
		for(int i = 2; i <= poly_x.length; i++) {
			double sum_x = 0;
			double sum_xy = 0;
			for(int j = 0; j < super.x.getLength(); j++) {
				sum_x = sum_x + Math.pow(super.x.getDoubleValue(j), i);
				if(cnt_xy < poly_xy.length)
					sum_xy = sum_xy + (Math.pow(super.x.getDoubleValue(j), i) * super.y.getDoubleValue(j));
			}
			
			poly_x[cnt_x] = sum_x;
			if(cnt_xy < poly_xy.length)
				poly_xy[cnt_xy][0] = sum_xy;
			cnt_x++;
			cnt_xy++;
		}
		//System.out.println("X:");
		//for(int h = 0; h < poly_x.length; h++) {
		//	System.out.println(poly_x[h]);
		//}
		//System.out.println("Y:");
		//for(int h = 0; h < poly_xy.length; h++) {
		//	System.out.println(poly_xy[h]);
		//}
		this.matrix_xy = poly_xy;
		
		double[][] x_matrix = new double[this.degree+1][this.degree+1];
		x_matrix[0][0] = super.x.getLength();
		cnt_x = 0;
		for(int i = 0; i <= this.degree; i++) {
			
			int j;
			if(i==0) {
				j=1;
				cnt_x = 0;
			}
			else {
				j = 0;
				cnt_x = i-1;
			}
			
			for(;j <= this.degree;j++) {
				if(i == this.degree && j == this.degree) {
					break;
				}
				x_matrix[i][j] = poly_x[cnt_x];
				cnt_x++;
			}
		}
		x_matrix[this.degree][this.degree] = poly_x[poly_x.length-1];
		//for(int i = 0; i <= this.degree; i++) {
		//	for(int j = 0; j <= this.degree;j++) {
		//		System.out.print("["+x_matrix[i][j]+"]");
		//	}
		//	System.out.println();
		//}
		this.matrix_x = x_matrix;
	}
	/**
	 * sets the standard error of coefficents
	 */
	public void set_se_ofCoefficents() {
		this.coefficent_se = new double[this.coefficent_matrix.length];
		double[][] ix = this.inverse(this.matrix_x);
		for(int i = 0; i < ix.length;i++) {
			this.coefficent_se[i] = Math.sqrt(this.RMSD* ix[i][i]);
			System.out.println(this.coefficent_se[i]);
		}
	}
	/**
	 * set the t scores of the coeffiecents
	 */
	public void set_T_coeffiecents() {
		double[] t = new double[this.coefficent_matrix.length];
		for(int i = 0; i < this.coefficent_matrix.length; i++) {
			t[i] = this.coefficent_matrix[i] / this.coefficent_se[i];
			System.out.println(t[i]);
		}
		this.coefficent_t_scores = t;
	}
	public void set_df_coefficents() {
		
	}
	public void print_x_matrix() {
		for(int i = 0; i < this.matrix_x.length;i++) {
			for(int j = 0; j < this.matrix_x.length;j++) {
				System.out.print(matrix_x[i][j]+"  ");
			}
			System.out.println();
		}
	}
	public void print_inverse_x_matrix() {
		double[][] ix = this.inverse(this.matrix_x);
		for(int i = 0; i < ix.length;i++) {
			for(int j = 0; j < ix.length;j++) {
				System.out.print(ix[i][j]+"  ");
			}
			System.out.println();
		}
	}

	public void get_SE_coeffiecents() {
		double[][] m = inverse(this.matrix_x);
		double[] SE = new double[m.length];
		for(int i = 0; i < SE.length;i++) {
			SE[i] = Math.sqrt( (m[i][i] / (super.x.getLength() * SST) ) );
			System.out.println(SE[i]);
		}
	}
	public double predictY(Particle x_val) {
		double y = 0;
		for(int i = coefficent_matrix.length-1; i >= 0; i--) {
			y = y + (coefficent_matrix[i] * Math.pow(x_val.getDoubleValue(),i));
		}
		return y;
	}

	
}
