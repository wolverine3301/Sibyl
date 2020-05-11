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
 * PROS:
 * - Less sensitive to outliers
 * - covers parametric and non-parametric data
 * - does not assume normal distribution
 * NOTE:
 * - can only be used between 10-30 sets
 * 
 */
public class Spearman extends Correlation{
	
	private Column x ,y;
	private double[] rank_x, rank_y;
	private String title;
	private double ranked_sum_x, ranked_sum_y;
	private double ranked_mean_x, ranked_mean_y;
	private double ranked_std_x, ranked_std_y;
	private double ranked_covariance;
	private double correlation;
	public final String method = "Spearman";
	
	public Spearman() {
		
	}
	public double getCorrelation(Column x, Column y) {
		this.x = x;
		this.y = y;
		x.sort_column();
		y.sort_column();
		ArrayList<Particle> x_s = x.getSortedValues();
		ArrayList<Particle> y_s = y.getSortedValues();
		this.rank_x = new double[x_s.size()];
		this.rank_y = new double[y_s.size()];

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
		this.title = x.getName()+" v. "+y.getName();
		ranks.clear();
		ranks_f.clear();
		
		//CORRELATION
		this.ranked_sum_x = 0;
		this.ranked_sum_y = 0;

		for(int i = 0; i < rank_x.length;i++) {
			this.ranked_sum_x = ranked_sum_x + rank_x[i];
			this.ranked_sum_y = ranked_sum_y + rank_y[i];
		}
		this.ranked_mean_x = ranked_sum_x/rank_x.length;
		this.ranked_mean_y = ranked_sum_y/rank_y.length;
		this.ranked_std_x = 0;
		this.ranked_std_y = 0;
		this.ranked_covariance = 0;
		for(int i = 0; i< rank_x.length;i++) {
			ranked_std_x = ranked_std_x + Math.pow((rank_x[i]-ranked_mean_x),2);
			ranked_std_y = ranked_std_y + Math.pow((rank_y[i]-ranked_mean_y),2);
			ranked_covariance = ranked_covariance + ((rank_x[i] - ranked_mean_x) * (rank_y[i] - ranked_mean_y));
		}
		ranked_std_x = Math.sqrt(ranked_std_x / (rank_x.length-1));
		ranked_std_y = Math.sqrt(ranked_std_y / (rank_y.length-1));
		
		ranked_covariance = ranked_covariance / (rank_x.length-1);
		this.correlation = ranked_covariance / (ranked_std_x * ranked_std_y);
		return correlation;
	}
	public void printFull() {
		System.out.println(this.title);
		System.out.println("X    Y   RANKED X   RANKED Y");
		for(int i = 0; i< this.rank_x.length;i++) {
			System.out.println(x.getDoubleValue(i) + "  "+ y.getDoubleValue(i)+"  "+ this.rank_x[i]+"  "+ this.rank_y[i]);
		}
		System.out.println("RANKED SUM OF X: "+this.ranked_sum_x);
		System.out.println("RANKED SUM OF Y: "+this.ranked_sum_y);
		System.out.println("RANKED MEAN OF X: "+this.ranked_mean_x);
		System.out.println("RANKED MEAN OF Y: "+this.ranked_mean_y);
		System.out.println("RANKED STD OF X: "+this.ranked_std_x);
		System.out.println("RANKED STD OF Y: "+this.ranked_std_y);
		System.out.println(ranked_covariance+" / "+ (rank_x.length-1) + " = " +(ranked_covariance/ (rank_x.length-1 ) ));
		System.out.println("RANKED COVARIANCE OF X and Y: "+this.ranked_covariance);
		System.out.println("SPEARMAN CORRELATION COEFFICENT: "+ this.correlation);
		
	}
	public String toString() {
		return title;
		
	}

}
