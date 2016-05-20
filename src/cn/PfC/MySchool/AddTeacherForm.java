package cn.PfC.MySchool;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class AddTeacherForm extends JFrame implements ActionListener{
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
	
	private JTextField username;
	private JPasswordField password;
	private JPasswordField passwordAgain;
	private JTextField name;
	private JTextField Jno;
	private DateChooserJButton birthday;
	
	private CheckboxGroup state;
	private CheckboxGroup sex;
	private Checkbox active,unactive,man,woman;
	
	private JPanel MidJP;
	
	private PreparedStatement ps;
	private String sqlStr;
	private String pwd;
	private String pwdag;
	
	public AddTeacherForm(){
		Layout();
		this.setBackground(Color.WHITE);
		this.setLocation(0, 0);
		this.setSize(300, 480);
		this.setTitle("添加教员信息");
		this.setVisible(true);
	}
	public void Layout(){
		this.setLayout(new BorderLayout());
		MidJP=new JPanel();
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
		MidJP.add(l4);MidJP.add(active);MidJP.add(unactive);
		
		l5=new JLabel("姓       名:");
		l5.setBounds(40,200,LWidth,Height);
		name=new JTextField(20);
		name.setBounds(50+LWidth, 200, TWidth, Height);
		MidJP.add(l5);MidJP.add(name);
		
		l6=new JLabel("教       号:");
		l6.setBounds(40,240,LWidth,Height);
		Jno=new JTextField(20);
		Jno.setBounds(50+LWidth, 240, TWidth, Height);
		MidJP.add(l6);MidJP.add(Jno);
		
		l7=new JLabel("性         别:");
		l7.setBounds(40,280,LWidth,Height);
		sex=new CheckboxGroup();
		man=new Checkbox("男",sex,true);
		woman=new Checkbox("女",sex,false);
		man.setBounds(50+LWidth, 280, TWidth/2, Height);
		woman.setBounds(50+LWidth+TWidth/2, 280, TWidth/2, Height);
		MidJP.add(l7);MidJP.add(man);MidJP.add(woman);
		
		l8=new JLabel("生       日:");
		l8.setBounds(40,320,LWidth,Height);
		birthday=new DateChooserJButton();
		birthday.setBounds(50+LWidth, 320, TWidth, Height);
		MidJP.add(l8);MidJP.add(birthday);
		
		b1=new JButton("保存");
		b2=new JButton("关闭");
		b1.setBounds(140, 360, 60, 20);
		b2.setBounds(210,360,60,20);
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
				sqlStr="INSERT INTO Teacher (LoginId,LoginPwd,UserStateId,TeacherName,Sex,Birthday,TeacherNo) values (?,?,?,?,?,?,?)";
				DBCon dbcon=new DBCon();
				try {
					int stateN;//记录状态
					ps=dbcon.dbCon.prepareStatement(sqlStr);
					ps.setString(1, username.getText());
					ps.setString(2, pwd);
					if(state.getSelectedCheckbox().getLabel()=="活动"){
						stateN=15;
					}
					else stateN=16;
					ps.setInt(3, stateN);
					ps.setString(4, name.getText());
					ps.setString(5, sex.getSelectedCheckbox().getLabel());
					ps.setString(6,birthday.getText());
					ps.setString(7,Jno.getText());
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
        if (Jno.getText().equals(""))  // 验证是否输入了学号
        {
        	JOptionPane.showMessageDialog(this, "请输入教号");
            Jno.requestFocus();
            return false;
        }
        if (birthday.getText().equals(""))  // 验证是否输入了生日
        {
        	JOptionPane.showMessageDialog(this, "请输入生日");
            birthday.requestFocus();
            return false;
        }
        return true;
    }
}
