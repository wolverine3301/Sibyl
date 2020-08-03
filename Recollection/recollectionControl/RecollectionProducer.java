package recollectionControl;

import java.util.ArrayList;

import dataframe.DataFrame;
import scorer.CrossValidation;

/**
 * Produces computationally expensive CrossValidation instances on previously instantiated DataFrames from the parent's memory field. 
 * - NOTE: Multiple threads of this class can be running at the same time! 
 * - NOTE 2: Currently not implemented for concurrent memory production and consumption!
 * @author Cade Reynoldson
 * @version 1.0
 */
public class RecollectionProducer implements Runnable {

    /** The parent to act as a producer for. */
    ReleaseRecollection parent;
    
    /**
     * Instantiates a new instance of a producer for the parent ReleaseRecollection. 
     * @param parent the parent to act as a producer for. 
     */
    public RecollectionProducer(ReleaseRecollection parent) {
        this.parent = parent;
    }
    
    /**
     * Runs the producing algorithm for pro
     */
    @Override
    public void run() {
        ArrayList<DataFrame> eval;
        while (parent.isProducing()) { //While there are still cvs to produce, run in this loop.
            synchronized (parent.EVALUATION_QUEUE) { //Synchronize on parent evaluation queue. 
                while (parent.EVALUATION_QUEUE.size() >= parent.getCapacity()) { // If queue is at capacity, wait. 
                    try {
                        parent.EVALUATION_QUEUE.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                eval = parent.getNext_Memory(); // Fetch next memory. 
                parent.EVALUATION_QUEUE.notify(); //Notifies anything waiting on evaluation queue that they can wake up. - not sure if needed. 
            }
            if (eval == null) //If next eval is null, notify parent that this thread is done running, all other threads will fall through. 
                parent.doneProducing();
            else {
                //Run Cross validiation (Computationally expensive, don't want to synchronize!)
                for (DataFrame df : eval) {
                    CrossValidation cv = new CrossValidation(df, 5, parent.getModel().copy());
                    parent.addToQueue(cv);
                }
            }
        }
    }
}
