package tsp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Helper {

    private static final String API_HOST = "http://api.formation.dataheroes.fr:8080/simulation";
    private static final String USERNAME = "galetteSaucisseOverflow";

    public static final Node origin = new Node(0, 0.5f, 0.5f, 0);

    public static int score(List<Node> path)
    {
        double gain = Helper.gain(Helper.origin, path.get(0));
        for (int i = 1; i < path.size(); i++)
        {
            gain += Helper.gain(path.get(i-1), path.get(i)) - i;
        }
        return (int)gain;
    }

    public static double gain(Node start, Node end)
    {
        return end.amount - start.dst(end);
    }

    public static List<Node> readFile(String fileName) {

        String cvsSplitBy = ";";
        List<Node> toReturn = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            while ((line = br.readLine()) != null) {

                String[] node = line.split(cvsSplitBy);

                toReturn.add(new Node(
                        Integer.parseInt(node[0]),
                        Float.parseFloat(node[1]),
                        Float.parseFloat(node[2]),
                        Integer.parseInt(node[3])
                ));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public static void sendSolution(String problem_id, int[] array) {
        String content =
        "{" +
            "problem_id : " + problem_id +
            "username : " + USERNAME +
            "orders:" + array.toString() +
        "}";
        try {
            URL url = new URL(API_HOST);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(content);
            wr.flush();
            wr.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch(IOException io) {
            io.printStackTrace();
        }

    }
}
