package dataframe;

import java.util.Set;

public class Util {
	public static DataFrame[] splitOnTarget(DataFrame df, int targetIndex) {
		Set<Object> classes = df.getColumn(targetIndex).uniqueValues;
		DataFrame[] targets = new DataFrame[classes.size()];
		
		int cnt = 0;
		for(Object i : classes) {
			String[] arg = {df.getColumn(targetIndex).getName() , "==", (String)i};
			targets[cnt] = DataFrame_Copy.acquire(df,arg);
			targets[cnt].setName((String)i);
			cnt++;
		}
		return targets;
		
	}
}
