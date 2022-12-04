package oceanCleanup.src.GUI;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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

    private ImageView collisionImage;
    private boolean collision;

    private ArrayList<Pane> colliders;
    private String collisionId;

    public void makeMovable(ImageView image, AnchorPane scene) {
        this.images = new ArrayList<>();
        this.images.add(image);
        this.playerHeight = image.getBoundsInParent().getHeight();
        this.playerWidth = image.getBoundsInParent().getWidth();
        this.colliders = new ArrayList<>();
        this.handOffsetX = playerWidth / 2 - 15;
        this.handOffsetY = playerHeight / 2 + 8;
        this.scene = scene;
        this.collisionImage = new ImageView(new Image(getClass().getResource("player/go_room.png").toExternalForm()));
        this.collisionImage.setScaleX(2);
        this.collisionImage.setScaleY(2);
        scene.getChildren().add(this.collisionImage);
        collisionImage.setVisible(false);

        keyPressed.addListener(((observableValue, aBoolean, t1) -> {
            if (!aBoolean) timer.start();
            else timer.stop();
        }));
    }

    private int movementVariable = 2;
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            for (ImageView image : images) {
                if (wPressed.get()) image.setLayoutY(image.getLayoutY() - movementVariable);
                if (aPressed.get()) image.setLayoutX(image.getLayoutX() - movementVariable);
                if (sPressed.get()) image.setLayoutY(image.getLayoutY() + movementVariable);
                if (dPressed.get()) image.setLayoutX(image.getLayoutX() + movementVariable);
            }
            playerAtBorder();
            playerAtCollision();
        }
    };

    private void playerAtCollision() {
        for (Pane collider : colliders) {
            if (images.get(0).getBoundsInParent().intersects(collider.getBoundsInParent())) {
                collisionId = collider.getId();
                collisionImage.setLayoutX(images.get(0).getLayoutX());
                collisionImage.setLayoutY(images.get(0).getLayoutY());
                collisionImage.setVisible(true);
                collision = true;
                break;
            } else {
                collisionImage.setVisible(false);
                collision = false;
            }
        }
    }


    public void onKeyPressedMovement(KeyCode code) {
        switch (code) {
            case W -> {
                if (!wPressed.get()) {
                    images.get(0).setImage(new Image(getClass().getResource("player/walk_left.gif").toExternalForm()));
                }
                wPressed.set(true);
            }
            case A -> {
                if (!aPressed.get()) {
                    images.get(0).setImage(new Image(getClass().getResource("player/walk_left.gif").toExternalForm()));
                }
                aPressed.set(true);
            }
            case S -> {
                if (!sPressed.get()) {
                    images.get(0).setImage(new Image(getClass().getResource("player/walk_right.gif").toExternalForm()));
                }
                sPressed.set(true);
            }
            case D -> {
                if (!dPressed.get()) {
                    images.get(0).setImage(new Image(getClass().getResource("player/walk_right.gif").toExternalForm()));
                }
                dPressed.set(true);
            }
        }
    }

    public void onKeyReleasedMovement(KeyCode code) {
        switch (code) {
            case W -> {
                wPressed.set(false);
                if (!wPressed.get() && !aPressed.get() && !sPressed.get() && !dPressed.get()) {
                    images.get(0).setImage(new Image(getClass().getResource("player/stand_left.png").toExternalForm()));
                }
            }
            case A -> {
                aPressed.set(false);
                if (!wPressed.get() && !aPressed.get() && !sPressed.get() && !dPressed.get()) {
                    images.get(0).setImage(new Image(getClass().getResource("player/stand_left.png").toExternalForm()));
                }
            }
            case S -> {
                sPressed.set(false);
                if (!wPressed.get() && !aPressed.get() && !sPressed.get() && !dPressed.get()) {
                    images.get(0).setImage(new Image(getClass().getResource("player/stand_right.png").toExternalForm()));
                }
            }
            case D -> {
                dPressed.set(false);
                if (!wPressed.get() && !aPressed.get() && !sPressed.get() && !dPressed.get()) {
                    images.get(0).setImage(new Image(getClass().getResource("player/stand_right.png").toExternalForm()));
                }
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

    public void addCollider(Pane collider) {
        scene.getChildren().add(collider);
        colliders.add(collider);
    }

    public void clearColliders() {
        scene.getChildren().removeAll(colliders);
        colliders.clear();
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

    public boolean isColliding() {
        return collision;
    }

    public String getCollision() {
        return collisionId;
    }

    public void setPlayerPosition(double x, double y) {
        images.get(0).setLayoutX(x);
        images.get(0).setLayoutY(y);
        for (int i = 1; i < images.size(); i++) {
            images.get(i).setLayoutX(x + handOffsetX);
            images.get(i).setLayoutY(y + handOffsetY);
        }
        collisionImage.setVisible(false);
        collision = false;
    }
}
