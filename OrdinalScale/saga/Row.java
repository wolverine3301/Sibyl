package saga;
import java.util.ArrayList;

public class Row<T> {
	public ArrayList<OldParticle<T>> row; //array of data
	public ArrayList<T> values;
	//public ArrayList<String> columnNames;
	public Row() {
		this.row = new ArrayList<OldParticle<T>>();
		this.values = new ArrayList<T>();
		//this.columnNames = new ArrayList<String>();
	}
	/**
	 * Adds a particle object to list 
	 * @param p
	 */
	public void addToRow(OldParticle p) {
		row.add(p);
		values.add((T) p.getValue());
	}
	/**
	 * add a raw type to a row which will first be converted to particle
	 * @param value
	 */
	public void add(T value) {
		OldParticle<T> p = new OldParticle<T>(value);
		addToRow(p);
	}
	/**
	 * return a particle object from list
	 * @param index
	 * @return
	 */
	public OldParticle getParticle(int index) {
		return row.get(index);
	}
	/**
	 * return length of row
	 * @return
	 */
	public int getlength() {
		return row.size();
	}
	
	public ArrayList<OldParticle<T>> getRow() {
	    return row;
	}
	/**
	 * print out row values
	 */
	public void printRow() {
		for(int i = 0;i < row.size();i++) {
			System.out.print(values.get(i)+ " ");
		}
	}
	

}
