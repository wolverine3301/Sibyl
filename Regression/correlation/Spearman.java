package correlation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

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
	/*
	public double getCorrelation(Column x, Column y) {
		double sum_d = 0;
		x.sort_column();
		y.sort_column();
		ArrayList<Particle> x_s = x.getSortedValues();
		ArrayList<Particle> y_s = y.getSortedValues();
		System.out.println(x.getName()+" v. "+y.getName());
		System.out.println(x_s);
		System.out.println(y_s);
		for(int i = 0; i < x.getLength(); i++) {
			double a = y.getParticle(x_s.get(i).getIndex()).getDoubleValue();
			for(int j = 0; j < y_s.size();j++) {
				if(y_s.get(j).getDoubleValue()==a) {
					//System.out.println(i+ " "+j +" "+ ((i+1)-(j+1)) );
					sum_d = sum_d + Math.pow( (i+1)-(j+1), 2);
				}else {
					continue;
				}
			}
		}
		//System.out.println(1- (6*sum_d)/(x.getLength() * (Math.pow(x.getLength(), 2)-1)) );
		//System.out.println(1 - ((6 * sum_d)/(x.getLength() * (Math.pow(x.getLength(), 2)-1)) ));
		return  (1 - ((6 * sum_d)/(x.getLength() * (Math.pow(x.getLength(), 2)-1)) ));
	}
	
	public double getCorrelation(Column x, Column y) {
		x.sort_column();
		y.sort_column();
		ArrayList<Particle> x_s = x.getSortedValues();
		ArrayList<Particle> y_s = y.getSortedValues();
		double[] rank_x = new double[x_s.size()];
		double[] rank_y = new double[y_s.size()];
		System.out.println(x.getName()+" v. "+y.getName());
		//rank x
		System.out.println("RANK X");
		for(int i = 0; i < x.getLength(); i++) {
			double rank = i;
			for(int j = 1; j <= x.getUniqueValueCounts().get(x_s.get(i).getValue());j++) {
				rank = rank + j;
			}
			rank = rank / x.getUniqueValueCounts().get(x_s.get(i).getValue());
			System.out.println(x_s.get(i).getValue() +"  "+ rank);
			rank_x[i] = rank;
		}
		//rank y
		System.out.println("RANK Y");
		for(int i = 0; i < y.getLength(); i++) {
			double rank = i;
			for(int j = 1; j <= y.getUniqueValueCounts().get(y_s.get(i).getValue());j++) {
				rank = rank + j;
			}
			rank = rank / y.getUniqueValueCounts().get(y_s.get(i).getValue());
			System.out.println(x_s.get(i).getValue() +"  "+ rank);
			rank_y[i] = rank;
		}
		double sum_d = 0;
		for(int i = 0; i < x.getLength(); i++) {
			double a = y.getParticle(x_s.get(i).getIndex()).getDoubleValue();
			for(int j = 0; j < y_s.size();j++) {
				if(y_s.get(j).getDoubleValue()==a) {
					//System.out.println(rank_x[i]+ "  "+rank_y[j] );
					sum_d = sum_d + Math.pow( (rank_x[i])-(rank_y[j]), 2);
				}else {
					continue;
				}
			}
		}
		//System.out.println(1- (6*sum_d)/(x.getLength() * (Math.pow(x.getLength(), 2)-1)) );
		//System.out.println(1 - ((6 * sum_d)/(x.getLength() * (Math.pow(x.getLength(), 2)-1)) ));
		return  (1 - ((6 * sum_d)/(x.getLength() * (Math.pow(x.getLength(), 2)-1)) ));
		
	}
	*/
	public double getCorrelation(Column x, Column y) {
		x.sort_column();
		y.sort_column();
		ArrayList<Particle> x_s = x.getSortedValues();
		ArrayList<Particle> y_s = y.getSortedValues();
		double[] rank_x = new double[x_s.size()];
		double[] rank_y = new double[y_s.size()];
		
		//RANK X
		HashMap<Double,Integer> ranks = new HashMap<Double,Integer>();
		HashMap<Double,Double> ranks_f = new HashMap<Double,Double>();
		for(int i = 0; i < x_s.size(); i++) {
			if(!ranks.containsKey(x_s.get(i).getDoubleValue()))
				ranks.put(x_s.get(i).getDoubleValue(),(i));
		}
		
		for(int i = 0; i < x_s.size(); i++) {
			if(!ranks_f.containsKey(x_s.get(i).getDoubleValue())) {
				double rank = ranks.get(x_s.get(i).getDoubleValue())+1;
				double sum = rank;
				if( x.getUniqueValueCounts().get(x_s.get(i).getValue()) > 1) {
					for(int j = 1; j < x.getUniqueValueCounts().get(x_s.get(i).getValue()); j++) {
						sum = sum+rank+1;
						rank++;
					}
				}
				sum = sum / x.getUniqueValueCounts().get(x_s.get(i).getValue());
				ranks_f.put(x_s.get(i).getDoubleValue(),sum);
			}
		}
		for(int i = 0; i < x.getLength();i++) {
			rank_x[i] = ranks_f.get(x.getDoubleValue(i));
		}
		ranks.clear();
		ranks_f.clear();
		//RANK Y
		for(int i = 0; i < y_s.size(); i++) {
			if(!ranks.containsKey(y_s.get(i).getDoubleValue()))
				ranks.put(y_s.get(i).getDoubleValue(),(i));
		}
		for(int i = 0; i < y_s.size(); i++) {
			if(!ranks_f.containsKey(y_s.get(i).getDoubleValue())) {
				double rank = ranks.get(y_s.get(i).getDoubleValue())+1;
				double sum = rank;
				if( y.getUniqueValueCounts().get(y_s.get(i).getValue()) > 1) {
					for(int j = 1; j < y.getUniqueValueCounts().get(y_s.get(i).getValue()); j++) {
						//rank = rank+rank+j;
						sum = sum+rank+1;
						rank++;
					}
				}
				sum = sum / y.getUniqueValueCounts().get(y_s.get(i).getValue());
				ranks_f.put(y_s.get(i).getDoubleValue(),sum);
			}
		}
		for(int i = 0; i < y.getLength();i++) {
			rank_y[i] = ranks_f.get(y.getDoubleValue(i));
		}
		System.out.println(x.getName()+" v. "+y.getName());
		System.out.println("X    Y    XR    YR");
		ranks.clear();
		ranks_f.clear();
		
		//CORRELATION
		double sx = 0;
		double sy = 0;

		for(int i = 0; i < rank_x.length;i++) {
			sx = sx + rank_x[i];
			sy = sy + rank_y[i];
		}
		double mean_x = sx/rank_x.length;
		double mean_y = sy/rank_y.length;
		double std_x = 0;
		double std_y = 0;
		double covar = 0;
		for(int i = 0; i< rank_x.length;i++) {
			std_x = Math.pow((rank_x[i]-mean_x),2);
			std_y = Math.pow((rank_y[i]-mean_y),2);
			covar = covar + ((rank_x[i] - mean_x) * (rank_y[i] - mean_y));
		}
		std_x = Math.sqrt(std_x/rank_x.length);
		std_y = Math.sqrt(std_y/rank_y.length);
		covar = covar / rank_x.length;
		System.out.println(std_x);
		System.out.println(std_y);
		System.out.println(covar);
		return covar / (std_x * std_y);
		
		
	}

}
