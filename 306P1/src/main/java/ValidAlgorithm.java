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
              ArrayList<Node> availableNode = new ArrayList<Node>();  //여기 !!!!
              AvailableNode(availableNode); //여기 !!!!
              ComputeFinishingTime(availableNode); //여기 !!!!
            if (availableNode.size() >= 2){

            } else{

            }
            //Add selected n to corresponding Pi then remove the n from G
          }


    }

    /**
     * This method find all nodes with no incomming edge
     * 은강아, availableNode() 메소드 안에서 availableNode arrayList를 바꾸면 avaialbeNode arrayList가 저 위에서도 바뀌지?? (여기 !!! 라 표시해놓은곳)
     * 안바뀌면 void로 해놓으면 안되고 arrayList로 output 시켜야됨
     */
    public void AvailableNode(ArrayList<Node> availableNode){
        //add nodes that has no incoming edge into availableNode arraylist
    }

    /**
     * This method find the nodes that finish earliest
     */
    public void ComputeFinishingTime(ArrayList<Node> availableNode){
        //for every nodes in availableNode{
        //remove all nodes that are not finish earliest  }
    }


}

