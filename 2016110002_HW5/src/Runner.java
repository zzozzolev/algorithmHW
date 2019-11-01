import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class Runner {

	public static void main(String[] args) throws NumberFormatException, IOException {
		String inputPath = "/Users/poza/jam/2019_2/algorithm_analysis/input.txt";
		FileReader fileReader = new FileReader(inputPath);
		BufferedReader bufferReader = new BufferedReader(fileReader);
		
		// make matrix from given inputs
		int n = Integer.parseInt(bufferReader.readLine().trim());
		
		int[][] dist = new int[n][n];
		for(int i=0; i < n; i++) {
			String[] splited = bufferReader.readLine().trim().split(" ");
			for(int j=0; j < n; j++)
				dist[i][j] = Integer.parseInt(splited[j]);
		}
		
		// init c 
		HashMap<ArrayList<Integer>, HashMap<Integer, Integer>> c = new HashMap<ArrayList<Integer>, HashMap<Integer, Integer>>();
		ArrayList<Integer> initS = new ArrayList<Integer>(){{add(1);}};
		updateC(c, initS, 1);
		
		for (int i = 2; i <= n; i++) {
			int[] arr = new int[n];
			ArrayList<ArrayList<Integer>> subsets = new ArrayList<ArrayList<Integer>>();
			getCombis(arr, 0, n, i-1, 1, i-1, subsets);
			
			// for all subsets S of size i and containing 1
			for(int j=0; j < subsets.size(); j++) {
				ArrayList<Integer> s = subsets.get(j);
				// c(s, 1) = inf
				updateC(c, s, i);
				// for all end included in subsets
				for(int k=0; k < s.size(); k++) {
					int end = s.get(k);
					// end 1 not allowed
					if (end == 1)
						continue;
					else {
						ArrayList<Integer> subtracted = (ArrayList<Integer>) s.clone();
						subtracted.remove(end);
						// get min dist for all second last
						int minDist = Integer.MAX_VALUE;
						for (int l=0; l < subtracted.size(); l++) {
							int secondLast = subtracted.get(l);
							if (dist[secondLast][end] == -1)
								continue;
							else {
								int pairDist = c.get(subtracted).get(l);
								if (pairDist < minDist)
									minDist = pairDist;
							}
						}
						c.get(s).put(end, minDist);
					}
				}
			}
					
		}
		int minDist = getMinDistfromStart(c, n, dist);
		System.out.println(minDist);
	}
	public static void getCombis(int[] arr, int index, int n, int r, int target, int origin_r, ArrayList<ArrayList<Integer>> subsets) { 
		if (r == 0) {
			ArrayList<Integer> combi = new ArrayList<Integer>();
			// always include 1
			combi.add(1);
			for(int i=0; i<origin_r; i++)
				combi.add(arr[i]);
			subsets.add(combi);
		}
		else if (target == n) 
			return; 
		else { 
			arr[index] = target; 
			getCombis(arr, index + 1, n, r - 1, target + 1, origin_r, subsets); 
			getCombis(arr, index, n, r, target + 1, origin_r, subsets); 
		} 
	}
	
	public static void updateC(HashMap<ArrayList<Integer>, HashMap<Integer, Integer>> c, 
							   ArrayList<Integer> s,
							   int size){
		int value = -1;
		if (size == 1) 
			value = 0;
		else
			value = Integer.MAX_VALUE;
		
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
		hm.put(1, value);
		
		c.put(s, hm);
	}
	
	public static int getMinDistfromStart(HashMap<ArrayList<Integer>, HashMap<Integer, Integer>> c, int n, int[][] dist) {
		ArrayList<Integer> all = new ArrayList<Integer>();
		for (int i=1; i <= n; i++)
			all.add(i);
		
		int minDist = Integer.MAX_VALUE;
		for (int j=2; j <= n; j++) {
			// j and start 1 not connected
			if (dist[j][1] == -1)
				continue;
			int secondLastDist = c.get(all).get(j);
			
			if (secondLastDist < minDist)
				minDist = secondLastDist + dist[j][1];
		}
		
		return minDist;
	}
}
