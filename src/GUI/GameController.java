package oceanCleanup.src.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import oceanCleanup.src.domain.Game;

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player.makeMovable(image, scene);
    }

    @FXML
    public void onKeyPressed(KeyEvent event) {
        if ((event.getCode() == KeyCode.W)
                || (event.getCode() == KeyCode.A)
                || (event.getCode() == KeyCode.S)
                || (event.getCode() == KeyCode.D)) {
            player.onKeyPressedMovement(event.getCode());
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
