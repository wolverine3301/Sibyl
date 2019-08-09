package clairvoyance;

import saga.*;

public class Euclidean extends Distance{
	public double distance(Row r1, Row r2) {
		double distance = 0;
		for(int i = 0;i < r1.getlength();i++) {
			Particle p1 = r1.getParticle(i);
			Particle p2 = r2.getParticle(i);
			//if the column is a string for categorical variablke
			if(p1.type.contains("Category")) {
				//if they are the same there is no distance to add
				if(!p1.type.contentEquals(p2.type)) {
					distance = distance + 1;
				}
			}
			else {
				distance = distance + Math.pow(((double)p2.getValue() - (double)p1.getValue()),2 );
			}
		}//end for
		return Math.sqrt(distance);

	}

	public DataFrame distance_matrix(DataFrame df) {
		DataFrame matrix = new DataFrame();
		for(int i = 0; i < df.numRows;i++) {
			
		}
	}
}
