package forms;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.table.*;
import javax.swing.text.*;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.util.*;

import net.proteanit.sql.DbUtils;
import connect.dbCon;

public class frmEE extends JFrame implements ActionListener, KeyListener, MouseListener {
	public String s;
	public JTable eetable;
	public JComboBox<?> comboBoxSelect;
	public JLabel lblClock, lblNewLabel, lblID, lblName, lblSurname, lblAge, lblCreatedBy, lblimage;
	public JTextField textFieldName, textFieldSurname, textFieldAge, textFieldSearch, txtID;
	public JFormattedTextField textFieldEID;
	public JButton btnSave, btnUpdate, btnDelete, btnSearch, btnLoadTable, btnReset, btnUpload;
	public JScrollPane scrollPane;
	public MaskFormatter mf;
	public JFileChooser fileChooser;
	public FileNameExtensionFilter filter;
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
	public ResultSetMetaData rsmd = null; // METHOD CLOCK

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
	public frmEE() { 
		setContentPane(new JLabel(new ImageIcon("img/bg1.jpg")));   
		conn = dbCon.getConnection();   
		Container con = getContentPane();  
		
		con.add(lblClock = new JLabel(""));   
		con.add(lblNewLabel = new JLabel("The Employee Information System"));   
		con.add(lblID = new JLabel("ID"));   
		con.add(lblName = new JLabel("Name"));   
		con.add(lblSurname = new JLabel("Surname"));   
		con.add(lblAge = new JLabel("Age"));   
		con.add(lblCreatedBy = new JLabel("Created By: EVFajardo"));   
		con.add(lblimage = new JLabel("No Image", SwingConstants.CENTER));        
		
		con.add(txtID = new JTextField());      
		try {
			mf = new MaskFormatter("##-####");
			} catch (ParseException e) {    
				e.printStackTrace();   
		}   
		mf.setPlaceholderCharacter('_');   
		con.add(textFieldEID = new JFormattedTextField(mf));
		
		con.add(textFieldName = new JTextField());   
		con.add(textFieldSurname = new JTextField());      
		
		con.add(textFieldAge = new JTextField());   
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
		con.add(btnUpload = new JButton("..."));      
		
		con.add(fileChooser = new JFileChooser());   
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));   
		filter = new FileNameExtensionFilter("PNG JPG AND JPEG", "png", "jpeg", "jpg");      
		
		fileChooser.addChoosableFileFilter(filter);      
		
		btnSave.addActionListener(this);   
		btnUpdate.addActionListener(this);   
		btnDelete.addActionListener(this);   
		btnReset.addActionListener(this);   
		btnSearch.addActionListener(this);   
		btnLoadTable.addActionListener(this);   
		btnUpload.addActionListener(this);   
		
		lblNewLabel.setBounds(160, 25, 385, 40);   
		lblClock.setBounds(10, 360, 230, 30);     
		lblCreatedBy.setBounds(10, 380, 220, 30);   
		lblID.setBounds(10, 90, 120, 30);   
		lblName.setBounds(10, 120, 120, 30);   
		lblSurname.setBounds(10, 150, 120, 30);   
		lblAge.setBounds(10, 180, 120, 30);   
		lblimage.setBounds(100,210,150,150);      
		
		
		lblimage.setBorder(BorderFactory.createLineBorder(Color.black));      
		
		txtID.setBounds(900, 900, 150, 25);   
		textFieldEID.setBounds(100, 90, 150, 25);   
		textFieldName.setBounds(100, 120, 150, 25);   
		textFieldSurname.setBounds(100, 150, 150, 25);   
		textFieldAge.setBounds(100, 180, 150, 25);   
		textFieldSearch.setBounds(520, 85, 100, 30); 
		
		comboBoxSelect.setModel(new DefaultComboBoxModel(new String[] {"id", "EEID", "Name", "Surname", "Age"}));   
		comboBoxSelect.setBounds(410, 85, 100, 30);   
		scrollPane.setBounds(280, 120, 440, 200);      
		
		btnLoadTable.setBounds(280, 85, 120, 30);   
		btnSearch.setBounds(630, 85, 90, 30);   
		btnReset.setBounds(280, 330, 100, 30);   
		btnSave.setBounds(390, 330, 100, 30);   
		btnUpdate.setBounds(500, 330, 110, 30);   
		btnDelete.setBounds(620, 330, 100, 30);   
		btnUpload.setBounds(10,210,80,30);      
		
		txtID.setEditable(false);   
		txtID.setVisible(true);      
		
		lblNewLabel.setFont(f4);   
		lblClock.setFont(f2);   
		lblID.setFont(f2);   
		lblName.setFont(f2);   
		lblSurname.setFont(f2);   
		lblAge.setFont(f2);   
		lblCreatedBy.setFont(f2); 
		 
		
		textFieldSearch.setFont(f1); 
		textFieldSearch.setColumns(10);   
		textFieldEID.setFont(f1); 
		textFieldEID.setColumns(10);   
		textFieldName.setFont(f1); 
		textFieldName.setColumns(10);   
		textFieldSurname.setFont(f1); 
		textFieldSurname.setColumns(10);   
		textFieldAge.setFont(f1); 
		textFieldAge.setColumns(10);      
		
		comboBoxSelect.setFont(f1); 
		 
		btnSearch.setFont(f1); 
		btnSave.setFont(f2);   
		btnUpdate.setFont(f2); 
		btnDelete.setFont(f2);   
		btnReset.setFont(f2); 
		btnLoadTable.setFont(f2);   
		btnUpload.setFont(f2); 
		
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
		btnUpload.setForeground(c1); 
		btnUpload.setBackground(c2);      
		
		eetable = new JTable();   
		scrollPane.setViewportView(eetable);  
		eetable.addMouseListener(this);      
		
		IconFontSwing.register(FontAwesome.getIconFont());   
		Icon icn_id = IconFontSwing.buildIcon(FontAwesome.HASHTAG,15,c6);   
		Icon icn_name = IconFontSwing.buildIcon(FontAwesome.ADDRESS_CARD_O ,15,c6);   
		Icon icn_sname = IconFontSwing.buildIcon(FontAwesome.ADDRESS_CARD_O ,15,c6);   
		Icon icn_age = IconFontSwing.buildIcon(FontAwesome.CALENDAR_O,15,c6);   
		Icon icn_load = IconFontSwing.buildIcon(FontAwesome.DOWNLOAD,15,c6);   
		Icon icn_search = IconFontSwing.buildIcon(FontAwesome.SEARCH,15,c6);   
		Icon icn_new = IconFontSwing.buildIcon(FontAwesome.FILE_O ,15,c1);    
		Icon icn_save = IconFontSwing.buildIcon(FontAwesome.DATABASE,15,c1);    
		Icon icn_update = IconFontSwing.buildIcon(FontAwesome.FILE_TEXT_O,15,c1);    
		Icon icn_delete = IconFontSwing.buildIcon(FontAwesome.TRASH_O,15,c1);    
		Icon icn_upload = IconFontSwing.buildIcon(FontAwesome.UPLOAD,15,c1);       
		
		lblID.setIcon(icn_id);lblName.setIcon(icn_name);   
		lblSurname.setIcon(icn_sname);lblAge.setIcon(icn_age);   
		btnLoadTable.setIcon(icn_load);btnSearch.setIcon(icn_search);   
		btnSave.setIcon(icn_save);btnUpdate.setIcon(icn_update);   
		btnDelete.setIcon(icn_delete);btnReset.setIcon(icn_new);   
		btnUpload.setIcon(icn_upload);      
		Clock();  
		
	}

	// method search
	public void Search() {
		try {
			String selection = (String) comboBoxSelect.getSelectedItem();
			String query = "select * from employee where " + selection + " = ? ";
			ps = conn.prepareStatement(query);

			ps.setString(1, textFieldSearch.getText());
			rs = ps.executeQuery();

			eetable.setModel(DbUtils.resultSetToTableModel(rs));
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// update table
	public void refreshTable() {
		try {
			String query = "select * from employee";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			eetable.setModel(DbUtils.resultSetToTableModel(rs));
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// clear
	public void Reset() {
		textFieldEID.setText("");
		textFieldName.setText("");
		textFieldSurname.setText("");
		textFieldAge.setText("");
	}

	// loaddata
	public void LoadList() {
		try {
			String query = "select * from employee ";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			DefaultListModel<String> DLM = new DefaultListModel<String>();
			while (rs.next()) {
				DLM.addElement(rs.getString("Name"));
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// resize image
	public ImageIcon ResizeImage(String imgPath) {
		ImageIcon MyImage = new ImageIcon(imgPath);
		Image img = MyImage.getImage();
		Image newImage = img.getScaledInstance(lblimage.getWidth(), lblimage.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImage);
		return image;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLoadTable) {
			try {
				String query = "select * from employee";
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				eetable.setModel(DbUtils.resultSetToTableModel(rs));
				ps.close();
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (e.getSource() == btnSearch) {
			Search();
		}
		if (e.getSource() == btnSave) {
			try {
				String query = "insert into employee (EEID, Name, SurName, Age,image) values (?, ?, ?, ?,?) ";
				ps = conn.prepareStatement(query);
				InputStream img = new FileInputStream(new File(s));
				ps.setString(1, textFieldEID.getText());
				ps.setString(2, textFieldName.getText());
				ps.setString(3, textFieldSurname.getText());
				ps.setString(4, textFieldAge.getText());
				ps.setBlob(5, img);
				ps.execute();
				JOptionPane.showMessageDialog(null, "Data Saved");
				ps.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			refreshTable();
			Reset();
		}
		if (e.getSource() == btnUpdate) {
			try {
				String query = "update employee set EEID=?, Name=?, Surname=?, Age=?, image=? where id=?";
				ps = conn.prepareStatement(query);
				InputStream img = new FileInputStream(new File(s));
				ps.setString(1, textFieldEID.getText());
				ps.setString(2, textFieldName.getText());
				ps.setString(3, textFieldSurname.getText());
				ps.setString(4, textFieldAge.getText());
				ps.setBlob(5, img);
				ps.setString(6, txtID.getText());
				ps.execute();

				JOptionPane.showMessageDialog(null, "Data Updated");
				ps.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			refreshTable();
			Reset();
		}
		if (e.getSource() == btnDelete) {
			int action = JOptionPane.showConfirmDialog(null, "Do you want to delete!", "Delete",
					JOptionPane.YES_NO_OPTION);
			if (action == 0) {
				try {
					String query = "delete from employee where id = '" + txtID.getText() + "' ";
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
		if (e.getSource() == btnReset) {
			Reset();
		}
		if (e.getSource() == btnUpload) {
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg", "gif", "png");
			fileChooser.addChoosableFileFilter(filter);
			int result = fileChooser.showSaveDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				String path = selectedFile.getAbsolutePath();
				lblimage.setIcon(ResizeImage(path));
				s = path;
			} else if (result == JFileChooser.CANCEL_OPTION) {
				System.out.println("No Data");
			}
		}
		refreshTable();
		LoadList();
	} // unused

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		if (ke.getSource() == textFieldSearch) {
			Search();
		}
	}

	// unused
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getSource() == eetable) {
			try {
				int row = eetable.getSelectedRow();
				String id = (eetable.getModel().getValueAt(row, 0).toString());
				String query = "select * from employee where id = '" + id + "' ";
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				while (rs.next()) {
					txtID.setText(rs.getString("id"));
					textFieldEID.setText(rs.getString("EEID"));
					textFieldName.setText(rs.getString("Name"));
					textFieldSurname.setText(rs.getString("Surname"));
					textFieldAge.setText(rs.getString("Age"));
					byte[] img = rs.getBytes("image");
					ImageIcon image = new ImageIcon(img);
					Image im = image.getImage();
					Image myImg = im.getScaledInstance(lblimage.getWidth(), lblimage.getHeight(), Image.SCALE_SMOOTH);
					ImageIcon newImage = new ImageIcon(myImg);
					lblimage.setIcon(newImage);
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
