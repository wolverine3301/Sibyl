package decisionTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Copy;
import dataframe.Row;
import info_gain.Gain;
import info_gain.GainInformation;
import log.Loggers;
import machinations.Model;
import particles.Particle;

public class DecisionNode {
    
    DataFrame dataFrame;
    
    Object valueOfSplit;
    
    String columnName;
    
    HashMap<Object, DecisionNode> children;
    
    Gain gainAlg;
    
    int targetIndex;
    
    /**
     * Creates a new decision node. 
     * @param df the df
     * @param gainAlg
     * @param targetIndex
     * @param value
     * @param columnName the name of the column - TODO: OPTIMIZE!!
     */
    public DecisionNode(DataFrame df, Gain gainAlg, int targetIndex, Object value, String columnName) {
        dataFrame = df;
        this.gainAlg = gainAlg;
        this.targetIndex = targetIndex;
        this.columnName = columnName;
        valueOfSplit = value; 
        children = new HashMap<Object, DecisionNode>();
    }
    
    /**
     * If this node doesn't contain any children, dataframe is split based on the unique values of 
     * the column which contains the highest gain out of the given gain algorithm. 
     */
    public void split() {
        if (children.isEmpty() && dataFrame != null) {
            ArrayList<GainInformation> gains = gainAlg.gain(targetIndex);
            if (gains == null) //If gains is null, no more splits can be made. 
                return;
            GainInformation maxGain = gains.get(0);
            Column c = dataFrame.categorical_columns.get((maxGain.getIndex()));
            Loggers.decTree_Logger.log(Level.INFO, "Decision Tree: Splitting on column - " + c.getName());
            String[] args = new String[3];
            args[0] = c.getName();
            args[1] = "=";
            for (Object o : c.getUniqueValues()) {
                args[2] = o.toString();
                System.out.println("Splitting - Current unique value: " + o + " - Array: " + Arrays.toString(args));
                DataFrame splitDf = DataFrame_Copy.acquire(dataFrame, args);
                if (splitDf == null) {
                    children.put(o, new DecisionNode(null, gainAlg.getGainAlgorithm(splitDf), targetIndex, o, c.getName()));
                } else {
                    children.put(o, new DecisionNode(splitDf, gainAlg.getGainAlgorithm(splitDf), targetIndex, o, c.getName()));
                }
            }
        } else if (dataFrame != null) {
            for (DecisionNode child : children.values())
                child.split();
        }
    }
    
    public DecisionNode nextNode(Row r) {
        int index = 0;
        if (children.containsKey(r.getParticle(index).getValue()))
            return children.get(r.getParticle(index).getValue());
        return this;
    }
    
    public DecisionNode nextNode(Row r, Model model) {
        return this;
    }
    
    /**
     * Prints a node. 
     */
    public void printNode() {
        if (!children.isEmpty()) {
            for (DecisionNode child : children.values())
                child.printNode();
        }
        System.out.println("Node value: " + valueOfSplit.toString() + " - Number of children: " + children.size());
    }
}
