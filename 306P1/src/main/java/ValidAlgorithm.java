import java.lang.reflect.Array;
import java.util.ArrayList;

public class ValidAlgorithm {
    private ArrayList<Node> _nodeList =new ArrayList<Node>();
    private ArrayList<Edge> _edgeList =new ArrayList<Edge>();
    private ArrayList<Processor> _processorList =new ArrayList<Processor>();

    public ValidAlgorithm(ArrayList<Node> nodeList, ArrayList<Edge> edgeList, ArrayList<Processor> processorList) {
        this._nodeList = nodeList;
        this._edgeList = edgeList;
        this._processorList = processorList;
    }

    /**
     * Start of the algorithm
     * @return
     */
    public ArrayList<Processor> run(){

        System.out.println("Before running, nodelist size is : " + _nodeList.size());
        while (_nodeList.size() > 0) {
            // get a list of nodes with NO incoming edges
            ArrayList<Node> availableNode = new ArrayList<Node>();
            AvailableNode(availableNode);

            assignToProcessor(availableNode);
            availableNode.clear();

            System.out.println("nodelist contains:");
            for(Node node:_nodeList){
                System.out.println(node.getName());
            }

            System.out.println("nodelist size is : " + _nodeList.size());
        }



        System.out.println("Processor contains:");
        for(Node node: _processorList.get(0).get_nodeList()){
            System.out.println(node.getName());
        }

        return _processorList;
    };


    public void assignToProcessor(ArrayList<Node> availableNodes){
        ArrayList<String> namesToRemove = new ArrayList<String>();

        for(Node node:availableNodes){
            _processorList.get(0).setNode(node, node.getWeight());
        }

        for(Node node:availableNodes){
            namesToRemove.add(node.getName());
            _nodeList.remove(node);
        }

        for(Node node: _nodeList){
            for(String name : namesToRemove){
                node.getincomingNodes().remove(name);
            }

        }

    }


    /**
     * This method find all nodes with no incmming edge
     */
    public void AvailableNode(ArrayList<Node> availableNode){
        //add nodes that has no incoming node from _nodeList into availableNode arraylist
        for(Node node: _nodeList){
            if(node.getincomingNodes().size() == 0){
                availableNode.add(node);
            }
        }
    }


}

