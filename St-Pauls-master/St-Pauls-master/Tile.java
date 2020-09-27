/*
 * This class will be used to create Tile objects to be used during the game
 */
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;

import java.awt.*;

import javafx.scene.layout.StackPane;
import java.lang.*;

/*
 * Group - St-Pauls
 * Author - Brian Byrne
 * Group members:
 * 	-Thomas Thornton
 * 	-Zach Dunne
 * */
public class Tile extends Rectangle {
    char letter;    //each tile has the instance variables of its letter and corresponding value
    int value;

    public Tile(char letter)
    {
        setLetter(letter); //constructor that takes the parameter of a letter and use that letter to match it to its associative letter
        setValue(letter);  // via the setValue method

        setWidth(UI.TILE_SIZE * 0.95);
        setHeight(UI.TILE_SIZE * 0.95);
        javafx.scene.image.Image a = new Image(getClass().getResource("tiles/atile.PNG").toExternalForm());
        javafx.scene.image.Image b = new Image(getClass().getResource("tiles/btile.PNG").toExternalForm());
        javafx.scene.image.Image c = new Image(getClass().getResource("tiles/ctile.PNG").toExternalForm());
        javafx.scene.image.Image d = new Image(getClass().getResource("tiles/dtile.PNG").toExternalForm());
        javafx.scene.image.Image e = new Image(getClass().getResource("tiles/etile.PNG").toExternalForm());
        javafx.scene.image.Image f = new Image(getClass().getResource("tiles/ftile.PNG").toExternalForm());
        javafx.scene.image.Image g = new Image(getClass().getResource("tiles/gtile.PNG").toExternalForm());
        javafx.scene.image.Image h = new Image(getClass().getResource("tiles/htile.PNG").toExternalForm());
        javafx.scene.image.Image i = new Image(getClass().getResource("tiles/itile.PNG").toExternalForm());
        javafx.scene.image.Image j = new Image(getClass().getResource("tiles/jtile.PNG").toExternalForm());
        javafx.scene.image.Image k = new Image(getClass().getResource("tiles/ktile.PNG").toExternalForm());
        javafx.scene.image.Image l = new Image(getClass().getResource("tiles/ltile.PNG").toExternalForm());
        javafx.scene.image.Image m = new Image(getClass().getResource("tiles/mtile.PNG").toExternalForm());
        javafx.scene.image.Image n = new Image(getClass().getResource("tiles/ntile.PNG").toExternalForm());
        javafx.scene.image.Image o = new Image(getClass().getResource("tiles/otile.PNG").toExternalForm());
        javafx.scene.image.Image p = new Image(getClass().getResource("tiles/ptile.PNG").toExternalForm());
        javafx.scene.image.Image q = new Image(getClass().getResource("tiles/qtile.PNG").toExternalForm());
        javafx.scene.image.Image r = new Image(getClass().getResource("tiles/rtile.PNG").toExternalForm());
        javafx.scene.image.Image s = new Image(getClass().getResource("tiles/stile.PNG").toExternalForm());
        javafx.scene.image.Image t = new Image(getClass().getResource("tiles/ttile.PNG").toExternalForm());
        javafx.scene.image.Image u = new Image(getClass().getResource("tiles/utile.PNG").toExternalForm());
        javafx.scene.image.Image v = new Image(getClass().getResource("tiles/vtile.PNG").toExternalForm());
        javafx.scene.image.Image w = new Image(getClass().getResource("tiles/wtile.PNG").toExternalForm());
        javafx.scene.image.Image x = new Image(getClass().getResource("tiles/xtile.PNG").toExternalForm());
        javafx.scene.image.Image y = new Image(getClass().getResource("tiles/ytile.PNG").toExternalForm());
        javafx.scene.image.Image z = new Image(getClass().getResource("tiles/ztile.PNG").toExternalForm());
        javafx.scene.image.Image blank = new Image(getClass().getResource("tiles/blank.png").toExternalForm());
        switch(letter) {
            case 'A':
                setFill(new ImagePattern(a));
                break;
            case 'B':
                setFill(new ImagePattern(b));
                break;
            case 'C':
                setFill(new ImagePattern(c));
                break;
            case 'D':
                setFill(new ImagePattern(d));
                break;
            case 'E':
                setFill(new ImagePattern(e));
                break;
            case 'F':
                setFill(new ImagePattern(f));
                break;
            case 'G':
                setFill(new ImagePattern(g));
                break;
            case 'H':
                setFill(new ImagePattern(h));
                break;
            case 'I':
                setFill(new ImagePattern(i));
                break;
            case 'J':
                setFill(new ImagePattern(j));
                break;
            case 'K':
                setFill(new ImagePattern(k));
                break;
            case 'L':
                setFill(new ImagePattern(l));
                break;
            case 'M':
                setFill(new ImagePattern(m));
                break;
            case 'N':
                setFill(new ImagePattern(n));
                break;
            case 'O':
                setFill(new ImagePattern(o));
                break;
            case 'P':
                setFill(new ImagePattern(p));
                break;
            case 'Q':
                setFill(new ImagePattern(q));
                break;
            case 'R':
                setFill(new ImagePattern(r));
                break;
            case 'S':
                setFill(new ImagePattern(s));
                break;
            case 'T':
                setFill(new ImagePattern(t));
                break;
            case 'U':
                setFill(new ImagePattern(u));
                break;
            case 'V':
                setFill(new ImagePattern(v));
                break;
            case 'W':
                setFill(new ImagePattern(w));
                break;
            case 'X':
                setFill(new ImagePattern(x));
                break;
            case 'Y':
                setFill(new ImagePattern(y));
                break;
            case 'Z':
                setFill(new ImagePattern(z));
                break;
            case ' ':
                setFill(new ImagePattern(blank));
                break;
        }
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public void setValue(char letter) {
        this.value=Pool.query(letter);//each letter has its unique value which can be returned by the hashMap
    }
    public char getLetter() {    //getter for the letter of the tile
        return letter;
    }

    public int getValue() {     // getter for the value of the tile
        return value;
    }

    public static void relocateTile(Tile tile, int x, int y){
        tile.relocate(x * UI.TILE_SIZE, y * UI.TILE_SIZE);
    }

    public String toString() {
        return "|" + letter + "|";
    }
}

