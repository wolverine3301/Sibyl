package knn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Copy;
import dataframe.Row;
import distances.Distance;
import distances.Euclidean;
import distances.Hamming;
import distances.Manhattan;
import machinations.Model;
import particles.DistanceParticle;

/**
 * KNN class, contains the algorithm and data needed to compute k nearest neighbors of a given row.
 * @author Cade Reynoldson
 * @version 1.5
 */
public class KNN extends Model {
    
    /**
     * 
     */
    private static final long serialVersionUID = 9134287604056743290L;

    /** The amount of predictions to be returned. */
    public int k = 30;
    
    /** The distance function knn will use. */
    Distance distanceFunction;
    
    private DataFrame dataFrame;
    
    public KNN() {
        distanceFunction = null;
    }
    
    /**
     * Initializes the dataframe. 
     */
    public void initiallize() {
        if (distanceFunction == null)
            distanceFunction = new Manhattan(); //Default distance function is manhattan distance. 
        k = 5;
    }
    
    /**
     * Changes the distance function that this instance of KNN uses. 
     * @param distanceFunction the new distance function to use.
     */
    public void setDistanceFunction(Distance distanceFunction) {
        this.distanceFunction = distanceFunction;
    }
    
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
     * KNN algorithm. Returns k predictions in the form of an HashMap, keys consisting of Strings (the name of the column being predicted) with values being ArrayLists. 
     * This is used on a DataFrame (testDf), preferrably 
     * @param row The row used for prediction.
     * @return an array of predictions, with the lowest index being the most likely, and highest index being the least likey (based on the given k value).
     */
    @Override
    public HashMap<String , ArrayList<Object>> predictDF(DataFrame testDf) {
        HashMap<String, ArrayList<Object>> predictions = new HashMap<String, ArrayList<Object>>();
        for (Column c : trainDF_targets.columns) { //master outside loop
            String targetName = c.getName();
            ArrayList<Object> values = new ArrayList<Object>();
            for (int i = 0; i < testDf.getNumRows(); i++) {
                values.add(predict(targetName, testDf.getRow_byIndex(i)));
            }
            predictions.put(targetName, values);
        }
        return predictions;
    }

	@Override
	public void saveModel(String fileName) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Predicts the target value of a row. 
	 */
    @Override
    public Object predict(String target, Row row) {
        PriorityQueue<DistanceParticle> neighbors = new PriorityQueue<DistanceParticle>(trainDF_variables.getNumRows(), new Comparator<DistanceParticle>() { //Neighbors priority queue. Quick access to closest neighbors.  
            @Override
            public int compare(DistanceParticle p1, DistanceParticle p2) {
                return Double.compare(p1.getValue(), p2.getValue());
            }
        });
        for (int i = 0; i < trainDF_variables.getNumRows(); i++) {
            neighbors.add(new DistanceParticle(distanceFunction.distance(row, trainDF_variables.getRow_byIndex(i)), i, distanceFunction.distanceType));
        }
        HashMap<Object, Integer> closestNeighbors = new HashMap<Object, Integer>();
        for (int i = 0; i < k; i++) { //Put k closest neighbors into hashmap, keep count. 
            Object value = trainDF_targets.getColumn_byName(target).getParticle(neighbors.remove().distanceToIndex).getValue();
            if (closestNeighbors.containsKey(value))
                closestNeighbors.put(value, closestNeighbors.get(value) + 1);
            else
                closestNeighbors.put(value, 1);
        }
        int mostOccuring = Integer.MIN_VALUE;
        Object value = null;
        for (Object o : closestNeighbors.keySet()) {
            if (closestNeighbors.get(o) > mostOccuring) {
                value = o;
                mostOccuring = closestNeighbors.get(o);
            }
        }
        return value;
    }

    @Override
    public Model copy() {
        KNN copy = new KNN();
    } 
}
