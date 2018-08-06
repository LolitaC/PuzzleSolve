package top.lolitac.puzzlesolve;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import top.lolitac.puzzlesolve.helper.GameHelper;


public class Game extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        String url = "puzzle/javafx-documentation.png";
        GridPane gridPane = new GridPane();
        Image image = new Image(url);
        ImageView imageView = new ImageView();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
