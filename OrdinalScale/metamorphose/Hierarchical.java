package metamorphose;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;

import clairvoyance.Distance;
import saga.DataFrame;

public class Hierarchical {
    
    /** A reference to the data frame to group into clusters. */
    private DataFrame trainingData;
    
    /** The distance method to use for calculation */
    private Distance distanceType;
    
    /**
     * Creates a new instance of hierarchical, so cluster can be ran.
     * @param theTrainingData
     * @param theDistanceType
     */
    public Hierarchical(DataFrame theTrainingData, Distance theDistanceType) {
        trainingData = theTrainingData;
        distanceType = theDistanceType;
    }

    /**
     * Clusters a given data frame using a bottom up hierarchical approach. Returns an array list of arraylists, with each representing a layer of clusters.
     * These clusters also contain references to the clusters which were merged to create them, creating an underlying tree structure.
     * @return An arraylist of arraylists, with each level of the outer array list representing a layer of cluster hierarchy.
     */
    public ArrayList<ArrayList<Cluster>> cluster() {
        ArrayList<ArrayList<Cluster>> clusters = new ArrayList<ArrayList<Cluster>>();
        int clusterLevel = 0;
        ArrayList<Cluster> currentLevel = new ArrayList<Cluster>();
        for (int i = 0; i < trainingData.numRows; i++) { //Initialize original cluster level, with all clusters being rows of the data frame
            Cluster c = new Cluster(trainingData.getRow_byIndex(i));
            c.addMember(trainingData.getRow_byIndex(i), 0, i);
            currentLevel.add(c);
        }
        clusters.add(currentLevel); //Add this level to the bottom of the cluster hierarchy.
        TreeSet<DistanceData> distances = new TreeSet<DistanceData>(new Comparator<DistanceData>() { //data structure to hold the distance data
            @Override
            public int compare(DistanceData d1, DistanceData d2) {
                return Double.compare(d1.distance, d2.distance);
            }
        });
        for (int i = 0; i < clusters.get(clusterLevel).size(); i++) { //initialize distances in the distance tree.
            for (int j = i + 1; j < clusters.get(clusterLevel).size(); j++) {
                distances.add(new DistanceData(clusters.get(clusterLevel).get(i), clusters.get(clusterLevel).get(j),
                        distanceType.distance(clusters.get(clusterLevel).get(i).centroid, clusters.get(clusterLevel).get(j).centroid)));
            }
        }
        while (clusters.get(clusterLevel).size() != 1) {                        //Merge closest pairs of clusters until all clusters are contained in one.
            currentLevel = new ArrayList<Cluster>();                            //Make a new blank array list for the next level of heirarchy.
            DistanceData closestClusters = distances.first();                   //Remove the distance data containing the closest clusters from the tree.
            distances.remove(closestClusters);                                  //remove the distance data of the two clusters.
            Cluster mergedCluster = Cluster.merge                               //create merged cluster from the closest clusters.
                    (closestClusters.c1, closestClusters.c2, trainingData, distanceType);
            currentLevel.add(mergedCluster);                                    //add the newly created cluster to the next level of the heiarchy.
            ArrayList<DistanceData> toRemove = new ArrayList<DistanceData>();
            for(DistanceData d : distances) {                                   //Remove all distance data that contains references to the newly merged clusters.
                if (d.containsClusterID(closestClusters.c1.clusterId) || d.containsClusterID(closestClusters.c2.clusterId)) {
                    toRemove.add(d);
                } 
            }
            distances.removeAll(toRemove);
            for (int i = 0; i < clusters.get(clusterLevel).size(); i++) {        //Add new distance references to the newly added cluster, and create the new layer of clusters.
                Cluster currentCluster = clusters.get(clusterLevel).get(i);
                if (!closestClusters.containsClusterID(currentCluster.clusterId)) {
                    currentLevel.add(currentCluster);
                    distances.add(new DistanceData(mergedCluster, currentCluster, //Add new distance reference to distance tree.
                            distanceType.distance(mergedCluster.centroid, currentCluster.centroid)));
                }
            }
            clusters.add(currentLevel);
            clusterLevel++;
        }
        return clusters;
    }
    
    /**
     * Holds data for comparing distances between clusters, along with references to compared clusters.
     * @author Cade Reynoldson
     * @version 1.0
     */
    private class DistanceData {
        
        /** One of the clusters used for measurment */
        private Cluster c1;
        
        /** One of the clusters used for measurment */
        private Cluster c2;
        
        /** The distance between c1 and c2. */
        private double distance;
        
        /**
         * Creates a new instance of distance data.
         * @param theC1 cluster 1
         * @param theC2 cluster 2
         * @param theDistance the distance between cluster 1 and cluster 2/
         */
        public DistanceData (Cluster theC1, Cluster theC2, double theDistance) {
            c1 = theC1;
            c2 = theC2;
            distance = theDistance;
        }
        
        /**
         * Returns true if one of this classes cluster's ID matches the parameterized cluster ID, false otherwise.
         * @param theClusterId the cluster ID to look for.
         * @return true if ID matches the id of a cluster, false otherwise.
         */
        public boolean containsClusterID(int theClusterId) {
            if (c1.clusterId == theClusterId || c2.clusterId == theClusterId) 
                return true;
            else
                return false;
        }
        
        /**
         * Returns a string representation of the clusters & the distance between them.
         * @return a string representation of the clusters & the distance between them. 
         */
        @Override
        public String toString() {
            return "\nCLUSTER 1: \n" + c1.toString() + "\nCLUSTER 2: \n" + c2.toString() + "\nDISTANCE BETWEEN C1 & C2: " + distance + "\n";
        }
    }
    
}
