import java.util.ArrayList;
import java.util.Arrays;
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
			ArrayList<ArrayList<Integer>> combiPoints = new ArrayList<ArrayList<Integer>>();
			for(int idx: combiIdx)
				combiPoints.add(points.get(idx));
			
			// know if it can be line segment by applying ccw for combiPoints
			int pointsCcw = ccw(combiPoints.get(0), combiPoints.get(1), combiPoints.get(2));
			
			// if not parallel continue
			if (pointsCcw != 0)
				continue;
			
			// else
			else {
				ArrayList<ArrayList<Integer>> excludedPoints = getExcludedPoints(combiPoints, points);
				// find additional parallel point
				combiPoints = addParallelPoint(combiPoints, excludedPoints);
				
				// get start point and end point by sorting them
				ArrayList<ArrayList<Integer>> startEndPoints = getStartEndPoints(combiPoints);
				// add startEndPoints to lineSegments
				lineSegments.add(startEndPoints);
			}
		}
		
		// get unique intersetions from lineSegments
		HashSet<ArrayList<Double>> uniqueInter = getUniqueInter(lineSegments);
		ArrayList<ArrayList<Double>> converted = new ArrayList<ArrayList<Double>>(uniqueInter);
		
		// sort intersections
		ArrayList<ArrayList<Double>> sortedInters = sortDoubleArrayListDouble(converted);
		
		for(ArrayList<Double> e: sortedInters) {
			System.out.println(e);
		}
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
		// (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)
		int exp = (b.get(0) - a.get(0)) * (c.get(1) - a.get(1)) - (b.get(1) - a.get(1)) * (c.get(0) - a.get(0));
		
		// parallel
		if (exp == 0) 
			return 0;
		// counter clockwise
		else if (exp > 0)
			return 1;
		// clockwise
		else
			return -1;
	}
	
	public static ArrayList<ArrayList<Integer>> getExcludedPoints(ArrayList<ArrayList<Integer>> combiPoints, ArrayList<ArrayList<Integer>> points){
		ArrayList<ArrayList<Integer>> excludedPoints = new ArrayList<ArrayList<Integer>>();
		excludedPoints.addAll(points);
		excludedPoints.removeAll(combiPoints);
		
		if (!points.containsAll(combiPoints))
			System.out.println("points dosen't have combiPoints");
		
		return excludedPoints;
	}
	
	public static ArrayList<ArrayList<Integer>> addParallelPoint(ArrayList<ArrayList<Integer>> combiPoints, ArrayList<ArrayList<Integer>> excludedPoints){
		if (combiPoints.size() != 3)
			System.out.println("combiPoints size != 3");
		
		for(ArrayList<Integer> point: excludedPoints) {
			int pointsCcw = ccw(combiPoints.get(0), combiPoints.get(1), point);
			
			if (pointsCcw == 0)
				combiPoints.add(point);
			else
				continue;
		}

		return combiPoints;
	}
	
	public static ArrayList<ArrayList<Integer>> getStartEndPoints(ArrayList<ArrayList<Integer>> combiPoints) {
		// get start point and end point
		ArrayList<ArrayList<Integer>> startEndPoints = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> sorted = sortDoubleArrayListInteger(combiPoints); 
		
		startEndPoints.add(sorted.get(0));
		startEndPoints.add(sorted.get(sorted.size()-1));
		
		return startEndPoints;
	}
	
	public static HashSet<ArrayList<Double>> getUniqueInter(ArrayList<ArrayList<ArrayList<Integer>>> lineSegments){
		HashSet<ArrayList<Double>> uniqueInter = new HashSet<ArrayList<Double>>();
		for(int i = 0; i < lineSegments.size(); i++) {
			ArrayList<ArrayList<Integer>> target = lineSegments.get(i);
			for(int j = i+1; j < lineSegments.size(); j++) {
				ArrayList<ArrayList<Integer>> compared = lineSegments.get(j);
				boolean intersect = isIntersect(target, compared);
				if (!intersect) 
					continue;
				else {
					ArrayList<Double> intersection = getIntersection(target, compared);
					uniqueInter.add(intersection);
				}
			}
		}	
		return uniqueInter;
	}
	
	public static boolean isIntersect(ArrayList<ArrayList<Integer>> ab, ArrayList<ArrayList<Integer>> cd) {
		int abCcw = ccw(ab.get(0), ab.get(1), cd.get(0)) * ccw(ab.get(0), ab.get(1), cd.get(1));
		int cdCcw = ccw(cd.get(0), cd.get(1), ab.get(0)) * ccw(cd.get(0), cd.get(1), ab.get(1));
		if (abCcw < 0 && cdCcw < 0) {
			return true;
		}
		else
			return false;		
	}
	
	public static ArrayList<Double> getIntersection(ArrayList<ArrayList<Integer>> ab, ArrayList<ArrayList<Integer>> cd){
		double abSlope = getSlope(ab);
		double abYInter = getYIntercept(ab.get(0), abSlope);
		
		double cdSlope = getSlope(cd);
		double cdYInter = getYIntercept(cd.get(0), cdSlope);
		
		ArrayList<Double> intersection = new ArrayList<Double>();
		
		Double raw_x = (cdYInter-abYInter) / (abSlope-cdSlope);
		
		Double rounded_x = roundByFirstDecimal(raw_x); 
		Double rounded_y = roundByFirstDecimal(abSlope * raw_x + abYInter);
		
		Double x = (Double)(rounded_x);
		Double y = (Double)(rounded_y);
		
		intersection.add(x);
		intersection.add(y);
		
		return intersection;
	}
	
	public static double getSlope(ArrayList<ArrayList<Integer>> ab) {
		ArrayList<Integer> a = ab.get(0);
		ArrayList<Integer> b = ab.get(1);
		
		return (b.get(1) - a.get(1)) / (b.get(0) - a.get(0));
	}
	
	public static double getYIntercept(ArrayList<Integer> a, double slope) {
		return a.get(1) - slope * a.get(0);
	}
	
	public static double roundByFirstDecimal(double d) {
		return Math.round(d * 10 / 10.0);
	}
	
	public static ArrayList<ArrayList<Integer>> sortDoubleArrayListInteger(ArrayList<ArrayList<Integer>> target){
		Collections.sort(target, new Comparator<ArrayList<Integer>>() {
			public int compare(ArrayList<Integer> a1, ArrayList<Integer> a2) {
				if (a1.get(0) > a2.get(0)) return 1;
				else if (a1.get(0) < a2.get(0)) return -1;
				else {
					if (a1.get(1) > a2.get(1)) return 1;
					else if (a1.get(1) < a2.get(1)) return -1;
					else return 0;
				}
			}
		});
		return target;
	}
	
	public static ArrayList<ArrayList<Double>> sortDoubleArrayListDouble(ArrayList<ArrayList<Double>> target){
		Collections.sort(target, new Comparator<ArrayList<Double>>() {
			public int compare(ArrayList<Double> a1, ArrayList<Double> a2) {
				if (a1.get(0) > a2.get(0)) return 1;
				else if (a1.get(0) < a2.get(0)) return -1;
				else {
					if (a1.get(1) > a2.get(1)) return 1;
					else if (a1.get(1) < a2.get(1)) return -1;
					else return 0;
				}
			}
		});
		return target;
	}
}
