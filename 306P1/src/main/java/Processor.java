import java.util.ArrayList;
import java.util.Collection;

public class Processor {

    private String _processorNumber;
    private ArrayList<Node> _nodeList =new ArrayList<Node>();
    private ArrayList<Node> _optimalNodeList =new ArrayList<Node>();
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

    public void set_optimalNodeListNode(){
        _optimalNodeList =new ArrayList<Node>(_nodeList);
    }

    public void removeNode(Node dNode){
        int x = _nodeList.indexOf(dNode);

        for (int i =x-1 ; i>=0 ; i--){
            if(_nodeList.get(i)==null){
                _nodeList.remove(i);
            }else {
                break;
            }
        }
        _nodeList.removeIf(dNode::equals);
    }

    public ArrayList<Node> get_nodeList(){
        return _nodeList;
    }

    public ArrayList<Node> get_optimalNodeList(){
        return _optimalNodeList;
    }

    public String get_processorNumber(){
        return _processorNumber;
    }

    @Override
    public String toString(){
        return "processor num: " + _processorNumber;
    }
}