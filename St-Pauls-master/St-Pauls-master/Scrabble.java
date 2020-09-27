import java.util.Scanner;
import java.util.LinkedList;
import java.io.*;

import javafx.application.Platform;
/*
 * Group - St-Pauls
 * Author - Collaborative
 * Group members:
 * 	-Brian Byrne
 * 	-Zach Dunne
 *  -Thomas Thornton
 * */
public class Scrabble {
    public static LinkedList<String> checker= new LinkedList<>();
    public static SearchTree tree= new SearchTree();
    public static SearchTree.treeNode top= new SearchTree.treeNode(null);
    static boolean  player1_move = true;
    static boolean firstPlay = true;
    protected static void gameLoop(){
        //display which players move it is
        if((UI.frame1.emptyFrame() || UI.frame2.emptyFrame()) && Pool.currentPoolTiles()){
            UI.gameOver();
        }
        if(player1_move) {
            UI.move_label.setText("Enter move " + UI.player1.getName() + ":");
        } else{
            UI.move_label.setText("Enter move " + UI.player2.getName() + ":");
        }
        //Take action on players input when they click submit button
        UI.submit.setOnAction(e ->
                {
                    //parse the input in to the starting coordinates, direction and word
                    String in = UI.player_input.getText();
                    UI.player_input.clear();
                    String[] arr_of_input = in.split(" ", 3);
                    if(arr_of_input.length == 1){
                        handleUserCommands(arr_of_input[0]);
                    } else if(arr_of_input.length == 2){
                        if (player1_move) {
                            exchangeLetters(arr_of_input[0], arr_of_input[1], UI.frame1);
                        } else {
                            exchangeLetters(arr_of_input[0], arr_of_input[1], UI.frame2);
                        }
                    } else if(arr_of_input.length == 3){
                        String inputWord = arr_of_input[2];
                        char[] coordinates = arr_of_input[0].toCharArray();
                        //find x coordinate
                        int x = coordinates[0] - 'A';
                        boolean horizontal = false;

                        if (arr_of_input[1].equals("A")) {
                            horizontal = true;
                        } else if (arr_of_input[1].equals("D")) {
                            horizontal = false;
                        } else {
                            UI.invalidEntry();
                        }

                        //find y coordinate (we -1 so that it matches our board, which goes from 0-14)
                        int y = 0;
                        if (coordinates.length == 2) {
                            y = Character.getNumericValue(coordinates[1]) - 1;
                            y = 14 - y;
                        } else if (coordinates.length == 3) {
                            //if y coordinate > 9 we must do this
                            y = 9 + Character.getNumericValue(coordinates[2]);
                            y = 14 - y;
                        } else {
                            UI.invalidEntry();
                        }
                        //call checkSpace to see if there's room on the board for their move
                        checkSpace(x, y, horizontal, inputWord.length());
                        //create an array of tiles that stores the tiles on the board in the position they're trying to place their word
                        Tile[] tiles_on_board = populateArrayWithBoardTiles(x, y, horizontal, inputWord.length());
                        //create an array of the squares each tile is placed on. This is used to calculate score
                        tileType[] bonus_squares = populateArrayWithBoardSquares(x, y, horizontal, inputWord.length(), tiles_on_board);

                        //This is the point at which the word connects to the tiles already on the board
                        int contact_point = 0;
                        //find letter in word[] that isn't null to use as starting point to place tiles
                        for (int i = 0; i < tiles_on_board.length; i++) {
                            if (tiles_on_board[i] != null) {
                                contact_point = i;
                            }
                        }
                        if(player1_move) {
                            //checks if their word connects to the board and if they have the letters for it in the frame
                            if (!firstPlay && checkWordValidity(tiles_on_board, inputWord, UI.frame1, x, y, horizontal)) {
                                playWord(x, y, contact_point, UI.frame1, horizontal, tiles_on_board, inputWord, bonus_squares);
                            } else if (firstPlay && checkWordValidity(tiles_on_board, inputWord, UI.frame1, x, y, horizontal)) {
                                playFirstWord(x, y, UI.frame1, horizontal, tiles_on_board, inputWord, bonus_squares);
                            }
                        } else{
                            //checks if their word connects to the board and if they have the letters for it in the frame
                            if (!firstPlay && checkWordValidity(tiles_on_board, inputWord, UI.frame2, x, y, horizontal)) {
                                playWord(x, y, contact_point, UI.frame2, horizontal, tiles_on_board, inputWord, bonus_squares);
                            } else if (firstPlay && checkWordValidity(tiles_on_board, inputWord, UI.frame2, x, y, horizontal)) {
                                //this is necessary in the rare case that player 1 exchanges tiles on the first move
                                playFirstWord(x, y, UI.frame2, horizontal, tiles_on_board, inputWord, bonus_squares);
                            }
                        }
                    } else{
                        UI.invalidEntry();
                    }
                }
        );
    }
    public static void readIN() {
//reads in the dictionary file line for line
        try {
            File dictionary = new File("utilities/dictionary.txt");
            Scanner sc = new Scanner(dictionary);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                checker.add(data);
            }
            sc.close();
            top=tree.convertList2Binary(tree.root);
            //Test to show that the tree works
            System.out.println(checker.size());
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found");
            e.printStackTrace();
        }
    }

    public static boolean dictionaryCheck(String word) {
      //checks the dictionary
        return checker.contains(word);
    }

    private static void playWord(int x, int y, int contact_point, Frame frame, boolean direction, Tile[] letters_on_board, String word, tileType[] bonus_squares){
        char[] letters = word.toCharArray();

        //used to check if player used all 7 tiles, hence eligible for 50 bonus points
        int count = 0;
        for (Tile tile : letters_on_board) {
            if (tile == null)
                count++;
        }

        //we place the tiles from the contact point out so that we're always placing tiles connected to board tiles
        if(direction) {
            //placing words from contact point onwards
            for (int i = contact_point; i < word.length(); i++) {
                if (letters_on_board[i] == null) {
                    Board.placeTile(frame.getTile(letters[i]), x + i, y);
                    letters_on_board[i] = frame.getTile(letters[i]);
                    frame.removeLetters(letters[i]);
                    if(letters[i] == '_'){
                        char blankTileLetter = UI.blankTile();
                        Board.board[x + i][y].setTile(new Tile(blankTileLetter));
                    }
                }
            }
            //placing words from contact point back
            for (int i = contact_point; i >= 0; i--) {
                if (letters_on_board[i] == null) {
                    Board.placeTile(frame.getTile(letters[i]), x + i, y);
                    letters_on_board[i] = frame.getTile(letters[i]);
                    frame.removeLetters(letters[i]);
                    if(letters[i] == ' '){
                        char blankTileLetter = UI.blankTile();
                        Board.board[x + i][y].setTile(new Tile(blankTileLetter));
                    }
                }
            }
        } else {//same as above but for placing a word vertically
            for (int i = contact_point; i < word.length(); i++) {
                if (letters_on_board[i] == null) {
                    Board.placeTile(frame.getTile(letters[i]), x, y + i);
                    letters_on_board[i] = frame.getTile(letters[i]);
                    frame.removeLetters(letters[i]);
                    if(letters[i] == ' '){
                        char blankTileLetter = UI.blankTile();
                        Board.board[x][y + i].setTile(new Tile(blankTileLetter));
                    }
                }
            }
            for (int i = contact_point; i >= 0; i--) {
                if (letters_on_board[i] == null) {
                    Board.placeTile(frame.getTile(letters[i]), x, y + i);
                    letters_on_board[i] = frame.getTile(letters[i]);
                    frame.removeLetters(letters[i]);
                    if(letters[i] == ' '){
                        char blankTileLetter = UI.blankTile();
                        Board.board[x][y + i].setTile(new Tile(blankTileLetter));
                    }
                }
            }
        }
        if(player1_move){
            UI.player1.setScore(UI.player1.getScore() + score(word, bonus_squares, count));
        } else{
            UI.player2.setScore(UI.player2.getScore() + score(word, bonus_squares, count));
        }
        UI.displayScore();
        UI.displayFrame();
        player1_move = !player1_move;
        gameLoop();
    }

    private static void playFirstWord(int x, int y, Frame frame, boolean direction, Tile[] letters_on_board, String word, tileType[] bonus_squares){
        char[] letters = word.toCharArray();

        //used to check if player used all 7 tiles, hence eligible for 50 bonus points
        int count = 0;
        for (Tile tile : letters_on_board) {
            if (tile == null)
                count++;
        }

        int contact_point;
        if(direction) {
            //we place the tiles from the contact point out so that we're always placing tiles connected to board tiles
            contact_point = 7 - x;
            Board.firstPlay(frame.getTile(letters[contact_point]));
            //get the tile that goes at center
            letters_on_board[contact_point] = frame.getTile(letters[contact_point]);
            frame.removeLetters(letters[contact_point]);
            //placing tiles from contact point onwards
            for (int i = contact_point; i < word.length(); i++) {
                if (letters_on_board[i] == null) {

                    //If there's space on the board for the tile we place it
                    Board.placeTile(frame.getTile(letters[i]), x + i, y); //x+i because we need the coordinate they entered plus the place in the word
                    letters_on_board[i] = frame.getTile(letters[i]);
                    frame.removeLetters(letters[i]);
                    if(letters[i] == ' '){
                        char blankTileLetter = UI.blankTile();
                        Board.board[x + i][y].setTile(new Tile(blankTileLetter));
                    }
                }
            }
            //same as above but for placing tiles behind the contact point
            for (int i = contact_point; i >= 0; i--) {

                if (letters_on_board[i] == null) {
                    Board.placeTile(frame.getTile(letters[i]), x + i, y);
                    letters_on_board[i] = frame.getTile(letters[i]);
                    frame.removeLetters(letters[i]);
                    if(letters[i] == ' '){
                        char blankTileLetter = UI.blankTile();
                        Board.board[x + i][y].setTile(new Tile(blankTileLetter));
                    }
                }
            }
        } else {
            //same as above but for placing a word vertically
            contact_point = 7 - y;
            Board.firstPlay(frame.getTile(letters[contact_point]));
            letters_on_board[contact_point] = frame.getTile(letters[contact_point]);
            frame.removeLetters(letters[contact_point]);
            for (int i = contact_point; i < word.length(); i++) {
                if (letters_on_board[i] == null) {
                    Board.placeTile(frame.getTile(letters[i]), x, y + i);
                    letters_on_board[i] = frame.getTile(letters[i]);
                    frame.removeLetters(letters[i]);
                    if(letters[i] == ' '){
                        char blankTileLetter = UI.blankTile();
                        Board.board[x + i][y].setTile(new Tile(blankTileLetter));
                    }
                }
            }
            for (int i = contact_point; i >= 0; i--) {
                if (letters_on_board[i] == null) {
                    Board.placeTile(frame.getTile(letters[i]), x, y + i);
                    letters_on_board[i] = frame.getTile(letters[i]);
                    frame.removeLetters(letters[i]);
                    if(letters[i] == ' '){
                        char blankTileLetter = UI.blankTile();
                        Board.board[x + i][y].setTile(new Tile(blankTileLetter));
                    }
                }
            }
        }
        firstPlay = false;
        if(player1_move){
            UI.player1.setScore(UI.player1.getScore() + score(word, bonus_squares, count));
        } else{
            UI.player2.setScore(UI.player2.getScore() + score(word, bonus_squares, count));
        }
        UI.displayScore();
        UI.displayFrame();
        player1_move = !player1_move;
        gameLoop();
    }

    private static boolean checkWordValidity(Tile[] word, String typedWord, Frame frame, int x, int y, boolean horizontal){
        boolean valid;
        readIN();
        if(!dictionaryCheck(typedWord))
            return false;

        Frame tmp_frame = new Frame();
        for(int i=0; i<frame.size(); i++)
            tmp_frame.addTiles(frame.get(i));

        //create an array of characters of the word they entered
        char[] letterArray = typedWord.toCharArray();
        valid = checkSpace(x, y, horizontal, typedWord.length());

        for(int i = 0; i < typedWord.length(); i++){
            //if statement to check if square on board has a tile
            if(word[i] != null) {
                //if the square has a tile then it must be equal to the word they typed that matches that square's coordinate
                if (letterArray[i] != word[i].getLetter())
                    valid = false;
            }
        }

        for(int i = 0; i < letterArray.length; i++) {
            if (word[i] == null) {
                //checks the players frame for the tile they want to play
                if(!tmp_frame.contains(letterArray[i])){
                    valid=false;
                } else{
                    tmp_frame.removeLetters(tmp_frame.getTile(letterArray[i]));
                }
            }
        }
        if(!valid){
            UI.invalidEntry();
        }
        return valid;
    }

    //create an array of the tiles on the board. This is used to check if the player is playing a word in a valid position
    private static Tile[] populateArrayWithBoardTiles(int x, int y, boolean direction, int wordLength){
        Tile[] boardTiles = new Tile[wordLength];

        if(checkSpace(x, y, direction, wordLength)) {
            if (direction) {
                for (int i = 0; i < wordLength; i++) {
                    boardTiles[i] = Board.board[i + x][y].getTile();
                }
            } else {
                for (int i = 0; i < wordLength; i++)
                    boardTiles[i] = Board.board[x][i + y].getTile();
            }
        } else{
            UI.invalidEntry();
        }

        return  boardTiles;
    }

    private static tileType[] populateArrayWithBoardSquares(int x, int y, boolean direction, int wordLength, Tile[] squares_without_tiles){
        tileType[] boardSquares = new tileType[wordLength];

        if(checkSpace(x, y, direction, wordLength)) {
            if (direction) {
                for (int i = 0; i < wordLength; i++) {
                    if(squares_without_tiles[i] == null) {
                        boardSquares[i] = Board.board[i + x][y].boardTile;
                    } else{
                        boardSquares[i] = tileType.NORMAL;
                    }
                }
            } else {
                for (int i = 0; i < wordLength; i++) {
                    if(squares_without_tiles == null){
                        boardSquares[i] = Board.board[x][i + y].boardTile;
                    } else{
                        boardSquares[i] = tileType.NORMAL;
                    }
                }
            }
        } else{
            UI.invalidEntry();
        }
        return boardSquares;
    }

    //check if there's enough room on the board for the word they tried to play
    private static boolean checkSpace(int x, int y, boolean direction, int word_length){
        boolean valid = true;
        if(direction){
            if(word_length > (15 - x) || y < 0)
                valid = false;
        } else{
            if(word_length > (15 - y) || y < 0)
                valid = false;
        }
        if(!valid)
            UI.invalidEntry();

        return valid;
    }

    private static void handleUserCommands(String input){
        switch (input) {
            case "QUIT":
                Platform.exit();
                break;
            case "PASS":
                player1_move = !player1_move;
                gameLoop();
                break;
            case "HELP":
                UI.help();
                break;
            default:
                UI.invalidEntry();
                break;
        }
    }

    private static void exchangeLetters(String command, String letters, Frame frame){
        char[] letterArray = letters.toCharArray();
        LinkedList<Tile> replace_these_letters = new LinkedList<>();
        boolean valid=true;
        if(command.equals("EXCHANGE")){
            for (char c : letterArray) {
                //checks the players frame for the tile they want to play
                if (!frame.checkForTile(frame.getTile(c))) {
                    valid = false;
                } else {
                    replace_these_letters.add(frame.getTile(c));
                    frame.removeLetters(c);
                }
            }
        }
        if(valid){
            UI.displayFrame();
            Pool.addBackLetters(replace_these_letters);
            frame.refillFrame();
            Pool.currentPoolTiles();
            player1_move = !player1_move;
            gameLoop();
        } else{
            UI.invalidEntry();
        }
    }

    private static int score(String word, tileType[] board_squares, int bonus){
        int score = 0;
        char[] letters = word.toCharArray();
        int num_triple_words = 0;
        int num_double_words = 0;

        for(int i = 0; i < letters.length; i++){
            if(board_squares[i] == tileType.DOUBLE_LETTER){
                score = score + (Pool.query(letters[i]) * 2);
            } else if(board_squares[i] == tileType.TRIPLE_LETTER){
                score = score + (Pool.query(letters[i]) * 3);
            } else if(board_squares[i] == tileType.NORMAL){
                score = score + Pool.query(letters[i]);
            } else if(board_squares[i] == tileType.DOUBLE_WORD || board_squares[i] == tileType.CENTRE){
                score = score + Pool.query(letters[i]);
                num_double_words++;
            } else if(board_squares[i] == tileType.TRIPLE_WORD){
                score = score + Pool.query(letters[i]);
                num_triple_words++;
            }
        }

        if(num_double_words > 0)
            score = score * num_double_words * 2;
        if(num_triple_words > 0)
            score = score * num_triple_words * 3;
        if(bonus == 7)
            score = score + 50;

        return score;
    }
}