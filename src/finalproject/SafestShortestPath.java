package finalproject;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

public class SafestShortestPath extends ShortestPath {
	public int health;
	public Graph costGraph;
	public Graph damageGraph;
	public Graph aggregatedGraph;

	//TODO level 8: finish class for finding the safest shortest path with given health constraint
	public SafestShortestPath(Tile start, int health) {
		super(start);
		this.health = health;
	}

	
	public void generateGraph() {
		// TODO Auto-generated method stub
		ArrayList<Tile> reachableTiles = GraphTraversal.BFS(source); // Use BFS to get reachable tiles
		ArrayList<Tile> vertices = new ArrayList<>(reachableTiles);
		double weight = 0;
		double weight2 = 0;
		costGraph = new Graph(vertices);
		damageGraph = new Graph(vertices);
		aggregatedGraph = new Graph(vertices);

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
						weight = ((MetroTile) tile).distanceCost;
						weight2 = ((MetroTile) tile).timeCost;
					} else {
						weight = neighbor.distanceCost; // Use distance cost as weight
						weight2 = neighbor.damageCost;
					}
					costGraph.addEdge(tile, neighbor, weight);
					damageGraph.addEdge(tile, neighbor, weight2);
					aggregatedGraph.addEdge(tile, neighbor, weight2);
				}
			}
		}
	}
	@Override
	public ArrayList<Tile> findPath(Tile startNode, LinkedList<Tile> waypoints) {
		ArrayList<Tile> resultpd = null;
		ArrayList<Tile> resultpc = null;
		ArrayList<Tile> resultpr = null;
		ArrayList<Tile> result = new ArrayList<Tile>();
		double pc = 0;
		double pd = 0;
		double pr = 0;
		//calculate pc
		resultpc = getCostPath(startNode, waypoints);
		resultpd = getDamageGraphPath(startNode, waypoints);
		pc = c(resultpc);
		//calculate c(pc)
		pd = d(resultpc);
		if (pd < health) {
			result = resultpc;
			return result;
		}
		//calculate pd

		//calculate d(pd)
		pd = d(resultpd);
		if (pd > health) {
			return null;
		}
		while(true){
			double lamda = lamda(resultpc, resultpd);
			//calculate pr
			resultpr = getAggregatedGraphPath(startNode, waypoints, lamda);
			pc = r(resultpc);
			pr = r(resultpr);
			if (pr == pc) {
				return resultpd;

			} else if (d(resultpr) <= health) {
				resultpd = resultpr;
			} else {
				resultpc = resultpr;
			}

		}
	}

	private int c (ArrayList<Tile> path){
		int result = 0;
		for(int i = 0;i < path.size()-1;i++){
			result += costGraph.findEdge(path.get(i),path.get(i+1)).weight;
		}
		return result;
	}
	private int r(ArrayList<Tile> path){
		int result = 0;
		for(int i = 0;i < path.size()-1;i++){
			result += aggregatedGraph.findEdge(path.get(i),path.get(i+1)).weight;
		}
		return result;
	}
	private int d (ArrayList<Tile> path){
		int result = 0;
		for(int i = 0;i < path.size()-1;i++){
			result += damageGraph.findEdge(path.get(i),path.get(i+1)).weight;
		}
		return result;
	}
	private double lamda(ArrayList<Tile> resultpc,ArrayList<Tile> resultpd){
		double cc = c(resultpc);
		double cd = c(resultpd);
		double dc = d(resultpc);
		double dd = d(resultpd);
		return Math.abs(cc-cd)/Math.abs(dd-dc);
	}

	private void changeweight(double lamda){
		for(Graph.Edge e:aggregatedGraph.getAllEdges()){
			if (e.getStart() instanceof MetroTile && e.getEnd() instanceof MetroTile) {
				((MetroTile) e.getStart()).fixMetro(e.getEnd());
				double c = ((MetroTile) e.getStart()).distanceCost;
				e.weight = c;
			} else {
				double c = costGraph.findEdge(e.getStart(), e.getEnd()).getWeight();
				double d = damageGraph.findEdge(e.getStart(), e.getEnd()).getWeight();
				e.weight = c + lamda * d;
			}
		}

	}
	private ArrayList<Tile> getDamageGraphPath(Tile startNode,LinkedList<Tile> waypoints){
		g = damageGraph;
		ArrayList<Tile> pdvertices = new ArrayList<>(g.getVertices());
		ArrayList<Tile> resultpart = new ArrayList<Tile>();
		ArrayList<Tile> resultpd= new ArrayList<Tile>();
		resultpd.add(startNode);
		for (Tile element : waypoints) {
			resultpart = findPath(resultpd.get(resultpd.size() - 1), element);
			for (int i = 1; i < resultpart.size(); i++) {
				resultpd.add(resultpart.get(i));
			}

		}
		for (Tile t : pdvertices) {
			if (t.isDestination) {
				resultpart = findPath(resultpd.get(resultpd.size() - 1), t);
				for (int i = 1; i < resultpart.size(); i++) {
					resultpd.add(resultpart.get(i));
				}

			}
		}
		return resultpd;

	}
	private ArrayList<Tile> getCostPath(Tile startNode,LinkedList<Tile> waypoints){
		g = costGraph;
		ArrayList<Tile> resultpart = new ArrayList<Tile>();
		ArrayList<Tile> pcvertices = new ArrayList<>(g.getVertices());
		ArrayList<Tile> resultpc = new ArrayList<Tile>();
		resultpc.add(startNode);
		for (Tile element : waypoints) {
			resultpart = findPath(resultpc.get(resultpc.size() - 1), element);
			for (int i = 1; i < resultpart.size(); i++) {
				resultpc.add(resultpart.get(i));
			}

		}
		for (Tile t : pcvertices) {
			if (t.isDestination) {
				resultpart = findPath(resultpc.get(resultpc.size() - 1), t);
				for (int i = 1; i < resultpart.size(); i++) {
					resultpc.add(resultpart.get(i));
				}

			}
		}
		return resultpc;


	}
	private ArrayList<Tile> getAggregatedGraphPath(Tile startNode,LinkedList<Tile> waypoints,double lamda){
		changeweight(lamda);
		g = aggregatedGraph;

		ArrayList<Tile> resultpart = new ArrayList<Tile>();
		ArrayList<Tile> prvertices = new ArrayList<>(g.getVertices());
		ArrayList<Tile> resultpr = new ArrayList<Tile>();
		resultpr.add(startNode);

		for (Tile element : waypoints) {
			resultpart = findPath(resultpr.get(resultpr.size() - 1), element);
			for (int i = 1; i < resultpart.size(); i++) {
				resultpr.add(resultpart.get(i));
			}

		}
		for (Tile t : prvertices) {
			if (t.isDestination) {
				resultpart = findPath(resultpr.get(resultpr.size() - 1), t);
				for (int i = 1; i < resultpart.size(); i++) {
					resultpr.add(resultpart.get(i));
				}

			}
		}
		return resultpr;


	}
}
