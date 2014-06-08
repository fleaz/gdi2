package lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    private HashMap<String, Vertex> vertices = new HashMap<String, Vertex>();
    private ArrayList<Edge> edges = new ArrayList<Edge>();

    public Graph(){

    }

    public void addEdge(Vertex from, Vertex to, int capacity){
        //System.out.println("Added Edge " + from.getName() + " -> " + to.getName());
        Edge e = new Edge(from, to, capacity, false); // add the normal edge
        Edge eBackward = new Edge(to, from, capacity, true); // add the corresponding backward edge
        edges.add(e);
        edges.add(eBackward);
    }

    public void addVertex(String name){
        //System.out.println("Added Vertex " + name);
        Vertex v = new Vertex(name);
        vertices.put(name, v);
    }

    public boolean hasVertex(String name){
        return vertices.containsKey(name);
    }

    public Vertex getVertex(String name){
        return vertices.get(name);
    }

    public ArrayList<Vertex> getVertices(){
        ArrayList<Vertex> tmp = new ArrayList<>();

        for (Map.Entry<String,Vertex> entry: vertices.entrySet()){
            tmp.add(entry.getValue());
        }

        return tmp;
    }

    public void clearStatus(){
        for(Map.Entry<String,Vertex> entry: vertices.entrySet()){
            entry.getValue().setVisited(false);
        }
    }

    public ArrayList<String> toDot() {
        ArrayList<String> dotGraph = new ArrayList<>();
        dotGraph.add("digraph {");
        for (Edge e: edges){
            if (e.toDot() != null){
                dotGraph.add(e.toDot());
            }

        }
        for(Map.Entry<String,Vertex> entry: vertices.entrySet()){
            if(entry.getValue().toDot() != null){
                dotGraph.add(entry.getValue().toDot());
            }
        }
        dotGraph.add("}");

        //for (String str: dotGraph){
        //    System.out.println(str);
        //}
        return dotGraph;
    }

    public void setSourceAndDestinations(String[] sources, String[] destinations){
        for (String source: sources){
            Vertex sourceVertex = this.getVertex(source);
            sourceVertex.setSource();
        }

        for (String dest: destinations){
            Vertex destVertex = this.getVertex(dest);
            destVertex.setDestination();
        }
    }

    public Edge getEdge(Vertex from, Vertex to) {
        for(Edge e: edges){
            if(e.from.equals(from) && e.to.equals(to)){
                return e;
            }
        }
        return null;
    }
}
