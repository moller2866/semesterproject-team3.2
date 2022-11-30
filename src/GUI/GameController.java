package oceanCleanup.src.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import oceanCleanup.src.domain.Game;
import oceanCleanup.src.domain.Item;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    Game game;

    @FXML
    private PlayerMove player = new PlayerMove();
    @FXML
    private AnchorPane scene;
    @FXML
    private ImageView image;
    @FXML
    private ImageView bucket;
    @FXML
    private ImageView dialogbox;
    @FXML
    private TextArea textBox;

    @FXML
    ImageView plastic;

    ArrayList<ImageView> items = new ArrayList<>();

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player.makeMovable(image, scene);
        textBox.setEditable(false);
        textBox.setMouseTransparent(true);
        textBox.setFont(Font.font("Verdana", FontWeight.BOLD, 9));
    }


    @FXML
    public void onKeyPressed(KeyEvent event) {
        if ((event.getCode() == KeyCode.W)
                || (event.getCode() == KeyCode.A)
                || (event.getCode() == KeyCode.S)
                || (event.getCode() == KeyCode.D)) {
            player.onKeyPressedMovement(event.getCode());
        } else if (event.getCode() == KeyCode.SPACE) {
            System.out.println("Space pressed");
            picupItem();
        } else if (event.getCode() == KeyCode.X) {
            if (game.dropItem("bucket")) items.addAll(player.dropItems());
        } else if (event.getCode() == KeyCode.I) {
            System.out.println(game.seeInventory());
        } else if (event.getCode() == KeyCode.E) {
            emptyBucket();
        }
    }

    private void emptyBucket() {
        if (player.hasBucket()) {
            if (game.getCurrentRoom().getShortDescription().contains("container")) {
                if (game.emptyBucketInRoom()) {
                    textBox.setText("You emptied the bucket");
                    File file = new File(getClass().getResource("items/bucket.png").getPath());
                    bucket.setImage(new Image("file:" + file.getAbsolutePath()));
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
            if (item.getBoundsInParent().intersects(image.getBoundsInParent())) {
                String fileName = item.getImage().getUrl();
                String itemName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
                System.out.println(itemName);
                if (player.hasBucket() || itemName.equals("bucket") || itemName.equals("bucket_filled")) {
                    game.getItem(itemName.split("_")[0]);
                    if (itemName.equals("bucket") || itemName.equals("bucket_filled")) {
                        player.addImage(item);
                    } else {
                        if (!game.getPlayerBucket().isEmpty()) {
                            File file = new File(getClass().getResource("items/bucket_filled.png").getPath());
                            bucket.setImage(new Image("file:" + file.getAbsolutePath()));
                        }
                        item.setVisible(false);
                    }
                    items.remove(item);
                    break;
                }
            }
        }
    }

    @FXML
    public void onKeyReleased(KeyEvent event) {
        if ((event.getCode() == KeyCode.W)
                || (event.getCode() == KeyCode.A)
                || (event.getCode() == KeyCode.S)
                || (event.getCode() == KeyCode.D)) {
            player.onKeyReleasedMovement(event.getCode());
        }

        if ((event.getCode() == KeyCode.H)) {
            textBox.setText(game.getRoomDescription());
        }

        if ((event.getCode() == KeyCode.I)) {
            textBox.setText(game.seeInventory());
        }

        if ((event.getCode() == KeyCode.T)) {
            textBox.setText(game.startTalk());
        }

    }

    public void addGame(Game game) {
        this.game = game;
        textBox.setText(game.getRoomDescription());

        changeRoom();
    }

    private void changeRoom() {
        for (Item item : this.game.getCurrentRoom().getItems()) {
            File file = new File(getClass().getResource("items/" + item.getName().toLowerCase() + ".png").getPath());
            ImageView temp = new ImageView(new Image("file:" + file.getAbsolutePath()));
            temp.setLayoutX(item.getX());
            temp.setLayoutY(item.getY());
            if (temp.getImage().getUrl().contains("bucket")) {
                this.bucket = temp;
            }
            scene.getChildren().add(temp);
            items.add(temp);
        }
    }

}
