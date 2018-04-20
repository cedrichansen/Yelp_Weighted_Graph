import java.util.ArrayList;

public class Main {

    public static void main (String [] args){
        System.out.println("Hello world");

        ArrayList<YelpData> businesses = ReadJson.readFromJson(1000,"../business.json");
        Graph g = new Graph();
        for (YelpData y:businesses){
            g.add(new Node(y), businesses.indexOf(y));
        }


        /// code below just checks the distance stuff between 2 nodes
        Node a = g.nodes[1];
        Node b = g.nodes[2];
        System.out.println("\nDistance from node A to B: "+ a.getDistanceTo(b) + "\n");
        ////




        System.out.println("done adding stuff to ht");

    }

}
