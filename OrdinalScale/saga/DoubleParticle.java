package saga;
/**
 * Double particle
 * @author logan.collier
 *
 */
public class DoubleParticle extends Particle{
	
    /**
	 * Constructor
	 * @param value
	 */
	public DoubleParticle(Double value) {
	    super(value, "Double");
	}
	
	/**
	 * @return the double value
	 */
    @Override
    public Double getValue() {
        return (Double) super.value;
    }
    
    /**
     * set to new value
     * @param newValue
     */
    @Override
	public void setValue(Object newValue) {
		this.value = (Double) newValue;
	}
    
    /**
     * Returns a deep copy of the double particle.
     * @return a deep copy of the double particle.
     */
    @Override
    public Particle deepCopy() {
        return new DoubleParticle((double) value);
    }

    @Override
    public int compareTo(Particle p) {
        return Double.compare((Double) value, (Double) p.value);
    }

}
