/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A single Square on the tetris board. Squares have fixed height and width and 
 * are positioned at grid locations on the board. A square at board location 
 * 3, 4 for example, would be drawn at 3*TetrisBoard.SQUARE_SIZE, 
 * 4*TetrisBoard.SQUARE_SIZE.
 * 
 * The tetris board x and y locations of the Square are IntegerProperties. You 
 * can either 
 * @author Xuanyi Zhu
 */
public class KrakoutSquare {
    // The shape for the square
    Rectangle shape = new Rectangle(0, 0, KrakoutBoard.SQUARE_SIZE, KrakoutBoard.SQUARE_SIZE);
    // The x location in board coordinates
    private DoubleProperty krakout_x = new SimpleDoubleProperty();
    // The y location in board coordinates
    private DoubleProperty krakout_y = new SimpleDoubleProperty();
    private KrakoutBoard board;
    
    /**
     * Creates a square and draws it in the board. The shape for a square is 
     * a rectangle with height and width equal to TetrisBoard.SQUARE_SIZE.
     * The location of the shape is set to tetris_x*TetrisBoard.SQUARE_SIZE,
     * tetris_y*TetrisBoard.SQUARE_SIZE via bindings, so that whenever
     * tetris_x and tetris_y are updated, the square's location will update.
     * @param board 
     */
    public KrakoutSquare(KrakoutBoard board) {
        this.board = board;
        this.board.getChildren().add(shape);
        
        // set the x and y locations so that they are always a multiple
        // of the size of a grid square
        shape.xProperty().bind(krakout_x.multiply(KrakoutBoard.SQUARE_SIZE));
        shape.yProperty().bind(krakout_y.multiply(KrakoutBoard.SQUARE_SIZE));
    }
 
    /**
     * Move the square to the specified x and y board coordinates. Undoes any
     * binding currently in effect on the coordinates, so that the square
     * remains fixed at the specified location.
     * @param x x-coordinate on the tetris board
     * @param y y-coordinate on the tetris board
     */
    public void moveToKrakoutLocation(double x, double y) {
        krakout_x.unbind();
        krakout_y.unbind();
        krakout_x.set(x); // due to binding, moves to x*SQUARE_SIZE, y*SQUARE_SIZE
        krakout_y.set(y);
    }
    
    public void move(){
        moveToKrakoutLocation(getX(),getY()+0.005);
    }
    
    /**
     * Get the x location of the square in board coordinates.
     * @return current x location on the board
     */
    public double getX() {
        return krakout_x.get();
    }
    
    /**
     * Get the y location of the square in board coordinates.
     * @return current y location on the board
     */
    public double getY() {
        return krakout_y.get();
    }

    /**
     * Get the x location in board coordinates as an IntegerProperty. If you 
     * want to learn to use javafx properties, you can use this to tie the 
     * Square's location to another variable (like the Piece's location). If you
     * prefer, you can use moveToTetrisLocation, getX and getY instead, ignoring
     * the property binding functionality.
     * @return the x location IntegerProperty
     */
    public DoubleProperty xProperty() {
        return krakout_x;
    }
    
    /**
     * Get the y location in board coordinates as an IntegerProperty. If you 
     * want to learn to use javafx properties, you can use this to tie the 
     * Square's location to another variable (like the Piece's location). If you
     * prefer, you can use moveToTetrisLocation, getX and getY instead, ignoring
     * the property binding functionality.
     * @return the y location IntegerProperty
     */
    public DoubleProperty yProperty() {
        return krakout_y;
    }

    /**
     * Sets the color of the square.
     * @param color 
     */
    public void setColor(Color color) {
        shape.setFill(color);
    }

    /**
     * Removes the square from the TetrisBoard's Pane.
     */
    void removeFromDrawing() {
        board.getChildren().remove(shape);
    }

}
