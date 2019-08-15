package saga;
/**
 * Integer particle
 * @author logan.collier
 *
 */
public class IntegerParticle extends Particle {
	
    /**
	 * Creates a new instance of an integer particle.
	 * @param value
	 */
	public IntegerParticle(Integer value) {
	    super(value, "Integer");
	}
	
	/**
	 * Returns the value of the particle (integer).
	 * @return The integer value in this particle.
	 */
    @Override
    public Integer getValue() {
        return (Integer) super.value;
    }
    
    /**
     * Set new value for the particle.
     */
    @Override
	public void setValue(Object newValue) {
		this.value = (Integer) newValue;
	}

    /**
     * Returns a deep copy of the integer particle.
     */
    @Override
    public Particle deepCopy() {
        return new IntegerParticle((int) value);
    }

}
