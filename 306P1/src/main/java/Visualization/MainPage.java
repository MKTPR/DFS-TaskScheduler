package Visualization;


import Algorithm.Edge;
import Algorithm.Node;
import Algorithm.Processor;
import Algorithm.TestMain;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.text.Format;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class MainPage extends JFrame{

    private JPanel _mainPanel;
    private JPanel _topPanel;
    private JPanel _leftPanel;
    private JPanel _rightPanel;
    private JPanel _bottomPanel;
    private JLabel _noOfThreads;
    private JLabel _parallelLabel;
    private JLabel _sequentialLabel;
    private JLabel _timerLabel;



    public MainPage(ArrayList<Processor> optimalProcessorList, int upperBound, int noOfProcessors, int isParallel) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 700);

        _mainPanel = setUpPage();
        _mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(_mainPanel);

        JPanel statusPanel = new JPanel(new BorderLayout());

        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));



        JLabel title = new JLabel("TaskScheduler", JLabel.CENTER);
        title.setFont(new Font("serif", Font.BOLD, 20));

        // First row of top panel
        _topPanel.add(title);


        TableView _scheduledTable = new TableView();
        _scheduledTable.initialiseView(optimalProcessorList, upperBound);
        JTable _JTable= new JTable(_scheduledTable);
        JScrollPane _scrollPane= new JScrollPane(_JTable);

        //Left Panel - Scroll panel
        _leftPanel.add(_scrollPane);


        //Right Panel - Add graph
        CreateGraph graphMaker = new CreateGraph(TestMain.getNodesList(), TestMain.getEdgesList());
        JPanel visualGraph = graphMaker.produceJPanel();
        _rightPanel.add(visualGraph);

       JPanel tempPanel = new JPanel();
       tempPanel.setLayout(new GridLayout(1, 1, 4, 20));
       tempPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

       JPanel outerTempPanel = new JPanel();
       outerTempPanel.setLayout(new GridLayout(1, 1, 20, 20));

        Instant start = Instant.now();
        _timerLabel = new JLabel();

       Timer timer = new Timer(1000, new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               Duration now = Duration.between(start,Instant.now());
               _timerLabel.setText("Run time: " + DateTimeFormatter.ofPattern("mm:ss").format(LocalTime.NOON.plus(now)) );
           }
       });
        timer.start();

        //_timerLabel.setText("Run time: " + Duration.between(start,Instant.now()).getSeconds()/60+":"+
                //Duration.between(start,Instant.now()).getSeconds());

        _sequentialLabel = new JLabel("SEQUENTIAL",JLabel.CENTER);
        _parallelLabel = new JLabel("PARALLEL",JLabel.CENTER);
        if (isParallel > 1){
            _parallelLabel.setOpaque(true);
            _parallelLabel.setBackground(Color.GRAY);
        } else {
            _sequentialLabel.setOpaque(true);
            _sequentialLabel.setBackground(Color.GRAY);
        }

        tempPanel.add(_sequentialLabel);
        tempPanel.add(_parallelLabel);
        JPanel bottomLeftPanel = new JPanel();
        JPanel bottomRightPanel = new JPanel();


        _noOfThreads = new JLabel("Number of Threads: " + Integer.toString(isParallel));


        bottomLeftPanel.add(tempPanel);
        bottomLeftPanel.add(_noOfThreads);
        bottomRightPanel.add(_timerLabel);

        outerTempPanel.add(bottomLeftPanel);
        outerTempPanel.add(bottomRightPanel);

        _bottomPanel.add(outerTempPanel,BorderLayout.CENTER);


        setLocationRelativeTo(null);
        setVisible(true);

    }
    private JPanel setUpPage() {
        JPanel _mainPanel = new JPanel();
        _mainPanel.setLayout(new MigLayout("fill"));
        _topPanel = new JPanel();
        _topPanel.setLayout(new GridLayout(1, 1, 20, 20));

        _leftPanel = new JPanel();
        _leftPanel.setLayout(new BorderLayout());

        _rightPanel = new JPanel();
        _rightPanel.setLayout(new BorderLayout());

        _bottomPanel = new JPanel();
        _bottomPanel.setLayout(new GridLayout(2, 1, 20, 20));

        _mainPanel.add(_topPanel, "span, center, width 100%, height 10%, wrap");
        _mainPanel.add(_leftPanel, "width 50%,height 67%");
        _mainPanel.add(_rightPanel, "width 50%, height 67%, wrap");
        _mainPanel.add(_bottomPanel, "span, center, width 10%, height 13%");
        return _mainPanel;
    }
}

