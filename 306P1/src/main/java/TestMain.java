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
            //Reads information from the specified dot file
            parser = new GraphParser(new FileInputStream("digraph.dot"));
            Map<String, GraphNode> nodes = parser.getNodes();
            Map<String, GraphEdge> edges = parser.getEdges();

//            log("--- nodes:"); //log
            for (GraphNode node : nodes.values()) {
//                log(node.getId() + " " + node.getAttributes()); //log

                //for each node name, create a new Node Object
                Node vertex = new Node(node.getId());

                //Set the weight of the Object
                vertex.setWeight(node.getAttributes().toString());

                //Add the created object into the node
                nodesList.add(vertex);
            }

//            log("--- edges:"); //log
            for (GraphEdge edge : edges.values()) {
//                log(edge.getNode1().getId() + "->" + edge.getNode2().getId() + " " + edge.getAttributes()); //log
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
                //New edge object creation
                Edge nodeEdge = new Edge(startNode, endNode);

                //Set weight of an edge object
                nodeEdge.setWeight(edge.getAttributes().toString());

                //Add the create edge into an arraylist
                edgesList.add(nodeEdge);
            }
            //Testing to create a new processor
            Processor processor1 = new Processor();
            processorList.add(processor1);
            Processor processor2 = new Processor();
            processorList.add(processor2);
            Processor processor3 = new Processor();
            processorList.add(processor3);

            printGraphInfo();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static void log(String s) {
        System.out.println(s);
    }

    public static void printGraphInfo(){
        System.out.println("---Node Info---");
        for(Node node:nodesList){
            System.out.println(node);
        }
        System.out.println("---Edge Info---");
        for(Edge edge:edgesList){
            System.out.println(edge);
        }
        System.out.println("---Processor Info---");
        for(Processor processor:processorList){
            System.out.println(processor);
        }
    }
}

