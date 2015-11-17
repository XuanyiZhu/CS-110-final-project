/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.scene.layout.Pane;

/**
 * A Pane in which tetris squares can be displayed.
 *
 * @author Xuanyi Zhu
 */
public class KrakoutBoard extends Pane {

    // The size of the side of a krakout square

    public static final int SQUARE_SIZE = 20;
    // The number of squares that fit on the screen in the x and y dimensions
    public static final int X_DIM_SQUARES = 20;
    public static final int Y_DIM_SQUARES = 30;
    public static final double BoardLength = 0.2;

    /**
     * Sizes the board to hold the specified number of squares in the x and y
     * dimensions.
     */
    public KrakoutBoard() {
        this.setPrefHeight(Y_DIM_SQUARES * SQUARE_SIZE);
        this.setPrefWidth(X_DIM_SQUARES * SQUARE_SIZE);

    }

}
