package distances;

import dataframe.Row;
import particles.Particle;

public class Mahalanobis extends Distance{

    public Mahalanobis() {
        super("Mahalanobis");
    }
    
    @Override
    public double distance(Row r1, Row r2) {
        double distance = 0;
        for (int i = 0; i < r1.getLength(); i++) {
            Particle p1 = r1.getParticle(i);
            Particle p2 = r2.getParticle(i);
        }
        return distance;
    }

}
