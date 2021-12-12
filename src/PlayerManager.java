import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

import javax.swing.*;

public class PlayerManager { //Classe permettant de gérer les joueurs ainsi que leurs scores
	static JLabel ScoreP1 = new JLabel("0"); //JLabel contenant le score du 1er joueur
	static JLabel ScoreP2 = new JLabel("0"); //JLabel contenant le score du 2éme joueur
	static String alphabets="aaaaaaaaabbccddddeeeeeeeeeeeeffggghhiiiiiiiiijkllllmmnnnnnnooooooppqrrrrrrrrssssttttttuuuuvvwwxyyz";
	//La répartition des alphabets par rapport leur distribution dans la langue Française
	static JPanel P1Panel=new JPanel(); //JPanel contenant les lettres du 1er joueur
	static JPanel P2Panel=new JPanel(); //JPanel contenant les lettres du 2éme joueur
	static Player p1=new Player("Player 1"); //Création des 2 objets des joueurs
	static Player p2=new Player("Player 2");
	static ArrayList<Buttons> listP1=new ArrayList<Buttons>(); //Liste des boutons du 1er joueur
	static ArrayList<Buttons> listP2=new ArrayList<Buttons>();//Liste des boutons du 2éme joueur
	
	public static void updateScore(int score) { //Fonction pour la mise à jour du score des joueurs
		if(ButtonManager.CurrentPlayerTurn==0) {//Tour du 1er joueur
			p1.setScore(p1.getScore()+score); 
			ScoreP1.setText(Integer.toString( p1.getScore()));
			//ScoreP1.updateUI();
		}
		else {//Tour du 2éme joueur
			p2.setScore(p2.getScore()+score);
			ScoreP2.setText(Integer.toString( p2.getScore()));
			//ScoreP2.updateUI();
		}
	}
	public static PlayerManager PlayerManager; //Objet static à appeler dans des autres classes
	static {
		ScoreP1.setBackground(Color.WHITE); //Modifications des visuels des score
		ScoreP1.setHorizontalAlignment(SwingConstants.CENTER);
		ScoreP1.setBounds(90, 150, 205, 64);
		ScoreP2.setBackground(Color.WHITE);
		ScoreP2.setHorizontalAlignment(SwingConstants.CENTER);
		ScoreP2.setBounds(1121, 150, 205, 64);
		Random r = new Random();
		for(int i=0;i<7;i++) { //Remplir aléatoirement la liste des lettres du 1er joueur
			int randomInt=r.nextInt(alphabets.length()); //Sélection d'un caractère par défaut
			alphabets = alphabets.replaceFirst(Character.toString( alphabets.charAt(randomInt)), ""); //Le caractère est remplacé par un espace pour qu'il ne soit pas appelé une autre fois
			listP1.add(new Buttons(1,i,0,alphabets.charAt(randomInt),p1));
			P1Panel.add(listP1.get(i).getButton());//Ajout des lettres dans le JPanel
		}
		for(int i=0;i<7;i++) {//Remplir aléatoirement la liste des lettres du 2éme joueur
			int randomInt=r.nextInt(alphabets.length()); //Sélection d'un caractère par défaut
			alphabets = alphabets.replaceFirst(Character.toString( alphabets.charAt(randomInt)), "");//Le caractère est remplacé par un espace pour qu'il ne soit pas appelé une autre fois
			listP2.add(new Buttons(1,i,0,alphabets.charAt(randomInt),p2));
			P2Panel.add(listP2.get(i).getButton()); //Ajout des lettres dans le JPanel
			listP2.get(i).getButton().setEnabled(false); //Le joueur 1 commence le jeu par défaut
		}
	}

	


}
