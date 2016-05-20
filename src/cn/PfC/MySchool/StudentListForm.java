package cn.PfC.MySchool;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentListForm extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JLabel l1;
	
	private JButton b1;
	private JButton b2;
	private JButton b3;
	private JButton b4;
	
	private Choice choice;
	
	private JScrollPane sp;
	
	private JPanel UpJP;
	private JPanel DownJP;
	
	DefaultTableModel dtm;
	private JTable tb;
	
	private Vector shuxing,jilu;
	
	private String sqlStr;
	private int row;

	public StudentListForm(){
		Layout();
		search();
		this.setBackground(Color.WHITE);
		this.setVisible(true);
		this.setLocation(400, 200);
		this.setSize(600, 400);
		this.setTitle("学员信息列表");
		this.setResizable(false);
	}
	public void Layout(){
		this.setLayout(new BorderLayout());
		
		UpJP=new JPanel();
		UpJP.setLayout(null);
		UpJP.setPreferredSize(new Dimension(600,50));
		
		DownJP=new JPanel();
		DownJP.setLayout(null);
		DownJP.setPreferredSize(new Dimension(600,50));
		
		l1=new JLabel("按性别筛选");
		l1.setBounds(20, 10, 80, 30);
		choice=new Choice();
		choice.add("全部");choice.add("m");choice.add("f");
		choice.setBounds(110, 10, 80, 30);
		b1=new JButton("筛选");
		b1.setBounds(200,10,60,30);
		UpJP.add(l1);UpJP.add(choice);UpJP.add(b1);
		
		b2=new JButton("修改");
		b3=new JButton("刷新");
		b4=new JButton("关闭");
		b2.setBounds(360,10,60,30);
		b3.setBounds(440,10,60,30);
		b4.setBounds(520,10,60,30);
		DownJP.add(b2);DownJP.add(b3);DownJP.add(b4);
		
		shuxing=new Vector<String>();
		shuxing.add("用户名");shuxing.add("姓名");shuxing.add("学号");
		shuxing.add("性别");shuxing.add("电话");
		jilu=new Vector();
		dtm=new DefaultTableModel(jilu,shuxing);
		tb=new JTable(dtm);
		sp=new JScrollPane(tb);
		
		this.add(UpJP,"North");this.add(sp);this.add(DownJP,"South");
		
		b1.addActionListener(this);
		b1.setActionCommand("筛选");
		b2.addActionListener(this);
		b2.setActionCommand("修改");
		b3.addActionListener(this);
		b3.setActionCommand("刷新");
		b4.addActionListener(this);
		b4.setActionCommand("关闭");
		
	
	}
	//查询所有学生信息
	private void search(){
			jilu.clear();
			DBCon dbcon=new DBCon();
			if(choice.getSelectedIndex()==0)
				sqlStr=new String("select *from student");
			else
			sqlStr=new String("select *from student where Sex='"+choice.getSelectedItem()+"'");
			dbcon.executeQuery(sqlStr);
			try {
				while(dbcon.rs.next()){
					Vector hang=new Vector();
					hang.add(dbcon.rs.getString(4));
					hang.add(dbcon.rs.getString(9));
					hang.add(dbcon.rs.getString(7));
					hang.add(dbcon.rs.getString(6));
					hang.add(dbcon.rs.getString(10));
					jilu.add(hang);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dbcon.close();
	}
	private boolean isSelected(){
		if(tb.getSelectedRow()==-1){
			JOptionPane.showConfirmDialog(null,"请选择要修改的行", "提示",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("筛选")){
			search();
			tb.revalidate();
		}
		else if(e.getActionCommand().equals("修改")){
			if(LoginForm.loginType!=1){
				if(isSelected()){
					row=tb.getSelectedRow();
					//System.out.println(row);
					EditorStudentForm.setUsername_old(tb.getValueAt(row, 0).toString());
					//System.out.println(username_old);
					new EditorStudentForm();
				}
			}
			else{
				JOptionPane.showMessageDialog(this, "对不起，只有管理员和教员有此权限");
			}
			tb.revalidate();
			
		}
		else if(e.getActionCommand().equals("刷新")){
			search();
			tb.revalidate();
		}
		else if(e.getActionCommand().equals("关闭")){
			this.dispose();
		}
	}

}
