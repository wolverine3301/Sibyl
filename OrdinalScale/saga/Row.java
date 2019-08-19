package saga;
import java.util.ArrayList;

public class Row {
    
    /** An array list of particles, represents a row. */
	public ArrayList<Particle> row;
	
	/** The length of the row (how many particles are stored in this row */
	public int rowLength;
	//public ArrayList<String> columnNames;
	public Row() {
		this.row = new ArrayList<Particle>();
		rowLength = 0;
		//this.columnNames = new ArrayList<String>();
	}
	
	/**
	 * Copy constructor, creates a new row from an existing row (Deep copy).
	 * @param theRow the row to "clone"
	 */
	public Row(Row theRow) {
	    row = new ArrayList<Particle>();
	    rowLength = theRow.rowLength;
	    for (Particle p : theRow.row)
	        row.add(p.deepCopy());
	}
	
	/**
	 * Adds a particle object to list 
	 * @param p
	 */
	public void addToRow(Particle p) {
		row.add(p);
		rowLength++;
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
		return rowLength;
	}
	
	public ArrayList<Particle> getRow() {
	    return row;
	}
	
	public String toString() {
	    String s = "";
	    for(int i = 0;i < row.size();i++) {
            s += row.get(i).toString()+ " ";
        }
	    return s;
	}
	
	/**
	 * print out row values
	 */
	public void printRow() {
		for(int i = 0;i < row.size();i++) {
			System.out.print(row.get(i).getValue() + " ");
		}
	}
	

}
