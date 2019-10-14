package testing;

import java.math.BigInteger;
import java.util.stream.IntStream;

public class threadBench {

	public static void main(String[] args) {
		int[][] a = new int[1][1000000000];
		for(int j = 0; j < 1;j++) {
			for(int i = 0; i < 1000000000;i++) {
				a[j][i] = i;
			}
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
	public static BigInteger sum(int[][] a) {
		BigInteger sum = BigInteger.ZERO;
		for(int i = 0; i < a.length;i++) {
			for(int j =0;j< a[i].length;j++) {
				sum = sum.add(BigInteger.valueOf(a[i][j]));
			}
		}
		return sum;
	}
	public static BigInteger sumTT(int[][] a) {
		BigInteger sum = BigInteger.ZERO;
		int i;
		for(i = 0; i < a.length;i++) {
			long[] results = new long[2];
			int[] b = a[i];
			Thread t0 = new Thread(() -> results[0] = sum2t(b,0,b.length/2));
			Thread t1 = new Thread(() -> results[1] = sum2t(b,(b.length/2)+1,b.length));
			t0.start();
			t1.start();
			try {
			    t0.join();
			    t1.join();
			} catch (InterruptedException e) { /* NOP */ }
			
			sum = sum.add(BigInteger.valueOf(results[0]+results[1]));
		}
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
