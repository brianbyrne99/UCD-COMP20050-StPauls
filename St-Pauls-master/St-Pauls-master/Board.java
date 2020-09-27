public class Board {

    static int BOARD_SIZE = 15;
    static public Square[][] board = new Square[BOARD_SIZE][BOARD_SIZE];

    static public void setBoard(){
        for (int i =0; i< BOARD_SIZE; i++){
            for(int j =0; j < BOARD_SIZE; j++){
                //adds in the triple word squares
                if((i==0 || i==7 || i==14) && (j==0 || j==7 || j==14)){
                    if(i==7 && j==7) //puts a double word square in the center
                        board[i][j] = new Square(true, tileType.CENTRE);
                    else
                        board[i][j] = new Square(true, tileType.TRIPLE_WORD);
                }
                //adds in the double word squares
                else if(((i == j) || (j == 14-i)) && (i != 0) && (i != 5) && (i != 6) && (i != 8) && (i != 9) && (i != 14)){
                    board[i][j] = new Square(true, tileType.DOUBLE_WORD);
                }
                //adds in the triple letter squares
                else if (((i==1 || i==13) && (j==5 || j==9)) || ((i==5 || i==9) && (j==1 || j==5 || j==9 || j==13))) {
                    board[i][j] = new Square(true, tileType.TRIPLE_LETTER);
                }
                //adds in the double letter squares
                else if (((i==3 || i==11) && (j==0 || j==7 || j==14)) || ((i==0 || i==7 || i==14) && (j==3 || j==11)) ||
                        ((i==2 || i==6 || i==8 || i==12) && (j==6 || j==8)) || ((j==2 || j==6 || j==8 || j==12) && (i==6 || i==8))) {
                    board[i][j] = new Square(true, tileType.DOUBLE_LETTER);
                }
                //adds in normal squares everywhere else
                else{
                    board[i][j] = new Square(true, tileType.NORMAL);
                }
            }
        }
    }

    public static void printBoard(){
        for (int row = 0; row < BOARD_SIZE; row++){
            System.out.println("");
            System.out.println("-------------------------------------------------------------");

            for (int column = 0; column < BOARD_SIZE; column++)
            {
                if(board[row][column].empty == true) {
                    System.out.print(board[row][column]);
                } else {
                    System.out.print("|" + board[row][column].getTile().toString() + "");
                }
            }
            System.out.print("|");
        }
        System.out.println("");
        System.out.println("-------------------------------------------------------------");
    }


    public static void placeTile(Tile tile,int x, int y)//used to place all tiles apart from the first tile placement
    {

        if(board[x][y].isEmpty()==false)      // Checks if there is a tile in the square before placement
        {
            System.out.println("Square full, please choose another square");//
        }
        /*
         * This section determines if a tile can be placed in the corners of the board
         */
        else {
            if(x == 0 && y== 0)
            {
                if(board[0][1].isEmpty() ==true && board[1][0].isEmpty()==true)
                {
                    System.out.println("Your tile must be attached to another tile");
                }
                else {
                    board[x][y].setTile(tile);
                    Tile.relocateTile(tile, x, y);
                }
            }
            else if(x == 0 && y== 14)
            {
                if(board[0][13].isEmpty() ==true && board[1][14].isEmpty()==true)
                {
                    System.out.println("Your tile must be attached to another tile");
                }
                else {
                    board[x][y].setTile(tile);
                    Tile.relocateTile(tile, x, y);
                }
            }

            else if(x == 14 && y== 0)
            {
                if(board[13][0].isEmpty() ==true && board[14][1].isEmpty()==true)
                {
                    System.out.println("Your tile must be attached to another tile");
                }
                else {
                    board[x][y].setTile(tile);
                    Tile.relocateTile(tile, x, y);
                }
            }

            else if(x == 14 && y== 14)
            {
                if(board[13][14].isEmpty() ==true && board[14][13].isEmpty()==true)
                {
                    System.out.println("Your tile must be attached to another tile");
                }
                else {
                    board[x][y].setTile(tile);
                    Tile.relocateTile(tile, x, y);
                }
            }




            //This section determines if tiles can be placed on the borders of the board excluding the corners

            else if(x > 0 && x < 14 && y==0)
            {
                if(board[x+1][y].isEmpty() ==true && board[x-1][y].isEmpty()==true && board[x][y+1].isEmpty()==true)
                {
                    System.out.println("Your tile must be attached to another tile");
                }
                else {
                    board[x][y].setTile(tile);
                    Tile.relocateTile(tile, x, y);
                }
            }

            else if(x > 0 && x < 14 && y==14)
            {
                if(board[x+1][y].isEmpty() ==true && board[x-1][y].isEmpty()==true && board[x][y-1].isEmpty()==true)
                {
                    System.out.println("Your tile must be attached to another tile");
                }
                else {
                    board[x][y].setTile(tile);
                    Tile.relocateTile(tile, x, y);
                }
            }

            else if(y > 0 && y < 14 && x==0)
            {
                if(board[x][y+1].isEmpty() ==true && board[x+1][y].isEmpty()==true && board[x][y-1].isEmpty()==true)
                {
                    System.out.println("Your tile must be attached to another tile");
                }
                else {
                    board[x][y].setTile(tile);
                    Tile.relocateTile(tile, x, y);
                }
            }

            else if(y > 0 && y < 14 && x==14)
            {
                if(board[x-1][y].isEmpty() ==true && board[x][y+1].isEmpty()==true && board[x][y-1].isEmpty()==true)
                {
                    System.out.println("Your tile must be attached to another tile");
                }
                else {
                    board[x][y].setTile(tile);
                    Tile.relocateTile(tile, x, y);
                }
            }


            //This section determines if a tile can be placed in all non-border sections of the board
            else {
                boolean[] neighbours = {board[x+1][y].isEmpty(),board[x-1][y].isEmpty(),board[x][y+1].isEmpty(),board[x][y-1].isEmpty()};
                if(neighbours[0] == true && neighbours[1] == true && neighbours[2] == true && neighbours[3] == true)
                {  //This checks if the tile is connected to another tile
                    System.out.println("Your tile must be attached to another tile");
                }
                else   //if the square is empty and connected to a tile, then you can add your word.
                {
                    board[x][y].setTile(tile);
                    Tile.relocateTile(tile, x, y);
                }

            }
        }
    }

    public static void firstPlay(Tile tile) // This method is used for placing the first letter which must be in the center square
    {
        board[7][7].setTile(tile);
        Tile.relocateTile(tile, 7, 7);
    }


}
