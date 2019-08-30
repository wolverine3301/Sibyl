package metamorphose;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import clairvoyance.Distance;
import saga.Column;
import saga.DataFrame;
import saga.DoubleParticle;
import saga.IntegerParticle;
import saga.Row;

/**
 * KMeans class. Runs on a passed DataFrame, and groups the DataFrame's rows into clusters, specifically K clusters (parameterized in the constructor).
 * @author Cade Reynoldson
 * @version 1.0
 */
public class KMeans {
    
    /** The amount of clusters to generate */
    private int k;
    
    /** The distance formula for distance calculations. */
    private Distance distanceType;
    
    /** The data set to be clustered */
    private DataFrame trainingData;
    
    /**
     * Creates a new instance of the class, used to initialize the parameters of the algorithm.
     * @param theK the amount of clusters to generate.
     * @param theDistanceType the type of distance formula to use for calculation.
     * @param theTrainingData the data set to group into clusters.
     */
    public KMeans(int theK, Distance theDistanceType, DataFrame theTrainingData) {
        k = theK;
        distanceType = theDistanceType;
        trainingData = theTrainingData;
    }
    
    /**
     * Uses random initial centroid choice to compute KMeans. (Non-optimized initial centroid choice implementation).
     * @return An array list (each index representing a cluster) of sets of rows, with rows representing cluster members.
     */
    public ArrayList<Set<Row>> cluster() {
        ArrayList<Cluster> centroids = new ArrayList<Cluster>(); //The clusters
        TreeSet<Integer> initialCentroidIndexes = new TreeSet<Integer>();
        while (centroids.size() != k) { //Initialize Centroids
            Random ran = new Random();
            int tempIndex = ran.nextInt(trainingData.numRows);
            if (!initialCentroidIndexes.contains(tempIndex)) {
                centroids.add(new Cluster(new Row(trainingData.getRow_byIndex(tempIndex))));
                initialCentroidIndexes.add(tempIndex);
            }
        }
        return kMeans(centroids);
    }
    
    /**
     * Uses optimized initial centroid choices to calculate kmeans.
     * @return An array list (each index representing a cluster) of sets of rows, with rows representing cluster members.
     */
    public ArrayList<Set<Row>> clusterOptimized() {
        ArrayList<Cluster> centroids = new ArrayList<Cluster>();
        TreeSet<Integer> initialCentroidIndexes = new TreeSet<Integer>();
        centroids.add(new Cluster(trainingData.getRow_byIndex(0)));
        initialCentroidIndexes.add(0);
        for (int i = 1; i < k; i++) {
            PriorityQueue<DistanceData> distances = calculateFarthestNeighbors(centroids.get(centroids.size() - 1).centroid); //fetch the last initialized centroid.
            DistanceData d = distances.remove();
            while (initialCentroidIndexes.contains(d.rowIndex)) { //If farthest centroid is contained, get second farthest, and so on.
                d = distances.remove();
            }
            centroids.add(new Cluster(trainingData.getRow_byIndex(d.rowIndex))); //Add selected centroid to the 
            initialCentroidIndexes.add(d.rowIndex);
        }
        System.out.println("INITIAL CENTROIDS: ");
        for (int i = 0; i < centroids.size(); i++) {
            System.out.println("Centroid " + i + ": " + centroids.get(i).centroid.toString());
        }
        return kMeans(centroids);
    }
    
    /**
     * Returns a max heap (priority queue) of distance calculations and their corresponding row distances. 
     * @param r The row to find the farthest neighbors of.
     * @return a max heap of distance calculations. 
     */
    private PriorityQueue<DistanceData> calculateFarthestNeighbors(Row r) {
        PriorityQueue<DistanceData> distances = new PriorityQueue<DistanceData>(trainingData.numRows, new Comparator<DistanceData>() {
            public int compare(DistanceData o1, DistanceData o2) {
                if (o1.distanceTo > o2.distanceTo)
                    return -1;
                else if (o1.distanceTo < o2.distanceTo)
                    return 1;
                else
                    return 0;
            }
        });
        for (int i = 0; i < trainingData.numRows; i++) 
            distances.add(new DistanceData(i, distanceType.distance(r, trainingData.getRow_byIndex(i))));
        return distances;
    }
    
