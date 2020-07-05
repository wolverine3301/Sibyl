package decisionTree;

import java.util.ArrayList;
import java.util.HashMap;

import dataframe.DataFrame;
import dataframe.Row;
import info_gain.Gain;
import machinations.Model;

public class DecisionTree {
    
    Gain gainAlg;
    
    DecisionNode headNode;
	
    int targetIndex;
    
    public DecisionTree(DataFrame df, Gain gainAlg, int targetIndex) {
        headNode = new DecisionNode(df, gainAlg, targetIndex, "Original", 0);
	}
    
    /**
     * Splits the lowest leafs of the tree. 
     */
    public void split() {
        headNode.split();
    }
    
    public void printTree() {
        System.out.println("Tree of target index: " + targetIndex + " - Target Name: " + headNode.dataFrame.target_columns.get(targetIndex).getName());
        headNode.printNode();
    }
}
