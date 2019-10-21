package particles;

public class TimeParticle extends Particle{
	public int year;
	public int quarter;
	public int month;
	public int weekDay;
	public int day;
	public int hour;
	public int minute;
	public int second;
	
	public TimeParticle(Object theValue, char theType) {
		super(theValue, theType);
		
	}

}
