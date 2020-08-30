package Visualization;

import Algorithm.Node;
import Algorithm.Processor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TableViewTest {

    private String[][] _data;
    private ArrayList<Processor> _optimalProcessorList;
    private Processor _processer = new Processor();
    private String[] _columnNames;
    private int _currentBestTime = 1;
    private static TableView instance = TableView.getInstance();

    @Before
    public void setUp() throws Exception {
        _data = new String[][]{{"a"}};
        _optimalProcessorList.add(_processer);
        _processer.setNode(new Node("a"), 1);
        _processer.set_optimalNodeListNode();
        instance.initialiseView(_optimalProcessorList, _currentBestTime);
        _columnNames = new String[]{"Times", "P1"};
    }

    @After
    public void tearDown() throws Exception {
        _optimalProcessorList.clear();
    }

    @Test
    public void getInstance() {
        assertEquals(TableView.getInstance(), instance);
    }

    @Test
    public void getColumnName() {
        assertEquals(_columnNames[0], instance.getColumnName(0));
        assertEquals(_columnNames[1], instance.getColumnName(1));
    }


    @Test
    public void getValueAt() {
        assertEquals(_data[0][0], instance.getValueAt(0,0));
    }
}