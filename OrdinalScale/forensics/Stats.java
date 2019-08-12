package forensics;
import java.util.HashMap;
import java.util.Map;

import saga.*;

public class Stats {
	public static double mean(Column c) {
		if(c.type.contains("Integer") || c.type.contains("Double")) {
			double sum = 0;
			// get sum
			for(int i = 0;i < c.getLength();i++) {

				sum = sum + ((Number) c.getValue(i)).doubleValue();
			}//end sum
			return sum / c.getLength();	
		}else {
			return 0;
		}
		
	}
	public static double variance(Column c) {
		double mean = mean(c);
		double var = 0;
		for(int i = 0;i < c.getLength();i++) {
			var = Math.pow((((Number) c.getValue(i)).doubleValue() - mean), 2);
		}
		return var/c.getLength();
	}
	public static double standardDeviation(Column c) {
		return Math.sqrt(variance(c));		
	}
	public static double entropy(Column c) {
		HashMap values = c.uniqueValCnt();
		for (Object value : values.values()) {
			System.out.println(value);
		}
		return 0;
		
	}

}
