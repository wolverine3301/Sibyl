package metamorphose;

import java.util.ArrayList;

import clairvoyance.Distance;
import saga.DataFrame;

public class Hierachical {
    
    private DataFrame trainingData;
    
    private DataFrame distanceMatrix;
    
    private Distance distanceType;
    
    public Hierachical(DataFrame theTrainingData, Distance theDistanceType) {
        trainingData = theTrainingData.;
        distanceType = theDistanceType;
        distanceMatrix = Distance.distance_matrix(trainingData, distanceType);
    }
    
    public void cluster() {
        //Bottom up approach
        ArrayList<ArrayList<Cluster>> clusters = new ArrayList<ArrayList<Cluster>>();
        int clusterLevel = 0;
        ArrayList<Cluster> currentLevel = new ArrayList<Cluster>();
        //Initialize original set, with all clusters being rows of the data frame
        for (int i = 0; i < trainingData.numRows; i++) {
            Cluster c = new Cluster(trainingData.getRow_byIndex(i));
            c.addMember(trainingData.getRow_byIndex(i), 0, i);
        }
        //Merge closest pairs of clusters until all clusters are contained in one
        while (clusters.get(clusters.size() - 1).size() != 1) {
            PriorityQueue<Double> distances = new PriorityQueue<Double>();
            for ()
        }
        //Merge closest pairs of clusters until all clusters are contained in one
    }
    
    
    
}
