package machinations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import dataframe.DataFrame;
import dataframe.Row;
import distances.Distance;
import particles.DistanceParticle;
import particles.Particle;

/**
 * KNN class, contains the algorithm and data needed to compute k nearest neighbors of a given row.
 * @author Cade Reynoldson
 * @version 1.0
 */
public class KNN extends Model {
    
    /** The amount of predictions to be returned. */
    public int k;
    
    /** The distance function knn will use. */
    Distance distanceFunction;
    
    /**
     * Creates a new KNN object, which can be used for calculating using the k nearest neighbors algorithm.
     * @param theDataFrame the data frame which will be the "training" data set.
     * @param theDistanceFunction the distance function to calculate KNN with.
     * @param theK the amount of predictions to be returned upon completion of the algorithm.
     */
    public KNN(DataFrame theDataFrame, Distance theDistanceFunction, int theK) {
        super(theDataFrame);
        distanceFunction = theDistanceFunction;
        k = theK;
    }
    
    /**
     * Uses KNN to calculate / estimate the missing value of a passed row, and returns the probability of a value in a hashmap, 
     * formatted as 50.0 for 50% probability, and so on. The row passed MUST contain one less entry than the test data frame's rows; with the 
     * missing entry being the desired prediction variable. The name of the column to be predicted MUST have its column type marked as "target".
     * @param row The row, or set of values on which predictions will be based on.
     * @return A hashmap of predictions and probabilities, with the key of the hashmap being the prediction, and the value being it's given probability.
     */
    @Override
    public HashMap<Object, Double> probability(Row row) {
        int taggedColumnIndex = findTaggedColumnIndex();
        if (taggedColumnIndex == -1)
            throw new IllegalArgumentException("No tagged column was detected.");
        DataFrame knnFrame = trainDF.exclude(taggedColumnIndex);
        PriorityQueue<DistanceParticle> neighbors = new PriorityQueue<DistanceParticle>(knnFrame.numRows, new Comparator<DistanceParticle>() {
            @Override
            public int compare(DistanceParticle p1, DistanceParticle p2) {
                return Double.compare(p1.getValue(), p2.getValue());
            }
        });
        for (int i = 0; i < knnFrame.numRows; i++)
            neighbors.add(new DistanceParticle(distanceFunction.distance(row, knnFrame.getRow_byIndex(i)), i, distanceFunction.distanceType));
        HashMap<Object, Double> probabilityMap = new HashMap<Object, Double>();
        HashMap<Object, Double> objectCount = new HashMap<Object, Double>();
        for (int i = 0; i < k; i++) { //load predictions into temporary hash map
            Object currentPrediction = trainDF.getColumn_byIndex(taggedColumnIndex).getParticle_atIndex(neighbors.remove().distanceToIndex).getValue();
            if (objectCount.containsKey(currentPrediction)) {
                double currentValue = objectCount.get(currentPrediction);
                objectCount.put(currentPrediction, currentValue + 1.0);
            } else {
                objectCount.put(currentPrediction, 1.0);
            }
        }
        for (Object key : objectCount.keySet())  // loop through predictions and calculate percent probablility of each prediction
            probabilityMap.put(key, (objectCount.get(key) / k) * 100.0);
        return probabilityMap;
    }

    /**
     * KNN algorithm. Returns k predictions in the form of an ArrayList of ArrayLists. The row passed MUST contain n-less entries than the test data frame
     * on which computations will be calculated on, with the missing entries being the desired prediction variable, since this allows for proper
     * distance measuring between rows. The name of the column to be predicted MUST have it's column type marked as "T".
     * @param row The row used for prediction.
     * @return an array of predictions, with the lowest index being the most likely, and highest index being the least likey (based on the given k value).
     */
    @Override
    public ArrayList<ArrayList<Particle>> predict(Row row) {
        ArrayList<ArrayList<Particle>> predictions = new ArrayList<ArrayList<Particle>>();
        for (int i = 0; i < trainDF_targets.numColumns; i++ ) {
            PriorityQueue<DistanceParticle> neighbors = new PriorityQueue<DistanceParticle>(trainDF_variables.numRows, new Comparator<DistanceParticle>() {
                @Override
                public int compare(DistanceParticle p1, DistanceParticle p2) {
                    return Double.compare(p1.getValue(), p2.getValue());
                }
            });
            for (int j = 0; j < trainDF_variables.numRows; j++)
                neighbors.add(new DistanceParticle(distanceFunction.distance(row, trainDF_variables.getRow_byIndex(j)), j, distanceFunction.distanceType));
            ArrayList<Particle> currPredictions = new ArrayList<Particle>(k);
            for (int j = 0; i < k; i++) {
                currPredictions.add(trainDF_variables.getColumn_byIndex(j).getParticle_atIndex(neighbors.remove().distanceToIndex));
            }
            predictions.add(currPredictions);
        }
        return predictions;
    } 
    
    /**
     * Returns the index of the tagged column for prediction.
     * @return the index of the tagged column for prediction.
     */
    private int findTaggedColumnIndex() {
        for (int i = 0; i < trainDF.numColumns; i++) {
            if (trainDF.getColumn_byIndex(i).type.toLowerCase().equals("target")) {
                return i;
            }
        }
        return -1;
    }
}
