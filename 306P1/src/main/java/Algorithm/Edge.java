package Algorithm;

/**
 * This class creates an Algorithm.Edge object, which stores the startNode, endNode
 * and the weight of the edge. Algorithm.Edge objects will be stored in an arraylist
 * within the Main Class.
 */
public class Edge {
    private Node _startNode;
    private Node _endNode;
    private int _weight;


    //Constructor of an Algorithm.Edge object
    public Edge(Node startNode, Node endNode){
        _startNode = startNode;
        _endNode = endNode;
    }

    public Edge() {

    }

    /**
     *  Sets the weight of the edge as an integer
     */
    public void setWeight(String weight){
        String split = (weight.split("="))[1];
        String split2 = split.replace("}", "");
        _weight = Integer.parseInt(split2);
    }
    /**
     * @return: Simple Getters for Nodes
     */
    public Node getStartNode(){
        return _startNode;
    }
    /**
     * @return: Simple Getters for Nodes
     */
    public Node getEndNode(){
        return _endNode;
    }
    /**
     * Simple Getters for nodeWeights used in calculations.
     */
    public int getWeight(){
        return _weight;
    }
    /**
     * Simple setters for edges - startNode and endNodes
     */
    public void setNodes(Node startNode, Node endNode){
        _startNode = startNode;
        _endNode = endNode;
    }
    /**
     * This method returns a String of all the necessary Edge information in the format
     * of a .dot file. This method is used by the outputToDotFile method in the class TestMain.
     * @return
     */
    @Override
    public String toString(){
        return _startNode.getName() +  " -> " + _endNode.getName() + "\t" + " [Weight=" + _weight + "];";
    }
}