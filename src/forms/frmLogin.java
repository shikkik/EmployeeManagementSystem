package forms;

import javax.swing.*;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

import connect.dbCon;

public class frmLogin extends JFrame implements ActionListener {

	public JLabel luname, lpwd;
	public JTextField tuname;
	public JButton login, cancel;
	public JPasswordField pfpwd;
	public char getpass[];
	public String pass;

	Font f1 = new Font("Times New Roman", Font.BOLD, 12);
	Font lbl = new Font("Times New Roman", Font.BOLD, 15);
	Color c1 = Color.WHITE;
	Color c2 = Color.DARK_GRAY;
	Color c3 = Color.BLUE;
	Color c4 = Color.RED;
	Color c5 = Color.BLACK;

	Connection conn = null;
	Statement stmt = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public frmLogin() {   
		setContentPane(new JLabel(new ImageIcon("img/bg1.jpg")));
		conn = dbCon.getConnection();   
		Container con = getContentPane();   
		con.setLayout(null);      
		
		IconFontSwing.register(FontAwesome.getIconFont());   
		Icon icn_uname = IconFontSwing.buildIcon(FontAwesome.USER,30,c5);   
		Icon icn_pwd = IconFontSwing.buildIcon(FontAwesome.KEY,20,c5);   
		Icon icn_login = IconFontSwing.buildIcon(FontAwesome.UNLOCK_ALT,20,c3);    
		Icon icn_cancel = IconFontSwing.buildIcon(FontAwesome.BAN,20,c4); 
		
		con.add(luname = new JLabel(" Username"));   
		con.add(lpwd = new JLabel(" Password"));   
		con.add(tuname = new JTextField(50));   
		con.add(pfpwd = new JPasswordField(50));     
		con.add(login = new JButton("Login"));     
		con.add(cancel = new JButton("Cancel"));   
		
		login.addActionListener(this);   
		cancel.addActionListener(this);      
		login.setIcon(icn_login);   
		cancel.setIcon(icn_cancel);  
		
		luname.setIcon(icn_uname);   
		lpwd.setIcon(icn_pwd);        
		luname.setBounds(50,30,150,30);   
		lpwd.setBounds(50,80,150,30);   
		tuname.setBounds(180,30,200,30);   
		pfpwd.setBounds(180,80,200,30);   
		login.setBounds(180,140,95,30);   
		cancel.setBounds(280,140,98,30);  
		
		luname.setFont(lbl);luname.setForeground(c5);   
		lpwd.setFont(lbl);lpwd.setForeground(c5);   
		tuname.setFont(f1);pfpwd.setFont(f1); 
		login.setFont(f1);cancel.setFont(f1);        
		
		con.setBackground(c2);    
		
	}

	public void actionPerformed (ActionEvent e) {   
		getpass = pfpwd.getPassword();   
		pass = String.valueOf(getpass);      
		
		if(e.getSource()==login) {    
			try {     
				String sql = "select * from user where uname=? and pword=?";     
				ps = conn.prepareStatement(sql);     
				ps.setString(1, tuname.getText());     
				ps.setString(2, pass);          
				
				rs = ps.executeQuery();     
				int count = 0;     
				while(rs.next()){    
					count = count + 1;   
					
					}     
				if (count == 1){      
					JOptionPane.showMessageDialog(null, "UserName and Password is correct");      
					
					new frmMainMenu();          
				}     
				else if(count > 1){      
					JOptionPane.showMessageDialog(null, "Duplicate UserName and Password");     
					}     
				else{      
					JOptionPane.showMessageDialog(null, "UserName and Password is not correct Try Again...");
					} 
				
				rs.close();     
				ps.close();    
				}catch(Exception err){     
					JOptionPane.showMessageDialog(null, err);    
					}   
			}
		if(e.getSource()==cancel) { 
			int dialog = JOptionPane.showConfirmDialog (null, "Are you sure you want to CANCEL?","WARNING",JOptionPane.YES_NO_OPTION);
			if(dialog == JOptionPane.YES_OPTION) {     
				frmLogin.this.dispose();    
				}    
			if(dialog == JOptionPane.NO_OPTION){     
				frmLogin.this.show();    
				}   
			}  
		}    public static void main(String[]args) {
			  frmLogin loginform = new frmLogin();   
			  loginform.setTitle("Login");   
			  loginform.setVisible(true);   
			  loginform.setSize(440,230);//W,H   
			  loginform.setLocationRelativeTo(null);   
			  loginform.setResizable(false);  
			  } 
		} 
		
	
		
		
		
		
		
		
	
