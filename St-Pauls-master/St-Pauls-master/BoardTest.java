
public class BoardTest {
   /*
    *  Few simple tests to show the playing of the scrabble game without word checking
    */
	public static void main(String[] args) {
		
		//Printing the board in ASCII
		Board.setBoard();
		Board.printBoard();
		System.out.println("ABOVE: Blank board");
		
		//Adding the first character to the board
		Tile tester = new Tile('B');
		Board.firstPlay(tester);
		Board.printBoard();
		System.out.println("ABOVE: Notice the B tile being placed at the centre of the board");
		
        //Adding a connecting letter to the first placement
		Tile tester2 = new Tile('A');
		Board.placeTile(tester2,7,8);
		Board.printBoard();
		System.out.println("ABOVE: placing a second tile that has to be connected to another tile");
		
		//Adding another connecting letter to the first placement
		Tile tester3 = new Tile('D');
		Board.placeTile(tester3,7,9);
		Board.printBoard();
		System.out.println("ABOVE: We form our first word following the rules of scrabble");
		
		//adding a tile vertically 
		Board.placeTile(tester3,8,8);
		Board.printBoard();
		System.out.println("ABOVE: Adding a tile vertically");
		
		//Forming further connections
		Board.placeTile(tester,9,8);
		Board.placeTile(tester3,9,7);
		Board.placeTile(tester3,9,6);
		Board.printBoard();
		System.out.println("ABOVE: Further branching ");
		
		//Constraints for players
		Board.placeTile(tester,0,0);
		Board.placeTile(tester,11,3);
		Board.placeTile(tester,2,7);
		Board.placeTile(tester,9,4);
		Board.printBoard();
		System.out.println("ABOVE:The above placements are not completed as they break the rules of scrabble");
		System.out.println("Indicated by warning statements");
		//Resetting the board
		Board.setBoard();
		Board.printBoard();
		System.out.println("ABOVE: the board can be reset");
		

	}

}
