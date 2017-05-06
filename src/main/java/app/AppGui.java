package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AppGui extends JFrame implements ActionListener {

private JPanel imageMainPanel;
private JPanel compressedImageMainPanel;
private JPanel imagePanel;
private JPanel buttonPanel;
private JLabel imageLabel;
private JLabel compressedImageLabel;
private JButton openFileButton;
private JButton trainTreeButton;
private JButton compressButton;
private JButton openCompressedButton;

public AppGui(){
	super("Huffman Image Compression");
	setSize(800,600);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setVisible(true);
	setLayout(new GridLayout(1,2));
	setResizable(false);
	
	openFileButton = new JButton("OPEN FILE");
	trainTreeButton = new JButton ("Train Tree");
	compressButton = new JButton ("Compress Image");
	openCompressedButton = new JButton("Open Compressed File");
	imageLabel = new JLabel();
	openFileButton.addActionListener(this);
	trainTreeButton.addActionListener(this);
	compressButton.addActionListener(this);
	openCompressedButton.addActionListener(this);
	
	
	imageMainPanel = new JPanel();
	//imageMainPanel.setLayout(new FlowLayout());
	imagePanel = new JPanel();
	imagePanel.setLayout(new GridLayout(2,1));
	buttonPanel = new JPanel();
	buttonPanel.setLayout(new GridLayout(1,4,2,2));
	buttonPanel.add(openFileButton);
	buttonPanel.add(trainTreeButton);
	buttonPanel.add(compressButton);
	buttonPanel.add(openCompressedButton);
	
	imagePanel.add(imageLabel, BorderLayout.NORTH);
	imagePanel.add(buttonPanel, BorderLayout.SOUTH);
	
	imageMainPanel.add(imagePanel);
	add(imageMainPanel);
	
	compressedImageMainPanel = new JPanel();
	compressedImageLabel = new JLabel("*INSERT COMPRESSED IMAGE");
	compressedImageMainPanel.add(compressedImageLabel, BorderLayout.CENTER);
	
	add(compressedImageMainPanel);
	
}	


	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		
		if(e.getSource() == openFileButton){
			int option = chooser.showOpenDialog(AppGui.this);
			if (option == JFileChooser.APPROVE_OPTION) {
		          File[] sf = chooser.getSelectedFiles();
		          String pathName = chooser.getSelectedFile().getPath();
		          ImageIcon icon = new ImageIcon(pathName);
		          imageLabel.setIcon(icon);
			}
		}	
		
		if(e.getSource() == trainTreeButton){
			
		}
		
		if(e.getSource() == compressButton){
			
		}
		
		if(e.getSource() == openCompressedButton){
			
		}
}
	public static void main(String [] args){
		//new AppGui();
		new App();
	}

	

}
