import tsp.Helper;
import tsp.Node;
import tsp.solvers.TSPNearestNeighbour;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        String problemID = args[1];
        String filename  = problemID + ".csv";

        List<Node> nodes = Helper.readFile(filename);
        List<Node> path  = TSPNearestNeighbour.solve(nodes);

        System.out.println("score: " + Helper.score(path));
    }
}
