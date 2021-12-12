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
			BufferedReader fichier = new BufferedReader(new FileReader("C:\\Users\\bahlo\\OneDrive\\Documents\\GitHub\\ScrabbleJava\\src\\dictionnaire.txt"));
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
			if(CurrentPlayerButtonClicked!=null) { //Avant de jouer , on doit verifier que le joueur a sélectionné une lettre
				if(GridClickedButtons.contains(btn)) { //si le joueur a selectionné un bouton dans la grille qui contient deja un chiffre il va ecrasé la valeur de ce dernier et ajouter la valeur recente
					
					for(int i=0;i<PlayerClickedButtons.size();i++) { //chercher le bouton clické dans la liste des boutons dans la main du joueur 
						if(PlayerClickedButtons.get(i).getValue()==btn.getButton().getText().charAt(0)) {	//la condition que le boutons existe dans la main du joueur 					
							PlayerClickedButtons.get(i).getButton().setEnabled(true); //activer le bouton 
							PlayerClickedButtons.get(i).getButton().setText(Character.toString(PlayerClickedButtons.get(i).getValue()));//changer la valeur 
							break;//sortir du boucle si le bouton est trouvé
						}
					}
				}else {
					GridClickedButtons.add(btn);//si le bouton du grille n'est pas clique on l'ajoute à la liste des boutons cliqués
				}
				PlayerClickedButtons.add(CurrentPlayerButtonClicked);//ajouter le bouton de la main du joueur à la liste des boutons cliqués de la main du joueur 
				btn.getButton().setText(Character.toString( CurrentPlayerButtonClicked.getValue()));//changer la valeur du bouton
				CurrentPlayerButtonClicked.getButton().setText("");//le bouton dans la main recoit comme valeur le vide
				CurrentPlayerButtonClicked.getButton().setEnabled(false);//desactiver le bouton		
				CurrentPlayerButtonClicked=null;//le bouton cliqué courant recoit null
			}
			else {//si le joueur clique sur un bouton de la grille mais il n'a pas cliqué sur un bouton de ses boutons 
				if(!btn.getButton().getText().equals(" ")) {//si le bouton qu'il a cliqué dans la grille contient une valeur
					
					for( Buttons b :PlayerClickedButtons) {//chercher le bouton clické dans la liste des boutons dans la main du joueur 
						if(Character.toString( b.getValue()).equals( btn.getButton().getText())){//la condition que le boutons existe dans la main du joueur
							b.getButton().setEnabled(true);//activer le bouton 
							b.getButton().setText(Character.toString(b.getValue()));//changer la valeur 
							PlayerClickedButtons.remove(b);//supprimer le bouton de la liste des boutons cliqué
							GridClickedButtons.remove(btn);//supprimer le bouton de la liste des boutons cliqué
							btn.getButton().setText(" ");//la valeur du bouton de la grille recoit une chaine vide
							break;//sortir du boucle si le bouton est trouvé
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
		//return(dictionnaire.contains(word)); //Vérifie si le mot est disponible ou pas dans le dictionnaire Français
		return true;
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
		int totalscore=0;// le score totale de la manche
		int vscore=0;//le score du mot vertical
		int hscore=0;//le score du mot horizontal
		if(turnNumber==0) {//si c'est le premier tour 
			if((list.get(7).get(7).getButton().getText().equals("*"))||(list.get(7).get(7).getButton().getText().equals(" "))) {//verifier si la valeur du boutons au centre est vide ou non
				JOptionPane.showMessageDialog(null, "You have to start from he center of the grid!!");//si il est vide afficher une alerte que le joueur doit débuter de la centre de la grille
				for(Buttons b:GridClickedButtons) {
					b.getButton().setEnabled(true);//activer tous les boutons de la grille
					b.getButton().setText(" ");//tous les boutons recoivent une chaine vide
				}
				list.get(7).get(7).getButton().setText("*");//le boutons du centre recoit une etoile
				GridClickedButtons.clear();//vider la liste
				return -1;//retourner que la chaine n'est pas correcte
			}
		}
		ArrayList<Buttons> NewGridClickedButtons=new ArrayList<Buttons> (GridClickedButtons);//déclarer une autre liste qui contient tous les boutons cliqué de la grille
		

		if(GridClickedButtons.size()==0) {//si le joueur n'a selectionné aucun chiffre
			//Aucun chiffre 
			//System.out.println("no chiffre");
			passNumber++;
			if(passNumber>3) {//tester si les deux joueur ont cliqué sur le bouton end turn 3 fois succéssive
				endGame();//si oui terminer le jeu
			}
			return -1;//retourner -1 pour que le programme ne 
		}
		else if(GridClickedButtons.size()==1) {//si le joueur a choisi deux boutons
			String VerticalWordH="";//la chaine verticale
			String HorizontalWordH="";//la chaine horizontale
			int X=GridClickedButtons.get(0).getPosX();//la valeur horizontale dans la liste de la  1 er bouton cliqué
			int Y=GridClickedButtons.get(0).getPosY();//la valeur verticale dans la liste de la  1 er bouton cliqué
			passNumber=1;//le nombre de passe initialisé à 1
			int k=0;
			for(int i=X;i>=0;i--) {//parcourir la ligne horizontale à gauche de la liste en commencant par le 1 er bouton cliqué 
				if(!list.get(Y).get(i).getButton().getText().equals(" ")) {//si le bouton contient une valeur
					
					HorizontalWordH=list.get(Y).get(i).getButton().getText()+HorizontalWordH;//ajouter la valeur du bouton à la chaine horizontale
					hscore+=list.get(Y).get(i).getScore();//ajouter le score de la valeur du bouton
					if(GridClickedButtons.contains(list.get(Y).get(i))) {//verifier is le bouton à cliqué dans cette manche sur le bouton actuelle
						/********Verification Left top****/////
						for(int j=Y;j>=0;j--) {//parcourir le verticale en commençant par l bouton actuelle 
							if(!list.get(j).get(i).getButton().getText().equals(" ")) {//si la valeur du bouton n'est pas vide
								VerticalWordH=list.get(j).get(i).getButton().getText()+VerticalWordH;////ajouter la valeur du bouton à la chaine vertivale
								vscore+=list.get(j).get(i).getScore();//ajouter le score du bouton au score verticale
							}
							else {
								break;// on sort de la boucle s'il y a un bouton dans sa valeur est nulle
							}
						}
						/********Verification left down****/////
						//meme mais en parcourant vers le bas 
						for(int j=Y+1;j<15;j++) {
							if(!list.get(j).get(i).getButton().getText().equals(" ")) {
								VerticalWordH+=list.get(j).get(i).getButton().getText();
								vscore+=list.get(j).get(i).getScore();
							}
							else {
								break;
							}
						}
						if(VerticalWordH.length()>1) {//si la longeur du mot est > à 1 c'est à dire que si c'est une seul caractere on n'a pas besoin de le verifier
							if(VerifWord(VerticalWordH)==false) {//verifier la mots
								System.out.println(VerticalWordH +"    "+VerifWord(VerticalWordH));//si fausse l'afficher
								return -1;//retourner que la mot est fausse
							}
							else {
								totalscore+=vscore;//sinon ajouter du score horizontale au score totale
							}
							k++;//augmenter la valeur de k c'est à dire qu'il y a une mot vertical verifié car le joueur peut saisir un seul chiffre pour valider une autre mot 
						}
						vscore=0;//initialiser le score verticale à 0
						VerticalWordH="";//initialiser la chaine verticale à 0
						}
				}
				else {
					break;//sortir du boucle si le boutons ne contient pas une valeur
				}
			}
			for(int i=X+1;i<15;i++) {//parcourir la ligne horizontale à droite de la liste en commencant par le 1 er bouton cliqué 
				// meme chose pour verifier la chaine à gauche
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
			
			if(HorizontalWordH.length()==1) {//si la longeur du mot trouvé égale à 1
				if(k==0) {//pour ne pas verifier le chiffre deux fois
					if(VerifWord(HorizontalWordH)==false) {//verifier le mot horizontale
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
		else { //si le joueur a saisi plus qu'un chiffre faire presque le meme qu'un seul chiffre en verifiant l'horizontale et puis le verticale de chaque bouton en horizontale
			
			String VerticalWordH="";
			String HorizontalWordH="";
			int X=GridClickedButtons.get(0).getPosX();
			int Y=GridClickedButtons.get(0).getPosY();
			passNumber=1;
			int Verif=VerifHorizonOrVerti();//verifier si le mot est horizontale ou verticale
			if(Verif==0) {//si le mot est horizontale
				/*Verification gauche*/
				for(int i=X;i>=0;i--) {
					if(!list.get(Y).get(i).getButton().getText().equals(" ")) {
						if(NewGridClickedButtons.contains(list.get(Y).get(i))) {//si le boutons existe 
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
				/*Verification droite*/
				for(int i=X+1;i<15;i++) {
					if(!list.get(Y).get(i).getButton().getText().equals(" ")) {
						if(NewGridClickedButtons.contains(list.get(Y).get(i))) {
							NewGridClickedButtons.remove(list.get(Y).get(i));
						}
						HorizontalWordH+=list.get(Y).get(i).getButton().getText();
						hscore+=list.get(Y).get(i).getScore();
						if(GridClickedButtons.contains(list.get(Y).get(i))) {
							/********Verification right top****/////
							for(int j=Y;j>=0;j--) {
								if(!list.get(j).get(i).getButton().getText().equals(" ")) {
									VerticalWordH=list.get(j).get(i).getButton().getText()+VerticalWordH;
									vscore+=list.get(j).get(i).getScore();
								}
								else {
									break;
								}
							}
							/********Verification right down****/////
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
				//si le joueur a saisi des chiffre éparpillé dans la grille,verifier la chaine du 1 er chiffre qu'il a saisi seulement et retourner les autres 
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
			//verifier verticalement
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
							/********Verification right top****/////
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
				/***verification down****/
				for(int i=Y+1;i<15;i++) {
					if(!list.get(i).get(X).getButton().getText().equals(" ")) {
						if(NewGridClickedButtons.contains(list.get(i).get(X))) {
							NewGridClickedButtons.remove(list.get(i).get(X));
						}
						VerticalWordH+=list.get(i).get(X).getButton().getText();
						vscore+=list.get(i).get(X).getScore();
						if(GridClickedButtons.contains(list.get(i).get(X))) {
						/********Verification Left down****/////
							for(int j=X;j>=0;j--) {
								if(!list.get(i).get(j).getButton().getText().equals(" ")) {
									HorizontalWordH=list.get(i).get(j).getButton().getText()+HorizontalWordH;
									hscore+=list.get(i).get(j).getScore();
								}
								else {
									break;
								}
							}
							/********Verification right down****/////
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
}