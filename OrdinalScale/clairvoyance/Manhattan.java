package clairvoyance;

import saga.DataFrame;
import saga.Particle;
import saga.Row;

public class Manhattan extends Distance{

    @Override
    public double distance(Row r1, Row r2) {
        double distance = 0;
        for(int i = 0;i < r1.getlength();i++) {
            Particle p1 = r1.getParticle(i);
            Particle p2 = r2.getParticle(i);
            Particle p3 = r1.getParticle(i + 1);
            Particle p4 = r2.getParticle(i + 1);
            //if the column is a string for categorical variablke
            if(p1.type.contains("Category")) {
                //if they are the same there is no distance to add
                if(!p1.type.contentEquals(p2.type)) {
                    distance = distance + 1;
                }
            }
            else if (p3.type.contains("Category")){
                if(!p3.type.contentEquals(p3.type)) {
                    distance = distance + 1;
                }
            } else {
                distance += Math.abs((double) p1.getValue() - (double) p4.getValue()) + Math.abs((double) p1.getValue() - (double) p3.getValue());
            }
        }
        return distance;
    }

    @Override
    public DataFrame distance_matrix() {
        // TODO Auto-generated method stub
        return null;
    }
    
    
}
