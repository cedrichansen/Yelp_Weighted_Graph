import java.util.ArrayList;

public class Main {

    public static void main (String [] args){
        System.out.println("Hello world");

        ArrayList<YelpData> businesses = ReadJson.readFromJson(100,"../business.json");
        Graph g = new Graph();
        for (YelpData y:businesses){
            g.add(new Node(y));
        }

        g.AssignEdges();




        System.out.println("Done adding to graph and edges have been assigned!");

    }

}
