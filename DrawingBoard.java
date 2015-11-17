
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Xuanyi Zhu
 */
public class DrawingBoard {

    private final double LineStartX = KrakoutBoard.X_DIM_SQUARES * KrakoutBoard.SQUARE_SIZE * (1 - KrakoutBoard.BoardLength) * 0.5;
    private final double LineY = KrakoutBoard.Y_DIM_SQUARES * KrakoutBoard.SQUARE_SIZE * 0.9;
    private final double LineEndX = KrakoutBoard.X_DIM_SQUARES * KrakoutBoard.SQUARE_SIZE * (1 + KrakoutBoard.BoardLength) * 0.5;

    private final DoubleProperty Start_x = new SimpleDoubleProperty(LineStartX);
    private final DoubleProperty End_x = new SimpleDoubleProperty(LineEndX);
    private final DoubleProperty YValue = new SimpleDoubleProperty(LineY);

    private final Line line = new Line(0, 0, 0, 0);

    private final KrakoutBoard board;

    public DrawingBoard(KrakoutBoard board) {
        this.board = board;
        this.board.getChildren().add(line);

        line.startXProperty().bind(Start_x);
        line.startYProperty().bind(YValue);
        line.endXProperty().bind(End_x);
        line.endYProperty().bind(YValue);

    }

    public void moreBoard(double a, double b) {
        getStart_x().unbind();
        getEnd_x().unbind();
        getStart_x().set(a);
        getEnd_x().set(b);
    }

    public double getStartX() {
        return line.getStartX();
    }

    public double getEndX() {
        return line.getEndX();
    }

    // since line start y is equal to end y, so i let line y = start y
    public double getLineY() {
        return line.getStartY();
    }

    void setLine(Color color, double width) {
        line.setStroke(color);
        line.setStrokeWidth(width);
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

    public double getLineMidX() {
        return (line.getStartX() + line.getEndX()) * 0.5;
    }

}
