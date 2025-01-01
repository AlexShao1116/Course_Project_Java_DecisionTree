package finalproject;


import finalproject.system.Tile;


import java.util.ArrayList;
import java.util.LinkedList;

public abstract class PathFindingService {
    Tile source;
    Graph g;

    public PathFindingService(Tile start) {
        this.source = start;
    }

    public abstract void generateGraph();

    //初始化
    public ArrayList<Tile> vertices;
    public  ArrayList<Tile> result;
    public ArrayList<Tile> findPath(Tile startNode) {
        initializeSingleSource(g, startNode);
        vertices = new ArrayList<>(g.getVertices());
        TilePriorityQ minHeap = new TilePriorityQ(new ArrayList<>(vertices));
        result = new ArrayList<Tile>();
        result.add(startNode);
        while (!minHeap.minHeap.isEmpty()) {
            Tile u = minHeap.removeMin();
            if (u.equals(vertices.get(vertices.size()-1))) {
               for(Tile t: getPath(startNode, vertices.get(vertices.size()-1))){
                   result.add(t);
               }
               return result;
            }
            for (Tile v : u.neighbors) {
                if(v.isWalkable()) {
                    relax(u, v, g.findEdge(u, v).getWeight(), minHeap);
                }
            }
        }
        return null; // No path found
    }
    private void initializeSingleSource(Graph graph, Tile startNode) {
        for (Tile vertex : graph.getVertices()) {
            vertex.setCostEstimate(Double.MAX_VALUE);
            vertex.setPredecessor(null);
        }
        startNode.setCostEstimate(0);
    }
    // Custom method to reverse an ArrayList
    private static <T> void reverseArrayList(ArrayList<T> list) {
        int size = list.size();
        for (int i = 0; i < size / 2; i++) {
            T temp = list.get(i);
            list.set(i, list.get(size - 1 - i));
            list.set(size - 1 - i, temp);
        }
    }

    // Now, use reverseArrayList in place of Collections.reverse
    private ArrayList<Tile> getPath(Tile startNode, Tile destinationNode) {
        ArrayList<Tile> path = new ArrayList<>();
        Tile current = destinationNode;
        while (current != null) {
            path.add(current);
            current = current.getPredecessor();
            if (current == startNode) {
                reverseArrayList(path);
                return path;
            }
        }
        reverseArrayList(path);
        return path;
    }


    private void relax(Tile u, Tile v, double weightUV, TilePriorityQ minHeap) {
        if (v.getCostEstimate() > u.getCostEstimate() + weightUV) {
            v.setCostEstimate(u.getCostEstimate() + weightUV);
            v.setPredecessor(u);
            minHeap.updateKeys(v, u, v.getCostEstimate());
        }
    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to a known destination
    public ArrayList<Tile> findPath(Tile start, Tile end) {
        initializeSingleSource(g, start);
        vertices = new ArrayList<>(g.getVertices());
        TilePriorityQ minHeap = new TilePriorityQ(new ArrayList<>(vertices));
        ArrayList<Tile> result1 = new ArrayList<Tile>();
        result1.add(start);
        while (!minHeap.minHeap.isEmpty()) {
            Tile u = minHeap.removeMin();
            if (u.equals(end)) {
                for(Tile t: getPath(start, end)){
                    result1.add(t);
                }
                return result1;
            }
            for (Tile v : u.neighbors) {
                if(v.isWalkable()) {
                    relax(u, v, g.findEdge(u, v).getWeight(), minHeap);
                }
            }
        }
        return null;
    }


    //TODO level 5: Implement basic dijkstra's algorithm to path find to the final destination passing through given waypoints
    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {
        vertices = new ArrayList<>(g.getVertices());
        ArrayList<Tile> resultpart = new ArrayList<Tile>();
        result = new ArrayList<Tile>();
        result.add(start);
        for (Tile element : waypoints) {
            resultpart = findPath(result.get(result.size() - 1), element);
            for (int i = 1; i < resultpart.size(); i++) {
                result.add(resultpart.get(i));
            }

        }
        for (Tile t : vertices) {
            if (t.isDestination) {
                resultpart = findPath(result.get(result.size() - 1), t);
                for (int i = 1; i < resultpart.size(); i++) {
                    result.add(resultpart.get(i));
                }

            }
        }
        return result;
    }
}