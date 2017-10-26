package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JCheckBoxMenuItem;

import dao.DAOFactory;

import javax.swing.UIManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;

public class MainFrame implements ActionListener {

	private JFrame frame;
	private ConnexionFrame cnx_frame;
	private JMenuBar menuBar;
	private JMenu submenu_db, file_menu;
	private JMenuItem mntmConnect; 
	private JTable table;
	private JTextArea ta_sql_req;
	private JMenuItem mntmExit;
	private JSeparator separator;
	private JCheckBoxMenuItem chckbxmntmAutocommit;

	private Connection cnx; 
	private JMenuItem mntmCommit;
	private JMenuItem mntmRollback;
	private JMenuItem mntmVersion;
	private JMenuItem mntmReset;

	private List<String> alDB;
	private List<JMenuItem> alItemsDB;
	private List<String> alLogs;
	
	private int row_count = 0;
	private SouthPanel sp;
	private String[] columnNames;
	private Object[][] data;
	private JScrollPane jScroll_db;
	
	private boolean isConnected=false, autocommit = true;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	/**
	 * Create the application.
	 */
	public MainFrame() {
		
		this.alDB = new ArrayList<String>();
		this.alItemsDB = new ArrayList<JMenuItem>();
		this.alLogs = new ArrayList<String>();
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("JDBC Swing App");
		frame.setBackground(Color.CYAN);
		frame.setBounds(100, 100, 985, 648);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuBar = new JMenuBar();
		
		frame.setJMenuBar(menuBar);

		file_menu = new JMenu("File");
		submenu_db = new JMenu("Select database");
		menuBar.add(file_menu);
		file_menu.add(submenu_db);

		
		mntmConnect = new JMenuItem("Connect...");
		mntmConnect.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_C, ActionEvent.ALT_MASK));
		file_menu.add(mntmConnect);

		//a submenu
		file_menu.addSeparator();

		chckbxmntmAutocommit = new JCheckBoxMenuItem("Autocommit");
		chckbxmntmAutocommit.setSelected(autocommit);

		file_menu.add(chckbxmntmAutocommit);

		mntmCommit = new JMenuItem("Commit");
		mntmCommit.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_C, ActionEvent.SHIFT_MASK));
		file_menu.add(mntmCommit);

		mntmRollback = new JMenuItem("Rollback");
		file_menu.add(mntmRollback);
		mntmRollback.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_R, ActionEvent.SHIFT_MASK));
		

		separator = new JSeparator();
		file_menu.add(separator);

		mntmReset = new JMenuItem("Reset");
		file_menu.add(mntmReset);
		mntmReset.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_R, ActionEvent.ALT_MASK));
		

		mntmVersion = new JMenuItem("Version");
		file_menu.add(mntmVersion);

		mntmExit = new JMenuItem("Exit");
		file_menu.add(mntmExit);
		//mntmExit.setMnemonic(KeyEvent.VK_E);
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_E, ActionEvent.ALT_MASK));



		table = new JTable();
		jScroll_db = new JScrollPane(table);
		frame.getContentPane().add(jScroll_db, BorderLayout.CENTER);

		sp = new SouthPanel();
		frame.getContentPane().add(sp, BorderLayout.SOUTH);
		
		
		
		
		
		
		if (isConnected == false){
			submenu_db.setEnabled(false);
			mntmCommit.setEnabled(false);
			mntmRollback.setEnabled(false);
			mntmVersion.setEnabled(false);
			chckbxmntmAutocommit.setEnabled(false);
		}

		if(chckbxmntmAutocommit.isSelected() == true) {
			mntmCommit.setEnabled(false);
			mntmRollback.setEnabled(false);
		}
		
		
		mntmConnect.addActionListener(this);
		mntmExit.addActionListener(this);
		mntmCommit.addActionListener(this);
		chckbxmntmAutocommit.addActionListener(this);
		mntmReset.addActionListener(this);
		mntmVersion.addActionListener(this);
		mntmRollback.addActionListener(this);
		sp.getBtnExec().addActionListener(this);
		
	}

	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource() == mntmConnect) {
			connect();
		}
		

		else if (event.getSource() == mntmExit){

			if(cnx != null) {
				try {
					cnx.close();
				}
				catch(Exception e) {
					alLogs.add("Erreur de fermeture de la connection " + e.getMessage() + "\n");
				}
			}
			frame.dispose();
			alLogs.add("Connection fermée.\n");
		}
		else if (event.getSource() == mntmCommit) {
			commit();
		}
		else if (event.getSource() == mntmReset) {
			reset();
		}
		else if (event.getSource() == chckbxmntmAutocommit) {
			autoCommit();
		}
		else if (event.getSource() == mntmRollback) {
			rollback();
		}
		else if (event.getSource() == mntmVersion) {
			showVersion();
		}
		else if ( alItemsDB.contains(event.getSource())) {
			
			processDB(event.getActionCommand());
		}
		else if(event.getSource() == sp.getBtnExec()) {
			
			this.go();
		}

		populateLogs();
	}

	// Commit
	public void commit() {
		if(cnx != null) {
			try {
				cnx.commit();
				alLogs.add("Commit réussi.");
			}
			catch(SQLException e) {
				alLogs.add("Erreur lors du commit : "+e.getMessage());
			}
			finally{
				reset();
			}
		}
	}

	// Reset
	public void reset() {
		//buffer = new StringBuffer();
		sp.getTxtEditor().setText("");
		//line = 1;
	}

	// Affichage des meta donnees sur la base
	public void showVersion() {
		if(cnx != null){
			try {
				DatabaseMetaData meta = cnx.getMetaData();
				alLogs.add("TerminalMonitor v2.0");
				alLogs.add("DBMS : "+meta.getDatabaseProductName()+" "+meta.getDatabaseProductVersion());
				alLogs.add("JDBC Driver : "+meta.getDriverName()+" "+meta.getDriverVersion());
			}
			catch(SQLException e) {
				alLogs.add("Impossible d'obtenir les infos de version :"+e.getMessage());
			}
			finally{reset();}
		}
	}

	// Active ou desactive le mode autocommit
	public void autoCommit() {

		if(chckbxmntmAutocommit.isSelected()) {

			mntmCommit.setEnabled(false);
			mntmRollback.setEnabled(false);
			autocommit = true;	
		}
		else {
			mntmCommit.setEnabled(true);
			mntmRollback.setEnabled(true);
			autocommit = false;
		}

		if(cnx != null) {
			try {

				cnx.setAutoCommit(autocommit);
				alLogs.add("Mode autocommit : "+autocommit);
			}
			catch(SQLException e) {alLogs.add("Erreur : "+e.getMessage());}
			finally{reset();}
		}
	}	

	// Rollback
	public void rollback() {
		if(cnx != null) {
			try {
				cnx.rollback();
				alLogs.add("Rollback réussi.");
			}
			catch(SQLException e) {alLogs.add("Erreur lors du rollback : "+e.getMessage());}
			finally{reset();}
		}
	}
