package oceanCleanup.src.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import oceanCleanup.src.domain.Game;

public class GameApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("dock.fxml"));
        Parent root = fxml.load();
        root.setFocusTraversable(true);
        GameController controller = fxml.getController();
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(controller::onKeyPressed);
        scene.setOnKeyReleased(controller::onKeyReleased);
        controller.addGame(new Game());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("The Ocean Cleanup");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
