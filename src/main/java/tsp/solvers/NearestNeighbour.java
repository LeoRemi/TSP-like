package tsp.solvers;

import tsp.Node;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighbour extends TSPSolver
{

    public List<Node> solve(List<Node> nodes) {
        Node current = origin;
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

    private Node popMax(Node origin, List<Node> nodes)
    {
        double gain = 0;
        Node node = null;
        for (Node n : nodes)
        {
            double tmp = TSPSolver.gain(origin, n);
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
