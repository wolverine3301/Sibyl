package correlation;

import java.util.ArrayList;

import dataframe.Column;
import particles.Particle;

/**
 * @author logan.collier
 * is a measure of how well the relationship between two variables can be described by a monotonic function.
 * PRO: Less sensitive to outliers
 */
public class Spearman extends Correlation{

	public Spearman(Column x, Column y) {
		
	}
	public double getCorrelation(Column x, Column y) {
		int[] xi = new int[x.getLength()];
		int[] yi = new int[x.getLength()];
		double[] d_squared = new double[x.getLength()];
		double sum_d = 0;
		ArrayList<Particle> x_s = x.getSortedValues();
		ArrayList<Particle> y_s = y.getSortedValues();
		for(int i = 0; i < x.getLength(); i++) {
			xi[i] = i+1;
			Object a = y.getParticle(x_s.get(i).getIndex()).getValue();
			for(int j = 0; j < y_s.size();j++) {
				if(y_s.get(j).equals(a)) {
					sum_d = sum_d + Math.pow( (i+1)-(j+1), 2);
				}else {
					continue;
				}
			}
		}
		return  1 - ((6 * sum_d)/(x.getLength() * (Math.pow(x.getLength(), 2)-1)) );
	}

}
