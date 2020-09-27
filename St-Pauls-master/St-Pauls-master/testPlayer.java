/*
 * Group - St-Pauls
 * Author - Thomas Thornton
 * Group members:
 * 	-Brian Byrne
 * 	-Zach Dunne
 * */ 
public class testPlayer {
	public static void main(String[] args) {
		//initialize game
		Pool.setPool();
		Frame jackFrame = new Frame();
		Frame patFrame = new Frame();
		Player jack = new Player("Jack", jackFrame);
		Player pat = new Player("Pat", patFrame);
		Board.setBoard();
		Board.printBoard();
		
		System.out.print("Refill Jack's frame: "); jackFrame.refillFrame();
		System.out.print("Refill Pat's frame: "); patFrame.refillFrame();
		System.out.println("\nSize of Jack's frame before reset: " + jackFrame.size());
		jackFrame.resetFrame();
		System.out.println("Size of Jack's frame after reset: " + jackFrame.size());
		System.out.print("\nRefill Jack's frame: "); jackFrame.refillFrame();
		System.out.print("\nJack's frame: "); jackFrame.showFrame();
		jackFrame.removeLetters(3);
		System.out.print("Jack's frame after 4th letter is removed: "); jackFrame.showFrame();
		System.out.print("\nRefill Jack's frame: ");jackFrame.refillFrame();
		System.out.println("\nJack's frame after refillFrame: " + jack.getFrame());
		System.out.println("\nIncrease Jack's score by 50"); jack.increaseScore(50);
		System.out.println(jack.getName() + "'s score: " + jack.getScore());
		System.out.println(pat.getName() + "'s score: " + pat.getScore());
	}
}
