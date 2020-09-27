import java.util.*;

/*
 * Group - St-Pauls
 * Author - Zach Dunne
 * Group members:
 * 	-Brian Byrne
 * 	-Thomas Thornton 
 * */

public class Player {

	String name; 		//variable to hold the player's score
	int score=0;		//variable to hold the player's score
	private Frame frame;
	
	public Player(String name, Frame frame) {
		setName(name);		//constructor for player class
		this.frame = frame;
	}
	
	public String getName() {
		return name;		//accesses player's name
	}
	
	public void setName(String x) {
		name = x;			//sets player's name
	}
	
	public void resetData() {
		setName(null);		//resets player's name
		setScore(0);		//resets player's score
		frame.resetFrame();
	}
	
	public void increaseScore(int x) {
		score += x;			//increases player's score by whatever points they've gained
	}
	
	public int getScore() {
		return score;		//accesses player's score
	}
	
	public void setScore(int x) {
		score = x;			//sets player's score
	}
	
	public LinkedList<Tile> getFrame() {
		return frame.getFRAME();	//accesses the player's frame
	}
		
}