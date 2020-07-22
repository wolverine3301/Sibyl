package dataframe;

import java.util.Set;


public class Util {
	public static DataFrame[] splitOnTarget(DataFrame df, Column target) {
		Set<Object> classes = target.uniqueValues;
		DataFrame[] targets = new DataFrame[classes.size()];
		
		int cnt = 0;
		for(Object i : classes) {
			if(i instanceof Integer) {
				String[] arg = {target.getName() , "==", String.valueOf(i)};
				targets[cnt] = DataFrame_Copy.acquire(df,arg);
				//targets[cnt].printDataCounts(true);
				targets[cnt].setName(i.toString());
				cnt++;
			}else {
				String[] arg = {target.getName() , "==", (String)i};
				targets[cnt] = DataFrame_Copy.acquire(df,arg);
				//targets[cnt].printDataCounts(true);
				targets[cnt].setName(i.toString());
				cnt++;
			}
		}
//		System.out.println("UTIL 1: "+targets[0].target_columns.size());
//		System.out.println(targets[0].target_columns.get(0).getName()+"  "+targets[0].target_columns.get(1).getName());
//		System.out.println("UTIL 2: "+targets[1].target_columns.size());
//        System.out.println(targets[1].target_columns.get(0).getName()+"  "+targets[1].target_columns.get(1).getName());
		return targets;
		
	}
}
