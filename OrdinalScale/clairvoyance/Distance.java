package clairvoyance;

import saga.DataFrame;

public abstract class Distance<T> {
	public DataFrame distance_matrix;
	
	public Distance() {
		distance_matrix = new DataFrame();
	}
	// is there an easy way to make this generic and work easily with saga?
	// such as produce the same results if plugged in either a dataframe or a 2D int[]
	public double distance(T r1, T r2) {
		return 0;
	}

}
