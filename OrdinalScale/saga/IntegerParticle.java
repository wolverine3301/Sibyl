package saga;

public class IntegerParticle extends Particle {

	public IntegerParticle(Integer value) {
	    super(value, "Integer");
	}

    @Override
    public Integer getValue() {
        return (Integer) super.value;
    }
	
	

}
