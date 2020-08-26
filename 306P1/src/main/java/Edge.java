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

    public Edge() {

    }

    //Sets the weight of the edge as an integer
    public void setWeight(String weight){
        String split = (weight.split("="))[1];
        String split2 = split.replace("}", "");
        _weight = Integer.parseInt(split2);
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

    public void setNodes(Node startNode, Node endNode){
        _startNode = startNode;
        _endNode = endNode;
    }

    @Override
    public String toString(){
        return _startNode.getName() +  " -> " + _endNode.getName() + "\t" + " [Weight=" + _weight + "];";
    }
}