package metamorphose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import clairvoyance.Distance;
import saga.Column;
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
    
    //TODO: Update distance methods or add a new distance method which ONLY takes numeric values into account!
    
    /**
     * Uses random initial centroid choice to compute KMeans
     * @return a hashmap consisting of keys that are the centroid means, and an array list of particles corresponding to the centroids cluster.
     */
    public ArrayList<Set<Row>> cluster() {
        ArrayList<Cluster> centroids = new ArrayList<Cluster>();
        TreeSet<Integer> initialCentroidIndexes = new TreeSet<Integer>();
        while (centroids.size() != k) { //Initialize Centroids
            Random ran = new Random();
            int tempIndex = ran.nextInt(trainingData.numRows);
            if (!initialCentroidIndexes.contains(tempIndex)) {
                centroids.add(new Cluster(new Row(trainingData.getRow_byIndex(tempIndex))));
                initialCentroidIndexes.add(tempIndex);
            }
        }
        int changeCount = -1;
        //For each row in training data, assign point to cluster, continue to do this until no further changes are made.
        while (changeCount != 0) {
            changeCount = 0;
            for (int i = 0; i < trainingData.numRows; i++) { //for each row, findNearestCentroid
                Row currentRow = trainingData.getRow_byIndex(i);
                double d = centroids.get(0).distanceFrom(currentRow);
                int nearestCentroidIndex = 0;
                for (int j = 1; j < centroids.size(); j++) { //loop through centroid collection
                    double distanceToCentroid = centroids.get(j).distanceFrom(currentRow);
                    if (d < distanceToCentroid) {
                        d = distanceToCentroid;
                        nearestCentroidIndex = j;
                    }
                }
                if (!centroids.get(nearestCentroidIndex).containsMember(currentRow)) { //If centroid chosen doesn't contain the member
                    centroids.get(nearestCentroidIndex).addMember(currentRow, d, i);
                    changeCount++;
                    for (int j = 0; j < centroids.size(); j++) { //Remove cluster membership from the other cluster which contains the row.
                        if (j != nearestCentroidIndex) {
                            if (centroids.get(j).removeMember(currentRow, i))
                                break;
                        }
                    }
                }
            }
            for (int i = 0; i < centroids.size(); i++) 
                centroids.get(i).updateCentroidValues();
        }
        ArrayList<Set<Row>> finalClusters = new ArrayList<Set<Row>>();
        for (Cluster c : centroids) {
            finalClusters.add(c.getMembers());
        }
        return finalClusters;
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
        
        private HashMap<Row, Double> clusterMembers;
        
        private TreeSet<Integer> clusterMemberIndexes;
        
        public Cluster(Row theCentroid) {
            centroid = theCentroid;
            clusterMembers = new HashMap<Row, Double>();
            clusterMemberIndexes = new TreeSet<Integer>();
        }
        
        public void addMember(Row theRow, double theDistance, int theIndex) {
            clusterMembers.put(theRow, theDistance);
            clusterMemberIndexes.add(theIndex);
        }
        
        public double distanceFrom(Row theRow) {
            return distanceType.distance(centroid, theRow);
        }
        
        /**
         * Returns true if the member is contained within the cluster & has been removed, false otherwise.
         * @param theRow the row to remove.
         * @param theIndex the index of the row being removed.
         * @return true if the member is contained & removed, false otherwise.
         */
        public boolean removeMember(Row theRow, int theIndex) {
            if (clusterMembers.containsKey(theRow)) {
                clusterMembers.remove(theRow);
                clusterMemberIndexes.remove(theIndex);
                return true;
            } else {
                return false;
            }
        }
        
        /**
         * Updates the centroid's values to the mean values of the assigned cluster members if the cluster values are numeric.
         */
        public void updateCentroidValues() {
            for (int i = 0; i < trainingData.numColumns; i++) {
                Column tempColumn = trainingData.getColumn_byIndex(i);
                if (tempColumn.type.contains("Double") || tempColumn.type.contains("Integer")) {
                    centroid.changeValue(i, new DoubleParticle(tempColumn.meanOfIndexes(clusterMemberIndexes)));
                }
            }
        }
        
        public boolean containsMember(Row r) {
            return clusterMembers.containsKey(r);
        }
        
        public Set<Row> getMembers() {
            return clusterMembers.keySet();
        }
    }
}
