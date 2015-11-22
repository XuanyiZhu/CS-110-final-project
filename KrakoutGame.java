/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 * This should be implemented to include your game control.
 *
 * @author Xuanyi Zhu
 */
public class KrakoutGame {

    private final Krakout krakoutApp;
    private final DrawingBoard line;
    private final KrakoutBall ball;
//    LinkedList<KrakoutSquare> square = new LinkedList<>();
    ArrayList<KrakoutSquare> square = new ArrayList<>();
    Iterator iterate = square.iterator();


    
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

        DrawingBoard line = new DrawingBoard(board);
        line.setLine(Color.BLACK, 5.0);
        this.line = line;

        for (int i = 0; i < KrakoutBoard.numberOfBoxes; i++) {
           
            square.add(new KrakoutSquare(board));
            square.get(i).moveToKrakoutLocation((1 + i - KrakoutBoard.X_DIM_SQUARES * (i / KrakoutBoard.X_DIM_SQUARES)), 1 + i / KrakoutBoard.X_DIM_SQUARES);
            square.get(i).setColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        }
        
//        Iterator<KrakoutSquare> iterator = square.iterator();
//        while (iterator.hasNext()){
//            int i = 0; 
//            iterator.get(i).moveToKrakoutLocation((1 + i - KrakoutBoard.X_DIM_SQUARES * (i / KrakoutBoard.X_DIM_SQUARES)), 
//                    1 + i / KrakoutBoard.X_DIM_SQUARES);
//            i++;
//        }

        KrakoutBall ball = new KrakoutBall(board, line);
        ball.setBall(Color.BLACK, Color.WHITE);
        this.ball = ball;
        this.krakoutApp = krakoutApp;
        // You can use this to show the score, etc.
        krakoutApp.setMessage("Game has started!");
    }

    public Point2D getBoxesLocation(int i) {

        Point2D Location = new Point2D(square.get(i).getX() * KrakoutBoard.SQUARE_SIZE, square.get(i).getY() * KrakoutBoard.SQUARE_SIZE);
        return Location;
    }

    /**
     * Animate the game, by moving the current board left or right.
     */
    void update() {
        Point2D directionVector = ball.updateVector();
        double directionVectorX = directionVector.getX();
        double directionVectorY = directionVector.getY();
        System.out.println("before check direction Y = "+directionVectorY );
        for (int i = 0; i < KrakoutBoard.numberOfBoxes; i++) {
            double squareX = getBoxesLocation(i).getX();
            double squareY = getBoxesLocation(i).getY();
            
            // touch the bottom/top of boxes
            if (squareX <= ball.getCenterX() && squareX + KrakoutBoard.SQUARE_SIZE>= ball.getCenterX() && 
                    ball.getCenterY() - squareY - KrakoutBoard.SQUARE_SIZE <= ball.RADIUS) {
//                System.out.println("Touching the buttom of boxes");
//                System.out.println("before vector" + directionVectorY);
                //change direction
              directionVectorY *= -1;
                       System.out.println("when touching direction Y = "+ directionVectorY );
                directionVector.normalize();
                ball.moveBall(directionVectorX + ball.getCenterX(), directionVectorY + ball.getCenterY());
                //remove the boxes
                square.get(i).removeFromDrawing();
            } 
                   System.out.println("after first touching direction Y = "+directionVectorY );
            
//            //touch the left/right of boxes
//            else if (squareY <= ball.getCenterY() && squareY + KrakoutBoard.SQUARE_SIZE>= ball.getCenterY() 
//                    && (squareX - ball.getCenterX()  <= ball.RADIUS || ball.getCenterX() - squareX - KrakoutBoard.SQUARE_SIZE <= ball.RADIUS)){
//                
//            }
            
           
        }
        System.out.println("after vector" + directionVectorY);
        System.out.println("vector " + directionVector);
               System.out.println("at the end direction Y = "+directionVectorY );
        ball.moveBall(directionVectorX + ball.getCenterX(), directionVectorY + ball.getCenterY());

//        for (int i = 0; i < KrakoutBoard.numberOfBoxes; i++) {
//            square.get(i).move(0.005);
//
//        }

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
