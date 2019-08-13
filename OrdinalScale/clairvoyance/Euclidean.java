package clairvoyance;

import saga.*;

public class Euclidean extends Distance{
	public double distance(Row r1, Row r2) {
		double distance = 0;
		for(int i = 0;i < r1.getlength();i++) {
			OldParticle p1 = r1.getParticle(i);
			OldParticle p2 = r2.getParticle(i);
			//if the column is a string for categorical variablke
			if(p1.type.contains("String")) {
				//if they are the same there is no distance to add
				if(!p1.type.contentEquals(p2.type)) {
					distance = distance + 1;
				}
			}
			else {
				distance = distance + Math.pow((Double) p2.getValue() - (Double) p1.getValue(),2 );
			} 
		}//end for
		return Math.sqrt(distance);
	}
}
