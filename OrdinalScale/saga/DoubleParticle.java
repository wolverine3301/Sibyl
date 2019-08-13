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

    @Override
    public Double getValue() {
        return (Double) super.value;
    }

}