    /**
     * Calculates KMeans on a passed arraylist of clusters. 
     * @param centroids The centroids/clusters to calculate KMeans on.
     * @return all rows from the training data set put into clusters.
     */
    private ArrayList<Set<Row>> kMeans(ArrayList<Cluster> centroids) {
        int changeCount = -1;
        while (changeCount != 0) { //For each row in training data, assign point to cluster, continue to do this until no further changes are made.
            changeCount = 0;
            for (int i = 0; i < trainingData.numRows; i++) { //for each row, find nearest centroid
                Row currentRow = trainingData.getRow_byIndex(i);
                double d = Double.MAX_VALUE; // Temporary double value, makes sure any calculated distance will be less than this for initial calculation.
                int nearestCentroidIndex = -1;
                for (int j = 0; j < centroids.size(); j++) { //loop through centroid collection, calculate distances to each, and store smallest distance & index.
                    double distanceToCentroid = centroids.get(j).distanceFrom(currentRow);
                    if (d > distanceToCentroid) {
                        d = distanceToCentroid;
                        nearestCentroidIndex = j;
                    }
                }
                if (!centroids.get(nearestCentroidIndex).containsMember(currentRow)) { //If centroid chosen doesn't contain the member
                    centroids.get(nearestCentroidIndex).addMember(currentRow, d, i);
                    changeCount++;
                    for (int j = 0; j < centroids.size(); j++) { //Remove cluster membership from the other cluster which contains the row. //TODO: OPTIMIZE!
                        if (j != nearestCentroidIndex) {
                            if (centroids.get(j).removeMember(currentRow, i))
                                break;
                        }
                    }
                }
            }
            for (int i = 0; i < centroids.size(); i++) //Update the mean centroid values. 
                centroids.get(i).updateCentroidValues();
        }
        ArrayList<Set<Row>> finalClusters = new ArrayList<Set<Row>>(); //Convert the final calculated clusters into an array list of sets.
        for (Cluster c : centroids) {
            finalClusters.add(c.getMembers());
        }
        return finalClusters;
    }
    
    /**
     * Represents a cluster of rows. Stores all possible needed data for future calculations & expansion on KMeans based algorithms.
     * @author Cade Reynoldson  
     * @version 1.0
     */
    private class Cluster {
        
        /** The centroid of the cluster */
        private Row centroid;
        
        /** The cluster members with their distances to the centroid. */
        private HashMap<Row, Double> clusterMembers;
        
        /** The indexes of the cluster members in the original data frame. */
        private TreeSet<Integer> clusterMemberIndexes;
        
        /**
         * Creates a new cluster with a given row as the centroid. 
         * @param theCentroid the row to be the centroid of the cluster.
         */
        public Cluster(Row theCentroid) {
            centroid = new Row(theCentroid);
            clusterMembers = new HashMap<Row, Double>();
            clusterMemberIndexes = new TreeSet<Integer>();
        }
        
        /**
         * Adds a new member to the cluster.
         * @param theRow The row, or member to be added.
         * @param theDistance the distance of the member to the centroid.
         * @param theIndex the index of the row in the original data frame.
         */
        public void addMember(Row theRow, double theDistance, int theIndex) {
            clusterMembers.put(theRow, theDistance);
            clusterMemberIndexes.add(theIndex);
        }
        
        /**
         * Returns the distance of the row from the centroid of the cluster.
         * @param theRow the row to calculate the distance from the centroid of the cluster.
         * @return the row's distance from the centroid of the cluster.
         */
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
                if (tempColumn.type.contains("Double")) 
                    centroid.changeValue(i, new DoubleParticle(tempColumn.meanOfIndexes(clusterMemberIndexes)));
                else if (tempColumn.type.contains("Integer"))
                    centroid.changeValue(i, new IntegerParticle((int) tempColumn.meanOfIndexes(clusterMemberIndexes)));
            }
        }
        
        /**
         * Returns whether the cluster contains a row.
         * @param r the row to check for containment in the cluster.
         * @return true / false if the row is contained.
         */
        public boolean containsMember(Row r) {
            return clusterMembers.containsKey(r);
        }
        
        /**
         * Returns a set of the members contained in the cluster.
         * @return a set of the members contained in the cluster.
         */
        public Set<Row> getMembers() {
            return clusterMembers.keySet();
        }
        
        /**
         * Returns a string representation of the cluster.
         * @return a string representation of the cluster.
         */
        @Override
        public String toString() {
            String str = "Centroid: " + centroid.toString() + "\nMembers: ";
            for (Row r : clusterMembers.keySet()) {
                str += "\n" + r.toString() + "Distance: " + clusterMembers.get(r);
            }
            return str;
        }
    }
    
    /**
     * Used by kmeans++ to properly optimize initial centroid placement.
     * @author Cade Reynoldson
     * @version 1.0
     */
    private class DistanceData {
        /** The index of the row the distance was calculated to */
        private int rowIndex;
        
        /** The distance to the row index */
        private double distanceTo;
        
        /** Initializes the fields */
        public DistanceData(int theRowIndex, double theDistanceTo) {
            rowIndex = theRowIndex;
            distanceTo = theDistanceTo;
        }
        
        /**
         * Returns a string representation of the distance data. Mainly used for debugging.
         * @return a string representation of the distance data.
         */
        @Override
        public String toString() {
            return "Distance to row " + rowIndex + ": " + distanceTo;
        }
        
    }
}
