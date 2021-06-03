package forms;

import javax.swing.*;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import java.awt.event.*;
import java.awt.*;

public class frmMainMenu implements ActionListener {
	JFrame f = new JFrame("Main Menu");
	JMenu menuEE, menuHELP, submenu;
	JMenuItem i1, i2, i3, i4, i5;
	Font f1 = new Font("Consolas", Font.BOLD, 13);
	Font f2 = new Font("Consolas", Font.BOLD, 16);
	Font lbl = new Font("Consolas", Font.BOLD, 15);
	Color c1 = Color.WHITE;
	Color c2 = Color.DARK_GRAY;
	Color c3 = Color.GRAY;
	Color c4 = Color.BLUE;
	Color c5 = Color.RED;

	public frmMainMenu() {
		JMenuBar mb = new JMenuBar();

		mb.add(menuEE = new JMenu("Employee"));
		menuEE.add(i1 = new JMenuItem("Employee Form"));
		menuEE.add(i2 = new JMenuItem("User Form"));
		menuEE.add(i3 = new JMenuItem("Exit"));

		menuEE.setMnemonic(KeyEvent.VK_Y);
		i1.setMnemonic(KeyEvent.VK_F);
		i2.setMnemonic(KeyEvent.VK_U);
		i3.setMnemonic(KeyEvent.VK_X);

		mb.add(menuHELP = new JMenu("Help"));
		menuHELP.add(submenu = new JMenu("Sub Menu"));
		submenu.add(i4 = new JMenuItem("About"));
		submenu.add(i5 = new JMenuItem("Help"));

		menuHELP.setMnemonic(KeyEvent.VK_H);
		submenu.setMnemonic(KeyEvent.VK_S);
		i4.setMnemonic(KeyEvent.VK_A);
		i5.setMnemonic(KeyEvent.VK_E);

		i1.addActionListener(this);
		i2.addActionListener(this);
		i3.addActionListener(this);

		IconFontSwing.register(FontAwesome.getIconFont());
		Icon icn_menuEE = IconFontSwing.buildIcon(FontAwesome.USERS, 12, c1);
		Icon icn_i1 = IconFontSwing.buildIcon(FontAwesome.WINDOW_MAXIMIZE, 12);
		Icon icn_i2 = IconFontSwing.buildIcon(FontAwesome.LIST, 12);
		Icon icn_i3 = IconFontSwing.buildIcon(FontAwesome.WINDOW_CLOSE_O, 12);
		Icon icn_help = IconFontSwing.buildIcon(FontAwesome.QUESTION_CIRCLE_O, 12, c1);

		mb.setBackground(c3);
		menuEE.setForeground(c1);
		menuHELP.setForeground(c1);

		menuEE.setIcon(icn_menuEE);
		i1.setIcon(icn_i1);
		i2.setIcon(icn_i2);
		i3.setIcon(icn_i3);
		menuHELP.setIcon(icn_help);
		menuEE.setFont(f2);
		menuHELP.setFont(f2);
		submenu.setFont(f2);
		i1.setFont(f2);
		i2.setFont(f2);
		i3.setFont(f2);
		i4.setFont(f2);
		i5.setFont(f2);
		f.setContentPane(new JLabel(new ImageIcon("img/bg.jpg")));
		f.setJMenuBar(mb);
		f.setLayout(null);
		f.setVisible(true);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public void actionPerformed(ActionEvent e) {   
		if (e.getSource()==i1) {    
			frmEE eeform = new frmEE();    
			eeform.setTitle("Employee Form");    
			eeform.setSize(735,450);    
			eeform.setVisible(true); 
			eeform.setLocationRelativeTo(null);    
			eeform.setResizable(false);   
			}   
		if(e.getSource()==i2) {    
			frmReg userfrm = new frmReg();    
			userfrm.setTitle("User Registration Form");    
			userfrm.setSize(735,450);    userfrm.setVisible(true);    
			userfrm.setLocationRelativeTo(null);    
			userfrm.setResizable(false);   
			}      
		if(e.getSource()==i3) {    
			int dialog = JOptionPane.showConfirmDialog (null, "Are you sure you want to EXIT?","WARNING",JOptionPane.YES_NO_OPTION);    
			if(dialog == JOptionPane.YES_OPTION) {     System.exit(0);    
			}    
			if(dialog == JOptionPane.NO_OPTION){     
				f.setLayout(null);          
				f.setVisible(true);         
				f.setExtendedState(JFrame.MAXIMIZED_BOTH);     
				}      
			}  
		} 
	 
	 public static void main(String[] args) {   
		 new frmMainMenu();    
		 } 
	 } 
