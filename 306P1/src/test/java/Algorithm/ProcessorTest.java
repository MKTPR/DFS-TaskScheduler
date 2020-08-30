package Algorithm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scala.Int;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ProcessorTest {

    private static int _processorNumber;
    private ArrayList<Node> _nodeList =new ArrayList<Node>();
    private ArrayList<Node> _optimalNodeList =new ArrayList<Node>();
    private Processor _processer;
    private Node nodeA;

    @Before
    public void setUp() throws Exception {
        nodeA = new Node("a");
        _processer = new Processor();
        _processer.setNode(null, 1);
        _processer.setNode(nodeA , 1);
        _nodeList.add(null);
        _nodeList.add(nodeA);
        _processorNumber++;
    }

    @After
    public void tearDown() throws Exception {
        _nodeList.clear();
        _optimalNodeList.clear();
    }

    @Test
    public void get_processorNumber() {
        assertEquals(Integer.toString(_processorNumber), _processer.get_processorNumber());
    }

    @Test
    public void removeNode() {
        _processer.removeNode(nodeA);
        _nodeList.clear();
        assertArrayEquals(_nodeList.toArray(), _processer.get_nodeList().toArray());
    }

    @Test
    public void get_nodeList() {
        assertArrayEquals(_nodeList.toArray(), _processer.get_nodeList().toArray());
    }

    @Test
    public void get_optimalNodeList() {
        _processer.set_optimalNodeListNode();
        _optimalNodeList = _nodeList;
        assertArrayEquals(_optimalNodeList.toArray(), _processer.get_optimalNodeList().toArray());
    }
}