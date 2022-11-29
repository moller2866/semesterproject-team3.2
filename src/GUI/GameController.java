package oceanCleanup.src.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import oceanCleanup.src.domain.Game;

import java.net.URL;
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

    final private double keyOpacityPressed = 0.7;
    final private double keySizePressed = 0.9;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player.makeMovable(image, scene);
        textBox.setEditable(false);
        textBox.setMouseTransparent(true);
        textBox.setFont(Font.font("Verdana", FontWeight.BOLD, 9));
    }

    private void pressedAction(ImageView key) {
        key.setOpacity(keyOpacityPressed);
        key.setScaleY(keySizePressed);
        key.setScaleX(keySizePressed);
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
            player.onKeyPressedMovement(event.getCode());
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
            wKey.setOpacity(keyOpacityPressed);
            pressedAction(wKey);
        }

        if ((event.getCode() == KeyCode.A)) {
            aKey.setOpacity(keyOpacityPressed);
            pressedAction(aKey);
        }

        if ((event.getCode() == KeyCode.S)) {
            sKey.setOpacity(keyOpacityPressed);
            pressedAction(sKey);
        }

        if ((event.getCode() == KeyCode.D)) {
            dKey.setOpacity(keyOpacityPressed);
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


    @FXML
    public void onKeyReleased(KeyEvent event) {
        if ((event.getCode() == KeyCode.W)
                || (event.getCode() == KeyCode.A)
                || (event.getCode() == KeyCode.S)
                || (event.getCode() == KeyCode.D)) {
            player.onKeyReleasedMovement(event.getCode());
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
    }

    public void addGame(Game game) {
        this.game = game;
        textBox.setText(game.getRoomDescriptionGUI());
    }

}
