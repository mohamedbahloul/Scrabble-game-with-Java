import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.Color;

import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class EndGame extends JDialog implements ActionListener,WindowListener {

	private final JPanel contentPanel = new JPanel();
	JButton Quitter = new JButton("Quitter");
	JButton Rejouer = new JButton("Rejouer");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EndGame dialog = new EndGame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EndGame() {
		addWindowListener(this);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		String PlayerName;
		int score;
		if(PlayerManager.p1.getScore()>PlayerManager.p2.getScore()) {
			PlayerName=PlayerManager.p1.getPseudo();
			score=PlayerManager.p1.getScore();
		}
		else if(PlayerManager.p1.getScore()<PlayerManager.p2.getScore()) {
			PlayerName=PlayerManager.p2.getPseudo();
			score=PlayerManager.p2.getScore();
		}
		else {
			PlayerName="Les deux joueurs ont apportés le méme score";
			score=PlayerManager.p2.getScore();
		}
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 436, 197);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Le gagnant est");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(0, 0, 436, 66);
		contentPanel.add(lblNewLabel);
		
		JLabel TheWinnerName = new JLabel(PlayerName);
		TheWinnerName.setForeground(Color.RED);
		TheWinnerName.setHorizontalAlignment(SwingConstants.CENTER);
		TheWinnerName.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		TheWinnerName.setBounds(0, 69, 436, 70);
		contentPanel.add(TheWinnerName);
		
		JLabel Score = new JLabel(score+"");
		Score.setHorizontalAlignment(SwingConstants.CENTER);
		Score.setForeground(Color.RED);
		Score.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		Score.setBounds(0, 122, 436, 75);
		contentPanel.add(Score);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 200, 436, 63);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				
				Rejouer.setBounds(60, 10, 131, 36);
				buttonPane.add(Rejouer);
				//getRootPane().setDefaultButton(Rejouer);
				Rejouer.addActionListener(this);
			}
			{
				
				Quitter.setBounds(245, 10, 131, 36);
				buttonPane.add(Quitter);
				Quitter.addActionListener(this);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source=e.getSource();
		if(source==Rejouer ) {
			this.setVisible(false);
			ScrabbleGameFrame sgf=new ScrabbleGameFrame();
		}else {
			int retour =JOptionPane.showConfirmDialog(this,"Voulez-vous vraiment quitter?","Quitter",JOptionPane.YES_NO_OPTION);
			if(retour==0){
				System.exit(0);
			}
		}
		
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
