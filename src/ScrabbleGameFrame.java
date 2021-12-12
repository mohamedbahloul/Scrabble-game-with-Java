import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLayeredPane;

public class ScrabbleGameFrame extends JFrame implements WindowListener {

	private JPanel contentPane;
	static ScrabbleGameFrame GameFrame;
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
 
	public static void resetAllValues() {
		PlayerManager.p1.setScore(0);
		PlayerManager.p2.setScore(0);
		for(ArrayList<Buttons> list: ButtonManager.list) {
			for(Buttons b : list) {
				b.getButton().setEnabled(true);
				b.getButton().setText(" ");
				b.setValue(' ');
			}
		}
		ButtonManager.list.get(7).get(7).getButton().setText("*");
		ButtonManager.turnNumber=0;
		ButtonManager.passNumber=0;
		PlayerManager.alphabets="aaaaaaaaabbccddddeeeeeeeeeeeeffggghhiiiiiiiiijkllllmmnnnnnnooooooppqrrrrrrrrssssttttttuuuuvvwwxyyz";
		PlayerManager.ScoreP1.setText("0");
		PlayerManager.ScoreP2.setText("0");
		
	}
	public ScrabbleGameFrame() {
		GameFrame=this;
		addWindowListener(this);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		resetAllValues();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		int retour =JOptionPane.showConfirmDialog(this,"Voulez-vous vraiment quitter la partie?","Quitter",JOptionPane.YES_NO_OPTION);
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
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
