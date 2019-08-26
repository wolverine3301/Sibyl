package metamorphose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeSet;

import clairvoyance.Distance;
import saga.DataFrame;
import saga.DoubleParticle;
import saga.IntegerParticle;
import saga.Particle;
import saga.Row;
import saga.StringParticle;

public class KMeans {
    
    private int k;
    
    private Distance distanceType;
    
    private DataFrame trainingData;
    
    public KMeans(int theK, Distance theDistanceType, DataFrame theTrainingData) {
        k = theK;
        distanceType = theDistanceType;
        trainingData = theTrainingData;
    }
    
    /**
     * Uses random initial centroid choice to compute KMeans
     * @return a hashmap consisting of keys that are the centroid means, and an array list of particles corresponding to the centroids cluster.
     */
    public HashMap<Double, ArrayList<Row>> cluster() {
        TreeSet<Cluster> centroids = new TreeSet<Cluster>();
        TreeSet<Integer> initialCentroidIndexes = new TreeSet<Integer>();
        while (centroids.size() != k) {
            Random ran = new Random();
            int tempIndex = ran.nextInt(trainingData.numRows);
            if (!initialCentroidIndexes.contains(tempIndex)) {
                centroids.add(new Cluster(new Row(trainingData.getRow_byIndex(tempIndex))));
                initialCentroidIndexes.add(tempIndex);
            }
        } 
        //Choose a random row 
        
        //For each row in training data, assign point to cluster
    }
    
    /**
     * Special variation of euclidean distance, removes the square root to make for more accurate clustering, and to avoid non-convergence.
     * @param r1 Row 1 for euclidean distance calculation.
     * @param r2 Row 2 for euclidean distance calculation.
     * @return the euclidean distance between the two rows.
     */
    private double EuclideanSquared(Row r1, Row r2) {
        double distance = 0;
        for(int i = 0;i < r2.getlength();i++) {
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
        }
        return distance;
    }
    
    private class Cluster {
        
        private Row centroid;
        
        private ArrayList<Row> clusterMembers;
        
        private ArrayList<Row> clusterMemberIndexes;
        
        public Cluster(Row theCentroid) {
            centroid = theCentroid;
            clusterMembers = new ArrayList<Row>();
        }
        
        public void addMember(Row theRow, int theIndex) {
            clusterMembers.add(theRow);
        }
    }
}
