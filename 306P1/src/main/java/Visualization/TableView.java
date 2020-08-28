package Visualization;

import Algorithm.Processor;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TableView extends AbstractTableModel {

    private String[][] _data;
    private ArrayList<Processor> _optimalProcessorList;
    private String[] _columnNames;
    private int _currentBestTime;
    private static TableView instance = null;

    public TableView(){
    }

    public void initialiseView(ArrayList<Processor> processorList, int currentBestTime) {
        instance = this;
        _optimalProcessorList = processorList;
        _currentBestTime = currentBestTime;
        _columnNames = initColNames();
        initData();
    }

    public static TableView getInstance(){
        if(instance == null) {
            instance = new TableView();
        }
        return instance;
    }

    private String[] initColNames() {
        String[] colNames = new String[_optimalProcessorList.size()+1];
        colNames[0] = "Times: ";
        for (int i = 1; i <= _optimalProcessorList.size(); i++) {
            colNames[i] = "P" + i;
        }
        return colNames;
    }

    private void initData() {
        _data = new String[_currentBestTime][_optimalProcessorList.size() + 1];

        for (int i = 0; i < _currentBestTime; i++) {
            _data[i][0] = (i) + "";
            for (int j = 1; j < _optimalProcessorList.size(); j++) {
                _data[i][j] = "";
            }
        }
        fireTableDataChanged();
        for (int i = 0; i < _currentBestTime; i++) {
            for (int j = 1; j <= _optimalProcessorList.size(); j++) {
                _data[i][j] = _optimalProcessorList.get(j-1).get_optimalNodeList().get(i).getName();
            }
        }


    }

    public void changeData(ArrayList<Processor> processorList, int currentBestTime) {
        _optimalProcessorList = processorList;
        _currentBestTime = currentBestTime;
        initData();
        fireTableDataChanged();
    }


    public void setValueAt(Object value, int row, int col) {
        _data[row][col] = (String) value;
        fireTableCellUpdated(row, col);
    }


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