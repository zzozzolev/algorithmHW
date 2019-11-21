import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Runner {

	public static void main(String[] args) throws IOException {
		String inputPath = "/Users/poza/jam/2019_2/algorithm_analysis/input.txt";
		FileReader fileReader = new FileReader(inputPath);
		BufferedReader bufferReader = new BufferedReader(fileReader);
		
		String[] splited = bufferReader.readLine().trim().split(" ");
		int n = Integer.parseInt(splited[0]);
		int m = Integer.parseInt(splited[1]);
		int nRow = m + 1; // constraint + object function
		int nCol = n + 1; // variable + constant
		
		ArrayList<Double> objValue = new ArrayList<Double>();
		double[][] constCoeff = getConstCoeff(nRow, nCol, bufferReader);
		
		// first objFunc only consists of coefficients 
		objValue.add(0.0);
		int objFuncRow = 0;
		int constCol = 0;
		
		double[][] slackForm = getSlackForm(nRow, nCol, objFuncRow, constCoeff);
		boolean isAllNeg = isObjFuncAllNeg(constCol, nCol, slackForm[objFuncRow]);
		boolean unbounded = false;
		
		while(!isAllNeg) {
			int maxCoeffCol = getMaxCoeffCol(constCol, nCol, slackForm[objFuncRow]);
			unbounded = isUnbounded(maxCoeffCol, slackForm);
			
			if (unbounded)
				break;
			
			// (row, col) (const, coeff)
			int tightConstIdx = getTightConstRow(maxCoeffCol, objFuncRow, constCol, slackForm);
			double[] rearranged = getRearranged(maxCoeffCol, nCol, slackForm[tightConstIdx]);
			replace(tightConstIdx, maxCoeffCol, rearranged, slackForm);
			
			objValue.add(slackForm[objFuncRow][constCol]);
			isAllNeg = isObjFuncAllNeg(constCol, nCol, slackForm[objFuncRow]);
		}
		
		System.out.println(objValue);
	}
	
	public static double[][] getConstCoeff(int nRow, int nCol, BufferedReader bufferReader) throws IOException{
		double[][] constCoeff = new double[nRow][nCol];
		for(int i=0; i<nRow; i++) {
			String[] splited = bufferReader.readLine().trim().split(" ");
			// 2x + 3y + 4
			for(int j=0; j<splited.length; j++) {
				constCoeff[i][j] = Double.parseDouble(splited[j]);
			}
		}
		return constCoeff;
	}
	
	public static double[][] getSlackForm(int nRow, int nCol, int objFuncRow, double[][] constCoeff) {
		// slack form: [constraint][n variable + constant + slack varibale] -> [m+1][n+1+m]
		// ex) 4x + 3y <= 2 -> a = 2 - 4x - 3y
		// array [4, 3, 2] -> [2, -4, -3, -1, 0, 0] (0 are other constraints slack variable)
		double[][] slackForm = new double[nRow][nCol+nRow];
		for(int i=0; i<nRow; i++) {
			for(int j=1; j<nCol; j++) {
				// coefficient
				if(i == objFuncRow)
					slackForm[i][j] = constCoeff[i][j-1];
				else	
					slackForm[i][j] = - constCoeff[i][j-1];
			}
			// constant
			slackForm[i][0] = constCoeff[i][nCol-1];
			// corresponding row slack variable set to - 1.0 (LHS) and others set to 0.0
			slackForm[i][nCol+i] = - 1.0;
		}
		return slackForm;
	}
	
	public static boolean isObjFuncAllNeg(int constCol, int nCol, double[] objFunc) {
		// do not consider constant -> index 0
		boolean isAllNeg = true;
		for(int i=constCol+1; i < nCol; i++) {
			if (objFunc[i] > 0) {
				isAllNeg = false;
				break;
			}
		}
		return isAllNeg;
	}
	
	public static int getMaxCoeffCol(int constCol, int nCol, double[] objFunc) {
		double maxCoeff = Double.MIN_VALUE;
		int maxCoeffCol = -1;
		// exclude constant
		for(int i = constCol+1; i < nCol; i++) {
			if (objFunc[i] > maxCoeff) {
				maxCoeff = objFunc[i];
				maxCoeffCol = i;
			}
		}
		if (maxCoeffCol == -1)
			System.out.println("invalid maxCoeffIdx");
		
		return maxCoeffCol;
	}
	
	public static boolean isUnbounded(int maxCoeffCol, double[][] slackForm) {
		boolean isPositive = false;
		for(int i=0; i<slackForm.length; i++) {
			// constCoeff == slackForm in objFuncRow
			// constCoeff == - slackForm in otherRow
			// so all of them need to be multiplied -1
			double target = - slackForm[i][maxCoeffCol];
			if (target > 0) {
				isPositive = true;
				break;
			}
		}
		return (isPositive? false : true);
	}
	
	public static int getTightConstRow(int maxCoeffCol, 
									   int objFuncRow, 
									   int constCol,
									   double[][] slackForm) {
		double tightValue = Double.MAX_VALUE;
		int tightRow = -1;
		for(int i = objFuncRow+1; i < slackForm.length; i++) {
			// only consider negative coeff
			if (slackForm[i][maxCoeffCol] >= 0)
				continue;
			else {
				double value = - slackForm[i][0] / slackForm[i][maxCoeffCol];
				if (value < tightValue) {
					tightValue = value;
					tightRow = i;
				}
			}
		}
		return tightRow;
	}
	
	public static double[] getRearranged(int maxCoeffCol, int nCol, double[] constraint) {
		// multiply -1 to move to LHS
		double[] rearranged = new double[constraint.length];
		double divider = - constraint[maxCoeffCol];
		
		for(int i = 0; i < constraint.length; i++) {
			if (i == maxCoeffCol) {
				// move i to LHS
				constraint[i] = -1.0;
				// set to 0.0 not to be multiplied other coeff
				rearranged[i] = 0.0;
			}
			else if(i < nCol) {
				// coeff / - max coeff
				constraint[i] /= divider;
				rearranged[i] = constraint[i];
			}
			else {
				// slack variable: coeff / - max coeff and move to RHS
				constraint[i] /= - divider;
				rearranged[i] = constraint[i];
			}
		}
		
		return rearranged;
	}
	
	public static void replace(int tightConstIdx, 
							   int maxCoeffCol,
							   double[] rearranged,
							   double[][] slackForm) {
		for(int i = 0; i < slackForm.length; i++) {
			// do not consider tight constraint (rearranged)
			if (i == tightConstIdx) {
				continue;
			}
			else {
				double multipleValue = slackForm[i][maxCoeffCol];
				for(int j=0; j<slackForm[i].length; j++){
					slackForm[i][j] += multipleValue * rearranged[j];
				}
			}
		}
	}
	
}
