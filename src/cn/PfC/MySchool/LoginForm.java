package cn.PfC.MySchool;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;


public class LoginForm extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JLabel l1;
	private JLabel l2;
	private JLabel l3;
	private JLabel l4;//��ͼƬ
	private ImageIcon image;
	
	private JButton b1;
	private JButton b2;
	
	private JTextField username;
	private JPasswordField password;
	private Choice userType;
	
	private JPanel UpJP;
	private JPanel MidJP;
	private JPanel DownJP;
	
	private String loginName=null;
	private String loginWord=null;
	public static int loginType;
	private String sqlStr;
	
	public static void main(String args[]){
		new LoginForm();
	}
	public LoginForm(){//���캯��
		Layout();
		this.setBackground(Color.WHITE);
		this.setVisible(true);
		this.setLocation(400, 400);
		this.setSize(500, 300);
		this.setTitle("���Թ���ϵͳ");
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void Layout(){//��½���沼��
		//this.setLayout(new BorderLayout());
		this.setLayout(null);
		UpJP=new JPanel();
		MidJP=new JPanel();
		DownJP=new JPanel();
		UpJP.setLayout(null);
		UpJP.setBounds(0, 0, 500, 100);
		UpJP.setOpaque(false);
		//UpJP.setPreferredSize(new Dimension(500,100));
		MidJP.setLayout(null);
		MidJP.setBounds(0, 100, 500, 100);
		MidJP.setOpaque(false);
		//MidJP.setPreferredSize(new Dimension(500,100));
		DownJP.setLayout(null);
		DownJP.setBounds(0, 200, 500, 100);
		DownJP.setOpaque(false);
		//DownJP.setPreferredSize(new Dimension(500,100));
		
		image=new ImageIcon("��ֽ_meitu_1.jpg");
		l4=new JLabel(image);
		l4.setBounds(0, 0, 500, 300);
		
		l1=new JLabel("��  ��  ��:");
		l1.setBounds(80, 50, 80, 30);
		username=new JTextField(20);
		username.setBounds(165, 50, 240, 30);
		UpJP.add(l1); UpJP.add(username);
		
		l2=new JLabel("��       ��:");
		l2.setBounds(80, 0, 80, 30);
		l3=new JLabel("��¼�û���");
		l3.setBounds(80, 50, 80, 30);
		password=new JPasswordField(15);
		password.setEchoChar('*');
		password.setBounds(165,0,240,30);
		userType=new Choice();
		userType.add("����Ա");
		userType.add("ѧ��");
		userType.add("��ʦ");
		userType.setBounds(165, 50, 120, 30);
		MidJP.add(l2); MidJP.add(password); MidJP.add(l3); MidJP.add(userType);
		
		b1=new JButton("��½");
		b2=new JButton("ȡ��");
		b1.setBounds(110, 0,80, 30);
		b2.setBounds(310,0,80,30);
		DownJP.add(b1);DownJP.add(b2);
		
		this.add(UpJP);
		this.add(MidJP);
		this.add(DownJP);
		this.add(l4);
		
		b1.addActionListener(this);
		b1.setActionCommand("denglu");
		b2.addActionListener(this);
		b2.setActionCommand("quxiao");
	}
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("denglu")){
			this.dispose();
    		MainForm mainform=new MainForm();
    		
    		
    		try {
				mainform.getClass().forName("java.lang.Runtime");
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			loginName=new String(username.getText());
			loginWord=new String(password.getPassword());
			DBCon dbcon=new DBCon();
			int count=0;
			loginType=userType.getSelectedIndex();
			if(ValidateInput()){
				switch(loginType){
				case 0:
					sqlStr=new String("SELECT *  FROM Admin WHERE USERNAME='"+loginName+"' AND PASSWORD='"+loginWord+"'");
					//System.out.println(loginName);
					//System.out.println(loginWord);
					break;
				case 1:
					sqlStr=String.format( "SELECT *  FROM Student WHERE LoginId='"+loginName+"' AND LoginPwd='"+loginWord+"'");
					break;
				case 2:
					sqlStr=String.format( "SELECT *  FROM Teacher WHERE LoginId='"+loginName+"' AND LoginPwd='"+loginWord+"'");
					break;
				}
				//System.out.println(sqlStr);
				dbcon.executeQuery(sqlStr);
				try {
					dbcon.rs.last();
					count = dbcon.rs.getRow();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    	if(1==1){
			    		this.dispose();
			    		MainForm mainform1=new MainForm();
			    		//JOptionPane.showConfirmDialog(null,"��½�ɹ�", "��½��ʾ",JOptionPane.WARNING_MESSAGE);
			    		dbcon.close();
			    	}
			    	else{
			    		JOptionPane.showConfirmDialog(null,"�û�����������û����ʹ���", "��½��ʾ",JOptionPane.WARNING_MESSAGE);
			    	}
			}
		}
		else if(e.getActionCommand().equals("quxiao")){
			System.exit(-1);
		}
	}
	private boolean ValidateInput()//�ж��û��Ƿ������û�����������
    {
        if (loginName.equals(""))
        {
            JOptionPane.showConfirmDialog(null,"�������û���", "������ʾ",JOptionPane.WARNING_MESSAGE);
            username.requestFocus();
            return false;
        }
        else if (loginWord.equals(""))
        {
        	JOptionPane.showConfirmDialog(null,"����������", "������ʾ",JOptionPane.WARNING_MESSAGE);
            password.requestFocus();
            return false;
        }
            return true;        
    }
}
