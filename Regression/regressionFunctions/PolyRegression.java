package regressionFunctions;


import dataframe.Column;
import forensics.Stats;
/**
 * step 1: calculate a weird matrix made by math
 * step 2: do some kind of fuckin witchcraft made by a mathematician
 * step 3: done
 * 
 * @author logan.collier
 *
 */
public class PolyRegression {
	private Column x;
	private Column y;
	private double[] poly_x;
	private double[] poly_xy;
	int degree; // degree of the polynomial
	/**
	 * 
	 * @param x
	 * @param y
	 * @param degree - max degree of polynomial
	 */
	public PolyRegression(Column x, Column y,int degree) {
		this.x = x;
		this.y = y;
		this.degree = degree;
		degree_sums();
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
	 * sets up a symetrical matrix of sum of x^i
	 * @param a
	 * @return
	 */
	private double[][] matrix_inversion(double[][] a) {
		int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i) 
            b[i][i] = 1;
        // Transform the matrix into an upper triangle
        gaussian(a, index);
        // Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i) {
            for (int j=i+1; j<n; ++j) {
                for (int k=0; k<n; ++k) {
                    b[index[j]][k]
                    	    -= a[index[j]][i]*b[index[i]][k];
                }
            }
        }
        // Perform backward substitutions
        for (int i=0; i<n; ++i) {
            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j) {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k) {
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
        
	}
	private double[][] degree_sums() {
		double[] poly_x = new double[this.degree*2];
		
		poly_x[0] = x.sum;
		
		double[] poly_xy = new double[this.degree+1];
		poly_xy[0] = y.sum;
		poly_xy[1] = Stats.sumMultiple_Columns(x, y);
		int cnt_xy = 2;
		int cnt_x = 1;
		for(int i = 2; i <= poly_x.length; i++) {
			double sum_x = 0;
			double sum_xy = 0;
			for(int j = 0; j < x.getLength(); j++) {
				sum_x = sum_x + Math.pow(x.getDoubleValue(j), i);
				if(cnt_xy < poly_xy.length)
					sum_xy = sum_xy + (Math.pow(x.getDoubleValue(j), i) * y.getDoubleValue(j));
			}
			
			poly_x[cnt_x] = sum_x;
			if(cnt_xy < poly_xy.length)
				poly_xy[cnt_xy] = sum_xy;
			cnt_x++;
			cnt_xy++;
		}
		System.out.println("X:");
		for(int h = 0; h < poly_x.length; h++) {
			System.out.println(poly_x[h]);
		}
		System.out.println("Y:");
		for(int h = 0; h < poly_xy.length; h++) {
			System.out.println(poly_xy[h]);
		}
		double[][] x_matrix = new double[this.degree+1][this.degree+1];
		x_matrix[0][0] = x.getLength();
		int cnt_xx = 0;
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
		for(int i = 0; i <= this.degree; i++) {
			for(int j = 0; j <= this.degree;j++) {
				System.out.print("["+x_matrix[i][j]+"]");
			}
			System.out.println();
		}
		return x_matrix;
	}
	
}
