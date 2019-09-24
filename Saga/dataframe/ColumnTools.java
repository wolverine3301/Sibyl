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

import particles.DoubleParticle;
import particles.IntegerParticle;
import particles.Particle;

/**
 * Contains static methods for preforming various operations on a given column.
 * @author Cade Reynoldson
 * @version 1.0
 */
public class ColumnTools {
    
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
     * Calculates the sum of a numeric column. Returns 0 if column is non numeric.
     * @param theColumn the column to preform calculations on.
     * @return the sum of a numeric column.
     */
    public static double sum(Column theColumn) {
        double sum = 0;
        if(theColumn.getType() == 'N') {
            for(int i = 0;i < theColumn.getLength();i++) {
                if (theColumn.getParticle(i) instanceof DoubleParticle)
                    sum += (Double) theColumn.getParticle(i).getValue();
                else
                    sum += (Integer) theColumn.getParticle(i).getValue();
            } 
        }
        return sum;
    }
    
    /**
     * Returns the most occouring value in a column.
     * @param theColumn the column to preform calculations on.
     * @return the most occouring value in a column.
     */
    public static Object mode(Column theColumn) {
        Object m = null;
        for (Map.Entry<Object,Integer> entry : uniqueValCnt(theColumn).entrySet()) {
            m = entry.getKey();
            break;
            }
        return m;   
    }
    /**
     * Calculates the mean of a column.
     * @param theColumn the column to preform calculations on.
     * @return the mean of a column.
     */
    public static double mean(Column theColumn) {
        return sum(theColumn) / (double) theColumn.getLength();  
    }
    
    /**
     * Returns the mean of a given set of indexes.
     * @param theColumn the column to preform calculations on.
     * @param indexes the set of indexes.
     * @return the sum of a given set of indexes.
     */
    public static double meanOfIndexes(Column theColumn, Set<Integer> indexes) {
        double sum = sumOfIndexes(theColumn, indexes);
        return sum / indexes.size();
    }
    
    /**
     * Returns the sum of a given set of indexes.
     * @param theColumn the column to preform calculations on.
     * @param indexes the set of indexes.
     * @return the sum of a given set of indexes.
     */
    public static double sumOfIndexes(Column theColumn, Set<Integer> indexes) {
        double sum = 0; 
        if (theColumn.getType() == 'N') {
            for (Integer i : indexes) {
                Particle p = theColumn.getParticle(i);
                if (p instanceof DoubleParticle)
                    sum += (Double) p.getValue();
                else if (p instanceof IntegerParticle)
                    sum += (Integer) p.getValue();
            }
        }
        return sum;
    }
    
    /**
     * Calculates the median value in a column. Returns the mode if column is non numeric.
     * @param theColumn the column to preform calculations on.
     * @return the median value of a column if column is numeric, mode if column is non numeric.
     */
    public static Object median(Column theColumn) {
        if (theColumn.getType() == 'N') {
            List<Particle> sorted = new ArrayList<Particle>();
            for (int i = 0; i < theColumn.getLength(); i++) {
                sorted.add(theColumn.getParticle(i).deepCopy());
            }
            Collections.sort(sorted);
            return sorted.get(sorted.size() / 2);
        } else {
            return mode(theColumn);
        }
    }
    
    /**
     * Calculates the variance of a column.
     * @param theColumn the column to preform calculations on.
     * @return The variance of a column.
     */
    public static double variance(Column theColumn) {
        double var = 0.0;
        double mean = mean(theColumn);
        for(int i = 0;i < theColumn.getLength();i++) {
            var += Math.pow(((Double)theColumn.getParticle(i).getValue() - mean), 2);
        }
        return var / theColumn.getLength();
    }
    
    /**
     * Calculates the standard deviation of a column.
     * @param theColumn the column to preform calculations on.
     * @return the standard deviation of a column.
     */
    public static double standardDeviation(Column theColumn) {
        return Math.sqrt(variance(theColumn));       
    }
    /**
     * Calculates the entropy of a column.
     * @param theColumn the column to preform calculations on.
     * @return the entropy of a column.
     */
    public static double entropy(Column theColumn) {
        HashMap<Object,Integer> values = uniqueValCnt(theColumn);
        double ent = 0;
        for (Integer value : values.values()) {
            ent = ent + (-1)* ((double)value / theColumn.getLength()) * ( Math.log10(((double)value / theColumn.getLength())) / Math.log10(2));
        }
        return ent;
    }
    
    /**
     * @return set of unique values
     */
    public static Set<Object> uniqueValues(Column theColumn){ 
        Set<Object> unique = new HashSet<Object>();
        for(int i = 0; i < theColumn.getLength(); i++) {
            unique.add(theColumn.getParticle(i).getValue());
        }   
        return unique;
    }
    
    /**
     * returns a hashmap: keys are each unique value in array list and they point to the number of occurances
     * @return
     */
    public static HashMap<Object, Integer> uniqueValCnt(Column theColumn) {
        Set<Object> unique = uniqueValues(theColumn);
        HashMap<Object, Integer> vals = new HashMap<>();
        //initialize map
        for(Object o : unique) {
            vals.put(o, 0);
        }
        //counting
        for(int i = 0; i < theColumn.getLength(); i++) {  
            Particle p = theColumn.getParticle(i);
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
        return temp;
    }//end uniqueValCnt
    
}
