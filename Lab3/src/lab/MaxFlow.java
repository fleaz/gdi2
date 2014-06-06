package lab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
                System.out.println(source + " not in Graph");
                noSource = true;
            }
        }
        for (String dest: destinations){
            if(!graph.hasVertex(dest)){
                System.out.println(dest + " not in Graph");
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

        // Hier kommt jetzt der Ford-Volker
		return 0;
	}
	
	/**
	 * Calculates the graph showing the maxFlow.
	 *
	 * @param sources a list of all source nodes
	 * @param destinations a list of all destination nodes
	 * @return a ArrayList of Strings as specified in the task in dot code
	 */
	public final ArrayList<String> findResidualNetwork(final String[] sources,	final String[] destinations) {
		//TODO Add you code here
		return null; // dummy, replace
	}

}