package particles;

/**
 * Distance Particle
 * @author logan.collier
 *
 */
public class DistanceParticle extends Particle{
	
    /** The index of the row in which the comparison was made. */
    public int distanceToIndex;
    
    /** The name of the function used to calculate the distance. */ 
    public String distanceType;
    
    /**
	 * Constructor
	 * @param value
	 */
	public DistanceParticle(Double value) {
	    super(value, 'D');
	    distanceToIndex = -1;
	}
	
	public DistanceParticle(Double value, int theDistanceTo, String theDistanceType) {
	    super(value, 'D');
	    distanceToIndex = theDistanceTo;
	    distanceType = theDistanceType;
	}
	
	/**
	 * @return Double value
	 */
    @Override
    public Double getValue() {
        return (Double) super.value;
    }
    /**
     * Set the value of the particle
     */
	public void setValue(Object newValue) {
		super.value = (Double) newValue;
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
