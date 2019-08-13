package saga;

/**
 * Object particle
 * @author logan.collier
 *
 */
public class ObjectParticle extends Particle{
	/**
	 * Constructor
	 * @param value
	 */
	public ObjectParticle(Object value) {
	    super(value, "Object");
	}

    @Override
    public Object getValue() {
        return (Object) super.value;
    }

}
