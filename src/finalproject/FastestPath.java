package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

import java.util.ArrayList;

public class FastestPath extends PathFindingService {
    //TODO level 6: find time prioritized path
    public FastestPath(Tile start) {
        super(start);
        generateGraph();
    }
             // Ensure the graph is generated upon object creation
	@Override
	public void generateGraph() {
        ArrayList<Tile> reachableTiles = GraphTraversal.BFS(source); // Use BFS to get reachable tiles
        double weight = 0;
        // Create a new graph based on the reachableTiles
        ArrayList<Tile> vertices = new ArrayList<>(reachableTiles);
        g = new Graph(vertices);
        for(Tile t: vertices){
            t.setCostEstimate(t.timeCost);
        }
        // Build edges in the graph based on distance cost (use isWalkable() to determine connections)
        for (Tile tile : reachableTiles) {
            ArrayList<Tile> neighbors = tile.neighbors;
            for (Tile neighbor : neighbors) {
                if (neighbor.isWalkable()) {
                    if (tile instanceof MetroTile && neighbor instanceof MetroTile) {
                        ((MetroTile) tile).fixMetro(neighbor);
                        weight = ((MetroTile) neighbor).timeCost; // Use metro weights if both tiles are MetroTiles
                    } else {
                        weight = neighbor.timeCost; // Use distance cost as weight
                    }
                    g.addEdge(tile, neighbor, weight);
                }
            }
        }
	}

}
