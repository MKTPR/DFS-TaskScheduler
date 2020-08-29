package Visualization;


import Algorithm.Processor;
import Algorithm.TestMain;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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


    private Timer _timer;
    private int _numberOfthreads;
    private JLabel _outputFile;

    private JButton _timerButton;
    private JButton _currentBestTime;
    private int _upperBound;
    JLabel _scheduleTableLabel;




    public MainPage(ArrayList<Processor> optimalProcessorList, int upperBound, int noOfProcessors, int isParallel, String input, String output) {

        try {
            UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1400, 900);

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
        title.setFont(new Font("Geeza Pro", Font.BOLD, 25));
        JPanel _titlePanel = new JPanel();
        _titlePanel.add(title, BorderLayout.EAST);

        JButton _abortButton = new JButton("ABORT");
        JPanel _abortPanel = new JPanel();


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

        _abortPanel.add(_abortButton, BorderLayout.CENTER);


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


        _topPanel.add(_abortPanel, "left");
        _topPanel.add(_titlePanel, "span 4, right");
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

        _scheduleTableLabel = new JLabel("Current Optimal Schedule");
        _scheduleTableLabel.setFont(new Font("Geeza Pro", Font.BOLD, 13));
        JPanel _scheduleTablePanel = new JPanel();
        _scheduleTablePanel.add(_scheduleTableLabel);

        JPanel _infoPanel = new JPanel();
        _infoPanel.setLayout(new MigLayout("fill"));
        _currentBestTime = new JButton("Current Best Time: "+ upperBound);
        JButton _numOfProcessors = new JButton("Number of Processors: "+ noOfProcessors);

        _infoPanel.add(_currentBestTime, BorderLayout.WEST);
        _infoPanel.add(_numOfProcessors, BorderLayout.EAST);

        _leftPanel.add(_scheduleTablePanel, "width 100%, height 8%, wrap");
        _leftPanel.add(_scrollPane, "width 100%, height 92%, wrap");
        _leftPanel.add(_infoPanel, "width 100%");


        /**
         * Right Panel Components
         * - Label of Input file name + output file
         * - Graph of the input nodes
         */
        CreateGraph graphMaker = new CreateGraph(TestMain.getNodesList(), TestMain.getEdgesList());
        JPanel visualGraph = graphMaker.produceJPanel();
        visualGraph.setSize(500,350);

        JLabel _taskGraphLabel = new JLabel("Task Digraph");
        JPanel _graphLabelPanel = new JPanel();
        _graphLabelPanel.add(_taskGraphLabel);
        _taskGraphLabel.setFont(new Font("Geeza Pro", Font.BOLD, 13));
        _rightPanel.add(_graphLabelPanel, "width 100%, height 8%, wrap");

        _rightPanel.add(visualGraph, "width 100%, height 92%");


        /**
         * Bottom Panel Components
         * - Sequential/Parallel Show
         * - How many threads
         * - Runtime
         */
        JPanel tempPanel = new JPanel();
       tempPanel.setLayout(new GridLayout(1, 1, 4, 20));
       tempPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

       JPanel outerTempPanel = new JPanel();
       outerTempPanel.setLayout(new GridLayout(1, 1, 20, 20));

        Instant start = Instant.now();




        _timerButton = new JButton();
        _timerButton.setText("00:00");
        _timerButton.setFont(new Font("Geeza Pro", Font.BOLD, 20));


       _timer = new Timer(0, new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               Duration now = Duration.between(start,Instant.now());
               _timerButton.setText("Run time: " + DateTimeFormatter.ofPattern("mm:ss").format(LocalTime.NOON.plus(now)) );
           }
       });
        _timer.start();

        //_timerLabel.setText("Run time: " + Duration.between(start,Instant.now()).getSeconds()/60+":"+
                //Duration.between(start,Instant.now()).getSeconds());

        _sequentialLabel = new JLabel("SEQUENTIAL",JLabel.CENTER);
        _sequentialLabel.setFont(new Font("Geeza Pro", Font.BOLD, 20));
        _parallelLabel = new JLabel("PARALLEL",JLabel.CENTER);
        _parallelLabel.setFont(new Font("Geeza Pro", Font.BOLD, 20));
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
        bottomLeftPanel.setLayout(new MigLayout("fill"));
        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setLayout(new MigLayout("fill"));



        _noOfThreads = new JLabel("Number of Threads: " + Integer.toString(_numberOfthreads));
        _noOfThreads.setFont(new Font("Geeza Pro", Font.BOLD, 20));



        bottomLeftPanel.add(tempPanel);
        bottomLeftPanel.add(_noOfThreads, "right");
        bottomRightPanel.add(_timerButton, "right");


        _bottomPanel.add(outerTempPanel);

        _bottomPanel.add(bottomLeftPanel, "width 50%, height 100%");
        _bottomPanel.add(bottomRightPanel, "width 50%, height 100%");



        setLocationRelativeTo(null);
        setVisible(true);

    }
    private JPanel setUpPage() {
        JPanel _mainPanel = new JPanel();
        _mainPanel.setLayout(new MigLayout("fill"));
        _topPanel = new JPanel();

        _leftPanel = new JPanel();

        _rightPanel = new JPanel();

        _bottomPanel = new JPanel();

        _topPanel.setLayout(new MigLayout("fill"));
        _leftPanel.setLayout(new MigLayout("fill"));
        _rightPanel.setLayout(new MigLayout("fill"));
        _bottomPanel.setLayout(new MigLayout("fill"));

        _mainPanel.add(_topPanel, "span, center, width 100%, height 10%, wrap");
        _mainPanel.add(_leftPanel, "width 50%,height 80%");
        _mainPanel.add(_rightPanel, "width 50%, height 80%, wrap");
        _mainPanel.add(_bottomPanel, "span, center, width 100%, height 10%");
        return _mainPanel;
    }

    public void stopVisualisation(){

        _timer.stop();
        ImageIcon _working = new ImageIcon("greenTick.png");
        _outputFile.setIcon(_working);
        _timerButton.setText(_timerButton.getText() + " (FINISHED)");
        _currentBestTime.setText("Best Time: "+ _upperBound);
        _scheduleTableLabel.setText("Optimal Schedule");

    }
    public void updateBestTime(int bestTime){
        _currentBestTime.setText("Current Best Time: "+ bestTime);
        _upperBound = bestTime;
    }
}

