package frequentPattern;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private List<Node> children = new ArrayList<>(); //list of children
	private Node parent; //parent node
	private Object item; //the unique item
	private Integer count; //count of item / set
	/**
	 * construct a node
	 * @param parent
	 */
	public Node(Node parent,Object item,Integer c) {
		this.parent = parent;
		this.item = item;
		this.count = c;
	}
	public void iterrateCount() {
		this.count = count++;
	}
	public Node getParent() {
		return this.parent;
	}
	public List<Node> getChildren(){
		return this.children;
	}
	public Object getItem() {
		return this.item;
	}
	public int getCount() {
		return this.count;
	}
	public void print() {
		System.out.println(item+","+count);
	}
	
}
