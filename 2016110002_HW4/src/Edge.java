
public class Edge {
	int left;
	int right;
	int weight;
	
	public Edge(int left, int right) {
		this.left = left;
		this.right = right;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		return String.format("(%d, %d), w:%d", this.left, this.right, this.weight);
	}
}
