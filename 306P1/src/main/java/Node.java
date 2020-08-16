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
    private ArrayList<String> _incomingNodes=new ArrayList<String>();

    //Constructor for a Node Object
    public Node(String Name){
        _name = Name;
    }

    //Records the weight of an object as an int
    public void setWeight(String weight){
        String split = (weight.split("="))[1];
        _weight = Character.getNumericValue(split.charAt(0));
    }

    //Adds an incoming node name(String) into an arraylist
    public void setIncomingNodes(String incomingNode){
        _incomingNodes.add(incomingNode);
    }

    //Sets the processor that the NodeObject is delegated to
    public void setProcessor(Processor processorName){
        _process=processorName;
    }

    public String getName(){
        return _name;
    }

    public ArrayList<String> getincomingNodes(){
        return _incomingNodes;
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
            startTime = Integer.toString(_process.get_nodeList().indexOf(this));
        }
        String nodeprint = "";
        for(String iNode : _incomingNodes){
            nodeprint += iNode;
        }
        //System.out.println(nodeprint);
        return _name + "\t\t" + " [Weight=" + _weight + ",Start=" + startTime + ",Processor=" + processor +"];";
    }
}