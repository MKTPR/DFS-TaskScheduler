import java.util.ArrayList;

public class ValidAlgorithm {
    private ArrayList<Node> _nodeList =new ArrayList<Node>();
    private ArrayList<Edge> _edgeList =new ArrayList<Edge>();
    private ArrayList<Processor> _processorList =new ArrayList<Processor>();

    public ValidAlgorithm(ArrayList<Node> nodeList, ArrayList<Edge> edgeList, ArrayList<Processor> processorList) {
        _nodeList = nodeList;
        _edgeList = edgeList;
        _processorList = processorList;

          while (_nodeList.size() > 1) {
              ArrayList<Node> availableNodeList = AvailableNode();
            if (availableNodeList.size() >= 2){

            } else{

            }
            
          }


    }

    /**
     * This method list all nodes that has no incomming edge
     */
    public ArrayList<Node> AvailableNode(){
        ArrayList<Node> availableNode = new ArrayList<Node>();

        //add nodes that has no incoming edge into availableNode arraylist

        Compute(availableNode);
        return  availableNode;
    }

    /**
     * This method find the nodes that finish earliest
     *
     * 은강아, availableNode를 parameter로 받아서 node를 지우는건데
     * availableNode를 compute() 메소드 안에서 바꾸면 avaialbeNode가 availableNode() 메소드에서도 바뀌지?
     * 안바뀌면 void로 해놓으면 안되고 arrayList로 output 시켜야됨
     */
    public void Compute(ArrayList<Node> availableNode){
        //for every nodes in availableNode{
        //remove all nodes that are not finish earliest  }
    }


}

