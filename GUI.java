package LZSS;
	/**
	 * Final Project
	 * Submitted by: 
	 * Student 1.Samar Abu Hdeeb 	ID# 211985957
	 * Student 2.Mayar Altalalka    ID# 207243619
	 * Student 3.Fardous Abu Zaid    ID# 211965108
	 */

import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JButton;

import java.awt.Color;

import java.awt.event.ActionListener;

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import javax.swing.JOptionPane;

import javax.swing.JTextField;

import javax.swing.JComboBox;

import javax.swing.DefaultComboBoxModel;

import javax.swing.JFileChooser;

import javax.swing.JRadioButton;

import javax.swing.event.ChangeListener;

import javax.swing.event.ChangeEvent;

import javax.swing.SwingConstants;

public class GUI {

	private JFrame frame;

	private JTextField path;//הוספת מסגרת 

	private boolean mode = true;

	private JTextField textField;//הוספת עוד מסגרת 


	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {

				try {

					GUI window = new GUI();

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

	public GUI() {

		initialize();

	}


	private void initialize() {

		frame = new JFrame();

		frame.setBounds(100, 100, 530, 288);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(null);

		

		JLabel lblFilepath = new JLabel("Input File:");

		lblFilepath.setBounds(30, 61, 73, 14);

		frame.getContentPane().add(lblFilepath);

		

		path = new JTextField();

		path.setBounds(90, 58, 300, 20);

		frame.getContentPane().add(path);

		path.setColumns(10);

		

		JButton btnComprees = new JButton("Compress Menu");

		

		btnComprees.setForeground(Color.BLACK);

		btnComprees.setBackground(Color.GREEN);

		btnComprees.setToolTipText("");

		btnComprees.setBounds(30, 11, 205, 23);

		frame.getContentPane().add(btnComprees);

		

		JComboBox dicSize = new JComboBox();

		dicSize.setModel(new DefaultComboBoxModel(new String[] {"32 Bytes", "64 Bytes", "128 Bytes", "256 Bytes", "512 Bytes", "1024 Bytes", "2048 Bytes", "4096 Bytes", "8192 Bytes", "16384 Bytes", "32768 Bytes"}));

		dicSize.setSelectedIndex(6);

		dicSize.setBounds(168, 122, 89, 20);

		frame.getContentPane().add(dicSize);

		

		JComboBox maxLen = new JComboBox();

		maxLen.setModel(new DefaultComboBoxModel(new String[] {"4", "8", "16", "32", "64", "128", "256", "512"}));

		maxLen.setSelectedIndex(3);

		maxLen.setBounds(168, 153, 89, 20);

		frame.getContentPane().add(maxLen);

		

		JComboBox minLen = new JComboBox();

		minLen.setModel(new DefaultComboBoxModel(new String[] {"2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "20"}));

		minLen.setSelectedIndex(1);

		minLen.setBounds(168, 184, 89, 20);

		frame.getContentPane().add(minLen);

		

		

		JButton btnAddFile = new JButton("Add File");

		btnAddFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				JFileChooser jfile = new JFileChooser();

				if(jfile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					File file = jfile.getSelectedFile();

					path.setText(file.getPath());

					

				}

				if(mode)

					textField.setText(path.getText().substring(0, path.getText().lastIndexOf('\\')) + "\\comp_" + 

							path.getText().substring(path.getText().lastIndexOf('\\') + 1, path.getText().length()));

				else

					textField.setText(path.getText().substring(0, path.getText().lastIndexOf('\\')) + "\\dec_" + 

							path.getText().substring(path.getText().lastIndexOf('\\') + 1, path.getText().length()));

				

			}

		});

		btnAddFile.setBounds(404, 57, 89, 23);

		frame.getContentPane().add(btnAddFile);

		

		JLabel lblWindowSize = new JLabel("Window Size:");

		lblWindowSize.setBounds(30, 125, 113, 14);

		frame.getContentPane().add(lblWindowSize);

		

		JLabel lblMaxSequenceLength = new JLabel("Max Sequence Length:");

		lblMaxSequenceLength.setBounds(30, 156, 157, 14);

		frame.getContentPane().add(lblMaxSequenceLength);

		

		JLabel lblMSequenceLength = new JLabel("Min Sequence Length:");

		lblMSequenceLength.setBounds(30, 187, 157, 14);

		frame.getContentPane().add(lblMSequenceLength);

		

		JButton btnDecompress = new JButton("Decompress Menu");

		

		btnDecompress.setToolTipText("");

		btnDecompress.setForeground(Color.BLACK);

		btnDecompress.setBackground(Color.LIGHT_GRAY);

		btnDecompress.setBounds(288, 11, 205, 23);

		frame.getContentPane().add(btnDecompress);

		

		JButton btnStart = new JButton("Start");

		

		btnStart.setBounds(404, 91, 89, 23);

		frame.getContentPane().add(btnStart);

		

		
		JRadioButton rdbtnLzssOnly = new JRadioButton("LZSS only");

		rdbtnLzssOnly.setSelected(true);

		rdbtnLzssOnly.setBounds(280, 121, 98, 23);

		frame.getContentPane().add(rdbtnLzssOnly);

		

		JLabel lblOuputFilePath = new JLabel("Ouput File:");

		lblOuputFilePath.setBounds(30, 94, 89, 14);

		frame.getContentPane().add(lblOuputFilePath);

		

		textField = new JTextField();

		textField.setColumns(10);

		textField.setBounds(90, 91, 300, 20);

		frame.getContentPane().add(textField);
	

		btnDecompress.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				dicSize.hide();

				lblWindowSize.hide();

				maxLen.hide();

				lblMaxSequenceLength.hide();

				minLen.hide();

				lblMSequenceLength.hide();

				btnDecompress.setBackground(Color.GREEN);

				btnComprees.setBackground(Color.LIGHT_GRAY);
		rdbtnLzssOnly.hide();
			path.setText("");

				textField.setText("");

				mode = false;

			}

		});

