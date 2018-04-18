import javafx.scene.Parent;

import java.lang.reflect.Array;
import java.util.*;

public class Means {

    int numberOfClusters = 10;
    ArrayList<Point> points;
    ArrayList<Cluster> clusters;

    public Means(){
        points = new ArrayList<Point>();
        clusters = new ArrayList<Cluster>();

    }

    public void addPoint(Point p) {
        points.add(p);
    }


    //create clusters with random points as center points
    public void initializeMeans(){
        Random r = new Random();
        for (int i = 0; i<numberOfClusters; i++){
            Cluster c = new Cluster(i);
            Point center = points.get(r.nextInt(points.size()));
            c.center = center;
            clusters.add(c);
        }
    }

    void clearClusters(){
        for (Cluster c : clusters){
            c.pointsInCluster.clear();
        }
    }

    ArrayList<Point> getCenters(){
        ArrayList<Point> centers = new ArrayList<Point>();
        for (Cluster c : clusters) {
            Point p = new Point(c.center.lattitude, c.center.longitude);
            centers.add(p);
        }
        return centers;
    }


    void findCluster(){

        double max = Double.MAX_VALUE;
        double min = max;

        int clusterNum = 0;
        double distance = 0;

        for (Point p: points){
            min = max;
            for (int i = 0; i<numberOfClusters; i++) {
                Cluster c = clusters.get(i);
                distance = p.getDistance(c.center);
                if (distance < min){
                    min = distance;
                    clusterNum = i;
                }
            }
            p.cluster = clusterNum;
            clusters.get(clusterNum).addPoint(p);
        }
    }



    /// this method might give weird stuff with lattitude and longitude
    void calculateCenter(){
        for (Cluster c: clusters){
            double x = 0;
            double y=0;
            ArrayList<Point> pointsInCluster = c.pointsInCluster;
            for (Point p: pointsInCluster){
                x += p.lattitude;
                y += p.longitude;
            }

            Point center = c.center;
            if (pointsInCluster.size()>0){
                double a = x/pointsInCluster.size();
                double b = y/pointsInCluster.size();
                center.lattitude = a;
                center.longitude = b;
            }
        }
    }


    Point getPoint(String s) {
        for (Point p: points){
            if (p.name.equals(s)){
                return p;
            }
        }
        return null;
    }

    public Point getClosestPoint(Point p){
        double min = Double.MAX_VALUE;
        Point r = null;
        for (Point x : points){

            //make sure same point isnt the closest point to itself
            if (p.yd.lattitude != x.yd.lattitude) {
                if (p.yd.longitude != x.yd.longitude) {

                    double distance = p.getDistance(x);
                    if (distance < min) {
                        r = x;
                        min = distance;
                    }
                }

            }

        }
        return r;
    }

    void calculate(){
        boolean finished = false;

        int l =0;
        while (!finished){
            System.out.println("calculating.... " + l);
            clearClusters();
            ArrayList<Point> oldCenters = getCenters();
            findCluster();
            calculateCenter();
            ArrayList<Point>  currentCenters = getCenters();

            double distance = 0;
            for (int i = 0; i< oldCenters.size(); i++){
                distance += oldCenters.get(i).getDistance(currentCenters.get(i));
            }

            System.out.println("current distance is: " + distance);
            if (distance == 0) {
                finished = true;
            }
            l++;
        }
    }



    ArrayList<YelpData> getClusterFromYD(YelpData yd){
        ArrayList<YelpData> ydInSameCluster = new ArrayList<YelpData>();
        for (Cluster currentCluster: clusters) {
            for (Point p: currentCluster.pointsInCluster){
                if (p.yd.hash == yd.hash && p.yd.name.equals(yd.name)) {
                    ydInSameCluster = this.addClusterYD(currentCluster);
                }
            }
        }
        return ydInSameCluster;
    }

    ArrayList<YelpData> addClusterYD(Cluster c) {
        ArrayList<YelpData> x = new ArrayList<YelpData>();
        for (Point p: c.pointsInCluster) {
            x.add(p.yd);
            System.out.println(p.yd.toString());
        }
        return x;
    }



}
