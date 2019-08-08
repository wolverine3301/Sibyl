package saga;
import java.util.ArrayList;

public class Row<T> {
	public ArrayList<Particle> row; //array of data
	public ArrayList<T> values;
	//public ArrayList<String> columnNames;
	public Row() {
		this.row = new ArrayList<Particle>();
		this.values = new ArrayList<T>();
		
		//this.columnNames = new ArrayList<String>();
	}
	/**
	 * Adds a particle object to list 
	 * @param p
	 */
	public void addToRow(Particle p) {
		row.add(p);
		values.add((T) p.getValue());
	}
	/**
	 * add a raw type to a row which will first be converted to particle
	 * @param value
	 */
	public void add(T value) {
		Particle<T> p = new Particle<T>(value);
		addToRow(p);
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