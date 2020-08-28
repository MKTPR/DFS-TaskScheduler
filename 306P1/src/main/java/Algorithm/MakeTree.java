package Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MakeTree {

    private static ArrayList<String> _currentBest = new ArrayList<>();
    private static ArrayList<Node> _nodesList;
    private static ArrayList<Processor> _processorList;
    private static ArrayList<Node> _scheduledNodes = new ArrayList<Node>();
    private static int _numOfProcessors;
    private static int _upperBound;
    private static String[] mapTemp = {"a", "b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v",
            "w","x","y","z"};
    private static List<String> map = Arrays.asList(mapTemp);

    /**
     * Constructor call for the Tree Object, responsible for the DFS algorithm
     * @param nodeList: Stores the information retrieved from the main class into a local
     *                ArrayList to access pointers to nodes
     * @param processorList: Similar to nodeList, but for Processors.
     * @param numOfProcessors: Number of processors to loop through for individual nodes
     * @param upperBound: Stores the duration of the optimal solution found (so far)
     *                  it is used to be compared against new values.
     */
    public MakeTree(ArrayList<Node> nodeList, ArrayList<Processor> processorList, int numOfProcessors, int upperBound){
        _nodesList=nodeList;
        _processorList=processorList;
        _numOfProcessors= numOfProcessors;
        _upperBound=upperBound;
    }

    /**
     * The method is responsible for the DFS search, looping through all node and processor
     * combination following the topological order. The recursive call within the method
     * is responsible for looping through all nodes. And the inner for loop will loop
     * through all processors. The optimalNodeList state within each processors will be
     * updated with every new optimal path.
     *
     * @param top: Holds the string of the topological order that will be looped through
     * @param nodeNumber: Counter for the recursion call to go through each node
     *                  within the topology
     * @param currentPath: Stores the current combination of node and processors
     *                   in a string. Helps for debugging, visualizing purposes.
     */
    public static void makeTree(String top, int nodeNumber, ArrayList<String> currentPath) {
        int index=0;
        String nodeProcessorComb = "";

        /**Retrieve the index of the Algorithm.Node at string from nodesList.
         * The index is used later to obtain the pointer to the
         * direct Algorithm.Node object.
         */
        for (Node i: _nodesList){
            String x = String.valueOf(top.charAt(nodeNumber));
            String a = _nodesList.get(map.indexOf(x)).getName();
            if (a.equals(i.getName())){
                index=_nodesList.indexOf(i);
                nodeProcessorComb = a;
            }
        }

        /**
         * Loop through every processor with the currently
         * selected Algorithm.Node.
         */
        for(int y=0;y<_numOfProcessors;y++){
            Node node = _nodesList.get(index);
            /**
             * Assign processor pointers and node pointers
             * Schedule the Algorithm.Node to the processor, find the
             * total duration of the current schedule,
             * then add combination to current path.
             */
            Processor processor = _processorList.get(y);
            scheduleNodeToProcessor( node, processor);
            currentPath.add(nodeProcessorComb+ (y+1));
            int _duration=0;
            for (Processor p: _processorList){
                if (p.get_nodeList().size()>_duration){
                    _duration=p.get_nodeList().size();
                }
            }
            if (_duration <= _upperBound){
                /**
                 * At the final recursion call (at the last Algorithm.Node), replace the upperBound
                 * with the new optimal path duration and replace the _currentBest path
                 * as well.
                 */
                if(nodeNumber == (_nodesList.size()-1)){
                    _upperBound=_duration;
                    _currentBest=currentPath;
                    /**
                     * As processor and node states are altered by branching later,
                     * clone the current NodeList state to a optimal List to be used for
                     * outputting at the end of the execution.
                     */
                    for (Processor i: _processorList){
                        i.set_optimalNodeListNode();
                    }
                }
                else {
                    /**
                     * Recursion call with a counter increment to make recursion call
                     * for the next Algorithm.Node in the topology string. When the recursion call is
                     * finished, the counter is decremented to show current position.
                     */
                    nodeNumber++;
                    makeTree(top, nodeNumber, currentPath);
                    nodeNumber--;
                    /**
                     * In the case of the first Algorithm.Node, different processors do not have
                     * to be regarded, as it will create the same branching system.
                     */
                    if (nodeNumber == 0){
                        y= y+ _numOfProcessors;
                    }
                }
                /**
                 * To test the next node and processor combination
                 * remove the node from processor and remove it from the
                 * current path as well.
                 */
                processor.removeNode(node);
                currentPath.remove(nodeNumber);
            }else {
                currentPath.remove(nodeNumber);
                processor.removeNode(node);
            }
        }
    }

    private static void scheduleNodeToProcessor(Node node, Processor processor){
        //schedule the node to the processor

        int finTime = findFinishingTime(node, processor);
        int timeDifference = finTime - processor.get_nodeList().size();

        if(timeDifference == node.getWeight()){
            //   System.out.println("same");
            processor.setNode(node, node.getWeight());
        }else{
            //    System.out.println("diff");
            //     System.out.println(timeDifference);
            //add idle time to processor
            for(int i=0; i<(timeDifference - node.getWeight());i++){
                processor.get_nodeList().add(null);
            }
            processor.setNode(node, node.getWeight());
        }
        // add the node to scheduled node
        _scheduledNodes.add(node);

    }

    private static int findFinishingTime(Node node, Processor processor){
        ArrayList<Node> parentNodes = node.getincomingNodes();
        int latestFinTime = 0;
        int tempFinTime = 0;
        int FinTimeSameProcessor = processor.get_nodeList().size();
        int tempEdgeWeight = 0;

        //If all parents are scheduled in the input processor, no transmission time is required.
        if(processor.get_nodeList().containsAll(parentNodes)){
            //  System.out.println("1");
            return FinTimeSameProcessor;
        }

        //If any parent is scheduled in another processor, transmission time may have to be taken account.
        //finishing time of the node =
        // max(latest finishing time of the parent + transmission time + node weight, processor size + node weight)
        //get parent with latest finishing time
        for(Node pNode : parentNodes){
            for(Processor pProcessor:_processorList){
                if(pProcessor.get_nodeList().contains(pNode) && !(pProcessor.get_processorNumber().equals(processor.get_processorNumber()))){
                    //     System.out.println("2");
                    //get edge cost (transmission cost)
                    for(Edge edge:node.get_incomingEdges()){
                        if(edge.getStartNode().equals(pNode)){
                            tempEdgeWeight = edge.getWeight();
                        }
                    }
                    tempFinTime =
                            pProcessor.get_nodeList().lastIndexOf(pNode) + node.getWeight() + tempEdgeWeight+ 1;//get
                    //  System.out.println(node.getName() +" "+tempFinTime+" "+pProcessor.get_nodeList().lastIndexOf(pNode) + " " + processor.toString());
                    // finishing time
                    if(tempFinTime > latestFinTime){
                        latestFinTime = tempFinTime;
                    }
                }
            }
        }
        //     System.out.println(Math.max(latestFinTime, FinTimeSameProcessor));
        return Math.max(latestFinTime, FinTimeSameProcessor);
    }

    public ArrayList<Processor> get_processorList(){
        return _processorList;
    }

    public int get_upperBound(){
        return _upperBound;
    }
}
