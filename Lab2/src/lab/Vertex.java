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

    public ArrayList<Edge> getOutgoingEdges(){
        return this.outgoingEdges;
    }

    public String toDot(){
        // A [label="A,0"];
        StringBuilder tmp = new StringBuilder();
        tmp.append(name);
        tmp.append(" [label=\"");
        tmp.append(name);
        tmp.append(",");
        tmp.append(delay);
        tmp.append("\"];");

        return tmp.toString();
    }
}




















