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
            if (availableNode.size() >= 2) {
                FindLargestEdge(availableNode);
            }
            _nodeList.remove(availableNode.get(0)); //_nodeList에서 지움
            //availableNode.get(0)._process //맨왼쪽에 들어가있는 node
                                            //Node class에 안에 있는 _process public으로 바꾸거나 getter method를 만들어야함
            //Add selected n to corresponding Pi then remove the n from G
          }


    }

    /**
     * This method find all nodes with no incmming edge
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

        //for every nodes (i) in availableNode, find a processor that has shorstest finishing time and set processor
        //availableNode.get(i).setProcessor(_processorList.get(j).get_processorNumber()); i랑 j는 int,

        //for every nodes in availableNode remove all nodes that are not finish earliest

        //여기서 node.setProcessor를 해도 괜찮음, 왜냐하면 while 루프 한번에 earlist finishing time인 node 하나씩 _nodeList에서 지워가는데
        //_nodeList에서 지워지는 earlist finishing time인 node의 processor는 여기서 지정하는 processor에 들어가는 node이고
        //earlist finishing time이 아니라서 이번 while 루프에서 안지워지는 node는 다음 while loop때 다시 setProcessor를 하기 때문에
        //마지막 루프가 끝날때는 모든 node는 제대로된 processor에 들어가게됨
    }

    /**
     * This method find the nodes with largest incoming edge
     */
    public void FindLargestEdge(ArrayList<Node> availableNode){
        //remove all nodes with not largest incoming edge weight
    }

}

