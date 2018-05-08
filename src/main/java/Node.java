import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Node implements Comparable {

    YelpData yd;
    Edge[] edges;
    static int totalNumNodes = 0;
    int IDNumber;
    double haversin;
    double minDistance = Double.POSITIVE_INFINITY;
    Node parent;
    LinkedList<Node> path;


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

        public String toString(){
            return "Name: " + dest.yd.name + " //////   Weight: " + weight;
        }
    }


    public Node (YelpData y) {
        yd = y;
        IDNumber = totalNumNodes++;
        haversin = Double.MAX_VALUE;
        edges = new Edge[4];
        path = new LinkedList<Node>();

    }

    // this below is needed for assigning all the edges - which is currently never used because everything
    // has already been written to file

    /*public int compareTo(Node other){
        return (int)this.haversin - (int)other.haversin;
    } */


    //for comparing weights for dijkstras
    @Override
    public int compareTo(Object o) {
        Node other = (Node)o;
        return Double.compare(other.minDistance, this.minDistance);
    }



    void addEdge(Node n) {

        for (int i =0; i<edges.length; i++){
            if (edges[i] != null){
                edges[i] = new Edge(n, n.haversin);
            }
        }
    }

    //Return an Arraylist of Nodes rather than Edges
    public ArrayList<Node> getNeighbouringNodes() throws IOException{
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (Edge e: edges) {
            nodes.add(Graph.read(e.dest.IDNumber));
        }
        return nodes;
    }


    //use this function to call getDistanceToOther Node. A bit easier this way
    double getDistanceTo(Node other){
        double distance = getDistanceToOtherNode(this.yd.lattitude, this.yd.longitude, other.yd.lattitude, other.yd.longitude);
        other.haversin = distance;
        return distance;
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
        return "Name: "+ yd.name + " --> Id: " + IDNumber;
    }




}
