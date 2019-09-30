package regressionScore;

/**
 * https://www.investopedia.com/terms/r/r-squared.asp
 * R-Squared -  is the proportion of the variance in the dependent variable that is predictable from the independent variable(s).
 * R-squared values range from 0 to 1 and are commonly stated as percentages from 0% to 100%
 * R-Squared only works as intended in a simple linear regression model with one explanatory variable.
 * With a multiple regression made up of several independent variables, the R-Squared must be adjusted.
 * 
 * Limitations:
 * R-squared will give you an estimate of the relationship between movements of a dependent variable based on an independent variable's movements.
 * It doesn't tell you whether your chosen model is good or bad
 * @author logan.collier
 *
 */
public class Rvalue {
	public double RSS;
	public Rvalue(Regression regression_function) {
		
	}
	private void setRSS() {
		this.RSS = 0;
		for(int i =0; i < x.getLength(); i++) {
			RSS = RSS + Math.pow(( (double)y.getParticle(i).getValue() - predictY(x.getParticle(i)) ), 2);
		}
	}
}
