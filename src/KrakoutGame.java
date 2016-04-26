/*
 * Controls the game logic. 
 * 
 * @author Xuanyi Zhu & Tariye Peter
 */

import java.io.FileWriter;
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public final class KrakoutGame {

    protected int points;
    protected String name;
    private final Krakout krakoutApp;
    protected KrakoutPaddle paddle;
    protected KrakoutBall ball;
    //create a ArrayList to contain all the  boxes
    ArrayList<KrakoutBoxes> boxes = new ArrayList<>();
    //add a background image
    Image backgroundImage = new Image("image.jpg");

    private KrakoutBoard board;

    /**
     * Initialize the game. setup the game
     *
     * @param krakoutApp
     * @param board
     */
    public KrakoutGame(Krakout krakoutApp, KrakoutBoard board) {
        this.krakoutApp = krakoutApp;
        setGame(board);
    }

    /**
     * play the background music, add ball, paddle, boxes to the pane set the
     * boxes images, ball image, and background image initialize the score and
     * display game started message
     *
     * @param board
     */
    public void setGame(KrakoutBoard board) {
        //set BG
        krakoutApp.pane.setBackground(Background(backgroundImage));
        //play sound
        Sound.sound3.loop();

        //add and set the paddle
        KrakoutPaddle line = new KrakoutPaddle(board);
        line.setPaddle(Color.WHITE, 10.0);
        this.paddle = line;

        //add and set ball
        KrakoutBall ball = new KrakoutBall(board, line);
        ball.setBall(new ImagePattern(new Image("ball.png")));
        this.ball = ball;

        //initial the points
        points = 0;

        //create boxes and set image
        for (int i = 0; i < KrakoutBoard.numberOfBoxes; i++) {
            boxes.add(new KrakoutBoxes(board));
            boxes.get(i).moveToKrakoutLocation(i % KrakoutBoard.X_DIM_BOXES, i / KrakoutBoard.X_DIM_BOXES);
            //store the boxes image(emoji) to an array
            Image[] image = {new Image("square1.png"), new Image("square2.png"),
                new Image("square3.png"), new Image("square4.png"), new Image("square5.png"),
                new Image("square6.png"), new Image("square7.png"), new Image("square8.png"),
                new Image("square9.png"), new Image("square10.png"), new Image("square11.png"),
                new Image("square12.png"), new Image("square13.png"), new Image("square14.png"),
                new Image("square15.png"), new Image("square16.png"), new Image("square17.png"),
                new Image("square18.png"), new Image("square19.png"), new Image("square20.png"),
                new Image("square21.png")};
            //set boxes image differently
            boxes.get(i).setBox(new ImagePattern(image[i % 21]));
        }
        krakoutApp.setMessage("Game has started!");
    }

    // delete all the elements from the pane
    public void delete() {
        ball.removeBall();
        for (int i = 0; i < boxes.size(); i++) {
            boxes.get(i).removeFromDrawing();
        }
        boxes.removeAll(boxes);
        paddle.removePaddle();
        krakoutApp.setMessage("");
    }

    /**
     * get the index of a box in the ArrayList
     *
     * @param i
     * @return Location of the box
     */
    public Point2D getBoxesLocation(int i) {
        Point2D Location = new Point2D(boxes.get(i).getX() * KrakoutBoard.BOXES_SIZE, boxes.get(i).getY() * KrakoutBoard.BOXES_SIZE);
        return Location;
    }

    /**
     * get the direction vector and move ball check for win or lose the game
     */
    void update() throws Exception {
        updateVector();
        ball.moveBall();

//boxes will slowing moving down
//        for (int i = 0; i < boxes.size(); i++) {
//            boxes.get(i).move(0.001);
//        }
    }

    /**
     * Move the current krakout piece left.
     */
    void left() {
        if (paddle.getStartX() > 25) {
            paddle.morePaddle(paddle.getStartX() - 25, paddle.getEndX() - 25);
        }
    }

    /**
     * Move the current tetris piece right.
     */
    void right() {
        if (paddle.getEndX() < (KrakoutBoard.X_DIM_BOXES * KrakoutBoard.BOXES_SIZE - 25)) {
            paddle.morePaddle(paddle.getStartX() + 25, paddle.getEndX() + 25);
        }
    }

    /**
     *
     * @return points
     */
    int getPOINTS() {
        return points;
    }

    /**
     *
     * @return the user name
     */
    String getName() {
        return name;
    }

    /**
     * Creates the Background image
     *
     * @param image imports an image file
     * @return new background
     */
    public Background Background(Image image) {
        BackgroundSize backgroundSize = new BackgroundSize(board.PANE_X, board.PANE_Y, false, false, false, false); // new BackgroundImage(image, repeatX, repeatY, position, size)
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize); // new Background(images...)
        Background background = new Background(backgroundImage);
        return background;
    }

    /**
     * open a txt file and write
     */
    public void FileWrite() {
        try {
            String filename = "Scores.txt";
            FileWriter fw = new FileWriter(filename, true); //the true will append the new data
            if (name != null && points >= board.numberOfBoxes * 0.7 * 5) {
                fw.write("\n" + name + "\t" + points);//appends the string to the file
                fw.close();
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Check if the ball is touching the boarder and any boxes if touching the
     * top of the boarder, paddle, or boxes' top and bottom, change direction
     * vector Y to to opposite sign if touching the left or right side of
     * boarder or boxes, change direction vector X to to opposite sign normalize
     * the direction vector if the ball touch the box, remove the box from pane
     * and its ArrayList while touching, play sound1(only if the background
     * music is playing)
     */
    public void updateVector() {
        double radius = ball.RADIUS;
        double ballX = ball.getCenterX();
        double ballY = ball.getCenterY();
        Point2D directionVector = ball.getDirectionVector();

        //touching top
        if (ballY <= radius) {
            ball.setDirectionVector(new Point2D(directionVector.getX(), -directionVector.getY()).normalize());
            if (Sound.sound3.isActive()) {
                Sound.sound1.play();
            }
        }
        // if the ball touching the left or right boundary, the direction vector will change
        else if (ballX <= radius || ballX + radius > KrakoutBoard.X_DIM_BOXES * KrakoutBoard.BOXES_SIZE) {
            ball.setDirectionVector(new Point2D(-directionVector.getX(), directionVector.getY()).normalize());
            if (Sound.sound3.isActive()) {
                Sound.sound1.play();
            }
        }
        //the ball touching the paddle
        else if ((paddle.getStartX() < ballX && ballX < paddle.getEndX()) && Math.abs(paddle.getPaddleY() - ballY) <= radius) {
            ball.setDirectionVector(new Point2D(ballX - paddle.getPaddleMidX(), ballY - KrakoutBoard.Y_DIM_BOXES * KrakoutBoard.BOXES_SIZE).normalize());
            if (Sound.sound3.isActive()) {
                Sound.sound1.play();
            }
        }
        
        // check for each boxes
        for (int i = 0; i < boxes.size(); i++) {
            double squareX = getBoxesLocation(i).getX();
            double squareY = getBoxesLocation(i).getY();
            
            // touch the bottom/top of boxes
            if ((squareX <= ballX && squareX + KrakoutBoard.BOXES_SIZE >= ballX
                    && Math.abs(ballY - squareY - KrakoutBoard.BOXES_SIZE) <= radius)) {
                if (Sound.sound3.isActive()) {
                    Sound.sound2.play();
                }

                //change direction
                ball.setDirectionVector(new Point2D(directionVector.getX(), -directionVector.getY()).normalize());
                //remove the boxes
                boxes.get(i).removeFromDrawing();
                boxes.remove(i);
                points = points + 5;
                krakoutApp.setMessage("Score: " + points);
            }
            //Touching the top of boxes
            else if (squareX <= ballX && squareX + KrakoutBoard.BOXES_SIZE >= ballX && Math.abs(squareY - ballY) <= radius) {
                if (Sound.sound3.isActive()) {
                    Sound.sound2.play();
                }
                //change direction
                ball.setDirectionVector(new Point2D(directionVector.getX(), -directionVector.getY()).normalize());
                //remove the boxes
                boxes.get(i).removeFromDrawing();
                boxes.remove(i);
                points = points + 5;
                krakoutApp.setMessage("Score: " + points);
            }
            //touching the left or right side of boxes
            else if (squareY <= ballY && squareY + KrakoutBoard.BOXES_SIZE >= ballY
                    && (Math.abs(squareX - ballX) <= radius || Math.abs(ballX - squareX - KrakoutBoard.BOXES_SIZE) <= radius)) {
                if (Sound.sound3.isActive()) {
                    Sound.sound2.play();
                }
                //change direction
                ball.setDirectionVector(new Point2D(-directionVector.getX(), directionVector.getY()).normalize());

                //remove the boxes
                boxes.get(i).removeFromDrawing();
                boxes.remove(i);

                points = points + 5;
                krakoutApp.setMessage("Score: " + points);
            }
        }
    }

}
