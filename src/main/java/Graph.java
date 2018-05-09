import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

import java.util.ArrayList;

public class Graph {
    Node[] nodes;
    final static String NODE_FILE = "Nodes";
    final static String EDGE_FILE = "Edges";
    final static int NODESIZE = 512;
    final static int EDGESIZE = 32;

    public Graph() {
        nodes = new Node[200000];
    }

    public boolean add(Node n) {
        if (nodes[n.IDNumber] == null) {
            nodes[n.IDNumber] = n;
            return true;
        }
        return false;
    }


    /*
    *
    * gets the number of Nodes actually in the graph
    *
     */
    public int getNumberOfElements() {
        int count = 0;
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                count++;
            }
        }
        return count;
    }

    public int getIndexOfLastElement() {
        int finalIndex = 0;
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                finalIndex = i;
            }
        }
        return finalIndex;
    }







    /*
    *
    * Goes through all nodes and looks to find the other 4 closest nodes and adds them as edges
    *
     */

    public void AssignEdges() {
        for (int i = 0; i < getNumberOfElements(); i++) {

            Node current = nodes[i];

            //where the actual 4 best values will be stores
            Node[] closest4 = new Node[4];

            //The extra spot is there so when sorting, I can always chop out the worst element
            //assign all 5 spots initially to first 5 elements
            Node[] closest5 = new Node[5];
            boolean match = false;
            for (int a = 0; a < closest5.length; a++) {
                if (a == i) {
                    //basically just skip over duplicates
                    match = true;
                }
                if (match) {
                    closest5[a] = this.nodes[a + 1];
                } else {
                    closest5[a] = this.nodes[a];
                }
            }

            for (int j = 0; j < getNumberOfElements() - 1; j++) {
                Node otherNode = this.nodes[j];
                if (i != j && !alreadyInClosest4(closest5, otherNode)) {
                    closest5[4] = otherNode;
                    current.getDistanceTo(closest5[4]);
                    Arrays.sort(closest5);
                    closest4 = removeLastElement(closest4, closest5);
                    System.out.println("Getting Edges for elem: " + i + " --> Currently looking at elem. " + j);
                }
            }

            // now that the closest 4 elements are known, add to list of edges
            for (int k = 0; k < current.edges.length; k++) {
                current.edges[k] = new Node.Edge(closest4[k], closest4[k].haversin);
            }
        }
    }

    public Node[] removeLastElement(Node[] small, Node[] big) {
        for (int i = 0; i < small.length; i++) {
            small[i] = big[i];
        }
        return small;
    }

    public boolean alreadyInClosest4(Node[] n, Node d) {

        for (int i = 0; i < n.length; i++) {
            if (n[i] == d) {
                return true;
            }
        }
        return false;
    }

    public void recoverEdges() throws IOException {
        for (int i = 0; i < this.getNumberOfElements(); i++) {
            Node current = nodes[i];
            System.out.println("Recovering neighbours for node " + i);
            for (int j = 0; j < current.edges.length; j++) {
                current.edges[j].dest = this.nodes[current.edges[j].dest.IDNumber];
            }
        }
    }


    public void Dijkstra(Node start) throws IOException {
        System.out.println("Getting spanning tree for: " + start.yd.name + " --- ID: " + start.IDNumber);
        clearPaths();
        PriorityQueue<Node> unVisited = new PriorityQueue<Node>();
        start.minDistance = 0;
        unVisited.add(start);

        while (!unVisited.isEmpty()) {

            Node current = unVisited.poll();

            for (Node.Edge neighbour : current.edges) {

                Double newDistance = current.minDistance + neighbour.weight;

                Node toLook = neighbour.dest;

                if (toLook.minDistance > newDistance) {

                    Node searchingNode = neighbour.dest;

                    unVisited.remove(searchingNode);
                    searchingNode.minDistance = newDistance;

                    ///*
                    //searchingNode.path = new LinkedList<Node>(current.path);
                    searchingNode.path.clear();
                    searchingNode.path.addAll(current.path);
                    searchingNode.path.add(current);
                    toLook.parent = current;
                    unVisited.add(searchingNode);
                }
            }
        }
        System.out.println("Done finding paths");
    }

    public ArrayList<Node> pathFromTo(Node src, Node dst)throws IOException{
        Dijkstra(src);
        for (int i = 0; i<this.getNumberOfElements(); i++){
            if (dst.path.size()!=0){
                return new ArrayList<Node>(dst.path);
            }
        }
        return null;
    }

    public void clearPaths() {
        for (int i = 0; i < getNumberOfElements(); i++) {
            if (this.nodes[i].path.size() != 0) {
                this.nodes[i].path.clear();
            }
        }
    }


    public int numberOfDisjointSets(Graph g, int uniqueSets) throws IOException {
        System.out.println("Working on disjoint sets..." + " Num Unique Sets: " + uniqueSets);
        System.out.println("num elements in current graph: " + g.getNumberOfElements());

        Node first = getFirstNode(g, uniqueSets);
        if (first == null || g.getNumberOfElements()==0) {
            return uniqueSets;
        }
        Dijkstra(first);
        Graph smaller = new Graph();
        for (int i = first.IDNumber; i < g.getIndexOfLastElement(); i++) {
            if (g.nodes[i] != null) {
                if (g.nodes[i].path.size() == 0) {
                    smaller.add(g.nodes[i]);
                }
            }
        }
        uniqueSets++;
        return numberOfDisjointSets(smaller, uniqueSets);

    }

    public Node getFirstNode(Graph g, int index) {
        for (int i = index; i < g.nodes.length; i++) {
            if (g.nodes[i] != null) {
                return g.nodes[i];
            }
        }
        return null;
    }


    public void writeEdges(Node n) throws IOException {
        System.out.println("writing edges for " + n.toString());

        RandomAccessFile file = new RandomAccessFile(EDGE_FILE, "rw");
        file.seek(n.IDNumber * EDGESIZE);
        FileChannel fc = file.getChannel();
        ByteBuffer bb = ByteBuffer.allocate(EDGESIZE);

        bb.putInt(n.edges[0].dest.IDNumber);
        bb.putInt(n.edges[1].dest.IDNumber);
        bb.putInt(n.edges[2].dest.IDNumber);
        bb.putInt(n.edges[3].dest.IDNumber);

        bb.flip();
        fc.write(bb);
        bb.clear();
        fc.close();
        file.close();

    }


    public void writeAllEdgesFromLoadedGraph(int NUM_BUSINESSES) throws IOException {
        for (int i = 0; i < NUM_BUSINESSES; i++) {
            this.writeEdges(this.nodes[i]);
        }
    }


    public void write(Node n) throws IOException {
        try {
            RandomAccessFile file = new RandomAccessFile(NODE_FILE, "rw");
            file.seek(n.IDNumber * NODESIZE);
            FileChannel fc = file.getChannel();
            ByteBuffer bb = ByteBuffer.allocate(NODESIZE);

            bb.putInt(n.IDNumber);

            System.out.println("writing node: " + n.IDNumber);

            YelpData current = n.yd;
            //write the name of the current yelp object
            byte[] name = current.name.getBytes();
            bb.putInt(name.length);
            bb.put(name);

            //write the id of current yelpObject
            byte[] id = current.id.getBytes();
            bb.putInt(id.length);
            bb.put(id);

            //write city
            byte[] city = current.city.getBytes();
            bb.putInt(city.length);
            bb.put(city);

            //add lat and long
            bb.putDouble(current.lattitude);
            bb.putDouble(current.longitude);


            String[] categories = new String[current.categories.size()];
            current.categories.toArray(categories);
            bb.putInt(current.numCategories);

            // add categories for each yelpdata item
            for (int j = 0; j < current.numCategories; j++) {
                byte[] catBytes = current.categories.get(j).getBytes();
                bb.putInt(catBytes.length);
                bb.put(catBytes);
            }

            //put dest id of edges so reader knows where to go read, and put weight
            bb.putInt(n.edges.length);
            for (int i = 0; i < n.edges.length; i++) {
                bb.putInt(n.edges[i].dest.IDNumber);
                bb.putDouble(n.edges[i].weight);
            }

            bb.flip();
            fc.write(bb);
            bb.clear();
            fc.close();
            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static Node read(long location) throws IOException {
        try {
            System.out.println("Reading Node: " + (int) location);
            Node n = new Node(null);
            RandomAccessFile file = new RandomAccessFile(NODE_FILE, "rw");
            file.seek(location * NODESIZE);
            FileChannel fc = file.getChannel();
            ByteBuffer bb = ByteBuffer.allocate(NODESIZE);
            fc.read(bb);
            bb.flip();

            n.IDNumber = bb.getInt();

            //read name for yelpdata
            int nameLen = bb.getInt();
            byte[] nameBuf = new byte[nameLen];
            bb.get(nameBuf);
            String name = new String(nameBuf);

            //read id
            int idLen = bb.getInt();
            byte[] idBuf = new byte[idLen];
            bb.get(idBuf);
            String id = new String(idBuf);

            //get the city
            int cityLen = bb.getInt();
            byte[] cityBuf = new byte[cityLen];
            bb.get(cityBuf);
            String city = new String(cityBuf);

            Double lattitude = bb.getDouble();
            Double longitude = bb.getDouble();

            int numCategories = bb.getInt();
            ArrayList<String> categories = new ArrayList<String>();

            //get all of the categories
            for (int k = 0; k < numCategories; k++) {
                int currCatLen = bb.getInt();
                byte[] catBuf = new byte[currCatLen];
                bb.get(catBuf);
                categories.add(new String(catBuf));
            }

            //once everything has been read from the file, add the object to the node
            YelpData yd = new YelpData(name, id, city, lattitude, longitude);
            yd.categories = categories;

            n.yd = yd;

            int numEdges = bb.getInt();
            for (int i = 0; i < numEdges; i++) {
                n.edges[i] = new Node.Edge(new Node(null));
                n.edges[i].dest.IDNumber = bb.getInt();
                n.edges[i].weight = bb.getDouble();
            }

            bb.clear();
            fc.close();
            file.close();
            return n;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    public void readAndCreateGraph() throws IOException {
        for (int i = 0; i < Main.NUM_BUSINESSES; i++) {
            this.nodes[i] = Graph.read((long) i);
            this.nodes[i].yd.location = i;
        }
    }


}