//test git
	/**
	 * Initialisation de la connection à la base de données
	 */
	public void connect() {

		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				
			}
		})*/;
		
		try {

			cnx_frame = new ConnexionFrame(this);
			cnx = cnx_frame.getConnexion();
			
			//cnx = new DAOFactory("jdbc:mysql://localhost:3306/", "root", "").getConnexion();
			

		} catch (Exception e) {
			alLogs.add("Erreur lors de la tentative de connexion : " + e.getMessage());
		}
		
	}
	
	public void login(Connection cnx){
		
		isConnected = true;
		submenu_db.setEnabled(true);
		mntmCommit.setEnabled(!chckbxmntmAutocommit.isSelected());
		mntmRollback.setEnabled(!chckbxmntmAutocommit.isSelected());
		mntmVersion.setEnabled(true);
		chckbxmntmAutocommit.setEnabled(true);
		
		this.cnx = cnx;
		
		try {
			DatabaseMetaData dbmd = cnx.getMetaData(); 
			ResultSet ctlgs = dbmd.getCatalogs();
			
			while (ctlgs.next())
			{
				alDB.add(ctlgs.getString(1));
				alItemsDB.add(new JMenuItem(ctlgs.getString(1)));
				//submenu_db.add(new JMenuItem(ctlgs.getString(1)));
				
			}
			submenu_db.setEnabled(true);
			
		} catch (SQLException e) {
			alLogs.add("Erreur survenue lors de la récupération des BDD existantes : " + e.getMessage());
		}
		
		for(JMenuItem item : alItemsDB) {
			
			item.addActionListener(this);
		
			submenu_db.add(item);
		}
	}
	
	public void processDB(String db_name) {
		
		try {
			cnx.close();
		}catch(SQLException e){
			e.printStackTrace();
			alLogs.add("Erreur lors de la fermeture de la connexion." + e.getMessage() );
		}
		
		cnx = new DAOFactory("jdbc:mysql://localhost:3306/" + db_name, "root", "").getConnexion();
		alLogs.add("Vous êtes maintenant connecté à la BDD " + db_name);
	}
	
	public void executeStatement(String buff) throws SQLException {
		String sql = buff;
		alLogs.add("Exec "+sql);
		Statement statement = null;
		try {
			statement = cnx.createStatement();
			if(statement.execute(sql)) { // true si retourne un ResultSet
				processResults(statement.getResultSet());	
			}
		}
		catch(SQLException e) {
			throw e;
		}
		finally {
			if(statement != null) statement.close();
		}
	}
	
	// Affichage formatté du ResultSet
		public void processResults(ResultSet results) throws SQLException {
			try {
				ResultSetMetaData meta = results.getMetaData();
				StringBuffer bar = new StringBuffer();
				StringBuffer buffer = new StringBuffer();
				int cols = meta.getColumnCount();
				row_count = 0; // Initialisation du nombre de lignes a afficher
				int i, width = 0;
				columnNames = new String[cols];
				for( i=1; i<=cols; i++) {
					columnNames[i-1] = meta.getColumnLabel(i);
					//System.out.println(meta.getColumnClassName(i));
				}
				ArrayList<Object> alO = new ArrayList<Object>();
				
				while(results.next()) {
					row_count++;
					
					// Formatter chaque colonne de la ligne
					for( i=1; i<=cols; i++) {
						Object value = results.getObject(i);
						String str;
						if(results.wasNull()) str = "NULL";
						else str = value.toString();
						
						//System.out.print(str + " ");
						alO.add(str);
					}
					//System.out.println();
				}
				//System.out.println(alO);
				data = new String[row_count][cols];
				int row_items=0;
				for( i=1; i<=row_count; i++) {
					for(int j=1; j<=cols; j++) {
						data[i-1][j-1] = alO.get(j+row_items-1);
						//System.out.print("[" + (i-1) + "][" + (j-1) + "] =" + alO.get(j+item-1) + "		");
					}
					row_items += cols;
					//System.out.println();
				}
				
				frame.getContentPane().remove(jScroll_db);
				table = new JTable(data, columnNames);
				table.setAutoscrolls(true);
				jScroll_db = new JScrollPane(table);
				
				DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			    render.setHorizontalAlignment(SwingConstants.CENTER);

			    for ( i=0; i < table.getColumnModel().getColumnCount() ; i++)
			    	table.getColumnModel().getColumn(i).setCellRenderer(render);
				
			    frame.getContentPane().add(jScroll_db, BorderLayout.CENTER);
				
				
			}
			catch(SQLException e) {throw e;}
			finally{
				try{results.close(); }
				catch(SQLException e) {}
			}
		}

