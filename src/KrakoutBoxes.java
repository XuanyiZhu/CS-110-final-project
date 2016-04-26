/*
 * KrakoutBoxes class creates a single box.Boxes have fixed height and width and
 * are positioned at grid locations on the board. A box at board location 3,
 * 4 for example, would be drawn at 3Krakout.BOXES_SIZE,
 * 4*KrakoutBoard.BOXES_SIZE. 
 * 
 * @author Xuanyi Zhu & Tariye Peter
 */

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class KrakoutBoxes {

    // The shape for the box
    Rectangle shape = new Rectangle(0, 0, KrakoutBoard.BOXES_SIZE, KrakoutBoard.BOXES_SIZE);
    // The x location in board coordinates
    private final DoubleProperty krakout_x = new SimpleDoubleProperty();
    // The y location in board coordinates
    private final DoubleProperty krakout_y = new SimpleDoubleProperty();
    private final KrakoutBoard board;

    /**
     * Creates a box and draws it in the board.
     *
     * @param board
     */
    public KrakoutBoxes(KrakoutBoard board) {
        this.board = board;
        this.board.getChildren().add(shape);

        // set the x and y locations so that they are always a multiple
        // of the size of a grid square
        shape.xProperty().bind(krakout_x.multiply(KrakoutBoard.BOXES_SIZE));
        shape.yProperty().bind(krakout_y.multiply(KrakoutBoard.BOXES_SIZE));
    }

    /**
     * Move the box to the specified x and y board coordinates. Undoes any
     * binding currently in effect on the coordinates, so that the box
     * remains fixed at the specified location.
     *
     * @param x x-coordinate on the krakout board
     * @param y y-coordinate on the krakout board
     */
    public void moveToKrakoutLocation(double x, double y) {
        krakout_x.unbind();
        krakout_y.unbind();
        krakout_x.set(x); // due to binding, moves to x*BOXES_SIZE, y*BOXES_SIZE
        krakout_y.set(y);
    }

    /**
     * enter the speed of the boxes
     *
     * @param speed
     */
    public void move(double speed) {
        moveToKrakoutLocation(getX(), getY() + speed);
    }

    /**
     * Get the x location of the square in board coordinates.
     *
     * @return current x location on the board
     */
    public double getX() {
        return krakout_x.get();
    }

    /**
     * Get the y location of the square in board coordinates.
     *
     * @return current y location on the board
     */
    public double getY() {
        return krakout_y.get();
    }

    /**
     * Get the x location in board coordinates as an IntegerProperty. 
     *
     * @return the x location DoubleProperty
     */
    public DoubleProperty xProperty() {
        return krakout_x;
    }

    /**
     * Get the y location in board coordinates as an IntegerProperty.
     *
     * @return the y location DoubleProperty
     */
    public DoubleProperty yProperty() {
        return krakout_y;
    }

   /**
     * Set the boxes to an image
     * @param image 
     */
     public void setBox(ImagePattern image) {
        shape.setFill(image);
    } 

    /**
     * Removes the square from the KrakoutBoard's Pane.
     */
    void removeFromDrawing() {
        board.getChildren().remove(shape);
    }

}
