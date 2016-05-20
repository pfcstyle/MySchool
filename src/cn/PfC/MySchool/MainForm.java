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
		this.setTitle("�������");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void Layout(){
		m1=new Menu("�û�����");
		m11=new Menu("�����û�");
		m11.add("����ѧԱ�û�");
		m11.add("������Ա�û�");
		m13=new Menu("�û���Ϣ�б�");
		m13.add("ѧԱ��Ϣ�б�");
		m13.add("��Ա��Ϣ�б�");
		m1.add(m11);
		m1.add("��ѯ���޸�ѧԱ");
		m1.add(m13);
		m1.addSeparator();
		m1.add("�˳�");
		m1.addActionListener(this);
		m11.addActionListener(this);
		m13.addActionListener(this);
		
		image=new ImageIcon("nwdaes10.jpg");
		l4=new JLabel(image);
		l4.setBounds(0, 0, 800, 600);
		
		m2=new Menu("������");
		m2.add("������");
		m2.addActionListener(this);
		
		m3=new Menu("���Թ���");
		m3.add("���Թ���");
		m3.addActionListener(this);
		
		m4 =new Menu("����");
		m4.addActionListener(this);
		
		m5=new Menu("����");
		m5.add("���ڿ��Թ���ϵͳ");
		m5.addActionListener(this);
		
		caidan=new MenuBar();
		caidan.add(m1); caidan.add(m2); caidan.add(m3); caidan.add(m4); caidan.add(m5);
		this.setMenuBar(caidan);
		this.add(l4);
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("��ѯ���޸�ѧԱ")){
			//System.out.println("Yes!");
			new SearchStudentForm();
		}
		else if(e.getActionCommand().equals("����ѧԱ�û�")){
			if(LoginForm.loginType==0)
				new AddStudentForm();
			else
				JOptionPane.showMessageDialog(this, "�Բ���ֻ�й���Ա�д�Ȩ��");
		}
		else if(e.getActionCommand().equals("������Ա�û�")){
			if(LoginForm.loginType==0)
				new AddTeacherForm();
			else
				JOptionPane.showMessageDialog(this, "�Բ���ֻ�й���Ա�д�Ȩ��");
		}
		else if(e.getActionCommand().equals("ѧԱ��Ϣ�б�")){
			new StudentListForm();
		}
		else if(e.getActionCommand().equals("��Ա��Ϣ�б�")){
			//System.out.println("jiaoyuan");
			if(LoginForm.loginType!=1)
				new TeacherListForm();
			else
				JOptionPane.showMessageDialog(this, "�Բ���ֻ�й���Ա�ͽ�Ա�д�Ȩ��");
		}
		else if(e.getActionCommand().equals("������")){
			if(LoginForm.loginType!=1)
				new QuestionForm();
			else
				JOptionPane.showMessageDialog(this, "�Բ���ֻ�й���Ա�ͽ�Ա�д�Ȩ��");
		}
		else if(e.getActionCommand().equals("���Թ���")){
			new TestForm();
		}
	}

}
