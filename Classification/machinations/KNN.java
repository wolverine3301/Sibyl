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
        super.setTrain(theDataFrame);
        distanceFunction = theDistanceFunction;
        k = theK;
    }
    
    /**
     * Uses KNN to calculate / estimate the missing value of a passed row, and returns the probability of a value in a hashmap, 
     * formatted as 0.50 for 50% probability, and so on. The row passed MUST contain one less entry than the test data frame's rows; with the 
     * missing entry being the desired prediction variable. The name of the column to be predicted MUST have its column type marked as "target".
     * @param row The row, or set of values on which predictions will be based on.
     * @return A hashmap of predictions and probabilities, with the key of the hashmap being the prediction, and the value being it's given probability.
     */
    @Override
    public HashMap<String, HashMap<Object, Double>> probability(Row row) {
        HashMap<String, HashMap<Object, Double>> probabilityMap = new HashMap<String, HashMap<Object, Double>>();
        //Priority queue which contains the distance data from the target row to every other row.
        PriorityQueue<DistanceParticle> neighbors = new PriorityQueue<DistanceParticle>(trainDF_variables.getNumRows(), new Comparator<DistanceParticle>() {
            @Override
            public int compare(DistanceParticle p1, DistanceParticle p2) {
                return Double.compare(p1.getValue(), p2.getValue());
            }
        });
        //Calculate the distances from the target row to each row in the target variables dataframe.
        for (int i = 0; i < trainDF_variables.getNumRows(); i++)
            neighbors.add(new DistanceParticle(distanceFunction.distance(row, trainDF_variables.getRow_byIndex(i)), i, distanceFunction.distanceType));
        HashMap<String, HashMap<Object, Double>> objectCount = new HashMap<String, HashMap<Object, Double>>(); //Initialize the hashmap for instances of an object (prediction)
        //Create a hashmap for each column name 
        for (String columnName : trainDF_targets.getColumnNames()) { //Create a hashmap for each column name 
            probabilityMap.put(columnName, new HashMap<Object, Double>());
            objectCount.put(columnName, new HashMap<Object, Double>());
        }
        //load k-predictions into temporary hash map. Initializes k-predictions for each target. 
        for (int i = 0; i < k; i++) { 
            Row currentPrediction = trainDF_targets.getRow_byIndex(neighbors.remove().distanceToIndex); //the current row 
            for (int j = 0; j < trainDF_targets.getNumColumns(); j++) {
                String columnName = trainDF_targets.getColumnNames().get(j); //Name of the current target row
                Object currentValue = currentPrediction.getParticle(j).getValue(); //The object value of the current prediction
                if (objectCount.get(columnName).containsKey(currentValue)) { // If the value is already contained in the object count hashmap, add one to its value.
                    double currentCount = objectCount.get(columnName).get(currentValue);
                    objectCount.get(columnName).put(currentValue, currentCount + 1.0);
                } else { // Else initialize value in hashmap to 1.
                    objectCount.get(columnName).put(currentValue, 1.0);
                }
            }
        }
        //Initialize the probabilites of each value. 
        for (String target : objectCount.keySet()) {
            HashMap<Object, Double> tempProbs = new HashMap<Object, Double>();
            for (Object value: objectCount.get(target).keySet()) {
                tempProbs.put(value, (objectCount.get(target).get(value) / k));
            }
            probabilityMap.put(target, tempProbs);
        }
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
    public HashMap<String , ArrayList<Object>> predictDF(DataFrame testDf) {
        HashMap<String, ArrayList<Object>> predictions = new HashMap<String, ArrayList<Object>>();
        for (int i = 0; i < trainDF_targets.getNumColumns(); i++ ) {
            PriorityQueue<DistanceParticle> neighbors = new PriorityQueue<DistanceParticle>(trainDF_variables.getNumRows(), new Comparator<DistanceParticle>() {
                @Override
                public int compare(DistanceParticle p1, DistanceParticle p2) {
                    return Double.compare(p1.getValue(), p2.getValue());
                }
            });
            for (int j = 0; j < trainDF_variables.getNumRows(); j++)
                neighbors.add(new DistanceParticle(distanceFunction.distance(row, trainDF_variables.getRow_byIndex(j)), j, distanceFunction.distanceType));
            ArrayList<Object> currPredictions = new ArrayList<Object>(k);
            for (int j = 0; i < k; i++) {
                currPredictions.add(trainDF_variables.getColumn(j).getParticle(neighbors.remove().distanceToIndex).getValue());
            }
            predictions.put(trainDF_targets.getColumnNames().get(i), currPredictions);
        }
        return predictions;
    } 
}
