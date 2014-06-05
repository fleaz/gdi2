package lab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class Navigation finds the shortest (and/or) path between points on a map
 * using the Dijkstra algorithm
 */
public class Navigation {
	/**
	 * Return codes:
     * -1 if the source is not on the map
     * -2 if the destination is not on the map
     * -3 if both source and destination points are not on the map
     * -4 if no path can be found between source and destination
	 */

	public static final int SOURCE_NOT_FOUND = -1;
	public static final int DESTINATION_NOT_FOUND = -2;
	public static final int SOURCE_DESTINATION_NOT_FOUND = -3;
	public static final int NO_PATH = -4;

    private HashMap<String, Vertex> vertices = new HashMap<String, Vertex>();
    private ArrayList<Edge> edges = new ArrayList<Edge>();
	/**
	 * The constructor takes a filename as input, it reads that file and fill
	 * the nodes and edges Lists with corresponding node and edge objects
	 * 
	 * @param filename
	 *            name of the file containing the input map
	 */
    public Navigation(String filename) {
        Pattern edgePattern =
                Pattern.compile("^(\\w+)\\s->\\s(\\w+)\\s\\[.*label=\"(\\d+),(\\d+)\".*\\];", Pattern.MULTILINE);
        Pattern vertexPattern =
                Pattern.compile("^(\\w+)\\s\\[.*label=\"(\\w+),(\\w+)\".*\\];", Pattern.MULTILINE);

        ArrayList<String> lines = readFile(filename);

        // First get all vertices
        for (String line: lines){
            Matcher m = vertexPattern.matcher(line);
            if (m.matches()){
                // It's a vertex
                String id = m.group(1);
                String name = m.group(2);
                int delay = Integer.valueOf(m.group(3));

                Vertex v = new Vertex(name, delay);
                vertices.put(id, v);
            }
            else{
                continue;
            }
        }

        // When we have every vertex, we can read the edges
        for (String line: lines){
            Matcher m = edgePattern.matcher(line);
            if(m.matches()){
                // It's an edge
                String from = m.group(1);
                String to = m.group(2);
                int length = Integer.valueOf(m.group(3));
                int maxSpeed = Integer.valueOf(m.group(4));

                Vertex fromV = vertices.get(from);
                Vertex toV = vertices.get(to);
                Edge e = new Edge(fromV, toV, length, maxSpeed);
                fromV.connectOutgoingEdge(e);
                edges.add(e);
            }
            else{
                continue;
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
	 * This methods finds the shortest route (distance) between points A and B
	 * on the map given in the constructor.
	 * 
	 * If a route is found the return value is an object of type
	 * ArrayList<String>, where every element is a String representing one line
	 * in the map. The output map is identical to the input map, apart from that
	 * all edges on the shortest route are marked "bold". It is also possible to
	 * output a map where all shortest paths starting in A are marked bold.
	 * 
	 * The order of the edges as they appear in the output may differ from the
	 * input.
	 * 
	 * @param A
	 *            Source
	 * @param B
	 *            Destinaton
	 * @return returns a map as described above if A or B is not on the map or
	 *         if there is no path between them the original map is to be
	 *         returned.
	 */
	public ArrayList<String> findShortestRoute(String A, String B) {
        //Source and target vertex
        Vertex currentVertex = vertices.get(A);
        Vertex targetVertex = vertices.get(B);

        // List of nodes and there shortest distance from A to there
        HashMap<Vertex, Integer> distances = new HashMap<>();
        ArrayList<Vertex> unvisitedVertices = new ArrayList<>();
        HashMap<Vertex,ArrayList<Edge>> paths = new HashMap<>();

        for (Map.Entry<String,Vertex> entry: vertices.entrySet()){
            if(entry.getValue().equals(currentVertex)){
                distances.put(entry.getValue(), 0);
            }
            else{
                distances.put(entry.getValue(), Integer.MAX_VALUE);
            }
            paths.put(entry.getValue(), new ArrayList<Edge>()); // Start with an empty path to every vertex
            unvisitedVertices.add(entry.getValue());
        }
        unvisitedVertices.remove(currentVertex);

        while(currentVertex != null && currentVertex != targetVertex){
            updateListLengthWithPath(currentVertex, distances, paths);
            Vertex nextVertex = findNextByLength(unvisitedVertices, distances);
            unvisitedVertices.remove(nextVertex);
            currentVertex = nextVertex;
        }

        ArrayList<String> dotGraph = new ArrayList<>();
        dotGraph.add("digraph {");
        ArrayList<Edge> finalPath = paths.get(targetVertex);

        for (Edge e: edges){
            if(finalPath.contains(e)){
                dotGraph.add(e.toDotBold());
            }
            else{
                dotGraph.add(e.toDot());
            }
        }

        for(Map.Entry<String,Vertex> entry: vertices.entrySet()){
            dotGraph.add(entry.getValue().toDot());
        }

        dotGraph.add("}");

		return dotGraph;
	}

    private void updateListLengthWithPath(Vertex vertex, HashMap<Vertex, Integer> listOfVertices, HashMap<Vertex,ArrayList<Edge>> paths) {
        ArrayList<Edge> edges = vertex.getOutgoingEdges();
        for(Edge e: edges){
            // Update distance if
            // (distance from source to me + distance from me to target) < last know distance to target

            if ((listOfVertices.get(vertex) + e.length) < listOfVertices.get(e.to)){
                listOfVertices.put(e.to, listOfVertices.get(vertex) + e.length);
                ArrayList<Edge> tmp = new ArrayList<Edge>(paths.get(e.from));
                tmp.add(e);
                paths.put(e.to, tmp);
            }
        }
    }

	/**
	 * This methods finds the fastest route (in time) between points A and B on
	 * the map given in the constructor.
	 * 
	 * If a route is found the return value is an object of type
	 * ArrayList<String>, where every element is a String representing one line
	 * in the map. The output map is identical to the input map, apart from that
	 * all edges on the shortest route are marked "bold". It is also possible to
	 * output a map where all shortest paths starting in A are marked bold.
	 * 
	 * The order of the edges as they appear in the output may differ from the
	 * input.
	 * 
	 * @param A
	 *            Source
	 * @param B
	 *            Destinaton
	 * @return returns a map as described above if A or B is not on the map or
	 *         if there is no path between them the original map is to be
	 *         returned.
	 */
	public ArrayList<String> findFastestRoute(String A, String B) {
        //Source and target vertex
        Vertex currentVertex = vertices.get(A);
        Vertex targetVertex = vertices.get(B);

        // List of nodes and there shortest distance from A to there
        HashMap<Vertex, Double> times = new HashMap<>();
        ArrayList<Vertex> unvisitedVertices = new ArrayList<>();
        HashMap<Vertex,ArrayList<Edge>> paths = new HashMap<>();

        for (Map.Entry<String,Vertex> entry: vertices.entrySet()){
            if(entry.getValue().equals(currentVertex)){
                times.put(entry.getValue(), 0.0);
            }
            else{
                times.put(entry.getValue(), Double.MAX_VALUE);
            }
            paths.put(entry.getValue(), new ArrayList<Edge>()); // Start with an empty path to every vertex
            unvisitedVertices.add(entry.getValue());
        }
        unvisitedVertices.remove(currentVertex);

        while(currentVertex != null && currentVertex != targetVertex){
            updateListTimeWithPath(currentVertex, times, paths);
            Vertex nextVertex = findNextByTime(unvisitedVertices, times);
            unvisitedVertices.remove(nextVertex);
            currentVertex = nextVertex;
        }

        ArrayList<String> dotGraph = new ArrayList<>();
        dotGraph.add("digraph {");
        ArrayList<Edge> finalPath = paths.get(targetVertex);

        for (Edge e: edges){
            if(finalPath.contains(e)){
                dotGraph.add(e.toDotBold());
            }
            else{
                dotGraph.add(e.toDot());
            }
        }

        for(Map.Entry<String,Vertex> entry: vertices.entrySet()){
            dotGraph.add(entry.getValue().toDot());
        }

        dotGraph.add("}");

        return dotGraph;
    }

    private void updateListTimeWithPath(Vertex vertex, HashMap<Vertex, Double> times, HashMap<Vertex,ArrayList<Edge>> paths) {
        ArrayList<Edge> edges = vertex.getOutgoingEdges();
        for(Edge e: edges){
            // Update distance if
            // (distance from source to me + distance from me to target) < last know distance to target

            if ((times.get(vertex) + e.getTravelTime() + e.getFrom().getDelay()) < times.get(e.to)){
                times.put(e.to, times.get(vertex) + e.getTravelTime() + vertex.getDelay());
                ArrayList<Edge> tmp = new ArrayList<Edge>(paths.get(e.from));
                tmp.add(e);
                paths.put(e.to, tmp);
            }
        }
    }

    /**
	 * Finds the shortest distance in kilometers between A and B using the
	 * Dijkstra algorithm.
	 * 
	 * @param A
	 *            the start point A
	 * @param B
	 *            the destination point B
	 * @return the shortest distance in kilometers rounded upwards.
	 *         SOURCE_NOT_FOUND if point A is not on the map
	 *         DESTINATION_NOT_FOUND if point B is not on the map
	 *         SOURCE_DESTINATION_NOT_FOUND if point A and point B are not on the map
     *         NO_PATH if no path can be found between point A and point
	 *         B
	 */
	public int findShortestDistance(String A, String B) {
        //Source and target vertex
        Vertex currentVertex = vertices.get(A);
        Vertex targetVertex = vertices.get(B);

        if (currentVertex == null && targetVertex == null){
            return SOURCE_DESTINATION_NOT_FOUND;
        }
        if (currentVertex == null){
            return SOURCE_NOT_FOUND;
        }
        if(targetVertex == null){
            return DESTINATION_NOT_FOUND;
        }


        // List of nodes and there shortest distance from A to there
        HashMap<Vertex, Integer> distances = new HashMap<>();
        ArrayList<Vertex> unvisitedVertices = new ArrayList<>();

        for (Map.Entry<String,Vertex> entry: vertices.entrySet()){
            if(entry.getValue().getName() == currentVertex.getName()){
                distances.put(entry.getValue(), 0);
            }
            else{
                distances.put(entry.getValue(), Integer.MAX_VALUE);
            }

            unvisitedVertices.add(entry.getValue());
        }
        unvisitedVertices.remove(currentVertex);

        while(currentVertex != null && currentVertex != targetVertex){
            updateListLength(currentVertex, distances);
            Vertex nextVertex = findNextByLength(unvisitedVertices, distances);
            unvisitedVertices.remove(nextVertex);
            currentVertex = nextVertex;
        }

        int ret = distances.get(targetVertex);

        if (ret == Integer.MAX_VALUE){
            return NO_PATH;
        }
        else{
            return ret;
        }
	}

    private void updateListLength(Vertex vertex, HashMap<Vertex, Integer> listOfVertices) {
        ArrayList<Edge> edges = vertex.getOutgoingEdges();
        for(Edge e: edges){
            // Update distance if
            // (distance from source to me + distance from me to target) < last know distance to target
            if ((listOfVertices.get(vertex) + e.length) < listOfVertices.get(e.to)){
                listOfVertices.put(e.to, listOfVertices.get(vertex) + e.length);
            }
        }
    }


    private Vertex findNextByLength(ArrayList<Vertex> unvisitedVertices, HashMap<Vertex, Integer> distances) {
        Integer shortest = Integer.MAX_VALUE;
        Vertex retVertex = null;

        for(Vertex v: unvisitedVertices){
            if (distances.get(v) < shortest){
                shortest = distances.get(v);
                retVertex = v;
            }
        }
        return retVertex;
    }

    /**
	 * Find the fastest route between A and B using the dijkstra algorithm.
	 * 
	 * @param A
	 *            Source
	 * @param B
	 *            Destination
	 * @return the fastest time in minutes rounded upwards. SOURCE_NOT_FOUND if
	 *         point A is not on the map DESTINATION_NOT_FOUND if point B is not
	 *         on the map SOURCE_DESTINATION_NOT_FOUND if point A and point B
	 *         are not on the map NO_PATH if no path can be found between point
	 *         A and point B
	 */
	public int findFastestTime(String A, String B) {
        //Source and target vertex
        Vertex startVertex = vertices.get(A);
        Vertex targetVertex = vertices.get(B);

        if (startVertex == null && targetVertex == null){
            return SOURCE_DESTINATION_NOT_FOUND;
        }
        if (startVertex == null){
            return SOURCE_NOT_FOUND;
        }
        if(targetVertex == null){
            return DESTINATION_NOT_FOUND;
        }
        if(startVertex == targetVertex){
            return 0;
        }

        // List of nodes and there shortest distance from A to there
        HashMap<Vertex, Double> times = new HashMap<>();
        ArrayList<Vertex> unvisitedVertices = new ArrayList<>();

        for (Map.Entry<String,Vertex> entry: vertices.entrySet()){
            if(entry.equals(startVertex)){
                times.put(entry.getValue(), 0.0);
            }
            else{
                times.put(entry.getValue(), Double.MAX_VALUE);
            }

            unvisitedVertices.add(entry.getValue());
        }


        for(Edge e: startVertex.getOutgoingEdges()){
            times.put(e.to, e.getTravelTime());
        }
        unvisitedVertices.remove(startVertex);

        Vertex currentVertex = findNextByTime(unvisitedVertices,times);

        while(currentVertex != null && currentVertex != targetVertex){
            updateListTime(currentVertex, times);
            currentVertex = findNextByTime(unvisitedVertices, times);
            unvisitedVertices.remove(currentVertex);
        }

        Double ret = times.get(targetVertex);

        if (ret == Double.MAX_VALUE){
            return NO_PATH;
        }
        else{
            int newRet = (int)Math.ceil(ret);
            return newRet;
        }
	}

    private void updateListTime(Vertex vertex, HashMap<Vertex, Double> times) {
        ArrayList<Edge> edges = vertex.getOutgoingEdges();
        for(Edge e: edges){
            // Update time if
            // (time from source to me + time from me to target + my delay) < last know time to target
            if ((times.get(vertex) + e.getTravelTime() + e.getFrom().getDelay()) < times.get(e.to)){
                times.put(e.to, times.get(vertex) + e.getTravelTime() + vertex.getDelay());
            }
        }
    }


    private Vertex findNextByTime(ArrayList<Vertex> unvisitedVertices, HashMap<Vertex, Double> times) {
        Double shortest = Double.MAX_VALUE;
        Vertex retVertex = null;

        for(Vertex v: unvisitedVertices){
            if (times.get(v) < shortest){
                shortest = times.get(v);
                retVertex = v;
            }
        }
        return retVertex;
    }

}
