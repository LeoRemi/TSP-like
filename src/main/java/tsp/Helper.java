package tsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class Helper {

    private static class Solution
    {
        public final String problem_id;
        public final String username;
        public final int[] orders;

        private Solution(String problem_id, String username, int[] orders) {
            this.problem_id = problem_id;
            this.username = username;
            this.orders = orders;
        }
    }

    private static final String API_HOST = "http://api.formation.dataheroes.fr:8080/simulation";
    private static final String USERNAME = "galetteSaucisseOverflow";

    public static final Node origin = new Node(0, 0.5f, 0.5f, 0);

    public static int score(List<Node> path)
    {
        double gain = Helper.gain(Helper.origin, path.get(0));
        for (int i = 1; i < path.size(); i++)
        {
            gain += Helper.gain(path.get(i-1), path.get(i)) - Math.min(i, path.get(i).amount);
        }
        return (int)gain;
    }

    public static double gain(Node start, Node end)
    {
        return end.amount - Math.abs(start.dst(end));
    }

    public static List<Node> readFile(String fileName) {

        String cvsSplitBy = ";";
        List<Node> toReturn = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine();
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

    public static String sendSolution(String problem_id, int[] array) {
        Solution solution = new Solution(problem_id, USERNAME, array);
        Gson jsonBuilder = new GsonBuilder().create();
        String payload = jsonBuilder.toJson(solution);
        StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(API_HOST);
        request.setEntity(entity);
        try {
            HttpResponse response = httpClient.execute(request);
            return "[" + response.getStatusLine().getStatusCode() + " - " + response.getStatusLine().getReasonPhrase() + "]";
        } catch (IOException e) {
            e.printStackTrace();
            return "[EXCEPTION]";
        }
    }
}
