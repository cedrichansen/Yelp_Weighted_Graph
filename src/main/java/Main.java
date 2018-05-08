import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    final static int NUM_BUSINESSES = 10000;

    public static void main(String[] args) {

            launch(args);

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

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("screen.fxml"));
        primaryStage.setTitle("CSC365 Hw03");
        primaryStage.setScene(new Scene(root, 1200, 771));
        primaryStage.show();
    }


}
