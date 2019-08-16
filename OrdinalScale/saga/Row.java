package saga;
import java.util.ArrayList;

public class Row {
	public ArrayList<Particle> row; //array of data
	//public ArrayList<String> columnNames;
	public Row() {
		this.row = new ArrayList<Particle>();
		//this.columnNames = new ArrayList<String>();
	}
	
	/**
	 * Copy constructor, creates a new row from an existing row (Deep copy).
	 * @param theRow the row to "clone"
	 */
	public Row(Row theRow) {
	    row = new ArrayList<Particle>();
	    for (Particle p : theRow.row)
	        row.add(p.deepCopy());
	}
	
	/**
	 * Adds a particle object to list 
	 * @param p
	 */
	public void addToRow(Particle p) {
		row.add(p);
	}
	/**
	 * return a particle object from list
	 * @param index
	 * @return
	 */
	public Particle getParticle(int index) {
		return row.get(index);
	}
	/**
	 * return length of row
	 * @return
	 */
	public int getlength() {
		return row.size();
	}
	
	public ArrayList<Particle> getRow() {
	    return row;
	}
	/**
	 * print out row values
	 */
	public void printRow() {
		for(int i = 0;i < row.size();i++) {
			System.out.print(row.get(i).getValue()+ " ");
		}
	}
	

}
