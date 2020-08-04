package Encoders;

import java.util.ArrayList;

import dataframe.Column;
import dataframe.DataFrame;

public class OneHotEncoder {
	
	public static DataFrame encode(DataFrame df) {
		ArrayList<String> removeColumns = new ArrayList<String>();
		
		for(Column i : df.categorical_columns) {
			
			for(Object j : i.getUniqueValues()) {
				Column col = new Column(j.toString(), 'C');
				for(int cnt = 0; cnt < i.getLength();cnt++) {
					if(i.getParticle(cnt).getValue().equals(j)) {
						col.add(1);
					}else {
						col.add(0);
					}
				}
				col.readyForStats = true;
				col.setStatistics();
				df.addColumn(col);
			}
			removeColumns.add(i.getName());
		}
		for(String i : removeColumns) {
			df.removeColumn(i);
		}
		return df;
	}

}
