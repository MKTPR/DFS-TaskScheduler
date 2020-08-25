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

    public ArrayList<Processor> run(){
        NodeProcessor nodeProcessor;
        while (_nodeList.size() > 0) {
            // get a list of nodes with NO incoming edges

            AvailableNode(availableNode);

            nodeProcessor = findEarliestFinishingNodeProcessor(availableNode);


            // availableNode에서 schedule된 노드 지우기

        }

        return _processorList;
    };

    //This method finds transmission time of a node when it is scheduled in a particular processor.
    //It returns -1 if not all the parent nodes are already scheduled
    private int findFinishingTime(Node node, Processor processor){
        ArrayList<Node> parentNodes = node.getincomingNodes();
        int latestFinTime = 0;
        int tempFinTime = 0;
        int FinTimeSameProcessor = 0;


        //Check if all its parents are scheduled
        if(!_scheduledNodes.containsAll(parentNodes)){
            return -1;
        }

        //If all parents are scheduled in the input processor, no transmission time is required.
        if(_scheduledNodes.containsAll(parentNodes)){
            FinTimeSameProcessor = processor.get_nodeList().size() + node.getWeight();
            return FinTimeSameProcessor;
        }

        //If any parent is scheduled in another processor, transmission time may have to be taken account.
        //finishing time of the node =
        // max(latest finishing time of the parent + transmission time + node weight, processor size + node weight)
        //get parent with latest finishing time
        for(Node pNode : parentNodes){
            for(Processor pProcessor:_processorList){
                if(pProcessor.get_nodeList().contains(pNode)){
                    tempFinTime = pProcessor.get_nodeList().indexOf(pNode) + pNode.getWeight() + 1;//get finishing time
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
            //만약 node의 모든 incoming nodes들이 scheduled lis에 존재하고, 아직 schedule되지 않았다면면 그 노드는 availblenode이다.
            if(_scheduledNodes.containsAll(node.getincomingNodes()) && !_scheduledNodes.contains(node)){
                availableNode.add(node);
            }
        }
    }

}
