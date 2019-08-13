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

    @Override
    public String getValue() {
        return (String) super.value;
    }

}
