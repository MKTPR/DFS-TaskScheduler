import java.lang.reflect.Array;
import java.util.ArrayList;

public class GreedyAlgorithm {

    private ArrayList<Node> _nodeList =new ArrayList<Node>();
    private ArrayList<Edge> _edgeList =new ArrayList<Edge>();
    private ArrayList<Processor> _processorList =new ArrayList<Processor>();
    private ArrayList<Node> _scheduledNodes = new ArrayList<Node>();
    private ArrayList<Node> availableNode = new ArrayList<Node>();


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

    // This method returns the total execution time of the algorithm
    public int computeGreedyFinishingTime(){
        int tempDuration = 0;
        int duration = 0;

        runAlgorithm();

        // calculate duration
        for(Processor processor:_processorList){
            tempDuration = processor.get_nodeList().size();
            if(tempDuration > duration){
                duration = tempDuration;
            }
        }

        emptyScheduledNodesInProcesses();

        return duration;
    }

    //This method clears scheduled nodeLists in all processors.
    private void emptyScheduledNodesInProcesses(){
        for(Processor processor:_processorList){
            processor.get_nodeList().clear();
        }
    }

    //This method runs the algorithm and returns an Arraylist of processors with sorted tasks.
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

    private void scheduleNodeToProcessor(NodeProcessor nodeProcessor){
        //schedule the node to the processor
        Node node = nodeProcessor.getNode();
        Processor processor = nodeProcessor.getProcessor();
        int finTime = findFinishingTime(node, processor);
        int timeDifference = finTime - processor.get_nodeList().size();

        if(timeDifference == node.getWeight()){
            processor.setNode(node, node.getWeight());
        }else{
            //add idle time to processor
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

    //This method finds transmission time of a node when it is scheduled in a particular processor.
    //It returns -1 if not all the parent nodes are already scheduled
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
        //finishing time of the node =
        // max(latest finishing time of the parent + transmission time + node weight, processor size + node weight)
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

    //This method finds earliest finishing node and processor out of given available node list.
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

    public void AvailableNode(ArrayList<Node> availableNode){
        //add nodes that has no incoming node from _nodeList into availableNode arraylist
        for(Node node: _nodeList){
            //만약 node의 모든 incoming nodes들이 scheduled list에 존재하고, 아직 schedule되지 않았다면면 그 노드는 availblenode이다.
            if(_scheduledNodes.containsAll(node.getincomingNodes()) && !_scheduledNodes.contains(node)){
                availableNode.add(node);
              //  System.out.println(node.getName());
               // System.out.println("***");
            }
        }
    }

}
