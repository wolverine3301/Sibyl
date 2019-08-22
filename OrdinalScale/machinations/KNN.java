package machinations;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.TreeSet;

import clairvoyance.Distance;
import saga.DataFrame;
import saga.DistanceParticle;
import saga.Row;

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
    
    @Override
    public HashMap<Object, Double> probability(Row row) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * KNN algorithm. Returns k predictions in the form of an object array. The row passed MUST contain one less entry than the test data frame
     * on which computations will be calculated on, with the missing entry being the desired prediction variable, since this allows for proper
     * distance measuring between rows. The name of the column to be predicted MUST have it's column type marked as "target".
     * @param row The row used for prediction.
     * @return an array of predictions, with the lowest index being the most likely, and highest index being the least likey.
     */
    @Override
    public Object predict(Row row) {
        int taggedColumnIndex = findTaggedColumnIndex();
        if (taggedColumnIndex == -1)
            throw new IllegalArgumentException("No tagged column was detected.");
        DataFrame knnFrame = trainingDataFrame.exclude(taggedColumnIndex);
        PriorityQueue<DistanceParticle> neighbors = new PriorityQueue<DistanceParticle>(knnFrame.numRows, new Comparator<DistanceParticle>() {
            @Override
            public int compare(DistanceParticle p1, DistanceParticle p2) {
                return Double.compare(p1.getValue(), p2.getValue());
            }
        });
        for (int i = 0; i < knnFrame.numRows; i++)
            neighbors.add(new DistanceParticle(distanceFunction.distance(row, knnFrame.getRow_byIndex(i)), i, distanceFunction.distanceType));
        Object[] predictions = new Object[k];
        for (int i = 0; i < k; i++) {
            predictions[i] = trainingDataFrame.getColumn_byIndex(taggedColumnIndex).getParticle_atIndex(neighbors.remove().distanceToIndex);
        }
        return predictions;
    } 
    
    /**
     * Returns the index of the tagged column for prediction.
     * @return the index of the tagged column for prediction.
     */
    private int findTaggedColumnIndex() {
        for (int i = 0; i < trainingDataFrame.numColumns; i++) {
            if (trainingDataFrame.getColumn_byIndex(i).type.toLowerCase().equals("target")) {
                return i;
            }
        }
        return -1;
    }
}
