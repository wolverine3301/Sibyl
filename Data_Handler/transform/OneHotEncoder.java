package transform;

import java.util.ArrayList;

import dataframe.Column;
import dataframe.DataFrame;

public class OneHotEncoder {
	
	public OneHotEncoder() {

	}
	public static DataFrame encode(DataFrame df) {
		ArrayList<Column> newColumns = new ArrayList<Column>();
		ArrayList<String> removeColumns = new ArrayList<String>();
		for(Column i : df.categorical_columns) {
			
			for(Object j : i.getUniqueValues()) {
				Column col = new Column(j.toString(), 'O');
				for(int cnt = 0; cnt < i.getLength();cnt++) {
					if(i.getParticle(cnt).getValue().equals(j)) {
						col.add(1);
					}else {
						col.add(0);
					}
				}
				newColumns.add(col);
			}
			removeColumns.add(i.getName());
			//df.removeColumn(i.getName());
		}
		System.out.println(df.getNumColumns());
		return df;
	}

}
