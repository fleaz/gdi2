package lab;

import java.util.ArrayList;

/**
 * Created by fleaz on 6/5/14.
 */
public class Vertex {
    ArrayList<Edge> outgoingEdges = new ArrayList<Edge>();
    String name;

    public Vertex(String name){
        this.name = name;
    }

    // Add a new outgoing Edge to this vertex
    public void connectOutgoingEdge(Edge edge){
        this.outgoingEdges.add(edge);
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Edge> getOutgoingEdges(){
        return this.outgoingEdges;
    }
}