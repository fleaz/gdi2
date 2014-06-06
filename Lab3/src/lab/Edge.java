package lab;

import java.util.ArrayList;

public class Edge {
    Vertex from,to;
    int flow, capacity;

    public Edge(Vertex from, Vertex to, int capacity){
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = 0;

        from.connectOutgoingEdge(this);
    }

    public int getFlow(){
        return this.flow;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public void addFlow(int newFlow){
        this.flow += newFlow;
    }

    public int getRemainingCapacity(){
        return (this.capacity - this.flow);
    }

    public boolean hasCapacityLeft(){
        return (this.capacity - this.flow > 0);
    }

    public String toDot() {
        if (from.getName() == "superSource" || to.getName() == "superDestination"){
            return null;
        }
        StringBuilder tmp = new StringBuilder();
        tmp.append(from.getName());
        tmp.append(" -> ");
        tmp.append(to.getName());
        tmp.append(" [label=\"");
        tmp.append(capacity);
        tmp.append("-");
        tmp.append(flow);
        tmp.append("\"]");
        if(hasCapacityLeft()){
            tmp.append("[style=bold]");
        }
        tmp.append(";");

        return tmp.toString();
    }
}