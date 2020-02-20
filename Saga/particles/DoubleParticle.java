package particles;
/**
 * Double particle
 * @author logan.collier
 *
 */
public class DoubleParticle extends Particle{
	
    /**
	 * Constructor
	 * @param value
	 */
	public DoubleParticle(Double value) {
	    super(value, 'd');
	}
	public DoubleParticle(Double value, int index) {
		super(value,'d',index);
	}
	
	/**
	 * @return the double value
	 */
    @Override
    public Double getValue() {
        return (Double) super.value;
    }
    
    /**
     * set to new value
     * @param newValue
     */
    @Override
	public void setValue(Object newValue) {
		this.value = (Double) newValue;
	}
    
    /**
     * Returns a deep copy of the double particle.
     * @return a deep copy of the double particle.
     */
    @Override
    public Particle deepCopy() {
        return new DoubleParticle((double) value,index);
    }
    

    @Override
    public int compareTo(Particle p) {
    	if(p.type == 'i')
    		return Double.compare((double) value, p.getDoubleValue());
    	else
    		return Double.compare((Double) value, (Double) p.value);
    }

}
