package ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import dao.DAOFactory;

public class ConnexionFrame implements ActionListener {

	private JFrame frame;
	private JTextField tf_usr;
	private JPasswordField tf_pwd;

	private JButton btn_login, btn_cancel;

	private String usr, pwd, select_db;

	private Connection connexion;
	private MainFrame mf;
	
	/**
	 * Create the application.
	 */
	public ConnexionFrame(MainFrame mf) {
		this.mf = mf;
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblUsername = new JLabel("Username :");
		JLabel lblPassword = new JLabel("Password :");

		tf_usr = new JTextField();
		tf_usr.setToolTipText("Please type your username");
		tf_usr.setColumns(10);

		tf_pwd = new JPasswordField();
		tf_pwd.setToolTipText("Please type your password");

		btn_login = new JButton("Log In");
		btn_cancel = new JButton("Cancel");

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(25)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblPassword)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblUsername)
							.addGap(65)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btn_login, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(tf_usr, Alignment.LEADING)
								.addComponent(tf_pwd, Alignment.LEADING))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
							.addGap(36))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(37)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsername)
						.addComponent(tf_usr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPassword)
						.addComponent(tf_pwd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_login)
						.addComponent(btn_cancel))
					.addContainerGap(108, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);

		btn_login.addActionListener(this);
	}

	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub

		if(event.getSource() == btn_login) {
			this.usr = this.tf_usr.getText();
			this.pwd = this.tf_pwd.getText();

			//System.out.println("User : " + usr + "\nPassword : " + pwd);

			connexion = new DAOFactory("jdbc:mysql://localhost:3306/", usr, pwd).getConnexion();

//			try {
//				DatabaseMetaData dbmd = connexion.getMetaData(); 
//				ResultSet ctlgs = dbmd.getCatalogs();
//				cb_db.removeAllItems();
//				cb_db.addItem("None");
//				cb_db.setSelectedIndex(0);
//				
//				cb_db.setEnabled(true);
//				tf_pwd.setEnabled(false);
//				tf_usr.setEnabled(false);
//
//				while (ctlgs.next())
//				{
//					cb_db.addItem(ctlgs.getString(1));
//				}
//			} catch (SQLException e) {
//
//			}
//			try {
//				connexion.close();
//			}
//			catch(Exception e) {
//				System.out.println("Erreur de fermeture de la connection " + e.getMessage());
//			}
//			System.out.println("Connection fermée");
			mf.login(connexion);
			this.frame.setVisible(false);
		}
		else if(event.getSource() == btn_cancel) {
			
			this.tf_usr.setText("");
			this.tf_pwd.setText("");
			this.frame.dispose();
		}
	}

	public Connection getConnexion() {
		return connexion;
	}
}
