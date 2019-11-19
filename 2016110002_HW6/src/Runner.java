import java.util.ArrayList;

public class Runner {

	public static void main(String[] args) {
		int n = 2;
		int m = 3;
		int[] objFuncCoeff = new int[n];
		int[][] constCoeff = new int[m][n];
		ArrayList<Double> objValue = new ArrayList<Double>();
		
		addNonNegativity(constCoeff);
		
		boolean isAllNeg = isObjFuncAllNeg(constCoeff[0]);
		boolean unbounded = false;
		// 0: constant, 1: x_1, ...
		int[][] slackForm = getSlackForm(constCoeff);
		
		while(!isAllNeg || unbounded) {
			int maxCoeffIdx = getMaxCoeffIdx(slackForm[0]);
			if (maxCoeffIdx == 0)
				System.out.println("constant is maxCoeffIdx");
			unbounded = isUnbounded(maxCoeffIdx, slackForm);
			
			if (unbounded)
				break;
			
			// (row, col) (const, coeff)
			int tightConstIdx = getTightConstIdx(maxCoeffIdx, slackForm);
			int[] rearranged = getRearranged(maxCoeffIdx, slackForm[tightConstIdx]);
			
			replace(rearranged, slackForm);
			
			objValue.add(slackForm[0][0]);
		}
	}
	
	public static void addNonNegativity(int[][] constCoeff) {
	 
	}
	
	public static boolean isObjFuncAllNeg(int[] objFunc) {
		// TODO: do not consider constant
	}
	
	public static int[][] getSlackForm(int[][] constCoeff) {
		// slack form: [constraint + non-negativity][n variable + constant]
		// [m+n][n+1]
	}
	
	public static int getMaxCoeffIdx(int[] objFuncCoeff) {
		// TODO: do not consider constant
	}
	
	public static boolean isUnbounded(int maxCoeffIdx, int[][] slackForm) {
		
	}
	
	public static int getTightConstIdx(int maxCoeffIdx, int[][] slackForm) {
		
	}
	
	public static int[] getRearranged(int maxCoeffIdx, int[] constraint) {
		// TODO: max coeff == 0
	}
	
	public static void replace(int[] rearranged, int[][] slackForm) {
		
	}
	
}
