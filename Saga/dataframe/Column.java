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
import java.util.TreeMap;
import java.util.Map.Entry;

import particles.DoubleParticle;
import particles.IntegerParticle;
import particles.NANParticle;
import particles.Particle;
 
public class Column {
    
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

    /** The array list of particles within the column */
    protected ArrayList<Particle> column;
    /** sorted array of particles within column (ordinal/numeric only)**/
    protected ArrayList<Particle> sorted_column;
    /******************************************
     *         COLUMN STATISTIC FIELDS        *
     ******************************************/
    
    public boolean readyForStats;
    
    /** The feature stats of the column. (Percentages of values) */
    protected HashMap<Object, Double> featureStats;
    
    /** A sorted set of the unique values contained within the column, with lowest index being most occouring, highest index being least occouring */
    protected Set<Object> uniqueValues;
    
    /** The total number of unique values in the column. */
    protected int totalUniqueValues;
    
    /** A hashmap with keys being the value, and its mapping being the total occourances in the column. */
    protected HashMap<Object, Integer> uniqueValueCounts;
    
    /** The most occouring value in the column. */
    public Object mode;
    
    /** The mean value of the column (NUMERIC ONLY)*/
    public double mean;
    
    /** The sum of the values in the column (NUMERIC ONLY) */
	public double sum;
	
	/** The median value in the column (NUMERIC ONLY) */
	public double median;
	
	/** The variance of the column (NUMERIC ONLY) */
	public double variance;
	
	/** The standard deviation of the column (NUMERIC ONLY) */
	public double std;
	
	/** The maximum value in the column (NUMERIC ONLY) */
	public double max;
	
	/** The Minimum value in the column (NUMERIC ONLY) */
	public double min;
	
	/** The Range of values in the column (NUMERIC ONLY) */
	public double range;
	
	/** The entropy of the column */
	public double entropy;
	
    /******************************************
     *      END COLUMN STATISTIC FIELDS       *
     ******************************************/
	public Column(double[] arr) {
		type = 'N';
        column = new ArrayList<Particle>(); 
        columnLength = 0;
        uniqueValues = new HashSet<Object>();
        featureStats = new HashMap<Object, Double>();
        uniqueValueCounts = new HashMap<Object, Integer>();
        for(double i : arr) {
        	Particle p = Particle.resolveType(i);
        	column.add(p);
        }
        this.setStatistics();
	}
	public Column(int[] arr) {
		type = 'N';
        column = new ArrayList<Particle>(); 
        columnLength = 0;
        uniqueValues = new HashSet<Object>();
        featureStats = new HashMap<Object, Double>();
        uniqueValueCounts = new HashMap<Object, Integer>();
        for(int i : arr) {
        	Particle p = Particle.resolveType(i);
        	column.add(p);
        }
        this.setStatistics();
	}
	
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
        uniqueValueCounts = new HashMap<Object, Integer>();
        readyForStats = false;
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
        uniqueValues = new HashSet<Object>();
        featureStats = new HashMap<Object, Double>();
        uniqueValueCounts = new HashMap<Object, Integer>();
        readyForStats = false;
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
        if (theColumn.isReadyForStats()) 
            copyStats(theColumn);
        else
            readyForStats = false;
        
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
     * Copies the stats of one column to this column (Mainly used for copy constructors).
     * @param theColumn the columns stats to copy.
     */ 
    public void copyStats(Column theColumn) {
        if (type == 'N') {
            sum = theColumn.sum; //SUM
            mode = theColumn.mode;//MODE
            variance = theColumn.variance;//VARIANCE  
            std = theColumn.std;//STANDARD DEVIATION
            median = theColumn.median;//MEDIAN
        }
        uniqueValueCounts = theColumn.uniqueValueCounts;
        totalUniqueValues = theColumn.totalUniqueValues;
        uniqueValues = theColumn.uniqueValues;
        featureStats = theColumn.featureStats;
        entropy = theColumn.entropy;
        mode = theColumn.mode;
        readyForStats = true;
    }
    
    /**
     * Returns the double value at a given index. Why we need this? I have no clue
     * @param index the index of the double to return
     * @return the double value at the given index.
     */
    public double getDoubleValue(int index) {
        double num = 0;
        if(column.get(index).type == 'i') {
            num = (int)column.get(index).getValue();
            return (double) num;
        } else {
            return (double) column.get(index).getValue();
        }
    }
    
    /**
     * returns the length of the column array list
     * @return
     */
    public int getLength() {
        return columnLength;    
    }
    
    /**
     * Returns the feature stats of the column.
     * @return the feature stats of the column.
     */
    public HashMap<Object, Double> getFeatureStats() {
        return featureStats;
    }
    
    /**
     * Returns the name of the column.
     * @return the name of the column.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the particle at a given index.
     * @param index particle at this index.
     * @return a particle at the given index.
     */
    public Particle getParticle(int index) {
        return column.get(index);
    }  
    
