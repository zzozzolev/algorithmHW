import java.lang.Math;

public class Runner {

	public static void main(String[] args) {
		int max_deg = 7;
		int n = 8;
		double PI = 3.141592;
		
		int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
		Complex[] A = new Complex[n];
		
		double kth = 2.0 * 1 * PI / 8;
		
		fft(a, A);
		
		for (Complex c: A) {
			c.roundBySeventhDecimal();
			System.out.println(c);
		}
	}
	
	public static void fft(int[] a, Complex[] A) {
		double PI = 3.141592; 
		int n = a.length;
		
		// N == 1
		if (n == 1) {
			// a_k * w_0 == a_k * 1
			A[0] = new Complex(a[0], 0);
			return;
		}
		
		int[] even = new int[n/2];
		int[] odd = new int[n/2];
		
		Complex[] even_A = new Complex[n/2];
		Complex[] odd_A = new Complex[n/2];
		
		// split a into even and odd
		splitIntoEvenOdd(a, even, odd);
		
		// call recursively fft
		fft(even, even_A);
		fft(odd, odd_A);
		
		for(int k = 0; k < n/2; k++) {
			double kth = 2.0 * k * PI / n;
			Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
			// i, i+n/2 is plus and minus pair
			A[k] = Complex.sum(even_A[k], Complex.multi(wk, odd_A[k]));
			wk.multiMinus();
			A[k + n/2] = Complex.sum(even_A[k], Complex.multi(wk, odd_A[k]));
		}
	}
	
	public static void splitIntoEvenOdd(int[] a, int[] even, int[] odd) {
		for(int i = 0; i < a.length / 2; i++) {
			even[i] = a[i * 2];
			odd[i] = a[i * 2 + 1];
		}
	}
}
