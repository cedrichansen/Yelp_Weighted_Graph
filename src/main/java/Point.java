public class Point {

    double lattitude, longitude;
    String name;
    int cluster;
    YelpData yd;

    public Point(YelpData y){
        lattitude = y.lattitude;
        longitude = y.longitude;
        yd = y;
    }

    public double getDistance(Point center){
        return Math.sqrt((Math.pow((this.lattitude- center.lattitude),2)) +
                (Math.pow((this.longitude- center.longitude),2)));
    }

    public String toString() {
        return "[" + lattitude + "]" + "[" + longitude + "]"  + yd.toString();
    }

}
