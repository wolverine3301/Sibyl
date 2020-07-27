package scorer;

import java.util.ArrayList;
import java.util.HashMap;

import dataframe.Column;
import machinations.Model;

/**
 * Evaluation of models
 * @author Logan Collier
 *
 */
public class Evaluate {
	/** the metric which has priority, such as accuracy and precision. defalut is overall.
	 *  Options are:
	 *  Overall
	 *  precision
	 *  accuracy
	 *  recall
	 *  F1
	 *  mcc
	 **/
	private Metric singular_metric;
	
	/**
	 * if a specific target is a higher priority than others
	 */
	private ArrayList<Column> priority_targets;
	
	/**
	 * specified class of a target to evaluate on, or keep as "Overall".
	 * if one class is more important to focus on then change to the name of that
	 * key -> target name => priority class
	 **/
	private HashMap<String,Object> priority_classes;
	/**
	 * store the model the best fits the priorities
	 */
	private double best_metric = 0; //the current best achieved metric
	private Model model;
	private CrossValidation CV;
	
	public Evaluate(ArrayList<Column> targets) {
		this.singular_metric = Metric.OVERALL;
		this.priority_targets = new ArrayList<Column>();
		this.priority_classes = new HashMap<String,Object>();
		//defualt with every class in every target having equal priority
		for(Column i : targets) {
			priority_classes.put(i.getName(),"Overall");
		}
	}
	/**
	 * Used to set a specific target priority level. If a one target is more important to meet
	 * a higher score set to a lower index. default will assume all targets are equal priority
	 * @param targetIndex
	 * @param klass
	 */
	public void setTargetPriority(int targetPriority, Column target) {
		if(priority_targets.isEmpty()) {
			this.priority_targets.add(targetPriority, target);
		}else {
			this.priority_targets.set(targetPriority, target);
		}
	}
	/**
	 * sets a particular class in a target with higher priority to maximize over others
	 * 
	 * @param targetName
	 * @param klass
	 */
	public void setTargetClassPriority(String targetName, Object klass) {
		priority_classes.put(targetName,klass);
	}
	/**
	 * set the most vital metric to be used when evaluating a model, for example if precision is more
	 * important than accuracy.
	 * @param m
	 */
	public void setMetric(Metric m) {
		this.singular_metric = m;
	}
	public void evaluation(CrossValidation cv) {
		//if all targets are equally important
		if(priority_targets.isEmpty()) {
			double modelMetric = 0;
			if(singular_metric == Metric.RECALL) {
				for(String i : cv.overall_recall.keySet()) {
					modelMetric = modelMetric + cv.overall_recall.get(i);
				}
				modelMetric = modelMetric / cv.overall_recall.keySet().size();
			}else if(singular_metric == Metric.PRECISION) {
				for(String i : cv.overall_precision.keySet()) {
					modelMetric = modelMetric + cv.overall_precision.get(i);
				}
				modelMetric = modelMetric / cv.overall_precision.keySet().size();
			}else if(singular_metric == Metric.F1) {
				for(String i : cv.overall_f1.keySet()) {
					modelMetric = modelMetric + cv.overall_f1.get(i);
				}
				modelMetric = modelMetric / cv.overall_f1.keySet().size();
			}else if(singular_metric == Metric.MCC) {
				for(String i : cv.overall_mcc.keySet()) {
					modelMetric = modelMetric + cv.overall_mcc.get(i);
				}
				modelMetric = modelMetric / cv.overall_mcc.keySet().size();
			}else {
				for(String i : cv.overall_recall.keySet()) {
					modelMetric = modelMetric + cv.overall_recall.get(i)+ cv.overall_precision.get(i)+cv.overall_f1.get(i);
				}
			}
			updateBestModel(cv,modelMetric);
			
		}

	}
	private void updateBestModel(CrossValidation cv, double metric) {
		if(this.best_metric < metric) {
			this.best_metric = metric;
			this.CV = cv;
		}
	}
	public void getBest() {
		System.out.println(best_metric);
	}
	
}
