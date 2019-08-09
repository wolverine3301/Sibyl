package clairvoyance;

import saga.*;

public abstract class Distance{

	// is there an easy way to make this generic and work easily with saga?
	// such as produce the same results if plugged in either a dataframe or a 2D int[]
	public abstract double distance(Row r1, Row r2);
	public abstract DataFrame distance_matrix();

}
