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
		button.setFont(new Font("Arial", Font.PLAIN, 7));
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
		player=p;
	}
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player p) {
		this.player = p;
	}
	
	public int getScore() {
		return score;
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
