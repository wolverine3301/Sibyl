package Controllers;

import java.util.List;

import dataframe.DataFrame;
import machinations.Model;
import scorer.CrossValidation;
import scorer.Evaluate;
import scorer.Metric;

public class Evaluate_Controller {
	
	private DataFrame df;
	private int trials;
	private List<Model> models;
	private Evaluate evaluation;
	
	public Evaluate_Controller(DataFrame df) {
		this.df = df;
		this.evaluation = new Evaluate(df.target_columns);
	}
	public void setMetric(Metric m) {
		evaluation.setMetric(m);
	}
	public void setPriorityTargetClass(String targetName,Object klass) {
		evaluation.setTargetClassPriority(targetName, klass);
	}
	public void evaluate(CrossValidation cv) {
		evaluation.evaluation(cv);
	}

}
