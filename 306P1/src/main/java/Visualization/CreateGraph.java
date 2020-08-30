package Visualization;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import java.awt.*;
import java.util.ArrayList;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;

/**
 * This class has responsibility of generating visualized graph using GraphStream external library.
 * With a give node list and edge list via constructor, produceJPanel() method will return Java Swing mountable
 * GUI JPanel
 * object.
 */
public class CreateGraph {
    Graph _graph = new SingleGraph("Graph");
    ArrayList<Algorithm.Node> _nodesList;
    ArrayList<Algorithm.Edge> _edgesList;
    ArrayList<Node> _gNodesList = new ArrayList<>();
    ArrayList<Edge> _gEdgesList = new ArrayList<>();

    public CreateGraph(ArrayList<Algorithm.Node> nodesList, ArrayList<Algorithm.Edge> edgesList){
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        _nodesList = nodesList;
        _edgesList = edgesList;

        parseInputToGraph(this._nodesList, this._edgesList);

    }

    /**
     * This method creates a Graph object with given nodeslist and edgeslist
     * @param nodesList
     * @param edgesList
     * @return Graph (GraphStream)
     */
    public Graph parseInputToGraph(ArrayList<Algorithm.Node> nodesList, ArrayList<Algorithm.Edge> edgesList){
        for(Algorithm.Node n: nodesList){
            String nodeID = n.getName();
            this._graph.addNode(nodeID);
            this._graph.getNode(nodeID).setAttribute("ui.label", nodeID);
        }
        for(Algorithm.Edge e: edgesList){
            //string of each Node in edgeList
            String startNode = e.getStartNode().getName();
            String endNode = e.getEndNode().getName();
            String edgeID = startNode + endNode;
            this._graph.addEdge(edgeID, startNode, endNode,true);
            this._graph.getEdge(edgeID).setAttribute("ui.label", e.getWeight());
        }

        saveNodesFromGraph(this._graph);
        saveEdgesFromGraph(this._graph);
        setGraphVisual(this._graph);

        return this._graph;
    }


    /**
     * This method sets visualization (style) attributes of the graph using css
     * @param graph
     */
    public void setGraphVisual(Graph graph){
        this._graph.setAttribute("stylesheet",
                "node { "
                        + "		text-size: 20px;"
                        + "		text-padding: 7px;"
                        + "     text-color: white;"
                        + "     stroke-mode: plain; "
                        + "		stroke-color: black;"
                        + "		fill-color: black;"
                        + "		text-alignment: center;"
                        + "     shape: circle; "
                        + "		size: 50px; "
                        + "} "
                        + "node#0{ "
                        + "		text-size: 22px;"
                        + "     text-color: white;"
                        + "		stroke-color: gray;"
                        + "		fill-color: rgb(150,150,150);"
                        + "} "
                        + "edge { "
                        + "		fill-color: rgb(120,120,120); "
                        + "		text-size: 15px;"
                        + "}"
        );
    }


    /**
     * This method pulls out the reference of each node from the graph and stores it is an array of nodes.
     * @param graph
     * @return Node list
     */
    public ArrayList<Node> saveNodesFromGraph(Graph graph){
        for(Node n:graph.getEachNode()) {
           // System.out.println(n.getId());
            this._gNodesList.add(n);
        }
        return this._gNodesList;
    }

    /**
     * This method pulls out the reference of each edge from the graph and stores it is an array of edges.
     * @param graph
     * @return Edge list
     */
    public ArrayList<Edge> saveEdgesFromGraph(Graph graph){
        for(Edge e:graph.getEachEdge()) {
            //System.out.println(e.getId());
            this._gEdgesList.add(e);
        }
        return this._gEdgesList;
    }

    /**
     * This method converts the graph into Java Swing JPanel object.
     * @return JPanel object
     */
    public JPanel produceJPanel(){
        JPanel myJPanel = new JPanel();
        myJPanel.setLayout(new BorderLayout());
        Viewer v = new Viewer(this._graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        v.enableAutoLayout();
        ViewPanel vp = v.addDefaultView(false);
        myJPanel.add(vp, BorderLayout.CENTER);
        return myJPanel;
    }

}

