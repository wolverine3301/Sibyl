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
    
    /** The centroid of the cluster */
    Row centroid;
    
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
    public double distanceFrom(Row theRow, Distance distanceType) {
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
    public void updateCentroidValues(DataFrame trainingData) {
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

