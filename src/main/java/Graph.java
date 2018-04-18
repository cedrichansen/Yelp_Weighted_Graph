import java.util.ArrayList;

public class Graph {
    HashTable ht;
    Node start;
    Node end;

    public Graph() {
        ht = new HashTable(300000);
    }

    public Graph(ArrayList <Node> nodes){
        ht = new HashTable(300000);
        for (Node n: nodes){
            ht.add(n);
        }
    }




}
