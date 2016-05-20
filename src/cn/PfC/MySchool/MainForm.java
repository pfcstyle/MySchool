package cn.PfC.MySchool;

import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MainForm extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private Menu m1;
	private Menu m11;
	private Menu m13;
	private Menu m2;
	private Menu m3;
	private Menu m4;
	private Menu m5;
	private MenuBar caidan;
	private JLabel l4;
	private ImageIcon image;
	public MainForm(){
		Layout();
		this.setBackground(Color.WHITE);
		this.setLocation(0, 0);
		this.setSize(800, 600);
		this.setTitle("管理界面");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void Layout(){
		m1=new Menu("用户管理");
		m11=new Menu("新增用户");
		m11.add("新增学员用户");
		m11.add("新增教员用户");
		m13=new Menu("用户信息列表");
		m13.add("学员信息列表");
		m13.add("教员信息列表");
		m1.add(m11);
		m1.add("查询及修改学员");
		m1.add(m13);
		m1.addSeparator();
		m1.add("退出");
		m1.addActionListener(this);
		m11.addActionListener(this);
		m13.addActionListener(this);
		
		image=new ImageIcon("nwdaes10.jpg");
		l4=new JLabel(image);
		l4.setBounds(0, 0, 800, 600);
		
		m2=new Menu("题库管理");
		m2.add("题库管理");
		m2.addActionListener(this);
		
		m3=new Menu("考试管理");
		m3.add("考试管理");
		m3.addActionListener(this);
		
		m4 =new Menu("窗口");
		m4.addActionListener(this);
		
		m5=new Menu("帮助");
		m5.add("关于考试管理系统");
		m5.addActionListener(this);
		
		caidan=new MenuBar();
		caidan.add(m1); caidan.add(m2); caidan.add(m3); caidan.add(m4); caidan.add(m5);
		this.setMenuBar(caidan);
		this.add(l4);
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("查询及修改学员")){
			//System.out.println("Yes!");
			new SearchStudentForm();
		}
		else if(e.getActionCommand().equals("新增学员用户")){
			if(LoginForm.loginType==0)
				new AddStudentForm();
			else
				JOptionPane.showMessageDialog(this, "对不起，只有管理员有此权限");
		}
		else if(e.getActionCommand().equals("新增教员用户")){
			if(LoginForm.loginType==0)
				new AddTeacherForm();
			else
				JOptionPane.showMessageDialog(this, "对不起，只有管理员有此权限");
		}
		else if(e.getActionCommand().equals("学员信息列表")){
			new StudentListForm();
		}
		else if(e.getActionCommand().equals("教员信息列表")){
			//System.out.println("jiaoyuan");
			if(LoginForm.loginType!=1)
				new TeacherListForm();
			else
				JOptionPane.showMessageDialog(this, "对不起，只有管理员和教员有此权限");
		}
		else if(e.getActionCommand().equals("题库管理")){
			if(LoginForm.loginType!=1)
				new QuestionForm();
			else
				JOptionPane.showMessageDialog(this, "对不起，只有管理员和教员有此权限");
		}
		else if(e.getActionCommand().equals("考试管理")){
			new TestForm();
		}
	}

}
