import java.util.ArrayList;

public class MakeTree {

    private static ArrayList<String> _currentBest = new ArrayList<>();
    private static ArrayList<Node> _nodesList;
    private static ArrayList<Processor> _processorList;
    private static ArrayList<Node> _scheduledNodes = new ArrayList<Node>();
    private static ArrayList<Node> availableNode = new ArrayList<Node>();
    private static int _numOfProcessors;

    public MakeTree(ArrayList<Node> nodeList, ArrayList<Processor> processorList, int numOfProcessors){
        _nodesList=nodeList;
        _processorList=processorList;
        _numOfProcessors= numOfProcessors;
    }

    public static void makeTree(String top, int nodeNumber, ArrayList<String> currentPath) {
        int index=0;
        String nodeProcessorComb = "";

        //Retrieve the index of the Node at string from nodesList
        for (Node i: _nodesList){
            String a = String.valueOf(top.charAt(nodeNumber));
            if (a.equals(i.getName())){
                index=_nodesList.indexOf(i);
                nodeProcessorComb = a;
            }
        }
        //Loop through various Processors
        for(int y=1;y<=_numOfProcessors;y++){
            //Schedule _nodesList.indexof(i) to processor y
            nodeProcessorComb = nodeProcessorComb + y;
            currentPath.add(nodeProcessorComb);
            if (/**getFinishingTime()<upperBound**/true){
                if(nodeNumber == (_nodesList.size()-1)){
                    /**_upperBound=getFinishingTime();**/
                    _currentBest=currentPath;
                }
                else {
                    nodeNumber++;
                    makeTree(top, nodeNumber, currentPath);
                    nodeNumber--;
                }
            }else {
                currentPath.remove(nodeNumber);
                //removeNodeFromProcessor();
            }
        }
    }

    public ArrayList<String> get_currentBest(){
        return _currentBest;
    }
}
