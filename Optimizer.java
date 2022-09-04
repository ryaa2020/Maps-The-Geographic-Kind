package onTheRoad;
import java.util.ArrayList;
import java.util.List;

public class Optimizer {
	
	public static void main(String[] args) {
		// solve shortest path problems
		// take a run-time parameter which is the full path to the file 
		//(in my set-up it is data/sample.txt). 
		// read in the input
			
		FileParser fp = new FileParser(args[0]);
		
		List<String> vertices = fp.getVertices();
		List<TripRequest> tripRequest = fp.getTrips();
		int V = vertices.size();
		
		// build the graph(s) 
		// solve the trip requests in the file.
		EdgeWeightedDigraph graph = new EdgeWeightedDigraph(V);
		
		for (int i = 0; i < tripRequest.size(); i ++) {
			int start = tripRequest.get(i).getStart();
			int end = tripRequest.get(i).getEnd();
			boolean isDistance = tripRequest.get(i).isDistance();	
			graph = fp.makeGraph(isDistance);
			ArrayList<DirectedEdge> result = GraphAlgorithms.getShortestPath(graph, start, end);
			GraphAlgorithms.printShortestPath(result, isDistance, vertices);
		}
		
	}
}