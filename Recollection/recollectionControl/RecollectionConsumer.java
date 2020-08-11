package recollectionControl;

import scorer.CrossValidation;

/**
 * Acts as an individually threaded consumer for ReleaseRecollection.
 * - NOTE: This class has not been tested with multiple consumer instances!
 * @author Cade Reynoldson
 * @version 1.0
 */
public class RecollectionConsumer implements Runnable{
    
    /** A reference to the parent release recollection. */
    private ReleaseRecollection parent;
    
    /**
     * Instantiates a recollection consumer.
     * @param parent the parent ReleaseRecollection.
     */
    public RecollectionConsumer(ReleaseRecollection parent) {
        this.parent = parent;
    }

    /**
     * Runs the consumer. 
     */
    @Override
    public void run() {
        while (parent.isProducing() || !parent.queueIsEmpty()) {
            CrossValidation cv;
            synchronized (parent.EVALUATION_QUEUE) {
                while (parent.EVALUATION_QUEUE.size() == 0) {
                    try {
                        parent.EVALUATION_QUEUE.wait();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
        		
        		//System.out.println(parent.EVALUATION_QUEUE.getLast().getOverall_f1());
                cv = parent.getNext_Queue();


                parent.EVALUATION_QUEUE.notify();
            }
            System.out.println("RECO CONSUMe:");
    		//System.out.println(cv.getOverall_f1());
    		//System.out.println(cv.getOverall_mcc());
    		//System.out.println(cv.getOverall_precision());
    		//System.out.println(cv.getOverall_recall());
            parent.evaluate(cv);
        }
    }
    
   
}
