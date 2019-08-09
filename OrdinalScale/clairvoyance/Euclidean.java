package clairvoyance;

import saga.*;

public class Euclidean extends Distance{
	public double distance(Row r1, Row r2) {
		double distance;
		for(int i = 0;i < r1.getlength();i++) {
			if( r2.row.get(i).getType())
			distance = distance + Math.pow((r2.values.get(i) - r1.values.get(i)),2 );
			
		}
		
	}

	@Override
	public DataFrame distance_matrix() {
		// TODO Auto-generated method stub
		return null;
	}
}
