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

    @Override
    public Integer getValue() {
        return (Integer) super.value;
    }
	
	

}
