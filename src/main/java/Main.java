import tsp.Helper;
import tsp.Node;
import tsp.solvers.TSPNearestNeighbour;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        String problemID = args[0];
        String filename  = problemID + ".csv";

        System.out.print("reading csv file...");
        List<Node> nodes = Helper.readFile(filename);
        System.out.println("done.");

        System.out.print("computing path...");
        List<Node> path  = TSPNearestNeighbour.solve(nodes);
        System.out.println("done");

        System.out.print("computing score...");
        int score = (int) Helper.score(path);
        System.out.println(" => " + score);

        System.out.print("sending solution...");
        int[] intPath = new int[path.size()];
        for (int i = 0; i < intPath.length; i++) intPath[i] = path.get(i).id;
        Helper.sendSolution(problemID, intPath);
    }
}
