import java.lang.Math;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

public class Runner {

	public static void main(String[] args) throws IOException {
		String inputPath = "/Users/poza/jam/2019_2/algorithm_analysis/input.txt";
		FileReader fileReader = new FileReader(inputPath);
		BufferedReader bufferReader = new BufferedReader(fileReader);
		
		String outputPath = "/Users/poza/jam/2019_2/algorithm_analysis/2016110002.txt";
		File file = new File(outputPath);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
		
		int nCase = Integer.parseInt(bufferReader.readLine().trim());
		
		for(int i = 0; i < nCase; i++) {
			int testNum = i + 1;
			String[] splited = bufferReader.readLine().trim().split(" ");
			int max_deg = Integer.parseInt(splited[0]);
			int n = getN(max_deg);
			
			int[] a = getCoeffi(n, splited);
			Complex[] A = new Complex[n];
			
			fft(a, A);
			
			writeOutput(testNum, A, bufferedWriter);
		}
		bufferReader.close();
		bufferedWriter.close();
	}
	
	
	public static int[] getCoeffi(int n, String[] splited) {
		int[] a = new int[n];
		for(int i = 0; i < n; i++) {
			if (i + 1 < splited.length) {
				a[i] = Integer.parseInt(splited[i+1]);
			}
		}
		return a;
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
	
	public static void writeOutput(int testNum, Complex[] A, BufferedWriter bufferedWriter) throws IOException {
		bufferedWriter.write("#"+testNum);
		bufferedWriter.newLine();
		for (Complex c: A) {
			c.roundBySeventhDecimal();
			bufferedWriter.write(String.format("%.6f %.6f", c.real, c.imag));
			bufferedWriter.newLine();
		}
	}
}
