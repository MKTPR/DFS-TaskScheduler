/**
 * This class creates an Edge object, which stores the startNode, endNode
 * and the weight of the edge. Edge objects will be stored in an arraylist
 * within the Main Class.
 */
public class Edge {
    private Node _startNode;
    private Node _endNode;
    private int _weight;


    //Constructor of an Edge object
    public Edge(Node startNode, Node endNode){
        _startNode = startNode;
        _endNode = endNode;
    }

    //Sets the weight of the edge as an integer
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

    @Override
    public String toString(){
        return _startNode.getName() + "->" + _endNode.getName() + " {Weight=" + _weight + "}";
    }
}
