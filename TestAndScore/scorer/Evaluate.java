package scorer;

import java.util.ArrayList;

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
	private Metric metric;
	/**specified class of a target to evaluate on, or keep as "Overall".
	 * if one class is more important to focus on then change to the name of that
	 **/
	private ArrayList<String> priority_classes;
	/**
	 * store the model the best fits the priorities
	 */
	private Model model;
	
	public Evaluate(ArrayList<Column> targets) {
		this.metric = Metric.OVERALL;
		this.priority_classes = new ArrayList<String>();
		for(int i = 0; i < targets.size(); i++) {
			priority_classes.add("Overall");
		}
	}
	
	public void setTargetClassPriority(int targetIndex, String klass) {
		this.priority_classes.set(targetIndex, klass);
	}
	public void setMetric(Metric m) {
		this.metric = m;
	}
	
}
