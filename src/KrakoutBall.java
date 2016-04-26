import javafx.geometry.Point2D;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/*
 * KrakoutBall class creates the ball. The initial position of the ball is (0.5 * PaneWidth, 0.9 * PaneHeight)
 * Ball has a fixed redius. The ball has direction vector, it change the ball's 
 * direction when the ball is interacting with the other element. 
 * 
 * @author Xuanyi Zhu & Tariye Peter
 */
public class KrakoutBall {

    // set the ball radius
    final int RADIUS = 18;
    private final DoubleProperty Center_x = new SimpleDoubleProperty(KrakoutBoard.X_DIM_BOXES * KrakoutBoard.BOXES_SIZE * 0.5);
    private final DoubleProperty Center_y = new SimpleDoubleProperty(KrakoutBoard.Y_DIM_BOXES * KrakoutBoard.BOXES_SIZE * 0.9 - RADIUS);

    //draw the ball
    private final Circle ball = new Circle(KrakoutBoard.X_DIM_BOXES * KrakoutBoard.BOXES_SIZE * 0.5, KrakoutBoard.Y_DIM_BOXES * KrakoutBoard.BOXES_SIZE * 0.9 - RADIUS, RADIUS);
    private final KrakoutBoard board;
    private Point2D directionVector;

    public KrakoutBall(KrakoutBoard board, KrakoutPaddle line) {
        this.board = board;
        board.getChildren().add(ball);

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
     * change the ball image
     * @param image 
     */
    void setBall(ImagePattern image) {
        ball.setFill(image);
    }
    
    /**
     * move the ball
     * move the ball by direction vector
     */
    void moveBall() {
        Center_x.unbind();
        Center_y.unbind();
        Center_x.set(getDirectionVector().getX()*6 + ball.getCenterX());
        Center_y.set(getDirectionVector().getY()*6 + ball.getCenterY());
    }

    /**
     * @return the directionVector
     */
    public Point2D getDirectionVector() {
        return directionVector;
    }

    /**
     * @param directionVector the directionVector to set
     */
    public void setDirectionVector(Point2D directionVector) {
        this.directionVector = directionVector;
    }
    /**
     * remove ball from the pame
     */
    void removeBall(){
        board.getChildren().remove(ball);
    }
    
}
