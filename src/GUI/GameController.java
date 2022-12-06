package oceanCleanup.src.GUI;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import oceanCleanup.src.domain.Bucket;
import oceanCleanup.src.domain.Game;
import oceanCleanup.src.domain.Item;
import oceanCleanup.src.domain.NPC;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    Game game;

    @FXML
    private PlayerMove playerMove = new PlayerMove();
    @FXML
    private AnchorPane scene;

    @FXML
    private ImageView background, bucket, ship, radar;
    @FXML
    private TextArea textBox;
    @FXML
    private TextArea popUpBox;
    @FXML
    private ImageView wKey, aKey, sKey, dKey, hKey, iKey, tKey, qKey, eKey, spaceKey;
    ArrayList<ImageView> items = new ArrayList<>();
    ArrayList<ImageView> nonInteractableItems = new ArrayList<>();
    private final double gameScale = 1.5;
    private ArrayList<ImageView> keyCaps;

    @FXML
    private Pane playerPane;
    private ArrayList<Pane> borders = new ArrayList<>();

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerMove.makeMovable(playerPane, scene);
        textBox.setEditable(false);
        textBox.setMouseTransparent(true);
        textBox.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        createShip();
        createRadar();
        keyCaps = new ArrayList<>() {{
            add(wKey);
            add(aKey);
            add(sKey);
            add(dKey);
            add(hKey);
            add(iKey);
            add(tKey);
            add(qKey);
            add(eKey);
            add(spaceKey);
        }};
    }

    private void createShip() {
        ship = new ImageView(new Image(getClass().getResource("graphics/oceancleanupship.png").toExternalForm()));
        ship.setLayoutX(294);
        ship.setLayoutY(250);
        ship.setFitHeight(261);
        ship.setFitWidth(261);
        imageMovement(ship);
    }

    private void createRadar() {
        radar = new ImageView(new Image(getClass().getResource("graphics/radar.gif").toExternalForm()));
        radar.setLayoutX(572.5);
        radar.setLayoutY(160);
        radar.setFitHeight(67);
        radar.setFitWidth(69);
    }

    private void imageMovement(Node node) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(node);
        translate.setDuration(Duration.seconds(1.7));
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setByY(-8);
        translate.setAutoReverse(true);
        translate.play();
    }

    private void pressedAction(String eventKey) {
        for (ImageView key : keyCaps) {
            if (key.getId().equals(eventKey)) {
                key.setOpacity(0.7);
                key.setScaleY(0.9);
                key.setScaleX(0.9);
            }
        }
    }

    private void nonPressed(String eventKey) {
        for (ImageView key : keyCaps) {
            if (key.getId().equals(eventKey)) {
                key.setOpacity(1);
                key.setScaleY(1);
                key.setScaleX(1);
            }
        }
    }

    @FXML
    public void onKeyPressed(KeyEvent event) {
        if (popUpBox.isVisible()) {
            if (event.getCode() == KeyCode.ENTER) {
                popUpBox.setVisible(false);
            } else {
                return;
            }
        }

        switch (event.getCode()) {
            case W, A, S, D -> playerMove.onKeyPressedMovement(event.getCode());
            case SPACE -> {
                goNextRoom();
                picupItem();
            }
            case Q -> dropBucket();
            case E -> emptyBucket();
            case H -> textBox.setText(game.getRoomDescriptionGUI());
            case I -> textBox.setText(game.seeInventory());
            case V -> {
                if (game.isRoomFull()) {
                    Media media = new Media(getClass().getResource("media/oceancleanup.mp4").toExternalForm());
                    MediaPlayer videoPlayer = new MediaPlayer(media);
                    MediaView video = new MediaView(videoPlayer);
                    video.setSmooth(true);
                    video.setVisible(true);
                    videoPlayer.setAutoPlay(true);
                    scene.getChildren().add(video);
                    Timeline tm = new Timeline(new KeyFrame(Duration.millis(3000), new KeyValue(video.opacityProperty(), 0)));
                    tm.setDelay(Duration.seconds(25));
                    tm.play();
                }
            }
            case T -> {
                if (game.currentRoomHasNPC()) {
                    popUpBox.setText(game.startTalk() + "\n                                     ... PRESS {ENTER} TO CONTINUE ...");
                    popUpBox.setVisible(true);
                    popUpBox.toFront();
                } else {
                    textBox.setText(game.startTalk());
                }
            }
        }
        pressedAction(event.getCode().getName());

    }

    private void goNextRoom() {
        if (playerMove.isColliding()) {
            String prevRoom = game.getCurrentRoom().getName();
            game.goRoomDirection(playerMove.getCollision());
            double x = game.getCurrentRoom().getColliders().get("to" + prevRoom.toLowerCase()).get(0);
            double y = game.getCurrentRoom().getColliders().get("to" + prevRoom.toLowerCase()).get(1);
            playerMove.setPlayerPosition(x, y);
            changeRoom();
        }
    }

    private void dropBucket() {
        if (playerMove.hasBucket()) {
            ImageView droppedBucket = playerMove.dropBucket();
            double x = playerPane.getLayoutX() + droppedBucket.getLayoutX();
            double y = playerPane.getLayoutY() + droppedBucket.getLayoutY();
            game.getPlayerBucket().setX(x);
            game.getPlayerBucket().setY(y);
            droppedBucket.setLayoutX(x);
            droppedBucket.setLayoutY(y);
            game.dropItem("bucket");
            scene.getChildren().add(droppedBucket);
            items.add(droppedBucket);
        }
    }

    @FXML
    public void onKeyReleased(KeyEvent event) {
        playerMove.onKeyReleasedMovement(event.getCode());
        playerMove.stopPlayerAnimation();
        nonPressed(event.getCode().getName());
    }


    private void emptyBucket() {
        if (playerMove.hasBucket()) {
            if (game.getCurrentRoom().getName().equals("container")) {
                if (game.emptyBucketInRoom()) {
                    textBox.setText("You emptied the bucket");
                    bucket.setImage(new Image(getClass().getResource("items/bucket.png").toExternalForm()));
                    addRoomContent();
                    if (game.isRoomFull()) {
                        popUpBox.setText("""




                                                                            >> You have completed your task <<

                                                                                      Thank you for helping us!

                                                                    We have prepared a small video for you to watch.
                                                       When you have closed this window, you can press {V} to watch it.
                                              
                                                                               ... PRESS {ENTER} TO CONTINUE ...""".indent(8));
                        popUpBox.setVisible(true);
                        popUpBox.toFront();
                    }
                } else {
                    textBox.setText("There is nothing to empty");
                }
            } else {
                textBox.setText("You can't empty the bucket here");
            }
        } else {
            textBox.setText("You don't have a bucket");
        }
    }

    private void picupItem() {
        for (ImageView item : items) {
            if (item.getBoundsInParent().intersects(playerPane.getBoundsInParent())) {
                String fileName = item.getImage().getUrl();
                String itemName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
                if (playerMove.hasBucket() || itemName.equals("bucket") || itemName.equals("bucket_filled")) {
                    if (game.getItem(itemName.split("_")[0], item.getLayoutX(), item.getLayoutY())) {
                        if (itemName.equals("bucket") || itemName.equals("bucket_filled")) {
                            item.setId("bucket");
                            playerMove.addBucket(item);
                        } else {
                            if (!game.getPlayerBucket().isEmpty()) {
                                bucket.setImage(new Image(getClass().getResource("items/bucket_filled.png").toExternalForm()));
                            }
                            item.setVisible(false);
                        }
                        items.remove(item);
                    }
                    break;
                }
            }
        }
    }

    public void addGame(Game game) {
        this.game = game;
        textBox.setText(game.getRoomDescriptionGUI());
        changeRoom();
        popUpBox.toFront();
    }

    private void changeRoom() {
        scene.getChildren().removeAll(items);
        scene.getChildren().removeAll(nonInteractableItems);
        items.clear();
        nonInteractableItems.clear();
        addRoomContent();
        changeSceneImage();
        addRoomBorders();
        createColliders();
        setScale();
    }

    private void addRoomContent() {
        for (Item item : this.game.getCurrentRoom().getItems()) {
            ImageView temp = new ImageView(new Image(getClass().getResource("items/" + item.getName().toLowerCase() + ".png").toExternalForm()));
            temp.setLayoutX(item.getX());
            temp.setLayoutY(item.getY());
            temp.setScaleX(gameScale);
            temp.setScaleY(gameScale);
            if (temp.getImage().getUrl().contains("bucket")) {
                Bucket b = (Bucket) item;
                if (b.getSize() == 0) {
                    temp.setImage(new Image(getClass().getResource("items/bucket.png").toExternalForm()));
                } else {
                    temp.setImage(new Image(getClass().getResource("items/bucket_filled.png").toExternalForm()));
                }
                this.bucket = temp;
            }
            scene.getChildren().add(temp);
            items.add(temp);
        }

        for (NPC npc : this.game.getCurrentRoom().getNPCs()) {
            ImageView temp = new ImageView(new Image(getClass().getResource("npcs/" + npc.getJob().toLowerCase() + ".png").toExternalForm()));
            temp.setLayoutX(npc.getX());
            temp.setLayoutY(npc.getY());
            scene.getChildren().add(temp);
            items.add(temp);
        }

        // adds non-interactive items to the scene
        switch (this.game.getCurrentRoom().getName()) {
            case "dock" -> {
                scene.getChildren().add(ship);
                nonInteractableItems.add(ship);
            }
            case "wheelhouse" -> {
                scene.getChildren().add(radar);
                nonInteractableItems.add(radar);
            }
        }
    }

    private void addRoomBorders() {
        scene.getChildren().removeAll(borders);
        borders.clear();
        for (ArrayList<Double> boundary : game.getCurrentRoom().getBorders()) {
            Pane temp = new Pane();
            temp.setPrefSize(boundary.get(2), boundary.get(3));
            temp.setLayoutX(boundary.get(0));
            temp.setLayoutY(boundary.get(1));
            temp.setId("border");
            //temp.setStyle("-fx-background-color: Blue;");
            //playerPane.setStyle("-fx-background-color: Red;");
            temp.setVisible(false);
            scene.getChildren().add(temp);
            borders.add(temp);
        }
    }

    private void changeSceneImage() {
        this.background.setImage(new Image(game.getCurrentRoom().getBackground()));
        textBox.setText(game.getRoomDescriptionGUI());
        this.background.setFitHeight(820);
        this.background.setFitWidth(1250);
    }

    private void createColliders() {
        playerMove.clearColliders();
        // for each key value pair
        for (Map.Entry<String, ArrayList<Double>> entry : game.getCurrentRoom().getColliders().entrySet()) {
            String key = entry.getKey();
            ArrayList<Double> value = entry.getValue();
            Pane temp = new Pane();
            temp.setPrefSize(value.get(2), value.get(3));
            temp.setLayoutX(value.get(0));
            temp.setLayoutY(value.get(1));
            temp.setId(key);
            //temp.setStyle("-fx-background-color: Green;");
            temp.setVisible(false);
            playerMove.addCollider(temp);
        }
    }

    //set Scale for all images
    public void setScale() {
        for (ImageView item : items) {
            item.setScaleX(gameScale);
            item.setScaleY(gameScale);
        }
    }
}
