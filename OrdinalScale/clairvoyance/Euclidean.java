package clairvoyance;

import saga.*;

public class Euclidean extends Distance{
	public double distance(Row r1, Row r2) {
		double distance = 0;
		for(int i = 0;i < r1.getlength();i++) {
			Particle p1 = r1.getParticle(i);
			Particle p2 = r2.getParticle(i);
			//if the column is a string for categorical variablke
			if(p1 instanceof StringParticle && p2 instanceof StringParticle) {
				//if they are the same there is no distance to add
				if(!p1.type.contentEquals(p2.type)) {
					distance = distance + 1;
				}
			} else if (p1 instanceof DoubleParticle && p2 instanceof DoubleParticle){
				distance = distance + Math.pow((Double) p2.getValue() - (Double) p1.getValue(),2 );
			} else if (p1 instanceof IntegerParticle && p2 instanceof IntegerParticle) {
			    int int1 = (int) p1.getValue();
			    int int2 = (int) p2.getValue();
			    distance = distance + Math.pow((double) int1 - (double) int2, 2);
			}
		}//end for
		return Math.sqrt(distance);
	}
}
