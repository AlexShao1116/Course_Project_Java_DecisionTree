package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;

public class Graph {
    private ArrayList<Tile> vertices;
    private ArrayList<Edge> edges;

    public Graph(ArrayList<Tile> vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
    }

    public void addEdge(Tile origin, Tile destination, double weight) {
        Edge edge = new Edge(origin, destination, weight);
        edges.add(edge);
    }

    public ArrayList<Edge> getAllEdges() {
        return edges;
    }
    public ArrayList<Tile> getVertices() {
        return vertices;
    }
    public ArrayList<Tile> getNeighbors(Tile t) {
        ArrayList<Tile> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getStart().equals(t)) {
                neighbors.add(edge.getEnd());
            }
        }
        return neighbors;
    }

    public double computePathCost(ArrayList<Tile> path) {
        double totalCost = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            Tile current = path.get(i);
            Tile next = path.get(i + 1);
            Edge edge = findEdge(current, next);
            totalCost += edge != null ? edge.weight : 0.0;
        }
        return totalCost;
    }

    public Edge findEdge(Tile start, Tile end) {
        for (Edge edge : edges) {
            if (edge.getStart().equals(start) && edge.getEnd().equals(end)) {
                return edge;
            }
        }
        return null;
    }

    public static class Edge {
        public Tile origin;
        public Tile destination;
        public double weight;

        public Edge(Tile origin, Tile destination, double weight) {
            this.origin = origin;
            this.destination = destination;
            this.weight = weight;
        }

        public Tile getStart() {
            return origin;
        }
        public double getWeight() {
            return weight;
        }
        public Tile getEnd() {
            return destination;
        }
    }
}