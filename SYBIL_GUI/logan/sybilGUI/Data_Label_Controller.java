package logan.sybilGUI;

import java.util.ArrayList;
import java.util.HashMap;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Read;

public class Data_Label_Controller {
	private DataFrame df;
	private HashMap<String,Character> cols = new HashMap<String,Character>();
	
	private ArrayList<Character> types = new ArrayList<Character>();
	public Data_Label_Controller() {
		df = DataFrame_Read.loadcsv("testfiles/iris.txt");
		df.setColumnType(1, 'T');
		setCol();
	}
	private void setCol() {
		for(Column i : df.columns) {
			cols.put(i.getName(), i.getType());
		}
	}
	public void changeCol(String key,char newType) {
		cols.replace(key, newType);
		System.out.println("CHANGED: "+cols.get(key));
	}
	public ArrayList<String> getColumnNames() {
		return df.getColumnNames();
	}
	public ArrayList<Character> getColumnTypes() {
		for(Column i : df.columns) {
			types.add(i.getType());
		}
		return types;
	}
	public int getNumColumns() {
		return df.getNumColumns();
	}

}
