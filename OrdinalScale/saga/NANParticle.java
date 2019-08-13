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

    @Override
    public String getValue() {
        return (String) super.value;
    }

}
