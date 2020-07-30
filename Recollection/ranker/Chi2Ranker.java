package ranker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chi2.Chi2Independents;
import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Copy;

public class Chi2Ranker extends Ranker{
	
	public Chi2Ranker(DataFrame df) {
		super(df);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initiallize() {
		Chi2Independents chi2 = new Chi2Independents(df);
		ranks = chi2.ranked();
	}

	@Override
	public DataFrame Rank(DataFrame df, int vars, String target) {
		List<String> cols = new ArrayList<String>();
		
		int breaker = 0;
		
		for(Column j : ranks.get(target)) {
			if(breaker >= vars) break;
			
			cols.add(j.getName());
			breaker++;
		}
		for(Column i : df.target_columns) {
			cols.add(i.getName());
		}
		return DataFrame_Copy.shallowCopy_columnNames(df, cols);
	}
}
