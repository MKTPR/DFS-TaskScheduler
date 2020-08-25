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

}
