package Controllers;

import java.util.List;

import armorment.Armorment;
import dataframe.DataFrame;
import machinations.Model;
import scorer.CrossValidation;
import scorer.Evaluate;
import scorer.Metric;

public class Evaluate_Controller {
	
	public static DataFrame df;
	public static Armorment models;
	
	public static void setDF(DataFrame df) {
		Evaluate_Controller.df = df;
	}
	public static void setArmorment(Armorment m) {
		Evaluate_Controller.models = m;
	}

}
