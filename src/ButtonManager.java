import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

import javax.swing.*;

public class ButtonManager {
	static int passNumber=1;
	static int turnNumber=0;
	static ArrayList<ArrayList<Buttons>> list=new ArrayList<ArrayList<Buttons>>(); //liste contenant les boutons de la grille
	static JPanel ButtonsPanel=new JPanel(); //JPanel contenant les boutons de la grille
	static Buttons CurrentPlayerButtonClicked=null; //le boutant courant cliqué par un joueur
	public static ArrayList<Buttons> GridClickedButtons=new ArrayList<Buttons>(); //Une liste des boutons cliqués sur la grille
	public static ArrayList<Buttons> PlayerClickedButtons=new ArrayList<Buttons>(); //Une liste des boutons cliqués par un joueur
	public static int CurrentPlayerTurn =0; //0 si le 1er joueur joue , 1 si le 2eme joueur joue
	public static ArrayList<String> dictionnaire=new ArrayList<String>(); //liste contenant le dictionnaire
	static {
		try {
			String ligne ;
			BufferedReader fichier = new BufferedReader(new FileReader("C:\\Users\\mahdi\\Documents\\GitHub\\ScrabbleJava\\src\\dictionnaire.txt"));
		while ((ligne = fichier.readLine()) != null) 
		{
			ligne=ligne.toLowerCase();
			dictionnaire.add(ligne); //ajout du dictionnaire dans l'array list
		}
		fichier.close();
		}catch (Exception e) {
			e.printStackTrace();
		} 
		ButtonsPanel.setLayout(new GridLayout(15,15)); //La grille est de taille 15*15
		ButtonsPanel.setPreferredSize(new Dimension(600,600));
		ArrayList<Buttons> li=new ArrayList<Buttons>();
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				//li.add(new Buttons(1,j,i,' '));
				Buttons NewButton=new Buttons(1,j,i,' ');
				NewButton.getButton().setFont(new Font("Tahoma", Font.PLAIN, 9));
				li.add(NewButton);
				ButtonsPanel.add(li.get(j).getButton()); //remplir la grille par les 15*15 boutons
			}
			list.add(li); //ajout d'une ligne des boutons dans la liste des boutons de la grille
			li=new ArrayList<Buttons>();
		}
		list.get(7).get(7).getButton().setText("*"); //La case au milieu de la grille doit contenir une étoile
	}
	public static void onButtonClick(Buttons btn) { //Bouton cliqué par un joueur
		if(btn.getPlayer()==null) { //Le bouton n'admet pas de joueur =>bouton dans la grille
			if(CurrentPlayerButtonClicked!=null) { //Avant de joueur , on doit verifier que le joueur a sélectionné une lettre
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
			else {
				if(!btn.getButton().getText().equals(" ")) {
					
					for( Buttons b :PlayerClickedButtons) {
						if(Character.toString( b.getValue()).equals( btn.getButton().getText())){
							b.getButton().setEnabled(true);
							b.getButton().setText(Character.toString(b.getValue()));
							PlayerClickedButtons.remove(b);
							GridClickedButtons.remove(btn);
							btn.getButton().setText(" ");
							break;
						}
					}
					
					
				}
			}
		}//Fin de la condition(Bouton dans la grille)
		else {
			CurrentPlayerButtonClicked=btn;//Si le bouton cliqué est un bouton d'un joueur , Le bouton courant cliqué prend la valeur du bouton 
		}
		
	}
	public static void ReturnButtons() { //Méthode permettant de retourner les boutons de l'utilisateur lors de l'échec d'un mot
		for(Buttons b : PlayerClickedButtons) { //Parcours des boutons cliqués par l'utilisateur
			b.getButton().setText(Character.toString( b.getValue()));//Retourner la valeur du bouton
			b.getButton().setEnabled(true);//Le bouton est accessible pour choisir
		}
		for(Buttons b : GridClickedButtons) {
			b.getButton().setText(" ");//Modifier la valeur des boutons cliqués sur la grille par un espace
			if((b.getPosX()==7)&&(b.getPosY()==7)) {
				b.getButton().setText("*"); //La case centrale contient une étoile
			}
		}
	}
	public static void endTrun(boolean testEval) {//Méthode à appeler lors du fin du tour d'un joueur
		
		CurrentPlayerButtonClicked=null;
		
		Random r = new Random();
		if(testEval) { //Si le mot est valide
			for(Buttons b:PlayerClickedButtons) {//Parcours des boutons cliqués par l'utilisateur
				if(PlayerManager.alphabets.length()!=0) {//S'il existe encore des lettres disponibles pour compléter les lettres du joueur
					int randomInt=r.nextInt(PlayerManager.alphabets.length()); 
					String s=Character.toString(PlayerManager.alphabets.charAt(randomInt)); //Génération d'une lettre aléatoire
					PlayerManager.alphabets = PlayerManager.alphabets.replaceFirst(Character.toString(PlayerManager.alphabets.charAt(randomInt)), "");//Suppression de la lettre aléatoire de la chaine des alphabets
					b.getButton().setText(s);//Le bouton de l'utilisateur prend la valeur de la lettre aléatoire
					b.setValue(s.charAt(0));
				}
				else { //Si n'existe pas des lettres de l'alphabet à appeler
					endGame(); //Fin du jeu
				}
				
			}
			for(Buttons b :GridClickedButtons) {
				b.getButton().setEnabled(false);//Les boutons cliqués sur la grille ne seront plus disponibles pour cliquer
			}
			turnNumber++;
			//System.out.println();
		
		}
		//Si le mot n'est pas valide , on retourne les boutons du joueur
		else {
			ReturnButtons();
		}
		if(CurrentPlayerTurn==0) { //Si le 1er joueur a joué
			
			for(Buttons b:PlayerManager.listP1) {//Les boutons du 1er joueur sont bloqués
				b.getButton().setEnabled(false);
			}
			for(Buttons b:PlayerManager.listP2) {//Les boutons du 2éme joueur sont débloqués
				b.getButton().setEnabled(true);
			}
			CurrentPlayerTurn=1;//on passe au tour du 2éme joueur
		}
		else {//Si le 2éme joueur a joué
			for(Buttons b:PlayerManager.listP2) {//Les boutons du 2éme joueur sont bloqués
				b.getButton().setEnabled(false);
			}
			for(Buttons b:PlayerManager.listP1) {//Les boutons du 1er joueur sont débloqués
				b.getButton().setEnabled(true);
			}
			
			CurrentPlayerTurn=0;//on passe au tour du 2éme joueur
		}
		
		PlayerClickedButtons.clear();//Les 2 listes des boutons cliqués par le joueur et sur la grille seront effacées
		GridClickedButtons.clear();
	}
	public static boolean VerifWord(String word)
	{
		return(dictionnaire.contains(word)); //Vérifie si le mot est disponible ou pas dans le dictionnaire Français
		//return true;
	}
	public static int VerifHorizonOrVerti() { //Vérifie si le mot entré par le joueur est vertical ou horizontal
		boolean horizontal=true;
		boolean vertival=true;

		for(int i =1;i<GridClickedButtons.size();i++) {//Parcours des lettres cliqués sur la grille
			if(GridClickedButtons.get(i).getPosX()!=GridClickedButtons.get(0).getPosX()) { 
				vertival=false; //Vérification si le mot n'est pas vertical
			}
			if(GridClickedButtons.get(i).getPosY()!=GridClickedButtons.get(0).getPosY()) {
				horizontal=false;//Vérification si le mot n'est pas horizontal
			}
		}
		if(vertival) {
			return 1;//Si le mot est vertical on retourne 1
		}
		else if(horizontal) {
			return 0;//Si le mot est horizontal on retourne 0
		}
		else {
			return -1;//sinon on retourne -1 (mot n'est pas valide)
		}
	}
	public static void endGame() {//Méthode pour arrêter le jeu , en appelant la fenetre EndGame
		EndGame endGame=new EndGame();
		endGame.setVisible(true);
		ScrabbleGameFrame.GameFrame.setVisible(false);
		System.out.println("haha");
		
	}
	public static int evaluate() {
		int totalscore=0;
		int vscore=0;
		int hscore=0;
		if(turnNumber==0) {
			if((list.get(7).get(7).getButton().getText().equals("*"))||(list.get(7).get(7).getButton().getText().equals(" "))) {
				JOptionPane.showMessageDialog(null, "You have to start from he center of the grid!!");
				System.out.println("wrong");
				for(Buttons b:GridClickedButtons) {
					b.getButton().setEnabled(true);
					b.getButton().setText(" ");
				}
				list.get(7).get(7).getButton().setText("*");
				GridClickedButtons.clear();
				return -1;
			}
		}
		ArrayList<Buttons> NewGridClickedButtons=new ArrayList<Buttons> (GridClickedButtons);
		

		if(GridClickedButtons.size()==0) {
			//Aucun chiffre 
			//System.out.println("no chiffre");
			passNumber++;
			if(passNumber>3) {
				endGame();
			}
			return -1;
		}
		else if(GridClickedButtons.size()==1) {
			String VerticalWordH="";
			String HorizontalWordH="";
			int X=GridClickedButtons.get(0).getPosX();
			int Y=GridClickedButtons.get(0).getPosY();
			passNumber=1;
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
			String VerticalWordH="";
			String HorizontalWordH="";
			int X=GridClickedButtons.get(0).getPosX();
			int Y=GridClickedButtons.get(0).getPosY();
			passNumber=1;
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