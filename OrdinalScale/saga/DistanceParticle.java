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

    @Override
    public Double getValue() {
        return (Double) super.value;
    }

}
