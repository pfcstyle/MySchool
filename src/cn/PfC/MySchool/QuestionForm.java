package cn.PfC.MySchool;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
public class QuestionForm extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private static String sqlStr;
	private PreparedStatement ps;
	
	private JLabel l1;
	
	private JButton b1;
	private JButton b2;
	private JButton b3;
	private JButton b4;
	
	private Choice choice;
	
	private JScrollPane sp;
	
	private JPanel UpJP;
	private JPanel DownJP;
	
	private JTable tb;
	
	private Vector shuxing,jilu;
	private Vector Id_old=new Vector();
	private int row,column;//��¼�������к���

	public QuestionForm(){
		Layout();
		search();
		this.setBackground(Color.WHITE);
		this.setLocation(400, 200);
		this.setSize(600, 400);
		this.setTitle("������");
		this.setVisible(true);
	}
	public void Layout(){
		UpJP=new JPanel();
		UpJP.setLayout(null);
		UpJP.setPreferredSize(new Dimension(600,50));
		
		DownJP=new JPanel();
		DownJP.setLayout(null);
		DownJP.setPreferredSize(new Dimension(600,50));
		
		l1=new JLabel("�����Ѷ�ɸѡ");
		l1.setBounds(20, 10, 80, 30);
		choice=new Choice();
		choice.add("ȫ��");choice.add("1");choice.add("2");choice.add("3");
		choice.setBounds(110, 10, 80, 30);
		b1=new JButton("ɸѡ");
		b1.setBounds(200,10,60,30);
		UpJP.add(l1);UpJP.add(choice);UpJP.add(b1);
		
		b2=new JButton("�޸�/����");
		b3=new JButton("ˢ��");
		b4=new JButton("�ر�");
		b2.setBounds(300,10,120,30);
		b3.setBounds(440,10,60,30);
		b4.setBounds(520,10,60,30);
		DownJP.add(b2);DownJP.add(b3);DownJP.add(b4);
		
		shuxing=new Vector();
		shuxing.add("��Ŀ");shuxing.add("��");shuxing.add("���Ѷ�");
		shuxing.add("��Ŀ���");shuxing.add("A");shuxing.add("B");shuxing.add("C");shuxing.add("D");
		jilu=new Vector();
		tb=new JTable(jilu,shuxing);
		sp=new JScrollPane(tb);
		
		this.add(UpJP,"North");this.add(sp);this.add(DownJP,"South");
		
		b1.addActionListener(this);
		b1.setActionCommand("ɸѡ");
		b2.addActionListener(this);
		b2.setActionCommand("�޸�");
		b3.addActionListener(this);
		b3.setActionCommand("ˢ��");
		b4.addActionListener(this);
		b4.setActionCommand("�ر�");
	}
	private void search(){
		jilu.clear();
		DBCon dbcon=new DBCon();
		if(choice.getSelectedIndex()==0)
			sqlStr=new String("select *from Question");
		else
		sqlStr=new String("select *from Question where difficulty='"+choice.getSelectedItem()+"'");
		dbcon.executeQuery(sqlStr);
		try {
			while(dbcon.rs.next()){
				Vector hang=new Vector();
				hang.add(dbcon.rs.getString(3));
				hang.add(dbcon.rs.getString(4));
				hang.add(dbcon.rs.getString(5));
				hang.add(dbcon.rs.getString(2));
				hang.add(dbcon.rs.getString(6));
				hang.add(dbcon.rs.getString(7));
				hang.add(dbcon.rs.getString(8));
				hang.add(dbcon.rs.getString(9));
				jilu.add(hang);
				Id_old.add(dbcon.rs.getString(1));
			}
			Vector hang=new Vector();
			hang.add("");
			hang.add("");
			hang.add("");
			hang.add("");
			hang.add("");
			hang.add("");
			hang.add("");
			hang.add("");
			jilu.add(hang);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dbcon.close();
}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("ɸѡ")){
			search();
			tb.revalidate();
		}
		else if(e.getActionCommand().equals("�޸�")){
			b3.requestFocus();
			update();
		}
		else if(e.getActionCommand().equals("ˢ��")){
			search();
			tb.revalidate();
		}
		else if(e.getActionCommand().equals("�ر�")){
			this.dispose();
		}
	}
	private void update(){//������������ȫ����д�����ݿ���
		DBCon dbcon=new DBCon();
		try{
			if(tb.getRowCount()>1){
				sqlStr="update Question set Question=?,Answer=?,Difficulty=?,MajorId=?,A=?,B=?,C=?,D=? where QuestionId=? ";
			}
			else{
				sqlStr="insert into Question (Question,Answer,Difficulty,MajorId,A,B,C,D) values (?,?,?,?,?,?,?,?)";
			}
			ps=dbcon.dbCon.prepareStatement(sqlStr);
			
			row=0;
			column=0;
			while(row<tb.getRowCount()&&!tb.getValueAt(row, 0).toString().equals("")){
				//System.out.println(((Vector)jilu.get(row)).get(0).toString());
				if(column==7){
					ps.setString(column+1, tb.getValueAt(row, column).toString());
					if(row<tb.getRowCount()-1)
					ps.setString(column+2, Id_old.get(row).toString());
					//System.out.println(column+":"+tb.getValueAt(row, column).toString());
					ps.executeUpdate();
					column=0;
					row++;
					if(row==tb.getRowCount()-1){
						sqlStr="insert into Question (Question,Answer,Difficulty,MajorId,A,B,C,D?) values (?,?,?,?,?,?,?,?)";
						ps=dbcon.dbCon.prepareStatement(sqlStr);
					}
				}
				else {
						if(column==2||column==3){
							ps.setInt(column+1, Integer.parseInt(tb.getValueAt(row, column).toString()));
						}else
						ps.setString(column+1, tb.getValueAt(row, column).toString());
						//System.out.println(column+":"+tb.getValueAt(row, column).toString());
						column++;
				}
			}
			search();
			JOptionPane.showMessageDialog(this, "����ɹ�");
		}catch(SQLException e1){
			e1.printStackTrace();
		}finally{
			dbcon.close();
		}
	}
}
