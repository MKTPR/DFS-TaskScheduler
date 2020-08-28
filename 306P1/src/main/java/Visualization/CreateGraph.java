package Visualization;

import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
 import java.util.ArrayList;

public class CreateGraph {
    Graph _graph = new SingleGraph("Graph");
    ArrayList<Algorithm.Node> _nodesList;
    ArrayList<Algorithm.Edge> _edgesList;

    CreateGraph(){

    }

    // Get list of all nodes
    public void inputNode(){
        for(Algorithm.Node _node:Algorithm.TestMain.getNodeList()){
            _graph.addNode(_node.getName());
        }
}
    // Get list of all edges

}