package lab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MaxFlow.java
 */


public class MaxFlow {
	/**
	 * Return codes:
	 * 		-1 no source on the map
	 * 		-2 no destination on the map
	 * 		-3 if both source and destination points are not on the map
	 * 		-4 if no path can be found between source and destination
	 * 	MAXINT if sources identical to destinations
	 */
	public static final int NO_SOURCE_FOUND = -1;
	public static final int NO_DESTINATION_FOUND = -2;
	public static final int NO_SOURCE_DESTINATION_FOUND = -3;
	public static final int NO_PATH = -4;
	public static final int SOURCES_SAME_AS_DESTINATIONS = Integer.MAX_VALUE;

    private Graph graph = new Graph();

	/**
	 * The constructor, setting the name of the file to parse.
	 * 
	 * @param filename the absolute or relative path and filename of the file
	 */
	public MaxFlow(final String filename) {
        Pattern edgePattern =
                Pattern.compile("(\\w+)\\s*->\\s*(\\w+)\\s*\\[.*label=\"(\\d+)\".*\\];");

        ArrayList<String> lines = readFile(filename);

        for (String line: lines){
            Matcher m = edgePattern.matcher(line);
            if(m.matches()){ // It's an edge
                String from = m.group(1);
                String to = m.group(2);
                int capacity = Integer.valueOf(m.group(3));

                if(!graph.hasVertex(from)){
                    graph.addVertex(from);
                }
                if(!graph.hasVertex(to)){
                    graph.addVertex(to);
                }

                Vertex fromV = graph.getVertex(from);
                Vertex toV = graph.getVertex(to);

                graph.addEdge(fromV, toV, capacity);
            }
        }
	}

    /**
     * Reads a given file
     * @param filename name of the file in the project dir
     * @return ArrayList<String> with every line of the file
     */
    private ArrayList<String> readFile(String filename) {
        ArrayList<String> lines = new ArrayList<String>();

        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = in.readLine()) != null) {
                lines.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }


	/**
	 * Calculates the maximum number of cars able to travel the graph specified
	 * in filename.
	 *
	 * @param sources a list of all source nodes
	 * @param destinations a list of all destination nodes
	 * @return 	the maximum number of cars able to travel the graph,
	 * 			NO_SOURCE_FOUND if no source is on the map
	 * 			NO_DESTINATION_FOUND if no destination is on the map
	 * 			NO_SOURCE_DESTINATION_FOUND if both - no source and no destination - are not on the map
	 * 			NO_PATH if no path can be found
	 * 			SOURCES_SAME_AS_DESTINATIONS if sources == destinations 
	 */
	public final int findMaxFlow(final String[] sources, final String[] destinations) {
		boolean noSource = false;
        boolean noDest = false;

        for (String source: sources){
            if(!graph.hasVertex(source)){
                noSource = true;
            }
        }
        for (String dest: destinations){
            if(!graph.hasVertex(dest)){
                noDest = true;
            }
        }

        if (noSource && noDest){
            return NO_SOURCE_DESTINATION_FOUND;
        }
        if (noSource){
            return NO_SOURCE_FOUND;
        }
        if (noDest){
            return NO_DESTINATION_FOUND;
        }

        if(sources.length == destinations.length){
            Arrays.sort(sources);
            Arrays.sort(destinations);
            if(Arrays.equals(sources, destinations)){
                return SOURCES_SAME_AS_DESTINATIONS;
            }
        }

        createSuperVertices(sources, destinations);
        graph.setSourceAndDestinations(sources, destinations);

        // Check if there is a path
        ArrayList<Edge> nextPath = findPath();
        if (nextPath == null){
            return NO_PATH;
        }



        while(true){
            // Find path
            nextPath = findPath();
            if (nextPath == null){
                break;
            }

            // Get max Cap on path
            int maxFlow = Integer.MAX_VALUE;
            for (Edge e: nextPath){
                if (e.getRemainingCapacity() < maxFlow){
                    maxFlow = e.getRemainingCapacity();
                }
            }

            // Add flow along the path
            for (Edge e: nextPath){
                e.addFlow(maxFlow);
            }
        }

		return 0;
	}

    private ArrayList<Edge> findPath() {
        HashMap<Vertex,Vertex> paths = new HashMap<>(); // save the predecessor for every vertex
        ArrayList<Vertex> queue = new ArrayList<>();

        Vertex destinationVertex = graph.getVertex("superDestination");
        Vertex currentVertex = null;
        Vertex lastVertex = null;

        //System.out.println("Destination Vertex: " + destinationVertex.getName());

        // Insert start vertex
        queue.add(graph.getVertex("superSource"));

        // Clear 'visited' status on the vertices
        graph.clearStatus();

        while (!queue.isEmpty()){
            currentVertex = queue.get(0);
            queue.remove(0);

            currentVertex.setVisited(true);

            if(currentVertex == destinationVertex){
                break;
            }
            else{
                for (Edge e: currentVertex.getOutgoingEdges()){
                    if (e.hasCapacityLeft()){ // Only use available edges (has remaining capacity)
                        // Add new Vertex to queue
                        queue.add(e.to);
                        paths.put(e.to,currentVertex);
                    }
                }
            }
        }

        if (currentVertex != destinationVertex){
            return null;
        }
        else{
            ArrayList<Edge> finalPath = new ArrayList<>();
            Vertex current = currentVertex;
            Vertex predecessor = paths.get(currentVertex);

            while (predecessor != null){
                //System.out.println("Current: " + current.getName());
                //System.out.println("Predecessor: " + predecessor.getName());
                Edge e = graph.getEdge(predecessor, current);
                finalPath.add(e);
                current = predecessor;
                predecessor = paths.get(current);
            }

            // Get the path in the right order
            Collections.reverse(finalPath);

            //System.out.println("Final path:");
            //for(Edge e: finalPath){
            //    System.out.println(e.from.getName() + " -> " + e.to.getName());
            //}
            //System.out.println("Ende.");
            return finalPath;
        }

    }

    /**
     * Creates two super vertices to use the FF algorith with multiple sources/targets
     * @param sources
     * @param destinations
     */
    private void createSuperVertices(String[] sources, String[] destinations) {
        graph.addVertex("superSource");
        graph.addVertex("superDestination");
        Vertex superSource = graph.getVertex("superSource");
        Vertex superDestination = graph.getVertex("superDestination");

        for (String source: sources){
            Vertex sourceVertex = graph.getVertex(source);
            graph.addEdge(superSource, sourceVertex, Integer.MAX_VALUE);
        }
        for (String dest: destinations){
            Vertex destVertex = graph.getVertex(dest);
            graph.addEdge(destVertex, superDestination, Integer.MAX_VALUE);
        }
    }

    /**
	 * Calculates the graph showing the maxFlow.
	 *
	 * @param sources a list of all source nodes
	 * @param destinations a list of all destination nodes
	 * @return a ArrayList of Strings as specified in the task in dot code
	 */
	public final ArrayList<String> findResidualNetwork(final String[] sources,	final String[] destinations) {
        findMaxFlow(sources, destinations);
        ArrayList<String> dotGraph = graph.toDot();

        return dotGraph;
	}

}