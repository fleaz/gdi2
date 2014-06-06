package lab;

import java.util.ArrayList;

public class Vertex {
    ArrayList<Edge> outgoingEdges = new ArrayList<Edge>();
    String name;
    boolean visited = false;
    boolean source = false;
    boolean destination = false;

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

    public boolean isVisited(){
        return this.visited;
    }

    public void setVisited(boolean status){
        this.visited = status;
    }

    public void setSource() {
        //System.out.println("Mark " + this.name + " as source");
        this.source = true;
    }

    public void setDestination() {
        //System.out.println("Mark " + this.name + " as Dest");
        this.destination = true;
    }

    public String toDot(){
        if(! (source || destination)){
            return null;
        }

        StringBuilder tmp = new StringBuilder();
        tmp.append(this.getName());
        tmp.append(" [shape=");

        if(source){
            tmp.append("doublecircle") ;
        }
        if(destination){
            tmp.append("circle") ;
        }
        tmp.append("[style=bold];");

        return tmp.toString();
    }
}