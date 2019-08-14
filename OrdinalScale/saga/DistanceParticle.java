package saga;
/**
 * Distance Particle
 * @author logan.collier
 *
 */
public class DistanceParticle extends Particle{
	/**
	 * Constructor
	 * @param value
	 */
	public DistanceParticle(Double value) {
	    super(value, "Double");
	}
	/**
	 * @return Double value
	 */
    @Override
    public Double getValue() {
        return (Double) super.value;
    }
    /**
     * Set the value of the particle
     */
	public void setValue(Object newValue) {
		super.value = (Double) newValue;
	}

}
