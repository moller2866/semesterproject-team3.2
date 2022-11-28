package oceanCleanup.src.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
