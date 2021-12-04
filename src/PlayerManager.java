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
	static String alphabets="abcdefghijklmnopqrstuuvwxyz";
	static JPanel P1Panel=new JPanel();
	static JPanel P2Panel=new JPanel();
	static Player p1=new Player("hama");
	static Player p2=new Player("mahdi");
	static ArrayList<Buttons> listP1=new ArrayList<Buttons>();
	static ArrayList<Buttons> listP2=new ArrayList<Buttons>();
	int CurrentPlayerTurn =0;;
	
	public static PlayerManager PlayerManager;
	static {
		ScoreP1.setBackground(Color.WHITE);
		ScoreP1.setHorizontalAlignment(SwingConstants.CENTER);
		ScoreP1.setBounds(90, 20, 205, 64);
		ScoreP2.setBackground(Color.WHITE);
		ScoreP2.setHorizontalAlignment(SwingConstants.CENTER);
		ScoreP2.setBounds(1121, 20, 205, 64);
		Random r = new Random();
		for(int i=0;i<7;i++) {
			listP1.add(new Buttons(1,i,0,alphabets.charAt(r.nextInt(26)),p1));
			P1Panel.add(listP1.get(i).getButton());
		}
		for(int i=0;i<7;i++) {
			
			listP2.add(new Buttons(1,i,0,alphabets.charAt(r.nextInt(26)),p2));
			P2Panel.add(listP2.get(i).getButton());
			listP2.get(i).getButton().setEnabled(false);
		}
	}

	


}
