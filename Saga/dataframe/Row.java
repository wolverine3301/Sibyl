package dataframe;
import java.util.ArrayList;

import particles.Particle;

public class Row {
    
    /** An array list of particles, represents a row. */
	public ArrayList<Particle> row;
	
	/** The length of the row (how many particles are stored in this row */
	public int rowLength;
	
	/**
	 * Creates a new empty row. 
	 */
	public Row() {
		this.row = new ArrayList<Particle>();
		rowLength = 0;
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
	 * Adds a particle to the row. 
	 * @param p the particle to add to the row. 
	 */
	public void add(Particle p) {
	    if (!row.isEmpty()) {
	        row.get(row.size() - 1).linkTo(p);
	    } 
	    row.add(p);
		rowLength++;
	}
	
	/**
	 * Removes the particle at a given index in the row.
	 * @param index the index of the particle to remove. 
	 */
	public void removeParticle(int index) {
	    row.remove(index);
	    rowLength--;
	}
	
	/**
	 * Returns the particle at a given index in the row. 
	 * @param index the index of the particle to return. 
	 * @return the particle at a given index in the row. 
	 */
	public Particle getParticle(int index) {
		return row.get(index);
	}
	
	/**
	 * Returns the length of the row. 
	 * @return the length of the row. 
	 */
	public int getLength() {
		return rowLength;
	}
	
	/**
	 * Changes the value (in the form of a particle) at a given index of the row. 
	 * @param index the index of the value to change. 
	 * @param p the value to change the index to. 
	 */
	public void changeValue(int index, Particle p) {
	    row.set(index, p);
	}
	
	/**
	 * Returns if the row is equal to another row. 
	 * @param r the row to check for equality. 
	 * @return true if the two rows contain identical values, false otherwise.
	 */
	public boolean equals(Row r) {
	    if (rowLength == r.rowLength) {
	        for (int i = 0; i < rowLength; i++) {
	            if (!row.get(i).equals(r.row.get(i)))
	                return false;
	        }
	        return true;
	    }
	    else 
	        return false;
	}
	
	
	/**
	 * Returns the row in the form of an arraylist of particles. 
	 * @return the row in the form of an arraylist of particles. 
	 */
	public ArrayList<Particle> getRow() {
	    return row;
	}

	/**
	 * Returns a string representation of this row. 
	 * @return a string representation of this row. 
	 */
	@Override
	public String toString() {
	    String s = "";
	    for(int i = 0; i < row.size(); i++) {
            s += row.get(i).toString()+ " ";
        }
	    return s;
	}
	
	/**
	 * Prints out the values contained in the row. 
	 */
	public void printRow() {
		for(int i = 0;i < row.size();i++) {
			System.out.print(row.get(i).getValue() + " ");
		}
	}
	

}
