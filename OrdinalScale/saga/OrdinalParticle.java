package saga;

import java.util.HashMap;

/**
 * Ordinal particle
 * @author logan.collier
 *
 */
public class OrdinalParticle extends Particle{
	/**
	 * Constructor
	 * @param value
	 */
	public OrdinalParticle(HashMap<String, Integer> value) {
	    super(value, "Ordinal");
	}

    @Override
    public Integer getValue() {
        return (Integer) super.value;
    }

	@Override
	public void setValue(Object newValue) {
		// TODO Auto-generated method stub
		
	}

}
