import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class Runner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> point1 = new ArrayList<Integer>() {{add(1); add(1);}};
		ArrayList<Integer> point2 = new ArrayList<Integer>() {{add(1); add(2);}};
		ArrayList<Integer> point3 = new ArrayList<Integer>() {{add(1); add(3);}};
		ArrayList<Integer> point4 = new ArrayList<Integer>() {{add(1); add(3);}};
		ArrayList<Integer> point5 = new ArrayList<Integer>() {{add(2); add(2);}};
		ArrayList<Integer> point6 = new ArrayList<Integer>() {{add(3); add(3);}};
		ArrayList<ArrayList<Integer>> points = new ArrayList<ArrayList<Integer>>() {{add(point1); add(point2); add(point3);
																					 add(point4); add(point5); add(point6);}};
		int[][] combisIdx = getCombisIdx(points.size());
		
		ArrayList<ArrayList<ArrayList<Integer>>> lineSegments = new ArrayList<ArrayList<ArrayList<Integer>>>();
		// get combis that can get line segment
		for(int[] combiIdx: combisIdx) {
			// get ArrayList<Integer> combiPoints by combiIdx
			
			// know if it can be line segment by applying ccw for combiPoints
			
			// if not that continue
			
			// else 
			
			// get start point and end point by sorting them
			
			// add line segment to lineSegments
		}
		
		// get unique intersetions from lineSegments
		
		// sort intersections
	}
	public static int[][] getCombisIdx(int pointSize){
		int r = 3;
		int n = pointSize;
		
		int[] indice = new int[n];
		for(int i = 0; i < n; i++)
			indice[i] = i;
		
		int combiNum = getCombinationNum(n, r);
		
		int[][] combis = new int[combiNum][r];
		int combisIdx = 0;
		
		for(int i = 0; i < n-r+1; i++) {
			int secondIdx = i+1;
			for(int j = secondIdx+1; j < n; j++) {
				int [] combi = new int[r];
				combi[0] = indice[i];
				combi[1] = indice[secondIdx];
				combi[2] = indice[j];
				combis[combisIdx] = combi;
				combisIdx += 1;
			}
			secondIdx += 1;
		}
		
		return combis;
	}
	
	public static int getCombinationNum(int n, int k) {
		int nFact = getFactorial(n);
		int kFact = getFactorial(k);
		int n_kFact = getFactorial(n-k);
		
		double divided = nFact / kFact * n_kFact;
		
		if (divided % 1 != 0)
			System.out.println("combinum "+divided+" not int");
		
		return (int)divided;
	}
	
	public static int getFactorial(int n) {
		int fact = 1;
		for(int i=1; i<=n; i++) {
			fact *= i;
		}
		return fact;
	}
	
	
	public static int ccw(ArrayList<Integer> a, ArrayList<Integer> b, ArrayList<Integer> c) {
		// get counterclockwise of point c for point a and b
	}
	
	public static int[] getStartEndPointIdx(ArrayList<Integer> a, ArrayList<Integer> b, ArrayList<Integer> c) {
		// get start point and end point indices 
	}
	
	public static HashSet<ArrayList<Integer>> getUniqueInter(ArrayList<ArrayList<ArrayList<Integer>>> lineSegments){
		
	}
	
	public static boolean isIntersect(ArrayList<ArrayList<Integer>> ab, ArrayList<ArrayList<Integer>> cd) {
		
	}
	
	public static ArrayList<Integer> getIntersection(ArrayList<ArrayList<Integer>> ab, ArrayList<ArrayList<Integer>> cd){
		
	}
	
	public static float getSlope(ArrayList<ArrayList<Integer>> ab) {
		
	}
	
	public static float getYIntercept(ArrayList<ArrayList<Integer>> ab) {
		
	}
	
	public static ArrayList<ArrayList<Integer>> sortIntersection(HashSet<ArrayList<Integer>> uniqueInter){
		
	}
}
