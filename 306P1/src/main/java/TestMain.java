import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

public class TestMain {

    private static ArrayList<Node> nodesList=new ArrayList<Node>();
    private static ArrayList<Edge> edgesList=new ArrayList<Edge>();
    private static ArrayList<Processor> processorList=new ArrayList<>();
    private static int _numOfProcessors;

    public static void main(String[] args) {
        GraphParser parser = null;
        try {
            parser = new GraphPagrser(new FileInputStream("digraph.dot"));
            Map<String, GraphNode> nodes = parser.getNodes();
            Map<String, GraphEdge> edges = parser.getEdges();

            //log("--- nodes:");
            for (GraphNode node : nodes.values()) {
                //log(node.getId() + " " + node.getAttributes());
                Node vertex = new Node(node.getId());
                vertex.setWeight(node.getAttributes().toString());
                nodesList.add(vertex);
            }

            //log("--- edges:");
            for (GraphEdge edge : edges.values()) {
                //log(edge.getNode1().getId() + "->" + edge.getNode2().getId() + " " + edge.getAttributes());
                Node endNode = null;
                Node startNode = null;
                for (Node vertex : nodesList) {
                    if(vertex.getName().equals(edge.getNode1().getId())){
                        startNode = vertex;
                    }else if(vertex.getName().equals(edge.getNode2().getId())){
                        endNode = vertex;
                        vertex.setIncomingNodes(edge.getNode1().getId());
                    }
                }
                Edge nodeEdge = new Edge(startNode, endNode);
                nodeEdge.setWeight(edge.getAttributes().toString());
                edgesList.add(nodeEdge);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static void log(String s) {
        System.out.println(s);
    }

}

