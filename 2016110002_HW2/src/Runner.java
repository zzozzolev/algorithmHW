import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class Runner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> point1 = new ArrayList<Integer>() {{add(4); add(1);}};
		ArrayList<Integer> point2 = new ArrayList<Integer>() {{add(4); add(2);}};
		ArrayList<Integer> point3 = new ArrayList<Integer>() {{add(4); add(3);}};
		ArrayList<Integer> point4 = new ArrayList<Integer>() {{add(4); add(4);}};
		ArrayList<Integer> point5 = new ArrayList<Integer>() {{add(5); add(2);}};
		ArrayList<Integer> point6 = new ArrayList<Integer>() {{add(6); add(3);}};
		
		ArrayList<ArrayList<Integer>> points = new ArrayList<ArrayList<Integer>>() {{add(point1); add(point2); add(point3);
																					 add(point4); add(point5); add(point6);}};
		int[][] combisIdx = getCombisIdx(points.size());
		
		HashSet<ArrayList<ArrayList<Integer>>> lineSegments = new HashSet<ArrayList<ArrayList<Integer>>>();
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
		// convert Double to Integer if coordinate is integer
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
			for(int j = i+1; j < n-r+2; j++) {
				for(int k = j+1; k < n; k++) {
					int [] combi = new int[r];
					combi[0] = indice[i];
					combi[1] = indice[j];
					combi[2] = indice[k];
					combis[combisIdx] = combi;
					combisIdx += 1;
				}
			}
		}
		
		return combis;
	}
	
	public static int getCombinationNum(int n, int k) {
		int nFact = getFactorial(n);
		int kFact = getFactorial(k);
		int n_kFact = getFactorial(n-k);
		
		double divided = nFact / (kFact * n_kFact);
		
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
	
	public static HashSet<ArrayList<Double>> getUniqueInter(HashSet<ArrayList<ArrayList<Integer>>> lineSegments){
		HashSet<ArrayList<Double>> uniqueInter = new HashSet<ArrayList<Double>>();
		ArrayList<ArrayList<ArrayList<Integer>>> convertedLineSegs = new ArrayList<ArrayList<ArrayList<Integer>>>(lineSegments);
		for(int i = 0; i < convertedLineSegs.size(); i++) {
			ArrayList<ArrayList<Integer>> target = convertedLineSegs.get(i);
			for(int j = i+1; j < lineSegments.size(); j++) {
				ArrayList<ArrayList<Integer>> compared = convertedLineSegs.get(j);
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
		// If two segments have common point
		if(ab.contains(cd.get(0)) || ab.contains(cd.get(1))) {
			return true;
		}
		
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
		
		ArrayList<Double> intersection = null;
		if (abSlope == 0 && cdSlope == 0) {
			intersection = getInterAllZeroSlope(ab, cd);
		}
		else if(abSlope == 0) {
			intersection = getInterOneZeroSlope(ab, cdSlope, cdYInter);
		}
		else if(cdSlope == 0) {
			intersection = getInterOneZeroSlope(cd, abSlope, abYInter);
		}
		else {
			intersection = getInterUnZeroSlope(abSlope, abYInter, cdSlope, cdYInter);
		}
		
		ArrayList<Double> roundedInter = new ArrayList<Double>();
		// round intersection
		Double rounded_x = roundByFirstDecimal(intersection.get(0)); 
		Double rounded_y = roundByFirstDecimal(intersection.get(1));
		
		roundedInter.add(rounded_x);
		roundedInter.add(rounded_y);
		
		return roundedInter;
	}
	
	public static double getSlope(ArrayList<ArrayList<Integer>> ab) {
		ArrayList<Integer> a = ab.get(0);
		ArrayList<Integer> b = ab.get(1);
		
		// when x increase is zero
		if (b.get(0) - a.get(0) == 0)
			return 0;
		
		Integer numerator = b.get(1) - a.get(1);
		Double denominator = new Double((b.get(0) - a.get(0)));
		
		return numerator / denominator;
	}
	
	public static double getYIntercept(ArrayList<Integer> a, double slope) {
		return a.get(1) - slope * a.get(0);
	}
	
	public static ArrayList<Double> getInterUnZeroSlope(double abSlope, double abYInter, double cdSlope, double cdYInter){
		ArrayList<Double> intersection = new ArrayList<Double>();
		
		Double x = (cdYInter-abYInter) / (abSlope-cdSlope);
		Double y = abSlope * x + abYInter;
		
		intersection.add(x);
		intersection.add(y);
		
		return intersection;
	}
	
	public static ArrayList<Double> getInterOneZeroSlope(ArrayList<ArrayList<Integer>> zeroSlopeSeg, double slope, double yInter){
		ArrayList<Double> intersection = new ArrayList<Double>();
		
		// get x or y from zeroSlopeSeg
		ArrayList<Double> setCoord = setCoordZero(zeroSlopeSeg);
		
		// x = (y - b) / a
		// y = ax + b
		if (setCoord.get(0) != 0) {
			intersection.add(setCoord.get(0));
			Double y = slope * setCoord.get(0) + yInter;
			intersection.add(y);
		}
		else if (setCoord.get(1) != 0) {
			Double x = (setCoord.get(1) - yInter) / slope;
			intersection.add(x);
			intersection.add(setCoord.get(1));
		}
		
		else 
			System.out.println("invalid setCoord: "+setCoord);
		
		return intersection;
		
	}
	
	public static ArrayList<Double> getInterAllZeroSlope(ArrayList<ArrayList<Integer>> ab, ArrayList<ArrayList<Integer>> cd){
		ArrayList<Double> intersection = new ArrayList<Double>();
		
		ArrayList<Double> abSetCoord = setCoordZero(ab);
		ArrayList<Double> cdSetCoord = setCoordZero(cd);
		
		Double x = null;
		Double y = null;
		
		if (abSetCoord.get(0) != 0 && cdSetCoord.get(1) != 0) {
			x = abSetCoord.get(0);
			y = cdSetCoord.get(1);
		}
		
		else if (cdSetCoord.get(0) != 0 && abSetCoord.get(1) != 0) {
			x = cdSetCoord.get(0);
			y = abSetCoord.get(1);
		}
		
		else
			System.out.println("invalid SetCoord: "+abSetCoord+", "+cdSetCoord);
		
		intersection.add(x);
		intersection.add(y);
		
		return intersection;
	}
	
	public static ArrayList<Double> setCoordZero(ArrayList<ArrayList<Integer>> zeroSlopeSeg){
		ArrayList<Double> setCoord = new ArrayList<Double>();
		
		ArrayList<Integer> ab = zeroSlopeSeg.get(0);
		ArrayList<Integer> cd = zeroSlopeSeg.get(1);
		
		if(ab.get(0) == cd.get(0)) {
			setCoord.add(new Double(ab.get(0)));
			setCoord.add(0.0);
		}
		else if(ab.get(1) == cd.get(1)) {
			setCoord.add(0.0);
			setCoord.add(new Double(ab.get(1)));
		}
		else
			System.out.println("invalid zeroSlopeSeg: "+ab+", "+cd);
		
		return setCoord;
			
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
