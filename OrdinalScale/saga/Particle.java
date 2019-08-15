package saga;

public abstract class Particle {
    
    /** The value of the Object */
    public Object value;
    
    /** A string representation of the type of object */
    public String type;
    
    /**
     * Creates a new instance of a particle with a given value and type.
     * @param theValue
     * @param theType
     */
    public Particle(Object theValue, String theType) {
        value = theValue;
        type = theType;
    }
    
    
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    
    public String toString() {
        return type + "=" + value;
    }
    
    /**
     * TO DO: UPDATE FOR SUPPORT WITH ORDINAL & OBJECT PARTICLES.
     * Resolves the type of a value from a string.
     * @param value the string to resolve the type of.
     */
    public static Particle resolveType(String value) {
        Particle newParticle;
        if(isNumeric(value)) { //If the passed string is numeric.
            if(isInteger(value))
                newParticle = new IntegerParticle(Integer.parseInt(value));
            else
                newParticle = new DoubleParticle(Double.parseDouble(value));
        } else { //If the passed string is just a string.
            if(value.isBlank() || value.toUpperCase().contentEquals("NAN") || value.toUpperCase().contentEquals("NULL")) 
                newParticle = new NANParticle(value);
            else 
                newParticle = new StringParticle(value);
        }
        return newParticle;
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
        else if (value instanceof String) {
            String s = (String) value;
            if(s.isBlank() || s.toUpperCase().contentEquals("NAN") || s.toUpperCase().contentEquals("NULL")) 
                newParticle = new NANParticle((String) value);
            else 
                newParticle = new StringParticle((String) value);
        } else {
            return null; //UPDATE FOR ORDINAL, OBJECT AND DISTANCE.
        }
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
}
