import java.util.ArrayList;

public class ValidAlgorithm {
    private ArrayList<Node> _nodeList =new ArrayList<Node>();
    private ArrayList<Edge> _edgeList =new ArrayList<Edge>();
    private ArrayList<Processor> _processorList =new ArrayList<Processor>();

    public ValidAlgorithm(ArrayList<Node> nodeList, ArrayList<Edge> edgeList, ArrayList<Processor> processorList) {
        this._nodeList = nodeList;
        this._edgeList = edgeList;
        this._processorList = processorList;
    }

    /**
     * Algorithm의 시작점
     * @return
     */
    public ArrayList<Processor> run(){

        while (_nodeList.size() > 1) {
            // get a list of nodes with NO incoming edges
            ArrayList<Node> availableNode = new ArrayList<Node>();
            AvailableNode(availableNode);

            ComputeFinishingTime(availableNode);
            if (availableNode.size() >= 2) {
                FindLargestEdge(availableNode);
            }
            _nodeList.remove(availableNode.get(0)); //맨왼쪽에 들어가있는 node를 _nodeList에서 지움
            // _processorList.get(Integer.parseInt(availableNode.get(0)._process))).setNode(availableNode.get(0), availableNode.get(0)._weight); //내 영혼을 갈아넣은 한줄의 코드였다
            //Node class에 안에 있는 _process랑 _weight를 public으로 바꾸거나 getter method를 만들어야함


        } //루프가 끝나면 _processorList에 node들을 다 set 하게 됨

        //임시로 return null해놓음
        return null;
    };

    public void test(){
        ArrayList<Node> availableNode = new ArrayList<Node>();
        AvailableNode(availableNode);

    }


    /**
     * This method find all nodes with no incmming edge
     */
    public void AvailableNode(ArrayList<Node> availableNode){
        //add nodes that has no incoming node from _nodeList into availableNode arraylist
        for(Node node: _nodeList){
            if(node.getincomingNodes().size() == 0){
                availableNode.add(node);
            }
        }
    }

    /**
     * This method calculates earliest end time for a input node
     * @param node
     * @return
     */
    public int calculateEndTimeForOneNode(Node node){
        int communicationCost = 0;

        ArrayList<Edge> incomingEdges = new ArrayList<Edge>(); //list of all incoming edges of the node
        ArrayList<Edge> reqEdges = new ArrayList<Edge>(); // the incoming edges that requires communication cost


        // get list of incoming edges of the input node
        for(Edge edge : _edgeList){
            if(edge.getEndNode().equals(node)){
                incomingEdges.add(edge);
            }
        }

        //For each processor, try
        



        return 0;
    }

    /**
     * This method find the nodes that finish earliest
     */
    public void ComputeFinishingTime(ArrayList<Node> availableNode){
        ArrayList<Node> resultingNode = new ArrayList<Node>();
        Node earliestNode = availableNode.get(0);

        for(Node node : availableNode){
            if(calculateEndTimeForOneNode(earliestNode)>calculateEndTimeForOneNode(node)){
                earliestNode = node;
            }
        }

        for(Node node: availableNode){
            if(calculateEndTimeForOneNode(earliestNode)==calculateEndTimeForOneNode(node)){
                resultingNode.add(node);
            }
        }
        availableNode.clear();

        for(Node node : resultingNode){
            availableNode.add(node);
        }

        // availableNodes 안에 있는 node들중에 processor에 넣으면 가장 적은 end time을 갖고 있는 노드들만 남기고 다 지운다


        //for every nodes (i) in availableNode, find a processor (j) that has shorstest finishing time and set processor
        // availableNode.get(i).setProcessor(_processorList.get(j).get_processorNumber()); i랑 j는 int,

        //for every nodes in availableNode remove all nodes that are not finish earliest


        //혹시 너가 의문을 품을까봐 적어놓음
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

        ArrayList<Edge> candidateEdgeList = new ArrayList<Edge>();

        for(Node node : availableNode){
            for(Edge edge : _edgeList){
                if(edge.getEndNode().getName().equals(node.getName())){
                    candidateEdgeList.add(edge);
                }
            }
        }

        Edge maxEdge = candidateEdgeList.get(0);

        for(Edge edge : candidateEdgeList){
            if(edge.getWeight() > maxEdge.getWeight()) {
                maxEdge = edge;
            }
        }
        availableNode.clear();
        availableNode.add(maxEdge.getEndNode());
    }

}

