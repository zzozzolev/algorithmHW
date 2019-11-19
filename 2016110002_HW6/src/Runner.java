import java.util.ArrayList;

public class Runner {

	public static void main(String[] args) {
		int n = 2;
		int m = 3;
		double[] objFuncCoeff = new double[n];
		double[][] constCoeff = new double[m][n];
		ArrayList<Double> objValue = new ArrayList<Double>();
		
		addNonNegativity(constCoeff);
		
		boolean isAllNeg = isObjFuncAllNeg(constCoeff[0]);
		boolean unbounded = false;
		// 0: constant, 1: x_1, ...
		double[][] slackForm = getSlackForm(constCoeff);
		
		while(!isAllNeg || unbounded) {
			int maxCoeffIdx = getMaxCoeffIdx(slackForm[0]);
			if (maxCoeffIdx == 0)
				System.out.println("constant is maxCoeffIdx");
			unbounded = isUnbounded(maxCoeffIdx, slackForm);
			
			if (unbounded)
				break;
			
			// (row, col) (const, coeff)
			int tightConstIdx = getTightConstIdx(maxCoeffIdx, slackForm);
			double[] rearranged = getRearranged(maxCoeffIdx, slackForm[tightConstIdx]);
			
			replace(rearranged, slackForm);
			
			objValue.add(slackForm[0][0]);
		}
	}
	
	public static void addNonNegativity(double[][] constCoeff) {
	 
	}
	
	public static boolean isObjFuncAllNeg(double[] objFunc) {
		// TODO: do not consider constant
	}
	
	public static double[][] getSlackForm(double[][] constCoeff) {
		// slack form: [constraint + non-negativity][n variable + constant]
		// [m+n][n+1]
	}
	
	public static int getMaxCoeffIdx(double[] objFuncCoeff) {
		// TODO: do not consider constant
	}
	
	public static boolean isUnbounded(int maxCoeffIdx, double[][] slackForm) {
		
	}
	
	public static int getTightConstIdx(int maxCoeffIdx, double[][] slackForm) {
		
	}
	
	public static double[] getRearranged(int maxCoeffIdx, double[] constraint) {
		// TODO: max coeff == 0
	}
	
	public static void replace(double[] rearranged, double[][] slackForm) {
		
	}
	
}
