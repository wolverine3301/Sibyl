package metamorphose;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import clairvoyance.Distance;
import saga.Column;
import saga.DataFrame;
import saga.DoubleParticle;
import saga.IntegerParticle;
import saga.Row;

/**
 * Represents a cluster of rows. Stores all possible needed data for future calculations & expansion on KMeans based algorithms.
 * @author Cade Reynoldson  
 * @version 1.0
 */
public class Cluster {
    
    /** The number of clusters created. */
    public static int numberOfClusters = 0;
    
    /** The ID of the cluster instance. */
    public int clusterId;
    
    /** The centroid of the cluster */
    Row centroid;
    
    /** The cluster members with their distances to the centroid. */
    private HashMap<Row, Double> clusterMembers;
    
    /** The indexes of the cluster members in the original data frame. */
    private TreeSet<Integer> clusterMemberIndexes;
    
    /** Referece to a child of the cluster, used by hierarchical for tree structuring */
    private Cluster child1;
    
    /** Reference to a child of the cluster, used by hierarchical for tree structuring */
    private Cluster child2;
    
    /**
     * Creates a new cluster with a given row as the centroid. 
     * @param theCentroid the row to be the centroid of the cluster.
     */
    public Cluster(Row theCentroid) {
        numberOfClusters++;
        clusterId = numberOfClusters;
        centroid = new Row(theCentroid);
        clusterMembers = new HashMap<Row, Double>();
        clusterMemberIndexes = new TreeSet<Integer>();
        child1 = null;
        child2 = null;
    }
    
    /**
     * Creates a new cluster with a given row as the centroid. 
     * @param theCentroid the row to be the centroid of the cluster.
     * @param theChild1 one of the children of the cluster.
     * @param theChild2 one of the childrean of the cluster.
     */
    public Cluster(Row theCentroid, Cluster theChild1, Cluster theChild2) {
        numberOfClusters++;
        clusterId = numberOfClusters;
        centroid = new Row(theCentroid);
        clusterMembers = new HashMap<Row, Double>();
        clusterMemberIndexes = new TreeSet<Integer>();
        child1 = theChild1;
        child2 = theChild2;
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
    
    public void merge(Cluster c2, DataFrame trainingData) {
        for (Row r : c2.clusterMembers.keySet()) {
            clusterMembers.put(r, c2.clusterMembers.get(r));
        }
        clusterMemberIndexes.addAll(c2.clusterMemberIndexes);
        updateCentroidValues(trainingData);
    }
    
    public void updateMemberDistances(Distance distanceType) {
        for (Row r : clusterMembers.keySet()) {
            clusterMembers.put(r, distanceType.distance(centroid, r));
        }
    }
    
    /**
     * Returns the distance of the row from the centroid of the cluster.
     * @param theRow the row to calculate the distance from the centroid of the cluster.
     * @return the row's distance from the centroid of the cluster.
     */
    public double distanceFrom(Row theRow, Distance distanceType) {
        return distanceType.distance(centroid, theRow);
    }
    
    /**
     * Returns the distance of this cluster from another cluster.
     * @param theCluster the cluster to calculate the distance to.
     * @param distanceType the distance type to be used for calculation.
     * @return the distance of this cluster to another cluster.
     */
    public double distanceFrom(Cluster theCluster, Distance distanceType) {
        return distanceType.distance(centroid, theCluster.centroid);
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
    public void updateCentroidValues(DataFrame trainingData) {
        for (int i = 0; i < trainingData.numColumns; i++) {
            Column tempColumn = trainingData.getColumn_byIndex(i);
            if (tempColumn.type == 'd') 
                centroid.changeValue(i, new DoubleParticle(tempColumn.meanOfIndexes(clusterMemberIndexes)));
            else if (tempColumn.type == 'i')
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
        String str = "Cluster " + clusterId + ":\nCentroid: "  + centroid.toString() + "\nMembers: ";
        for (Row r : clusterMembers.keySet()) {
            str += "\n" + r.toString() + "Distance: " + clusterMembers.get(r) + "\n";
        }
        return str;
    }
    
    /**
     * Returns child 1 of the cluster.
     * @return child 1 of the cluster.
     */
    public Cluster getChild1() {
        return child1;
    }
    
    /**
     * Returns child 2 of the cluster.
     * @return child 2 of the cluster.
     */
    public Cluster getChild2() {
        return child2;
    }
    
    
    /**
     * Prints the children of the cluster.
     */
    public void printChildren() {
        if (child1 != null && child2 != null) {
            child1.printChildren();
            child2.printChildren();
        }
        System.out.println(toString());
    }
    
    
    /**
     * Returns true if two clusters are identical, false otherwise.
     * @param c1 the cluster to compare with.
     * @return true if the two clusters are identical, false otherwise.
     */
    public boolean equals(Cluster c1) {
        if (centroid.equals(c1.centroid) && clusterMembers.equals(c1.clusterMembers)
                && clusterMemberIndexes.equals(c1.clusterMemberIndexes))
            return true;
        else
            return false;
    }
    
    /**
     * Merges two clusters into one.
     * @param c1 cluster one to merge.
     * @param c2 cluster two to merge.
     * @param trainingData the original data set in which these clusters are comprised of.
     * @param distanceType the type of distance formula in which these clusters will be compared with.
     * @return a new cluster which is a product of two clusters.
     */
    public static Cluster merge(Cluster c1, Cluster c2, DataFrame trainingData, Distance distanceType) {
        Cluster c = new Cluster(c1.centroid, c1, c2);
        c.clusterMembers.putAll(c1.clusterMembers);
        c.clusterMembers.putAll(c2.clusterMembers);
        c.clusterMemberIndexes.addAll(c1.clusterMemberIndexes);
        c.clusterMemberIndexes.addAll(c2.clusterMemberIndexes);
        c.updateCentroidValues(trainingData);
        c.updateMemberDistances(distanceType);
        return c;
    }
    
}   

