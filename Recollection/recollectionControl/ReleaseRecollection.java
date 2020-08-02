package recollectionControl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dataframe.DataFrame;
import machinations.Model;
import scorer.CrossValidation;
import scorer.Evaluate;


public class ReleaseRecollection {
	LinkedList<CrossValidation> EVALUATION_QUEUE = new LinkedList<>();
	
	private List<ArrayList<DataFrame>> memories;
	private Model model;
	private Evaluate evaluate;
	private int capacity = 1000;
	private boolean finish=false;
	
	public ReleaseRecollection(List<ArrayList<DataFrame>> memories, Model model,Evaluate ev) {
		this.memories = memories;
		this.model = model;
		this.evaluate = ev;
	}
	public void produce() throws InterruptedException { 
			
			int cnt = 0;
			//DataFrame k;
			for(ArrayList<DataFrame> i : memories) {
				
	        //while (true) { 
				for(DataFrame k : i) {
		            synchronized (this){ 
		                // producer thread waits if list is full
		                while (EVALUATION_QUEUE.size() == capacity) 
		                    wait(); 
		                
		                
		            	CrossValidation cv = new CrossValidation(k,5, model);
		            	//cv.printOverAllScore();
		            	System.out.println("SIZE: "+EVALUATION_QUEUE.size());
		            	// to insert the crossvals in the list 
		            	EVALUATION_QUEUE.add(cv);
		            	cnt++;
		                // notifies the consumer thread that 
		                // now it can start consuming 
		                notify(); 
	
		                //Thread.sleep(10); 
	            	}
	            } 
	        }
			finish=true;
	    } 
		public void consume() throws InterruptedException { 
	        while (!finish) { 
	            synchronized (this){ 
	                // consumer thread waits while list 
	                // is empty 
	                while (EVALUATION_QUEUE.size() == 0) 
	                    wait(); 
	
	                // to retrive the ifrst job in the list 
	                evaluate.evaluation(EVALUATION_QUEUE.removeFirst());
	                // Wake up producer thread 
	                notify(); 
	
	                // and sleep 
	                Thread.sleep(10); 
	            } 
	        } 
	    } 
}
