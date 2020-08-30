package Algorithm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EdgeTest {

    private Node _startNode;
    private Node _endNode;
    private int _weight;
    private Edge _edge;

    @Before
    public void setUp() throws Exception {
        _startNode = new Node("a");
        _endNode = new Node("b");
        _weight = 3;
        _edge = new Edge(_startNode,_endNode);
        _edge.setWeight("{weight=500}");

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getStartNode() {
        assertEquals(_startNode, _edge.getStartNode());
    }

    @Test
    public void getEndNode() {
        assertEquals(_endNode, _edge.getEndNode());
    }

    @Test
    public void getWeight() {
        assertEquals(500, _edge.getWeight());
    }

    @Test
    public void testToString() {
        String output = "a->b[Weight=500];";
        assertEquals(output,_edge.toString().replace(" ", "").replace("\t",""));
    }
}