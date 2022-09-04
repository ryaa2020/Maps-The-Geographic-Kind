package onTheRoad;

/**
 * Common algorithms for Graphs. 
 * They all assume working with a EdgeWeightedDirected graph.
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;

public class GraphAlgorithms {

	/**
	 * Reverses the edges of a graph
	 * 
	 * @param g
	 *            edge weighted directed graph
	 * @return graph like g except all edges are reversed
	 */
	public static EdgeWeightedDigraph graphEdgeReversal(EdgeWeightedDigraph g) {
		// create a new weighted diagraph
		EdgeWeightedDigraph myGraph = new EdgeWeightedDigraph(g.V());
		
		// go through each edge
		for (DirectedEdge edge : g.edges()) {
			// create a new edge that is reversed 
			DirectedEdge oppositeEdge = new DirectedEdge(edge.to(), edge.from(), edge.weight());
			// add reversed edge to graph
			myGraph.addEdge(oppositeEdge);
		}
		return myGraph;
	}

	/**
	 * Performs breadth-first search of g from vertex start.
	 * 
	 * @param g
	 *            directed edge weighted graph
	 * @param start
	 *            index of starting vertex for search
	 */
	public static void breadthFirstSearch(EdgeWeightedDigraph g, int start) {
		g.reset(); // reset graph
		
		// create new queue
		Deque<Integer> q = new ArrayDeque<Integer>();
		
		// find out which vertices visited and the distance to get there one at a time
		
		// visiting the first one
		g.visit(new DirectedEdge(start, start, 0.0), 0.0);
		
		// adding the first to the queue
		q.add(start);
		
		while (!q.isEmpty()) {
			// remove the last one
			int v = q.remove();
			for (DirectedEdge e : g.adj(v)) {
				int w = e.to();
				// checking if vertex has been visited
				if (!g.isVisited(w)) { // it has not been visited
					g.visit(e, g.getDist(v)+1);
					q.add(w); // add to the queue
				}
			}
		}
	}

	/**
	 * Calculates whether the graph is strongly connected
	 * 
	 * @param g
	 *            directed edge weighted graph
	 * @return whether graph g is strongly connected.
	 */
	public static boolean isStronglyConnected(EdgeWeightedDigraph g) {
		// do breadthFirstSearch to see which vertices have been visited starting at 0
		breadthFirstSearch(g, 0);
		
		// loop through the vertices
		for (int v = 0; v < g.V(); v++) {
			if (! g.isVisited(v)) { // if it has not been visited  
				return false;
			}	
		}
		
		// reverse the graph
		EdgeWeightedDigraph reverseGraph = graphEdgeReversal(g);
		
		// do breadthFirstSearch to see which vertices have been visited starting at 0
		breadthFirstSearch(reverseGraph, 0);
		
		// loop through the vertices
		for (int v = 0; v < reverseGraph.V(); v++) {
			if (! reverseGraph.isVisited(v)) { // if it has not been visited 
				return false;
			}	
		}
		return true;
	}

	public static void relax(EdgeWeightedDigraph g, DirectedEdge e, IndexMinPQ<Double> pq) {

		// defining start and finish
		int v = e.from(), w = e.to();
		
		// comparing the weights
		if (g.getDist(w) > g.getDist(v) + e.weight()) {
			g.setDist(w, g.getDist(v) + e.weight()); // setting the new distance
			g.setEdgeTo(e); // setting the new edge

			// update the priority queue since the priority queue's size has changed
			if (pq.contains(w)) {
				
				pq.decreaseKey(w, g.getDist(w)); 
			// adding to the priority queue
			} else {
				
				pq.insert(w, g.getDist(w));
			}
		}
	}
	
	/**
	 * Runs Dijkstra's algorithm on path to calculate the shortest path from
	 * starting vertex to every other vertex of the graph.
	 * 
	 * @param g
	 *            directed edge weighted graph
	 * @param s
	 *            starting vertex
	 * @return a hashmap where a key-value pair <i, path_i> corresponds to the i-th
	 *         vertex and path_i is an arraylist that contains the edges along the
	 *         shortest path from s to i.
	 */
	public static HashMap<Integer, ArrayList<DirectedEdge>> dijkstra(EdgeWeightedDigraph g, int s) {
		g.reset(); // reset graph
		IndexMinPQ<Double> pq;
		HashMap<Integer, ArrayList<DirectedEdge>> myHash = new HashMap<Integer, ArrayList<DirectedEdge>>();
		
		// priority queue of vertices
		pq = new IndexMinPQ<Double>(g.V());
		
		// set everything to positive infinity
		for (int v = 0; v < g.V(); v++) {
			g.setDist(v, Double.POSITIVE_INFINITY);
		}
		g.setDist(s, 0.0);

		// relax vertices in order of distance from s
		pq.insert(s, g.getDist(s));
		
		while (!pq.isEmpty()) {
			int v = pq.delMin();
			//System.out.println(g.adj(v).toString());
			for (DirectedEdge e : g.adj(v)) {
				relax(g, e, pq);
			}
		}
		// loop through vertices
		for (int i = 0; i < g.V(); i++) {
			ArrayList<DirectedEdge> myArray  = new ArrayList<DirectedEdge>();
			// check if the distance is less than infinity
			if (g.getDist(i) < Double.POSITIVE_INFINITY) {
				for (DirectedEdge edge = g.getEdgeTo(i); edge != null; edge = g.getEdgeTo(edge.from())) {
					myArray.add(edge);
				}
				// insert into the hashMap
				myHash.put(i, myArray);
			} else {
				myHash.put(i, null);
			}
		}
	return myHash;
	}

	/**
	 * Computes shortest path from start to end using Dijkstra's algorithm.
	 *
	 * @param g
	 *            directed graph
	 * @param start
	 *            starting node in search for shortest path
	 * @param end
	 *            ending node in search for shortest path
	 * @return a list of edges in that shortest path in correct order
	 */
	public static ArrayList<DirectedEdge> getShortestPath(EdgeWeightedDigraph g, int start, int end) {
		// run dijkstra
		HashMap<Integer, ArrayList<DirectedEdge>> djHashMap = dijkstra(g, start);
		
		// create a new array list
		ArrayList<DirectedEdge> myEdgeList = new ArrayList<DirectedEdge>();
		
		// get all the paths from start to end
		ArrayList<DirectedEdge> endPath = djHashMap.get(end);
		// System.out.println(endPath);
		// reverse the path (get in the correct order) 
		// loop through the paths and add to the new arrayList
		for (int i = endPath.size()-1; i >= 0; i--) {
			myEdgeList.add(endPath.get(i));
		}
		
		return myEdgeList;
	}

	/**
	 * Using the output from getShortestPath, print the shortest path
	 * between two nodes
	 * 
	 * @param path shortest path from start to end
	 * @param isDistance prints it based on distance (true) or time (false)
	 */
	public static void printShortestPath(ArrayList<DirectedEdge> path, boolean isDistance, List<String> vertices) {
		// checking if it is looking for distance or time
		if (isDistance) { // when it is distance 
			// variable to calculate total distance
			double distance = 0;
			System.out.println("Shortest distance from " + vertices.get(path.get(0).from()) + " to " + vertices.get(path.get(path.size()-1).to()));
			System.out.println("\tBegin at " + vertices.get(path.get(0).from()));
			for (int i = 0; i < path.size(); i++) { // loop to print out each path 
				System.out.println("\tContinue to " + vertices.get(path.get(i).to()) + " (" + path.get(i).weight() + " miles)");
				distance += path.get(i).weight();
			}
			System.out.println("Total distance: " + distance + " miles");
		} else { // when it is time
			// variable to calculate total time
			double time = 0;
			System.out.println("Shortest driving time from " + vertices.get(path.get(0).from()) + " to " + vertices.get(path.get(path.size()-1).to()));
			System.out.println("\tBegin at " + vertices.get(path.get(0).from()));
			for (int i = 0; i < path.size(); i++) { // loop to print out each path 
				System.out.println("\tContinue to " + vertices.get(path.get(i).to()) + " (" + hoursToHMS(path.get(i).weight()) + ")");
				time += path.get(i).weight();
			}
			System.out.println("Total time: " + hoursToHMS(time));
		}
	}

	/**
	 * Converts hours (in decimal) to hours, minutes, and seconds
	 * 
	 * @param rawhours
	 *            time elapsed
	 * @return Equivalent of rawhours in hours, minutes, and seconds (to nearest
	 *         10th of a second)
	 */
	private static String hoursToHMS(double rawHours) {
		//Find the hours, minutes, and seconds
		int numHours = (int)rawHours;
		double fractionalHours = rawHours - numHours;
		int tenthSeconds = (int)Math.round(fractionalHours * 36000);
		int minutes = tenthSeconds/600;
		int tenthSecondsLeft = tenthSeconds - 600 * minutes;
		double seconds = (double)(tenthSecondsLeft/10);
		
		//Don't show hours if there are zero of them. 
		//Don't show hours or minutes if there are zero of both of them. 
		if (numHours == 0 && minutes == 0) {
			return seconds + " secs";
		} else if (numHours == 0 && minutes > 0) {
			return minutes + " mins " + Double.toString(seconds) + " secs";
		} else {
			return numHours + " hrs " + minutes + " mins " + Double.toString(seconds) + " secs";
		}

	}
}