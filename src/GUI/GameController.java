package oceanCleanup.src.GUI;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import oceanCleanup.src.domain.Bucket;
import oceanCleanup.src.domain.Game;
import oceanCleanup.src.domain.Item;
import oceanCleanup.src.domain.NPC;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    Game game;

    @FXML
    private ImageView background;
    @FXML
    private PlayerMove playerMove = new PlayerMove();
    @FXML
    private AnchorPane scene;
    @FXML
    private ImageView playerImage;
    @FXML
    private ImageView bucket;
    @FXML
    private ImageView dialogbox;
    @FXML
    private TextArea textBox;
    @FXML
    private ImageView wKey, aKey, sKey, dKey,
            hKey, iKey, tKey, qKey, eKey, spaceKey;

    @FXML
    private ImageView ship;

    ArrayList<ImageView> items = new ArrayList<>();
    ArrayList<ImageView> nonInteractableItems = new ArrayList<>();
    private double gameScale = 1.5;
    private int roomcounter = 0;

    @FXML
    private Text timerLabel;
    DoubleProperty time = new SimpleDoubleProperty();

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerMove.makeMovable(playerImage, scene);
        textBox.setEditable(false);
        textBox.setMouseTransparent(true);
        textBox.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        imageMovement(ship);
        timerLabel.textProperty().bind(time.asString("%.1f s"));
        timerLabel.setVisible(false);
        setTextBoxNormal();
    }

    private void setTextBoxNormal() {
        textBox.setPrefSize(449,135);
        textBox.setLayoutX(18);
        textBox.setLayoutY(655);
    }

    private void setTextboxTalk() {
        textBox.setPrefSize(449, 225);
        textBox.setLayoutX(18);
        textBox.setLayoutY(565);
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

    private void pressedAction(ImageView key) {
        key.setOpacity(0.7);
        key.setScaleY(0.9);
        key.setScaleX(0.9);
    }

    private void nonPressed(ImageView key) {
        key.setOpacity(1);
        key.setScaleY(1);
        key.setScaleX(1);
    }

    @FXML
    public void onKeyPressed(KeyEvent event) {
        if ((event.getCode() == KeyCode.W)
                || (event.getCode() == KeyCode.A)
                || (event.getCode() == KeyCode.S)
                || (event.getCode() == KeyCode.D)) {
            playerMove.onKeyPressedMovement(event.getCode());
        } else if (event.getCode() == KeyCode.SPACE) {
            picupItem();
        } else if (event.getCode() == KeyCode.Q) {
            dropItem();
        } else if (event.getCode() == KeyCode.E) {
            emptyBucket();
        }
        if ((event.getCode() == KeyCode.H)) {
            setTextBoxNormal();
            textBox.setText(game.getRoomDescriptionGUI());
            pressedAction(hKey);
        }

        if ((event.getCode() == KeyCode.I)) {
            setTextBoxNormal();
            textBox.setText(game.seeInventory());
            pressedAction(iKey);
        }

        if ((event.getCode() == KeyCode.T)) {
            if (game.currentRoomHasNPC()) {
                setTextboxTalk();
                textBox.setText(game.startTalk());
                pressedAction(tKey);
            } else {
                setTextBoxNormal();
                textBox.setText(game.startTalk());
                pressedAction(tKey);
            }

        }

        if ((event.getCode() == KeyCode.W)) {
            pressedAction(wKey);
        }

        if ((event.getCode() == KeyCode.A)) {
            pressedAction(aKey);
        }

        if ((event.getCode() == KeyCode.S)) {
            pressedAction(sKey);
        }

        if ((event.getCode() == KeyCode.D)) {
            pressedAction(dKey);
        }

        if ((event.getCode() == KeyCode.Q)) {
            pressedAction(qKey);
        }

        if ((event.getCode() == KeyCode.E)) {
            pressedAction(eKey);
        }

        if ((event.getCode() == KeyCode.SPACE)) {
            pressedAction(spaceKey);
        }
    }

    private void dropItem() {
        if (playerMove.hasBucket()) {
            ArrayList<ImageView> droppedItems = playerMove.dropItems();
            game.getPlayerBucket().setX(droppedItems.get(0).getLayoutX());
            game.getPlayerBucket().setY(droppedItems.get(0).getLayoutY());
            game.dropItem("bucket");
            items.addAll(droppedItems);
        }
    }


    @FXML
    public void onKeyReleased(KeyEvent event) {
        if ((event.getCode() == KeyCode.W)
                || (event.getCode() == KeyCode.A)
                || (event.getCode() == KeyCode.S)
                || (event.getCode() == KeyCode.D)) {
            playerMove.onKeyReleasedMovement(event.getCode());
        }

        if ((event.getCode() == KeyCode.W)) {
            nonPressed(wKey);
        }

        if ((event.getCode() == KeyCode.A)) {
            nonPressed(aKey);
        }

        if ((event.getCode() == KeyCode.S)) {
            nonPressed(sKey);
        }

        if ((event.getCode() == KeyCode.D)) {
            nonPressed(dKey);
        }

        if ((event.getCode() == KeyCode.H)) {
            nonPressed(hKey);
        }

        if ((event.getCode() == KeyCode.I)) {
            nonPressed(iKey);
        }

        if ((event.getCode() == KeyCode.T)) {
            startMiniGame();
            nonPressed(tKey);
        }

        if ((event.getCode() == KeyCode.Q)) {
            nonPressed(qKey);
        }

        if ((event.getCode() == KeyCode.E)) {
            nonPressed(eKey);
        }

        if ((event.getCode() == KeyCode.SPACE)) {
            nonPressed(spaceKey);
        }

        if ((event.getCode() == KeyCode.L)) {

            switch (roomcounter) {
                case 0:
                    game.goRoomDirection("north");
                    roomcounter++;
                    break;
                case 1:
                    game.goRoomDirection("west");
                    roomcounter++;
                    break;
                case 2:
                    game.goRoomDirection("north");
                    roomcounter++;
                    break;
                case 3:
                    game.goRoomDirection("south");
                    game.goRoomDirection("east");
                    game.goRoomDirection("south");
                    game.goRoomDirection("east");
                    roomcounter++;
                    break;
                case 4:
                    game.goRoomDirection("east");
                    roomcounter++;
                    break;
                case 5:
                    game.goRoomDirection("west");
                    game.goRoomDirection("west");
                    roomcounter = 0;
                    break;
            }
            changeRoom();
        }
    }

    BooleanProperty running = new SimpleBooleanProperty();

    AnimationTimer timer = new AnimationTimer() {

        private long startTime;

        @Override
        public void start() {
            startTime = System.currentTimeMillis();
            running.set(true);
            super.start();
        }

        @Override
        public void stop() {
            running.set(false);
            super.stop();
        }

        @Override
        public void handle(long timestamp) {
            long now = System.currentTimeMillis();
            time.set((now - startTime) / 1000.0);
        }
    };

    private void startMiniGame() {
        if (game.startTalk().contains("Jack")) {
            timerLabel.setVisible(true);
            timer.start();
        }
    }


    private void emptyBucket() {
        setTextBoxNormal();
        if (playerMove.hasBucket()) {
            if (game.getCurrentRoom().getName().equals("container")) {
                if (game.emptyBucketInRoom()) {
                    textBox.setText("You emptied the bucket");
                    bucket.setImage(new Image(getClass().getResource("items/bucket.png").toExternalForm()));
                    addRoomContent();
                    if (game.isRoomFull()) timer.stop();
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
            if (item.getBoundsInParent().intersects(playerImage.getBoundsInParent())) {
                String fileName = item.getImage().getUrl();
                String itemName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
                if (playerMove.hasBucket() || itemName.equals("bucket") || itemName.equals("bucket_filled")) {
                    if (game.getItem(itemName.split("_")[0], item.getLayoutX(), item.getLayoutY())) {
                        if (itemName.equals("bucket") || itemName.equals("bucket_filled")) {
                            playerMove.addImage(item);
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
    }

    private void changeRoom() {
        scene.getChildren().removeAll(items);
        scene.getChildren().removeAll(nonInteractableItems);
        items.clear();
        nonInteractableItems.clear();
        addRoomContent();
        changeSceneImage();
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
            case "dock":
                ImageView temp = new ImageView(new Image(getClass().getResource("graphics/oceancleanupship.png").toExternalForm()));
                temp.setLayoutX(294);
                temp.setLayoutY(250);
                temp.setFitHeight(261);
                temp.setFitWidth(261);
                imageMovement(temp);
                scene.getChildren().add(temp);
                nonInteractableItems.add(temp);
                break;
            case "wheelhouse":
                temp = new ImageView(new Image(getClass().getResource("graphics/radar.gif").toExternalForm()));
                temp.setLayoutX(572.5);
                temp.setLayoutY(160);
                temp.setFitHeight(67);
                temp.setFitWidth(69);
                scene.getChildren().add(temp);
                nonInteractableItems.add(temp);
                break;
        }
    }

    private void changeSceneImage() {
        if (game.getCurrentRoom().getName().equals("dock")) {
            this.background.setImage(new Image(getClass().getResource("graphics/dock.png").toExternalForm()));
        } else if (game.getCurrentRoom().getName().equals("ship")) {
            this.background.setImage(new Image(getClass().getResource("graphics/ship.png").toExternalForm()));
        } else if (game.getCurrentRoom().getName().equals("wheelhouse")) {
            this.background.setImage(new Image(getClass().getResource("graphics/wheelhouse.png").toExternalForm()));
        } else if (game.getCurrentRoom().getName().equals("ocean")) {
            this.background.setImage(new Image(getClass().getResource("graphics/ocean.png").toExternalForm()));
        } else if (game.getCurrentRoom().getName().equals("recyclingcenter")) {
            this.background.setImage(new Image(getClass().getResource("graphics/recyclingcenter.png").toExternalForm()));
        } else if (game.getCurrentRoom().getName().equals("container")) {
            this.background.setImage(new Image(getClass().getResource("graphics/container.png").toExternalForm()));
        }
        setTextBoxNormal();
        textBox.setText(game.getRoomDescriptionGUI());
        this.background.setFitHeight(820);
        this.background.setFitWidth(1250);
    }

    //set Scale for all images
    public void setScale() {
        for (ImageView item : items) {
            item.setScaleX(gameScale);
            item.setScaleY(gameScale);
        }
    }
}
