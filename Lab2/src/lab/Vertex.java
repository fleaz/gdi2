package lab;

import java.util.ArrayList;

/**
 * Created by fleaz on 5/19/14.
 */
public class Vertex {
    ArrayList<Edge> outgoingEdges = new ArrayList<Edge>();
    String name;
    int delay;

    public Vertex(String name, int delay){
        this.name = name;
        this.delay = delay;

    }

    // Add a new outgoing Edge to this vertex
    public void connectOutgoingEdge(Edge edge){
        this.outgoingEdges.add(edge);
    }

    public String getName(){
        return this.name;
    }

    public int getDelay(){
        return this.delay;
    }
}
