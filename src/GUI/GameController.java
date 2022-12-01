package oceanCleanup.src.GUI;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import oceanCleanup.src.domain.Bucket;
import oceanCleanup.src.domain.Game;
import oceanCleanup.src.domain.Item;

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
    private double gameScale = 1.5;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerMove.makeMovable(playerImage, scene);
        textBox.setEditable(false);
        textBox.setMouseTransparent(true);
        textBox.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        imageMovement(ship);
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
        key.setScaleY(1.4);
        key.setScaleX(1.4);
    }

    private void nonPressed(ImageView key) {
        key.setOpacity(1);
        key.setScaleY(1.5);
        key.setScaleX(1.5);
    }

    @FXML
    public void onKeyPressed(KeyEvent event) {
        if ((event.getCode() == KeyCode.W)
                || (event.getCode() == KeyCode.A)
                || (event.getCode() == KeyCode.S)
                || (event.getCode() == KeyCode.D)) {
            playerMove.onKeyPressedMovement(event.getCode());
        } else if (event.getCode() == KeyCode.SPACE) {
            System.out.println("Space pressed");
            picupItem();
        } else if (event.getCode() == KeyCode.Q) {
            dropItem();
        } else if (event.getCode() == KeyCode.I) {
            System.out.println(game.seeInventory());
        } else if (event.getCode() == KeyCode.E) {
            emptyBucket();
        }
        if ((event.getCode() == KeyCode.H)) {
            textBox.setText(game.getRoomDescriptionGUI());
            pressedAction(hKey);
        }

        if ((event.getCode() == KeyCode.I)) {
            textBox.setText(game.seeInventory());
            pressedAction(iKey);
        }

        if ((event.getCode() == KeyCode.T)) {
            textBox.setText(game.startTalk());
            pressedAction(tKey);
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
            if (game.getRoomDescriptionGUI().contains("dock")) {
                game.goRoomDirection("north");
                ship.setVisible(false);
            } else {
                game.goRoomDirection("south");
                ship.setVisible(true);
            }
            changeRoom();
        }
        if ((event.getCode() == KeyCode.K)) {
            System.out.println(game.getRoomDescriptionCLI());
        }
    }


    private void emptyBucket() {
        if (playerMove.hasBucket()) {
            if (game.getCurrentRoom().getShortDescription().contains("container")) {
                if (game.emptyBucketInRoom()) {
                    textBox.setText("You emptied the bucket");
                    bucket.setImage(new Image(getClass().getResource("items/bucket.png").toExternalForm()));
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
                System.out.println(itemName);
                if (playerMove.hasBucket() || itemName.equals("bucket") || itemName.equals("bucket_filled")) {
                    if (game.getItem(itemName.split("_")[0],item.getLayoutX(), item.getLayoutY())) {
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
        items.clear();

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
        changeSceneImage();
        setScale();
    }

    private void changeSceneImage() {
        if (game.getCurrentRoom().getLongDescriptionGUI().contains("dock")) {
            this.background.setImage(new Image(getClass().getResource("graphics/dock.png").toExternalForm()));
            textBox.setText(game.getRoomDescriptionGUI());
        } else if (game.getCurrentRoom().getLongDescriptionGUI().contains("ship")) {
            this.background.setImage(new Image(getClass().getResource("graphics/W.png").toExternalForm()));
            textBox.setText(game.getRoomDescriptionGUI());
        }
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
