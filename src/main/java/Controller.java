import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class Controller {

    Means means;

    private ArrayList<YelpData> list;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchField;
    Graph g;
    @FXML
    TableView<YelpData> table;
    @FXML
    TableColumn<YelpData, String> businessName;
    @FXML
    TableColumn<YelpData, String> city;
    @FXML
    TableColumn<YelpData, String> lattitude;
    @FXML
    TableColumn<YelpData, String> longitude;
    @FXML
    TableColumn<YelpData,String> id;

    @FXML
    TableView<YelpData> spanningTreeTable;
    @FXML
    TableColumn<YelpData, String> spanningName;
    @FXML
    TableColumn<YelpData, String> spanningCity;
    @FXML
    TableColumn<YelpData, String> spanningLongitude;
    @FXML
    TableColumn <YelpData, String> spanningLattitude;


    @FXML
    TableView<YelpData> pathTable;

    @FXML
    TableColumn<YelpData, String> pathBusinessName;

    @FXML
    TableColumn<YelpData, String> pathCity;

    @FXML
    TableColumn<YelpData, String> pathLattitude;

    @FXML
    TableColumn<YelpData, String> pathLongitude;







    public void searchSpanningTree(ActionEvent event) throws Exception {

        for ( int i = 0; i<spanningTreeTable.getItems().size(); i++) {
            spanningTreeTable.getItems().clear();

        }
        YelpData test = new YelpData(null, table.getSelectionModel().getSelectedItem().id, null, 0,0);
        Node seeker = null;
        for (int i=0; i<g.getNumberOfElements(); i++){
            if (g.nodes[i].yd.id.equals(test.id)){
                seeker = g.nodes[i];
                break;
            }
        }

        g.createSpanningTree(seeker);
        ArrayList<YelpData> spanningNodes = new ArrayList<YelpData>();

        for (int i=0; i<g.getNumberOfElements(); i++){
            if (g.nodes[i].path.size()!=0){
                spanningNodes.add(g.nodes[i].yd);
            }
        }

        ObservableList<YelpData> data = FXCollections.observableArrayList(spanningNodes);
        //spanningName.setCellValueFactory(new PropertyValueFactory<>("name"));
        spanningName.setCellValueFactory(new PropertyValueFactory<YelpData, String>("name"));
        spanningCity.setCellValueFactory(new PropertyValueFactory<YelpData, String>("city"));
        spanningLattitude.setCellValueFactory(new PropertyValueFactory<YelpData, String>("lattitude"));
        spanningLongitude.setCellValueFactory(new PropertyValueFactory<YelpData, String>("longitude"));
        spanningTreeTable.getItems().addAll(data);

    }


    public void displayPath(ActionEvent event) throws Exception{
        for ( int i = 0; i<pathTable.getItems().size(); i++) {
            pathTable.getItems().clear();
        }

        YelpData test = new YelpData(null, table.getSelectionModel().getSelectedItem().id, null, 0,0);
        Node source = null;
        for (int i=0; i<g.getNumberOfElements(); i++){
            if (g.nodes[i].yd.id.equals(test.id)){
                source = g.nodes[i];
                break;
            }
        }

        test = new YelpData(null, spanningTreeTable.getSelectionModel().getSelectedItem().id, null, 0,0);
        Node dest = null;
        for (int i=0;  i<g.getNumberOfElements(); i++){
            if (g.nodes[i].yd.id.equals(test.id)){
                dest = g.nodes[i];
                break;
            }
        }

        ArrayList<Node> path = new ArrayList<Node>();
        for (int i= 0; i<g.getNumberOfElements(); i++){
            if (g.nodes[i] == dest){
                path.addAll(g.nodes[i].path);
            }
        }

        path.add(path.size(), dest);

        for (Node n: path){
            System.out.println(n);
        }

        System.out.println();

        ArrayList<YelpData> pathBusinesses = new ArrayList<YelpData>();
        for (Node n: path){
            pathBusinesses.add(n.yd);
        }

        ObservableList<YelpData> data = FXCollections.observableArrayList(pathBusinesses);
        pathBusinessName.setCellValueFactory(new PropertyValueFactory<YelpData, String>("name"));
        pathCity.setCellValueFactory(new PropertyValueFactory<YelpData, String>("city"));
        pathLattitude.setCellValueFactory(new PropertyValueFactory<YelpData, String>("lattitude"));
        pathLongitude.setCellValueFactory(new PropertyValueFactory<YelpData, String>("longitude"));
        pathTable.getItems().addAll(data);

        System.out.println();






    }




    public void initialize() throws Exception{

        Graph graph = new Graph();
        graph.readAndCreateGraph();
        graph.recoverEdges();
        g = graph;
        ArrayList<YelpData> b = new ArrayList<YelpData>();

        for (int i =0; i<g.getNumberOfElements(); i++){
            b.add(g.nodes[i].yd);
        }


        list = b;
        final ObservableList<YelpData> data = FXCollections.observableArrayList(list);

        businessName.setCellValueFactory(
                new PropertyValueFactory<YelpData, String>("name"));

        city.setCellValueFactory(
                new PropertyValueFactory<YelpData, String>("city"));

        id.setCellValueFactory(
                new PropertyValueFactory<YelpData, String>("id")
        );

        lattitude.setCellValueFactory(
                new PropertyValueFactory<YelpData, String>("lattitude"));

        longitude.setCellValueFactory(
                new PropertyValueFactory<YelpData, String>("longitude"));

        table.getItems().addAll(data);

        System.out.println();

    }



}