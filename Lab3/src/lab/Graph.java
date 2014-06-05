package lab;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fleaz on 6/5/14.
 */
public class Graph {
    private HashMap<String, Vertex> vertices = new HashMap<String, Vertex>();
    private ArrayList<Edge> edges = new ArrayList<Edge>();

    public Graph(){

    }

    public void addEdge(Vertex from, Vertex to, int capacity){
        Edge e = new Edge(from, to, capacity);
        edges.add(e);
    }

    public void addVertex(String name){
        Vertex v = new Vertex(name);
        vertices.put(name, v);
    }
    
}
