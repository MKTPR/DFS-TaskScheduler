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
    private Timer _timer;
    private int _numberOfthreads;
    private JLabel _outputFile;



    public MainPage(ArrayList<Processor> optimalProcessorList, int upperBound, int noOfProcessors, int isParallel, String input, String output) {

        try {
            UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 700);

        _mainPanel = setUpPage();
        _mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(_mainPanel);

        JPanel statusPanel = new JPanel(new BorderLayout());

        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        /**
         * Top Panel Components:
         * - Title ("Task Scheduler"
         * - AbortButton
         * - Progress Bar
         * - Input & Output File name
         */
        JLabel title = new JLabel("Task Scheduler");
        title.setFont(new Font("Geeza Pro", Font.BOLD, 30));

        JButton _abortButton = new JButton("ABORT");
        JPanel _abortPanel = new JPanel();
        _abortPanel.add(_abortButton, BorderLayout.CENTER);
        _abortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmationBox = JOptionPane.YES_NO_OPTION;
                confirmationBox = JOptionPane.showConfirmDialog (null, "Are you sure you want to stop the"
                        + " search? \nThis will lose all progress and exit the interface.","Warning!",confirmationBox);
                if(confirmationBox == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });

        JLabel _inputFile = new JLabel("Input: " + input);
        _inputFile.setFont(new Font("Geeza Pro", Font.PLAIN, 16));
        _outputFile = new JLabel("Output: " + output);
        _outputFile.setFont(new Font("Geeza Pro", Font.PLAIN, 16));
        //From https://www.animatedimages.org/img-animated-office-image-0061-43485.htm
        ImageIcon _working = new ImageIcon("working2.gif");
        _outputFile.setIcon(_working);

        JPanel _progressPanel = new JPanel();
        _progressPanel.setLayout(new MigLayout());

        _progressPanel.add(_inputFile);
        _progressPanel.add(_outputFile);

        _topPanel.add(title,"center, span");
        _topPanel.add(_abortPanel);
        _topPanel.add(_progressPanel, "right");



        /**
         * Left Panel Components
         * - Scroll Pane
         *      - TableView - Schedules
         *
         */
        TableView _scheduledTable = new TableView();
        _scheduledTable.initialiseView(optimalProcessorList, upperBound);
        JTable _JTable= new JTable(_scheduledTable);
        JScrollPane _scrollPane= new JScrollPane(_JTable);

        _leftPanel.add(_scrollPane);


        /**
         * Right Panel Components
         * - Label of Input file name + output file
         * - Graph of the input nodes
         */
        CreateGraph graphMaker = new CreateGraph(TestMain.getNodesList(), TestMain.getEdgesList());
        JPanel visualGraph = graphMaker.produceJPanel();
        visualGraph.setSize(500,350);
        _rightPanel.add(visualGraph, "width 100%, height 100%");

       JPanel tempPanel = new JPanel();
       tempPanel.setLayout(new GridLayout(1, 1, 4, 20));
       tempPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

       JPanel outerTempPanel = new JPanel();
       outerTempPanel.setLayout(new GridLayout(1, 1, 20, 20));

        Instant start = Instant.now();
        _timerLabel = new JLabel();
        _timerLabel.setText("00:00");

       _timer = new Timer(0, new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               Duration now = Duration.between(start,Instant.now());
               _timerLabel.setText("Run time: " + DateTimeFormatter.ofPattern("mm:ss").format(LocalTime.NOON.plus(now)) );
           }
       });
        _timer.start();

        //_timerLabel.setText("Run time: " + Duration.between(start,Instant.now()).getSeconds()/60+":"+
                //Duration.between(start,Instant.now()).getSeconds());

        _sequentialLabel = new JLabel("SEQUENTIAL",JLabel.CENTER);
        _parallelLabel = new JLabel("PARALLEL",JLabel.CENTER);
        if (isParallel > 1){
            _parallelLabel.setOpaque(true);
            _parallelLabel.setBackground(Color.GRAY);
            _numberOfthreads = isParallel;
        } else {
            _sequentialLabel.setOpaque(true);
            _sequentialLabel.setBackground(Color.GRAY);
            _numberOfthreads = 1;
        }

        tempPanel.add(_sequentialLabel);
        tempPanel.add(_parallelLabel);
        JPanel bottomLeftPanel = new JPanel();
        JPanel bottomRightPanel = new JPanel();


        _noOfThreads = new JLabel("Number of Threads: " + Integer.toString(_numberOfthreads));


        bottomLeftPanel.add(tempPanel);
        bottomLeftPanel.add(_noOfThreads);
        bottomRightPanel.add(_timerLabel);

        outerTempPanel.add(bottomLeftPanel);
        outerTempPanel.add(bottomRightPanel);

        _bottomPanel.add(outerTempPanel);


        setLocationRelativeTo(null);
        setVisible(true);

    }
    private JPanel setUpPage() {
        JPanel _mainPanel = new JPanel();
        _mainPanel.setLayout(new MigLayout("fill"));
        _topPanel = new JPanel();
        _topPanel.setLayout(new GridLayout(1, 2, 20, 20));

        _leftPanel = new JPanel();
        _leftPanel.setLayout(new BorderLayout());

        _rightPanel = new JPanel();
        _rightPanel.setLayout(new GridLayout(1,1,20,20));

        _bottomPanel = new JPanel();
        _bottomPanel.setLayout(new GridLayout(2, 1, 20, 20));

        _topPanel.setLayout(new MigLayout("fill"));
        _rightPanel.setLayout(new MigLayout("fill"));

        _mainPanel.add(_topPanel, "span, center, width 100%, height 10%, wrap");
        _mainPanel.add(_leftPanel, "width 50%,height 67%");
        _mainPanel.add(_rightPanel, "width 50%, height 67%, wrap");
        _mainPanel.add(_bottomPanel, "span, center, width 10%, height 13%");
        return _mainPanel;
    }

    public void stopVisualisation(){

        _timer.stop();
        ImageIcon _working = new ImageIcon("greenTick.png");
        _outputFile.setIcon(_working);
        _timerLabel.setText(_timerLabel.getText() + " (FINISHED)");

    }
}

