import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.ImageIcon;

public class ScrabbleGameFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScrabbleGameFrame frame = new ScrabbleGameFrame();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public ScrabbleGameFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1424, 783);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel ButtonsPan = new JPanel();
		ButtonsPan.setBackground(Color.ORANGE);
		ButtonsPan.setBounds(360, 10, 700, 659);
		ButtonsPan.add(ButtonManager.ButtonsPanel);
		contentPane.add(ButtonsPan);
		
		JLabel lblNewLabel = new JLabel(PlayerManager.p1.getPseudo());
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBackground(Color.ORANGE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(90, 340, 205, 64);
		contentPane.add(lblNewLabel);
		
		
		PlayerManager.ScoreP1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(PlayerManager.ScoreP1);
		
		PlayerManager.ScoreP2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(PlayerManager.ScoreP2);
		
		JButton btnNewButton = new JButton("End Turn");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBackground(new Color(240, 240, 240));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println(ButtonManager.evaluate());
				int evaluation=ButtonManager.evaluate();
				if(evaluation!=-1) {
					PlayerManager.updateScore(evaluation);
					ButtonManager.endTrun(true);
					System.out.println("score= "+ evaluation);
				}
				else {
					ButtonManager.endTrun(false);
				}
				
				
			}
		});
		btnNewButton.setBounds(614, 672, 198, 64);
		contentPane.add(btnNewButton);
		
		JPanel P1ButtonsPan = new JPanel();
		P1ButtonsPan.setBounds(0, 402, 356, 64);
		PlayerManager.P1Panel.setBackground(Color.ORANGE);
		P1ButtonsPan.add(PlayerManager.P1Panel);
		P1ButtonsPan.setBackground(Color.ORANGE);
		contentPane.add(P1ButtonsPan);
		
		JLabel lb2NewLabel = new JLabel(PlayerManager.p2.getPseudo());
		lb2NewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb2NewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lb2NewLabel.setBackground(Color.ORANGE);
		lb2NewLabel.setBounds(1121, 340, 205, 64);
		contentPane.add(lb2NewLabel);
		
		JPanel P2ButtonsPan = new JPanel();
		P2ButtonsPan.setBounds(1062, 402, 348, 64);
		PlayerManager.P2Panel.setBackground(Color.ORANGE);
		P2ButtonsPan.add(PlayerManager.P2Panel);
		P2ButtonsPan.setBackground(Color.ORANGE);
		contentPane.add(P2ButtonsPan);
		
		JLabel label = new JLabel("");
		label.setBounds(111, 436, 324, 310);
		contentPane.add(label);
		this.setVisible(true);
	}
}
