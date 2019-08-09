package clairvoyance;

import saga.*;

public class Euclidean extends Distance{
	public double distance(Row r1, Row r2) {
		double distance;
		for(int i = 0;i < r1.getlength();i++) {
			Particle p1 = r1.getParticle(i);
			Particle p2 = r2.getParticle(i)
			if(p1.type.contains("String"))
			distance = distance + Math.pow((r2.values.get(i) - r1.values.get(i)),2 );
			
		}
		
	}

	@Override
	public DataFrame distance_matrix() {
		// TODO Auto-generated method stub
		return null;
	}
}
