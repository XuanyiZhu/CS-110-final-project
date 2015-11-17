/*
 * Game like break breaker which the player smash a wall of bricks by deflecting 
 * a bouncing ball with a paddle. The paddle moves horizontally and is controlled by
 * the directional keys on the keyboard. The bricks are coming down.
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The Krakout Application, which contains the board and a message label.
 *
 * @author Xuanyi Zhu
 */
public class Krakout extends Application {

    private static final double MILLISEC = 1;
    private KrakoutGame game;
    private KrakoutBoard krakoutBoard;
    private Timeline animation;
    private Label statusLabel;

    /**
     * Launches the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Sets up the krakout board and game, as well as a status label that can be
     * used to display scores and messages.
     *
     * Enables key events for the arrow keys and space bar, as well as an
     * animation.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        krakoutBoard = new KrakoutBoard();

        statusLabel = new Label("Krakout");
        statusLabel.setTextFill(Color.RED);

        BorderPane pane = new BorderPane();
        pane.setCenter(krakoutBoard);
        pane.setTop(statusLabel);

        Scene scene = new Scene(pane);

        game = new KrakoutGame(this, krakoutBoard);

        setUpAnimation();

        setUpKeyPresses();

        primaryStage.setTitle("Krakout");

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    /**
     * Changes the message in the status label at the top of the screen.
     *
     * @param message
     */
    public void setMessage(String message) {
        statusLabel.setText(message);
    }

    /**
     * Sets up an animation timeline that calls update on the game every
     * MILLISEC milliseconds.
     */
    private void setUpAnimation() {
        // Create a handler
        EventHandler<ActionEvent> eventHandler = (ActionEvent e) -> {
            this.pause();
            game.update();
            this.resume();
        };

        // Create an animation for alternating text
        animation = new Timeline(new KeyFrame(Duration.millis(MILLISEC), eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    /**
     * Sets up key events for the arrow keys and space bar. All keys send
     * messages to the game, which should react appropriately.
     */
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>fix delay<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private void setUpKeyPresses() {
        krakoutBoard.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    game.left();
                    break;
                case RIGHT:
                    game.right();
                    break;

            }
        });
        krakoutBoard.requestFocus(); // board is focused to receive key input

    }

    /**
     * Pauses the animation.
     */
    private void pause() {
        animation.pause();
    }

    /**
     * Resumes the animation.
     */
    private void resume() {
        animation.play();
    }

}
