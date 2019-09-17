package metamorphose;

import java.util.ArrayList;

import dataframe.DataFrame;
import dataframe.Row;
import distances.Distance;

public class BallTree {
    
    /** The data frame to convert to a ball tree */
    private DataFrame dataFrame;
    
    /** The type of distance for the class to use for calculation. */
    private Distance distanceType;
    
    /** The number of splits to take place within the tree. */
    private int k;
    
    public BallTree(DataFrame theDataFrame, Distance theDistanceType, int theK) {
        dataFrame = theDataFrame;
        distanceType = theDistanceType;
        k = theK;
    }
    
    public void split() {
        
    }
    
    
    
    
    
    
    
    
    
    
    
    private class Ball {
        
        private Row center;
        
        private ArrayList<Row> members;
        
        private double radius;
        
        private Ball leftChild;
        
        private Ball rightChild;
        
        public Ball(Row theCenter, double theRadius) {
            center = theCenter;
            theRadius = theRadius;
        }
        
        public void split() {
            
        }
        
    }
}
