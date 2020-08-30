package Algorithm;

import java.util.ArrayList;

/**
 * This class creates a Algorithm.Node object, which stores the name, weight
 * delegated processor and a list of incoming and outgoing nodes/edges. Algorithm.Node objects
 * will be stored in an arraylist within the Main Class.
 */
public class Node {
    private String _name;
    private Processor _processor;
    private int _weight;
    private ArrayList<Node> _incomingNodes=new ArrayList<Node>();
    private ArrayList<Node> _outgoingNodes =new ArrayList<Node>();
    private ArrayList<Edge> _outgoingEdges =new ArrayList<Edge>();
    private ArrayList<Edge> _incomingEdges =new ArrayList<Edge>();


    //Constructor for a Algorithm.Node Object
    public Node(String Name){
        _name = Name;
    }

    //Records the weight of an object as an int
    public void setWeight(String weight){
        String split = (weight.split("="))[1];
        String split2 = split.replace("}", "");
        _weight = Integer.parseInt(split2);
    }

    /**
     * Basic Setters and Getters for the Node object fields.
     */
    public void setIncomingNodes(Node incomingNode){
        _incomingNodes.add(incomingNode);
    }

    public void setIncomingEdges(Edge incomingEdge){
        _incomingEdges.add(incomingEdge);
    }

    public void setOutgoingEdges(Edge outgoingEdge) { _outgoingEdges.add(outgoingEdge); }

    public void setOutgoingNodes(Node outgoingNode) { _outgoingNodes.add(outgoingNode); }

    //Sets the processor that the NodeObject is delegated to
    public void setProcessor(Processor processorName){
        _processor =processorName;
    }

    public String getName(){
        return _name;
    }

    public ArrayList<Node> getincomingNodes(){
        return _incomingNodes;
    }
    public ArrayList<Edge> get_incomingEdges(){
        return _incomingEdges;
    }

    public int getWeight(){
        return _weight;
    }

    /**
     * This method returns a String of all the necessary Node information in the format
     * of a .dot file. This method is used by the outputToDotFile method in the class TestMain.
     * @return
     */
    @Override
    public String toString(){
        String processor;
        String startTime;
        if(_processor == null){
            processor = "none";
            startTime = "none";
        }else{
            processor = _processor.get_processorNumber();
            startTime = Integer.toString(_processor.get_optimalNodeList().indexOf(this));
        }
        String nodeprint = "";
        for(Node iNode : _incomingNodes){
            nodeprint += iNode;
        }
        //System.out.println(nodeprint);
        return _name + "\t\t" + " [Weight=" + _weight + ",Start=" + startTime + ",Processor=" + processor +"];";
    }
}