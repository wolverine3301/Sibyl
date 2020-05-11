package regression_correlationExamples;

import dataframe.Column;
import regressionFunctions.ConfidenceIntervals;
import regressionFunctions.PolyRegression;

public class Polynomial_regression_example {

	public static void main(String[] args) {
		double[] a1 = {0,2,2,5,5,9,9,9,9,10};
		double[] a2 = {-2,0,2,1,3,1,0,0,1,-1};
		Column x = new Column(a1);
		Column y = new Column(a2);
		PolyRegression p = new PolyRegression(x,y,2);
		p.printEquation();
		//System.out.println("MSE: "+p.MSE);
		//System.out.println("R2: "+p.R2);
		//System.out.println("RMSD: "+p.RMSD);
		//p.printModelReport();
		//System.out.println(p.coefficents.length);
		ConfidenceIntervals c = new ConfidenceIntervals(p);
		System.out.println(p.predictY(x.getParticle(2)));
		System.out.println(c.upper_intervalY(x.getParticle(2)));
		//p.set_se_ofCoefficents();
		//p.set_T_coeffiecents();
		p.print_coefficentMatrix();
		c.printUpper();
		c.printLower();
		//p.print_inverse_x_matrix();
		//ystem.out.println(p.SE);
		//p.get_T_coeffiecents();
		//p.get_SE_coeffiecents();
		
	}

}
