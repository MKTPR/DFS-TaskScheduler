import java.util.ArrayList;
import java.util.Collection;

public class Processor {

    private String _processorNumber;
    private ArrayList<Node> _nodeList =new ArrayList<Node>();
    private static int pCount = 1;

    //Constructor of pNumber, changes int to a String
    public Processor() {
        _processorNumber = String.valueOf(pCount);
        pCount++;
    }

    //Adds a node to a working queue of a processor with regards of the duration.
    public void setNode(Node dNode, int duration){
        for (int i=1;i<=duration;i++) {
            _nodeList.add(dNode);
        }
    }

    public void removeNode(Node dNode){
        _nodeList.removeIf(dNode::equals);
    }

    public ArrayList<Node> get_nodeList(){
        return _nodeList;
    }

    public String get_processorNumber(){
        return _processorNumber;
    }

    @Override
    public String toString(){
        return "processor num: " + _processorNumber;
    }
}