package dataframe;

import java.util.Set;


public class Util {
	public static DataFrame[] splitOnTarget(DataFrame df, Column target) {
		Set<Object> classes = target.uniqueValues;
		DataFrame[] targets = new DataFrame[classes.size()];
		
		int cnt = 0;
		for(Object i : classes) {
			String[] arg = {target.getName() , "==", (String) i};
			targets[cnt] = DataFrame_Copy.acquire(df,arg);
			targets[cnt].setName(i.toString());
			cnt++;
		}
		
		System.out.println("UTIL :"+targets[0].target_columns.toString());
		System.out.println("UTIL :"+targets[1].numTargets);
		System.out.println("UTIL :"+targets[0].numCategorical);
		System.out.println("UTIL :"+targets[1].numCategorical);
		return targets;
		
	}
}
