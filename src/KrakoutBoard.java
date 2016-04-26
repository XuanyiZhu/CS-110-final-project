/*
 * KrakoutBoard setup the boxes size, the paddle length, the number of boxes and the pane size
 */

import javafx.scene.layout.Pane;

/**
 * A Pane in which tetris squares can be displayed.
 *
 * @author Xuanyi Zhu
 */
public class KrakoutBoard extends Pane {

    // The size of the side of a krakout square
    public static final int BOXES_SIZE = 45;
    // The number of squares that fit on the screen in the x and y dimensions
    public static final int X_DIM_BOXES = 15;
    public static final int Y_DIM_BOXES = 20;
    public static final double BoardLength = 0.25;
public static final int numberOfBoxes = 120;

    //the width of the pane
    public static int PANE_X = X_DIM_BOXES * BOXES_SIZE;
    //the height of the pane
    public static int PANE_Y = Y_DIM_BOXES * BOXES_SIZE;

    /**
     * Sizes the board to hold the specified number of squares in the x and y
     * dimensions.
     */
    public KrakoutBoard() {
        this.setPrefHeight(Y_DIM_BOXES * BOXES_SIZE);
        this.setPrefWidth(X_DIM_BOXES * BOXES_SIZE);
    }

}
