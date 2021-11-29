import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

import javax.swing.*;

public class ButtonManager {
	static ArrayList<ArrayList<Buttons>> list=new ArrayList<ArrayList<Buttons>>();
	static JPanel ButtonsPanel=new JPanel();
	static Buttons CurrentPlayerButtonClicked=null;
	public static ArrayList<Buttons> GridClickedButtons=new ArrayList<Buttons>();
	public static ArrayList<Buttons> PlayerClickedButtons=new ArrayList<Buttons>();
	public static int CurrentPlayerTurn =0;
	
	static {
		
		ButtonsPanel.setLayout(new GridLayout(15,15));
		ButtonsPanel.setPreferredSize(new Dimension(600,600));
		ArrayList<Buttons> li=new ArrayList<Buttons>();
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				li.add(new Buttons(1,i,j,' '));
				ButtonsPanel.add(li.get(j).getButton());
			}
			
			list.add(li);
			li=new ArrayList<Buttons>();
		}
	}
	public static void onButtonClick(Buttons btn) {
		if(btn.getPlayer()==null) {
			if(CurrentPlayerButtonClicked!=null) {
				btn.getButton().setText(Character.toString( CurrentPlayerButtonClicked.getValue()));
				PlayerClickedButtons.add(CurrentPlayerButtonClicked);
				GridClickedButtons.add(btn);
				CurrentPlayerButtonClicked.getButton().setText("");
				CurrentPlayerButtonClicked.getButton().setEnabled(false);			
				CurrentPlayerButtonClicked=null;
			}
		}else {
			CurrentPlayerButtonClicked=btn;
		}
		
	}
	public static void endTrun() {
		CurrentPlayerButtonClicked=null;
		Random r = new Random();
		if(CurrentPlayerTurn==0) {
			
			for(Buttons b:PlayerManager.listP1) {
				b.getButton().setEnabled(false);
			}
			for(Buttons b:PlayerManager.listP2) {
				b.getButton().setEnabled(true);
			}
			CurrentPlayerTurn=1;
		}
		else {
			for(Buttons b:PlayerManager.listP2) {
				b.getButton().setEnabled(false);
			}
			for(Buttons b:PlayerManager.listP1) {
				b.getButton().setEnabled(true);
			}
			
			CurrentPlayerTurn=0;
		}

		for(Buttons b:PlayerClickedButtons) {
			System.out.print(b.getValue());
			b.getButton().setText(Character.toString( PlayerManager.alphabets.charAt(r.nextInt(26))));
		}
		System.out.println();
		PlayerClickedButtons.clear();
	}
}
