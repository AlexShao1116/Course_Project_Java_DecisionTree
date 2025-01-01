package finalproject;
import finalproject.system.Tile;
import java.util.ArrayList;

public class TilePriorityQ {
	public ArrayList<Tile> minHeap;

	public TilePriorityQ(ArrayList<Tile> vertices) {
		this.minHeap = new ArrayList<>();
		for (Tile vertex : vertices) {
			insert(vertex);
		}
	}

	private void buildHeap() {
		int startIndex = parent(minHeap.size() - 1);
		for (int i = startIndex; i >= 0; i--) {
			minHeapify(i);
		}
	}

	private void minHeapify(int index) {
		int smallest = index;
		int left = 2 * index + 1;
		int right = 2 * index + 2;

		if (left < minHeap.size() && minHeap.get(left).getCostEstimate() < minHeap.get(smallest).getCostEstimate()) {
			smallest = left;
		}

		if (right < minHeap.size() && minHeap.get(right).getCostEstimate() < minHeap.get(smallest).getCostEstimate()) {
			smallest = right;
		}

		if (smallest != index) {
			swap(index, smallest);
			minHeapify(smallest);
		}
	}

	private void swap(int i, int j) {
		Tile temp = minHeap.get(i);
		minHeap.set(i, minHeap.get(j));
		minHeap.set(j, temp);
	}

	private int parent(int index) {
		return (index - 1) / 2;
	}

	private void decreaseKey(int index) {
		while (index > 0 && minHeap.get(parent(index)).getCostEstimate() > minHeap.get(index).getCostEstimate()) {
			swap(index, parent(index));
			index = parent(index);
		}
	}

	private void insert(Tile tile) {
		minHeap.add(tile);
		int currentIndex = minHeap.size() - 1;
		decreaseKey(currentIndex);
	}

	public Tile removeMin() {
		if (minHeap.isEmpty()) {
			return null;
		}
		Tile minTile = minHeap.get(0);
		int lastIndex = minHeap.size() - 1;
		minHeap.set(0, minHeap.get(lastIndex));
		minHeap.remove(lastIndex);
		if (!minHeap.isEmpty()) {
			minHeapify(0);
		}
		return minTile;
	}

	public void updateKeys(Tile t, Tile newPred, double newEstimate) {
		for (int i = 0; i < minHeap.size(); i++) {
			Tile tile = minHeap.get(i);
			if (tile.equals(t)) {
				tile.setPredecessor(newPred);
				tile.setCostEstimate(newEstimate);
				decreaseKey(i); // Adjust the position of the tile in the heap if necessary
				break;
			}
		}
	}
}

