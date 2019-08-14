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
	/**
	 * @return the object
	 */
    @Override
    public Object getValue() {
        return (Object) super.value;
    }
    /**
     * Change the object value
     */
	@Override
	public void setValue(Object newValue) {
		this.value = newValue;
		
	}

}
