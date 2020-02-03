package frequentPattern;

import java.util.TreeMap;

import dataframe.DataFrame;

public class FrequentPatternTree {
	private DataFrame df; //the data frame
	private int minimum_support; //to be 'frequent' a item/set of items ,it must occur at least this many times
	private TreeMap<Object, Integer> tree_map;
	private Node root;
	/**
	 * Frequent pattern tree for each row of a data frame
	 * @param df
	 */
	public FrequentPatternTree(DataFrame df) {
		this.df = df;
		this.minimum_support = 2;
		//this.tree_map = new TreeMap<Object, Integer>();
		initialize();
		printTree(root);
	}
	 private Node addChild(Node parent, Object item,Integer cnt) {
		   Node node = new Node(parent,item,cnt);
		   parent.getChildren().add(node);
		   return node;
		 }
		 
	public void printTree(Node node) {
		node.print();
			for (Node each : node.getChildren()) {
				printTree(each);
			}
	}
	private void initialize() {
		this.root = new Node(null,null,null);
		
		for(int i = 0; i < this.df.getNumColumns(); i++) {
			for(Object j : df.getColumn(i).getUniqueValueCounts().keySet()) {
				if(df.getColumn(i).getUniqueValueCounts().get(j) >= this.minimum_support) {
					Node childNode = addChild(root,j,df.getColumn(i).getUniqueValueCounts().get(j));
					//System.out.println(j);
				}
			}
			
		}
	}
	

}
