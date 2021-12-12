import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Buttons  implements ActionListener {
	private int score;
	private int PosX;
	private int PosY;
	private JButton button;
	private char Value;
	private Player player=null;
	public Buttons(int score, int posX, int posY,char Value)  {
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
	public Buttons(int score, int posX, int posY,char Value,Player p)  {
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
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player p) {
		this.player = p;
	}
	public int getScore() {
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
	public void setScore(int score) {
		this.score = score;
	}
	public int getPosX() {
		return PosX;
	}
	public void setPosX(int posX) {
		PosX = posX;
	}
	public int getPosY() {
		return PosY;
	}
	public void setPosY(int posY) {
		PosY = posY;
	}
	public JButton getButton() {
		return button;
	}
	public char getValue() {
		return Value;
	}
	public void setValue(char val) {
		Value=val;
		button.setText(Character.toString(val));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		ButtonManager.onButtonClick(this);
	}


	
}
