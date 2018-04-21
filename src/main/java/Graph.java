import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Graph {
    Node [] nodes;
    Node start;
    Node end;

    public Graph() {
        nodes = new Node [200000];
    }

    public boolean add(Node n){
        if (nodes[n.IDNumber]== null) {
            nodes[n.IDNumber]=n;
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
        for (int i = 0; i<getNumberOfElements(); i++) {

            Node current = nodes[i];

            //where the actual 4 best values will be stores
            Node [] closest4 = new Node[4];

            //The extra spot is there so when sorting, I can always chop out the worst element
            //assign all 5 spots initially to first 5 elements
            Node [] closest5 = new Node[5];
            boolean match = false;
            for (int a = 0; a<closest5.length; a++){
                if (a==i){
                    //basically just skip over duplicates
                    match = true;
                }
                if (match){
                    closest5[a] = this.nodes[a+1];
                } else {
                    closest5[a] = this.nodes[a];
                }
            }

            for(int j = 0; j<getNumberOfElements(); j++){
                Node otherNode = this.nodes[j];
                if (i != j && !alreadyInClosest4(closest5, otherNode)){
                    closest5[4] = otherNode;
                    current.getDistanceTo(closest5[4]);
                    Arrays.sort(closest5);
                    closest4 = removeLastElement(closest4, closest5);
                    System.out.println("Getting Edges for elem: " + i + " --> Currently looking at elem. " + j);
                }
            }

            for (int k=0; k<current.edges.length; k++){
                current.edges[k] = new Node.Edge(closest4[k], closest4[k].haversin);
            }
        }
    }

    public Node [] removeLastElement(Node [] small, Node [] big){
        for (int i = 0; i<small.length; i++){
            small[i] = big[i];
        }
        return small;
    }

    public boolean alreadyInClosest4(Node [] n, Node d){

        for (int i =0; i<n.length; i++){
            if (n[i]== d){
                return true;
            }
        }
        return false;
    }




}
