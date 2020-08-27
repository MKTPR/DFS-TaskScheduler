import java.util.ArrayList;

/**
 * This class creates a Node object, which stores the name, weight
 * delegated processor and a list of incoming nodes. Node objects
 * will be stored in an arraylist within the Main Class.
 */
public class Node {
    private String _name;
    private Processor _process;
    private int _weight;
    private ArrayList<Node> _incomingNodes=new ArrayList<Node>();
    private ArrayList<Node> _outgoingNodes =new ArrayList<Node>();
    private ArrayList<Edge> _outgoingEdges =new ArrayList<Edge>();
    private ArrayList<Edge> _incomingEdges =new ArrayList<Edge>();


    //Constructor for a Node Object
    public Node(String Name){
        _name = Name;
    }

    //Records the weight of an object as an int
    public void setWeight(String weight){
        String split = (weight.split("="))[1];
        String split2 = split.replace("}", "");
        _weight = Integer.parseInt(split2);
    }

    //Adds an incoming node name(String) into an arraylist
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
        _process=processorName;
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

    @Override
    public String toString(){
        String processor;
        String startTime;
        if(_process == null){
            processor = "none";
            startTime = "none";
        }else{
            processor = _process.get_processorNumber();
            startTime = Integer.toString(_process.get_optimalNodeList().indexOf(this));
        }
        String nodeprint = "";
        for(Node iNode : _incomingNodes){
            nodeprint += iNode;
        }
        //System.out.println(nodeprint);
        return _name + "\t\t" + " [Weight=" + _weight + ",Start=" + startTime + ",Processor=" + processor +"];";
    }
}