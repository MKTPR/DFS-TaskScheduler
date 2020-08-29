package Algorithm;

import java.util.ArrayList;

/**
 * This class is scheduling using greedy algorithm for upper bound
 */
public class GreedyAlgorithm {
    private ArrayList<Node> _nodeList =new ArrayList<Node>();
    private ArrayList<Edge> _edgeList =new ArrayList<Edge>();
    private ArrayList<Processor> _processorList =new ArrayList<Processor>();
    private ArrayList<Node> _scheduledNodes = new ArrayList<Node>();
    private ArrayList<Node> availableNode = new ArrayList<Node>();

    /**
     * Constructor call for the Greedy Algorithm Object, the constructor deep copy input arraylist
     * @param nodeList
     * @param edgeList
     * @param processorList
     */
    public GreedyAlgorithm(ArrayList<Node> nodeList, ArrayList<Edge> edgeList, ArrayList<Processor> processorList) {
        //Deep copy nodeList
        for(Node node : nodeList){
            this._nodeList.add(node);
        }
        //Deep copy edgeList
        for(Edge edge:edgeList){
            this._edgeList.add(edge);
        }
        //Deep copy processorList
        for(Processor processor : processorList){
            this._processorList.add(processor);
        }
    }

    /**
     * This method computes for total duration for scheduling using greedy algoorithm that schedules node with earliest finishing time first
     * Loops through every processor and return the value with longest end time
     * @return the total execution time for scheduling nodes into processors
     */
    public int computeGreedyFinishingTime(){
        int tempDuration = 0;
        int duration = 0; //total execution time
        runAlgorithm();
        // calculate duration
        for(Processor processor:_processorList){
            tempDuration = processor.get_nodeList().size();
            if(tempDuration > duration){
                duration = tempDuration;
            }
        }
        return duration;
    }

    /**
     * This method clears scheduled nodeLists in all processors.
     */
    public void emptyScheduledNodesInProcesses(){
        _processorList.clear();
        _edgeList.clear();
        availableNode.clear();
        _edgeList.clear();
        _scheduledNodes.clear();
    }

    /**
     * the main method for the class that finds available nodes and compute earliest finishing time
     * @return Array list of processor
     */
    public ArrayList<Processor> runAlgorithm(){
        NodeProcessor nodeProcessor;
        while (_nodeList.size() > 0) {
            // get a list of nodes with NO incoming edges
            AvailableNode(availableNode);
            nodeProcessor = findEarliestFinishingNodeProcessor(availableNode);
            scheduleNodeToProcessor(nodeProcessor);
        }
        return _processorList;
    };

    /**
     * schedule node to processor from node processor object
     * @param nodeProcessor
     */
    private void scheduleNodeToProcessor(NodeProcessor nodeProcessor){
        //schedule the node to the processor
        Node node = nodeProcessor.getNode();
        Processor processor = nodeProcessor.getProcessor();
        int finTime = findFinishingTime(node, processor);
        int timeDifference = finTime - processor.get_nodeList().size();
        //case where scheduling a node into a same processor
        if(timeDifference == node.getWeight()){
            processor.setNode(node, node.getWeight());
        }else{
            //add idle time to processor
            //case where scheduling a node into a different processor
            for(int i=0; i<(timeDifference - node.getWeight());i++){
                processor.get_nodeList().add(null);
            }
            processor.setNode(node, node.getWeight());
        }
        //remove the node from available node
        availableNode.remove(node);
        // add the node to scheduled node
        _scheduledNodes.add(node);
        //remove from the nodelist
        _nodeList.remove(node);
    }

    /**
     * This method transmission time of a node when it is scheduled in a particular processor.
     * @param node
     * @param processor
     * @return -1 if not all the parent nodes are already scheduled, and return value of finishing time of given params
     */
    private int findFinishingTime(Node node, Processor processor){
        ArrayList<Node> parentNodes = node.getincomingNodes();
        int latestFinTime = 0;
        int tempFinTime = 0;
        int FinTimeSameProcessor = processor.get_nodeList().size() + node.getWeight();;
        int tempEdgeWeight = 0;
        //Check if all its parents are scheduled
        if(!_scheduledNodes.containsAll(parentNodes)){
            return -1;
        }
        //If all parents are scheduled in the input processor, no transmission time is required.
        if(processor.get_nodeList().containsAll(parentNodes)){
            return FinTimeSameProcessor;
        }
        //If any parent is scheduled in another processor, transmission time may have to be taken account.
        //get parent with latest finishing time
        for(Node pNode : parentNodes){
            for(Processor pProcessor:_processorList){
                if(pProcessor.get_nodeList().contains(pNode) && !(pProcessor.get_processorNumber().equals(processor.get_processorNumber()))){
                    //get edge cost (transmission cost)
                    for(Edge edge:node.get_incomingEdges()){
                        if(edge.getStartNode().equals(pNode)){
                            tempEdgeWeight = edge.getWeight();
                        }
                    }
                    tempFinTime =
                            pProcessor.get_nodeList().lastIndexOf(pNode) + node.getWeight() + tempEdgeWeight+ 1;//get
                    // finishing time
                    if(tempFinTime > latestFinTime){
                        latestFinTime = tempFinTime;
                    }
                }
            }
        }
        return Math.max(latestFinTime, FinTimeSameProcessor);
    }

    /**
     * This method finds earliest finishing node and processor out of given available node list.
     * @param availableNode
     * @return node processor object with earlist finishing node with its processor
     */
    public NodeProcessor findEarliestFinishingNodeProcessor(ArrayList<Node> availableNode){
        Node earliestFinNode = null;
        Node tempNode = null;
        Processor earliestFinProcessor = null;
        Processor tempProcessor = null;
        int finishingTime = 1000000;
        int tempFinishingTime = 1000000;

        for(Node node:availableNode){
            for(Processor processor:_processorList){
                tempFinishingTime = findFinishingTime(node, processor);
                tempNode = node;
                tempProcessor = processor;
                if(tempFinishingTime < finishingTime){
                    finishingTime = tempFinishingTime;
                    earliestFinNode = tempNode;
                    earliestFinProcessor = tempProcessor;
                }
            }
        }
        return new NodeProcessor(earliestFinNode, earliestFinProcessor);
    }

    /**
     * This method finds out nodes with no incomming edges and put available nodes into a available node list
     * @param availableNode
     */
    public void AvailableNode(ArrayList<Node> availableNode){
        //add nodes that has no incoming node from _nodeList into availableNode arraylist
        for(Node node: _nodeList){
            //if node's every incomming edges are in scheduled list but the node is not in scheduled list or node is not already in the available node list
            if(_scheduledNodes.containsAll(node.getincomingNodes()) && !_scheduledNodes.contains(node) && !this.availableNode.contains(node)){
                availableNode.add(node);
            }
        }
    }

    /**
     * this method is getter method that returns array list of processor created from greedy algorithm object
     * @return array list of processor
     */
    public ArrayList<Processor> get_processorList(){
        return _processorList;
    }
}
