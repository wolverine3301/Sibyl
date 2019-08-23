package machinations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import saga.*;

public class NaiveBayes extends Model{
	
	private List<Column> targets;
	public NaiveBayes(DataFrame df) {
		super(df);
		targets = new ArrayList<Column>();
		
	}
	public HashMap<Object, HashMap<String, HashMap<Object, Double>>> naivebayes(){
		
	}
	/**
	 * set targets
	 */
	private void setTargets() {
		for(Column c : super.trainingDataFrame.columns) {
			if(c.type.contains("target")) {
				targets.add(c);
			}
		}
	}
	@Override
	public HashMap<Object, Double> probability(Row row) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object predict(Row row) {
		// TODO Auto-generated method stub
		return null;
	}

}
