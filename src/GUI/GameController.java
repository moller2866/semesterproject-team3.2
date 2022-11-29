package oceanCleanup.src.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import oceanCleanup.src.domain.Game;

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
    private ImageView wKey, aKey, sKey, dKey,
            hKey, iKey, tKey, qKey, eKey, spaceKey;

    private double keyOpacity = 0.6;

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

        if ((event.getCode() == KeyCode.W)) {
            wKey.setOpacity(keyOpacity);
        }

        if ((event.getCode() == KeyCode.A)) {
            aKey.setOpacity(keyOpacity);
        }

        if ((event.getCode() == KeyCode.S)) {
            sKey.setOpacity(keyOpacity);
        }

        if ((event.getCode() == KeyCode.D)) {
            dKey.setOpacity(keyOpacity);
        }

        if ((event.getCode() == KeyCode.H)) {
            hKey.setOpacity(keyOpacity);
        }

        if ((event.getCode() == KeyCode.I)) {
            iKey.setOpacity(keyOpacity);
        }

        if ((event.getCode() == KeyCode.T)) {
            tKey.setOpacity(keyOpacity);
        }

        if ((event.getCode() == KeyCode.Q)) {
            qKey.setOpacity(keyOpacity);
        }

        if ((event.getCode() == KeyCode.E)) {
            eKey.setOpacity(keyOpacity);
        }

        if ((event.getCode() == KeyCode.SPACE)) {
            spaceKey.setOpacity(keyOpacity);
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

        if ((event.getCode() == KeyCode.W)) {
            wKey.setOpacity(1);
        }

        if ((event.getCode() == KeyCode.A)) {
            aKey.setOpacity(1);
        }

        if ((event.getCode() == KeyCode.S)) {
            sKey.setOpacity(1);
        }

        if ((event.getCode() == KeyCode.D)) {
            dKey.setOpacity(1);
        }

        if ((event.getCode() == KeyCode.H)) {
            hKey.setOpacity(1);
        }

        if ((event.getCode() == KeyCode.I)) {
            iKey.setOpacity(1);
        }

        if ((event.getCode() == KeyCode.T)) {
            tKey.setOpacity(1);
        }

        if ((event.getCode() == KeyCode.Q)) {
            qKey.setOpacity(1);
        }

        if ((event.getCode() == KeyCode.E)) {
            eKey.setOpacity(1);
        }

        if ((event.getCode() == KeyCode.SPACE)) {
            spaceKey.setOpacity(1);
        }
    }

    public void addGame(Game game) {
        this.game = game;
        textBox.setText(game.getRoomDescription());
    }

}
