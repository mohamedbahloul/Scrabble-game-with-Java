import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

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

public class ScrabbleGameFrame extends JFrame implements WindowListener {//Fenetre du jeu

	public static boolean endGame=false;//Variable booléenne pour déclencher la fin du jeu
	static JLabel Timer = new JLabel("480");//JLabel indiquant la durée restante du jeu
	public static int currentTime=480;//Le temps restant pour le jeu
	private JPanel contentPane;
	static ScrabbleGameFrame GameFrame; //Variable static à utiliser dans des autres classes
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScrabbleGameFrame frame = new ScrabbleGameFrame();//Affichage de la fenetre
					frame.setVisible(true);
					counter();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
 
	public static void resetAllValues() { //Méthode à appeler si les joueurs veulent rejouer
		PlayerManager.p1.setScore(0); //Les scores sont à 0
		PlayerManager.p2.setScore(0);
		for(ArrayList<Buttons> list: ButtonManager.list) {
			for(Buttons b : list) {
				b.getButton().setEnabled(true);  //Les boutons de la grille sont prêts et leurs valeurs sont vides
				b.getButton().setText(" ");
				b.setValue(' ');
			}
		}
		ButtonManager.list.get(7).get(7).getButton().setText("*"); //case étoile centrale
		ButtonManager.turnNumber=0; //TurnNumber et PassNumber prennent la valeur 0
		ButtonManager.passNumber=0;
		PlayerManager.alphabets="aaaaaaaaabbccddddeeeeeeeeeeeeffggghhiiiiiiiiijkllllmmnnnnnnooooooppqrrrrrrrrssssttttttuuuuvvwwxyyz";//Chaine alphabets complète
		PlayerManager.ScoreP1.setText("0"); //Les JLabels prennent les valeurs des scores
		PlayerManager.ScoreP2.setText("0");
		currentTime=480; //La durée de jeu est 480 secondes
		endGame=false;
		Timer.setText("480");
		
	}
	public ScrabbleGameFrame() {
		GameFrame=this; //Instancier la valeur static GameFrame 
		addWindowListener(this);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1445, 850);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);//Background orangé
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel ButtonsPan = new JPanel();
		ButtonsPan.setBackground(Color.ORANGE);
		ButtonsPan.setBounds(370, 77, 700, 659);
		ButtonsPan.add(ButtonManager.ButtonsPanel); //Ajout du ButtonsPanel de la classe ButtonManager
		contentPane.add(ButtonsPan);
		
		JLabel lblNewLabel = new JLabel(PlayerManager.p1.getPseudo());//Ajout des pseudos des joueurs et leurs scores de la PlayerManager
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBackground(Color.ORANGE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(100, 407, 205, 64);
		contentPane.add(lblNewLabel);
		
		
		PlayerManager.ScoreP1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(PlayerManager.ScoreP1);
		
		PlayerManager.ScoreP2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(PlayerManager.ScoreP2);
		
		JButton btnNewButton = new JButton("End Turn");//Ajout du bouton EndTurn pour terminer le tour
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBackground(new Color(240, 240, 240));
		btnNewButton.addActionListener(new ActionListener() {//Ajout d'un ActionListener pour ce bouton
			public void actionPerformed(ActionEvent e) {
				//System.out.println(ButtonManager.evaluate());
				int evaluation=ButtonManager.evaluate();
				if(evaluation!=-1) { //Si le mot est valide
					PlayerManager.updateScore(evaluation); //Mise à jour du score
					ButtonManager.endTrun(true);//Appel de la fonction end turn(true) car le mot est valide
					System.out.println("score= "+ evaluation);
				}
				else {
					ButtonManager.endTrun(false);//Appel de la fonction end turn(false) car le mot est invalide
				}
				
				
			}
		});
		btnNewButton.setBounds(624, 739, 198, 64);
		contentPane.add(btnNewButton);
		
		JPanel P1ButtonsPan = new JPanel();//Ajout des JPanles des boutons des 2 joueurs
		P1ButtonsPan.setBounds(10, 469, 356, 64);
		PlayerManager.P1Panel.setBackground(Color.ORANGE);
		P1ButtonsPan.add(PlayerManager.P1Panel);
		P1ButtonsPan.setBackground(Color.ORANGE);
		contentPane.add(P1ButtonsPan);
		
		JLabel lb2NewLabel = new JLabel(PlayerManager.p2.getPseudo());
		lb2NewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb2NewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lb2NewLabel.setBackground(Color.ORANGE);
		lb2NewLabel.setBounds(1131, 407, 205, 64);
		contentPane.add(lb2NewLabel);
		
		JPanel P2ButtonsPan = new JPanel();
		P2ButtonsPan.setBounds(1072, 469, 348, 64);
		PlayerManager.P2Panel.setBackground(Color.ORANGE);
		P2ButtonsPan.add(PlayerManager.P2Panel);
		P2ButtonsPan.setBackground(Color.ORANGE);
		contentPane.add(P2ButtonsPan);
		
		JLabel label = new JLabel("");
		label.setBounds(121, 503, 324, 310);
		contentPane.add(label);
		
		
		Timer.setHorizontalAlignment(SwingConstants.CENTER);//Ajout du label affichant le temps restant
		Timer.setFont(new Font("Tahoma", Font.BOLD, 15));
		Timer.setBounds(682, 10, 140, 64);
		contentPane.add(Timer);
		this.setVisible(true);
		
		
	}

	public static void counter() {//Méhode gérant le compteur
		
		ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	if((currentTime<=0)&&(endGame==false)) {//Si le temps maximal est atteint
            		ButtonManager.endGame();//Fin de la partie
            		endGame=true;
                }
                //...Perform a task...
            	currentTime--;//Diminution du temps courant 
            	Timer.setText(currentTime+"");
            	
            }
        };
        
        Timer timer = new Timer(1000 ,taskPerformer);
        
        timer.setRepeats(true);
        timer.start();
        if(currentTime<=0) {
        	timer.stop();
		}
        try {
        Thread.sleep(500);
        }
        catch(InterruptedException e) {
        	System.out.println(e.getMessage());
        }
        
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