		btnComprees.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				dicSize.show();

				lblWindowSize.show();

				maxLen.show();

				lblMaxSequenceLength.show();

				minLen.show();

				lblMSequenceLength.show();

				btnComprees.setBackground(Color.GREEN);

				btnDecompress.setBackground(Color.LIGHT_GRAY);

				rdbtnLzssOnly.show();

				path.setText("");

				textField.setText("");

				mode = true;

			}

		});

		btnStart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String fpath = path.getText();

				if(fpath.length() == 0) 

					path.setText("Add file");

				else {

					if(mode) {

						int winSize = Integer.parseInt(dicSize.getSelectedItem().toString().substring(0,dicSize.getSelectedItem().toString().length() - 6));

						int maxiLen = Integer.parseInt(maxLen.getSelectedItem().toString());

						int miniLen = Integer.parseInt(minLen.getSelectedItem().toString());

						String[] compArgs = {fpath, textField.getText(), Boolean.toString(mode),"false","false",

											Integer.toString(winSize), Integer.toString(maxiLen), Integer.toString(miniLen)};

						try {

							FileInputStream tmp = new FileInputStream(fpath);

							long originalSize = tmp.available();

							tmp.close();

							Compressor.main(compArgs);

							tmp = new FileInputStream(textField.getText());

							long compFileSize = tmp.available();

							tmp.close();

							JOptionPane.showMessageDialog(frame, "Done");

						} catch (IOException e1) {

							// TODO Auto-generated catch block

							e1.printStackTrace();

						}

					}

					else {

						String[] mainArgs = {fpath, textField.getText() , Boolean.toString(mode)};

						try {

							Compressor.main(mainArgs);

							JOptionPane.showMessageDialog(frame, "Done");

						} catch (IOException e1) {

							// TODO Auto-generated catch block

							e1.printStackTrace();

						}

					}

				}

			}

		});

	}

}
