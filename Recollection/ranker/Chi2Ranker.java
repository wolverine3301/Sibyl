package ranker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chi2.Chi2Independents;
import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Copy;

public class Chi2Ranker {
	
	public static DataFrame chi2Rank(DataFrame df,int vars) {
		Chi2Independents chi2 = new Chi2Independents(df);
		HashMap<String, ArrayList<Column>> rank = chi2.ranked();
		List<String> cols = new ArrayList<String>();
		
		int breaker = 0;
		
		for(String i : rank.keySet()) {
			for(Column j : rank.get(i)) {
				if(breaker >= vars) break;
				
				cols.add(j.getName());
				breaker++;
			}
			if(breaker >= vars) break;
		}
		for(Column i : df.target_columns) {
			cols.add(i.getName());
		}
		return DataFrame_Copy.shallowCopy_columnNames(df, cols);
	}
}
