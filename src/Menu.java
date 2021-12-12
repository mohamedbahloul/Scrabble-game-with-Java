import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Menu extends JFrame implements ActionListener{

	private JPanel contentPane;
	JButton btnNewButton = new JButton("Jouer");
	JButton btnRgles = new JButton("R\u00E8gles");
	JButton btnQuitter = new JButton("Quitter");
	JLabel lblNewLabel_2 = new JLabel("Bienvenue au jeu du scrabble");
	JLabel lblNewLabel = new JLabel("");
	Menu frame ;
	static ScrabbleGameFrame GameFrame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
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
	public Menu() {
		setTitle("Bienvenue au jeu du scrabble");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Jouer");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(new Color(240, 240, 240));
		btnNewButton.setBounds(224, 188, 112, 40);
		contentPane.add(btnNewButton);
		
		JButton btnRgles = new JButton("R\u00E8gles");
		btnRgles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRgles.setForeground(Color.BLACK);
		btnRgles.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRgles.setBackground(SystemColor.menu);
		btnRgles.setBounds(224, 238, 112, 40);
		contentPane.add(btnRgles);
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.setForeground(Color.BLACK);
		btnQuitter.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnQuitter.setBackground(SystemColor.menu);
		btnQuitter.setBounds(224, 288, 112, 40);
		contentPane.add(btnQuitter);
		
		
		JLabel lblNewLabel_2 = new JLabel("Bienvenue au jeu du scrabble");
		lblNewLabel_2.setFont(new Font("VELOCISTA", Font.PLAIN, 30));
		lblNewLabel_2.setForeground(new Color(0, 0, 0));
		lblNewLabel_2.setBounds(40, 83, 644, 123);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\mahdi\\Documents\\GitHub\\ScrabbleJava\\src\\scrabble.PNG"));
		lblNewLabel.setBounds(0, 0, 571, 361);
		contentPane.add(lblNewLabel);
		btnNewButton.addActionListener(new jeu());
		btnRgles.addActionListener(new regles());
		btnQuitter.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
	public static class regles extends JFrame implements ActionListener {
		public void actionPerformed(ActionEvent e)
		{
		JFrame f=new JFrame();
		String ch1="Nombre de joueurs du Scrabble : 2 joueurs \n";
		String ch2="But du jeu : Cumuler le plus de points en formant des mots entrecroisés sur une grille de 15×15 cases. "+ "Chaque lettre possède des une valeur de 1.";
		String ch3="Pour placer un mot : \n";
		String ch4="    Longueur minimum : deux lettres. \n";
		String ch5="    Premier mot : Le premier mot doit toujours couvrir la case centrale. \n";
		String ch6="    Placement des mots suivants : soit perpendiculairement, soit parallèlement à un mot déjà placé. \n";
		String ch7="    Sens d’écriture : Les mots doivent toujours être écrits de gauche à droite ou de haut en bas. \n";
		String ch8="    Prolonger un mot : Possibilité de continuer un mot déjà placé en le prolongeant par l’avant, l’arrière ou les deux à la fois. \n";
		String ch9="    Le Joueur compte les points marqués (voir score) et annonce le score . \n";
		String ch10="    Ensuite c'est le role de son adversaire pour jouer . \n";
		String ch11="Passer son tour : \n";
		String ch12="    Le Joueur annonce « End Turn » et donne le tour à l'autre joueur \n";
		String ch13="    Il est possible de passer autant de fois que vous le souhaitez. \n";
		String ch14="   Si tous les joueurs passent chaque fois à leur tour; trois fois de suite, la partie s’arrête et le joueur ayant le plus de score gagne \n";
			JLabel l1=new JLabel(ch1);JLabel l2=new JLabel(ch2);JLabel l3=new JLabel(ch3);JLabel l4=new JLabel(ch4);JLabel l5=new JLabel(ch5);JLabel l6=new JLabel(ch6);
			JLabel l7=new JLabel(ch7);JLabel l8=new JLabel(ch8);JLabel l9=new JLabel(ch9);JLabel l10=new JLabel(ch10);JLabel l11=new JLabel(ch11);JLabel l12=new JLabel(ch12);
			JLabel l13=new JLabel(ch13);JLabel l14=new JLabel(ch14);
			l1.setFont(new Font("Verdana", Font.PLAIN, 12));l2.setFont(new Font("Verdana", Font.PLAIN, 12));l3.setFont(new Font("Verdana", Font.PLAIN, 12));l4.setFont(new Font("Verdana", Font.PLAIN, 12));
			l5.setFont(new Font("Verdana", Font.PLAIN, 12));l6.setFont(new Font("Verdana", Font.PLAIN, 12));l7.setFont(new Font("Verdana", Font.PLAIN, 12));l8.setFont(new Font("Verdana", Font.PLAIN, 12));
			l9.setFont(new Font("Verdana", Font.PLAIN, 12));l10.setFont(new Font("Verdana", Font.PLAIN, 12));l11.setFont(new Font("Verdana", Font.PLAIN, 12));l12.setFont(new Font("Verdana", Font.PLAIN, 12));
			l13.setFont(new Font("Verdana", Font.PLAIN, 12));l14.setFont(new Font("Verdana", Font.PLAIN, 12));
			f.setTitle("Règles du jeu");
			f.setSize(1000,800);
			f.setLocationRelativeTo(null);
			f.setLayout(new GridLayout(14,1));
			f.add(l1);f.add(l2);f.add(l3);f.add(l4);f.add(l5);f.add(l6);f.add(l7);f.add(l8);f.add(l9);f.add(l10);f.add(l11);f.add(l12);f.add(l13);f.add(l14);
			f.getContentPane().setBackground(Color.orange );
			f.setVisible(true);
		}
	}
	public static class jeu extends JFrame implements ActionListener
	{
		JLabel p1=new JLabel("Player 1 :");
	JLabel p2=new JLabel("Player 2 :");
	static JTextField t1=new JTextField(10);
	static JTextField t2=new JTextField(10);
	JLabel blank=new JLabel("         ");
	JButton jouer=new JButton("Jouer");
	JFrame f= new JFrame();
		public void actionPerformed(ActionEvent e)
		{	
			f.setLayout(new GridLayout(3,2));
			jouer.setFont(new Font("Tahoma", Font.PLAIN, 15));
			jouer.setForeground(new Color(0, 0, 0));
			jouer.setBackground(new Color(240, 240, 240));
			f.add(p1);f.add(t1);f.add(p2);f.add(t2);f.add(blank);f.add(jouer);
			f.getContentPane().setBackground(Color.orange );
			f.setSize(300,150);
			f.setVisible(true);
			jouer.addActionListener((new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!t1.getText().equals("")) {
					PlayerManager.p1.setPseudo(t1.getText());
				}
				if(!t2.getText().equals("")) {
					PlayerManager.p2.setPseudo(t2.getText());
				}
				f.setVisible(false);
				GameFrame=new ScrabbleGameFrame();
			}
		}));
		}
	}
	
}
