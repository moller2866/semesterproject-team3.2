package oceanCleanup.src.GUI;

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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    private PlayerMove playerMove = new PlayerMove();
    @FXML
    private AnchorPane scene;

    @FXML
    private Pane dockToShip, dockToRecyclingCenter,
            recyclingCenterToContainer, recyclingCenterToDock,
            containerToRecyclingCenter,
            shipToWheelhouse, shipToDock,
            wheelhouseToShip;
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

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerMove.makeMovable(playerPane, scene);
        textBox.setEditable(false);
        textBox.setMouseTransparent(true);
        textBox.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        createShip();
        createRadar();
        createColliders();
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

    private void createColliders() {
        dockToShip = new Pane();
        dockToShip.setPrefSize(34, 162);
        dockToShip.setLayoutX(538);
        dockToShip.setLayoutY(312);
        dockToShip.setVisible(false);
        dockToShip.setId("dockToShip");

        dockToRecyclingCenter = new Pane();
        dockToRecyclingCenter.setPrefSize(34, 162);
        dockToRecyclingCenter.setLayoutX(1165);
        dockToRecyclingCenter.setLayoutY(319);
        dockToRecyclingCenter.setVisible(false);
        dockToRecyclingCenter.setId("dockToRecyclingCenter");

        recyclingCenterToContainer = new Pane();
        recyclingCenterToContainer.setPrefSize(80, 16);
        recyclingCenterToContainer.setLayoutX(984);
        recyclingCenterToContainer.setLayoutY(565);
        recyclingCenterToContainer.setVisible(false);
        recyclingCenterToContainer.setId("recyclingCenterToContainer");

        recyclingCenterToDock = new Pane();
        recyclingCenterToDock.setPrefSize(34, 162);
        recyclingCenterToDock.setLayoutX(104);
        recyclingCenterToDock.setLayoutY(319);
        recyclingCenterToDock.setVisible(false);
        recyclingCenterToDock.setId("recyclingCenterToDock");

        containerToRecyclingCenter = new Pane();
        containerToRecyclingCenter.setPrefSize(34, 162);
        containerToRecyclingCenter.setLayoutX(264);
        containerToRecyclingCenter.setLayoutY(312);
        containerToRecyclingCenter.setVisible(false);
        containerToRecyclingCenter.setId("containerToRecyclingCenter");

        shipToWheelhouse = new Pane();
        shipToWheelhouse.setPrefSize(34, 162);
        shipToWheelhouse.setLayoutX(1);
        shipToWheelhouse.setLayoutY(353);
        shipToWheelhouse.setVisible(false);
        shipToWheelhouse.setId("shipToWheelhouse");

        shipToDock = new Pane();
        shipToDock.setPrefSize(169, 27);
        shipToDock.setLayoutX(473);
        shipToDock.setLayoutY(759);
        shipToDock.setVisible(false);
        shipToDock.setId("shipToDock");

        wheelhouseToShip = new Pane();
        wheelhouseToShip.setPrefSize(169, 27);
        wheelhouseToShip.setLayoutX(515);
        wheelhouseToShip.setLayoutY(552);
        wheelhouseToShip.setVisible(false);
        wheelhouseToShip.setId("wheelhouseToShip");
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
            switch (playerMove.getCollision()) {
                case "dockToShip" -> {
                    game.goRoomDirection("north");
                    double x = shipToDock.getLayoutX();
                    double y = shipToDock.getLayoutY();
                    playerMove.setPlayerPosition(x + 60, y - 60);
                }
                case "dockToRecyclingCenter" -> {
                    game.goRoomDirection("east");
                    double x = recyclingCenterToDock.getLayoutX();
                    double y = recyclingCenterToDock.getLayoutY();
                    playerMove.setPlayerPosition(x + 60, y + 60);
                }
                case "recyclingCenterToDock" -> {
                    game.goRoomDirection("west");
                    double x = dockToRecyclingCenter.getLayoutX();
                    double y = dockToRecyclingCenter.getLayoutY();
                    playerMove.setPlayerPosition(x - 60, y + 60);
                }
                case "recyclingCenterToContainer" -> {
                    game.goRoomDirection("east");
                    double x = containerToRecyclingCenter.getLayoutX();
                    double y = containerToRecyclingCenter.getLayoutY();
                    playerMove.setPlayerPosition(x + 60, y + 60);
                }
                case "containerToRecyclingCenter" -> {
                    game.goRoomDirection("west");
                    double x = recyclingCenterToContainer.getLayoutX();
                    double y = recyclingCenterToContainer.getLayoutY();
                    playerMove.setPlayerPosition(x + 60, y + 50);
                }
                case "shipToWheelhouse" -> {
                    game.goRoomDirection("west");
                    double x = wheelhouseToShip.getLayoutX();
                    double y = wheelhouseToShip.getLayoutY();
                    playerMove.setPlayerPosition(x + 60, y - 60);
                }
                case "wheelhouseToShip" -> {
                    game.goRoomDirection("east");
                    double x = shipToWheelhouse.getLayoutX();
                    double y = shipToWheelhouse.getLayoutY();
                    playerMove.setPlayerPosition(x + 60, y + 60);
                }
                case "shipToDock" -> {
                    game.goRoomDirection("south");
                    double x = dockToShip.getLayoutX();
                    double y = dockToShip.getLayoutY();
                    playerMove.setPlayerPosition(x + 60, y + 60);
                }
            }
            changeRoom();
        }
    }

    private void dropBucket() {
        if (playerMove.hasBucket()) {
            ImageView droppedBucket = playerMove.dropBucket();
            double x = playerPane.getLayoutX() + droppedBucket.getTranslateX();
            double y = playerPane.getLayoutY() + droppedBucket.getTranslateY();
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
        switch (event.getCode()) {
            case W, D, A, S -> playerMove.onKeyReleasedMovement(event.getCode());
        }
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

                                The game is now over, but you can still walk around and explore!

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

    private void changeSceneImage() {
        playerMove.clearColliders();
        switch (game.getCurrentRoom().getName()) {
            case "dock" -> {
                this.background.setImage(new Image(getClass().getResource("graphics/dock.png").toExternalForm()));
                playerMove.addCollider(dockToShip);
                playerMove.addCollider(dockToRecyclingCenter);
            }
            case "ship" -> {
                this.background.setImage(new Image(getClass().getResource("graphics/ship.png").toExternalForm()));
                playerMove.addCollider(shipToDock);
                playerMove.addCollider(shipToWheelhouse);
            }
            case "wheelhouse" -> {
                this.background.setImage(new Image(getClass().getResource("graphics/wheelhouse.png").toExternalForm()));
                playerMove.addCollider(wheelhouseToShip);
            }
            case "ocean" ->
                    this.background.setImage(new Image(getClass().getResource("graphics/ocean.png").toExternalForm()));
            case "recyclingcenter" -> {
                this.background.setImage(new Image(getClass().getResource("graphics/recyclingcenter.png").toExternalForm()));
                playerMove.addCollider(recyclingCenterToDock);
                playerMove.addCollider(recyclingCenterToContainer);
            }
            case "container" -> {
                this.background.setImage(new Image(getClass().getResource("graphics/container.png").toExternalForm()));
                playerMove.addCollider(containerToRecyclingCenter);
            }
        }
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
