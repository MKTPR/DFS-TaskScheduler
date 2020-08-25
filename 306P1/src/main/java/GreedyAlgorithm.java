import java.util.ArrayList;

public class GreedyAlgorithm {

    private ArrayList<Node> _nodeList =new ArrayList<Node>();
    private ArrayList<Edge> _edgeList =new ArrayList<Edge>();
    private ArrayList<Processor> _processorList =new ArrayList<Processor>();

    public GreedyAlgorithm(ArrayList<Node> nodeList, ArrayList<Edge> edgeList, ArrayList<Processor> processorList) {
        this._nodeList = nodeList;
        this._edgeList = edgeList;
        this._processorList = processorList;
    }

    public ArrayList<Processor> run(){
        while (_nodeList.size() > 0) {
            // get a list of nodes with NO incoming edges
            ArrayList<Node> availableNode = new ArrayList<Node>();
            AvailableNode(availableNode);

            FindFinishingTime(availableNode);


        }

        return _processorList;
    };
    public void FindFinishingTime(ArrayList<Node> availableNode){
        int minimum = availableNode.get(0).getWeight() + _processorList.get(0).get_nodeList().size(); // + transmission cost
        if (_processorList.get(0).get_nodeList().containsAll(availableNode.get(0).getincomingNodes())) {
            minimum = availableNode.get(0).getWeight() + _processorList.get(0).get_nodeList().size();
        }
        for(Node node: availableNode){
            for(Processor processor: _processorList){
                if(processor.get_nodeList().containsAll(node.getincomingNodes())){
                    if((node.getWeight() + processor.get_nodeList().size()) < minimum){
                        minimum = (node.getWeight() + processor.get_nodeList().size());
                    } else{
                        int minimum = availableNode.get(0).getWeight() + _processorList.get(0).get_nodeList().size(); // + transmission cost
                    }
                }
            }
        }


    }


    public void AvailableNode(ArrayList<Node> availableNode){
        //add nodes that has no incoming node from _nodeList into availableNode arraylist
        for(Node node: _nodeList){
            if(node.getincomingNodes().size() == 0){
                availableNode.add(node);
            }
        }
    }

}
