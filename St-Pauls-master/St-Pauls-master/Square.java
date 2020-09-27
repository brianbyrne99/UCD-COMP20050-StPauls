import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;



enum tileType{
    NORMAL,
    DOUBLE_LETTER, TRIPLE_LETTER,
    DOUBLE_WORD, TRIPLE_WORD, CENTRE
}

public class Square extends Rectangle {

    boolean empty;
    tileType boardTile;
    Tile letterTile;

    Square(boolean empty, tileType t) {
        this.empty = empty;
        this.boardTile = t;

        setWidth(UI.TILE_SIZE);
        setHeight(UI.TILE_SIZE);
        javafx.scene.image.Image dl = new Image(getClass().getResource("utilities/dl.png").toExternalForm());
        javafx.scene.image.Image tl = new Image(getClass().getResource("utilities/tl.png").toExternalForm());
        javafx.scene.image.Image dw = new Image(getClass().getResource("utilities/dw.png").toExternalForm());
        javafx.scene.image.Image tw = new Image(getClass().getResource("utilities/tw.png").toExternalForm());
        javafx.scene.image.Image n = new Image(getClass().getResource("utilities/n.png").toExternalForm());
        javafx.scene.image.Image star = new Image(getClass().getResource("utilities/star.png").toExternalForm());

        switch(boardTile) {
            case DOUBLE_LETTER:
                setFill(new ImagePattern(dl));
                break;
            case TRIPLE_LETTER:
                setFill(new ImagePattern(tl));
                break;
            case DOUBLE_WORD:
                setFill(new ImagePattern(dw));
                break;
            case TRIPLE_WORD:
                setFill(new ImagePattern(tw));
                break;
            case NORMAL:
                setFill(new ImagePattern(n));
                break;
            case CENTRE:
                setFill(new ImagePattern(star));
                break;

        }

    }

    public void setTile(Tile tile) {
        this.letterTile = tile;
        this.empty = false;
    }

    public Tile getTile() {
        return this.letterTile;
    }


    public void setEmpty(boolean empty) {
        this.empty = empty;
    }


    public boolean isEmpty() {
        return empty;
    }


}
