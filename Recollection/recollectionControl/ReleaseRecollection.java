package recollectionControl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dataframe.DataFrame;
import machinations.Model;
import scorer.CrossValidation;
import scorer.Evaluate;


public class ReleaseRecollection implements Runnable {
    
    /** Queue containing cross validation evaluations. */
    public LinkedList<CrossValidation> EVALUATION_QUEUE = new LinkedList<>();
    
    /** List of dataframes to evaluate. */
	private List<ArrayList<DataFrame>> memories;
	
	private Model model;
	private Evaluate evaluate;
	private int capacity = 1000;
	private boolean producing;
	Thread[] producers;
	Thread consumer;
	
	public ReleaseRecollection(List<ArrayList<DataFrame>> memories, Model model, Evaluate ev, int numProducers) {
		this.memories = memories;
		this.model = model;
		this.evaluate = ev;
		producing = true;
		producers = new Thread[numProducers];
	}
	
    @Override
    public void run() {
        for (int i = 0; i < producers.length; i++) {
            producers[i] = new Thread(new RecollectionProducer(this));
            producers[i].start();
        }
        consumer = new Thread(new RecollectionConsumer(this));
        consumer.start();
        for (int i = 0; i < producers.length; i++) {
            try {
                producers[i].join();
            } catch (InterruptedException e) {
                System.out.println("Error joining producer thread: ");
                e.printStackTrace();
            }
        }
        try {
            consumer.join();
        } catch (InterruptedException e) {
            System.out.println("Error joining consumer thread: ");
            e.printStackTrace();
        }
        
    }
	
	public int getCapacity() {
	    return capacity;
	}
	
	public void doneProducing() {
	    synchronized (EVALUATION_QUEUE) {
	        producing = false;
	    }
	}
	
	/**
	 * Indicates if there are producers still running. 
	 * @return true if there are producers still running, false otherwise. 
	 */
	public boolean isProducing() {
	    return producing;
	}
	
//	public void produce() throws InterruptedException { 
//		
//		int cnt = 0;
//		//DataFrame k;
//		for(ArrayList<DataFrame> i : memories) {
//			
//        //while (true) { 
//			for(DataFrame k : i) {
//	            synchronized (this){ 
//	                // producer thread waits if list is full
//	                while (EVALUATION_QUEUE.size() == capacity) 
//	                    wait(); 
//	                
//	                
//	            	CrossValidation cv = new CrossValidation(k,5, model);
//	            	//cv.printOverAllScore();
//	            	System.out.println("SIZE: "+EVALUATION_QUEUE.size());
//	            	// to insert the crossvals in the list 
//	            	EVALUATION_QUEUE.add(cv);
//	            	cnt++;
//	                // notifies the consumer thread that 
//	                // now it can start consuming 
//	                notify(); 
//
//	                //Thread.sleep(10); 
//            	}
//            } 
//        }
//		producing = false;
//    } 
	
//	public void consume() throws InterruptedException { 
//	    while (producing) {
//	        while (queueIsEmpty()) //While the queue is empty, wait. 
//	            wait();
//	        evaluate.evaluation(EVALUATION_QUEUE.removeFirst());
//	    }
//	    
//	    
//        while (!finish) { 
//            synchronized (this){ 
//                // consumer thread waits while list 
//                // is empty 
//                while (EVALUATION_QUEUE.size() == 0) 
//                    wait(); 
//
//                // to retrive the ifrst job in the list 
//                evaluate.evaluation(EVALUATION_QUEUE.removeFirst());
//                // Wake up producer thread 
//                notify(); 
//
//                // and sleep 
//                Thread.sleep(10); 
//            } 
//        } 
//    } 
	
	/**************************************************
	 *             EVALUATION QUEUE METHODS           *
	 **************************************************/
	
	/**
	 * Returns true/false based on if there is a value contained in the evaluation queue.  
	 * @return 
	 */
	public boolean queueIsEmpty() {
	    synchronized (EVALUATION_QUEUE) {
	        return EVALUATION_QUEUE.size() == 0;
	    }
	}
	
	/**
	 * Indicates if the evaluation queue is full. 
	 * @return return true/false if the evaluation queue is full. 
	 */
	public boolean queueIsFull() {
       synchronized (EVALUATION_QUEUE) {
           return EVALUATION_QUEUE.size() >= 1000;
        }
	}
	
	/**
	 * Synchronized on evaluation queue for fetching a value from it. 
	 * - NOTE: CURRENTLY ONLY WORKS WITH ONE CONSUMER!
	 * @return the next value in the queue. 
	 */
	public CrossValidation getNext_Queue() {
	    return EVALUATION_QUEUE.removeFirst();
	}
	
	public void addToQueue(CrossValidation cv) {
	    synchronized (EVALUATION_QUEUE) {
	        EVALUATION_QUEUE.add(cv);
	    }
	}
	
	public ArrayList<DataFrame> getNext_Memory() {
	    ArrayList<DataFrame> next = null;
	    synchronized (memories) {
	        if (!memories.isEmpty())
	            next = memories.remove(0);
	    }
	    return next;
	}
	
	public void evaluate(CrossValidation cv) {
	    evaluate.evaluation(cv);
	}
	
	public Model getModel() {
	    return model;
	}		
}
