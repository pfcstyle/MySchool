package cn.PfC.MySchool;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class SearchStudentForm extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String searchStr;//��¼�����ַ���
	private String sqlStr;//��¼sql����
	private int row;//��¼��ѡ���е��к�
	
	private JPanel UpJP;
	private JPanel DownJP;
	
	private JLabel l1;
	private JLabel l2;
	
	private JTextField search;
	
	private JButton b1;
	private JButton b2;
	private JButton b3;
	private JButton b4;
	
	DefaultTableModel dtm;
	private JTable t1;
	
	private Vector shuxing,jilu;
	//private Vector<Object> jilu;
	
	private JScrollPane sp;

	public SearchStudentForm(){
		Layout();
		this.setBackground(Color.WHITE);
		this.setVisible(true);
		this.setLocation(400, 200);
		this.setSize(500, 400);
		this.setTitle("���Թ���ϵͳ");
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
		
		l1=new JLabel("�û���");
		l1.setBounds(20, 10, 50, 30);
		search=new JTextField(20);
		search.setBounds(80, 10, 120, 30);
		l2=new JLabel("��֧��ģ�����ң�");
		l2.setBounds(200, 10, 120, 30);
		b1=new JButton("����");
		b1.setBounds(340,10,60,30);
		UpJP.add(l1);UpJP.add(search);UpJP.add(l2);UpJP.add(b1);
		
		shuxing=new Vector<String>();
		jilu=new Vector<String>();
		shuxing.add("�û���");
		shuxing.add("����");
		shuxing.add("ѧ��");
		shuxing.add("״̬");
		dtm=new MyModel(); 
		dtm.setDataVector(jilu, shuxing);
		t1=new JTable(dtm);
		//t1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sp=new JScrollPane(t1);
		
		b2=new JButton("�ر�");
		b2.setBounds(400,10,60,30);
		b3=new JButton("ˢ��");
		b4=new JButton("�޸�");
		b3.setBounds(330,10,60,30);
		b4.setBounds(260, 10, 60, 30);
		DownJP.add(b2);DownJP.add(b3);DownJP.add(b4);
		
		this.add(UpJP,"North");this.add(sp);this.add(DownJP,"South");
		
		b1.addActionListener(this);
		b1.setActionCommand("����");
		b2.addActionListener(this);
		b2.setActionCommand("�ر�");
		b3.addActionListener(this);
		b3.setActionCommand("ˢ��");
		b4.addActionListener(this);
		b4.setActionCommand("�޸�");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("����")){
			search();
		}
		else if(e.getActionCommand().equals("�ر�")){
			this.dispose();
		}
		else if(e.getActionCommand().equals("�޸�")){
			if(LoginForm.loginType!=1){
				if(isSelected()){
					row=t1.getSelectedRow();
					//System.out.println(row);
					EditorStudentForm.setUsername_old(t1.getValueAt(row, 0).toString());
					//System.out.println(username_old);
					new EditorStudentForm();
				}
			}
			else{
				JOptionPane.showMessageDialog(this, "�Բ���ֻ�й���Ա�ͽ�Ա�д�Ȩ��");
			}
			
		}
		else if(e.getActionCommand().equals("ˢ��")){
			search();
		}
	}
	private boolean isSelected(){
		if(t1.getSelectedRow()==-1){
			JOptionPane.showConfirmDialog(null,"��ѡ��Ҫ�޸ĵ���", "��ʾ",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	private void search(){
		searchStr=new String(search.getText());
		if(ValidateInput()){
			jilu.clear();
			DBCon dbcon=new DBCon();
			sqlStr=new String("select *from Student where LoginId like'%"+searchStr+"%'");
			dbcon.executeQuery(sqlStr);
			try {
				while(dbcon.rs.next()){
					Vector hang=new Vector();
					hang.add(dbcon.rs.getString(4));
					hang.add(dbcon.rs.getString(9));
					hang.add(dbcon.rs.getString(7));
					if(dbcon.rs.getInt(2)==15){
						hang.add("active");
					}
					else hang.add("unactive");
					//System.out.println(dbcon.rs.getString(2));
					jilu.add(hang);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			t1.revalidate();
			dbcon.close();
		}
	}
	private boolean ValidateInput()//�ж��û��Ƿ������û�����������
    {
        if (searchStr.equals(""))
        {
            JOptionPane.showConfirmDialog(null,"�������������", "������ʾ",JOptionPane.WARNING_MESSAGE);
            search.requestFocus();
            return false;
        }
            return true;        
    }
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
}
