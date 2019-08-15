package saga;
/**
 * String Particle
 * @author logan.collier
 *
 */
public class StringParticle extends Particle{
	/**
	 * Constructor
	 * @param value
	 */
	public StringParticle(String value) {
	    super(value, "String");
	}
	/**
	 * return the string value
	 */
    @Override
    public String getValue() {
        return (String) super.value;
    }
    /**
     * change the string value
     */
	@Override
	public void setValue(Object newValue) {
		this.value = (String) newValue;
		
	}
	
	/**
	 * Returns a deep copy of the string particle.
	 * @return a deep copy of the string particle.
	 */
    @Override
    public Particle deepCopy() {
        return new StringParticle((String) value);
    }

}
