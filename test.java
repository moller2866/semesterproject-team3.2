package oceanCleanup;

import javafx.scene.image.ImageView;

public class test {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    private static void DEBUGGINGMoveItemsAround(ImageView temp) {
        temp.setOnMouseDragged(event -> {
            temp.setLayoutX(event.getSceneX());
            temp.setLayoutY(event.getSceneY());
        });
        temp.setOnMouseReleased(event -> {
            String fileName = temp.getImage().getUrl();
            String itemName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
            String outString = "    {\n" +
                    "      \"type\": \""+itemName+"\",\n"
                    + "      \"x\":" + temp.getLayoutX() + ",\n"
                    + "      \"y\":" + temp.getLayoutY() + "\n"
                    + "    },\n";
            System.out.println(outString);
        });
    }

}
