package Controllers;

import java.util.ArrayList;
import java.util.HashMap;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Read;

public class Data_Label_Controller {
	private DataFrame df;
	private HashMap<String,Character> cols = new HashMap<String,Character>();
	private String fileName;
	
	private ArrayList<Character> types = new ArrayList<Character>();
	public Data_Label_Controller() {
		df = new DataFrame();
	}
	/**
	 * opens a data file and creates a dataframe
	 * @param filePath
	 */
	public void openFile(String filePath) {
		this.fileName = filePath;
		makeDataFrame();
		setCol();
		df.printDataFrame();
	}
	private void makeDataFrame() {
		df = DataFrame_Read.loadcsv(fileName);
	}
	private void setCol() {
		cols.clear();
		for(Column i : df.columns) {
			cols.put(i.getName(), i.getType());
		}
	}
	public void changeCol(String key,char newType) {
		cols.replace(key, newType);
		df.setColumnType(key, newType);
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
	public char getColummnType(String name) {
		return cols.get(name);
	}
	public int getNumColumns() {
		return df.getNumColumns();
	}
	public String getFileName() {
		return fileName;
	}
	public int getTotalNumericColumns() {
		return df.numNumeric;
	}
	public int getTotalCategoricalColumns() {
		return df.numCategorical;
	}
	public int getTotalTargetColumns() {
		return df.numTargets;
	}
	public int getTotalMetaColumns() {
		return df.numMeta;
	}
	public int getTotalRows() {
		return df.getNumRows();
	}
	public DataFrame getDF() {
		return this.df;
	}
	

}
