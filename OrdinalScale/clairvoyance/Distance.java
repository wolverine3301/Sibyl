package clairvoyance;

import saga.DataFrame;

public abstract class Distance<T> {

	// is there an easy way to make this generic and work easily with saga?
	// such as produce the same results if plugged in either a dataframe or a 2D int[]
	public abstract double distance(T r1, T r2);
	public abstract DataFrame<T> distance_matrix();

}
