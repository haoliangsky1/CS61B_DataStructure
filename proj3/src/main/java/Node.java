import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by haoliangsky on 4/1/16.
 */
public class Node {
    // This class of object may be used in a graph as the intersections
    long id;
    double latitude;
    double longtitude;
    ArrayList<Node> neighbors;
    ArrayList<Double> distances;
    public String name;

    public Node(long id, double latitude, double longtitude, String name) {
        this.id = id;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.name = name;
        neighbors = new ArrayList<>();
        distances = new ArrayList<>();
    }
    public boolean hasNeighbor() {
        return neighbors.size() > 0;
    }
    public void addNeighbor(Node neighbor) {
        if (this.id != neighbor.id) {
            neighbors.add(neighbor);
            distances.add(distance(this, neighbor));
        }
    }
    public double distance(Node n1, Node n2) {
        double vertical = Math.pow(n2.getLat() - n1.getLat(), 2);
        double horizontal = Math.pow(n2.getLon() - n1.getLon(), 2);
        return Math.pow(vertical + horizontal, 0.5);
    }
    public ArrayList<Node> getNeighbors() {
        return this.neighbors;
    }
    public ArrayList<Double> getDistances() {
        return this.distances;
    }

    public double getLat() {
        return this.latitude;
    }
    public double getLon() {
        return this.longtitude;
    }
    public String getName() {
        return this.name;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (id != node.id) return false;
        if (Double.compare(node.latitude, latitude) != 0) return false;
        return Double.compare(node.longtitude, longtitude) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longtitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
