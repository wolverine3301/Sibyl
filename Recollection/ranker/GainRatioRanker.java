package ranker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chi2.Chi2Independents;
import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Copy;
import info_gain.GainRatio;

public class GainRatioRanker extends Ranker{
	
	
	public GainRatioRanker(DataFrame df) {
		super(df);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void initiallize() {
		GainRatio gr = new GainRatio(super.df);
		int cnt =0;
		for(Column i : df.target_columns) {
			ranks.put(i.getName(), gr.gain(cnt));
			cnt++;
		}
		
	}
	@Override
	public DataFrame Rank(DataFrame df, int vars,String target) {

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
