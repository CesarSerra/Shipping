package com.java102.cesar;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ShippingGUI extends JFrame {
	private JTextField idJTextField;
	private JTextField nameJTextField;
	private JTextField addressJTextField;
	private JTextField cityJTextField;
	private JTextField stateJTextField;
	private JTextField zipJTextField;
	private JTextField timeJTextField;

	private String FILENAME;
	private String CHOSENFILE;
	private ArrayList<Parcel> parcels = null;
	private String[] stateArray = null;
	private int count = 0;

	
	private JTextArea textArea;
	private JComboBox comboBox;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShippingGUI frame = new ShippingGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	

	public ShippingGUI() {

		setIconImage(Toolkit.getDefaultToolkit().getImage(
				ShippingGUI.class
						.getResource("/com/java102/cesar/shipping.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 825, 473);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Component frame = null;
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"XML File", "XML", "XML");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(new JPanel());
				File chosenFile = null;
				if (returnVal == JFileChooser.APPROVE_OPTION) 
				{
					chosenFile = chooser.getSelectedFile();
					CHOSENFILE = chosenFile.toString();
					System.out.println(chosenFile.toString());
					parcels = ReadXML.readFromXML(chosenFile.toString());
					System.out.println(parcels.size());
					idJTextField.setText(Integer.toString(parcels.get(count)
							.getID()));
					nameJTextField.setText(parcels.get(count).getPerson()
							.getName());
					addressJTextField.setText(parcels.get(count).getPerson()
							.getAddress().getAddress());
					cityJTextField.setText(parcels.get(count).getPerson()
							.getAddress().getCity());
					stateJTextField.setText(parcels.get(count).getPerson()
							.getAddress().getState());
					zipJTextField.setText(parcels.get(count).getPerson()
							.getAddress().getZip());
					timeJTextField.setText(parcels.get(count).getPerson()
							.getDate()
							+ " " + parcels.get(count).getTime());
				}
				if (returnVal == JFileChooser.CANCEL_OPTION) {
					if (chosenFile == null) {
						JOptionPane.showMessageDialog(frame,
								"No File Selected", "File Error",
								JOptionPane.WARNING_MESSAGE);
					}
				}
				updateCombo();
			}
		});
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				InputEvent.CTRL_MASK));
		mnFile.add(mntmOpen);

		JMenuItem saveJMenuItem = new JMenuItem("Save");
		saveJMenuItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				saveToFile();
				
			}
		});
		saveJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(saveJMenuItem);

		JMenuItem mntmPrint = new JMenuItem("Print");
		mntmPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				InputEvent.CTRL_MASK));
		mnFile.add(mntmPrint);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				InputEvent.CTRL_MASK));
		mnFile.add(mntmExit);

		JMenu mnAction = new JMenu("Action");
		menuBar.add(mnAction);

		JMenuItem mntmScanNew = new JMenuItem("Scan New");
		mntmScanNew.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				addGUI();
			}
		});
		mnAction.add(mntmScanNew);

		JMenuItem mntmRemove = new JMenuItem("Remove");
		mnAction.add(mntmRemove);

		JMenuItem mntmEdit = new JMenuItem("Edit");
		mnAction.add(mntmEdit);

		JMenuItem mntmBack = new JMenuItem("Back");
		mnAction.add(mntmBack);

		JMenuItem mntmNext = new JMenuItem("Next");
		mnAction.add(mntmNext);

		JMenu mnSearch = new JMenu("Search");
		mnAction.add(mnSearch);

		JMenuItem mntmId = new JMenuItem("ID");
		mnSearch.add(mntmId);

		JMenuItem mntmName = new JMenuItem("Name");
		mntmName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchByNameDialog();
			}
		});
		mnSearch.add(mntmName);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		getContentPane().setLayout(null);

		JPanel parcelJPanel = new JPanel();
		parcelJPanel.setBorder(new TitledBorder(null, "Parcel Information",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		parcelJPanel.setBounds(12, 148, 600, 190);
		getContentPane().add(parcelJPanel);
		parcelJPanel.setLayout(null);

		JLabel idJLabel = new JLabel("Parcel ID:");
		idJLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		idJLabel.setBounds(12, 28, 71, 22);
		parcelJPanel.add(idJLabel);

		idJTextField = new JTextField();
		idJTextField.setEditable(false);
		idJTextField.setBounds(90, 28, 486, 22);
		parcelJPanel.add(idJTextField);
		idJTextField.setColumns(10);

		JLabel nameJLabel = new JLabel("Name:");
		nameJLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nameJLabel.setBounds(12, 63, 71, 22);
		parcelJPanel.add(nameJLabel);

		nameJTextField = new JTextField();
		nameJTextField.setEditable(false);
		nameJTextField.setBounds(90, 63, 486, 22);
		nameJTextField.setColumns(10);
		parcelJPanel.add(nameJTextField);

		JLabel addressjLabel = new JLabel("Address:");
		addressjLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		addressjLabel.setBounds(12, 98, 71, 22);
		parcelJPanel.add(addressjLabel);

		addressJTextField = new JTextField();
		addressJTextField.setEditable(false);
		addressJTextField.setBounds(90, 98, 486, 22);
		addressJTextField.setColumns(10);
		parcelJPanel.add(addressJTextField);

		JLabel cityJLabel = new JLabel("City:");
		cityJLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cityJLabel.setBounds(12, 133, 71, 22);
		parcelJPanel.add(cityJLabel);

		cityJTextField = new JTextField();
		cityJTextField.setEditable(false);
		cityJTextField.setColumns(10);
		cityJTextField.setBounds(90, 133, 139, 22);
		parcelJPanel.add(cityJTextField);

		JLabel stateJLabel = new JLabel("State:");
		stateJLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		stateJLabel.setBounds(248, 133, 71, 22);
		parcelJPanel.add(stateJLabel);

		stateJTextField = new JTextField();
		stateJTextField.setEditable(false);
		stateJTextField.setColumns(10);
		stateJTextField.setBounds(295, 133, 71, 22);
		parcelJPanel.add(stateJTextField);

		JLabel zipJLabel = new JLabel("Zip:");
		zipJLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		zipJLabel.setBounds(405, 133, 35, 22);
		parcelJPanel.add(zipJLabel);

		zipJTextField = new JTextField();
		zipJTextField.setEditable(false);
		zipJTextField.setColumns(10);
		zipJTextField.setBounds(437, 134, 139, 22);
		parcelJPanel.add(zipJTextField);

		JPanel stateJPanel = new JPanel();
		stateJPanel.setBorder(new TitledBorder(null, "Parcel By State",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		stateJPanel.setBounds(624, 148, 181, 190);
		getContentPane().add(stateJPanel);
		stateJPanel.setLayout(null);

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("src/Files/state.txt"));
			String line = br.readLine();
			StringBuilder states = new StringBuilder();
			while (line != null) {
				line = line.trim();
				if (!line.isEmpty())
					states.append(line).append(";");
				line = br.readLine();
			}
			stateArray = states.substring(0, states.length() - 1).split(";");
			br.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		comboBox = new JComboBox(stateArray);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateCombo();
			}
		});
		comboBox.setBounds(12, 32, 157, 24);
		stateJPanel.add(comboBox);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(12, 69, 157, 108);
		stateJPanel.add(textArea);

		JLabel arriveJLabel = new JLabel("Arrived at:");
		arriveJLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		arriveJLabel.setBounds(41, 57, 81, 32);
		getContentPane().add(arriveJLabel);

		timeJTextField = new JTextField();
		timeJTextField.setEditable(false);
		timeJTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		timeJTextField.setBounds(149, 57, 345, 32);
		getContentPane().add(timeJTextField);
		timeJTextField.setColumns(10);

		JPanel buttonJPanel = new JPanel();
		buttonJPanel.setBounds(12, 351, 793, 52);
		getContentPane().add(buttonJPanel);
		buttonJPanel.setLayout(new GridLayout(0, 6, 0, 0));

		JButton scanJButton = new JButton("Scan New");
		scanJButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				addGUI();
			}


		});
		scanJButton.setMnemonic('s');
		buttonJPanel.add(scanJButton);

		JButton removeJButton = new JButton("Remove");
		removeJButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeParcel();
			}
		});
		removeJButton.setMnemonic('r');
		buttonJPanel.add(removeJButton);

		JButton editJButton = new JButton("Edit");
		editJButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editParcel();
			}
		});
		editJButton.setMnemonic('e');
		buttonJPanel.add(editJButton);

		JButton searchJButton = new JButton("Search");
		searchJButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				searchByIDDialog();
				
				
			}
		});
		searchJButton.setMnemonic('h');
		buttonJPanel.add(searchJButton);

		JButton backJButton = new JButton("< Back");
		backJButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count--;
				if (count < 0) {
					count = (parcels.size() - 1);
				}
				updateGUI();
			}
		});
		backJButton.setMnemonic('b');
		buttonJPanel.add(backJButton);

		JButton nextJButton = new JButton("Next >");
		nextJButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				count++;
				if (count >= parcels.size()) {
					count = 0;
				}
				updateGUI();
			}

		});

		nextJButton.setMnemonic('n');
		buttonJPanel.add(nextJButton);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ShippingGUI.class
				.getResource("/com/java102/cesar/f6f1a7763786.png")));
		lblNewLabel.setBounds(644, 13, 161, 135);
		getContentPane().add(lblNewLabel);
	}

	protected void saveToFile()
	{
		ReadXML.commitToFile(parcels, CHOSENFILE);
	}



	protected void searchByNameDialog() {
	
		if(parcels == null)
		{
			displayNoDataDialog();
			return;
		}
		
		String name = JOptionPane.showInputDialog(this, "Enter last name to search for.", "");
		name.trim();
		int index = -1;
		for (int i = 0; i < parcels.size(); ++i) {
			final Parcel p = parcels.get(i);
			if (p.getPerson().getLastName().equalsIgnoreCase(name)) {
				index = i;
				break;
			}
		}
		
		if (index < 0) {
			JOptionPane.showMessageDialog(this, "Parcel not found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		count = index;
		updateGUI();
		
	}



	protected void removeParcel() {
		
		parcels.remove(count);
		if (count >= parcels.size()) {
			count--;
		}
		updateGUI();
	}



	protected void editParcel() {
		
		if (parcels == null) {
			displayNoDataDialog();
			return;
		}
		AddGUI dialog = new AddGUI(this, parcels.get(count));
		dialog.setVisible(true);
		dialog.requestFocus();
	}



	protected void searchByIDDialog() {
		if(parcels == null)
		{
			displayNoDataDialog();
			return;
		}
		
		String IDStr = JOptionPane.showInputDialog(this, "Enter ID to search for.", "");
		int ID = -1;
		try {
		 ID = Integer.parseInt(IDStr);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Invalid ID", "Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		
		int index = searchByID(ID);
		
		if (index < 0) {
			JOptionPane.showMessageDialog(this, "Parcel not found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		count = index;
		updateGUI();
	}
	
	
	protected int searchByID(int id) {
		for (int i = 0; i < parcels.size(); ++i) {
			final Parcel p = parcels.get(i);
			if (p.getID() == id) {
				return i;
			}
		}
		return -1;
	}



	private void updateGUI() {
		idJTextField.setText(Integer.toString(parcels.get(count).getID()));
		nameJTextField.setText(parcels.get(count).getPerson().getName());
		addressJTextField.setText(parcels.get(count).getPerson().getAddress()
				.getAddress());
		cityJTextField.setText(parcels.get(count).getPerson().getAddress()
				.getCity());
		stateJTextField.setText(parcels.get(count).getPerson().getAddress()
				.getState());
		zipJTextField.setText(parcels.get(count).getPerson().getAddress()
				.getZip());
		timeJTextField.setText(parcels.get(count).getPerson().getDate() + " "
				+ parcels.get(count).getTime());
	}

	private void updateCombo() 
	{
		if (parcels == null) {
			displayNoDataDialog();
			return;
		}

		int selectedIndex = comboBox.getSelectedIndex();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parcels.size(); i++) {
			if (stateArray[selectedIndex].equalsIgnoreCase(parcels.get(i).getPerson().getAddress().getState())) {
				sb.append(parcels.get(i).getID());
				sb.append("\n");
			} 
		}
		textArea.setText(sb.toString());
	}

	private void displayNoDataDialog() 
	{
		JOptionPane.showMessageDialog(this, "No File Loaded", "No Data", JOptionPane.ERROR_MESSAGE);
		
	}
	private void addGUI() 
	{
		if (parcels == null) {
			displayNoDataDialog();
			return;
		}
		AddGUI dialog = new AddGUI(this);
		dialog.setVisible(true);
		dialog.requestFocus();
	}
	
	protected int getNextGUID() {
		
		return parcels.get(parcels.size()-1).getID() + 1;
	}

	protected void addParcel(Parcel parcel) {
		if (parcel != null && parcels != null) {
			parcels.add(parcel);
			updateGUI();
		}
		
	}
	
	protected void setSelectedIndex(int index) {
		if (index > 0 && index < parcels.size()) {
			count = index;
			updateGUI();
		}
	}
}
