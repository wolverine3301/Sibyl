package saga;
/**
 * Integer particle
 * @author logan.collier
 *
 */
public class IntegerParticle extends Particle {
	/**
	 * Constructor
	 * @param value
	 */
	public IntegerParticle(Integer value) {
	    super(value, "Integer");
	}
	/**
	 * @return the integer value
	 */
    @Override
    public Integer getValue() {
        return (Integer) super.value;
    }
    /**
     * Set new value
     */
    @Override
	public void setValue(Object newValue) {
		this.value = (Integer) newValue;
	}

}
