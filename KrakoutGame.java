/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 * This should be implemented to include your game control.
 *
 * @author pipWolfe
 */
public class KrakoutGame {

    private final Krakout krakoutApp;
    private final DrawingBoard line;
    private final int numberOfBoxes = 100;
    private final int BoxesOneRow = 19;
    static boolean BallIsMove;
    private KrakoutBall ball;
ArrayList<KrakoutSquare> square = new ArrayList<>();
    /**
     * Initialize the game. Remove the example code and replace with code that
     * creates a random piece.
     *
     * @param krakoutApp
     * @param board A reference to the board on which Squares are drawn
     */
    public KrakoutGame(Krakout krakoutApp, KrakoutBoard board) {
//         Some sample code that places two squares on the board.
//         Take this out and construct your random piece here.
//        KrakoutSquare square1 = new KrakoutSquare(board);
//        square1.moveToKrakoutLocation(1, 1);
//        square1.setColor(Color.BLUEVIOLET);
//        
//        KrakoutSquare square2 = new KrakoutSquare(board);
//        square2.moveToKrakoutLocation(5, 3);
//        square2.setColor(Color.RED);

        DrawingBoard line = new DrawingBoard(board);
        line.setLine(Color.BLACK, 5.0);
        this.line = line;

        
  
        for (int i = 0; i < numberOfBoxes; i++) {
            square.add(i, new KrakoutSquare(board));
            square.get(i).moveToKrakoutLocation((1 + i- BoxesOneRow* (i/BoxesOneRow)), 1 + i/BoxesOneRow);
            square.get(i).setColor(Color.rgb((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));
            
        }
        
        KrakoutBall ball = new KrakoutBall(board, line);
        ball.setBall(Color.BLACK, Color.WHITE);
        this.ball = ball;
        
    //    DrawingBoxes boxes = new DrawingBoxes(board);

        this.krakoutApp = krakoutApp;
        // You can use this to show the score, etc.
        krakoutApp.setMessage("Game has started!");
    }

    /**
     * Animate the game, by moving the current board left or right.
     */
    void update() {
        Point2D directionVector = ball.updateVector();
        ball.moveBall(directionVector.getX() + ball.getCenterX(), directionVector.getY() + ball.getCenterY());
        
        for (int i = 0; i < numberOfBoxes; i++) {
            square.get(i).move();
        
        }
    }

    /**
     * Move the current krakout piece left.
     */
    void left() {

        System.out.println("left key was pressed!");
        if (line.getStartX() > 10) {
            line.moreBoard(line.getStartX() - 10, line.getEndX() - 10);
        }

    }

    /**
     * Move the current tetris piece right.
     */
    void right() {
        System.out.println("right key was pressed!");
        if (line.getEndX() < (KrakoutBoard.X_DIM_SQUARES * KrakoutBoard.SQUARE_SIZE - 10)) {
            line.moreBoard(line.getStartX() + 10, line.getEndX() + 10);
        }

    }
    




}
