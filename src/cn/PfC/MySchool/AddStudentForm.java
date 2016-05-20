package cn.PfC.MySchool;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
public class AddStudentForm extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int Height=20;//定义控件高度
	private static final int LWidth=60;//定义标签宽度
	private static final int TWidth=120;//定义框的宽度
	
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
	private JLabel l9;
	private JLabel l10;
	private JLabel l11;
	
	private JTextField username;
	private JPasswordField password;
	private JPasswordField passwordAgain;
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
	private PreparedStatement ps;
	
	String pwd;//记录注册用户的密码
	String pwdag;//记录注册用户的重复密码

	public AddStudentForm(){
		Layout();
		this.setBackground(Color.WHITE);
		this.setLocation(100, 100);
		this.setSize(300,580);
		this.setTitle("添加学员信息");
		this.setVisible(true);
	}
	public void Layout(){
		this.setLayout(new BorderLayout());
		MidJP=new JScrollPane();
		MidJP.setLayout(null);
		
		l1=new JLabel("用  户  名:");
		username=new JTextField(20);
		l1.setBounds(40, 40, LWidth, Height);
		username.setBounds(50+LWidth, 40, TWidth,Height);
		MidJP.add(username);MidJP.add(l1);
		
		l2=new JLabel("密        码:");
		password=new JPasswordField(20);
		l2.setBounds(40, 80, LWidth, Height);
		password.setBounds(50+LWidth, 80, TWidth, Height);
		MidJP.add(l2);MidJP.add(password);
		
		l3=new JLabel("确认密码:");
		passwordAgain=new JPasswordField(20);
		l3.setBounds(40, 120, LWidth, Height);
		passwordAgain.setBounds(50+LWidth, 120, TWidth, Height);
		MidJP.add(l3);MidJP.add(passwordAgain);
		
		l4=new JLabel("状         态:");
		l4.setBounds(40,160,LWidth,Height);
		state=new CheckboxGroup();
		active=new Checkbox("活动",state,true);
		unactive=new Checkbox("非活动",state,false);
		active.setBounds(50+LWidth, 160, TWidth/2, Height);
		unactive.setBounds(50+LWidth+TWidth/2, 160, TWidth/2, Height);
		MidJP.add(l4);MidJP.add(unactive);MidJP.add(active);
		
		l5=new JLabel("姓       名:");
		l5.setBounds(40,200,LWidth,Height);
		name=new JTextField(20);
		name.setBounds(50+LWidth, 200, TWidth, Height);
		MidJP.add(l5);MidJP.add(name);
		
		
		
		l6=new JLabel("学       号:");
		l6.setBounds(40,240,LWidth,Height);
		Sno=new JTextField(20);
		Sno.setBounds(50+LWidth, 240, TWidth, Height);
		MidJP.add(l6);MidJP.add(Sno);
		
		l7=new JLabel("电       话:");
		l7.setBounds(40,280,LWidth,Height);
		Pno=new JTextField(20);
		Pno.setBounds(50+LWidth, 280, TWidth, Height);
		MidJP.add(l7);MidJP.add(Pno);
		
		l8=new JLabel("电子邮件:");
		l8.setBounds(40,320,LWidth,Height);
		Email=new JTextField(40);
		Email.setBounds(50+LWidth, 320, TWidth, Height);
		MidJP.add(l8);MidJP.add(Email);
		
		l9=new JLabel("性         别:");
		l9.setBounds(40,360,LWidth,Height);
		sex=new CheckboxGroup();
		man=new Checkbox("F",sex,true);
		woman=new Checkbox("M",sex,false);
		man.setBounds(50+LWidth, 360, TWidth/2, Height);
		woman.setBounds(50+LWidth+TWidth/2, 360, TWidth/2, Height);
		MidJP.add(l9);MidJP.add(man);MidJP.add(woman);
		
		l10=new JLabel("年       级:");
		l10.setBounds(40,400,LWidth,Height);
		grade=new Choice();
		grade.add("1");grade.add("2");grade.add("3");grade.add("4");grade.add("5");
		grade.add("6");grade.add("7");grade.add("8");grade.add("9");grade.add("10");
		grade.add("11");
		grade.setBounds(50+LWidth, 400, TWidth, Height);
		MidJP.add(l10);MidJP.add(grade);
		
		l11=new JLabel("班       级:");
		l11.setBounds(40,440,LWidth,Height);
		classroom=new Choice();
		classroom.add("Big");classroom.add("Middle");classroom.add("Small");
		classroom.setBounds(50+LWidth,440,TWidth,Height);
		MidJP.add(l11);MidJP.add(classroom);
		
		b1=new JButton("保存");
		b2=new JButton("关闭");
		b1.setBounds(140, 480, 60, 20);
		b2.setBounds(210,480,60,20);
		MidJP.add(b1);MidJP.add(b2);
		
		this.add(MidJP);
		
		b1.addActionListener(this);
		b1.setActionCommand("保存");
		b2.addActionListener(this);
		b2.setActionCommand("关闭");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("保存")){
			if(ValidateInput()){
				sqlStr="INSERT INTO Student (LoginId,LoginPwd,UserStateId,GradeId,StudentName,Sex,Phone,StudentNO,Email,Class，Major) values " +
						"(?,?,?,?,?,?,?,?,?,?,?)";
				DBCon dbcon=new DBCon();
				try {
					int stateN;//记录状态
					ps=dbcon.dbCon.prepareStatement(sqlStr);
					ps.setString(1, username.getText());
					ps.setString(2, pwd);
					if(state.getSelectedCheckbox().getLabel().equals("活动")){
						stateN=15;
					}
					else stateN=16;
					ps.setInt(3, stateN);
					ps.setString(4, grade.getSelectedItem());
					ps.setString(5, name.getText());
					ps.setString(6, sex.getSelectedCheckbox().getLabel());
					ps.setString(7, Pno.getText());
					ps.setString(8, Sno.getText());
					ps.setString(9, Email.getText());
					ps.setString(10, classroom.getSelectedItem());
					ps.setString(11, "软件工程");
					ps.executeUpdate();
					JOptionPane.showMessageDialog(this, "保存成功");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally{
					dbcon.close();
				}
				this.dispose();
			}
		}
		else if(e.getActionCommand().equals("关闭")){
			this.dispose();
		}
	}
	private boolean ValidateInput()
    {
		pwd=new String(password.getPassword());
		pwdag=new String(passwordAgain.getPassword());
        if (username.getText().equals(""))  // 验证是否输入了用户名 
        {
        	JOptionPane.showMessageDialog(this, "请输入用户名");
            username.requestFocus();
            return false;
        }
        if (pwd.equals(""))  // 验证是否输入了密码
        {
        	JOptionPane.showMessageDialog(this, "请输入密码");
            password.requestFocus();
            return false;
        }
        if (!(pwd.equals(pwdag)))  // 验证两次密码是否一致
        {
        	System.out.println(password.getPassword());
        	System.out.println(passwordAgain.getPassword());
        	JOptionPane.showMessageDialog(this, "对不起，两次输入密码不一致");
            passwordAgain.requestFocus();
            return false;
        }
        if (name.getText().equals(""))  // 验证是否输入了用户姓名
        {
        	JOptionPane.showMessageDialog(this, "请输入用户姓名");
            name.requestFocus();
            return false;
        }
        if (Sno.getText().equals(""))  // 验证是否输入了学号
        {
        	JOptionPane.showMessageDialog(this, "请输入学号");
            Sno.requestFocus();
            return false;
        }
        return true;
    }
}