//		/**
//		 * Affichage de l'en-tête
//		 */		
//		public StringBuffer affEnTete(ResultSetMetaData meta) throws SQLException {
//			StringBuffer result = new StringBuffer();
//			StringBuffer filler; // Pour remplir avec de sespaces blancs
//			String label; // Label de la colonne fomattŽ
//			int size; // Taille max en nombre de caract?res de la colonne
//			int cols = meta.getColumnCount();		
//			for(int i=1; i<=cols; i++) {
//				filler = new StringBuffer();
//				label = meta.getColumnLabel(i);
//				size = meta.getColumnDisplaySize(i);
//				//System.out.println(label+" "+size);
//				int x;
//				// Si le titre est plus long que la largeur de la colonne
//				if(label.length() > size ) {
//					label = label.substring(0, size);
//				}
//				// Si le titre est moins long que la largeur de la colonne
//				if(label.length() < size) {
//					int j;
//					x = (size-label.length())/2;
//					for(j=0; j<x; j++) {
//						filler.append(' ');
//					}
//					label = filler + label + filler;
//					if(label.length() > size) {
//						label = label.substring(0, size);
//					}
//					else {			
//						while(label.length() < size) {
//							label += " ";
//						}
//					}
//				}
//				result.append(label + "|");
//			}//fin for
//			return result;
//		} // fin affEnTete
//
//		/**
//		 * Formatter chaque ligne de l'ensemble rŽsultat
//		 */
//		public StringBuffer affLignes(ResultSet results, int cols) throws SQLException {
//			ResultSetMetaData meta = results.getMetaData();
//			StringBuffer buffer = new StringBuffer();
//			while(results.next()) {
//				row_count++;
//				buffer.append('|');
//				// Formatter chaque colonne de la ligne
//				for(int i=1; i<=cols; i++) {
//					StringBuffer filler = new StringBuffer();
//					Object value = results.getObject(i);
//					int size = meta.getColumnDisplaySize(i);
//					String str;
//					if(results.wasNull()) str = "NULL";
//					else str = value.toString();
//					if(str.length() > size) str = str.substring(0,size);
//					if(str.length() < size) {
//						int x = (size-str.length())/2;
//						for(int j=0; j < x ; j++) filler.append(' ');
//						str = filler + str + filler;
//						if(str.length() > size) str = str.substring(0, size);
//						else while(str.length() < size) str += " ";
//					}
//					buffer.append(str + "|");
//				}
//				buffer.append("\n");
//			}
//			return buffer;
//		}
		
		// Execution du tampon courant
		public void go() {
			if(! sp.getTxtEditor().getText().equals("")) {
				try {
					executeStatement(sp.getTxtEditor().getText());
				}
				catch(SQLException e) {
					alLogs.add(e.getMessage());
				}
				finally{reset();}
			}
			reset();
			
		}
		
		private void populateLogs() {
			
			StringBuilder sbLogs = new StringBuilder();
			//sbLogs.append(sp.getTxtLogs().getText());
			for(String error : alLogs)
				sbLogs.append(error).append("\n");
			
			sp.getTxtLogs().setText(sbLogs.toString());
		}
}
