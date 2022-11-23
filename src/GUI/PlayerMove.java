package oceanCleanup.src.GUI;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;

public class PlayerMove {

    private BooleanProperty wPressed = new SimpleBooleanProperty();
    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty sPressed = new SimpleBooleanProperty();
    private BooleanProperty dPressed = new SimpleBooleanProperty();

    private BooleanBinding keyPressed = wPressed.or(aPressed).or(sPressed).or(dPressed);


    private ImageView image;

    private AnchorPane scene;

    public void makeMovable(ImageView image, AnchorPane scene) {
        this.image = image;
        this.scene = scene;

        movementSetup();

        keyPressed.addListener(((observableValue, aBoolean, t1) -> {
            if (!aBoolean) timer.start();
            else timer.stop();
        }));
    }

    private int movementVariable = 2;
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            if (wPressed.get()) image.setLayoutY(image.getLayoutY() - movementVariable);
            if (sPressed.get()) image.setLayoutY(image.getLayoutY() + movementVariable);
            if (aPressed.get()) image.setLayoutX(image.getLayoutX() - movementVariable);
            if (dPressed.get()) image.setLayoutX(image.getLayoutX() + movementVariable);
        }
    };

    private void movementSetup() {
        scene.setOnKeyPressed(onKeyPressedMovement());

        scene.setOnKeyReleased(onKeyReleasedMovement());
    }

    private EventHandler<KeyEvent> onKeyPressedMovement() {
        return e -> {
            if (e.getCode() == KeyCode.W) {
                if (!wPressed.get()) {
                    File file = new File(getClass().getResource("player/walk_left.gif").getPath());
                    image.setImage(new Image("file:" + file.getAbsolutePath()));
                }
                wPressed.set(true);
            }
            if (e.getCode() == KeyCode.A) {
                if (!aPressed.get()) {
                    File file = new File(getClass().getResource("player/walk_left.gif").getPath());
                    image.setImage(new Image("file:" + file.getAbsolutePath()));
                }
                aPressed.set(true);
            }
            if (e.getCode() == KeyCode.S) {
                if (!sPressed.get()) {
                    File file = new File(getClass().getResource("player/walk_right.gif").getPath());
                    image.setImage(new Image("file:" + file.getAbsolutePath()));
                }
                sPressed.set(true);
            }
            if (e.getCode() == KeyCode.D) {
                if (!dPressed.get()) {
                    File file = new File(getClass().getResource("player/walk_right.gif").getPath());
                    image.setImage(new Image("file:" + file.getAbsolutePath()));
                }
                dPressed.set(true);
            }
        };
    }

    private EventHandler<KeyEvent> onKeyReleasedMovement() {
        return e -> {

            if (e.getCode() == KeyCode.W) {
                wPressed.set(false);
            }
            if (e.getCode() == KeyCode.A) {
                aPressed.set(false);
            }
            if (e.getCode() == KeyCode.S) {
                sPressed.set(false);
            }
            if (e.getCode() == KeyCode.D) {
                dPressed.set(false);
            }
            if ((e.getCode() == KeyCode.W) || (e.getCode() == KeyCode.A)) {
                if (!wPressed.get() && !aPressed.get() && !sPressed.get() && !dPressed.get()) {
                    File file = new File(getClass().getResource("player/stand_left.png").getPath());
                    image.setImage(new Image("file:" + file.getAbsolutePath()));
                }
            } else if ((e.getCode() == KeyCode.S) || (e.getCode() == KeyCode.D)) {
                if (!wPressed.get() && !aPressed.get() && !sPressed.get() && !dPressed.get()) {
                    File file = new File(getClass().getResource("player/stand_right.png").getPath());
                    image.setImage(new Image("file:" + file.getAbsolutePath()));
                }
            }
        };
    }

}
