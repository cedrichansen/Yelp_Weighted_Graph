import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Line;

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


    @FXML
    ScatterChart<?,?> pathGraph;

    @FXML
    LineChart<?,?> graphLineChart;









    public void searchSpanningTree(ActionEvent event) throws Exception {

        for ( int i = 0; i<spanningTreeTable.getItems().size(); i++) {
            spanningTreeTable.getItems().clear();
        }
        int location = table.getSelectionModel().getSelectedIndex();
        Node selection = g.nodes[location];
        g.Dijkstra(selection);


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
        graphLineChart.getData().clear();

        YelpData y = table.getSelectionModel().getSelectedItem();
        int location =y.location;
        Node source = g.nodes[location];


        YelpData d = spanningTreeTable.getSelectionModel().getSelectedItem();
        Node dest = g.nodes[d.location];

        ArrayList<Node> path = new ArrayList<Node>();
        /*for (int i= 0; i<g.getNumberOfElements(); i++){
            if (g.nodes[i] == dest){
                path.addAll(g.nodes[i].path);
            }
        }*/
        path.add(path.size(), dest);

        Node tempN = dest.parent;
        while(tempN!=null){
            path.add(tempN);
            tempN = tempN.parent;
        }


        for (int i = path.size()-1; i>-1; --i){
            System.out.println(path.get(i).yd);
        }


        ArrayList<YelpData> pathBusinesses = new ArrayList<YelpData>();
        for (int i = path.size()-1; i>-1; --i){
            pathBusinesses.add(path.get(i).yd);
        }

        ObservableList<YelpData> data = FXCollections.observableArrayList(pathBusinesses);
        pathBusinessName.setCellValueFactory(new PropertyValueFactory<YelpData, String>("name"));
        pathCity.setCellValueFactory(new PropertyValueFactory<YelpData, String>("city"));
        pathLattitude.setCellValueFactory(new PropertyValueFactory<YelpData, String>("lattitude"));
        pathLongitude.setCellValueFactory(new PropertyValueFactory<YelpData, String>("longitude"));
        pathTable.getItems().addAll(data);


        XYChart.Series pathSeries = new XYChart.Series();
        pathSeries.setName("Businesses in path");
        XYChart.Series treeSeries = new XYChart.Series();
        treeSeries.setName("Spanning tree Businesses");

        for (int i = 0; i<path.size(); i++){
                pathSeries.getData().add(new XYChart.Data(pathBusinesses.get(i).longitude, pathBusinesses.get(i).lattitude));
        }

        ArrayList<YelpData> tree = getSpanningTree();
        ArrayList<XYChart.Series> trees = new ArrayList<XYChart.Series>();
        for (int i = 0; i< tree.size(); i++){
            XYChart.Series temp = new XYChart.Series();
            temp.getData().add(new XYChart.Data(tree.get(i).longitude, tree.get(i).lattitude));
            trees.add(temp);
        }

        graphLineChart.getData().add(pathSeries);
        for (int i = 0; i < trees.size(); i++){
        graphLineChart.getData().add(trees.get(i));
        }

        System.out.println();




    }



    ArrayList<YelpData> getSpanningTree(){
        ArrayList<YelpData> tree = new ArrayList<YelpData>();
        for (int i = 0; i<g.getNumberOfElements(); i++){
            if (g.nodes[i].path.size()!=0){
                tree.add(g.nodes[i].yd);
            }
        }
        return tree;
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