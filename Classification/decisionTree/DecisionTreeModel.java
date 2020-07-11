package decisionTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import dataframe.DataFrame;
import dataframe.Row;
import info_gain.Gain;
import machinations.Model;

public class DecisionTreeModel extends Model {
    
    /** The maximum number of iterations to allow. */
    private int maxIterations;
    
    /** The original dataframe to generate trees with. */
    private DataFrame originalDf;
    
    /** The gain method to generate decision trees with. */
    private Gain gainMethod;
    
    /** Decision trees mapped to their target index. */
    public HashMap<Integer, DecisionTree> trees;
    
    
    public DecisionTreeModel(DataFrame df, int maxIterations, Gain gainMethod) {
        originalDf = df;
        this.maxIterations = maxIterations;
        this.gainMethod = gainMethod;
        trees = new HashMap<Integer, DecisionTree>();
    }
    
    public void generateTree(int targetIndex) {
        DecisionTree tree = new DecisionTree(originalDf, gainMethod, targetIndex);
        tree.split();
        trees.put(targetIndex, tree);
    }
    
    /**
     * TEST ALGORITHM - Iterates through the tree using the values predicted VIA a calulation of a model. 
     * @param row the row to predict. 
     * @param model the model to use for iteration through the tree. 
     * @param onlyLeaf indicates if each iteration through the tree should be based on the calculation of the model or only the final prediction. 
     * @return the prediction of the model. 
     */
    public Object predict(Row row, Model model, boolean onlyLeaf, int targetIndex) {
        if (onlyLeaf) {
            DecisionNode currentNode = trees.get(targetIndex).headNode;
            while (currentNode != null) {
                DecisionNode temp = currentNode.nextNode(row);
                if (temp != currentNode)  //If the reference to temp is the same as current node, break the loop.
                    break;
                else
                    currentNode = temp;
            }
        } else {
            while (currentNode != null) {
                
            }
        }
    }
    
    
    @Override
    public HashMap<String, HashMap<Object, Double>> probability(Row row) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Object predict(Row row) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public HashMap<String, ArrayList<Object>> predictDF(DataFrame testDF) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void initiallize() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void saveModel(String fileName) {
        // TODO Auto-generated method stub
        
    }


}