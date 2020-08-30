package OldVersions;


import Algorithm.Edge;
import Algorithm.Node;
import Algorithm.Processor;

import java.util.ArrayList;

/**
 * This class creates valid algorithm of scheduling set of tasks into a number of processors.
 */

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
     * This method is the starting point of the valid scheduling algorithm
     * @return
     */
    public ArrayList<Processor> run(){
        while (_nodeList.size() > 0) {
            // get a list of nodes with NO incoming edges
            ArrayList<Node> availableNode = new ArrayList<Node>();
            AvailableNode(availableNode);

            assignToProcessor(availableNode);
        }

        return _processorList;
    };

    /**
     * This method schedules nodes to processors
     * @param availableNodes
     */
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
     * This method find all nodes with no incoming edge
     * @param availableNode
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

