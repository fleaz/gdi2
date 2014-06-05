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

    public double getTravelTime(){
        // Duration to drive along this edge
        // (speed / length) / 60 to get minutes
        double time = ((double)this.length / (double)this.speedLimit) * 60.0;
        return time;
    }

    public String toDot(){
        // E -> F [label="4,60"];
        StringBuilder tmp = new StringBuilder();
        tmp.append(from.getName());
        tmp.append(" -> ");
        tmp.append(to.getName());
        tmp.append(" [label=\"");
        tmp.append(length);
        tmp.append(",");
        tmp.append(speedLimit);
        tmp.append("\"];");

        return tmp.toString();
    }

    public String toDotBold(){
        // E -> F [label="4,60"];
        StringBuilder tmp = new StringBuilder();
        tmp.append(from.getName());
        tmp.append(" -> ");
        tmp.append(to.getName());
        tmp.append(" [label=\"");
        tmp.append(length);
        tmp.append(",");
        tmp.append(speedLimit);
        tmp.append("\"][style=bold];");

        return tmp.toString();
    }
}
