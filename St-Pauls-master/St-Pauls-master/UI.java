import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import javax.swing.*;
import java.util.Optional;

/*
 * Group - St-Pauls
 * Author - Thomas Thornton
 * Group members:
 * 	-Brian Byrne
 *  -Zach Dunne
 * */

public class UI extends Application {
    //Size of each square in the scene
    public static int TILE_SIZE = 40;
    //Number of squares in the board
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;

    static Frame frame1 = new Frame();
    static Frame frame2 = new Frame();

    static Player player1 = new Player(null, frame1);
    static Player player2 = new Player(null, frame2);

    private Group squareGroup = new Group();
    private static Group frameGroup = new Group();
    private static Group ui = new Group();
    Label xaxis = new Label("A        B        C        D        E         F        G        " +
                                 "H        I        J        K        L         M       N        O");
    Label yaxis = new Label("15\n\n14\n\n13\n\n12\n\n11\n\n10\n\n9\n\n8\n\n7\n\n6" +
            "\n\n5\n\n4\n\n3\n\n2\n\n1");

    Pane root = new Pane();

    //creating the UI of the board
    private Parent createHardware(){
        root.setPrefSize(WIDTH * TILE_SIZE + 12 * TILE_SIZE, HEIGHT * TILE_SIZE + (TILE_SIZE * 2));
        root.getChildren().addAll(squareGroup, frameGroup, xaxis, yaxis);
        yaxis.setFont(new Font("Arial", 15.5));
        yaxis.relocate(15 * TILE_SIZE, 0);
        xaxis.relocate(0, 15 * TILE_SIZE);
        xaxis.setFont(new Font("Arial", 14));
        //moving frames to the bottom of the scene
        frame1.relocate(0, 15.5 * TILE_SIZE);
        frame2.relocate(7.5 * TILE_SIZE, 15.5 * TILE_SIZE);

        //initialising the board in UI form
        for(int j = 0; j < HEIGHT; j++){
            for(int i = 0; i < WIDTH; i++){
                Square square = Board.board[i][j];
                square.relocate(i * TILE_SIZE, j * TILE_SIZE);
                squareGroup.getChildren().add(square);
            }
        }
        return root;
    }

    //this is where all player input will be entered
    static TextField player_input = new TextField();
    static Button submit = new Button("Submit");
    protected static Label move_label = new Label("Enter Move: ");
    private static Label score = new Label();
    private static Label blankTile = new Label("To play a blank tile: \nType the word you want to play and leave a space for the blank tile" +
            "\ne.g. using the blank tile for E HELLO would become H LLO");

    private void createUI(){
        //creating submit button and label to show which players move it is
        move_label.relocate(0,-0.5 * TILE_SIZE);
        submit.relocate(5 * TILE_SIZE, 0);
        submit.setOnAction(e ->
                {
                    String string = player_input.getText();
                    player_input.clear();
                    System.out.println(string);
                }

        );
        blankTile.relocate(0, TILE_SIZE);
        score.relocate(0, TILE_SIZE * 3);
        score.setText("Score\n" + player1.getName() + ": " + player1.getScore() + "\n" + player2.getName() + ": " + player2.getScore());

        ui.relocate(15.8 * TILE_SIZE, TILE_SIZE);
        ui.getChildren().addAll(player_input, move_label, submit, score, blankTile);
        ui.relocate(15.8 * TILE_SIZE, TILE_SIZE);
        root.getChildren().add(ui);
    }

    public void start(Stage stage) {
        Board.setBoard();
        Pool.setPool();

        Scene scene = new Scene(createHardware());
        stage.setTitle("Scrabble");
        stage.setScene(scene);
        stage.getIcons().add(new Image("utilities/stpauls.png"));
        stage.show();
        initialisePlayers();
        createUI();
        displayFrame();
        Scrabble.gameLoop();

    }

    protected static char blankTile(){
        char letter = ' ';

        TextInputDialog l = new TextInputDialog("Tile Letter");
        l.setTitle("Blank Tile");
        l.setHeaderText("Blank Tile");
        l.setContentText("Enter the letter you want the blank tile to be");
        l.showAndWait();
        if(l.getEditor().getText().length() > 1){
            invalidEntry();
            blankTile();
        } else if((l.getEditor().getText().toCharArray()[0] >= 'A' && l.getEditor().getText().toCharArray()[0] <= 'Z')
                || (l.getEditor().getText().toCharArray()[0] >= 'a' && l.getEditor().getText().toCharArray()[0] <= 'z')){
            letter = l.getEditor().getText().toUpperCase().toCharArray()[0];
        } else{
            invalidEntry();
            blankTile();
        }
        System.out.println(letter);
        return letter;
    }

