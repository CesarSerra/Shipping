package com.java102.cesar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;

import java.awt.GridLayout;

import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.Toolkit;

import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ShippingGUI extends JFrame 
{
	private JTextField idJTextField;
	private JTextField nameJTextField;
	private JTextField addressJTextField;
	private JTextField cityJTextField;
	private JTextField stateJTextField;
	private JTextField zipJTextField;
	private JTextField timeJTextField;
	
	private String FILENAME;
	private ArrayList<Parcel> parcels = null;
	
	final Random random = new Random();
	final int millisInDay = 24*60*60*1000;
	private static final String FORMAT = "%02d:%02d:%02d";
	Time time = new Time((long)random.nextInt(millisInDay));
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					ShippingGUI frame = new ShippingGUI();
					frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	public static String parseTime(long milliseconds) 
	{
	      return String.format(FORMAT,
	              TimeUnit.MILLISECONDS.toHours(milliseconds),
	              TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(
	              TimeUnit.MILLISECONDS.toHours(milliseconds)),
	              TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(
	              TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
	}
	public ShippingGUI() 
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Elliott\\Documents\\Java102\\Shipping Hub\\src\\com\\java102\\cesar\\shipping.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 825, 473);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Component frame = null;
				JFileChooser chooser = new JFileChooser("Shipping//src//Files");
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "XML File", "XML", "XML");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(new JPanel());
				File chosenFile = null;
				if(returnVal == JFileChooser.APPROVE_OPTION) 
				{
					chosenFile = chooser.getSelectedFile();
					parcels = ReadXML.readFromXML(chosenFile.toString());
				}  
				if(returnVal == JFileChooser.CANCEL_OPTION)
				{
					chosenFile = null;
					JOptionPane.showMessageDialog(frame,
						    "File could not be found.",
						    "File Error",
						    JOptionPane.WARNING_MESSAGE);
				}
				
				idJTextField.setText(Integer.toString(parcels.get(0).getID()));
				nameJTextField.setText(parcels.get(0).getPerson().getName());
				addressJTextField.setText(parcels.get(0).getPerson().getAddress().getAddress());
				cityJTextField.setText(parcels.get(0).getPerson().getAddress().getCity());
				stateJTextField.setText(parcels.get(0).getPerson().getAddress().getState());
				zipJTextField.setText(parcels.get(0).getPerson().getAddress().getZip());
				timeJTextField.setText(parcels.get(0).getPerson().getDate() + " " + parcels.get(0).getTime());
	
			}
		});
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnFile.add(mntmOpen);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Clear");
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		mnFile.add(mntmNewMenuItem);
		
		JMenuItem mntmPrint = new JMenuItem("Print");
		mntmPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		mnFile.add(mntmPrint);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		mnFile.add(mntmExit);
		
		JMenu mnAction = new JMenu("Action");
		menuBar.add(mnAction);
		
		JMenuItem mntmScanNew = new JMenuItem("Scan New");
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
		mnSearch.add(mntmName);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		getContentPane().setLayout(null);
		
		JPanel parcelJPanel = new JPanel();
		parcelJPanel.setBorder(new TitledBorder(null, "Parcel Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		stateJPanel.setBorder(new TitledBorder(null, "Parcel By State", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		stateJPanel.setBounds(624, 148, 181, 190);
		getContentPane().add(stateJPanel);
		stateJPanel.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(12, 32, 157, 24);
		stateJPanel.add(comboBox);
		
		JTextArea textArea = new JTextArea();
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
		scanJButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
			}
		});
		scanJButton.setMnemonic('s');
		buttonJPanel.add(scanJButton);
		
		JButton removeJButton = new JButton("Remove");
		removeJButton.setMnemonic('r');
		buttonJPanel.add(removeJButton);
		
		JButton editJButton = new JButton("Edit");
		editJButton.setMnemonic('e');
		buttonJPanel.add(editJButton);
		
		JButton searchJButton = new JButton("Search");
		searchJButton.setMnemonic('h');
		buttonJPanel.add(searchJButton);
		
		JButton backJButton = new JButton("< Back");
		backJButton.setMnemonic('b');
		buttonJPanel.add(backJButton);
		
		JButton nextJButton = new JButton("Next >");
		nextJButton.setMnemonic('n');
		buttonJPanel.add(nextJButton);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ShippingGUI.class.getResource("/com/java102/cesar/f6f1a7763786.png")));
		lblNewLabel.setBounds(644, 13, 161, 135);
		getContentPane().add(lblNewLabel);
	}
}
