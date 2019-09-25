package sagatesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import dataframe.DataFrame;
import dataframe.DataFrameTools;

class DataFrameTesting {
    
    /**
     * Tests the lengths of the created data frame.
     */
    @Test
    void testLengthsCreation() {
        DataFrame testFrame = new DataFrame();
        testFrame.loadcsv("testfiles/testing.csv");
        assertEquals(6, testFrame.getNumColumns(), "Data frame has incorrect number of columns upon creation.");
        assertEquals(6, testFrame.getNumRows(), "Data frame has incorrect number of rows upon creation");
        List<Integer> columnIndexes = new ArrayList<Integer>();
        columnIndexes.add(0);
        columnIndexes.add(1);
        columnIndexes.add(2);
        DataFrame temp = DataFrameTools.shallowCopy_columnIndexes(testFrame, columnIndexes);
        assertEquals(3, temp.getNumColumns(), "Data frame has incorrect number of columns upon copy.");
        assertEquals(6, temp.getNumRows(), "Data frame has incorrect number of rows upon copy.");
    }
    
    @Test
    void testLengthsColumnCopy() {
        DataFrame testFrame = new DataFrame();
        testFrame.loadcsv("testfiles/testing.csv");
        List<Integer> columnIndexes = new ArrayList<Integer>();
        columnIndexes.add(0);
        columnIndexes.add(1);
        columnIndexes.add(2);
        DataFrame temp = DataFrameTools.shallowCopy_columnIndexes(testFrame, columnIndexes);
        assertEquals(3, temp.getNumColumns(), "Data frame has incorrect number of columns upon copy.");
        assertEquals(6, temp.getNumRows(), "Data frame has incorrect number of rows upon copy.");
    }
    
    @Test
    void testLengthsRowIndexCopy() {
        DataFrame testFrame = new DataFrame();
        testFrame.loadcsv("testfiles/testing.csv");
        List<Integer> rowIndexes = new ArrayList<Integer>();
        rowIndexes.add(0);
        rowIndexes.add(1);
        rowIndexes.add(2);
        DataFrame temp = DataFrameTools.shallowCopy_rowIndexes(testFrame, rowIndexes);
        assertEquals(6, temp.getNumColumns(), "Data frame has incorrect number of columns upon copy.");
        assertEquals(3, temp.getNumRows(), "Data frame has incorrect number of rows upon copy.");
    }
}
