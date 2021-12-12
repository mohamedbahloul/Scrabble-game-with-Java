
public class Player { //Chaque joueur contient un score et un pseudo
	
	int score;
	String pseudo;
	public Player(String pseudo) { //Constructeur
		this.pseudo=pseudo;
	}
	public int getScore() { //Getters et setters
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
}
