package particles;

import java.util.Comparator;

/**
 * Particle abstract class, represents various types of data to be stored in the data frame. 
 * 
 * @author Logan Collier
 * @author Cade Reynoldson
 * 
 */
public abstract class Particle implements Comparable<Particle>{
    
    /** The value of the Object */
    public Object value;
    
    /** A string representation of the type of object 
     * TYPE CHARACTERS:
     * 'i' : Integer
     * 'd' : Double
     * 's' : String
     * 'n' : Nan/Null
     * 'o' : Object
     * 'c' : Categorical
     * 'e' : Distance
     * 'g' : Object
     * 't' : time
     * */
    public char type;
    
    /** The next particle, acting as a linked list of rows. */
    public Particle nextParticle;
    
    /** index position in a column **/
    public int index;
    
    /**
     * Creates a new instance of a particle with a given value and type.
     * @param theValue
     * @param theType
     */
    public Particle(Object theValue, char theType) {
        this.value = theValue;
        this.type = theType;
        this.nextParticle = null;
    }
    /**
     * new particle with index position
     * @param value
     * @param type
     * @param index
     */
    public Particle(Object value, char type, int index) {
    	this.value = value;
    	this.type = type;
    	this.nextParticle = null;
    	this.index = index;
    }
    
    /**
     * @return the type
     */
    public char getType() {
        return type;
    }
    
    /**
     * Links this particle another particle.
     * @param p the particle to link to.
     */
    public void linkTo(Particle p) {
        nextParticle = p;
    }
    
    
    public String toString() {
        return type + "=" + value;
    }
    
    public boolean equals(Particle p) {
        if (value.equals(p.value) && type == p.type)
            return true;
        else
            return false;
    }
    /**
     * Used to override automatic resolution of data type, force a particle to become whatever object
     * type specified as the parameter. can cause errors
     * 
     * @param value
     * @param type - desired type of particle
     * @return particle
     */
    public static Particle resolveType(String value,char type) {
    	Particle newParticle;
    	if(type == 'N') {
            if(isInteger(value)) {
                newParticle = new IntegerParticle(Integer.parseInt(value));
            }else
                newParticle = new DoubleParticle(Double.parseDouble(value));
    	}else {
    		newParticle = new StringParticle(value);
    	}
    	return newParticle;
    }
    /**
     * Used to override automatic resolution of data type, force a particle to become whatever object
     * type specified as the parameter. can cause errors
     * 
     * @param value
     * @param type - desired type of particle
     * @return particle
     */
    public static Particle resolveType(Object value,char type) {
    	Particle newParticle = null;
    	if(type == 'N') {
            if (value instanceof Integer)
                newParticle = new IntegerParticle((Integer) value);
            else if (value instanceof Double)
                newParticle = new DoubleParticle((Double) value);
    	}else {
    		newParticle = new ObjectParticle(value);
    	}
    	return newParticle;
    }
    /**
     * TO DO: UPDATE FOR SUPPORT WITH ORDINAL & OBJECT PARTICLES.
     * Resolves the type of a value from a string.
     * @param value the string to resolve the type of.
     */
    public static Particle resolveType(String value) {
        Particle newParticle;
        
        if(isNumeric(value)) { //If the passed string is numeric.
        	
            if(isInteger(value)) {
                newParticle = new IntegerParticle(Integer.parseInt(value));
            }else
                newParticle = new DoubleParticle(Double.parseDouble(value));
        } else { //If the passed string is just a string.
            if(value==null || value.isEmpty() || value.toUpperCase().contentEquals("NAN") || value.toUpperCase().contentEquals("NULL")) {
            	newParticle = new NANParticle(value);
            }else {
            	
                newParticle = new StringParticle(value);
            }
        }
        
        return newParticle;
    }
    /**
     * TO DO: UPDATE FOR SUPPORT WITH ORDINAL & OBJECT PARTICLES.
     * Resolves the type of a value from a string.
     * @param value the string to resolve the type of.
     */
    public static Particle resolveType(String value,int index) {
        Particle newParticle;
        
        if(isNumeric(value)) { //If the passed string is numeric.
        	
            if(isInteger(value)) {
                newParticle = new IntegerParticle(Integer.parseInt(value));
            }else
                newParticle = new DoubleParticle(Double.parseDouble(value));
        } else { //If the passed string is just a string.
            if(value==null || value.isEmpty() || value.toUpperCase().contentEquals("NAN") || value.toUpperCase().contentEquals("NULL")) {
            	newParticle = new NANParticle(value);
            }else {
            	
                newParticle = new StringParticle(value);
            }
        }
        newParticle.setIndex(index);
        return newParticle;
    }
    public void setIndex(int index) {
    	this.index = index;
    }
    public int getIndex() {
    	return index;
    }
    
   /**
     * TO DO: UPDATE FOR SUPPORT WITH ORDINAL & OBJECT PARTICLES.
     * Resolves the type of a value from a string.
     * @param value the value to resolve the type of.
     */
    public static Particle resolveType(Object value) {
        Particle newParticle;
        if (value instanceof Integer)
            newParticle = new IntegerParticle((Integer) value);
        else if (value instanceof Double)
            newParticle = new DoubleParticle((Double) value);
        else if (value instanceof Object) {
            newParticle = new ObjectParticle(value);
        } else {
        	
            return null; //UPDATE FOR ORDINAL, OBJECT AND DISTANCE.
        }
        return newParticle;
    }
    public static Particle resolveType(Object value,int index) {
        Particle newParticle;
        if (value instanceof Integer)
            newParticle = new IntegerParticle((Integer) value);
        else if (value instanceof Double)
            newParticle = new DoubleParticle((Double) value);
        else if (value instanceof Object) {
            newParticle = new ObjectParticle(value);
        } else {
        	
            return null; //UPDATE FOR ORDINAL, OBJECT AND DISTANCE.
        }
        newParticle.setIndex(index);
        return newParticle;
    }
    
    /**
     * Checks if a passed string is an integer.
     * @param strNum the string which may contain an integer.
     * @return true if the string is an integer, false otherwise.
     */
    private static boolean isInteger(String strNum) {
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
    
    /**
     * Checks if a passed string is a type of numeric value.
     * @param strNum the string which may contain a numeric value.
     * @return true of the string is numeric
     */
    private static boolean isNumeric(String strNum) {
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
    public double getDoubleValue() {
    	double num = 0;
    	if(type == 'i') {
    		num = (int)value;
    		return (double) num;
    	}else if(type == 'n'){
    		return 0;
    	}else {
    		return (double)value;
    	}
    }
    
    /**
     * Returns the value of the particle.
     * @return the value of the particle.
     */
    public abstract Object getValue();
    
    /**
     * Changes the value of the particle.
     * @param newValue 
     */
    public abstract void setValue(Object newValue);
    
    /**
     * Returns a deep copy of a given particle.
     * @return a deep copy of a given particle.
     */
    public abstract Particle deepCopy();
    
    public abstract int compareTo(Particle p);
}
