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
	    super(value, 'o');
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
	
    @Override
    public Particle deepCopy() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public int compareTo(Particle p) {
        // TODO Auto-generated method stub
        return 0;
    }

}
