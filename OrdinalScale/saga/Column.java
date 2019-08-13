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

    /** The type of data stored within the column. */
    public String type; 
    
    /** The name of the column */
    public String name; 
    
    /** The array list of particles within the column */
    public ArrayList<Particle> column; 

    /**
     * Creates a column with a given name.
     * @param name the name of the column.
     */
    public Column(String name) {
        this.name = name;
        column = new ArrayList<Particle>();
        
    }
    
    /**
     * Creates a column with a given name and data type.
     * @param theName
     * @param theType
     */
    public Column(String theName, String theType) {
        name = theName;
        type = theType;
        column = new ArrayList<Particle>();
    }
    
//  /**
//   * Copy constructor.
//   * @param theColumn
//   */
//  @SuppressWarnings("unchecked")
//    public Column(Column theColumn) {
//      type = theColumn.type;
//      name = theColumn.name;
//        column = new ArrayList<Particle>();
//        for (Particle particle : theColumn.column) 
//            column.add(new Particle(particle));
//  }
    
    public Particle getParticle_atIndex(int index) {
        return column.get(index);
    }
    
    /**
     * manual override of auto determined type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * adds a particle object to column
     * @param p
     */
    public void addToColumn(Particle p) {
        column.add(p);
    }
    
    private 
    
    /**
     * add a raw type to column which will be converted to a particle
     * adds a value to the end of the array list
     * @param <T>
     * @param value
     */
    public void add(Object value) {
        Particle p = new Particle(value);
        //auto declaration of column type
        if(column.isEmpty()) {
            this.type = p.type;
        }
        addToColumn(p);
    }
    /**
     * hasValue
     * returns true if specified value is in array list else returns false
     * @param value
     * @return
     */
    public boolean hasValue(Object value) {
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
    public void removeValue(Object value) {
        column.remove(value);
    }
    /**
     * removeIndex
     * removes object at the specified index
     * @param index
     */
    public void removeIndex(int index) {
        column.remove(index);
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
     * make new column from array
     * @param arr
     */
    public void makeColumn_fromArray(Object arr[]) {
        for(int i = 0; i < arr.length; i++) {
            Particle tmp = new Particle(arr[i]);
            this.column.add(tmp);
            this.type = tmp.type;
        }
    }
    
    /**
     * adds array to end of column list
     * @param arr
     */
    public void concatArray(Object arr[]) {
        for(int i = 0; i < arr.length; i++) {
            Particle tmp = new Particle(arr[i]);
            this.column.add(tmp);
        }
    }
    
   /**
     * return number of unique values
     * @return
     */
    public int numOfUniques() {
        return uniqueValues().toArray().length;
    }
    
    
    //UPDATE THESE TWO BELOW
    
    /**
     * set of unique values
     * @return
     */
    public Set<Particle> uniqueValues(){
        Set<Particle> unique = new HashSet<Particle>(column);
        return unique;
    }
    
    /**
     * returns a hashmap: keys are each unique value in array list and they point to the number of occurances
     * @return
     */
    public HashMap<Particle, Integer> uniqueValCnt() {
        Set<Particle> unique = uniqueValues();
        @SuppressWarnings("unchecked")
        Particle[] uni = (Particle[]) unique.toArray(); //get array of uniqe values
        
        HashMap<Particle, Integer> vals = new HashMap<>();
        //initialize map
        for(int i = 0;i < uni.length;i++) {
            vals.put(uni[i], 0);
        }//end for
        //counting
        for(Particle i : column) {
            vals.replace(i, vals.get(i), vals.get(i)+1);
        }//end for
        return vals;
    }//end uniqueValCnt
    
    /**
     * Print column
     */
    public void printCol() {
        System.out.println(name +" "+type);
        for(int i = 0; i < column.size();i++) {
            System.out.println(column.get(i).value);
        }
    }
	
	
	

}
