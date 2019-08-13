package clairvoyance;

import java.util.Arrays;

import saga.*;

public abstract class Distance{

	// is there an easy way to make this generic and work easily with saga?
	// such as produce the same results if plugged in either a dataframe or a 2D int[]
	public abstract double distance(Row r1, Row r2);
	
	/**
	 * Creates a distance matrix given a data frame, and distance formula to run off of.
	 * @param theDataFrame
	 * @param distanceType
	 * @return
	 */
	public static DataFrame<Double> distance_matrix(DataFrame theDataFrame, Distance distanceType) {
	    DataFrame<Double> dataFrame = new DataFrame<Double>();
	    System.out.println("PASSED DATA FRAME ROWS: " + theDataFrame.numRows);
	    for (int i = 0; i < theDataFrame.numRows; i++) {
	        Row currentRow = theDataFrame.getRow_byIndex(i);
	        Double[] tempArray = new Double[currentRow.row.size()];
	        for (int j = 0; j < theDataFrame.numRows; j++) {
	            double distance = distanceType.distance(currentRow, theDataFrame.getRow_byIndex(j));
	            System.out.println(distance);
	            tempArray[j] = distance;
	        }
	        System.out.println(Arrays.toString(tempArray));
	        dataFrame.addRowFromArray(tempArray);
	    }
	    return dataFrame;
	}

}
