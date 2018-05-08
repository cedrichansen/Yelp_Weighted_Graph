import java.io.IOException;
import java.util.ArrayList;

public class Main {

    final static int NUM_BUSINESSES = 10000;

    public static void main(String[] args) {


        try {
            Graph graph = new Graph();
            //assignEdgesAndWriteNodes(g, NUM_BUSINESSES);
            graph.readAndCreateGraph();
            //g.writeAllEdgesFromLoadedGraph(NUM_BUSINESSES);
            graph.recoverEdges();

            //int numDisjoint = graph.numberOfDisjointSets(graph, 0);



            System.out.println("\nGraph has been read!!!");




        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /*
   *
   * method below takes all businesses, adds them to graph, and then proceeds to look through
   * the entire graph to find the closest 4 other nodes (based on haversine formula, and then writes all
   * of the nodes to the "Nodes" file, so that the edges are already known when loading the file upon program
   * startup
   *
   */
    public static void assignEdgesAndWriteNodes(Graph g, int numberBusinesses) throws IOException {
        ArrayList<YelpData> businesses = ReadJson.readFromJson(numberBusinesses, "../business.json");
        for (YelpData y : businesses) {
            g.add(new Node(y));
        }

        g.AssignEdges();

        for (int i = 0; i < NUM_BUSINESSES; i++) {
            g.write(g.nodes[i]);
            g.writeEdges(g.nodes[i]);
        }
    }


}
