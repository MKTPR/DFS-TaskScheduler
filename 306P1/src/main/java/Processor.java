import java.util.ArrayList;

public class Processor {

    private String _processorNumber;
    private static ArrayList<Node> _nodeList =new ArrayList<Node>();

    //Constructor of pNumber, changes int to a String
    public Processor(int pNumber){
        _processorNumber = String.valueOf(pNumber);
    }

    //Adds a node to a working queue of a processor with regards of the duration.
    public void setNode(Node dNode, int duration){
        for (int i=0;i<=duration;i++) {
            _nodeList.add(dNode);
        }
    }

    public String get_processorNumber(){
        return _processorNumber;
    }

}
