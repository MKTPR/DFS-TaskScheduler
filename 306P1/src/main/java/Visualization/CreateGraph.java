package Visualization;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import java.awt.*;
import java.util.ArrayList;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;

public class CreateGraph {
    Graph _graph = new SingleGraph("Graph");
    ArrayList<Algorithm.Node> _nodesList = new ArrayList<>();
    ArrayList<Algorithm.Edge> _edgesList = new ArrayList<>();
    ArrayList<Node> _gNodesList = new ArrayList<>();
    ArrayList<Edge> _gEdgesList = new ArrayList<>();

    public CreateGraph(ArrayList<Algorithm.Node> nodesList, ArrayList<Algorithm.Edge> edgesList){
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
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
     * This method set css for the graph
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

    /**
     * This method returns Jpanel to insert View into Java Swing GUI
     * @return
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

