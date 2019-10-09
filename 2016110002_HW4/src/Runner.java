import java.util.ArrayList;
import java.util.HashSet;

public class Runner {

	public static void main(String[] args) {
		ArrayList<Edge> edges = new ArrayList<Edge>();
		int absoluteMinCut = -1;
		// init graph
		
		// getMinCut n*(n-1)/2 times
		
		
	}
	public static int getMinCut(ArrayList<Edge> edges) {
		int[] parents;
		int[] ranks;
		HashSet<Integer> uniqueParents = new HashSet<Integer>();
		
		// init parents
		
		// assign random weights to the edges
		
		// sort the edges by weight in ascending order
		
		// iterate edges until only two vertex remains
		
	}
	
	public static void initParents(int[] parents) {
		
	}
	
	public static void assignRandomWeights(ArrayList<Edge> edges) {
		
	}
	
	public static void sortEdges(ArrayList<Edge> edges) {
		
	}
	
	public static void unionByRank(int[] parents, int[] ranks) {
		
	}
	
	public static void find(int vertex, int[] parents) {
		// path compression
	}
	
	public static void getDiffParentVertexNum(int[] parents, ArrayList<Edge> edges) {
		
	}
}
