package discreteize;

import java.util.ArrayList;
import java.util.List;

public class Bin {
	
	private double s1;
	private double s2;
	private List<Double> bin;
	private String binString;
	/**
	 * a single bin for grouping numerical variables
	 * mathmatical equivelent to (s1 < x <= s2)
	 * @param s1 - a value is in this bin if it is greater than this number , AND
	 * @param s2 - a value is less than or equal to this number
	 * 
	 */
	public Bin(double s1, double s2) {
		this.s1 = s1;
		this.s2 = s2;
		bin = new ArrayList<Double>(2);
		bin.add(s1);
		bin.add(s2);
		this.binString = "("+s1+","+s2+"]";
		
	}
	public boolean inBinInt(int n) {
		if(n > s1 && n < s2) {
			return true;
		}else {
			return false;
		}
	}
	public boolean inBinDouble(double n) {
		if(n > s1 && n <= s2) {
			return true;
		}else {
			return false;
		}
	}
	public void printBin() {
		System.out.print("("+s1+","+s2+"]");
	}
	public String toString() {
		return this.binString;
	}

}
