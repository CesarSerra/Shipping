package com.java102.cesar;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

public class AddGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox stateComboBox;
	private JTextField firstNameTextField;
	private JTextField addressTextField;
	private JTextField cityTextField;
	private JFormattedTextField zipFormattedTextField;
	private JTextField lastNameTextField;

	private boolean edit = false;
	private Parcel parcel;
	final int millisInDay = 24 * 60 * 60 * 1000;
	private static final String TIME_FORMAT = "%02d:%02d";

	private ShippingGUI parent;

	/**
	 * @wbp.parser.constructor
	 */
	public AddGUI(ShippingGUI gui) {
		parent = gui;
		init();
	}

	public AddGUI(ShippingGUI gui, Parcel parcel) {
		parent = gui;
		init();
		this.parcel = parcel;
		Person p = parcel.getPerson();
		Address a = p.getAddress();
		stateComboBox.setSelectedItem(a.getState());
		firstNameTextField.setText(p.getFirstName());
		addressTextField.setText(a.getAddress());
		cityTextField.setText(a.getCity());
		zipFormattedTextField.setText(a.getZip());
		lastNameTextField.setText(p.getLastName());
	}

	private void init() {
		setBounds(100, 100, 450, 306);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblAddNewParcel = new JLabel("Add New Parcel");
		lblAddNewParcel.setFont(new Font("Dialog", Font.BOLD, 17));
		lblAddNewParcel.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewParcel.setBounds(0, 12, 448, 32);
		contentPanel.add(lblAddNewParcel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Parcel Information",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 56, 426, 188);
		contentPanel.add(panel);
		panel.setLayout(null);

		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblFirstName.setBounds(12, 22, 103, 22);
		panel.add(lblFirstName);

		firstNameTextField = new JTextField();
		firstNameTextField.setColumns(10);
		firstNameTextField.setBounds(133, 22, 281, 22);
		panel.add(firstNameTextField);

		JLabel label_1 = new JLabel("Address:");
		label_1.setFont(new Font("Dialog", Font.PLAIN, 15));
		label_1.setBounds(12, 83, 71, 23);
		panel.add(label_1);

		addressTextField = new JTextField();
		addressTextField.setColumns(10);
		addressTextField.setBounds(90, 84, 324, 22);
		panel.add(addressTextField);

		JLabel label_2 = new JLabel("City:");
		label_2.setFont(new Font("Dialog", Font.PLAIN, 15));
		label_2.setBounds(12, 118, 71, 22);
		panel.add(label_2);

		cityTextField = new JTextField();
		cityTextField.setColumns(10);
		cityTextField.setBounds(90, 118, 324, 22);
		panel.add(cityTextField);

		JLabel label_3 = new JLabel("State:");
		label_3.setFont(new Font("Dialog", Font.PLAIN, 15));
		label_3.setBounds(12, 152, 71, 22);
		panel.add(label_3);

		JLabel label_4 = new JLabel("Zip:");
		label_4.setFont(new Font("Dialog", Font.PLAIN, 15));
		label_4.setBounds(177, 152, 28, 22);
		panel.add(label_4);

		String[] stateArray = null;
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
		stateComboBox = new JComboBox(stateArray);
		stateComboBox.setBounds(90, 152, 52, 24);
		panel.add(stateComboBox);

		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter("#####");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (formatter != null) {
			zipFormattedTextField = new JFormattedTextField(formatter);
		} else {
			zipFormattedTextField = new JFormattedTextField();
		}
		zipFormattedTextField.setColumns(20);
		zipFormattedTextField.setBounds(213, 153, 77, 23);

		panel.add(zipFormattedTextField);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblLastName.setBounds(12, 56, 92, 15);
		panel.add(lblLastName);

		lastNameTextField = new JTextField();
		lastNameTextField.setBounds(133, 54, 281, 22);
		panel.add(lastNameTextField);
		lastNameTextField.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						okActionPreformed();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelActionPreformed();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected void cancelActionPreformed() {
		dispose();
	}

	protected void okActionPreformed() {

		String state = stateComboBox.getSelectedItem().toString().trim();
		String firstName = firstNameTextField.getText().trim();
		String lastName = lastNameTextField.getText().trim();
		String addressStr = addressTextField.getText().trim();
		String city = cityTextField.getText().trim();
		String zip = zipFormattedTextField.getText().trim();

		if (state.isEmpty() || firstName.isEmpty() || lastName.isEmpty()
				|| addressStr.isEmpty() || city.isEmpty() || zip.isEmpty()) {
			JOptionPane.showMessageDialog(this,
					"All fields must be filled out.", "Input Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (edit) {
			
			Person p = parcel.getPerson();
			Address a = p.getAddress();
			
			a.setAddress(addressStr);
			a.setCity(city);
			a.setState(state);
			a.setZip(zip);
			
			p.setFirstName(firstName);
			p.setLastName(lastName);
			
			parent.setSelectedIndex(parent.searchByID(parcel.getID()));

		} else {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Address address = new Address(addressStr, city, state, zip);
			Person person = new Person(firstName, lastName,
					dateFormat.format(cal.getTime()), address);

			int ID = parent.getNextGUID();
			Parcel parcel = new Parcel(ID,
					parseTime((long) (Math.random() * millisInDay)), person);
			parent.addParcel(parcel);
		
			parent.setSelectedIndex(parent.searchByID(ID));
		}
		
		dispose();
	}

	public static String parseTime(long milliseconds) {
		return String.format(
				TIME_FORMAT,
				TimeUnit.MILLISECONDS.toHours(milliseconds),
				TimeUnit.MILLISECONDS.toMinutes(milliseconds)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
								.toHours(milliseconds)),
				TimeUnit.MILLISECONDS.toSeconds(milliseconds)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes(milliseconds)));
	}

}
