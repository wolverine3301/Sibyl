package testing;

import java.math.BigInteger;
import java.util.stream.IntStream;

public class threadBench {

	public static void main(String[] args) {
		//100,000 2 threads is faster
		int[] a = new int[100000];

		for(int i = 0; i < 100000;i++) {
			a[i] = 1;
		}

		long start = System.currentTimeMillis();
		BigInteger sum1 = sum(a);
		long fin = System.currentTimeMillis() - start;
		System.out.println("NOT THREADED: " +sum1 + " Time: "+fin);

		start = System.currentTimeMillis();
		BigInteger sum3 = sumTT(a);
		fin = System.currentTimeMillis() - start;
		System.out.println("2 THREADED: " +sum3 + " Time: "+fin);
		
	}
	public static BigInteger sum(int[] a) {
		BigInteger sum = BigInteger.ZERO;
			for(int j =0;j< a.length;j++) {
				sum = sum.add(BigInteger.valueOf(a[j]));
			}
		return sum;
	}
	public static BigInteger sumTT(int[] a) {
		BigInteger sum = BigInteger.ZERO;
			long[] results = new long[2];
			Thread t0 = new Thread(() -> results[0] = sum2t(a,0,a.length/2));
			Thread t1 = new Thread(() -> results[1] = sum2t(a,(a.length/2)+1,a.length));
			t0.start();
			t1.start();
			try {
			    t0.join();
			    t1.join();
			} catch (InterruptedException e) { /* NOP */ }
			
			sum = sum.add(BigInteger.valueOf(results[0]+results[1]));
		
		return sum;
	}
	public static long sum2t(int[] a,int s, int f) {
		long sum = 0;
		for(int i =s; i < f;i++) {
			sum = sum + a[i];
		}
		return sum;
	}

}
