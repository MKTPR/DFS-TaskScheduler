import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class TestMain {

    private static ArrayList<Node> nodesList = new ArrayList<Node>();
    private static ArrayList<Node> nodesListOriginal = new ArrayList<Node>();
    private static ArrayList<Edge> edgesList = new ArrayList<Edge>();
    private static ArrayList<Processor> processorList = new ArrayList<>();
    private static int _numOfProcessors;
    private static int isParallel = -1;
    private static boolean isVisualise = false;
    private static String isOutput = "output.dot";

    private static ArrayList<String> perms = new ArrayList<String>();
    private static ArrayList<String> Topologies = new ArrayList<String>();

    public static void main(String[] args) throws Exception{
        if (args.length < 2){
            throw new Exception("invalid input: name of input file and number of cores required. \n " +
                    "Example input: java -jar scheduler.jar Input.dot 10 [OPTION]");
        }

        String input = args[0];
        isOutput = (input.split("\\."))[0] + "-output.dot";

        _numOfProcessors = Integer.parseInt(args[1]);
        //Parse input .dot file
        parseGraphInput(input);
        nodesListOriginal = new ArrayList<>(nodesList);
        //Testing to create a 5 new processor
        createNewProcessor(_numOfProcessors);

        if (args.length > 2){
            for (int i = 2; i < args.length; i++){
                if (args[i].contains("-p")){
                    try {
                        if (Integer.parseInt(args[i+1]) < 1){
                            System.out.println("Invalid number of cores: Defaulting to sequential");
                        }
                        else {
                            isParallel = Integer.parseInt(args[i+1]);
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number of cores: Defaulting to sequential");
                    } catch (IndexOutOfBoundsException e){
                        System.out.println("Invalid number of cores: Defaulting to sequential");
                    }


                }
                else if (args[i].contains("-v")){
                    isVisualise = true;
                }
                else if (args[i].contains("-o")){
                    try {
                        isOutput = args[i + 1];
                        if (!isOutput.endsWith(".dot")){
                            isOutput += ".dot";
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("invalid output file");
                    }
                }
            }

        }

        getTopologies();

        // Print graph information on console
//        printGraphInfo();

        // test valid algorithm
        ValidAlgorithm va = new ValidAlgorithm(nodesList, edgesList, processorList);
        ArrayList<Processor> scheduledProcessors = va.run();
        //print output
        for (Processor processor : scheduledProcessors) {
            //System.out.println("----Processor number: " + processor.get_processorNumber() + " - Schedule----");
            for (Node node : processor.get_nodeList()) {
                node.setProcessor(processor);
                //System.out.println(node.getName());
            }
        }
        outputToDotFile();

    }


    private static void log(String s) {
        System.out.println(s);
    }

    /**
     * This method parses .dot file to corresponding java objects
     *
     * @param input name of input .dot file
     */
    private static void parseGraphInput(String input) {
        GraphParser parser = null;
        try {
            //Reads information from the specified dot file
            parser = new GraphParser(new FileInputStream(input));
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
                //New edge object creation
                Edge nodeEdge = new Edge();
                for (Node vertex : nodesList) {
                    if (vertex.getName().equals(edge.getNode1().getId())) {
                        startNode = vertex;
                        for (Node vertex2: nodesList) {
                            if (vertex2.getName().equals(edge.getNode2().getId())){
                                vertex.setOutgoingNodes(vertex2);
                            }
                        }
                        vertex.setOutgoingEdges(nodeEdge);
                    } else if (vertex.getName().equals(edge.getNode2().getId())) {
                        endNode = vertex;
                        for (Node vertex2: nodesList) {
                            if (vertex2.getName().equals(edge.getNode1().getId())){
                                vertex.setIncomingNodes(vertex2);
                            }
                        }
                        vertex.setIncomingEdges(nodeEdge);
                    }
                }


                nodeEdge.setNodes(startNode, endNode);
                //Set weight of an edge object
                nodeEdge.setWeight(edge.getAttributes().toString());

                //Add the create edge into an arraylist
                edgesList.add(nodeEdge);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates new processors and adds them into the processorList.
     *
     * @param numOfProcessorsToCreate indicates the number of processors to make
     */
    private static void createNewProcessor(int numOfProcessorsToCreate) {
        for (int i = 0; i < numOfProcessorsToCreate; i++) {
            Processor processor = new Processor();
            processorList.add(processor);
        }
    }

    /**
     * This method prints out useful information about the input graph
     */
    public static void printGraphInfo() {
        System.out.println("---Node Info---");
        for (Node node : nodesList) {
            System.out.println(node);
        }
        System.out.println("---Edge Info---");
        for (Edge edge : edgesList) {
            System.out.println(edge);
        }
        System.out.println("---Processor Info---");
        for (Processor processor : processorList) {
            System.out.println(processor);
        }
    }
    private static void outputToDotFile(){
        PrintWriter writer = null;

        try{
            int counter = 0;
            writer = new PrintWriter(isOutput);
            writer.println("digraph " + isOutput.split("\\.")[0] + " {");
            for (Node node : nodesListOriginal){
                if(counter == 0 ) {
                    writer.println("\t\t" + node.toString());
                }
                else {
                    writer.println("\t\t" + node.toString());
                    //assumes edges cant go backwards (i.e from b to a)
                    for (Edge edge : edgesList){
                        if (edge.getEndNode().equals(node)){
                            writer.println("\t\t" + edge.toString());
                        }
                    }
                }
            counter++;
            }
            writer.println("}");
            writer.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void getTopologies() {
        ArrayList<Node> unvisited = nodesList;
        ArrayList<Node> temp = new ArrayList<Node>();
        ArrayList<Node> visited = new ArrayList<Node>();
        String topology =  "";

        while (unvisited.size() > 0){
            for(Node node : unvisited){
                if(visited.containsAll(node.getincomingNodes())){
                    topology += node.getName();
                    temp.add(node);
                }
            }
            visited.addAll(temp);
            unvisited.removeAll(temp);
            topology += "-";
        }
        String[] split = topology.split("-");
        ArrayList<ArrayList<String>> top45 = new ArrayList<ArrayList<String>>();
        for(String str : split){
            permutation("", str);
            top45.add(new ArrayList<String>(perms));
            perms.clear();
        }

        generateTop(top45);
    }

    private static void generateTop(ArrayList<ArrayList<String>> sets) {
        int solutions = 1;
        String singleTop = "";
        for(int i = 0; i < sets.size(); solutions *= sets.get(i).size(), i++);
        for(int i = 0; i < solutions; i++) {
            int j = 1;
            for(ArrayList<String> set : sets) {
                singleTop += set.get((i/j)%set.size());
                j *= set.size();
            }
            Topologies.add(singleTop);
            singleTop = "";
        }
    }




    private static void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0) perms.add(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
        }
    }

}