    //pop up if player enters an invalid input
    protected static void invalidEntry(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Scrabble");
        alert.setContentText("Invalid input");
        alert.showAndWait();
        Scrabble.gameLoop();
    }

    //pop up when game is over
    protected static void gameOver(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Scrabble");
        alert.setContentText("Game Over");
        alert.showAndWait();
        Scrabble.gameLoop();
    }

    //create pop ups to receive player names and use this to initialise players
    private void initialisePlayers(){
        TextInputDialog p1 = new TextInputDialog("Player 1");
        p1.setTitle("Player 1");
        p1.setHeaderText("Player 1");
        p1.setContentText("Player 1 enter your name:");
        Optional<String> name1 = p1.showAndWait();
        name1.ifPresent(name -> player1.setName(name));
        Label player_one = new Label(player1.getName());
        player_one.relocate(0, 16.6 * TILE_SIZE );
        frameGroup.getChildren().add(player_one);

        TextInputDialog p2 = new TextInputDialog("Player 2");
        p2.setTitle("Player 2");
        p2.setHeaderText("Player 2");
        p2.setContentText("Player 2 enter your name:");
        Optional<String> name2 = p2.showAndWait();
        name2.ifPresent(name -> player2.setName(name));
        Label player_two = new Label(player2.getName());
        player_two.relocate(7.5 * TILE_SIZE, 16.6 * TILE_SIZE );
        frameGroup.getChildren().add(player_two);
    }

    public static void displayScore(){
        ui.getChildren().remove(score);
        score.relocate(0, TILE_SIZE * 3);
        score.setText("Score\n" + player1.getName() + ": " + player1.getScore() + "\n" + player2.getName() + ": " + player2.getScore());
        ui.getChildren().add(score);
    }

    private static boolean first = true;
    public static void displayFrame(){
        int frame1_size_before_refill = frame1.size();
        int frame2_size_before_refill = frame2.size();

        frame1.refillFrame();
        frame2.refillFrame();
        Group tiles_in_frame = new Group();

        if(first){
            //Fill frames before the first move
            for(int i = 0; i < frame1.size(); i++){
                (frame1.get(i)).relocate(TILE_SIZE * i + 0.1 * TILE_SIZE, 15.6 * TILE_SIZE);
                tiles_in_frame.getChildren().add(frame1.get(i));
            }
            for(int i = 0; i < frame2.size(); i++){
                (frame2.get(i)).relocate(TILE_SIZE * i + 7.6 * TILE_SIZE, 15.6 * TILE_SIZE);
                tiles_in_frame.getChildren().add(frame2.get(i));
            }
            frameGroup.getChildren().addAll(frame1, frame2);
            frameGroup.getChildren().addAll(tiles_in_frame);
            first = !first;
        } else{
            //Replace tiles that get used from first move on
            if(Scrabble.player1_move) {
                for (int i = 0; i < frame1.size(); i++) {
                    (frame1.get(i)).relocate(TILE_SIZE * i + 0.1 * TILE_SIZE, 15.6 * TILE_SIZE);
                    //avoid creating duplicates of tiles
                    if(i >= frame1_size_before_refill) {
                        frameGroup.getChildren().add(frame1.get(i));
                    }
                }
            } else {
                for (int i = 0; i < frame2.size(); i++) {
                    (frame2.get(i)).relocate(TILE_SIZE * i + 7.6 * TILE_SIZE, 15.6 * TILE_SIZE);
                    //avoid creating duplicates of tiles
                    if(i >= frame2_size_before_refill) {
                        frameGroup.getChildren().add(frame2.get(i));
                    }
                }
            }
        }

    }

    protected static void help(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText(null);
        alert.setContentText("To play move enter coordinate of first tile e.g. H8\n\nThe direction of the word" +
                "(across or down) e.g. A\n\nThe word itself e.g. BEANS\n\nH8 A BEANS\n\nEXCHANGE <letters> to swap those letters \ne.g.EXCHANGE EIA" +
                "\n\nPASS to pass your turn\n\nQUIT to quit");
        alert.showAndWait();
    }

    public static void main(String args[]){
        launch(args);
    }
}
