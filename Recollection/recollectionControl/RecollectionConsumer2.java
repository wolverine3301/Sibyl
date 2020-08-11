package recollectionControl;

import java.util.concurrent.BlockingQueue;

import scorer.CrossValidation;
import scorer.Evaluate;

public class RecollectionConsumer2 implements Runnable {
	/** The parent to act as a producer for. */
    ReleaseRecollection2 parent;
	private final BlockingQueue<CrossValidation> EVALUATION_QUEUE;
	private Evaluate evaluate;
	private boolean evalOn;
	public RecollectionConsumer2(BlockingQueue<CrossValidation> blockingQueue,boolean evalOn,Evaluate evaluate) {
	  this.EVALUATION_QUEUE = blockingQueue;
	  this.evaluate = evaluate;
	 }

	public void run() {
		while (true) {
			try {
				evaluate.evaluation(EVALUATION_QUEUE.take());
			} catch (InterruptedException ex) {
				System.out.println("Evaluation Consumer thread interrupted.");
			}
		}
		//System.out.println("EVALUATION COMPLETE");
	}
	
}
