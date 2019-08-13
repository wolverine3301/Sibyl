package clairvoyance;

import saga.Particle;
import saga.Row;

/**
 * Computes the hamming distance between two given rows.
 * NOTE: All particles or correspoding columns analyzed by this distance method must have the
 * same length, as well as  
 * @author Cade Reynoldson
 *
 */
public class Hamming extends Distance{

    @Override
    public double distance(Row r1, Row r2) {
        double distance = 0;
        for(int i = 0;i < r1.getlength();i++) {
            Particle p1 = r1.getParticle(i);
            Particle p2 = r2.getParticle(i);
            if(p1.type.contains("String")) { //IF THIS IS CATEGORICAL - UPDATE LATER
                if(!p1.type.contentEquals(p2.type)) { //IF THEY ARE DIFFERENT
                    distance = distance + 1;
                }
            }
            else {
                if (p1 instanceof OrdinalParticle && p2 instanceof OrdinalParticle) {
                    //ORDINAL HANDLING HERE
                } else ()
            } 
        }
    }

}
