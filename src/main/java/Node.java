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


    double getDistanceTo(Node other){
        return getDistanceToOtherNode(this.yd.lattitude, this.yd.longitude, other.yd.lattitude, other.yd.longitude);
    }

    static double getDistanceToOtherNode(double startLat, double startLong, double endLat, double endLong) {
        final int EARTH_RADIUS = 6371; // Approx Earth radius in KM


        double dLat  = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat   = Math.toRadians(endLat);

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // <-- d
    }

    static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }



    public String toString(){
        return yd.name + " <--> " + IDNumber;
    }




}
