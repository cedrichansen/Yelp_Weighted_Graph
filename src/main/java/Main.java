import java.util.ArrayList;

public class Main {

    public static void main (String [] args){
        System.out.println("Hello world");

        ArrayList<YelpData> businesses = ReadJson.readFromJson(1000,"../business.json");
        HashTable ht = new HashTable(16);
        for (YelpData y:businesses){
            ht.add(new Node(y));
        }

        System.out.println("done adding stuff to ht");



    }

}
