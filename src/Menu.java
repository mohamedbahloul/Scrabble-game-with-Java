import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
public class Menu extends JFrame implements ActionListener,WindowListener {
	JPanel contentPanel=new JPanel();
	 JButton jouer =new JButton("Jouer");
	 JButton regle =new JButton("Régles de jeu");
	 JButton exit =new JButton("Quitter");
	public Menu() {
		addWindowListener(this);
		this.setTitle("Scrabble");
		this.setSize(1000,800);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 986, 763);
		contentPanel.setLayout(null);
		jouer.setBounds(278, 100, 435, 100);
		contentPanel.add(jouer);
		regle.setBounds(278, 272, 435, 100);
		contentPanel.add(regle);
		exit.setBounds(278, 467, 435, 100);
		contentPanel.add(exit);
		contentPanel.setPreferredSize(new Dimension(50,50));
	    this.getContentPane().add(contentPanel);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		int retour =JOptionPane.showConfirmDialog(this,"Voulez-vous vraiment quitter?","Quitter",JOptionPane.YES_NO_OPTION);
		if(retour==0){
			System.exit(0);
		}
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
