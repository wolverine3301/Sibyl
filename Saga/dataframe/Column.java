package dataframe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import particles.Particle;
 
public abstract class Column {
    /** The code for type of data stored within the column.
     * Accepted values are:
     * 'T' - a target column
     * 'M' - a meta column (one that isnt to be used in learning such as ID numbers)
     * 'C' - a category column (such as red or blue)
     * 'G' - a custom object column
     * 'O' - a ordinal column (Ordered categorys such as A,B, and C grades)
     * 'N' - a numerical column
     *  */
    protected char type; 
    /** The name of the column */
    protected String name; 
    /** The length of the column (the amount of particles stored in the column) */
    protected int columnLength;
    protected int unique_val_count;
    protected Object mode;
    /** The array list of particles within the column */
    protected ArrayList<Particle> column;
    /** The feature stats of the column. */
    protected HashMap<Object, Double> featureStats;
    protected Set<Object> uniqueValues;
    protected HashMap<Object, Integer> uniqueValCnt;
    /**
     * Creates a column with a given name.
     * @param name the name of the column.
     */
    public Column(String name) {
        this.name = name;
        column = new ArrayList<Particle>(); 
        columnLength = 0;
        uniqueValues = new HashSet<Object>();
        featureStats = new HashMap<Object, Double>();
        uniqueValCnt = new HashMap<Object, Integer>();
    }
    
    /**
     * Creates a column with a given name and data type.
     * @param theName
     * @param theType
     */
    public Column(String theName, char theType) {
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

    
    public Particle getParticle(int index) {
        return column.get(index);
    }
    
    /**
     * Manual override of auto determined type.
     * @param type the new type of the column.
     */
    public void setType(char type) {
        this.type = type;
    }
    
    /**
     * Returns the type of the column.
     * @return the type of the column.
     */
    public char getType() {
        return type;
    }
    
    /**
     * Returns the name of the column.
     * @return the name of the column.
     */
    public String getName() {
        return name;
    }
    
    public HashMap<Object, Double> getFeatureStats() {
        return featureStats;
    }    
    /**
     * Resolves the type of the column.
     */
    public void resolveType() {
        for (int i = 0; i < columnLength - 2; i++) {
            char s1 = column.get(i).type;
            char s2 = column.get(i + 1).type;
            char s3 = column.get(i + 2).type;
            if (s1 == s2 && s1 == s3) {
                setType(Column.particleTypeToColumnType(s1));
                break;
            }
        }
    }
    
    /**
     * adds a particle object to column
     * @param p
     */
    public void add(Particle p) {
        column.add(p);
        columnLength++;
    }
    //TODO
    //Need to update so column type is set correctly with certain particle types
    /**
     * add a raw type to column which will be converted to a particle
     * adds a value to the end of the array list
     * @param value
     */
    public void add(Object value) {
        Particle p = Particle.resolveType(value);
        //auto declaration of column type
        if(column.isEmpty()) {
        	if(p.type == 'd' || p.type == 'i')
        		this.type = 'N';
        }
        add(p);
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
     * @return set of unique values
     */
    public void setUniqueValues(Column column){ 
        Set<Object> unique = new HashSet<Object>();
        for(int i = 0; i < column.getLength(); i++) {
            unique.add(column.getParticle(i).getValue());
        }   
        this.uniqueValues = unique;
    }
    /**
     * returns a hashmap: keys are each unique value in array list and they point to the number of occurances
     * @return
     */
    public void setUniqueValCnt() {
        Set<Object> unique = this.uniqueValues;
        HashMap<Object, Integer> vals = new HashMap<>();
        //initialize map
        for(Object o : unique) {
            vals.put(o, 0);
        }
        //counting
        for(int i = 0; i < column.size(); i++) {  
            Particle p = column.get(i);
            vals.replace(p.getValue(), (Integer)vals.get(p.getValue()) +1);
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
        this.uniqueValCnt = temp;
    }//end uniqueValCnt
    /**
     * return number of unique values
     * @return
     */
    public void setNumOfUniques() {
        this.unique_val_count = uniqueValues.size();
    }
	/**
	 * sets the features of proportion each value has in the column
	 */
	public void setFeatureStats() {
		HashMap<Object, Integer> a = uniqueValCnt;
		for(Entry<Object, Integer> i : a.entrySet()) {
			featureStats.put(i.getKey(), (double)i.getValue()/column.size());
		}
	}
    /**
     * Returns the character corresponding to column types from a particle type.
     * @param pType the type of the particle.
     * @return the character of the corresponding column type. 
     */
    public static char particleTypeToColumnType(char pType) {
        if (pType == 'i' || pType == 'd')
            return 'N';
        else if (pType == 'o')
            return 'G';
        else
            return 'M';
    }
    /**
     * Returns the most occouring value in a column.
     * @param theColumn the column to preform calculations on.
     * @return the most occouring value in a column.
     */
    public void setMode() {
        Object m = null;
        for (Map.Entry<Object,Integer> entry : uniqueValCnt.entrySet()) {
            m = entry.getKey();
            break;
            }
        this.mode = m;   
    }

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
	 * Creates a string representing the column.
	 * @return a string representing the column.
	 */
	@Override
	public String toString() {
	    String str = "Column Name: " + name + "\nColumn Type: " + type + "\n";
	    for (Particle p : column)
	        str += p.toString() + "\n";
	    return str;
	}
    /**
     * getLength
     * returns the length of the column array list
     * @return
     */
    public int getLength() {
        return columnLength;    
    }

}
