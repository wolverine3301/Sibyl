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

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(Object newValue) {
		// TODO Auto-generated method stub
		
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
	private void setTimeFormat(String time) {
		char[] time_arr = time.toCharArray();
		
		
	}

}
