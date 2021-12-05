import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
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
	public static String dictionnaire="";
	static {try {
		String ligne ;
		BufferedReader fichier = new BufferedReader(new FileReader("C:\\dictionnaire.txt"));
		while ((ligne = fichier.readLine()) != null) 
		{
			ligne=ligne.toLowerCase();
			dictionnaire +=ligne;
		}
		fichier.close();
}catch (Exception e) {
	e.printStackTrace();
	} }
	
	static {
		
		ButtonsPanel.setLayout(new GridLayout(15,15));
		ButtonsPanel.setPreferredSize(new Dimension(600,600));
		ArrayList<Buttons> li=new ArrayList<Buttons>();
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				li.add(new Buttons(1,j,i,' '));
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
			//System.out.print(b.getValue()); 
			String s=Character.toString( PlayerManager.alphabets.charAt(r.nextInt(26)));
			b.getButton().setText(s);
			b.setValue(s.charAt(0));
		}
		//System.out.println();
		PlayerClickedButtons.clear();
	}
	public static boolean VerifWord(String word)
	{
		return(dictionnaire.contains(word));
	}
	public static int VerifHorizonOrVerti() {
		boolean horizontal=true;
		boolean vertival=true;

		for(int i =1;i<GridClickedButtons.size();i++) {
			if(GridClickedButtons.get(i).getPosX()!=GridClickedButtons.get(0).getPosX()) {
				vertival=false;
			}
			if(GridClickedButtons.get(i).getPosY()!=GridClickedButtons.get(0).getPosY()) {
				horizontal=false;
			}
		}
		if(vertival) {
			return 1;
		}
		else if(horizontal) {
			return 0;
		}
		else {
			return -1;
		}
	}
	public static void evaluate() {
		String VerticalWordH="";
		String HorizontalWordH="";
		int X=GridClickedButtons.get(0).getPosX();
		int Y=GridClickedButtons.get(0).getPosY();

		if(GridClickedButtons.size()==0) {
			//Aucun chiffre 
		}
		else if(GridClickedButtons.size()==1) {
			int k=0;
			for(int i=X;i>=0;i--) {
				if(!list.get(Y).get(i).getButton().getText().equals(" ")) {
					HorizontalWordH=list.get(Y).get(i).getButton().getText()+HorizontalWordH;
					
					/********Verification Left top****/////
					for(int j=Y;j>=0;j--) {
						if(!list.get(j).get(i).getButton().getText().equals(" ")) {
							VerticalWordH=list.get(j).get(i).getButton().getText()+VerticalWordH;
						}
						else {
							break;
						}
					}
					/********Verification left down****/////
					for(int j=Y+1;j<15;j++) {
						if(!list.get(j).get(i).getButton().getText().equals(" ")) {
							VerticalWordH+=list.get(j).get(i).getButton().getText();
						}
						else {
							break;
						}
					}
					if(VerticalWordH.length()>1) {
						System.out.println("Vertical Word "+VerticalWordH);
						k++;
					}
					VerticalWordH="";
				}
				else {
					break;
				}
			}
			for(int i=X+1;i<15;i++) {
				if(!list.get(Y).get(i).getButton().getText().equals(" ")) {
					HorizontalWordH+=list.get(Y).get(i).getButton().getText();
					/********Verification Left top****/////
					for(int j=Y;j>=0;j--) {
						if(!list.get(j).get(i).getButton().getText().equals(" ")) {
							VerticalWordH=list.get(j).get(i).getButton().getText()+VerticalWordH;
						}
						else {
							break;
						}
					}
					/********Verification left down****/////
					for(int j=Y+1;j<15;j++) {
						if(!list.get(j).get(i).getButton().getText().equals(" ")) {
							VerticalWordH+=list.get(j).get(i).getButton().getText();
						}
						else {
							break;
						}
					}
					if(VerticalWordH.length()>1) {
						System.out.println("Vertical Word "+VerticalWordH);
						k++;
					}
					VerticalWordH="";
				}
				else {
					break;
				}
			}
			if(HorizontalWordH.length()==1) {
				if(k==0) {
					System.out.println("Horizontal Word "+HorizontalWordH);
				}
			}
			else if(HorizontalWordH.length()>1)
				System.out.println("Horizontal Word "+HorizontalWordH);
			
		}
		else {
			int Verif=VerifHorizonOrVerti();
			if(Verif==0) {
				/*Verification left*/
				for(int i=X;i>=0;i--) {
					if(!list.get(Y).get(i).getButton().getText().equals(" ")) {
						HorizontalWordH=list.get(Y).get(i).getButton().getText()+HorizontalWordH;
						
						/********Verification Left top****/////
						for(int j=Y;j>=0;j--) {
							if(!list.get(j).get(i).getButton().getText().equals(" ")) {
								VerticalWordH=list.get(j).get(i).getButton().getText()+VerticalWordH;
							}
							else {
								break;
							}
						}
						/********Verification left down****/////
						for(int j=Y+1;j<15;j++) {
							if(!list.get(j).get(i).getButton().getText().equals(" ")) {
								VerticalWordH+=list.get(j).get(i).getButton().getText();
							}
							else {
								break;
							}
						}
						if(VerticalWordH.length()>1)
							System.out.println("Vertical Word "+VerticalWordH);
						VerticalWordH="";
					}
					else {
						break;
					}
				}
				for(int i=X+1;i<15;i++) {
					if(!list.get(Y).get(i).getButton().getText().equals(" ")) {
						HorizontalWordH+=list.get(Y).get(i).getButton().getText();
						/********Verification Left top****/////
						for(int j=Y;j>=0;j--) {
							if(!list.get(j).get(i).getButton().getText().equals(" ")) {
								VerticalWordH=list.get(j).get(i).getButton().getText()+VerticalWordH;
							}
							else {
								break;
							}
						}
						/********Verification left down****/////
						for(int j=Y+1;j<15;j++) {
							if(!list.get(j).get(i).getButton().getText().equals(" ")) {
								VerticalWordH+=list.get(j).get(i).getButton().getText();
							}
							else {
								break;
							}
						}
						if(VerticalWordH.length()>1)
							System.out.println("Vertical Word "+VerticalWordH);
						VerticalWordH="";
					}
					else {
						break;
					}
				}
				if(HorizontalWordH.length()>1)
					System.out.println("Horizontal Word "+HorizontalWordH);
			
			}
			else if(Verif==1) {
				
				/*Verification top*/
				for(int i=Y;i>=0;i--) {
					if(!list.get(i).get(X).getButton().getText().equals(" ")) {
						VerticalWordH=list.get(i).get(X).getButton().getText()+VerticalWordH;
						/********Verification Left top****/////
						for(int j=X;j>=0;j--) {
							if(!list.get(i).get(j).getButton().getText().equals(" ")) {
								HorizontalWordH=list.get(i).get(j).getButton().getText()+HorizontalWordH;
							}
							else {
								break;
							}
						}
						/********Verification left down****/////
						for(int j=X+1;j<15;j++) {
							if(!list.get(i).get(j).getButton().getText().equals(" ")) {
								HorizontalWordH+=list.get(i).get(j).getButton().getText();
							}
							else {
								break;
							}
						}
						if(HorizontalWordH.length()>1)
							System.out.println("Horizontal Word "+HorizontalWordH);
						HorizontalWordH="";
					}
					else {
						break;
					}
				}
				/***verification down*/////
				for(int i=Y+1;i<15;i++) {
					if(!list.get(i).get(X).getButton().getText().equals(" ")) {
						VerticalWordH+=list.get(i).get(X).getButton().getText();
						/********Verification Left top****/////
						for(int j=X;j>=0;j--) {
							if(!list.get(i).get(j).getButton().getText().equals(" ")) {
								HorizontalWordH=list.get(i).get(j).getButton().getText()+HorizontalWordH;
							}
							else {
								break;
							}
						}
						/********Verification left down****/////
						for(int j=X+1;j<15;j++) {
							if(!list.get(i).get(j).getButton().getText().equals(" ")) {
								HorizontalWordH+=list.get(i).get(j).getButton().getText();
							}
							else {
								break;
							}
						}
						if(HorizontalWordH.length()>1)
							System.out.println("Horizontal Word "+HorizontalWordH);
						HorizontalWordH="";
					}
					else {
						break;
					}
				}
				if(VerticalWordH.length()>1)
				System.out.println("Vertical  Word "+VerticalWordH);
				
				
				
				
				
				
			}
			else {
				
			}
			
			
			
		}
		GridClickedButtons.clear();
		PlayerClickedButtons.clear();
		/*
		for(int i=1;i<GridClickedButtons.size();i++) {
			if(GridClickedButtons.get(i).getPosX()!=GridClickedButtons.get(i-1).getPosX()) {
				
			}
		}*/
	}


public static void main(String[] args)
{
	System.out.println(VerifWord("aaron"));
}
}