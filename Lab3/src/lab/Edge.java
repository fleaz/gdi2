package lab;

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

    public Vertex getFrom(){
        return this.from;
    }

    public Vertex getTo(){
        return this.to;
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
}