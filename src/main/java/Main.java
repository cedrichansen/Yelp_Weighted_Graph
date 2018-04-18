import java.util.ArrayList;

public class Main {

    public static void main (String [] args){
        System.out.println("Hello world");

        ArrayList<YelpData> businesses = ReadJson.readFromJson(1000,"../business.json");
        Graph g = new Graph();
        for (YelpData y:businesses){
            g.ht.add(new Node(y));
        }

        System.out.println("done adding stuff to ht");

    }

}
