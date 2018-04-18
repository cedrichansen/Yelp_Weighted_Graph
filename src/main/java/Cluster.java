import java.util.ArrayList;

public class Cluster {

    ArrayList<Point> pointsInCluster;
    Point center;
    int id;

    public Cluster(int id){
        this.id = id;
        pointsInCluster = new ArrayList<Point>();
        center = null;
    }

    public void addPoint(Point p){
        pointsInCluster.add(p);
    }

    public void setCenter(Point c) {
        center = c;
    }

    public void printPoints() {
        for (Point p : pointsInCluster){
            System.out.println(p.toString());
        }
    }




}
