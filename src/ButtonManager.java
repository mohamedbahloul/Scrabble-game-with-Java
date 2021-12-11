import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

import javax.swing.*;

public class ButtonManager {
	static int turnNumber=0;
	static ArrayList<ArrayList<Buttons>> list=new ArrayList<ArrayList<Buttons>>();
	static JPanel ButtonsPanel=new JPanel();
	static Buttons CurrentPlayerButtonClicked=null;
	public static ArrayList<Buttons> GridClickedButtons=new ArrayList<Buttons>();
	public static ArrayList<Buttons> PlayerClickedButtons=new ArrayList<Buttons>();
	public static int CurrentPlayerTurn =0;
	public static ArrayList<String> dictionnaire=new ArrayList<String>();
	static {
		try {
			String ligne ;
			BufferedReader fichier = new BufferedReader(new FileReader("C:\\Users\\bahlo\\OneDrive\\Documents\\GitHub\\ScrabbleJava\\src\\dictionnaire.txt"));
		while ((ligne = fichier.readLine()) != null) 
		{
			ligne=ligne.toLowerCase();
			dictionnaire.add(ligne);
		}
		fichier.close();
		}catch (Exception e) {
			e.printStackTrace();
		} 
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
		list.get(7).get(7).getButton().setText("*");
	}
	public static void onButtonClick(Buttons btn) {
		if(btn.getPlayer()==null) {
			if(CurrentPlayerButtonClicked!=null) {
				if(GridClickedButtons.contains(btn)) {
					
					for(int i=0;i<PlayerClickedButtons.size();i++) {
						if(PlayerClickedButtons.get(i).getValue()==btn.getButton().getText().charAt(0)) {
							
							PlayerClickedButtons.get(i).getButton().setEnabled(true);
							PlayerClickedButtons.get(i).getButton().setText(Character.toString(PlayerClickedButtons.get(i).getValue()));
							break;
						}
					}
					
					
				}else {
					GridClickedButtons.add(btn);
				}
				PlayerClickedButtons.add(CurrentPlayerButtonClicked);
				btn.getButton().setText(Character.toString( CurrentPlayerButtonClicked.getValue()));
				CurrentPlayerButtonClicked.getButton().setText("");
				CurrentPlayerButtonClicked.getButton().setEnabled(false);			
				CurrentPlayerButtonClicked=null;
			}
		}else {
			CurrentPlayerButtonClicked=btn;
		}
		
	}
	public static void ReturnButtons() {
		for(Buttons b : PlayerClickedButtons) {
			b.getButton().setText(Character.toString( b.getValue()));
			b.getButton().setEnabled(true);
		}
		for(Buttons b : GridClickedButtons) {
			b.getButton().setText(" ");
			if((b.getPosX()==7)&&(b.getPosY()==7)) {
				b.getButton().setText("*");
			}
		}
	}
	public static void endTrun(boolean testEval) {
		
		CurrentPlayerButtonClicked=null;
		
		Random r = new Random();
		if(testEval) {
			for(Buttons b:PlayerClickedButtons) {
				//System.out.print(b.getValue()); 
				int randomInt=r.nextInt(PlayerManager.alphabets.length());
				PlayerManager.alphabets = PlayerManager.alphabets.replaceFirst(Character.toString( PlayerManager.alphabets.charAt(randomInt)), "");
				System.out.println(PlayerManager.alphabets.length());
				String s=Character.toString(PlayerManager.alphabets.charAt(randomInt));
				b.getButton().setText(s);
				b.setValue(s.charAt(0));
				
			}
			for(Buttons b :GridClickedButtons) {
				b.getButton().setEnabled(false);
			}
			turnNumber++;
			//System.out.println();
		
		}
		else {
			ReturnButtons();
		}
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
		
		PlayerClickedButtons.clear();
		GridClickedButtons.clear();
	}
	public static boolean VerifWord(String word)
	{
		//return(dictionnaire.contains(word));
		return true;
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

	public static int evaluate() {
		int totalscore=0;
		int vscore=0;
		int hscore=0;
		if(turnNumber==0) {
			if(list.get(7).get(7).getButton().getText().equals("*")) {
				JOptionPane.showMessageDialog(null, "You have to start from he center of the grid!!");
				System.out.println("wrong");
				for(Buttons b:GridClickedButtons) {
					b.getButton().setEnabled(true);
					b.getButton().setText(" ");
				}
				GridClickedButtons.clear();
				return -1;
			}
		}
		ArrayList<Buttons> NewGridClickedButtons=new ArrayList<Buttons> (GridClickedButtons);
		String VerticalWordH="";
		String HorizontalWordH="";
		int X=GridClickedButtons.get(0).getPosX();
		int Y=GridClickedButtons.get(0).getPosY();

		if(GridClickedButtons.size()==0) {
			//Aucun chiffre 
			System.out.println("no chiffre");
			return -1;
		}
		else if(GridClickedButtons.size()==1) {
			
			int k=0;
			for(int i=X;i>=0;i--) {
				if(!list.get(Y).get(i).getButton().getText().equals(" ")) {
					
					HorizontalWordH=list.get(Y).get(i).getButton().getText()+HorizontalWordH;
					hscore+=list.get(Y).get(i).getScore();
					if(GridClickedButtons.contains(list.get(Y).get(i))) {
						/********Verification Left top****/////
						for(int j=Y;j>=0;j--) {
							if(!list.get(j).get(i).getButton().getText().equals(" ")) {
								VerticalWordH=list.get(j).get(i).getButton().getText()+VerticalWordH;
								vscore+=list.get(j).get(i).getScore();
							}
							else {
								break;
							}
						}
						/********Verification left down****/////
						for(int j=Y+1;j<15;j++) {
							if(!list.get(j).get(i).getButton().getText().equals(" ")) {
								VerticalWordH+=list.get(j).get(i).getButton().getText();
								vscore+=list.get(j).get(i).getScore();
							}
							else {
								break;
							}
						}
						if(VerticalWordH.length()>1) {
							if(VerifWord(VerticalWordH)==false) {
								System.out.println(VerticalWordH +"    "+VerifWord(VerticalWordH));
								return -1;
							}
							else {
								totalscore+=vscore;
							}
							k++;
						}
						vscore=0;
						VerticalWordH="";
					}
				}
				else {
					break;
				}
			}
			for(int i=X+1;i<15;i++) {
				if(!list.get(Y).get(i).getButton().getText().equals(" ")) {
					HorizontalWordH+=list.get(Y).get(i).getButton().getText();
					hscore+=list.get(Y).get(i).getScore();
					/********Verification Left top****/////
					if(GridClickedButtons.contains(list.get(Y).get(i))) {
						for(int j=Y;j>=0;j--) {
							if(!list.get(j).get(i).getButton().getText().equals(" ")) {
								VerticalWordH=list.get(j).get(i).getButton().getText()+VerticalWordH;
								vscore+=list.get(j).get(i).getScore();
							}
							else {
								break;
							}
						}
						/********Verification left down****/////
						for(int j=Y+1;j<15;j++) {
							if(!list.get(j).get(i).getButton().getText().equals(" ")) {
								VerticalWordH+=list.get(j).get(i).getButton().getText();
								vscore+=list.get(j).get(i).getScore();
							}
							else {
								break;
							}
						}
						if(VerticalWordH.length()>1) {
							if(VerifWord(VerticalWordH)==false) {
								System.out.println(VerticalWordH +"    "+VerifWord(VerticalWordH));
								
								return -1;
							}
							else {
								totalscore+=vscore;
							}
							k++;
						}
						VerticalWordH="";
						vscore=0;
					}
				}
				else {
					break;
				}
			}
			
			if(HorizontalWordH.length()==1) {
				if(k==0) {
					if(VerifWord(HorizontalWordH)==false) {
						System.out.println(HorizontalWordH +"    "+VerifWord(HorizontalWordH));
						
						return -1;
					}
					else {
						totalscore+=hscore;
					}
				}
			}
			else if(HorizontalWordH.length()>1) {
				if(VerifWord(HorizontalWordH)==false) {
					System.out.println(HorizontalWordH +"    "+VerifWord(HorizontalWordH));
					
					return -1;
				}else {
					totalscore+=hscore;
				}
			}
			
		}
		else {
			int Verif=VerifHorizonOrVerti();
			if(Verif==0) {
				/*Verification left*/
				for(int i=X;i>=0;i--) {
					if(!list.get(Y).get(i).getButton().getText().equals(" ")) {
						if(NewGridClickedButtons.contains(list.get(Y).get(i))) {
							NewGridClickedButtons.remove(list.get(Y).get(i));
						}
						HorizontalWordH=list.get(Y).get(i).getButton().getText()+HorizontalWordH;
						hscore+=list.get(Y).get(i).getScore();
						if(GridClickedButtons.contains(list.get(Y).get(i))) {
						/********Verification Left top****/////
							for(int j=Y;j>=0;j--) {
								if(!list.get(j).get(i).getButton().getText().equals(" ")) {
									VerticalWordH=list.get(j).get(i).getButton().getText()+VerticalWordH;
									vscore+=list.get(j).get(i).getScore();
								}
								else {
									break;
								}
							}
							/********Verification right top****/////
							for(int j=Y+1;j<15;j++) {
								if(!list.get(j).get(i).getButton().getText().equals(" ")) {
									VerticalWordH+=list.get(j).get(i).getButton().getText();
									vscore+=list.get(j).get(i).getScore();
								}
								else {
									break;
								}
							}
							if(VerticalWordH.length()>1) {
								if(VerifWord(VerticalWordH)==false) {
									System.out.println(VerticalWordH +"    "+VerifWord(VerticalWordH));
									
									return -1;
								}
								else {
									totalscore+=vscore;
								}
							}
							VerticalWordH="";
							vscore=0;
						}
					}
					else {
						break;
					}
					
				}
				
				for(int i=X+1;i<15;i++) {
					if(!list.get(Y).get(i).getButton().getText().equals(" ")) {
						if(NewGridClickedButtons.contains(list.get(Y).get(i))) {
							NewGridClickedButtons.remove(list.get(Y).get(i));
						}
						HorizontalWordH+=list.get(Y).get(i).getButton().getText();
						hscore+=list.get(Y).get(i).getScore();
						if(GridClickedButtons.contains(list.get(Y).get(i))) {
							/********Verification Left top****/////
							for(int j=Y;j>=0;j--) {
								if(!list.get(j).get(i).getButton().getText().equals(" ")) {
									VerticalWordH=list.get(j).get(i).getButton().getText()+VerticalWordH;
									vscore+=list.get(j).get(i).getScore();
								}
								else {
									break;
								}
							}
							/********Verification left down****/////
							for(int j=Y+1;j<15;j++) {
								if(!list.get(j).get(i).getButton().getText().equals(" ")) {
									VerticalWordH+=list.get(j).get(i).getButton().getText();
									vscore+=list.get(j).get(i).getScore();
								}
								else {
									break;
								}
							}
							if(VerticalWordH.length()>1) {
								if(VerifWord(VerticalWordH)==false) {
									System.out.println(VerticalWordH +"    "+VerifWord(VerticalWordH));
									
									return -1;
								}
								else {
									totalscore+=vscore;
								}
							}
							VerticalWordH="";
							vscore=0;
						}
					}
					else {
						break;
					}
				}
				for(Buttons b :NewGridClickedButtons) {
					
					b.getButton().setEnabled(true);
					b.getButton().setText(" ");
					GridClickedButtons.remove(b);
					
				}
				NewGridClickedButtons.clear();
				if(HorizontalWordH.length()>1) {
					if(VerifWord(HorizontalWordH)==false) {
						System.out.println(HorizontalWordH +"    "+VerifWord(HorizontalWordH));
						
						return -1;
					}
					else {
						totalscore+=hscore;
					}
				}
			
			}
			else if(Verif==1) {
				vscore=0;
				hscore=0;
				/*Verification top*/
				for(int i=Y;i>=0;i--) {
					if(!list.get(i).get(X).getButton().getText().equals(" ")) {
						if(NewGridClickedButtons.contains(list.get(i).get(X))) {
							NewGridClickedButtons.remove(list.get(i).get(X));
						}
						VerticalWordH=list.get(i).get(X).getButton().getText()+VerticalWordH;
						vscore+=list.get(i).get(X).getScore();
						if(GridClickedButtons.contains(list.get(i).get(X))) {
						/********Verification Left top****/////
							for(int j=X;j>=0;j--) {
								if(!list.get(i).get(j).getButton().getText().equals(" ")) {
									HorizontalWordH=list.get(i).get(j).getButton().getText()+HorizontalWordH;
									hscore+=list.get(i).get(j).getScore();
								}
								else {
									break;
								}
							}
							/********Verification left down****/////
							for(int j=X+1;j<15;j++) {
								if(!list.get(i).get(j).getButton().getText().equals(" ")) {
									HorizontalWordH+=list.get(i).get(j).getButton().getText();
									hscore+=list.get(i).get(j).getScore();
								}
								else {
									break;
								}
							}
							if(HorizontalWordH.length()>1) {
								if(VerifWord(HorizontalWordH)==false) {
									System.out.println(HorizontalWordH +"    "+VerifWord(HorizontalWordH));
									return -1;
								}
								else {
									totalscore+=hscore;
								}
							}
							HorizontalWordH="";
							hscore=0;
						}
					}
					else {
						break;
					}
				}
				/***verification down*/////
				for(int i=Y+1;i<15;i++) {
					if(!list.get(i).get(X).getButton().getText().equals(" ")) {
						if(NewGridClickedButtons.contains(list.get(i).get(X))) {
							NewGridClickedButtons.remove(list.get(i).get(X));
						}
						VerticalWordH+=list.get(i).get(X).getButton().getText();
						vscore+=list.get(i).get(X).getScore();
						if(GridClickedButtons.contains(list.get(i).get(X))) {
						/********Verification Left top****/////
							for(int j=X;j>=0;j--) {
								if(!list.get(i).get(j).getButton().getText().equals(" ")) {
									HorizontalWordH=list.get(i).get(j).getButton().getText()+HorizontalWordH;
									hscore+=list.get(i).get(j).getScore();
								}
								else {
									break;
								}
							}
							/********Verification left down****/////
							for(int j=X+1;j<15;j++) {
								if(!list.get(i).get(j).getButton().getText().equals(" ")) {
									HorizontalWordH+=list.get(i).get(j).getButton().getText();
									hscore+=list.get(i).get(j).getScore();
								}
								else {
									break;
								}
							}
							if(HorizontalWordH.length()>1) {
								if(VerifWord(HorizontalWordH)==false) {
									System.out.println(HorizontalWordH +"    "+VerifWord(HorizontalWordH));
									return -1;
								}
								else {
									totalscore+=hscore;
								}
							}
							HorizontalWordH="";
							hscore=0;
						}
					}
					else {
						break;
					}
				}
				if(VerticalWordH.length()>1) {
					if(VerifWord(VerticalWordH)==false) {
						System.out.println(VerticalWordH +"    "+VerifWord(VerticalWordH));
						return -1;
					}
					else {
						totalscore+=vscore;
					}
				}
				for(Buttons b :NewGridClickedButtons) {
					
					b.getButton().setEnabled(true);
					b.getButton().setText(" ");
					GridClickedButtons.remove(b);
					
				}
				NewGridClickedButtons.clear();
			}
			else {
				ReturnButtons();
				return -1;
			}
			
			
			
		}
		
		return totalscore;

	}


public static void main(String[] args)
{
	System.out.println(VerifWord("rm"));
}
}