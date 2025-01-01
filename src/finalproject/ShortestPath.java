package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

import java.util.ArrayList;


public class ShortestPath extends PathFindingService {

    public ShortestPath(Tile start) {
        super(start);
        generateGraph(); // Ensure the graph is generated upon object creation
    }

    @Override
    public void generateGraph() {
        ArrayList<Tile> reachableTiles = GraphTraversal.BFS(source); // Use BFS to get reachable tiles

        // Create a new graph based on the reachableTiles
        ArrayList<Tile> vertices = new ArrayList<>(reachableTiles);
        double weight = 0;
        g = new Graph(vertices);
        for (Tile t : vertices) {
            t.setCostEstimate(t.distanceCost);
        }
        // Build edges in the graph based on distance cost (use isWalkable() to determine connections)
        for (Tile tile : reachableTiles) {
            ArrayList<Tile> neighbors = tile.neighbors;
            for (Tile neighbor : neighbors) {
                if (neighbor.isWalkable()) {
                    if (tile instanceof MetroTile && neighbor instanceof MetroTile) {
                        ((MetroTile) tile).fixMetro(neighbor);
                        weight = ((MetroTile) tile).distanceCost; // Use metro weights if both tiles are MetroTiles
                    } else {
                        weight = neighbor.distanceCost; // Use distance cost as weight
                    }
                    g.addEdge(tile, neighbor, weight);
                }
            }
        }
    }

}