package Visualization;


import Algorithm.Processor;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class MainPage extends JFrame{

    private JPanel _mainPanel;
    private JPanel _topPanel;
    private JPanel _leftPanel;
    private JPanel _rightPanel;
    private JPanel _bottomPanel;



    public MainPage(ArrayList<Processor> optimalProcessorList, int upperBound) {

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
        ImageIcon image = new ImageIcon("line_graph.png");
        Image largeLogo = image.getImage();
        Image smallLogo = largeLogo.getScaledInstance(550, 350, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newLogo = new ImageIcon(smallLogo);
        JLabel graph = new JLabel("", newLogo, JLabel.LEFT);
        _rightPanel.add(graph);

        //JLabel randomText = new JLabel();
        //randomText.setText("Some random text here");

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

