public class Edge {
    private Node _startNode;
    private Node _endNode;
    private int _weight;


    public Edge(Node startNode, Node endNode){
        _startNode = startNode;
        _endNode = endNode;
    }

    public void setWeight(String weight){
        String split = (weight.split("="))[1];
        _weight = Character.getNumericValue(split.charAt(0));
    }

    public Node getStartNode(){
        return _startNode;
    }

    public Node getEndNode(){
        return _endNode;
    }

    public int getWeight(){
        return _weight;
    }
}
