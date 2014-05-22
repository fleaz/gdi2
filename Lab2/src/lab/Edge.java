package lab;

/**
 * Created by fleaz on 5/19/14.
 */
public class Edge {
    Vertex from,to;
    int length, speedLimit;

    public Edge(Vertex from, Vertex to, int length, int speedLimit){
        this.from = from;
        this.to = to;
        this.length = length;
        this.speedLimit = speedLimit;

        from.connectOutgoingEdge(this);
    }

    public Vertex getFrom(){
        return this.from;
    }

    public Vertex getTo(){
        return this.to;
    }

    public int getLength(){
        return this.length;
    }

    public int getSpeedLimit(){
        return this.speedLimit;
    }
}
