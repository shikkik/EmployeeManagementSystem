package forms;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

import net.proteanit.sql.DbUtils;
import connect.dbCon;

public class frmReg extends JFrame implements ActionListener, KeyListener, MouseListener {
	public JTable usertable;
	public JComboBox<?> comboBoxSelect;
	public JLabel lblClock, lblNewLabel, lblID, lbluname, lblpword, lblCreatedBy;
	public JTextField tfuname, tfpword, txtID, textFieldSearch;
	public JButton btnSave, btnUpdate, btnDelete, btnSearch, btnLoadTable, btnReset;
	public JScrollPane scrollPane;
	public MaskFormatter mf;

	Font f1 = new Font("Cambria", Font.BOLD, 12);
	Font f2 = new Font("Cambria", Font.BOLD, 14);
	Font f3 = new Font("Cambria", Font.BOLD, 16);
	Font f4 = new Font("Cambria", Font.BOLD, 24);

	Color c1 = Color.WHITE;
	Color c2 = Color.DARK_GRAY;
	Color c3 = Color.BLUE;
	Color c4 = Color.RED;
	Color c5 = Color.CYAN;
	Color c6 = Color.BLACK;

	public Connection conn = null;
	public Statement st = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	public DefaultTableModel dm = null;
	public ResultSetMetaData rsmd = null;

