import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Buttons  implements ActionListener { //Classe des boutons contenant le score , les coordonnées de chaque point , le JButton ,et le joueur corresponant (NULL si c'est un bouton de la grille)
	private int score;
	private int PosX;
	private int PosY;
	private JButton button;
	private char Value;
	private Player player=null;
	public Buttons(int score, int posX, int posY,char Value)  { //Constructeur pour les boutons de la grille
		super();
		this.score = score;
		PosX = posX;
		PosY = posY;
		this.Value=Value;
		button=new JButton(Character.toString(Value));
		button.addActionListener(this);
		button.setPreferredSize(new Dimension(45,45));
		button.setFont(new Font("Arial", Font.BOLD, 7));
		button.setBackground(new Color(240, 240, 240));
	}
	public Buttons(int score, int posX, int posY,char Value,Player p)  { //Constructeur pour les boutons des joueurs
		super();
		this.score = score;
		PosX = posX;
		PosY = posY;
		this.Value=Value;
		button=new JButton(Character.toString(Value));
		button.addActionListener(this);
		button.setPreferredSize(new Dimension(45,45));
		button.setBackground(new Color(240, 240, 240));
		player=p;
	}
	
	public Player getPlayer() { //getter du joueur
		return player;
	}
	public void setPlayer(Player p) { //setter du joueur
		this.player = p;
	}
	public int getScore() { //getter du score ( dépend de la lettre du bouton)
		char c=button.getText().charAt(0);
			switch(c) {
			case 'a','e','i','l','o','r','s','t','u','n':
				return 1;
			case 'b','c','m','p':
				return 3;
			case 'd','g':
				return 2;
			case 'f','h','v','w','y':
				return 4;
			case 'k':
				return 5;
			case 'j','x':
				return 8;
			case 'z','q':
				return 10;
			default : 
				return 1;
			}
	}
	public void setScore(int score) { //setter du score
		this.score = score;
	}
	public int getPosX() { //getter de la position des abscisses
		return PosX;
	}
	public void setPosX(int posX) { //setter de la position des abscisses
		PosX = posX;
	}
	public int getPosY() {//getter de la position des ordonnées
		return PosY;
	}
	public void setPosY(int posY) {//setter de la position des ordonnées
		PosY = posY;
	}
	public JButton getButton() { //getter du JButton
		return button;
	}
	public char getValue() { //getter de la valeur de la  lettre
		return Value;
	}
	public void setValue(char val) { //setter de la valeur de la lettre
		Value=val;
		button.setText(Character.toString(val));
	}
	@Override
	public void actionPerformed(ActionEvent e) { //Action à réaliser si le bouton est cliqué
		ButtonManager.onButtonClick(this);
	}


	
}
