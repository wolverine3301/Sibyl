package metamorphose;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

import dataframe.DataFrame;
import distances.Distance;

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
     * These clusters also contain references to the clusters which were merged to create them, creating an underlying tree structure. The returned list will have
     * one cluster at the highest index of the outerlying arraylist, the second highest having two, and so on for each row of the main data frame.
     * @return An arraylist of arraylists, with each level of the outer array list representing a layer of cluster hierarchy.
     */
    public ArrayList<ArrayList<Cluster>> cluster() {
        ArrayList<Cluster> currentLevel = initializeClusters();
        TreeSet<DistanceData> distances = calculateDistances(currentLevel);
        return hierarchical(1, currentLevel, distances);
    }
    
    /**
     * Clusters a given data frame using a bottom up hierarchical approach. Returns an array list of arraylists, with each representing a layer of clusters.
     * These clusters also contain references to the clusters which were merged to create them, creating an underlying tree structure. The returned list will have
     * k cluster(s) at the highest index of the outerlying arraylist, the second highest having k - 1 cluster, and so on for each row of the main data frame.
     * @return An arraylist of arraylists, with each level of the outer array list representing a layer of cluster hierarchy.
     */
    public ArrayList<ArrayList<Cluster>> cluster(int k) {
        ArrayList<Cluster> currentLevel = initializeClusters();
        TreeSet<DistanceData> distances = calculateDistances(currentLevel);
        return hierarchical(k, currentLevel, distances);
    }
    
    /**
     * Does all of the while loop based computations of the hierarchical algorithm, producing k contained clusters in the highest heirarchy.
     * @param k the length of the top cluster list of the hierarchy.
     * @param currentLevel the array list of current levels
     * @param distances
     * @return
     */
    private ArrayList<ArrayList<Cluster>> hierarchical(int k, ArrayList<Cluster> currentLevel, TreeSet<DistanceData> distances) {
        ArrayList<ArrayList<Cluster>> clusters = new ArrayList<ArrayList<Cluster>>();
        clusters.add(currentLevel);
        int clusterLevel = 0;
        while (clusters.get(clusterLevel).size() != k) {                        //Merge closest pairs of clusters until all clusters are contained in one.
            currentLevel = new ArrayList<Cluster>();                            //Make a new blank array list for the next level of heirarchy.
            DistanceData closestClusters = distances.first();                   //Remove the distance data containing the closest clusters from the tree.
            Cluster mergedCluster = Cluster.merge                               //create merged cluster from the closest clusters.
                    (closestClusters.c1, closestClusters.c2, trainingData, distanceType);                                    //add the newly created cluster to the next level of the heiarchy.
            distances.remove(closestClusters);                                  //remove the distance data of the two clusters.
            ArrayList<DistanceData> toRemove = new ArrayList<DistanceData>();
            ArrayList<DistanceData> toAdd = new ArrayList<DistanceData>();
            HashSet<Cluster> currentLevelSet = new HashSet<Cluster>();
            for(DistanceData d : distances) {                                   //Update all distance data that contains references to the newly merged clusters
                if (d.containsClusterID(closestClusters.c1.clusterId)) {        //Calculate new distances to cluster, and add all looped over clusters to a hash set.
                    toRemove.add(d);
                    toAdd.add(new DistanceData(d.excludeCluster(closestClusters.c1.clusterId), mergedCluster));
                    currentLevelSet.add(d.excludeCluster(closestClusters.c1.clusterId));
                } else if (d.containsClusterID(closestClusters.c2.clusterId)) {
                    toRemove.add(d);
                    toAdd.add(new DistanceData(d.excludeCluster(closestClusters.c2.clusterId), mergedCluster));
                    currentLevelSet.add(d.excludeCluster(closestClusters.c2.clusterId));
                } 
            }
            currentLevel.addAll(currentLevelSet);   //Add all "Placeholder" clusters to the newly created "current level"
            currentLevel.add(mergedCluster);        //Add the merged cluster to the end of the list.
            distances.removeAll(toRemove);          //Remove all the distance data which contain references to the two merged clusters.
            distances.addAll(toAdd);                //Add the new distance data to the distance tree.
            clusters.add(currentLevel);             //Add the new cluster level to the main cluster heirarchy data structure
            clusterLevel++;
        }
        return clusters;
    }
    
    /**
     * Converts every row in the data frame into their own cluster.
     * @return an array list of containing a cluster for every row in the data frame.
     */
    private ArrayList<Cluster> initializeClusters() {
        ArrayList<Cluster> initialLevel = new ArrayList<Cluster>();
        for (int i = 0; i < trainingData.getNumRows(); i++) { //Initialize original cluster level, with all clusters being rows of the data frame
            Cluster c = new Cluster(trainingData.getRow_byIndex(i));
            c.addMember(trainingData.getRow_byIndex(i), 0, i);
            initialLevel.add(c);
        }
        return initialLevel;
    }
    
    /**
     * Used to initialize the distance tree. This calculates the distance between every cluster in the parameterized array list of clusters.
     * @param clusters the clusters to calculate the distance between.
     * @return a tree set of distance calculations.
     */
    private TreeSet<DistanceData> calculateDistances(ArrayList<Cluster> clusters) {
        TreeSet<DistanceData> distances = new TreeSet<DistanceData>(new Comparator<DistanceData>() { //data structure to hold the distance data
            @Override
            public int compare(DistanceData d1, DistanceData d2) {
                return Double.compare(d1.distance, d2.distance);
            }
        });
        for (int i = 0; i < clusters.size(); i++) { //initialize distances in the distance tree.
            for (int j = i + 1; j < clusters.size(); j++) {
                distances.add(new DistanceData(clusters.get(i), clusters.get(j)));
            }
        }
        return distances;
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
        public DistanceData (Cluster theC1, Cluster theC2) {
            c1 = theC1;
            c2 = theC2;
            distance = distanceType.distance(c1.centroid, c2.centroid);
        }
        
        /**
         * Retruns the cluster whos ID was not passed to the method.
         * @param theClusterId the cluster id of the cluster not to return.
         * @return the cluster whos ID was not passed to the method.
         */
        public Cluster excludeCluster(int theClusterId) {
            if (c1.clusterId == theClusterId)
                return c2;
            else if (c2.clusterId == theClusterId)
                return c1;
            else
                return null;  
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
