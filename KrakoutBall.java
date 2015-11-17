
import javafx.geometry.Point2D;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Xuanyi Zhu
 */
public class KrakoutBall {

    // set the ball radius
    final int RADIUS = 10;
    private final DoubleProperty Center_x = new SimpleDoubleProperty(KrakoutBoard.X_DIM_SQUARES * KrakoutBoard.SQUARE_SIZE * 0.5);
    private final DoubleProperty Center_y = new SimpleDoubleProperty(KrakoutBoard.Y_DIM_SQUARES * KrakoutBoard.SQUARE_SIZE * 0.9 - RADIUS);

    //private draw the ball
    private final Circle ball = new Circle(KrakoutBoard.X_DIM_SQUARES * KrakoutBoard.SQUARE_SIZE * 0.5, KrakoutBoard.Y_DIM_SQUARES * KrakoutBoard.SQUARE_SIZE * 0.9 - RADIUS, RADIUS);

    private final KrakoutBoard board;
    private final DrawingBoard line;

    Point2D directionVector;

    public KrakoutBall(KrakoutBoard board, DrawingBoard line) {
        this.board = board;
        board.getChildren().add(ball);

        this.line = line;

        ball.centerXProperty().bind(Center_x);
        ball.centerYProperty().bind(Center_y);

    }

    /**
     * @return the CenterX
     */
    public double getCenterX() {
        return ball.getCenterX();
    }

    /**
     * @return the CenterY
     */
    public double getCenterY() {
        return ball.getCenterY();
    }

    /**
     * set the ball's color
     *
     * @param StrokeColor
     * @param FillColor
     */
    void setBall(Color StrokeColor, Color FillColor) {
        ball.setStroke(StrokeColor);
        ball.setFill(FillColor);
    }

    public Point2D updateVector() {

        // if the ball touching the upper boundary, the direction vector will change
        if (ball.getCenterY() <= RADIUS) {
            directionVector = new Point2D(directionVector.getX(), -directionVector.getY());
        } // if the ball touching the left or right boundary, the direction vector will change
        else if (getCenterX() <= RADIUS || getCenterX() + RADIUS > KrakoutBoard.X_DIM_SQUARES * KrakoutBoard.SQUARE_SIZE) {
            directionVector = new Point2D(-directionVector.getX(), directionVector.getY());

        } else if ((line.getStartX() < getCenterX() + RADIUS && getCenterX() - RADIUS < line.getEndX()) && getCenterY() + RADIUS == line.getLineY()
                || (line.getStartX() < getCenterX() && getCenterX() < line.getEndX()) && getCenterY() + RADIUS > line.getLineY()) {
            directionVector = new Point2D(getCenterX() - line.getLineMidX(), getCenterY() - KrakoutBoard.Y_DIM_SQUARES * KrakoutBoard.SQUARE_SIZE);

        }

        return directionVector.normalize().multiply(4);

    }

    void moveBall(double a, double b) {
        Center_x.unbind();
        Center_y.unbind();
        Center_x.set(a);
        Center_y.set(b);

    }

}
