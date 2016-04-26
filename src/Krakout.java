/*
 * Krakout is a game like brick breaker but with emojis style which the player 
 * smash a wall of emojis by deflecting a bouncing a ball with a paddle. The 
 * paddle moves horizontally and is controlled by the directional keys on the keyboard.
 * ##############################################################################
 * Krakout is the main class that sets up the animation and key events and creates the game, ball and paddle. 
 * @author Xuanyi Zhu & Tariye Peter 
 */

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Krakout extends Application {

    //animation speed
    private static final double MILLISEC = 1;
    private KrakoutGame game;
    private KrakoutBoard krakoutBoard;
    private Timeline animation;
    private Label statusLabel;
    public BorderPane pane;

    /**
     * Launches the application.
     *
     * @param args the command paddle arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Sets up the krakout pane and game, as well as a status label that can be
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
        //set menu bar
        MenuBar menuBar = setMenu();

        //set pane
        BorderPane pane = new BorderPane();
        this.pane = pane;
        pane.setCenter(krakoutBoard);
        pane.setTop(menuBar);
        Scene scene = new Scene(pane);
        pane.setBottom(statusLabel);

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
    protected void setUpAnimation() {
        // Create a handler
        EventHandler<ActionEvent> eventHandler = (ActionEvent e) -> {
            try {
                this.pause();
                try {
                    game.update();
                } catch (Exception ex) {
                    Logger.getLogger(Krakout.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.resume();
                endGame();

            } catch (Exception ex) {
                Logger.getLogger(Krakout.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    void endGame() throws Exception {

        if (game.ball.getCenterY() >= (KrakoutBoard.BOXES_SIZE * KrakoutBoard.Y_DIM_BOXES)) {
            Sound.sound4.play();

            JOptionPane.showMessageDialog(null, "Game Over!!!");
            if (game.points >= krakoutBoard.numberOfBoxes * 0.7 * 5) {
                game.name = JOptionPane.showInputDialog(null, "Enter Your Name");
            }
            String message = "Your score is: " + game.getPOINTS();
            JOptionPane.showMessageDialog(null, message);

            game.FileWrite();
            int reply = JOptionPane.showConfirmDialog(null, "Do You Want to Start a new game?", " ", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {

                game.delete();
                game.setGame(krakoutBoard);
            } else if (reply == JOptionPane.NO_OPTION) {
                System.exit(0);
            }

        } else if (game.boxes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "You Win!!!!");

            game.name = JOptionPane.showInputDialog(null, "Enter Your Name");
            String message = "Your score is: " + game.getPOINTS();
            JOptionPane.showMessageDialog(null, message);
            System.exit(0);
        }

    }

    /**
     * setup menu, which include menu, score, setting
     *
     * @return menu
     */
    public MenuBar setMenu() {

        MenuBar menuBar = new MenuBar();

        BorderPane pane = new BorderPane();
        this.pane = pane;

        Menu menuFile = new Menu("Menu");

        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction((ActionEvent t) -> {
            game.delete();
            game.setGame(krakoutBoard);
        });
        
        // pause the game
        MenuItem Pause = new MenuItem("Pause");
        Pause.setOnAction((ActionEvent t) -> {
            pause();
            Sound.sound3.stop();
        });
        
       //resume the game 
        MenuItem Resume = new MenuItem("Resume");
        Resume.setOnAction((ActionEvent t) -> {
            resume();
            Sound.sound3.loop();
        });
         // add MenuItem to menu
        menuFile.getItems().addAll(newGame, Pause, Resume);

        // Score menu
        Menu menuScore = new Menu("Score");
        MenuItem Best = new MenuItem("High Scores");
        Best.setOnAction((ActionEvent t) -> {
            pause();
            Sound.sound3.stop();
            try {
                Scanner file = new Scanner(new File("Scores.txt"));
                String read = "";
                while (file.hasNextLine()) {

                    read += file.nextLine() + "\n";
                }
                JOptionPane.showMessageDialog(null, new JTextArea(read));
            } catch (Exception errorMessage) {

            }
            resume();
            Sound.sound3.loop();
        });

        menuScore.getItems().addAll(Best);

        Menu menuSetting = new Menu("Setting");

        // Mute all sounds
        MenuItem Mute = new MenuItem("Mute");
        Mute.setOnAction((ActionEvent t) -> {
            Sound.sound3.stop();
        });

        //Unmute all sounds
        MenuItem Unmute = new MenuItem("Unmute");
        Unmute.setOnAction((ActionEvent t) -> {
            Sound.sound3.loop();
        });

        menuSetting.getItems().addAll(Mute, Unmute);

        menuBar.getMenus().addAll(menuFile, menuScore, menuSetting);
        return menuBar;
    }
}
