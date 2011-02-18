package dk.aau.cs.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import dk.aau.cs.gui.SharedPlacesAndTransitionsPanel.SharedPlacesListModel;
import dk.aau.cs.model.tapn.SharedPlace;
import dk.aau.cs.util.RequireException;

public class SharedPlaceNamePanel extends JPanel {
	private static final long serialVersionUID = -8099814326394422263L;

	private final JRootPane rootPane;
	private final SharedPlacesListModel listModel;
	private JTextField nameField;

	public SharedPlaceNamePanel(JRootPane rootPane, SharedPlacesListModel SharedPlacesListModel) {
		this.rootPane = rootPane;
		this.listModel = SharedPlacesListModel;
		initComponents();		
	}
	
	public void initComponents(){
		setLayout(new BorderLayout());
		
		JPanel namePanel = createNamePanel();
		JPanel buttonPanel = createButtonPanel();
		
		add(namePanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.PAGE_END);
	}

	private JPanel createNamePanel() {
		JPanel namePanel = new JPanel(new GridBagLayout());
		
		JLabel label = new JLabel("Please enter a name:");
		GridBagConstraints gbc = new GridBagConstraints();
		namePanel.add(label, gbc);
		
		nameField = new JTextField();
		nameField.setMinimumSize(new Dimension(100,27));
		nameField.setPreferredSize(new Dimension(150, 27));
		gbc = new GridBagConstraints();
		gbc.gridy = 1;
		namePanel.add(nameField, gbc);
		return namePanel;
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		
		JButton okButton = new JButton("OK");
		rootPane.setDefaultButton(okButton);
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String name = nameField.getText();
				if(name == null || name.isEmpty()){
					JOptionPane.showMessageDialog(SharedPlaceNamePanel.this, "You must specify a name.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}else{
					try{
						listModel.addElement(new SharedPlace(name));
						exit();
					}catch(RequireException e){
						JOptionPane.showMessageDialog(SharedPlaceNamePanel.this, "A Transition or place already exists with the specified name.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		return buttonPanel;
	}

	private void exit() {
		rootPane.getParent().setVisible(false);
	}

}

