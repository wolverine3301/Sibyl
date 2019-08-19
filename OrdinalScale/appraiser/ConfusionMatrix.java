package appraiser;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import saga.*;
public class ConfusionMatrix {
	public ArrayList<Integer> truePositive;
	public ArrayList<Integer> falsePositive;
	public ArrayList<Integer> trueNegative;
	public ArrayList<Integer> falseNegative;
	private DataFrame df;
	public ConfusionMatrix(DataFrame df) {
		this.df = df;
		truePositive = new ArrayList<Integer>();
		falsePositive = new ArrayList<Integer>();
		trueNegative = new ArrayList<Integer>();
		falseNegative = new ArrayList<Integer>();
		
		
		
	}
	public void test_score() {
		List<Column> targets = new ArrayList<Column>();
		for(int i = 0; i < df.numColumns;i++) {
			//get target columns
			if(df.columnTypes.get(i) == "target") {
				targets.add(df.getColumn_byIndex(i));
			}
		}
		Set<Object> classes = targets.get(0).uniqueValues();
		System.out.println(classes);
		
	}

}
