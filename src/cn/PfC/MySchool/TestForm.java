package cn.PfC.MySchool;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.sql.*;

public class TestForm extends JFrame implements ActionListener{
	private JLabel l1;
	private JLabel l2;
	
	private CheckboxGroup cg;
	private Checkbox A;
	private Checkbox B;
	private Checkbox C;
	private Checkbox D;
	
	private JButton b1;
	private JButton b2;
	
	private int tNo=1;//记录题号
	private int tScores;//记录分数
	
	private String sqlStr;
	
	private DBCon dbcon;
	public TestForm(){
		try {
			Layout();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setBackground(Color.WHITE);
		this.setLocation(400, 200);
		this.setSize(300, 450);
		this.setTitle("考试管理");
		this.setVisible(true);
	}
	
	public void Layout() throws SQLException{
		this.setLayout(null);
		dbcon=new DBCon();
		sqlStr=new String("select *from Question");
		dbcon.executeQuery(sqlStr);
		dbcon.rs.next();
		
		l1=new JLabel("第"+tNo+"题");
		l1.setBounds(100, 40, 100 ,40);
		this.add(l1);
		
		l2=new JLabel(dbcon.rs.getString(3).toString());
		l2.setBounds(30,100,260,30);
		this.add(l2);
		
		cg=new CheckboxGroup();
		A=new Checkbox("A: "+dbcon.rs.getString(6).toString(),cg,true);
		B=new Checkbox("B: "+dbcon.rs.getString(7).toString(),cg,false);
		C=new Checkbox("C: "+dbcon.rs.getString(8).toString(),cg,false);
		D=new Checkbox("D: "+dbcon.rs.getString(9).toString(),cg,false);
		A.setBounds(40, 150, 250, 30);
		A.setName("A");
		B.setBounds(40, 200, 250, 30);
		B.setName("B");
		C.setBounds(40, 250, 250, 30);
		C.setName("C");
		D.setBounds(40, 300, 250, 30);
		D.setName("D");
		this.add(A);this.add(B);this.add(C);this.add(D);
		
		b1=new JButton("下一题");
		b2=new JButton("退出");
		b1.setBounds(100, 335, 100,30);
		b2.setBounds(220, 335, 60,30);
		this.add(b1);this.add(b2);
		
		b1.addActionListener(this);
		b2.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("下一题")){
			tNo++;
			try {
				if(cg.getSelectedCheckbox().getName().equals(dbcon.rs.getString(4).toString())){
					tScores+=10;
				}
				if(!dbcon.rs.next()){
					JOptionPane.showMessageDialog(this, "已到最后一题，考试结束，本次考试得分:"+tScores);
					this.dispose();
					dbcon.close();
				}else{
					l1.setText("第"+tNo+"题");
					l2.setText(dbcon.rs.getString(3).toString());
					A.setLabel("A: "+dbcon.rs.getString(6).toString());
					B.setLabel("B: "+dbcon.rs.getString(7).toString());
					C.setLabel("C: "+dbcon.rs.getString(8).toString());
					D.setLabel("D: "+dbcon.rs.getString(9).toString());
				}
				
			}catch(SQLException e1){
				e1.printStackTrace();
			}
			this.repaint();
		}
		else if(e.getActionCommand().equals("退出")){
			this.dispose();
		}
	}
}
