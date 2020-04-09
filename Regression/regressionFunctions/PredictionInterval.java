package regressionFunctions;

public class PredictionInterval {
	private Regression function;
	private float[] upper;
	private float[] lower;
	
	/**
	 * Prideictive interval for regression functions
	 * @param function
	 */
	public PredictionInterval(Regression function) {
		this.function = function;
	}
	
}
