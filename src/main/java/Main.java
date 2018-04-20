import java.util.ArrayList;

public class Main {

    public static void main (String [] args){
        System.out.println("Hello world");

        ArrayList<YelpData> businesses = ReadJson.readFromJson(1000,"../business.json");
        Graph g = new Graph();
        for (YelpData y:businesses){
            g.ht.add(new Node(y));
        }


        /// code below just checks the distance stuff between 2 nodes
        int index = 0;
        int index2=0;
        for (;index<g.ht.table.length; index++) {
            if (g.ht.table[index] != null) {
                break;
            }
        }
        for (;index2<g.ht.table.length; index2++) {
            if (g.ht.table[index2] != null && index != index2) {
                break;
            }
        }
        Node a = g.ht.table[index].get(0);
        Node b = g.ht.table[index2].get(0);
        System.out.println("\nDistance from node A to B: "+ a.getDistanceTo(b) + "\n");
        ////




        System.out.println("done adding stuff to ht");

    }

}