    /**
     * Returns the type of the column.
     * @return the type of the column.
     */
    public char getType() {
        return type;
    }
    
    public int getTotalUniqueValues() {
        return totalUniqueValues;
    }
    
    /**
     * Returns a set of the unique values of the column.
     * @return a set of the unique values of the column.
     */
    public Set<Object> getUniqueValues(){
        return uniqueValues;
    }
    
    /**
     * Returns the unique value counts in the form of a hashmap, with values mapped to their count.
     * @return the unique value counts of the column.
     */
    public HashMap<Object, Integer> getUniqueValueCounts() {
        return uniqueValueCounts;
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
     * Returns if the stats fields have been initialized.
     * @return if the stats fields have been initialized.
     */
    public boolean isReadyForStats() {
        return readyForStats;
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
     * Print column
     */
    public void printCol() {
        System.out.println(name +" "+type);
        for(int i = 0; i < column.size();i++) {
            System.out.println(column.get(i).value);
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
    
    public void resolveTypeNew(char pType) {
        if (pType == 'i' || pType == 'd')
            this.type = 'N';
        else if (pType == 'o')
            this.type = 'G';
        else if (pType == 's')
            this.type = 'C';
        else
            this.type = 'M';
    }
    
    /**
     * Resolves the type of the column.
     */
    public void resolveType() {
        for (int i = 0; i < columnLength - 1; i++) {
            char s1 = column.get(i).type;
            char s2 = column.get(i + 1).type;
            char s3 = column.get(i + 2).type;
            if (s1 == s2 && s1 == s3) {
                setType(Column.particleTypeToColumnType(s1));
                break;
            }
        }
    }
    public void sort_column() {
    	if(this.type == 'N' || this.type == 'O') {
    		this.sorted_column = new ArrayList<Particle>();
            for (Particle particle : this.column) 
            	this.sorted_column.add(particle.deepCopy());
    	}
    	//System.out.println(this.sorted_column);
    	Collections.sort(this.sorted_column);
    	//System.out.println(this.sorted_column);
    }
    /**
     * Prepares the column's statistic based fields. 
     */
    public void setStatistics() {
        if (type == 'N') {
            setSumAndMean();        //SUM AND MEAN
            setVariance();          //VARIANCE
            setStandardDeviation(); //STANDARD DEVIATION
            setMedian();            //MEDIAN
        }
        setUniqueValueCount();      //UNIQUE VAL COUNT
        setUniqueValues();          //SET UNIQUE VALUES SET
        setTotalUniqueValues();     //TOTAL UNIQUE VALUES      
        setEntropy();               //ENTROPY
        setMode();                  //MODE
        setFeatureStats();          //FEATURE STATS
        readyForStats = true;
    }
    
    /**
     * Manual override of auto determined type.
     * @param type the new type of the column.
     */
    public void setType(char type) {
        this.type = type;
    } 
    
    /**
     * Sets the unique values of the column
     * WARNING: UNIQUE VALUE COUNT MUST BE SET PRIOR TO THIS METHOD
     * BEING CALLED - JUST USE prepareForStatistics() LIKE A GOOD PROGRAMMER
     */
    private void setUniqueValues(){ 
        uniqueValues = uniqueValueCounts.keySet();
    }
    
    /**
     * returns a hashmap: keys are each unique value in array list and they point to the number of occurances
     * @return
     */
    public void setUniqueValueCount() {
        HashMap<Object, Integer> vals = new HashMap<Object, Integer>();
        //counting
        for(int i = 0; i < column.size(); i++) {
            Object value = column.get(i).getValue();
            if (!vals.containsKey(value))
                vals.put(value, 1);
            else {
                Integer temp = vals.get(value);
                vals.replace(value, temp + 1);
            }
                
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
        this.uniqueValueCounts = temp;
    }
    
    /**
     * return number of unique values
     * @return
     */
    private void setTotalUniqueValues() {
        this.totalUniqueValues = uniqueValues.size();
    }
    
	/**
	 * sets the features of proportion each value has in the column
	 */
	private void setFeatureStats() {
		HashMap<Object, Integer> a = uniqueValueCounts;
		
		for(Entry<Object, Integer> i : a.entrySet()) {
			this.featureStats.put(i.getKey(), (double)i.getValue()/column.size());
		}
	}
    /**
     * Returns the most occouring value in a column.
     * @param theColumn the column to preform calculations on.
     * @return the most occouring value in a column.
     */
    private void setMode() {
        Object m = null;
        for (Map.Entry<Object,Integer> entry : uniqueValueCounts.entrySet()) {
            m = entry.getKey();
            break;
            }
        this.mode = m;   
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
	 * Returns a string consisting of the statisitcs of the column.
	 * @return a string consisting of the statistics of the column.
	 */
	public String toStringStatistics() {
	    String str = "Column " + name + "\nType: " + type;
	    str += "\nMean: " + mean;
	    str += "\nMode: " + mode;
	    str += "\nMedian: " + median;
	    str += "\nSum: " + sum;
	    str += "\nVariance: " + variance;
	    str += "\nStandard Deviation: " + std;
	    str += "\nEntropy: " + entropy;
	    str += "\nNumber of Unique Values: " + totalUniqueValues;
	    str += "\nUnique Values: " + uniqueValues;
	    str += "\nUnique Value Counts: " + uniqueValueCounts;
	    str += "\nFeature Stats: " + featureStats + "\n";
	    return str;
	}
	
    /**
     * NUMERIC COLUMN METHODS
     */
	
	/**
     * Calculates the sum & mean of a numeric column. Returns 0 if column is non numeric.
     * @param column the column to preform calculations on.
     * @return the sum of a numeric column.
     */
    private void setSumAndMean() {
    	//long sum = IntStream.of(array).parallel().sum();
        double sum = 0;
        int nanCount = 0;
        this.max = column.get(0).getDoubleValue();
        this.min = column.get(0).getDoubleValue();
	       for(int i = 0;i < column.size();i++) {
	    	   if (column.get(i) instanceof NANParticle) 
	    		   nanCount++;
	    	   else if (column.get(i) instanceof DoubleParticle)
	               sum += (Double) column.get(i).getValue();
	           else {
	               sum += (Integer) column.get(i).getValue();
	           }
	    	   if(column.get(i).getDoubleValue() > this.max) {
	    		   setMax(column.get(i).getDoubleValue());
	    	   }
	    	   if(column.get(i).getDoubleValue() < this.min) {
	    		   setMin(column.get(i).getDoubleValue());
	    	   }
	        }
        this.sum = sum;
        this.mean = sum / (column.size() - nanCount);
        this.range = this.max - this.min;
    }
    private void setMin(double m) {
    	this.min = m;
    }
    /**
     * set max
     * @param m
     */
    private void setMax(double m) {
    	this.max = m;
    }
    /**
     * Calculates the entropy of a column.
     * @param theColumn the column to preform calculations on.
     * @return the entropy of a column.
     */
    private void setEntropy() {
        HashMap<Object,Integer> values = uniqueValueCounts;
        double ent = 0;
        for (Integer value : values.values()) {
            ent = ent + (-1)* ((double)value / column.size()) * ( Math.log10(((double)value / column.size())) / Math.log10(2));
        }
        this.entropy  = ent;
    }
    
    /**
     * Calculates the variance of a column.
     * @param theColumn the column to preform calculations on.
     * @return The variance of a column.
     */
    private void setVariance() {
        double var = 0.0;
        for(int i = 0;i < column.size();i++) {
        	if(column.get(i) instanceof NANParticle) {
        		continue;
        	}
            var += Math.pow(((Double)getDoubleValue(i) - mean), 2);
        }
        this.variance = var / column.size();
    }
    
    /**
     * Calculates the median value in a column.
     * @param theColumn the column to preform calculations on.
     * @return the median value of a column if column is numeric, mode if column is non numeric.
     */
    private void setMedian() {
        List<Double> sorted = new ArrayList<Double>();
        for (int i = 0; i < column.size(); i++) {
            if (!(column.get(i) instanceof NANParticle))
                sorted.add(column.get(i).getDoubleValue());
        }
        Collections.sort(sorted);
        if (sorted.size() % 2 == 0)
            median = (double) sorted.get(sorted.size() / 2);
        else
            median = (double) sorted.get((sorted.size() + 1) / 2);
    }
    
    /**
     * Calculates the standard deviation of a column.
     * @param theColumn the column to preform calculations on.
     * @return the standard deviation of a column.
     */
    private void setStandardDeviation() {
        this.std = Math.sqrt(variance);       
    }
    
    /**
     * Returns the sum of a given set of indexes.
     * @param theColumn the column to preform calculations on.
     * @param indexes the set of indexes.
     * @return the sum of a given set of indexes.
     */
    public double sumOfIndexes(Set<Integer> indexes) {
        double sum = 0; 
        if (getType() == 'N') {
            for (Integer i : indexes) {
                Particle p = getParticle(i);
                if (p instanceof DoubleParticle)
                    sum += (Double) p.getValue();
                else if (p instanceof IntegerParticle)
                    sum += (Integer) p.getValue();
            }
        }
        return sum;
    }
    
    /**
     * Returns the mean of a given set of indexes.
     * @param theColumn the column to preform calculations on.
     * @param indexes the set of indexes.
     * @return the sum of a given set of indexes.
     */
    public double meanOfIndexes(Set<Integer> indexes) {
        double sum = sumOfIndexes(indexes);
        return sum / indexes.size();
    }
    /**
     * returns a columns sorted values
     * @return
     */
    public ArrayList<Particle> getSortedValues(){
    	return this.sorted_column;
    }
}
