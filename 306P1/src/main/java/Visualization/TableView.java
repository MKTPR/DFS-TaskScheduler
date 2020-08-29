package Visualization;

import Algorithm.Processor;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * this class manages the table in the visualization screen
 */
public class TableView extends AbstractTableModel {

    private String[][] _data;
    private ArrayList<Processor> _optimalProcessorList;
    private String[] _columnNames;
    private int _currentBestTime;
    private static TableView instance = null;

    public TableView(){
    }

    /**
     * Initialises all the values for the table and sets column names
     * and creates a big enough array to fit all the data into
     * @param processorList
     * @param currentBestTime
     */
    public void initialiseView(ArrayList<Processor> processorList, int currentBestTime) {
        instance = this;
        _optimalProcessorList = processorList;
        _currentBestTime = currentBestTime;
        _columnNames = initColNames();
        initData();
    }

    /**
     * Used to get the current instance of the table view class
     * creates new instance if one does not already exist
     * @return
     */
    public static TableView getInstance(){
        if(instance == null) {
            instance = new TableView();
        }
        return instance;
    }

    /**
     * creates the headings for each column in the table
     * first column is always times and the rest is processers names
     * @return
     */
    private String[] initColNames() {
        String[] colNames = new String[_optimalProcessorList.size()+1];
        colNames[0] = "Times: ";
        for (int i = 1; i <= _optimalProcessorList.size(); i++) {
            colNames[i] = "P" + i;
        }
        return colNames;
    }

    /**
     * initialises the data in the table to the schedule found by the
     * greedy algorithm.
     * Fires an event that updates the table view on the main page with the correct data
     */
    private void initData() {
        _data = new String[_currentBestTime][_optimalProcessorList.size() + 1];
        //initialises the times column
        for (int i = 0; i < _currentBestTime; i++) {
            _data[i][0] = (i) + "";
            for (int j = 1; j < _optimalProcessorList.size(); j++) {
                _data[i][j] = "";
            }
        }
        fireTableDataChanged(); // fires the event
        //updates the data in the table
        for (int i = 0; i < _currentBestTime; i++) {
            for (int j = 1; j <= _optimalProcessorList.size(); j++) {
               if((_optimalProcessorList.get(j-1).get_optimalNodeList().size() > i)) {
                   if ((_optimalProcessorList.get(j-1).get_optimalNodeList().get(i)!=null)){
                       _data[i][j] = _optimalProcessorList.get(j - 1).get_optimalNodeList().get(i).getName();
                   }
               }
            }
        }


    }

    /**
     * Called whenever a better schedule is found and updates the data in the
     * table to the new schedule that was found.
     * Fires event to update the visual table
     * @param processorList
     * @param currentBestTime
     */
    public void changeData(ArrayList<Processor> processorList, int currentBestTime) {
        _optimalProcessorList = processorList;
        _currentBestTime = currentBestTime;
        initData();
        fireTableDataChanged();
    }


    /**
     * used to change data at a particular section.
     * inherited method
     * @param value
     * @param row
     * @param col
     */
    public void setValueAt(Object value, int row, int col) {
        _data[row][col] = (String) value;
        fireTableCellUpdated(row, col);
    }


    @Override
    public String getColumnName(int i){
        return _columnNames[i];
    }

    @Override
    public int getRowCount() {
        return _data.length;
    }

    @Override
    public int getColumnCount() {
        return _columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return _data[rowIndex][columnIndex];
    }
}