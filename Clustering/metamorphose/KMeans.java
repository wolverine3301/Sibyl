package metamorphose;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import dataframe.DataFrame;
import dataframe.Row;
import distances.Distance;

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
    public ArrayList<Set<Row>> cluster(int k, Distance distanceType, DataFrame trainingData) {
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
        return kMeans(centroids);
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
                    double distanceToCentroid = centroids.get(j).distanceFrom(currentRow, distanceType);
                    if (d > distanceToCentroid) {
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
            for (int i = 0; i < centroids.size(); i++) //Update the mean centroid values. 
                centroids.get(i).updateCentroidValues(trainingData);
        }
        ArrayList<Set<Row>> finalClusters = new ArrayList<Set<Row>>(); //Convert the final calculated clusters into an array list of sets.
        for (Cluster c : centroids) {
            finalClusters.add(c.getMembers());
        }
        return finalClusters;
    }
    
    /**
     * Returns a max heap (priority queue) of distance calculations and their corresponding row distances, calculating the farthest neighbors from a cluster. 
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
     * Used by kmeans++ to properly optimize initial centroid placement.
     * @author Cade Reynoldson
     * @version 1.0
     */
    private class DistanceData {
        
        /** The index of the row the distance was calculated to */
        private int rowIndex;
        
        /** The distance to the row index */
        private double distanceTo;
        
        /**
         * Initializes the fields. ITS CALLED A CONSTRUCTOR FOR A REASON 
         * @param theRowIndex The row index of the calculated distance.
         * @param theDistanceTo The distance to that row index.
         */
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
