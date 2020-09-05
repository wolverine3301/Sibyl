package dataframe;

import java.util.Set;

/**
 * 
 * @author logan collier
 * @author cade reynoldson
 * 
 * DataFrame Utility functions
 *
 */
public class Util {
	/**
	 * splits a dataframe on values in the specified column target
	 * 
	 * @param df - the dataframe 
	 * @param target - the column to split the dataframe on
	 * @return array of dataframes 
	 */
	public static DataFrame[] splitOnTarget(DataFrame df, Column target) {
		Set<Object> classes = target.uniqueValues;
		DataFrame[] targets = new DataFrame[classes.size()];
		
		int cnt = 0;
		for(Object i : classes) {
			if(i instanceof Integer) {
				String[] arg = {target.getName() , "==", String.valueOf(i)};
				targets[cnt] = DataFrame_Copy.acquire(df,arg);
				targets[cnt].setName(i.toString());
				cnt++;
			}else {
				String[] arg = {target.getName() , "==", (String)i};
				targets[cnt] = DataFrame_Copy.acquire(df,arg);
				targets[cnt].setName(i.toString());
				cnt++;
			}
		}
		return targets;
	}
}
