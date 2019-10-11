import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class Runner {

	public static void main(String[] args) throws IOException {
		String inputPath = "/Users/poza/jam/2019_2/algorithm_analysis/input.txt";
		FileReader fileReader = new FileReader(inputPath);
		BufferedReader bufferReader = new BufferedReader(fileReader);
		
		String outputPath = "/Users/poza/jam/2019_2/algorithm_analysis/2016110002.txt";
		File file = new File(outputPath);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
		
		int nCase = Integer.parseInt(bufferReader.readLine().trim());
		
		for(int n=1; n <= nCase; n++) {
			ArrayList<Edge> edges = new ArrayList<Edge>();
			int totalMinCut = -1;
			
			String[] splited = bufferReader.readLine().trim().split(" ");
			
			int vertexNum = Integer.parseInt(splited[0]);
			int edgeNum = Integer.parseInt(splited[1]);
			
			// init graph
			for(int i = 0; i < edgeNum; i++) {
				String[] leftRight = bufferReader.readLine().trim().split(" ");
				int left = Integer.parseInt(leftRight[0]);
				int right = Integer.parseInt(leftRight[1]);
				edges.add(new Edge(left, right));
			}		
			
			// getMinCut n*(n-1)/2 times
			int nIter = (vertexNum * (vertexNum - 1)) / 2;
			ArrayList<ArrayList<Integer>> totalMixed = new ArrayList<ArrayList<Integer>>(); 
			int randomSeed = 0;
			
			for(int i = 0; i < nIter; i++) {
				int[] seedAndMinCut = getMinCut(edges, vertexNum, randomSeed, totalMixed);
				randomSeed = seedAndMinCut[0];
				int minCut = seedAndMinCut[1];
				if (minCut == -1) 
					continue;
				if (minCut < totalMinCut || totalMinCut == -1)
					totalMinCut = minCut;
			}
			bufferedWriter.write(String.format("#%d %d", n, totalMinCut));
			bufferedWriter.newLine();
		}
		bufferReader.close();
		bufferedWriter.close();	
	}
	public static int[] getMinCut(ArrayList<Edge> edges, int vertexNum, int randomSeed, ArrayList<ArrayList<Integer>> totalMixed) {
		int[] parents = new int[vertexNum];
		int[] ranks = new int[vertexNum];
		ArrayList<Edge> mst = new ArrayList<Edge>();
		
		// init parents
		initParents(parents);
		
		// assign weights to the edges randomly
		randomSeed = assignWeights(edges, randomSeed, totalMixed);
		
		// sort the edges by weight in ascending order
		sortEdges(edges);
		
		// iterate edges until only two vertex remains (mst size n-2)
		for(int i = 0; mst.size() < vertexNum - 2; i++) {
			if (i == edges.size()) {
				System.out.println("i == edges.size()");
				break;
			}
			Edge e = edges.get(i);
			
			if (find(e.left, parents) != find(e.right, parents)) {
				mst.add(e);
				unionByRank(e, parents, ranks);
			}
		}
		
		boolean existUnConnected = isExistUnConnected(parents);
		
		int[] seedAndMinCut = new int[2];
		seedAndMinCut[0] = randomSeed;
		
		if (existUnConnected) 
			seedAndMinCut[1] = -1;
		
		else 
			seedAndMinCut[1] = getDiffParentVertexNum(parents, edges);
		
		return seedAndMinCut;
		
	}
	
	public static void initParents(int[] parents) {
		for(int i = 0; i < parents.length; i++) {
			parents[i] = i;
		}
	}
	
	public static int assignWeights(ArrayList<Edge> edges, int randomSeed, ArrayList<ArrayList<Integer>> totalMixed) {
		ArrayList<Integer> mixed;
		boolean flag = false;
		// get mixed index until mixed is not in totalMixed
		do {
			mixed = getMixed(randomSeed, edges.size());
		  	// if mixed is already in totalMixed 
		  	if (totalMixed.contains(mixed)) {
		  		flag = true;
		  	}
		  	else {
		  		totalMixed.add(mixed);
		  		flag = false;
		  	}
		  	randomSeed ++;
		  	
		} while(flag);
		
		for(int i = 0; i < edges.size(); i++) {
			Edge e = edges.get(mixed.get(i));
			e.setWeight(i+1);
		}
		
		return randomSeed;
	}
	
	public static ArrayList<Integer> getMixed(int randomSeed, int edgeSize) {
		Random r = new Random(randomSeed);
		ArrayList<Integer> index = new ArrayList<Integer>();
		ArrayList<Integer> mixed = new ArrayList<Integer>();
		
		for(int i=0; i < edgeSize; i++) {
			index.add(i);
		}
		
		for(int i=index.size(); i>0; i--) {
			int randInt = r.nextInt(i);
			mixed.add(index.get(randInt));
			index.remove(randInt);
		}
		
		return mixed;
	}
	
	public static void sortEdges(ArrayList<Edge> edges) {
		Collections.sort(edges, new Comparator<Edge>() {
			public int compare(Edge e1, Edge e2) {
				if (e1.weight >= e2.weight) return 1;
				else return -1;
			}
		});
	}
	
	public static void unionByRank(Edge e, int[] parents, int[] ranks) {
		int parentL = find(e.left, parents);
		int parentR = find(e.right, parents);
		
		// different group
		if (parentL != parentR) {
			// assign parent of lower rank node to higher rank node 
			if (ranks[parentL] > ranks[parentR]) {
				parents[parentR] = parentL;
			}
			else {
				parents[parentL] = parentR;
				
				// increase rank by one
				if (ranks[parentL] == ranks[parentR]) 
					ranks[parentR] += 1;	
			}
		}
		
	}
	
	public static int find(int vertex, int[] parents) {
		// path compression
		if (parents[vertex] != vertex) {
			parents[vertex] = find(parents[vertex], parents);
		}
		return parents[vertex];
	}
	
	public static boolean isExistUnConnected(int[] parents) {
		HashMap<Integer, Integer> counter = new HashMap<Integer, Integer>();
		for(int parent: parents) {
			if (counter.containsKey(parent)) {
				int num = counter.get(parent);
				counter.put(parent, num+1);
			}
			else
				counter.put(parent, 1);
		}
		
		// there are only two group
		if (counter.size() == 2) {
			for(int num: counter.values()) {
				// unconnected
				if (num == 1)
					return true;
			}
		}
		
		return false;
	}
	
	public static int getDiffParentVertexNum(int[] parents, ArrayList<Edge> edges) {
		int diffNum = 0;
		for(Edge e: edges) {
			if (parents[e.left] != parents[e.right])
				diffNum += 1;	
		}
		return diffNum;
	}
}
