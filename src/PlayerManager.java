import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

import javax.swing.*;

public class PlayerManager {
	static JLabel ScoreP1 = new JLabel("0");
	static JLabel ScoreP2 = new JLabel("0");
	static String alphabets="aaaaaaaaabbccddddeeeeeeeeeeeeffggghhiiiiiiiiijkllllmmnnnnnnooooooppqrrrrrrrrssssttttttuuuuvvwwxyyz";
	static JPanel P1Panel=new JPanel();
	static JPanel P2Panel=new JPanel();
	static Player p1;
	static Player p2;
	static int EndGame=0;
	static {
		if((Menu.jeu.t1.getText().equals(""))||(Menu.jeu.t2.getText().equals("")))
		{
			p1=new Player("Player 1");
			p2=new Player("Player 2");
		}
		else
		{
			p1=new Player(Menu.jeu.t1.getText());
			p2=new Player(Menu.jeu.t2.getText());
		}
	}
	static ArrayList<Buttons> listP1=new ArrayList<Buttons>();
	static ArrayList<Buttons> listP2=new ArrayList<Buttons>();
	
	public static void updateScore(int score) {
		if(ButtonManager.CurrentPlayerTurn==0) {
			p1.setScore(p1.getScore()+score);
			ScoreP1.setText(Integer.toString( p1.getScore()));
			//ScoreP1.updateUI();
		}
		else {
			p2.setScore(p2.getScore()+score);
			ScoreP2.setText(Integer.toString( p2.getScore()));
			//ScoreP2.updateUI();
		}
		
	}
	public static PlayerManager PlayerManager;
	static {
		ScoreP1.setHorizontalAlignment(SwingConstants.CENTER);
		ScoreP1.setBounds(90, 20, 205, 64);
		ScoreP2.setHorizontalAlignment(SwingConstants.CENTER);
		ScoreP2.setBounds(1121, 20, 205, 64);
		Random r = new Random();
		for(int i=0;i<7;i++) {
			int randomInt=r.nextInt(alphabets.length());
			alphabets = alphabets.replaceFirst(Character.toString( alphabets.charAt(randomInt)), "");
			listP1.add(new Buttons(1,i,0,alphabets.charAt(randomInt),p1));
			P1Panel.add(listP1.get(i).getButton());
		}
		for(int i=0;i<7;i++) {
			int randomInt=r.nextInt(alphabets.length());
			alphabets = alphabets.replaceFirst(Character.toString( alphabets.charAt(randomInt)), "");
			listP2.add(new Buttons(1,i,0,alphabets.charAt(randomInt),p2));
			P2Panel.add(listP2.get(i).getButton());
			listP2.get(i).getButton().setEnabled(false);
		}
	}
static {
		
		if(EndGame==3)
	{
			for(Buttons b : listP1) {
				b.getButton().setEnabled(false);
									}
		for(Buttons b : listP2) 	{
			b.getButton().setEnabled(false);
									}
		if(p1.getScore()>p2.getScore())
			{
				JFrame f= new JFrame();
				f.setTitle("Fin du match");
				f.add(new JLabel(p1.getPseudo()+" a gagné !"));
				f.setSize(200,70);
				f.setVisible(true);
			}
			else if(p2.getScore()>p1.getScore())
			{
				JFrame f= new JFrame();
				f.setTitle("Fin du match");
				f.add(new JLabel(p2.getPseudo()+" a gagné !"));
				f.setSize(200,70);
				f.setVisible(true);
			}
			else
			{
				JFrame f= new JFrame();
				f.setTitle("Fin du match");
				f.add(new JLabel("Match nul !"));
				f.setSize(200,70);
				f.setVisible(true);
			}}
		}
	}
