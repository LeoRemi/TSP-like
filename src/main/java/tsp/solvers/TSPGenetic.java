package tsp.solvers;

import tsp.Helper;
import tsp.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class TSPGenetic
{
    private static class Solution
    {
        public final List<Node> path;
        public final List<Node> notvisited;
        public final double value;

        private Solution(List<Node> path, List<Node> visited, double value) {
            this.path = path;
            this.notvisited = visited;
            this.value = value;
        }
    }

    private static int NB_BRANCHES = 10;
    private static int NB_MUTATIONS = 1000;

    public static List<Node> solve(List<Node> nodes) {
        System.out.print("populating..");
        List<Solution> population = new ArrayList<>();
        for (int i = 0; i < NB_BRANCHES; i++)
        {
            population.add(new Solution(new ArrayList<>(), new ArrayList<>(nodes), 0));
        }

        // generate initial population
        for (int i = 0; i < nodes.size() - 1; i++)
        {
            population = nextStep(population, NB_BRANCHES);
            population = population.subList(0, NB_BRANCHES);
        }
        population = nextStep(population, NB_BRANCHES);

        System.out.print("mutating..");
        List<List<Node>> paths = new ArrayList<>();
        for (Solution p : population) paths.add(p.path);

        int c = 0;
        for (List<Node> path : new ArrayList<>(paths))
        {
            for (int i = 0; i < NB_MUTATIONS; i++)
            {
                paths.add(mutate(path));
            }
            System.out.print(""+ (c++ * NB_MUTATIONS) +"..");
        }

        System.out.print("sorting ("+paths.size()+" elements)..");
        paths.sort(Comparator.comparingDouble(Helper::score));
        return paths.get(0);
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    private static List<Node> mutate(List<Node> p)
    {
        int i = randInt(0, p.size() - 1);
        int j = randInt(0, p.size() - 1);
        List<Node> path = new ArrayList<>(p);
        Node tmp = path.get(i);
        path.set(i, path.get(j));
        path.set(j, tmp);
        return path;
    }

    private static int step = 0;
    private static List<Solution> nextStep(List<Solution> solutions, int nbBranches)
    {
        step++;
        if (step % 100 == 0) System.out.print("" + (step++) + "..");

        List<Solution> result = new ArrayList<>();
        for (Solution s : solutions)
        {
            Node origin = (s.path.isEmpty()) ? Helper.origin : s.path.get(s.path.size() - 1);

            Node[] nodes = new Node[nbBranches];
            double[] dst = new double[nbBranches];

            for (Node n : s.notvisited)
            {
                double tmp = origin.dst(n);
                for (int i = 0; i < nbBranches; i++)
                {
                    if (nodes[i] == null || dst[i] < tmp)
                    {
                        nodes[i] = n;
                        dst[i] = tmp;
                    }
                }
            }

            for (int i = 0; i < nbBranches; i++)
            {
                List<Node> path = new ArrayList<>(s.path);
                path.add(nodes[i]);
                List<Node> notvisited = new ArrayList<>(s.notvisited);
                notvisited.remove(nodes[i]);
                result.add(new Solution(path, notvisited, s.value + dst[i]));
            }
        }
        result.sort(Comparator.comparingDouble(n -> n.value));
        return result;
    }
}
