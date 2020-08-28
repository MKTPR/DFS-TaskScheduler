package Visualization;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
 import java.util.ArrayList;

public class CreateGraph {
    Graph _graph = new SingleGraph("Graph");
    ArrayList<Algorithm.Node> _nodesList;
    ArrayList<Algorithm.Edge> _edgesList;

    CreateGraph(ArrayList<Algorithm.Node> nodeList, ArrayList<Algorithm.Edge> edgelist){
        _nodesList = nodeList;
        _edgesList = edgelist;
    }

    // Get list of all nodes
    public void parseInput(){
        for(Algorithm.Node _node:_nodesList){
            _graph.addNode(_node.getName());
        }
        for(Algorithm.Edge _edge:_edgesList){
            _graph.addEdge(_edge.getStartNode().getName() + _edge.getEndNode().getName(),_edge.getStartNode().getName(),_edge.getEndNode().getName());
        }
}
    // Get list of all edges

}