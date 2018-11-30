import tsp.Helper;
import tsp.Node;
import tsp.solvers.TSPGenetic;
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

        System.out.print("computing path (nearest neighbour)...");
        List<Node> path  = TSPNearestNeighbour.solve(nodes);
        System.out.println("done");

        System.out.print("computing score...");
        int score = (int) Helper.score(path);
        System.out.println(" => " + score);

        // ========================================================

        System.out.print("computing path (genetic)...");
        List<Node> path2  = TSPGenetic.solve(nodes);
        System.out.println("done");

        System.out.print("computing score...");
        int score2 = (int) Helper.score(path2);
        System.out.println(" => " + score);

        System.out.print("sending solution...");
        int[] intPath2 = new int[path.size()];
        for (int i = 0; i < intPath2.length; i++) intPath2[i] = path.get(i).id;
        Helper.sendSolution(problemID, intPath2);
    }
}
