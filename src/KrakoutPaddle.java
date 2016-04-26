
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/*
 * draw the paddle and moveBoard method will move the paddle to a location(a,b)
 */
/**
 *
 * @author Xuanyi Zhu
 */
public class KrakoutPaddle {

    private final double LineStartX = KrakoutBoard.X_DIM_BOXES * KrakoutBoard.BOXES_SIZE * (1 - KrakoutBoard.BoardLength) * 0.5;
    private final double LineY = KrakoutBoard.Y_DIM_BOXES * KrakoutBoard.BOXES_SIZE * 0.9;
    private final double LineEndX = KrakoutBoard.X_DIM_BOXES * KrakoutBoard.BOXES_SIZE * (1 + KrakoutBoard.BoardLength) * 0.5;

    private final DoubleProperty Start_x = new SimpleDoubleProperty(LineStartX);
    private final DoubleProperty End_x = new SimpleDoubleProperty(LineEndX);
    private final DoubleProperty YValue = new SimpleDoubleProperty(LineY);

    private final Line paddle = new Line(0, 0, 0, 0);

    private final KrakoutBoard board;

    public KrakoutPaddle(KrakoutBoard board) {
        this.board = board;
        this.board.getChildren().add(paddle);

        paddle.startXProperty().bind(Start_x);
        paddle.startYProperty().bind(YValue);
        paddle.endXProperty().bind(End_x);
        paddle.endYProperty().bind(YValue);

    }

    /**
     * Move the paddle to the specified x and y board coordinates. Undoes any
     * binding currently in effect on the coordinates, so that the paddle
     * remains fixed at the specified location.
     *
     * @param a
     * @param b 
     */
    public void morePaddle(double a, double b) {
        getStart_x().unbind();
        getEnd_x().unbind();
        getStart_x().set(a);
        getEnd_x().set(b);
    }

    /**
     * 
     * @return paddle start point x value
     */
    public double getStartX() {
        return paddle.getStartX();
    }

     /**
     * 
     * @return paddle end point x value
     */
    public double getEndX() {
        return paddle.getEndX();
    }


    /**
     * 
     * @return the paddle y value
     */
    public double getPaddleY() {
        return paddle.getStartY();
    }

    /**
     * set the paddle's color and width
     * @param color
     * @param width 
     */
    void setPaddle(Color color, double width) {
        paddle.setStroke(color);
        paddle.setStrokeWidth(width);
    }

    /**
     * @return the Start_x
     */
    public DoubleProperty getStart_x() {
        return Start_x;
    }

    /**
     * @return the End_x
     */
    public DoubleProperty getEnd_x() {
        return End_x;
    }

    /**
     * @return the End_x
     */
    public DoubleProperty getYValue() {
        return YValue;
    }

    /**
     * 
     * @return the middle point of paddle x value
     */
    public double getPaddleMidX() {
        return (paddle.getStartX() + paddle.getEndX()) * 0.5;
    }
    
    /**
     * remove the paddle from the pane
     */
    void removePaddle(){
        board.getChildren().remove(paddle);
    }
}
