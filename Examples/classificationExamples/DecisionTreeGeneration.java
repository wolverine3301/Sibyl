package classificationExamples;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;

import dataframe.DataFrame;
import decisionTree.DecisionTree;
import decisionTree.DecisionTreeModel;
import info_gain.Gain;
import info_gain.InformationGain;
import log.Loggers;

public class DecisionTreeGeneration {
    public static void main(String[] args) {
        DataFrame df = DataFrame.read_csv("testfiles/iris.txt");
        df.setColumnType(0, 'T');
        Loggers.decTree_Logger.addHandler(Loggers.consoleHandler);
        DecisionTreeModel model = new DecisionTreeModel(df, 4, new InformationGain(df));
        model.generateTree(0);
        DecisionTree tree = model.trees.get(0);
        tree.printTree();
    }
}
