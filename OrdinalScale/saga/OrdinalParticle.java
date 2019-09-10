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
	    super(value, 'O');
	}
	/**
	 * @return the ordinal value
	 */
    @Override
    public Integer getValue() {
        return (Integer) super.value;
    }
    /**
     * change the ordinal value
     */
	@Override
	public void setValue(Object newValue) {
		this.value = newValue;
	}
	
	/**
	 * TODO update this.
	 */
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
