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

	private double current_precision=0;
	private double current_recall=0;
	private double current_f1=0;
	private double current_mcc=0;
	public Evaluate(ArrayList<Column> targets) {
		this.singular_metric = Metric.OVERALL;
		this.priority_targets = new ArrayList<Column>();
		this.priority_classes = new HashMap<String,Object>();
		//defualt with every class in every target having equal priority
		//for(Column i : targets) {
		//	priority_classes.put(i.getName(),i.mode);
		//}
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
		double modelMetric = 0;

		//if all targets and classes are equally important
		if(priority_targets.isEmpty() && priority_classes.isEmpty()) {
			for(String i : cv.overall_recall.keySet()) {
				this.current_recall = current_recall + cv.overall_recall.get(i);
				this.current_precision = current_precision + cv.overall_precision.get(i);
				this.current_f1 = current_f1 + cv.overall_f1.get(i);
				this.current_mcc = current_mcc + cv.overall_mcc.get(i);
			}
			this.current_recall = current_recall / cv.overall_recall.keySet().size();
			this.current_precision = current_precision / cv.overall_precision.keySet().size();
			this.current_f1 = current_f1 / cv.overall_f1.keySet().size();
			this.current_mcc = current_mcc / cv.overall_mcc.keySet().size();
			
			if(singular_metric == Metric.RECALL) {
				modelMetric = current_recall;
			}else if(singular_metric == Metric.PRECISION) {
				modelMetric = current_precision;
				
			}else if(singular_metric == Metric.F1) {
				modelMetric = current_f1;
				
			}else if(singular_metric == Metric.MCC) {
				modelMetric = current_mcc;
				
			}else {
				for(String i : cv.overall_recall.keySet()) {
					modelMetric = modelMetric + cv.overall_recall.get(i)+ cv.overall_precision.get(i)+cv.overall_f1.get(i);
				}
			}
			
		}else if(!priority_classes.isEmpty()) {
			for(String i : priority_classes.keySet()) {

				if(singular_metric == Metric.RECALL) {
					modelMetric = modelMetric + cv.total_recall.get(i).get(priority_classes.get(i));
				}else if(singular_metric == Metric.PRECISION) {
					modelMetric = modelMetric + cv.total_precision.get(i).get(priority_classes.get(i));
				}else if(singular_metric == Metric.F1) {
					modelMetric = modelMetric + cv.total_f1.get(i).get(priority_classes.get(i));
				}else if(singular_metric == Metric.MCC) {
					modelMetric = modelMetric + cv.total_mcc.get(i).get(priority_classes.get(i));
				}
			}
		}
		updateBestModel(cv,modelMetric);
	}
	private void updateBestModel(CrossValidation cv, double metric) {
		if(this.best_metric < metric) {
			this.best_metric = metric;
			this.CV = cv;
			System.out.println("NEW BEST");
		}
	}
	public void getBest() {
		System.out.println(best_metric);
	}
	public double getCurrent_precision() {
		return current_precision;
	}
	public void setCurrent_precision(double current_precision) {
		this.current_precision = current_precision;
	}
	public double getCurrent_recall() {
		return current_recall;
	}
	public void setCurrent_recall(double current_recall) {
		this.current_recall = current_recall;
	}
	public double getCurrent_f1() {
		return current_f1;
	}
	public void setCurrent_f1(double current_f1) {
		this.current_f1 = current_f1;
	}
	public double getCurrent_mcc() {
		return current_mcc;
	}
	public void setCurrent_mcc(double current_mcc) {
		this.current_mcc = current_mcc;
	}
}
