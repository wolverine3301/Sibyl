package forensics;

import java.util.HashMap;
import java.util.List;

import saga.*;

public class InformationGain {
	private DataFrame df;
	private List<Column> targets;
	
	public HashMap<String,Double> info_gains;
	
	public InformationGain(DataFrame df) {
		this.df = df;
		info_gains = new HashMap<String,Double>();
		

	}
	/**
	 * set target variables
	 */
	private void setTargets() {
		targets = df.getColumnByTypes("target");
	}
	public double gain() {
		for(Column i : df.columns) {
			if(i.type == "target") {
				continue;
			}
			
			
		}
		
	}

}
