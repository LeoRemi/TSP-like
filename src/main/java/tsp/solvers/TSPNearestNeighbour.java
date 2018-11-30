package tsp.solvers;

import tsp.Helper;
import tsp.Node;

import java.util.ArrayList;
import java.util.List;

public class TSPNearestNeighbour
{
    public static List<Node> solve(List<Node> nodes) {
        Node current = Helper.origin;
        List<Node> visited = new ArrayList<>();
        List<Node> notvisited = new ArrayList<>(nodes);

        while (!notvisited.isEmpty())
        {
            Node next = popMax(current, notvisited);
            visited.add(next);
            current = next;
        };

        return visited;
    }

    private static Node popMax(Node origin, List<Node> nodes)
    {
        double gain = 0;
        Node node = null;
        for (Node n : nodes)
        {
            double tmp = Helper.gain(origin, n);
            if (tmp > gain)
            {
                gain = tmp;
                node = n;
            }
        }
        nodes.remove(node);
        return node;
    }
}
