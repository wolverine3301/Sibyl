package saga;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
/**
 * column object for a data frame
 * @author logan.collier
 *
 * @param <T>
 */
public class Column<T> {
	int test;
	public String type; //column type
	public String name; //column name
	public ArrayList<T> column; //array of data

	public Column(String name,String type) {
		this.name = name;
		this.type = type;
		column = new ArrayList<T>();
		
	}
	/**
	 * add
	 * adds a value to the end of the array list
	 * @param value
	 */
	public void add(T value) {
		this.column.add(value);
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
	 * removes first ocurance of a specific value that may be in the array list 
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
	 * getValue
	 * returns the object at the specified index
	 * @param index
	 * @return
	 */
	public T getValue(int index) {
		return this.column.get(index);
	}
	/**
	 *addArray
	 *adds an array to column array list 
	 * @param arr
	 */
	public void addArray(T arr[]) {
		for(int i = 0; i < arr.length; i++) {
			this.column.add(arr[i]);
		}
	}
	/**
	 * uniqueValues
	 * returns a set of all unique values in array list
	 * @return
	 */
	//return all unique items from column
	public Set<T> uniqueValues(){
		Set<T> unique = new HashSet<T>(this.column);
		return unique;
	}//end uniqueValues
	
   /**
     * Creates a tree set of unique values in the array list.
     * @return a tree set of unique values.
     */
    public Set<T> uniqueValuesTree() {
        return new TreeSet<T>(this.column);
    }
    
	/**
	 * uniqueValCnt
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
		for(T i : this.column) {
			vals.replace(i, vals.get(i), vals.get(i)+1);
		}//end for
		return vals;

	}//end uniqueValCnt
	
	
	
	
	

}
