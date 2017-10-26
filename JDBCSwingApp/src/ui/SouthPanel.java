package ui;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SouthPanel extends JPanel {

	private JTextArea txtEditor;
	private JTextArea txtLogs;
	private JButton btnExec;
	private JButton btnAnnuler;
	
	/**
	 * Create the panel.
	 */
	public SouthPanel() {
		
		txtEditor = new JTextArea();
		txtEditor.setTabSize(80);
		txtEditor.setColumns(10);
		txtEditor.setAutoscrolls(true);
		
		JLabel lblInput = new JLabel("Input :");
		txtEditor.setToolTipText("Please type your requests here");
		
		btnExec = new JButton("Ex\u00E9cuter");		
		btnAnnuler = new JButton("Annuler");
		
		txtLogs = new JTextArea();
		txtLogs.setAutoscrolls(true);
		txtLogs.setEditable(false);
		
		JScrollPane scroll = new JScrollPane (txtLogs, 
				   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
		JLabel lblLogs = new JLabel("Logs :");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(3)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblInput)
						.addComponent(lblLogs))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtEditor, GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
						.addComponent(scroll, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnExec, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnAnnuler, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(94)
							.addComponent(lblInput))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(txtEditor, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnAnnuler, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
								.addComponent(btnExec, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
							.addGap(110))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(54)
							.addComponent(lblLogs)
							.addContainerGap())))
		);
		setLayout(groupLayout);

	}

	public JTextArea getTxtEditor() {
		return txtEditor;
	}

	public JButton getBtnExec() {
		return btnExec;
	}

	public JTextArea getTxtLogs() {
		return txtLogs;
	}

	public JButton getBtnAnnuler() {
		return btnAnnuler;
	}
	
	
}
