import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvToNode {

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
}
