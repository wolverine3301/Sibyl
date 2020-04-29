package dataframe;

import java.util.Set;


public class Util {
	public static DataFrame[] splitOnTarget(DataFrame df, Column target) {
		Set<Object> classes = target.uniqueValues;
		DataFrame[] targets = new DataFrame[classes.size()];
		System.out.println("UTIL: "+target.getName()+" "+target.getUniqueValues().size());
		int cnt = 0;
		for(Object i : classes) {
			String[] arg = {target.getName() , "==", (String) i};
			targets[cnt] = DataFrame_Copy.acquire(df,arg);
			targets[cnt].setName(i.toString());
			cnt++;
		}
		return targets;
		
	}
}
