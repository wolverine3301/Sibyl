package regressionFunctions;

public class PredictionInterval {
	private Regression function;
	private float[] upper;
	private float[] lower;
	
	public PredictionInterval(Regression function) {
		this.function = function;
	}
	
}
