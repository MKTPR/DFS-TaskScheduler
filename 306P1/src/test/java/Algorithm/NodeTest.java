package Algorithm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class NodeTest {
    private String _name;
    private Processor _processor;
    private int _weight;
    private Node _node;

    @Before
    public void setUp() throws Exception {
        _name = "a";
        _processor = new Processor();
        _weight = 600;

        _node = new Node("a");
        _node.setWeight("{weight=600}");
        _node.setProcessor(_processor);
        _processor.setNode(_node,600);
        _processor.set_optimalNodeListNode();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getName() {
        assertEquals("a",_node.getName());
    }

    @Test
    public void getWeight() {
        assertEquals(600, _node.getWeight());
    }

    @Test
    public void testToString() {
        String output = "a[Weight=600,Start=0,Processor=1];";
        assertEquals(output,_node.toString().replace(" ", "").replace("\t","") );
    }
}