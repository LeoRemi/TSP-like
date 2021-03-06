package tsp;

public class Node
{
    public final int id;
    public final double lat;
    public final double lng;
    public final int amount;

    public Node(int id, float lat, float lng, int amount) {
        this.id = id;
        this.lat = Math.toRadians(lat);
        this.lng = Math.toRadians(lng);
        this.amount = amount;
    }

    public double dst(Node n)
    {

        return Math.acos(Math.sin(lat)*Math.sin(n.lat) + Math.cos(lat)*Math.cos(n.lat)*Math.cos(lng - n.lng)) * 6371.f;
    }
}