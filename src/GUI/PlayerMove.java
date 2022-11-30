package oceanCleanup.src.GUI;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.util.ArrayList;

public class PlayerMove {

    private BooleanProperty wPressed = new SimpleBooleanProperty();
    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty sPressed = new SimpleBooleanProperty();
    private BooleanProperty dPressed = new SimpleBooleanProperty();

    private BooleanBinding keyPressed = wPressed.or(aPressed).or(sPressed).or(dPressed);


    private ArrayList<ImageView> images;
    private double playerHeight;
    private double playerWidth;

    private double handOffsetX;
    private double handOffsetY;
    private AnchorPane scene;

    public void makeMovable(ImageView image, AnchorPane scene) {
        this.images = new ArrayList<>();
        this.images.add(image);
        this.playerHeight = image.getBoundsInParent().getHeight();
        this.playerWidth = image.getBoundsInParent().getWidth();

        this.handOffsetX = playerWidth / 2 - 15;
        this.handOffsetY = playerHeight / 2 + 8;

        this.scene = scene;

        keyPressed.addListener(((observableValue, aBoolean, t1) -> {
            if (!aBoolean) timer.start();
            else timer.stop();
        }));
    }

    private int movementVariable = 2;
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            // set layoutX and layoutY for each image
            for (ImageView image : images) {
                if (wPressed.get()) {
                    image.setLayoutY(image.getLayoutY() - movementVariable);
                }
                if (aPressed.get()) {
                    image.setLayoutX(image.getLayoutX() - movementVariable);
                }
                if (sPressed.get()) {
                    image.setLayoutY(image.getLayoutY() + movementVariable);
                }
                if (dPressed.get()) {
                    image.setLayoutX(image.getLayoutX() + movementVariable);
                }
            }
            playerAtBorder();
        }
    };

    public void onKeyPressedMovement(KeyCode code) {
        if (code == KeyCode.W) {
            if (!wPressed.get()) {
                File file = new File(getClass().getResource("player/walk_left.gif").getPath());
                images.get(0).setImage(new Image("file:" + file.getAbsolutePath()));
            }
            wPressed.set(true);
        }
        if (code == KeyCode.A) {
            if (!aPressed.get()) {
                File file = new File(getClass().getResource("player/walk_left.gif").getPath());
                images.get(0).setImage(new Image("file:" + file.getAbsolutePath()));
            }
            aPressed.set(true);
        }
        if (code == KeyCode.S) {
            if (!sPressed.get()) {
                File file = new File(getClass().getResource("player/walk_right.gif").getPath());
                images.get(0).setImage(new Image("file:" + file.getAbsolutePath()));
            }
            sPressed.set(true);
        }
        if (code == KeyCode.D) {
            if (!dPressed.get()) {
                File file = new File(getClass().getResource("player/walk_right.gif").getPath());
                images.get(0).setImage(new Image("file:" + file.getAbsolutePath()));
            }
            dPressed.set(true);
        }
    }

    public void onKeyReleasedMovement(KeyCode code) {
        if (code == KeyCode.W) {
            wPressed.set(false);
        }
        if (code == KeyCode.A) {
            aPressed.set(false);
        }
        if (code == KeyCode.S) {
            sPressed.set(false);
        }
        if (code == KeyCode.D) {
            dPressed.set(false);
        }
        if ((code == KeyCode.W) || (code == KeyCode.A)) {
            if (!wPressed.get() && !aPressed.get() && !sPressed.get() && !dPressed.get()) {
                File file = new File(getClass().getResource("player/stand_left.png").getPath());
                images.get(0).setImage(new Image("file:" + file.getAbsolutePath()));
            }
        } else if ((code == KeyCode.S) || (code == KeyCode.D)) {
            if (!wPressed.get() && !aPressed.get() && !sPressed.get() && !dPressed.get()) {
                File file = new File(getClass().getResource("player/stand_right.png").getPath());
                images.get(0).setImage(new Image("file:" + file.getAbsolutePath()));
            }
        }
    }

    public void addImage(ImageView image) {
        image.setLayoutX(images.get(0).getLayoutX() + handOffsetX);
        image.setLayoutY(images.get(0).getLayoutY() + handOffsetY);
        images.add(image);
    }

    public void removeImage(ImageView image) {
        image.setLayoutX(images.get(0).getLayoutX() + handOffsetX);
        image.setLayoutY(images.get(0).getLayoutY() + handOffsetY);
        images.remove(image);
    }


    public void playerAtBorder() {
        double leftBorder = 0;
        double rightBorder = scene.getWidth() - playerWidth;
        double bottomBorder = scene.getHeight() - playerHeight;
        double topBorder = 0;
        
        if (images.get(0).getLayoutX() < leftBorder) {
            images.get(0).setLayoutX(leftBorder);
            for (int i = 1; i < images.size(); i++) {
                images.get(i).setLayoutX(leftBorder + handOffsetX);
            }
        }
        if (images.get(0).getLayoutX() > rightBorder) {
            images.get(0).setLayoutX(rightBorder);
            for (int i = 1; i < images.size(); i++) {
                images.get(i).setLayoutX(rightBorder + handOffsetX);
            }
        }
        if (images.get(0).getLayoutY() < topBorder) {
            images.get(0).setLayoutY(topBorder);
            for (int i = 1; i < images.size(); i++) {
                images.get(i).setLayoutY(topBorder + handOffsetY);
            }
        }
        if (images.get(0).getLayoutY() > bottomBorder) {
            images.get(0).setLayoutY(bottomBorder);
            for (int i = 1; i < images.size(); i++) {
                images.get(i).setLayoutY(bottomBorder + handOffsetY);
            }
        }
    }

    public ArrayList<ImageView> dropItems() {
        ArrayList<ImageView> droppedItems = new ArrayList<>();
        for (int i = 1; i < images.size(); i++) {
            droppedItems.add(images.get(i));
        }
        images.removeAll(droppedItems);
        return droppedItems;
    }

    public boolean hasBucket() {
        return images.size() > 1;
    }
}
