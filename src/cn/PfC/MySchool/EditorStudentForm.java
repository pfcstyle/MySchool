package cn.PfC.MySchool;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Parser;
public class EditorStudentForm extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int Height=20;//����ؼ��߶�
	private static final int LWidth=60;//�����ǩ���
	private static final int TWidth=120;//�����Ŀ��
	
	private PreparedStatement ps;
	
	private JButton b1;
	private JButton b2;
	
	private JLabel l1;
	private JLabel l2;
	private JLabel l3;
	private JLabel l4;
	private JLabel l5;
	private JLabel l6;
	private JLabel l7;
	private JLabel l8;
	
	private JTextField name;
	private JTextField Sno;
	private JTextField Pno;
	private JTextField Email;
	
	private CheckboxGroup state;
	private CheckboxGroup sex;
	private Checkbox active,unactive,man,woman;
	
	private Choice grade,classroom;
	
	private JScrollPane MidJP;
	
	private String sqlStr;
	private static String username_old;//��¼���޸��ߵ�ԭ�û���

	public EditorStudentForm(){
		try {
			Layout();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setBackground(Color.WHITE);
		this.setLocation(100, 100);
		this.setSize(300,450);
		this.setTitle("�޸�ѧԱ��Ϣ");
		this.setVisible(true);
	}
	public void Layout() throws SQLException{
		sqlStr=new String("select *from Student where LoginId='"+username_old+"'");
		DBCon dbcon=new DBCon();//�����ݿ�����
		dbcon.executeQuery(sqlStr);
		
		dbcon.rs.next();
		
		this.setLayout(new BorderLayout());
		MidJP=new JScrollPane();
		MidJP.setLayout(null);	
		
		l1=new JLabel("��        ��:");
		name=new JTextField(dbcon.rs.getString(9));
		l1.setBounds(40, 40, LWidth, Height);
		name.setBounds(50+LWidth, 40, TWidth,Height);
		MidJP.add(name);MidJP.add(l1);
		
		l2=new JLabel("ѧ        ��:");
		Sno=new JTextField(dbcon.rs.getString(7));
		l2.setBounds(40, 80, LWidth, Height);
		Sno.setBounds(50+LWidth, 80, TWidth, Height);
		MidJP.add(l2);MidJP.add(Sno);
		
		l3=new JLabel("��        ��:");
		Pno=new JTextField(dbcon.rs.getString(10));
		l3.setBounds(40, 120, LWidth, Height);
		Pno.setBounds(50+LWidth, 120, TWidth, Height);
		MidJP.add(l3);MidJP.add(Pno);
		
		l4=new JLabel("�����ʼ�:");
		Email=new JTextField(dbcon.rs.getString(11));
		l4.setBounds(40, 160, LWidth, Height);
		Email.setBounds(50+LWidth, 160, TWidth, Height);
		MidJP.add(l4);MidJP.add(Email);
		
		l5=new JLabel("��       ��:");
		l5.setBounds(40,200,LWidth,Height);
		sex=new CheckboxGroup();
		if(dbcon.rs.getString(8).equals("m")){
			man=new Checkbox("m",sex,true);
			woman=new Checkbox("f",sex,false);
		}
		else{
			man=new Checkbox("m",sex,false);
			woman=new Checkbox("f",sex,true);
		}
		man.setBounds(50+LWidth, 200, TWidth/2, Height);
		woman.setBounds(50+LWidth+TWidth/2, 200, TWidth/2, Height);
		MidJP.add(l5);MidJP.add(man);MidJP.add(woman);
		
		l6=new JLabel("״       ̬:");
		l6.setBounds(40,240,LWidth,Height);
		state=new CheckboxGroup();
		if(dbcon.rs.getInt(2)==15){
			active=new Checkbox("�",state,true);
			unactive=new Checkbox("�ǻ",state,false);
		}
		else{
			active=new Checkbox("�",state,false);
			unactive=new Checkbox("�ǻ",state,true);
		}
		active.setBounds(50+LWidth, 240, TWidth/2, Height);
		unactive.setBounds(50+LWidth+TWidth/2, 240, TWidth/2, Height);
		MidJP.add(l6);MidJP.add(active);MidJP.add(unactive);
		
		l7=new JLabel("��       ��:");
		l7.setBounds(40,280,LWidth,Height);
		grade=new Choice();
		grade.add("1");grade.add("2");grade.add("3");grade.add("4");grade.add("5");
		grade.add("6");grade.add("7");grade.add("8");grade.add("9");grade.add("10");
		grade.add("11");
		grade.setBounds(50+LWidth, 280, TWidth, Height);
		MidJP.add(l7);MidJP.add(grade);
		
		l8=new JLabel("��       ��:");
		l8.setBounds(40,320,LWidth,Height);
		classroom=new Choice();
		classroom.add("���");classroom.add("�а�");classroom.add("С��");
		classroom.setBounds(50+LWidth,320,TWidth,Height);
		MidJP.add(l8);MidJP.add(classroom);
		
		b1=new JButton("����");
		b2=new JButton("ȡ��");
		b1.setBounds(140, 360, 60, 20);
		b2.setBounds(210,360,60,20);
		MidJP.add(b1);MidJP.add(b2);
		
		this.add(MidJP);
		b1.addActionListener(this);
		b1.setActionCommand("����");
		b2.addActionListener(this);
		b2.setActionCommand("ȡ��"); 
		dbcon.close();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("����")){
			sqlStr="update student set StudentName=?,StudentNO=?,Phone=?,Email=?,Sex=?,UserStateId=?,GradeId=?,Class=? where LoginId=?";
			DBCon dbcon=new DBCon();
			try {
				int stateN;//��¼״̬
				ps=dbcon.dbCon.prepareStatement(sqlStr);
				ps.setString(1, name.getText());
				ps.setString(2, Sno.getText());
				ps.setString(3, Pno.getText());
				ps.setString(4, Email.getText());
				ps.setString(5, sex.getSelectedCheckbox().getLabel());
				if(state.getSelectedCheckbox().getLabel()=="�"){
					stateN=15;
				}
				else stateN=16;
				ps.setInt(6, stateN);
				ps.setInt(7, Integer.parseInt(grade.getSelectedItem()));
				ps.setString(8, classroom.getSelectedItem());
				ps.setString(9, username_old);
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "����ɹ�");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally{
				dbcon.close();
			}
			this.dispose();
		}
		else if(e.getActionCommand().equals("ȡ��")){
			this.dispose();
		}
	}
	public static String getUsername_old() {
		return username_old;
	}
	public static void setUsername_old(String username_old) {
		EditorStudentForm.username_old = username_old;
	}
}
