import java.util.ArrayList;

public class Graph {
    Node [] nodes;
    Node start;
    Node end;

    public Graph() {
        nodes = new Node [200000];
    }

    public boolean add(Node n, int i){
        if (nodes[i]== null) {
            nodes[i]=n;
            return true;
        }
        return false;
    }

    public int getNumberOfElements(){
        int count = 0;
        for (int i =0; i<nodes.length; i++){
            if (nodes[i] != null){
                count ++;
            }
        }
        return count;
    }

    public void AssignEdges(){
        Node [] closest4 = new Node[4];
        Node bad = new Node(null);

        for (int i = 0; i<getNumberOfElements(); i++) {
            for(int j = 0; j<getNumberOfElements(); j++){
                if (i != j){

                }
            }
        }


    }




}
