package tsp.solvers;

import tsp.Node;
import java.util.List;

public interface TSPSolver
{
    public List<Node> solve(List<Node> nodes);

    static int gain(Node start, Node end)
    {
        return (int) (end.amount - start.dst(end));
    }
}
