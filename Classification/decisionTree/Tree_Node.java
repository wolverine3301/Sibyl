package decisionTree;

import java.util.ArrayList;

import dataframe.Column;
import dataframe.DataFrame;

public class Tree_Node {
	
	protected ArrayList<String> edges;
	protected Column col;
	DataFrame[] re;
	public Tree_Node(DataFrame df, Column split) {
		this.col = split;
		this.edges = new ArrayList<String>();
	}
}
