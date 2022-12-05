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

    private Pane playerPane;

    private ArrayList<Pane> colliders;
    private String collisionId;
    private ImageView playerImage;
    private ImageView bucketImage;
    private ImageView collisionImage;

    private boolean borderTop, borderRight, borderBottom, borderLeft;

    public void makeMovable(Pane playerPane, AnchorPane scene) {
        this.playerPane = playerPane;
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
            if (aPressed.get() && !borderLeft) playerPane.setLayoutX(playerPane.getLayoutX() - movementVariable);
            if (wPressed.get() && !borderTop) playerPane.setLayoutY(playerPane.getLayoutY() - movementVariable);
            if (sPressed.get() && !borderBottom) playerPane.setLayoutY(playerPane.getLayoutY() + movementVariable);
            if (dPressed.get() && !borderRight) playerPane.setLayoutX(playerPane.getLayoutX() + movementVariable);
            playerAtBorder();
            playerAtCollision();
        }
    };

    private void playerAtCollision() {
        for (Pane collider : colliders) {
            if (playerPane.getBoundsInParent().intersects(collider.getBoundsInParent())) {
                collisionId = collider.getId();
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
        playerPane.getChildren().add(image);
        image.setLayoutY(60);
        image.setLayoutX(10);
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

        borderLeft = false;
        borderRight = false;
        borderTop = false;
        borderBottom = false;

        for (Node node : scene.getChildren()) {
            if (node.getId() != null && node.getId().equalsIgnoreCase("border")) {
                if (node.getBoundsInParent().intersects(playerPane.getBoundsInParent())) {

                    if ((playerPane.getLayoutX() > node.getLayoutX())
                            && (playerPane.getLayoutX() >= node.getLayoutX() + node.getBoundsInParent().getWidth() - 5)) {
                        borderLeft = true;
                    }
                    if ((playerPane.getLayoutX() + playerWidth <= node.getLayoutX())
                            && (playerPane.getLayoutX() + playerWidth < node.getLayoutX() + node.getBoundsInParent().getWidth())) {
                        borderRight = true;
                    }
                    if ((playerPane.getLayoutY() > node.getLayoutY())
                            && (playerPane.getLayoutY() >= node.getLayoutY() + node.getBoundsInParent().getHeight() - 5)) {
                        borderTop = true;
                    }
                    if ((playerPane.getLayoutY() + playerHeight <= node.getLayoutY())
                            && (playerPane.getLayoutY() + playerHeight < node.getLayoutY() + node.getBoundsInParent().getHeight())) {
                        borderBottom = true;
                    }
                }
            }
        }
        if (playerPane.getLayoutX() < leftBorder) playerPane.setLayoutX(leftBorder);
        if (playerPane.getLayoutX() > rightBorder) playerPane.setLayoutX(rightBorder);
        if (playerPane.getLayoutY() < topBorder) playerPane.setLayoutY(topBorder);
        if (playerPane.getLayoutY() > bottomBorder) playerPane.setLayoutY(bottomBorder);


    }

    public ImageView dropBucket() {
        playerPane.getChildren().remove(bucketImage);
        return bucketImage;
    }

    public boolean hasBucket() {
        for (Node image : playerPane.getChildren()) {
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
        playerPane.setLayoutX(x);
        playerPane.setLayoutY(y);
        collisionImage.setVisible(false);
        collision = false;
    }
}
