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
 * @author Cade Reynoldson
 */
public class Column {

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
    
    /**
     * 
     * @param value
     * @return
     */
    private Particle getNewParticle(Object value) {
        if (value instanceof Integer)
            return new IntegerParticle((Integer) value);
        else if (value instanceof Double)
            return new DoubleParticle((Double) value);
        else if (value instanceof String)
            return new StringParticle((String) value);
        else 
            return new ObjectParticle(value);
    }
    
    /**
     * add a raw type to column which will be converted to a particle
     * adds a value to the end of the array list
     * @param value
     */
    public void add(Object value) {
        Particle p = getNewParticle(value);
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
    
    // NEED TO UPDATE FOR VALUES
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
            Particle tmp = getNewParticle(arr[i]);
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
            Particle tmp = getNewParticle(arr[i]);
            this.column.add(tmp);
        }
    }
    
   /**
     * return number of unique values
     * @return
     */
    public int numOfUniques() {
        return uniqueValues().size();
    }  
    /**
     * @return set of unique values
     */
    public Set<Object> uniqueValues(){
    	Set<Object> unique = new HashSet<Object>();
    	for(Particle i : column) {
    		unique.add(i.getValue());
    	}	
        return unique;
    }
    
    /**
     * returns a hashmap: keys are each unique value in array list and they point to the number of occurances
     * @return
     */
    public HashMap<Object, Integer> uniqueValCnt() {
        Set<Object> unique = uniqueValues();
        Object[] uni = unique.toArray(); //get array of uniqe values
        
        HashMap<Object, Integer> vals = new HashMap<>();
        //initialize map
        for(int i = 0;i < uni.length;i++) {
            vals.put(uni[i], 0);
        }//end for
        //counting
        for(Particle i : column) {
            vals.replace(i.getValue(), vals.get(i), vals.get(i)+1);
        }//end count
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
    /**
     * If numeric column
     * @return sum of values
     */
    public double sum() {
    	Number sum = 0;
    	if(type.contains("Double")) {
    		// get sum
    		for(int i = 0;i < column.size();i++) {
    			sum.equals(sum.doubleValue() + (Double)column.get(i).getValue());
    		}//end sum	
    	}
    	else if(type.contains("Integer")) {
    		for(int i = 0;i < column.size();i++) {
    			sum.equals(sum.intValue() + (Integer)column.get(i).getValue());
    		}//end sum
    	}
    	return sum.doubleValue();
    }
    /**
     * @return mean of column
     */
    public double mean() {
    	return sum() / column.size();	
    }
    /**
     * @return variance of column
     */
	public  double variance() {
		double var = 0.0;
		for(int i = 0;i < column.size();i++) {
			var += Math.pow(((Double)column.get(i).getValue()- mean()), 2);
		}
		return var /column.size();
	}
	/**
	 * return standard deviation
	 * @return
	 */
	public double standardDeviation() {
		return Math.sqrt(variance());		
	}
	/**
	 * @return entropy
	 */
	public double entropy() {
		HashMap<Object,Integer> values = uniqueValCnt();
		double ent = 0;
		for (Integer value : values.values()) {
			ent = ent + (value / column.size()) * (Math.log10((value / column.size())) / Math.log10(2));
		}
		return ent;
	}

}
