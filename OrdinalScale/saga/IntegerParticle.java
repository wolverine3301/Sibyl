package saga;

public class IntegerParticle extends Particle<Integer>{
	public int value;
	public String type = "Integer";

	public IntegerParticle(T value) {
	 super(value);
	}

}
