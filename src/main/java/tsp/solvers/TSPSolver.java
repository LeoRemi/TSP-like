package tsp.solvers;

import tsp.Node;
import java.util.List;

public abstract class TSPSolver
{
    static final Node origin = new Node(0, 0.5f, 0.5f, 0);

    public abstract List<Node> solve(List<Node> nodes);

    static int score(List<Node> path)
    {
        double gain = TSPSolver.gain(TSPSolver.origin, path.get(0));
        for (int i = 1; i < path.size(); i++)
        {
            gain += TSPSolver.gain(path.get(i-1), path.get(i));
        }
        return (int)gain;
    }

    static double gain(Node start, Node end)
    {
        return end.amount - start.dst(end);
    }
}
