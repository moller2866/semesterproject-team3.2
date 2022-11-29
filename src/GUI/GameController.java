package oceanCleanup.src.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import oceanCleanup.src.domain.Game;
import oceanCleanup.src.domain.Item;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController extends Game implements Initializable {

    @FXML
    PlayerMove player = new PlayerMove();
    @FXML
    AnchorPane scene;
    @FXML
    ImageView image;

    @FXML
    ImageView bucket;

    @FXML
    ImageView plastic;

    ArrayList<ImageView> items = new ArrayList<>();

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player.makeMovable(image, scene);
        items.add(bucket);
        items.add(plastic);
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
            // add item that intersects with player
            for (ImageView item : items) {
                if (item.getBoundsInParent().intersects(image.getBoundsInParent())) {
                    String fileName = item.getImage().getUrl();
                    String itemName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
                    System.out.println(itemName);
                    getItem(itemName);
                    if (!itemName.equals("bucket")) {
                        item.setVisible(false);
                    } else {
                        player.addImage(bucket);
                    }
                    items.remove(item);
                    break;
                }
            }

            if (image.getBoundsInParent().intersects(bucket.getBoundsInParent())) {
                if (getItem("bucket")) player.addImage(bucket);
                System.out.println("intersects");
            }
        } else if (event.getCode() == KeyCode.X) {
            if (dropItem("bucket")) player.removeImage(bucket);
        } else if (event.getCode() == KeyCode.I) {
            System.out.println(seeInventory());
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
    }

}
