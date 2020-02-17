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

	public Spearman() {
		
	}
	public double getCorrelation(Column x, Column y) {
		double sum_d = 0;
		x.sort_column();
		y.sort_column();
		ArrayList<Particle> x_s = x.getSortedValues();
		ArrayList<Particle> y_s = y.getSortedValues();
		System.out.println(y_s);
		for(int i = 0; i < x.getLength(); i++) {
			double a = y.getParticle(x_s.get(i).getIndex()).getDoubleValue();
			System.out.println(a);
			for(int j = 0; j < y_s.size();j++) {
				if(y_s.get(j).getDoubleValue()==a) {
					//System.out.println(i+"  "+j);
					sum_d = sum_d + Math.pow( (i+1)-(j+1), 2);
				}else {
					continue;
				}
			}
		}
		//System.out.println(1 - ((6 * sum_d)/(x.getLength() * (Math.pow(x.getLength(), 2)-1)) ));
		return  1 - ((6 * sum_d)/(x.getLength() * Math.pow(x.getLength()-1, 2) ) );
	}

}
