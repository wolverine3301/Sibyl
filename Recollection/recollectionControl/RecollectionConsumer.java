package recollectionControl;

import scorer.CrossValidation;

public class RecollectionConsumer implements Runnable{
    
    private ReleaseRecollection parent;
    
    public RecollectionConsumer(ReleaseRecollection parent) {
        this.parent = parent;
    }

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
                cv = parent.getNext_Queue();
                parent.EVALUATION_QUEUE.notify();
            }
            parent.evaluate(cv);
        }
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
    }
    
   
}
