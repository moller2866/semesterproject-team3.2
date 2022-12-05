package oceanCleanup.src.GUI;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
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


    private double playerHeight;
    private double playerWidth;

    private AnchorPane scene;

    private boolean collision;

    private Pane playerStack;

    private ArrayList<Pane> colliders;
    private String collisionId;
    private ImageView playerImage;
    private ImageView bucketImage;
    private ImageView collisionImage;

    public void makeMovable(Pane playerPane, AnchorPane scene) {
        this.playerStack = playerPane;
        this.playerHeight = playerPane.getBoundsInParent().getHeight();
        this.playerWidth = playerPane.getBoundsInParent().getWidth();
        this.colliders = new ArrayList<>();
        this.scene = scene;

        for (Node image : playerPane.getChildren()) {
            if (image.getId().equalsIgnoreCase("collider")) {
                this.collisionImage = (ImageView) image;
            } else if (image.getId().equalsIgnoreCase("player")) {
                this.playerImage = (ImageView) image;
            }
        }


        keyPressed.addListener(((observableValue, aBoolean, t1) -> {
            if (!aBoolean) timer.start();
            else timer.stop();
        }));
    }

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            final int movementVariable = 2;
            if (wPressed.get()) playerStack.setLayoutY(playerStack.getLayoutY() - movementVariable);
            if (aPressed.get()) playerStack.setLayoutX(playerStack.getLayoutX() - movementVariable);
            if (sPressed.get()) playerStack.setLayoutY(playerStack.getLayoutY() + movementVariable);
            if (dPressed.get()) playerStack.setLayoutX(playerStack.getLayoutX() + movementVariable);
            playerAtBorder();
            playerAtCollision();
        }
    };

    private void playerAtCollision() {
        for (Pane collider : colliders) {
            if (playerStack.getBoundsInParent().intersects(collider.getBoundsInParent())) {
                collisionId = collider.getId();
                collisionImage.setLayoutX(playerStack.getLayoutX());
                collisionImage.setLayoutY(playerStack.getLayoutY());
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
                    playerImage.setImage(new Image(getClass().getResource("player/walk_left.gif").toExternalForm()));
                }
                wPressed.set(true);
            }
            case A -> {
                if (!aPressed.get()) {
                    playerImage.setImage(new Image(getClass().getResource("player/walk_left.gif").toExternalForm()));
                }
                aPressed.set(true);
            }
            case S -> {
                if (!sPressed.get()) {
                    playerImage.setImage(new Image(getClass().getResource("player/walk_right.gif").toExternalForm()));
                }
                sPressed.set(true);
            }
            case D -> {
                if (!dPressed.get()) {
                    playerImage.setImage(new Image(getClass().getResource("player/walk_right.gif").toExternalForm()));
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
                    playerImage.setImage(new Image(getClass().getResource("player/stand_left.png").toExternalForm()));
                }
            }
            case A -> {
                aPressed.set(false);
                if (!wPressed.get() && !aPressed.get() && !sPressed.get() && !dPressed.get()) {
                    playerImage.setImage(new Image(getClass().getResource("player/stand_left.png").toExternalForm()));
                }
            }
            case S -> {
                sPressed.set(false);
                if (!wPressed.get() && !aPressed.get() && !sPressed.get() && !dPressed.get()) {
                    playerImage.setImage(new Image(getClass().getResource("player/stand_right.png").toExternalForm()));
                }
            }
            case D -> {
                dPressed.set(false);
                if (!wPressed.get() && !aPressed.get() && !sPressed.get() && !dPressed.get()) {
                    playerImage.setImage(new Image(getClass().getResource("player/stand_right.png").toExternalForm()));
                }
            }
        }
    }

    public void addBucket(ImageView image) {
        this.bucketImage = image;
        playerStack.getChildren().add(image);
        image.setTranslateX(-5);
        image.setTranslateY(27);
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

        if (playerStack.getLayoutX() < leftBorder) playerStack.setLayoutX(leftBorder);
        if (playerStack.getLayoutX() > rightBorder) playerStack.setLayoutX(rightBorder);
        if (playerStack.getLayoutY() < topBorder) playerStack.setLayoutY(topBorder);
        if (playerStack.getLayoutY() > bottomBorder) playerStack.setLayoutY(bottomBorder);
    }

    public ImageView dropBucket() {
        playerStack.getChildren().remove(bucketImage);
        return bucketImage;
    }

    public boolean hasBucket() {
        for (Node image : playerStack.getChildren()) {
            if (image.getId().equalsIgnoreCase("bucket")) {
                return true;
            }
        }
        return false;
    }

    public boolean isColliding() {
        return collision;
    }

    public String getCollision() {
        return collisionId;
    }

    public void setPlayerPosition(double x, double y) {
        playerStack.setLayoutX(x);
        playerStack.setLayoutY(y);
        collisionImage.setVisible(false);
        collision = false;
    }
}
