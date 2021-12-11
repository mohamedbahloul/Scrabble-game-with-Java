import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

	public class MenuScrabble extends JFrame implements ActionListener{
		JPanel contentPanel=new JPanel(new GridLayout(3,1));
		 JButton jouer =new JButton("Jouer");
		 JButton regle =new JButton("Régles de jeu");
		 JButton exit =new JButton("Quitter");
		 JLabel bienvenue=new JLabel("Bienvenue au jeu du scrabble !");
		public MenuScrabble() {
			contentPanel.add(jouer);
			contentPanel.add(regle);
			contentPanel.add(exit);
			contentPanel.setSize(new Dimension(200, 200));
			 JLabel background=new JLabel(new ImageIcon("C:\\scrabble.jpeg"));
			 background.setSize(1000,800);
			 this.add(background);
			 background.setLayout(new BorderLayout());
		     background.add(bienvenue,BorderLayout.NORTH);
		     background.add(contentPanel,BorderLayout.CENTER);
		     jouer.addActionListener(new jeu());
		     regle.addActionListener(new regles());
		     exit.addActionListener(this);
		     this.setTitle("Scrabble");
			this.setSize(1000,800);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
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
				f.setTitle("Règles du jeu");
				f.setSize(1000,800);
				f.setLocationRelativeTo(null);
				f.setLayout(new GridLayout(14,1));
				f.add(l1);f.add(l2);f.add(l3);f.add(l4);f.add(l5);f.add(l6);f.add(l7);f.add(l8);f.add(l9);f.add(l10);f.add(l11);f.add(l12);f.add(l13);f.add(l14);
				f.setVisible(true);
			}
		}
		public static class jeu extends JFrame implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				ScrabbleGameFrame f=new ScrabbleGameFrame();
			}
		}
	public static void main(String[] args) {
		MenuScrabble m=new MenuScrabble();

	}

}
