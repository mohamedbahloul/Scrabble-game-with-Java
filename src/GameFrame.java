import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;

public class GameFrame extends JFrame implements ActionListener {

	public GameFrame() {
		this.setTitle("Gestion de CV");
		this.setSize(1000,800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel ButtonsPan=new JPanel();
		ButtonsPan.setPreferredSize(new Dimension(1000,1000));
		ButtonsPan.setBounds(1000,1000,500,500);
		ButtonsPan.add(ButtonManager.ButtonsPanel,BorderLayout.CENTER);
		this.getContentPane().add(ButtonsPan,BorderLayout.CENTER);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
