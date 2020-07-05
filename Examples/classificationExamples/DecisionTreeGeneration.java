package classificationExamples;

import dataframe.DataFrame;
import decisionTree.DecisionTree;
import decisionTree.DecisionTreeModel;
import info_gain.Gain;
import info_gain.InformationGain;

public class DecisionTreeGeneration {
    public static void main(String[] args) {
        DataFrame df = DataFrame.read_csv("testfiles/iris.txt");
        df.setColumnType(0, 'T');
        DecisionTreeModel model = new DecisionTreeModel(df, 4, new InformationGain(df));
        model.generateTree(0);
        DecisionTree tree = model.trees.get(0);
        tree.printTree();
    }
}
