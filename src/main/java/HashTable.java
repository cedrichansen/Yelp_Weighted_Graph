import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;





public class HashTable {

    /*
    *
    *
    *
    *I modified some stuff in here so if it breaks go back to old ht
    *
    *
    *
    */

    LinkedList<Node>[] table;
    static double count = 0;

    public HashTable(int size) {

        table = new LinkedList[size];
    }

    public Node add(Node n) {

        int hash = n.yd.hashify(this);

        if (this.table[hash] == null) {
            this.table[hash] = new LinkedList<Node>();
            this.table[hash].add(n);
        } else {
            this.table[hash].add(n);
        }

        count++;
        if ((count/(double)table.length) > 0.75) {
            this.resize();
        }
        return n;
    }


    public boolean contains(Node n, HashTable ht) {
        int hash = n.yd.hashify(ht);

        if (ht.table[hash] == null) {
            return false;
        }

        for (Node nd : ht.table[hash]){
            if (nd.yd.name.equals(n.yd.name)){
                return true;
            }
        }

        return false;
    }


    public void resize() {
        HashTable biggerHT = new HashTable(this.table.length * 2);

        for (int i = 0; i < this.table.length; i++) {
            if (this.table[i] != null) {
                for (Node nd : this.table[i]) {
                    biggerHT.add(nd);
                }
            }
        }
        table = biggerHT.table;
    }

}
