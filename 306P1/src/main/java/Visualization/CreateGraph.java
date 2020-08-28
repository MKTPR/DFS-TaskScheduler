package Visualization;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import java.util.ArrayList;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

public class CreateGraph {
    Graph _graph = new SingleGraph("Graph");
    ArrayList<Algorithm.Node> _nodesList = new ArrayList<>();
    ArrayList<Algorithm.Edge> _edgesList = new ArrayList<>();
    ArrayList<Node> _gNodesList = new ArrayList<>();
    ArrayList<Edge> _gEdgesList = new ArrayList<>();

    public CreateGraph(ArrayList<Algorithm.Node> nodesList, ArrayList<Algorithm.Edge> edgesList){
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        _nodesList = nodesList;
        _edgesList = edgesList;

        parseInputToGraph(this._nodesList, this._edgesList);

    }

    /**
     * This method displays graph
     */
    public void diplayGraph(){
        _graph.display();
    }

    /**
     * This method creates a graph with given nodeslist and edgeslist
     * @param nodesList
     * @param edgesList
     * @return Graph
     */
    public Graph parseInputToGraph(ArrayList<Algorithm.Node> nodesList, ArrayList<Algorithm.Edge> edgesList){
        for(Algorithm.Node n: nodesList){
            this._graph.addNode(n.getName());
        }
        for(Algorithm.Edge e: edgesList){
            //string of each Node in edgeList
            this._graph.addEdge(e.getStartNode().getName() + e.getEndNode().getName(),e.getStartNode().getName(),
                    e.getEndNode().getName(), true);
        }

        saveNodesFromGraph(this._graph);
        saveEdgesFromGraph(this._graph);
        setGraphVisual(this._graph);
        setNameToNodes();
        return this._graph;
    }

    /**
     * This method assigned graphically visualizable name to each node
     */
    public void setNameToNodes(){
        for(Node node:this._graph.getEachNode()) {
            node.setAttribute("ui.label", node.getId());
        }
    }


    public void setGraphVisual(Graph graph){
        this._graph.setAttribute("stylesheet",
                "node { "
                        + "     shape: circle; "
                        + "		size: 50px; "
                        + "     stroke-mode: plain; "
                        + "		stroke-color: black;"
                        + "		fill-color: white;"
                        + "		text-alignment: center;"
                        + "		text-size: 20px;"
                        + "		text-padding: 7px;"
                        + "} "
                        + "edge { "
                        + "		fill-color: black; "
                        + "}");
    }


    /**
     * This method returns Node list from a Graph
     */
    public ArrayList<Node> saveNodesFromGraph(Graph graph){
        for(Node n:graph.getEachNode()) {
            System.out.println(n.getId());
            this._gNodesList.add(n);
        }
        return this._gNodesList;
    }

    /**
     * This method returns Edge list from a Graph
     */
    public ArrayList<Edge> saveEdgesFromGraph(Graph graph){
        for(Edge e:graph.getEachEdge()) {
            System.out.println(e.getId());
            this._gEdgesList.add(e);
        }
        return this._gEdgesList;
    }
}

