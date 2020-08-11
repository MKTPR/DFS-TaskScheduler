import java.util.ArrayList;

public class Node {
    private String _name;
    private String _process;
    private int _weight;
    private static ArrayList<String> _incomingNodes=new ArrayList<String>();


    public Node(String Name){
        _name = Name;
    }

    public void setWeight(String weight){
        String split = (weight.split("="))[1];
        _weight = Character.getNumericValue(split.charAt(0));
    }

    public void setIncomingNodes(String incomeingNode){
        _incomingNodes.add(incomeingNode);
    }

    public String getName(){
        return _name;
    }

    public ArrayList<String> getincomingNodes(){
        return _incomingNodes;
    }

    public int getWeight(){
        return _weight;
    }
}
