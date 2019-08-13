package saga;

public class StringParticle extends Particle{
	
	public StringParticle(String value) {
	    super(value, "String");
	}

    @Override
    public String getValue() {
        return (String) super.value;
    }

}
