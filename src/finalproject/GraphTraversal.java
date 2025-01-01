package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;

public class GraphTraversal {

	// BFS traversal using ArrayList
	public static ArrayList<Tile> BFS(Tile start) {
		ArrayList<Tile> visited = new ArrayList<>();
		ArrayList<Tile> toVisit = new ArrayList<>();
		toVisit.add(start);

		while (!toVisit.isEmpty()) {
			Tile current = toVisit.remove(0);
			if (!visited.contains(current) && current.isWalkable()) {
				visited.add(current);
				for (Tile neighbor : current.neighbors) {
					if (!visited.contains(neighbor)) {
						toVisit.add(neighbor);
					}
				}
			}
		}
		return visited;
	}

	// DFS traversal using recursion
	public static ArrayList<Tile> DFS(Tile start) {
		ArrayList<Tile> visited = new ArrayList<>();
		dfsRecursive(start, visited);
		return visited;
	}

	private static void dfsRecursive(Tile current, ArrayList<Tile> visited) {
		if (!visited.contains(current) && current.isWalkable()) {
			visited.add(current);
			for (Tile neighbor : current.neighbors) {
				dfsRecursive(neighbor, visited);
			}
		}
	}
}