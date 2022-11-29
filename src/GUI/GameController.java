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
        items.add(bucket);
        items.add(plastic);

        for (Item item : game.currentRoom().getItems()) {
            File file = new File("src\\GUI\\images\\" + item.getName() + ".png");
            ImageView temp = new ImageView(new Image( "file:" + file.getAbsolutePath()));
            temp.setLayoutX(item.getX());
            temp.setLayoutY(item.getY());
            items.add(temp);
        }


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
    }

}
