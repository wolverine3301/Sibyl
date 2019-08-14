package forensics;
import java.util.HashMap;
import java.util.Map;

import saga.*;

public class Stats {
	

	public static double variance(Column c) {
		double mean = mean(c);
		double var = 0;
		for(int i = 0;i < c.getLength();i++) {
			var += Math.pow((c.getNumValue(i)- mean), 2);
		}
		return var/c.getLength();
	}

	public static double entropy(Column c) {

		System.out.println(ent);
		ent = ent * -1;
		return ent;
		
	}

}
