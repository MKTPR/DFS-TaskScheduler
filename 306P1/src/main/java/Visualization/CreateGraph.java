package Visualization;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import java.util.ArrayList;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

public class CreateGraph {
    Graph _graph = new SingleGraph("Graph");
    ArrayList<Algorithm.Node> _nodesList;
    ArrayList<Algorithm.Edge> _edgesList;

    CreateGraph(ArrayList<Algorithm.Node> nodeList, ArrayList<Algorithm.Edge> edgeList){
        _nodesList = nodeList;
        _edgesList = edgeList;
        parseInput();
        _graph.display();
    }

    public void parseInput(){
        for(Algorithm.Node _node:_nodesList){
            _graph.addNode(_node.getName());
        }
        for(Algorithm.Edge _edge:_edgesList){
            //string of each Node in edgeList
            _graph.addEdge(_edge.getStartNode().getName() + _edge.getEndNode().getName(),_edge.getStartNode().getName(),_edge.getEndNode().getName());
        }
    }

}