package recollectionControl;

import java.util.ArrayList;

import dataframe.DataFrame;
import scorer.CrossValidation;

public class RecollectionProducer implements Runnable {

    ReleaseRecollection parent;
    
    public RecollectionProducer(ReleaseRecollection parent) {
        this.parent = parent;
    }
    
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
                parent.EVALUATION_QUEUE.notify(); //Notifies anything waiting on evaluation queue that they can wake up. 
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
//        for(ArrayList<DataFrame> i : memories) {
//            
//        //while (true) { 
//            for(DataFrame k : i) {
//                synchronized (this){ 
//                    // producer thread waits if list is full
//                    while (EVALUATION_QUEUE.size() == capacity) 
//                        wait(); 
//                    
//                    
//                    CrossValidation cv = new CrossValidation(k,5, model);
//                    //cv.printOverAllScore();
//                    System.out.println("SIZE: "+EVALUATION_QUEUE.size());
//                    // to insert the crossvals in the list 
//                    EVALUATION_QUEUE.add(cv);
//                    cnt++;
//                    // notifies the consumer thread that 
//                    // now it can start consuming 
//                    notify(); 
//
//                    //Thread.sleep(10); 
//                }
//            } 
//        }
//        producing = false;
    }
}
