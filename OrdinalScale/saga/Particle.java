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
    
    public Particle(Particle theParticle) {
        
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
    
    public abstract Object getValue();
    
    /**
     * Change the value of the particle
     * @param newValue
     */
    public abstract void setValue(Object newValue);
    
    
    
}
