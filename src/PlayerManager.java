import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

import javax.swing.*;

public class PlayerManager {
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
