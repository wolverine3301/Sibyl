package armorment;

import java.util.ArrayList;

import bayes.NaiveBayes2;
import knn.KNN;
import machinations.Model;

public class Armorment {
	
	private static ArrayList<Model> models;
	
	public Armorment() {
		models = new ArrayList<Model>();
	}
	public void initiallize() {
		
	}
	public void addNaiveBayes() {
		NaiveBayes2 nb = new NaiveBayes2();
		models.add(nb);
	}
	public void addKNN(int init_N,int limit_N) {
		for(int i = init_N; i <= limit_N; i++) {
			KNN knn = new KNN();
			knn.setK(i);
			models.add(knn);
		}
	}
	public void addDecisionTree() {
		
	}
	public ArrayList<Model> getModels() {
		return models;
	}
	public boolean isEmpty() {
		return this.models.isEmpty();
	}

}
