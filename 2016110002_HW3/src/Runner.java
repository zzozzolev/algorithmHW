import java.lang.Math;

public class Runner {

	public static void main(String[] args) {
		int max_deg = 7;
		int n = getN(max_deg);
		
		int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
		Complex[] A = new Complex[n];
		
		fft(a, A);
		
		for (Complex c: A) {
			c.roundBySeventhDecimal();
			System.out.println(c.real+" "+c.imag);
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
	public static int getN(int max_deg) {
		// maximum of max_deg = 63, so max 2^k = 64, k = 6
		int max_k = 6;
		
		for(int k = 1; k < max_k + 1; k++) {
			int two_k = (int)Math.pow(2, k);
			if (max_deg < two_k)
				return two_k;
		}
		System.out.println("invalid max_deg: "+max_deg);
		return -1;
			
	}
	
	public static void splitIntoEvenOdd(int[] a, int[] even, int[] odd) {
		for(int i = 0; i < a.length / 2; i++) {
			even[i] = a[i * 2];
			odd[i] = a[i * 2 + 1];
		}
	}
}
