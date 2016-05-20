package cn.PfC.MySchool;

import javax.swing.table.DefaultTableModel;

public class MyModel extends DefaultTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean isCellEditable(int row, int col){
		return false;
	}
	public MyModel(){};
}
