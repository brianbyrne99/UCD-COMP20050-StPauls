import java.util.*;

/* Java class pool has the following functionality:
 * 1)Stores the value of each tile
 * 2)Stores the tiles currently in the pool
 * 3)Allows the pool to be reset
 * 4) Allows display of the number of tiles in the pool
 * 5)Allows the pool to be checked to see if it is empty
 * 6)Allows tiles to be drawn at random from the pool
 * 7)Allows the value of a tile to be queried
 */

/*
 * Group - St-Pauls
 * Author - Brian Byrne
 * Group members:
 * 	-Thomas Thornton 
 * 	-Zach Dunne
 * */

public class Pool {
static HashMap<Character, Integer> value = new HashMap<>();
/*HashMap enables each unique character to be assigned to its game value
* and allows the value to be returned via a simple search method
*/

static LinkedList<Tile> tiles = new LinkedList<Tile>();
/*Linked list tiles is a list of all elements
*of type Tile in the pool before and during the game
*/

static Random random = new Random();// allows one unique object tile to be chosen at random

	public static void assign() {   //assign function is used for the querying of Tile values using the letter as a key
        value.put('A', 1);         //this assigns 'A' its integer value of '1' and adds it to the hashMap value
        value.put('B', 3);
        value.put('C', 3);
        value.put('D', 2);
        value.put('E', 1);
        value.put('F', 4);
        value.put('G', 2);
        value.put('H', 4);
        value.put('I', 1);
        value.put('J', 8);
        value.put('K', 5);
        value.put('L', 1);
        value.put('M', 3);
        value.put('N', 1);
        value.put('O', 1);
        value.put('P', 3);
        value.put('Q', 10);
        value.put('R', 1);
        value.put('S', 1);
        value.put('T', 1);
        value.put('U', 1);
        value.put('V', 4);
        value.put('W', 4);
        value.put('X', 8);
        value.put('Y', 4);
        value.put('Z', 10);
        value.put(' ', 0);// this blank char represents the blank tile that can be used as any letter in the game of scrabble
	}
	
	public static void setPool() {//setPool fills up our pool linked list with all the tiles required to begin a a game of "Scrabble".
		/*   These for loops add the specified amount of tiles of a certain letter to the initial pool.
		 *   I have organized these in descending order starting with the largest
		 */
		int i;
		for(i=0;i<12;i++)                 
		{
			tiles.add(new Tile('E'));  //12 tiles of letter E are added to the pool
		}
		for(i=0;i<9;i++)
		{
			tiles.add(new Tile('A'));
			tiles.add(new Tile('I'));
		}
		for(i=0;i<8;i++)
		{
			tiles.add(new Tile('O'));
		}
		for(i=0;i<6;i++)
		{
			tiles.add(new Tile('N'));
			tiles.add(new Tile('R'));
			tiles.add(new Tile('T'));
		}
		for(i=0;i<4;i++)
		{
			tiles.add(new Tile('D'));
			tiles.add(new Tile('L'));
			tiles.add(new Tile('S'));
			tiles.add(new Tile('U'));
		}
		for(i=0;i<3;i++)
		{
			tiles.add(new Tile('G'));
		}
		for(i=0;i<2;i++)
		{
			tiles.add(new Tile('B'));
			tiles.add(new Tile('C'));
			tiles.add(new Tile('F'));
			tiles.add(new Tile('H'));
			tiles.add(new Tile('M'));
			tiles.add(new Tile('P'));
			tiles.add(new Tile('V'));
			tiles.add(new Tile('Y'));
			tiles.add(new Tile(' '));
			tiles.add(new Tile('W'));
		}
		tiles.add(new Tile('J'));   // Since these characters are only added once there is no need for a loop to add these tiles to our pool
		tiles.add(new Tile('X'));
		tiles.add(new Tile('Q'));
		tiles.add(new Tile('Z'));
		tiles.add(new Tile('K'));
	
	}
	
	public static int query(char search)
	{
		/* Method "query()" is a simple hashMap search that uses input 
		 * to return an integer value corresponding to the letter of the tile entered into the method
		 */
		assign();	
		int y=0;
		if(value.containsKey(search))
		{
			y = value.get(search);
		}
		else
			throw new IllegalArgumentException("Error no such tile exists in scrabble");
		return y;	
	}
	
	public static boolean currentPoolTiles(){
        boolean empty = false;
        //this method informs the user of the remaining tiles in the pool
     	 if(tiles.size()==0) {
       		 System.out.println("No tiles remaining in pool");  //if linked list is empty, we know the pool is now empty
        	 empty = true;
    	}
    	else {
          System.out.println("Number of tiles remaining in pool: " + tiles.size());//tiles.size() returns the length of the linked list in
      	}

        return empty;
    	}                                                                                  // it's current state
	
	public static void randomSelection(LinkedList<Tile> frame, int amount)
	{
		/*  This method allows the player to withdraw a specified number of tiles for their frame, from the pool.
		 *  amount should be calculated as 7-(no. of tiles remaining in frame)
		 *  Using random.nextInt we can remove a tile from the pool, and add it to the players frame simultaneously
		 */
		
		if(tiles.size() >= amount)
		{
			for(int i=0;i<amount;i++) { 
				int x = random.nextInt(tiles.size());
	       			Tile tile = tiles.get(x);
		     		tiles.remove(tile);
		     		frame.add(tile);
			}
		}
		/*
		 * This condition is only for when less tiles are left in the pool than are required by the player
		 * for their frame. In this case they will use the remainder of the bag
		 * 
		 * In the case of no tiles remaining in the pool, no more tiles will be added to the players frame
		 */
		
		if(tiles.size() != 0 && tiles.size() < amount)
		{
			for(int i=0;i<tiles.size();i++) { 
				int x = random.nextInt(tiles.size());
		        	Tile tile = tiles.get(x);
			     	tiles.remove(tile);
			     	frame.add(tile);
			}
		}
		currentPoolTiles();//calling currentPoolTiles will display to the player how many, if any tiles are left in the pool
   	}
	
	public static void addBackLetters(LinkedList<Tile> replace){
		tiles.addAll(replace);
		currentPoolTiles();//calling currentPoolTiles will display to the player how many, if any tiles are left in the pool
	}

}
