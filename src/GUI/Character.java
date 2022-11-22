package oceanCleanup.src.GUI;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Character extends Pane {

    ImageView imageView;
    int width = 30;
    int height = 50;

    public Character(ImageView imageView) {
        this.imageView = imageView;
    }

    void moveX(int x) {
        boolean right = x > 0 ? true : false;
        for (int i = 0; i < Math.abs(x); i++) {
            if (right) {
                this.setTranslateX(this.getTranslateX() + 1);
            } else {
                this.setTranslateX(this.getTranslateX() - 1);
            }
        }
    }

    void moveY(int y) {
        boolean right = y > 0 ? true : false;
        for (int i = 0; i < Math.abs(y); i++) {
            if (right) {
                this.setTranslateY(this.getTranslateY() + 1);
            } else {
                this.setTranslateY(this.getTranslateY() - 1);
            }
        }
    }
}
