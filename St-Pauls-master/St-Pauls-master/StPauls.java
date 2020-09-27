import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class StPauls implements BotAPI {
    private PlayerAPI me;
    private OpponentAPI opponent;
    private BoardAPI board;
    private UserInterfaceAPI info;
    private DictionaryAPI dictionary;
    private int turnCount;

    StPauls(PlayerAPI me, OpponentAPI opponent, BoardAPI board, UserInterfaceAPI ui, DictionaryAPI dictionary) {
        this.me = me;
        this.opponent = opponent;
        this.board = board;
        this.info = ui;
        this.dictionary = dictionary;
        turnCount = 0;
    }

public static ArrayList<Word> availableSpaces = new ArrayList<Word>();
public static ArrayList<String> possibleWords= new ArrayList<String>();
public static ArrayList<String> sowpods = new ArrayList<String>();
public static HashMap<Integer, Character> coordinates= new HashMap<>();

    @Override
    public String getCommand() {
        assignValues();
        availableSpaces.clear();
        possibleWords.clear();
        String entry;
        Random rand = new Random();
        if (turnCount == 0) {
            readIn();
            turnCount++;
            return "NAME StPauls";
        }
        if(turnCount == 1) {
            turnCount++;
            return "SCORE";
        }

        if (board.isFirstPlay()) {
            int bool = rand.nextInt(2) + 1;
            char direction = 'D';
            if (bool == 2)
                direction = 'A';

            entry = "H8 " + direction + " " + frameBuilder();
            return entry;
        }
        //Playing any words after first word

        postion();
        for(Word word : availableSpaces) {

            String playableLets= frameBuilder()+word.getLetter(0);
            String toBeAvoided = new String();

            for(char i = 'A'; i <= 'Z'; i++) {
                if (playableLets.indexOf(i) == -1)
                    toBeAvoided += i;    //creates a string of letters not present in the frame
            }
            for(String check : sowpods)
            {
                if(word.getLetter(0) == check.charAt(0)){
                    int counter=0;
                    for(int j=0;j<toBeAvoided.length();j++)
                    {
                        if(check.indexOf(toBeAvoided.charAt(j)) == -1)
                            counter++;
                    }

                    if(counter==toBeAvoided.length())
                        possibleWords.add(check);

                }
            }

        }
        Random ran = new Random();
        int choice=ran.nextInt(possibleWords.size())+0;
        String finalWord = possibleWords.get(choice);
        String enter=new String();
        for(Word space: availableSpaces)
        {
            if(space.getLetter(0) == finalWord.charAt(0)){
                enter+=coordinates.get(space.getFirstColumn());
                enter+=space.getFirstRow()+1;
                if(space.isHorizontal())
                    enter+="  A ";
                else
                    enter+=" D ";

                enter+=finalWord;
                return enter;
            }
        }

        return "PASS";

    }

    //This method updates creates a list of potential word plays
    public void postion() {

            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    Square square = board.getSquareCopy(i, j);
                    Square right = (j == 14) ? board.getSquareCopy(7, 7) : board.getSquareCopy(i, j + 1);
                    Square left = (j == 0) ? board.getSquareCopy(7, 7) : board.getSquareCopy(i, j - 1);
                    Square top = (i == 0) ? board.getSquareCopy(7, 7) : board.getSquareCopy(i - 1, j);
                    Square bottom = (i == 14) ? board.getSquareCopy(7, 7) : board.getSquareCopy(i + 1, j);

                    if (square.isOccupied())  {
                        if(!right.isOccupied() && !left.isOccupied()){
                            char firstLetter= square.getTile().getLetter();
                            availableSpaces.add(new Word(i,j,true, firstLetter+""));
                        }

                        if(!top.isOccupied() && !bottom.isOccupied()){
                            char firstLetter= square.getTile().getLetter();
                            availableSpaces.add(new Word(i,j,false, firstLetter+""));
                        }

                    }
                }
            }

            /*
             *dictionary search
             */

        }

    public void readIn() {
//reads in the dictionary file line for line
        try {
            File input = new File("C:\\Users\\brian\\IdeaProjects\\BotWorld\\src\\csw.txt");
            Scanner sc = new Scanner(input);
           // for(Word temp : availableSpaces) {
                while (sc.hasNextLine()) {
                    String data = sc.nextLine();
                    sowpods.add(data);
                }

        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found");
            e.printStackTrace();
        }
    }

    public String frameBuilder()
    {
        //if (scrabble.getDictionary().areWords(scrabble.getLatestWords()));
       String word=me.getFrameAsString(), botframe="";
       for(int i=0;i<word.length();i++) {
           if (word.charAt(i) >= 'A' && word.charAt(i) <= 'Z')
                botframe += word.charAt(i);
       }
         return botframe;
    }


public void assignValues()
{
    int i=0;
  for(char let= 'A';let<='O';let++){
      coordinates.put(i,let);
      i++;
  }
}
}