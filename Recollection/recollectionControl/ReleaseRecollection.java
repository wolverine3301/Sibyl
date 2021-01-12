package recollectionControl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import dataframe.DataFrame;
import log.Loggers;
import machinations.Model;
import scorer.CrossValidation;
import scorer.Evaluate;

/**
 * RELEASE RECOLLECTION!
 * Runs cross validation on multiple "memories" generated by recollection. 
 * @author Cade Reynoldson
 * @author Logan Collier
 * @version 1.0
 */
public class ReleaseRecollection implements Runnable {
    
    /** Queue containing cross validation evaluations. */
    public LinkedList<CrossValidation> EVALUATION_QUEUE = new LinkedList<>();
    
    /** List of dataframes to evaluate. */
	private List<ArrayList<DataFrame>> memories;
	
	/** The model in which we will take the world with. */
	private Model model;
	
	/** The evaluation of that model. */
	private Evaluate evaluate;
	
	/** The capacity of the "bounded buffer" */
	private int capacity = 1000;
	
	/** Indicates whether there is producer threads still producing cross validations. */
	private boolean producing;
	
	/** Array of producer threads. */
	Thread[] producers;
	
	/** Consumer thread for taking in data. */
	Thread consumer;
	
	/**
	 * RELEASE THE RECOLLECTION!
	 * @param memories list of dataframes to release the recollection on. Generated by recollection itself. 
	 * @param model the model to evaluate. 
	 * @param ev the evaluation function.
	 * @param numProducers the number of producer threads to run cross validations with.
	 */
	public ReleaseRecollection(List<ArrayList<DataFrame>> memories, Model model, Evaluate ev, int numProducers) {
		this.memories = memories;
		this.model = model;
		this.evaluate = ev;
		producing = true;
		producers = new Thread[numProducers];
	}
	
	/**
	 * Releases the recollection. Can be invoked using a thread for multiple concurrency.
	 */
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
	
    /**
     * Returns the max capacity of the bounded buffer. 
     * @return the max capacity of the bounded buffer. 
     */
	public int getCapacity() {
	    return capacity;
	}
	
	/**
	 * Called when the methods have iterated through all of the memories. 
	 */
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
	
	/**
	 * Adds a CrossValidation instance to the evaluation queue. 
	 * @param cv the cv to add to the evaluation queue. 
	 */
	public void addToQueue(CrossValidation cv) {
	    synchronized (EVALUATION_QUEUE) {
	        EVALUATION_QUEUE.add(cv);

	    }
	}
	
	/**
	 * Returns the next memory contianed in the memory queue. 
	 * @return the next memory contianed in the memory queue. 
	 */
	public ArrayList<DataFrame> getNext_Memory() {
	    ArrayList<DataFrame> next = null;
	    synchronized (memories) {
	        if (!memories.isEmpty())
	            next = memories.remove(0);
	    }
	    return next;
	}
	
	/**
	 * Evaluates a completed cross validation. 
	 * @param cv the complete
	 */
	public void evaluate(CrossValidation cv) {
	    evaluate.evaluation(cv);
	}
	
	/**
	 * Returns the current model recollection is being released on. 
	 * @return the current model recollection is being 
	 */
	public Model getModel() {
	    return model;
	}		
}