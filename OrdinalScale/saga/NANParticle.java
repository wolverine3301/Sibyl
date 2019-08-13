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

	@Override
	public void setValue(Object newValue) {
		// TODO Auto-generated method stub
		
	}

}
