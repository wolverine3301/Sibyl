package distances;

import dataframe.Row;
import particles.Particle;

/**
 * Computes the hamming distance between two given rows.
 * NOTE: All particles or correspoding columns analyzed by this distance method must have the
 * same length and data type for best results. 
 * @author Cade Reynoldson
 *
 */
public class Hamming extends Distance{

    public Hamming() {
        super("Hamming");
    }
    
    @Override
    public double distance(Row r1, Row r2) {
        double distance = 0;
        for(int i = 0;i < r1.getLength();i++) {
            Particle p1 = r1.getParticle(i);
            Particle p2 = r2.getParticle(i);
            if(p1.type == 'c') { //IF THIS IS CATEGORICAL - UPDATE LATER
                if(p1.type != p2.type) { //IF THEY ARE DIFFERENT
                    distance++;
                }
            }
            else {
                String p1ValueStr = String.valueOf(p1.getValue());
                String p2ValueStr = String.valueOf(p2.getValue());
                if (p1ValueStr.length() == p2ValueStr.length()) {
                    int count = 0;
                    for (int j = 0; i < p1ValueStr.length(); i++) {
                        if (p1ValueStr.charAt(j) != p2ValueStr.charAt(j))
                            count++;
                    } 
                    distance += count;
                }
            } 
        }
        return distance;
    }

}
