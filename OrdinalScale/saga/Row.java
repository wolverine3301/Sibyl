package saga;
import java.util.ArrayList;

public class Row<T> {
	public ArrayList<Particle> row; //array of data
	//public ArrayList<String> columnNames;
	public Row() {
		this.row = new ArrayList<Particle>();
		//this.columnNames = new ArrayList<String>();
	}
	public void addtoRow(Particle p) {
		row.add(p);
	}
	

}
