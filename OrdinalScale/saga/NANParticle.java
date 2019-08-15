package saga;
/**
 * NAN particle
 * @author logan.collier
 *
 */
public class NANParticle extends Particle{
	
    /**
	 * constructor
	 * @param value
	 */
	public NANParticle(String value) {
	    super(value, "NAN");
	}
	
	/**
	 * @return value of string
	 */
	@Override
    public String getValue() {
        return (String) super.value;
    }
	
	/**
	 * Changes the value of the NAN particle.
	 */
    @Override
	public void setValue(Object newValue) {
		this.value = newValue;
	}
    
    /**
     * Returns a deep copy of the NAN particle.
     */
    @Override
    public Particle deepCopy() {
        return new NANParticle((String) value);
    }

}