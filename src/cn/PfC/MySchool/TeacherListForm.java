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
public class TeacherListForm extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private String sqlStr;
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
	private int row,column;//记录表格里的行和列
	private Vector Id_old=new Vector();

	public TeacherListForm(){
		Layout();
		search();
		this.setBackground(Color.WHITE);
		this.setLocation(400, 200);
		this.setSize(600, 400);
		this.setTitle("教员信息列表");
		this.setVisible(true);
	}
	public void Layout(){
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
		
		shuxing=new Vector();
		shuxing.add("用户名");shuxing.add("密码");shuxing.add("姓名");
		shuxing.add("性别");shuxing.add("生日");
		jilu=new Vector();
		tb=new JTable(jilu,shuxing);
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
	private void search(){
		jilu.clear();
		Id_old.clear();
		DBCon dbcon=new DBCon();
		if(choice.getSelectedIndex()==0)
			sqlStr=new String("select *from Teacher");
		else
		sqlStr=new String("select *from Teacher where Sex='"+choice.getSelectedItem()+"'");
		dbcon.executeQuery(sqlStr);
		try {
			while(dbcon.rs.next()){
				Vector hang=new Vector();
				hang.add(dbcon.rs.getString(3));
				hang.add(dbcon.rs.getString(4));
				hang.add(dbcon.rs.getString(7));
				hang.add(dbcon.rs.getString(5));
				hang.add(dbcon.rs.getString(6));
				jilu.add(hang);
				Id_old.add(dbcon.rs.getString(1));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dbcon.close();
		tb.revalidate();
}
private void update(){//将表格里的数据全部重写到数据库里
	DBCon dbcon=new DBCon();
	try{
		sqlStr="update teacher set LoginId=?,LoginPwd=?,TeacherName=?,Sex=?,Birthday=? where TeacherId=? ";
		ps=dbcon.dbCon.prepareStatement(sqlStr);
		row=0;
		column=0;
		while(row<tb.getRowCount()){
			if(column==4){
				ps.setString(column+1, tb.getValueAt(row, column).toString());
				ps.setString(6, Id_old.get(row).toString());
				ps.executeUpdate();
				column=0;
				row++;
			}
			else {
				ps.setString(column+1, tb.getValueAt(row, column).toString());
				//System.out.println(column);
				column++;
			}
		}
		JOptionPane.showMessageDialog(this, "修改成功");
	}catch(SQLException e1){
		e1.printStackTrace();
	}
	dbcon.close();
}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
if(e.getActionCommand().equals("筛选")){
			search();
		}
		else if(e.getActionCommand().equals("修改")){
			if(LoginForm.loginType==0){
				update();
			}
			else{
				JOptionPane.showMessageDialog(this, "对不起，只有管理员有此权限");
				search();
			}
		}

		else if(e.getActionCommand().equals("刷新")){
			search();
		}
		else if(e.getActionCommand().equals("关闭")){
			this.dispose();
		}
	}
	
}
