import java.util.ArrayList;

public class MakeTree {

    private static ArrayList<String> _currentBest = new ArrayList<>();
    private static ArrayList<Node> _nodesList;
    private static ArrayList<Processor> _processorList;
    private static ArrayList<Node> _scheduledNodes = new ArrayList<Node>();
    private static int _numOfProcessors;
    private static int _upperBound;

    public MakeTree(ArrayList<Node> nodeList, ArrayList<Processor> processorList, int numOfProcessors, int upperBound){
        _nodesList=nodeList;
        _processorList=processorList;
        _numOfProcessors= numOfProcessors;
        _upperBound=upperBound;
    }

    public static void makeTree(String top, int nodeNumber, ArrayList<String> currentPath) {
        int index=0;
        String nodeProcessorComb = "";

        //Retrieve the index of the Node at string from nodesList
        for (Node i: _nodesList){
            String a = String.valueOf(top.charAt(nodeNumber));
            if (a.equals(i.getName())){
                index=_nodesList.indexOf(i);
                nodeProcessorComb = a;
            }
        }
        //Loop through various Processors
        for(int y=0;y<=_numOfProcessors;y++){
            Node node = _nodesList.get(index);
            Processor processor = _processorList.get(y);
            scheduleNodeToProcessor( node, processor);
            nodeProcessorComb = nodeProcessorComb + y;
            currentPath.add(nodeProcessorComb);
            int _duration = findFinishingTime(node, processor);
            if (_duration <_upperBound){
                if(nodeNumber == (_nodesList.size()-1)){
                    _upperBound=_duration;
                    _currentBest=currentPath;
                }
                else {
                    nodeNumber++;
                    makeTree(top, nodeNumber, currentPath);
                    nodeNumber--;
                }
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
        //remove from the nodelist
        _nodesList.remove(node);
    }

    private static int findFinishingTime(Node node, Processor processor){
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

    public ArrayList<String> get_currentBest(){
        return _currentBest;
    }
}
