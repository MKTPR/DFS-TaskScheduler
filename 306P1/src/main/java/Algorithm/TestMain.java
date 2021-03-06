package Algorithm;


import Visualization.TableView;
import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;
import Visualization.MainPage;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import Visualization.CreateGraph;

/**
 * This main class is responsible for taking user input, calling a MainPage Window object,
 * calling the Greedy Algorithm, then the DFS Branch to eventually produce a output.dot file
 * to the user.
 */
public class TestMain {

    private static ArrayList<Node> nodesList = new ArrayList<Node>();
    private static ArrayList<Edge> edgesList = new ArrayList<Edge>();
    private static ArrayList<Processor> processorList = new ArrayList<>();
    private static ArrayList<Processor> optimalProcessorList = new ArrayList<>();
    private static int _numOfProcessors;
    private static int isParallel = -1;
    private static int _nodeNumber =0;
    private static ArrayList<String> _currentBest = new ArrayList<>();
    private static int _upperBound;
    private static ArrayList<Integer> start = new ArrayList<>();
    private static ArrayList<Integer> end = new ArrayList<>();
    private static int increment;
    private static boolean isVisualise = false;
    private static String input;
    private static String isOutput = "output.dot";
    private static ArrayList<Thread> threads = new ArrayList<>();
    private static String[] map = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v",
    "w","x","y","z"};
    private static ArrayList<String> perms = new ArrayList<String>();
    private static ArrayList<String> Topologies = new ArrayList<String>();

    public static ArrayList<Node> getNodesList(){
        return nodesList;
    }
    public static ArrayList<Edge> getEdgesList(){
        return edgesList;
    }
    private static MainPage page;

    public static void main(String[] args)  throws Exception{

        input = args[0];
        //Takes the input of File names and the processor number.
        isOutput = (input.split("\\."))[0] + "-output.dot";

        //Checks that the input is valid
        if (args.length < 2){
            throw new Exception("invalid input: name of input file and number of cores required. \n " +
                    "Example input: java -jar scheduler.jar Input.dot 10 [OPTION]");
        }else {
            checkInputValidity(args);
        }
        try {
            _numOfProcessors = Integer.parseInt(args[1]);
        } catch(Exception e) {
            throw new Exception("invalid input: number of processors must be specified in integer");
        }

        //Parse input .dot file
        parseGraphInput(input);


        createNewProcessor(_numOfProcessors);


        // Greedy Algorithm to Set up a upper Bound
        GreedyAlgorithm va = new GreedyAlgorithm(nodesList, edgesList, processorList);
        _upperBound = va.computeGreedyFinishingTime();

        //Comment Out Later
       // System.out.println("up = "+_upperBound);
        optimalProcessorList = new ArrayList<>(va.get_processorList());

        //Copies the current processor State for the visualization panel.
        for (Processor l: optimalProcessorList){
            l.set_optimalNodeListNode();
        }

        /**
         * generates all topologies in the topologies arraylist
         */
        getTopologies(); //generates all topologies in the topologies arraylist

        //Creates a visualization page - MainPage object is created
        if (isVisualise) {
            page = new MainPage(optimalProcessorList, _upperBound, _numOfProcessors, isParallel, input, isOutput, Topologies.size());
        }

        //Clears node state back to it's original state.
        va.emptyScheduledNodesInProcesses();
        for ( Processor d : processorList){
            d.get_nodeList().clear();
        }

        //If Parallelization.
        if (isParallel>=2) {

            //Divide the topologies list for individual threads
            increment = (Topologies.size() / isParallel);
            start.add(0);
            end.add(increment);

            for (int i= 0; i< (isParallel-1); i++ ){

               if (end.size() == (isParallel-1)){
                   start.add(start.get(i) + increment);
                   end.add(Topologies.size());
               } else {
                   start.add(start.get(i) + increment);
                   end.add((end.get(i) + increment));
               }
            }

            //Create worker thread. This code will only run if isParallel > 2
            for (int i = 0; i < isParallel; i++) {
                Thread run = new Thread(() -> {
                    int f = Integer.parseInt(Thread.currentThread().getName());
                        for (int j = start.get(f); j<end.get(f);j++) {

                            String top = Topologies.get(j);
                            ArrayList<String> _currentPath = new ArrayList<>(nodesList.size());
                            MakeTreeThreading tree = new MakeTreeThreading(nodesList, processorList, _numOfProcessors, _upperBound);
                            tree.makeTree(top, _nodeNumber, _currentPath);
                            /**
                             * Constantly update _upperBound with the newly found upperbound
                             * if it is lower than the current optimal.
                             */
                            if (_upperBound > tree.get_upperBound()) {
                                _upperBound = tree.get_upperBound();
                                //Comment out later
                                //System.out.println(_upperBound);
                                optimalProcessorList = new ArrayList<>(tree.get_processorList());
                                //Update Visualization Panel
                                if (isVisualise){
                                    TableView _TV = TableView.getInstance();
                                    _TV.changeData(optimalProcessorList,_upperBound);
                                    page.updateBestTime(_upperBound);
                                };
                            }
                            if (isVisualise) {
                                page.increaseTopSearched();
                            }
                        }
                });
                //sets name for individual threads so they can be used for indexing
                run.setName(i+"");
                threads.add(run);
                run.start();
            }
        }

       //waits for all threads to finish
        for (Thread i: threads){
            while( i.isAlive()){
                int k=0;
                k++;
            }
        }

        //if search is to be done sequentially this section is used
        if (isParallel<2) {
            /**
             * Loops through various topology Strings in the Topologies arraylist to
             * try all combination. Then calls on the makeTree method through the tree
             * object.
             */
            for (String top : Topologies) {
                ArrayList<String> _currentPath = new ArrayList<>(nodesList.size());
                MakeTreeThreading tree = new MakeTreeThreading(nodesList, processorList, _numOfProcessors, _upperBound);
                tree.makeTree(top, _nodeNumber, _currentPath);
                /**
                 * upperBound is constantly replaced with new ones.
                 */
                if (_upperBound > tree.get_upperBound()) {
                    _upperBound = tree.get_upperBound();
                    optimalProcessorList = new ArrayList<>(tree.get_processorList());
                    //System.out.println(_upperBound);
                    if (isVisualise){
                        TableView _TV = TableView.getInstance();
                        _TV.changeData(optimalProcessorList,_upperBound);
                        page.updateBestTime(_upperBound);
                    }
                }
                if (isVisualise) {
                    page.increaseTopSearched();
                }
            }
        }
        /**
         * Loop through each processor to set the Algorithm.Node state to
         * match the information in the optimalNodeList state.
         */
        for (Processor processor : optimalProcessorList) {
            for (int i = 0; i < processor.get_optimalNodeList().size(); i++) {
                if (processor.get_optimalNodeList().get(i) != null) {
                    Node node = processor.get_optimalNodeList().get(i);
                    node.setProcessor(processor);
                }
            }
        }

        //System.out.println("up = "+_upperBound);

        /**
         * Output node state in .dot format.
         */
        outputToDotFile();
        if (isVisualise) {
            page.stopVisualisation();
        }
    }

    /**
     * This method insures that the input arguments given by the user were valid and then assigns the
     * appropriate values to fields depending on the argument values.
     * @param args
     */
    private static void checkInputValidity(String[] args) {
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
    }

    public static ArrayList<Node> getNodeList(){
        return nodesList;
    }
    public static ArrayList<Edge> getEdgeList(){
        return edgesList;
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

                //for each node name, create a new Algorithm.Node Object
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
            System.out.println("Specified File: "+ input +" doesnt Exist");
            System.exit(1);
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
     * This method prints out useful information about the input graph.
     * Was useful for benchmarking/debugging
     */
    /*public static void printGraphInfo() {
        System.out.println("---Algorithm.Node Info---");
        for (Node node : nodesList) {
            System.out.println(node);
        }
        System.out.println("---Algorithm.Edge Info---");
        for (Edge edge : edgesList) {
            System.out.println(edge);
        }
        System.out.println("---Algorithm.Processor Info---");
        for (Processor processor : processorList) {
            System.out.println(processor);
        }
    }*/

    /**
     * This method formats the optimal schedule into .dot format and then outputs
     * the schedule to the output file
     */
    private static void outputToDotFile(){
        PrintWriter writer = null;

        try{
            int counter = 0;
            writer = new PrintWriter(isOutput);
            writer.println("digraph " + "\"" + isOutput.split("\\.")[0] + "\"" + " {");
            for (Node node : nodesList){
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

    /**
     * first separates nodes based on when they have no incoming edge
     * uses permutation and generateTop functions to get all topologies
     */
    private static void getTopologies() {
        ArrayList<Node> unvisited = new ArrayList<Node>(nodesList);
        ArrayList<Node> temp = new ArrayList<Node>();
        ArrayList<Node> visited = new ArrayList<Node>();
        String topology =  "";

        while (unvisited.size() > 0){
            for(Node node : unvisited){
                if(visited.containsAll(node.getincomingNodes())){
                    topology += map[nodesList.indexOf(node)];
                    temp.add(node);
                }
            }
            visited.addAll(temp);
            unvisited.removeAll(temp);
            topology += "-";
        }
        String[] split = topology.split("-");
        ArrayList<ArrayList<String>> top = new ArrayList<ArrayList<String>>();
        for(String str : split){
            permutation("", str);
            top.add(new ArrayList<String>(perms));
            perms.clear();
        }

        generateTop(top);
    }

    /**
     * This method does the cartesian product of the list of lists.
     * This creates the topologies and adds to the topologies arraylist.
     * e.g [[a],[bc,cb],[d]] -> [abcd, acbd]
     */
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



    /**
     * uses recursive call to add all permutations of the input string to the perms arraylist
     */
    private static void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0) perms.add(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
        }
    }

    /**
     *
     */

}