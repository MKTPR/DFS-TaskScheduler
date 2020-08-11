import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class TestMain {

    public static void main(String[] args) {
        GraphParser parser = null;
        try {
            parser = new GraphParser(new FileInputStream("digraph.dot"));
            Map<String, GraphNode> nodes = parser.getNodes();
            Map<String, GraphEdge> edges = parser.getEdges();

            log("--- nodes:");
            for (GraphNode node : nodes.values()) {
                log(node.getId() + " " + node.getAttributes());
            }

            log("--- edges:");
            for (GraphEdge edge : edges.values()) {
                log(edge.getNode1().getId() + "->" + edge.getNode2().getId() + " " + edge.getAttributes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static void log(String s) {
        System.out.println(s);
    }

}

