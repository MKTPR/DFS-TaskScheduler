package Algorithm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GreedyAlgorithmTest {

    private ArrayList<Node> _nodeList =new ArrayList<Node>();
    private ArrayList<Edge> _edgeList =new ArrayList<Edge>();
    private ArrayList<Processor> _processorList =new ArrayList<Processor>();
    private ArrayList<Node> _scheduledNodes = new ArrayList<Node>();
    private ArrayList<Node> availableNode = new ArrayList<Node>();
    private GreedyAlgorithm GA;

    @Before
    public void setUp() throws Exception {
        Node a = new Node("a");
        a.setWeight("{weight=1}");
        Node b = new Node("b");
        b.setWeight("{weight=2}");
        Edge ab = new Edge(a,b);
        ab.setWeight("{weight=3}");
        a.setOutgoingEdges(ab);
        a.setOutgoingNodes(b);
        b.setIncomingEdges(ab);
        b.setIncomingNodes(a);

        _nodeList.add(a);
        _nodeList.add(b);
        _edgeList.add(ab);

        _processorList.add(new Processor());
        _processorList.add(new Processor());
        GA = new GreedyAlgorithm(_nodeList,_edgeList,_processorList);
    }

    @After
    public void tearDown() throws Exception {
        _processorList.clear();
        _nodeList.clear();
        _edgeList.clear();
        _scheduledNodes.clear();
        availableNode.clear();
    }


    @Test
    public void computeGreedyFinishingTime() {
        assertEquals(3, GA.computeGreedyFinishingTime() );
    }

    @Test
    public void runAlgorithm() {
        _processorList.get(0).setNode(_nodeList.get(0),1);
        _processorList.get(0).setNode(_nodeList.get(1),2);
        assertArrayEquals(_processorList.toArray(), GA.runAlgorithm().toArray() );
        assertArrayEquals(_processorList.get(0).get_nodeList().toArray(), GA.runAlgorithm().get(0).get_nodeList().toArray());
        assertArrayEquals(_processorList.get(1).get_nodeList().toArray(), GA.runAlgorithm().get(1).get_nodeList().toArray());
    }

    @Test
    public void findEarliestFinishingNodeProcessor() {
        availableNode.add(_nodeList.get(0));
        assertEquals(_nodeList.get(0), GA.findEarliestFinishingNodeProcessor(availableNode).getNode());
        assert(_processorList.contains(GA.findEarliestFinishingNodeProcessor(availableNode).getProcessor()));
    }

}