import java.util.ArrayList;

public class Runner {

	public static void main(String[] args) {
		int n = 2;
		int m = 3;
		int[] objFuncCoeff = new int[n];
		int[][] constCoeff = new int[m][n];
		ArrayList<Double> objValue = new ArrayList<Double>();
		
		
	}
	
	public static int[][] getSlackForm(int[][] constCoeff) {
		// slack form: [constraint + non-negativity][n variable + constant]
		// [m+n][n+1]
	}
	
	public static void addNonNegativity(int[][] constCoeff) {
		
	}
	
	public static int getMaxCoeffIdx(int[] objFuncCoeff) {
		
	}
	
	public static boolean isUnbounded(int maxCoeffIdx, int[][] constCoeff) {
		
	}
	
	public static int getTightConstIdx(int[][] slackForm) {
		
	}
	
	public static int[] getRearranged(int[][] slackForm) {
		
	}
	
	
}
