import java.util.ArrayList;

public class Node {

    YelpData yd;
    ArrayList<Edge> edges;
    static int totalNumNodes = 0;
    final int IDNumber;

    public static class Edge{
        Node dest;
        double weight;

        public Edge(Node d, double w){
            dest = d;
            weight = w;
        }

        public Edge(Node d){
            dest = d;
        }
    }


    public Node (YelpData y) {
        yd = y;
        IDNumber = ++totalNumNodes;

    }

    void addEdge(YelpData y) {
        this.edges.add(new Edge(new Node(y)));
    }

    boolean hasEdgeTo(YelpData y){
        return this.edges.contains(new Edge(new Node(y)));
    }

    ArrayList<Node> getNeighbouringNodes(){
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (Edge e: edges) {
            nodes.add(e.dest);
        }
        return nodes;
    }


    public String toString(){
        return yd.name + " <--> " + IDNumber;
    }




}
