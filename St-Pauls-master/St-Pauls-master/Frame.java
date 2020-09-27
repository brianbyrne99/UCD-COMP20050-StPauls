import java.awt.*;
import java.util.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/*
 * Group - St-Pauls
 * Author - Thomas Thornton
 * Group members:
 * 	-Brian Byrne
 * 	-Zach Dunne
 * */

public class Frame extends Rectangle {

    LinkedList<Tile> playerFrame = new LinkedList<Tile>();//stores the letters the player has in their frame

    public Frame(){
        setWidth(UI.TILE_SIZE * 7.4);
        setHeight(UI.TILE_SIZE * 1.2);
        setFill(Color.BURLYWOOD);
    }

    public void resetFrame() { //method to remove all tiles from pool
        int size = playerFrame.size();
        for(int i = 0; i < size; i++) {
            playerFrame.remove(i);
        }
    }

    public void removeLetters(Tile T){ //method to remove a specified tile
        boolean check=true;

        for(int i = 0; i < playerFrame.size(); i++) { //checks if the tile selected is in the frame
            if(!(playerFrame.contains(T))) {
                check = false;
                break;
            }
        }

        if(!check) {
            System.out.println("Invalid selection of tiles");
        } else {
            playerFrame.remove(T); //remove the tile from the frame
        }
    }

    public void removeLetters(char letter){
        int index = -1;
        for(int i = 0; i < playerFrame.size(); i++){
            if(playerFrame.get(i).getLetter() == letter){
                index = i;
            }
        }

        if(index == -1){
            System.out.println("Invalid letter");
        } else{
            playerFrame.remove(index);
        }
    }

    public void removeLetters(int i){ //method to remove a specified tile at certain index
        if(i < playerFrame.size()) {
            playerFrame.remove(i);
        } else {
            System.out.println("Invalid letter");
        }
    }

    public void addTiles(Tile a) {
        playerFrame.add(a); //add given tile to linked list (obsolete due to refillFrame())
    }

    public boolean checkForTile(Tile T) {
        return playerFrame.contains(T); //checks if the frame has a specified tile
    }

    public boolean emptyFrame() {
        return playerFrame.isEmpty(); //checks if the frame is empty
    }

    public LinkedList<Tile> getFRAME() {
        return playerFrame; //returns the player's frame
    }

    public void refillFrame() { //refill the frame to 7 tiles from the pool
        int missing;
        missing = 7 - playerFrame.size();
        Pool.randomSelection(playerFrame, missing);
    }

    public void showFrame() { //display the frame
        System.out.println(playerFrame);
    }

    public Tile get(int i) {
        return playerFrame.get(i);
    }

    public Tile getTile(char letter){
        int index = -1;
        for(int i = 0; i < playerFrame.size(); i++){
            if(playerFrame.get(i).getLetter() == letter){
                index = i;
            }
        }
            return playerFrame.get(index);
    }

    public int size() {
        return playerFrame.size();
    }

    public boolean contains(char letter){
        boolean valid=false;
        for(int i=0; i < playerFrame.size(); i++){
            if(playerFrame.get(i).getLetter() == letter){
                valid = true;
            }
        }
        return valid;
    }
}
