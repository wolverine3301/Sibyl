package saga;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
/**
 * column object for a data frame
 * @author logan.collier
 * @param <T>
 *
 * @param <T>
 */
public class Column<T> {

	public String type; //column type
	public String name; //column name
	public ArrayList<Particle<T>> column; //array of data
	public ArrayList<T> values;

	/**
	 * Creates a column with a given name.
	 * @param name the name of the column.
	 */
	public Column(String name) {
		this.name = name;
		column = new ArrayList<Particle<T>>();
		values = new ArrayList<T>();
		
	}
	
	public Column(String theName, String theType) {
	    name = theName;
	    type = theType;
        column = new ArrayList<Particle<T>>();
        values = new ArrayList<T>();
	}
	
	/**
	 * Copy constructor.
	 * @param theColumn
	 */
	@SuppressWarnings("unchecked")
    public Column(Column<T> theColumn) {
	    type = theColumn.type;
	    name = theColumn.name;
        column = new ArrayList<Particle<T>>();
        values = (ArrayList<T>) theColumn.values.clone();
        Iterator<Particle<T>> columnIterator = theColumn.column.iterator();
        while (columnIterator.hasNext()) //Copy the particles into the column arraylist.
            column.add(new Particle<T>(columnIterator.next()));
	}
	/**
	 * manual override of auto determined type
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * add
	 * adds a value to the end of the array list
	 * @param <T>
	 * @param value
	 */
	public void add(T value) {
		Particle<T> p = new Particle<T>(value);
		//auto declaration of column type
		if(column.isEmpty()) {
			this.type = p.type;
		}
		this.column.add(p);
		this.values.add((T) p.getValue());
	}
	/**
	 * hasValue
	 * returns true if specified value is in array list else returns false
	 * @param value
	 * @return
	 */
	public boolean hasValue(T value) {
		if(this.column.contains(value)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * removeValue
	 * removes first occurrence of a specific value that may be in the array list 
	 * @param value
	 */
	public void removeValue(T value) {
		this.column.remove(value);
	}
	/**
	 * removeIndex
	 * removes object at the specified index
	 * @param index
	 */
	public void removeIndex(int index) {
		this.column.remove(index);
	}
	/**
	 * getLength
	 * returns the length of the column array list
	 * @return
	 */
	public int getLength() {
		return this.column.size();
	}
	/**
	 * gets value of an object by index
	 * @param index
	 * @return
	 */
	public T getValue(int index) {
		return (T) values.get(index);
	}
	/**
	 * make new column from array
	 * @param arr
	 */
	public void makeColumn_fromArray(T arr[]) {
		for(int i = 0; i < arr.length; i++) {
			Particle tmp = new Particle(arr[i]);
			this.column.add(tmp);
			this.type = tmp.type;
			this.values.add((T) tmp.getValue());
		}
	}
	/**
	 * adds array to end of column list
	 * @param arr
	 */
	public void concatArray(T arr[]) {
		for(int i = 0; i < arr.length; i++) {
			Particle tmp = new Particle(arr[i]);
			this.column.add(tmp);
			this.values.add((T) tmp.getValue());
		}
	}
	/**
	 * set of unique values
	 * @return
	 */
	//return all unique items from column
	public Set<T> uniqueValues(){
		Set<T> unique = new HashSet<T>(this.values);
		return unique;
	}//end uniqueValues
	
   /**
    * Creates a tree set of unique values in the array list.
    * @return
    */
    public Set<T> uniqueValuesTree() {
        return new TreeSet<T>(this.values);
    }
    
	/**
	 * returns a hashmap: keys are each unique value in array list and they point to the number of occurances
	 * @return
	 */
	public HashMap<T,Integer> uniqueValCnt() {
		Set<T> unique = uniqueValues();
		@SuppressWarnings("unchecked")
		T[] uni = (T[]) unique.toArray(); //get array of uniqe values
		
		HashMap<T,Integer> vals = new HashMap<>();
		//initialize map
		for(int i = 0;i < uni.length;i++) {
			vals.put(uni[i], 0);
		}//end for
		//counting
		for(T i : this.values) {
			vals.replace(i, vals.get(i), vals.get(i)+1);
		}//end for
		return vals;

	}//end uniqueValCnt
	/**
	 * print column
	 */
	public void printCol() {
		System.out.println(name +" "+type);
		for(int i = 0; i < column.size();i++) {
			System.out.println(column.get(i).value);
		}
	}
	
	
	

}
