package saga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
    
    /** The length of the column (the amount of particles stored in the column) */
    public int columnLength;
    /**
     * Creates a column with a given name.
     * @param name the name of the column.
     */
    public HashMap<Object, Double> feature_stats = new HashMap<Object, Double>();;
    public Column(String name) {
        this.name = name;
        column = new ArrayList<Particle>(); 
        columnLength = 0;
        
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
        columnLength = 0;
    }
    
    /**
     * Copy constructor.
     * @param theColumn
     */
    public Column(Column theColumn) {
        type = theColumn.type;
        name = theColumn.name;
        columnLength = theColumn.columnLength;
        column = new ArrayList<Particle>();
        for (Particle particle : theColumn.column) 
            column.add(particle.deepCopy());
    }
    
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
     * Resolves the type of the column.
     */
    public void resolveType() {
        for (int i = 0; i < columnLength - 2; i++) {
            String s1 = column.get(i).type;
            String s2 = column.get(i + 1).type;
            String s3 = column.get(i + 2).type;
            if (s1.equals(s2) && s1.equals(s3)) {
                setType(s1);
                break;
            }
        }
    }
    
    /**
     * adds a particle object to column
     * @param p
     */
    public void addToColumn(Particle p) {
        column.add(p);
        columnLength++;
    }
    
    /**
     * add a raw type to column which will be converted to a particle
     * adds a value to the end of the array list
     * @param value
     */
    public void add(Object value) {
        Particle p = Particle.resolveType(value);
        //auto declaration of column type
        if(column.isEmpty()) {
            this.type = p.type;
        }
        addToColumn(p);
        columnLength++;
    }
    
    /**
     * Changes the value of a particle in the data frame.
     * @param index the index to be changed.
     * @param p the particle to be replaced.
     */
    public void changeValue(int index, Particle p) {
        column.set(index, p);
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
        columnLength--;
    }
    /**
     * removeIndex
     * removes object at the specified index
     * @param index
     */
    public void removeIndex(int index) {
        column.remove(index);
        columnLength--;
    }
    /**
     * getLength
     * returns the length of the column array list
     * @return
     */
    public int getLength() {
        return columnLength; 
        
    }

    /**
     * make new column from array
     * @param arr
     */
    public void makeColumn_fromArray(Object arr[]) {
        for(int i = 0; i < arr.length; i++) {
            Particle tmp = Particle.resolveType(arr[i]);
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
            Particle tmp = Particle.resolveType(arr[i]);
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
        	vals.replace(i.getValue(), (Integer)vals.get(i.getValue()) +1);
        }//end count
     // Create a list from elements of HashMap 
        List<Map.Entry<Object, Integer>> list = new LinkedList<Map.Entry<Object, Integer> >(vals.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<Object, Integer> >() { 
            public int compare(Map.Entry<Object, Integer> o1,  
                               Map.Entry<Object, Integer> o2) 
            { 
                return (o1.getValue()).compareTo(o2.getValue()); 
            } 
        });
        Collections.reverse(list);
        // put data from sorted list to hashmap  
        HashMap<Object, Integer> temp = new LinkedHashMap<Object, Integer>(); 
        for (Map.Entry<Object, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        }
        return temp;
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
    	double sum = 0;
    	if(type.contains("Double")) {
    		// get sum
    		for(int i = 0;i < column.size();i++) {
    		    if (column.get(i) instanceof DoubleParticle)
    		        sum += (Double) column.get(i).getValue();
    		}//end sum	
    	}
    	else if(type.contains("Integer")) {
    		for(int i = 0;i < column.size();i++) {
    		    if (column.get(i) instanceof IntegerParticle)
    		        sum += (Integer)column.get(i).getValue();
    		}//end sum
    	}
    	return sum;
    }
    /**
     * return most occuring value
     * @return
     */
    public Object mode() {
    	Object m = null;
    	for (Map.Entry<Object,Integer> entry : uniqueValCnt().entrySet()) {
            m = entry.getKey();
            break;
    		}
    	return m; 	
    }
    /**
     * @return mean of column
     */
    public double mean() {
    	return sum() / (double) column.size();	
    }
    
    /**
     * Returns the mean of a given set of indexes.
     * @param indexes the set of indexes.
     * @return the sum of a given set of indexes.
     */
    public double meanOfIndexes(Set<Integer> indexes) {
        double sum = sumOfIndexes(indexes);
        return sum / indexes.size();
    }
    
    /**
     * Returns the sum of a given set of indexes.
     * @param indexes the set of indexes.
     * @return the sum of a given set of indexes.
     */
    public double sumOfIndexes(Set<Integer> indexes) {
        double sum = 0; 
        if(type.contains("Double")) {
            for (Integer i : indexes) {
                if (column.get(i) instanceof DoubleParticle)
                    sum += (Double)column.get(i).getValue();
            }
        }
        else if(type.contains("Integer")) {
            for (Integer i : indexes) {
                if (column.get(i) instanceof IntegerParticle)
                    sum += (Integer)column.get(i).getValue();
            }//end sum
        }
        return sum;
    }
    
    //NEEDS WORK
    public double median() {
    	
    	List<Object> sorted = new ArrayList<Object>();
    	for(Particle i : column) {
    		sorted.add(i.getValue());
    	}
    	Collections.sort(sorted);
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
			
			ent = ent + (-1)* ((double)value / column.size()) * ( Math.log10(((double)value / column.size())) / Math.log10(2));
		}
		return ent;
	}
	/**
	 * sets the features of proportion each value has in the column
	 */
	public void setFeatureStats() {
		HashMap<Object, Integer> a = uniqueValCnt();
		for(Entry<Object, Integer> i : a.entrySet()) {
			feature_stats.put(i.getKey(), (double)i.getValue()/column.size());
		}
	}

}