	// METHOD CLOCK
	public void Clock() {
		Thread clock = new Thread() {
			public void run() {
				try {
					while (true) {
						Calendar cal = new GregorianCalendar();
						int day = cal.get(Calendar.DAY_OF_MONTH);
						int month = cal.get(Calendar.MONTH);
						int year = cal.get(Calendar.YEAR);
						int second = cal.get(Calendar.SECOND);
						int minute = cal.get(Calendar.MINUTE);
						int hour = cal.get(Calendar.HOUR);
						lblClock.setText("Time " + hour + " : " + minute + " : " + second + " Date " + year + " / "
								+ (month + 1) + " / " + day);
						sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		clock.start();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public frmReg() {
		setContentPane(new JLabel(new ImageIcon("img/bg1.jpg")));
		conn = dbCon.getConnection();
		Container con = getContentPane();

		con.add(lblClock = new JLabel(""));
		con.add(lblNewLabel = new JLabel("User Registration"));
		con.add(lblID = new JLabel("ID"));
		con.add(lbluname = new JLabel("User Name"));
		con.add(lblpword = new JLabel("Password"));
		con.add(lblCreatedBy = new JLabel("Created By: EVFajardo"));
		con.add(txtID = new JTextField());
		con.add(tfuname = new JTextField());
		con.add(tfpword = new JTextField());
		con.add(textFieldSearch = new JTextField());
		textFieldSearch.addKeyListener(this);
		con.add(comboBoxSelect = new JComboBox());
		con.add(scrollPane = new JScrollPane());
		con.add(btnSave = new JButton("Save"));
		con.add(btnUpdate = new JButton("Update"));
		con.add(btnDelete = new JButton("Delete"));
		con.add(btnReset = new JButton("New"));
		con.add(btnSearch = new JButton("Search"));
		con.add(btnLoadTable = new JButton("Load Data"));
		btnSave.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
		btnReset.addActionListener(this);
		btnSearch.addActionListener(this);
		btnLoadTable.addActionListener(this);
		lblNewLabel.setBounds(160, 25, 385, 40);
		lblClock.setBounds(10, 360, 220, 30);
		lblCreatedBy.setBounds(10, 380, 220, 30);
		lblID.setBounds(10, 120, 120, 30);
		lbluname.setBounds(10, 160, 120, 30);
		lblpword.setBounds(10, 200, 120, 30);
		txtID.setBounds(120, 120, 150, 25);
		tfuname.setBounds(120, 160, 150, 25);
		tfpword.setBounds(120, 200, 150, 25);
		textFieldSearch.setBounds(520, 85, 100, 30);
		comboBoxSelect.setModel(new DefaultComboBoxModel(new String[] { "id", "uname" }));
		comboBoxSelect.setBounds(410, 85, 100, 30);
		scrollPane.setBounds(280, 120, 440, 200);
		btnLoadTable.setBounds(280, 85, 120, 30);
		btnSearch.setBounds(630, 85, 90, 30);
		btnReset.setBounds(280, 330, 100, 30);
		btnSave.setBounds(390, 330, 100, 30);
		btnUpdate.setBounds(500, 330, 110, 30);
		btnDelete.setBounds(620, 330, 100, 30);
		txtID.setEditable(false);
		lblNewLabel.setFont(f4);
		lblClock.setFont(f2);
		lblID.setFont(f2);
		lbluname.setFont(f2);
		lblpword.setFont(f2);
		lblCreatedBy.setFont(f2);
		textFieldSearch.setFont(f1);
		textFieldSearch.setColumns(10);
		tfuname.setFont(f1);
		tfuname.setColumns(10);
		tfpword.setFont(f1);
		tfpword.setColumns(10);
		comboBoxSelect.setFont(f1);
		btnSearch.setFont(f1);
		btnSave.setFont(f3);
		btnUpdate.setFont(f3);
		btnDelete.setFont(f3);
		btnReset.setFont(f3);
		btnLoadTable.setFont(f2);
		textFieldSearch.setBackground(c5);
		textFieldSearch.setForeground(c6);
		lblNewLabel.setForeground(c6);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		btnLoadTable.setForeground(c3);
		btnSave.setForeground(c1);
		btnSave.setBackground(c2);
		btnUpdate.setForeground(c1);
		btnUpdate.setBackground(c2);
		btnDelete.setForeground(c1);
		btnDelete.setBackground(c2);
		btnReset.setForeground(c1);
		btnReset.setBackground(c2);
		usertable = new JTable();
		scrollPane.setViewportView(usertable);
		usertable.addMouseListener(this);

		IconFontSwing.register(FontAwesome.getIconFont());
		Icon icn_id = IconFontSwing.buildIcon(FontAwesome.HASHTAG, 15, c6);
		Icon icn_name = IconFontSwing.buildIcon(FontAwesome.ADDRESS_CARD_O, 15, c6);
		Icon icn_sname = IconFontSwing.buildIcon(FontAwesome.ADDRESS_CARD_O, 15, c6);
		Icon icn_age = IconFontSwing.buildIcon(FontAwesome.CALENDAR_O, 15, c6);
		Icon icn_load = IconFontSwing.buildIcon(FontAwesome.DOWNLOAD, 15, c6);
		Icon icn_search = IconFontSwing.buildIcon(FontAwesome.SEARCH, 15, c6);
		Icon icn_new = IconFontSwing.buildIcon(FontAwesome.FILE_O, 15, c1);
		Icon icn_save = IconFontSwing.buildIcon(FontAwesome.DATABASE, 15, c1);
		Icon icn_update = IconFontSwing.buildIcon(FontAwesome.FILE_TEXT_O, 15, c1);
		Icon icn_delete = IconFontSwing.buildIcon(FontAwesome.TRASH_O, 15, c1);

		lblID.setIcon(icn_id);
		lbluname.setIcon(icn_name);
		lblpword.setIcon(icn_sname);
		btnLoadTable.setIcon(icn_load);
		btnSearch.setIcon(icn_search);
		btnSave.setIcon(icn_save);
		btnUpdate.setIcon(icn_update);
		btnDelete.setIcon(icn_delete);
		btnReset.setIcon(icn_new);
		Clock();

	}

	// method search
	public void Search() {
		try {
			String selection = (String) comboBoxSelect.getSelectedItem();
			String query = "select * from user where " + selection + " = ? ";
			ps = conn.prepareStatement(query);

			ps.setString(1, textFieldSearch.getText());
			rs = ps.executeQuery();

			usertable.setModel(DbUtils.resultSetToTableModel(rs));
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	// update table
	public void refreshTable() {
		try {
			String query = "select * from user";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			usertable.setModel(DbUtils.resultSetToTableModel(rs));
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// clear
	public void Reset() {
		tfuname.setText("");
		tfpword.setText("");

	}

	// loaddata
	public void LoadList(){   
		try {    String query = "select * from user ";    
		ps = conn.prepareStatement(query);    
		rs = ps.executeQuery();        
		
		DefaultListModel<String> DLM = new DefaultListModel<String>();        
		
		while(rs.next()){     
			DLM.addElement(rs.getString("uname"));    
			}    
		ps.close();    
		} catch (Exception e) {    
			e.printStackTrace();   	
		}  
		
	}

	public void actionPerformed(ActionEvent e) {   
		if(e.getSource()==btnLoadTable) {    
			try {     
				String query = "select * from user";     
				ps = conn.prepareStatement(query);     
				rs = ps.executeQuery();     
				usertable.setModel(DbUtils.resultSetToTableModel(rs));     
				ps.close();     
				rs.close();         
				} catch (Exception ex) {     
					ex.printStackTrace();    
					}   
			}   
		if(e.getSource()==btnSearch) {    
			Search();  
			
		}   
		if(e.getSource()==btnSave) {    
			try {     
				String query = "insert into user (uname,pword) values (?, ?) ";
				ps = conn.prepareStatement(query);        
				ps.setString(1, tfuname.getText());  
				ps.setString(2, tfpword.getText());      
				ps.execute();     
				JOptionPane.showMessageDialog(null, "Data Saved");     
				ps.close();         
				} catch (Exception ex) {     
					ex.printStackTrace();   
				}    
			refreshTable();    
			Reset();  
			}  
		if(e.getSource()==btnUpdate) {   
			try {   
				String query = "update user set id = '"+ txtID.getText() +"', uname='"+ tfuname.getText() +"', pword = '" + tfpword.getText() + "' where id = '" + txtID.getText() + "'";    
				ps = conn.prepareStatement(query);             
				ps.execute();         
				JOptionPane.showMessageDialog(null, "Data Updated"); 
				ps.close();    
				} catch (Exception ex) {   
					ex.printStackTrace();  		
					
				}    
			refreshTable();   
			Reset();   }   
		if(e.getSource()==btnDelete) {  
			int action = JOptionPane.showConfirmDialog(null, "Do you want to delete!", "Delete", JOptionPane.YES_NO_OPTION);    
			if(action == 0){    
				try { 
					String query = "delete from user where id = '" + txtID.getText() + "' ";   
					ps = conn.prepareStatement(query);   
					ps.execute(); 
					JOptionPane.showMessageDialog(null, "Data Deleted");   
					ps.close();     
					} catch (Exception ex) { 
						ex.printStackTrace();   
						
					}    
				refreshTable();    
				Reset();    
				
			}   
			
		}   
		if(e.getSource()==btnReset) { 
			Reset();   
		}      
		refreshTable();   
		LoadList();  
		}   
	//unused
	
	@Override  public void keyPressed(KeyEvent arg0) {} 
	@Override  public void keyTyped(KeyEvent arg0) {}    
	
	@Override  public void keyReleased(KeyEvent ke) {   
		if(ke.getSource()==textFieldSearch) {    
			Search();   
			}  
		}    
	//unused  
	@Override  
	public void mouseEntered(MouseEvent arg0) {}  
	@Override  public void mouseExited(MouseEvent arg0) {}  
	@Override  public void mousePressed(MouseEvent arg0) {}  
	@Override  public void mouseReleased(MouseEvent arg0) {}   
	@Override  public void mouseClicked(MouseEvent me) {   
		if(me.getSource()==usertable) {    
			try {     
				int row = usertable.getSelectedRow();     
				String id = (usertable.getModel().getValueAt(row, 0).toString());  
				String query = "select * from user where id = '" + id + "' ";  
				ps = conn.prepareStatement(query);       
				rs = ps.executeQuery();    
				while(rs.next()){ 
					txtID.setText(rs.getString("id"));
					tfuname.setText(rs.getString("uname"));  
					tfpword.setText(rs.getString("pword"));    
					}    
				ps.close();              
				} catch (Exception e) {     
					e.printStackTrace();   
					}    
			}  
		}  
	public static void main(String[] args) {     
		
	} 
	
}					
