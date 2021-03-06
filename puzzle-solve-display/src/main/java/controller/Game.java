package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game extends Application {

    public static void main(String[] args) {
        launch(Game.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create Image and ImageView objects
        Image image = new Image("puzzle/javafx-documentation.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);

        // Obtain PixelReader
        PixelReader pixelReader = image.getPixelReader();
        System.out.println("Image Width: "+image.getWidth());
        System.out.println("Image Height: "+image.getHeight());
        System.out.println("Pixel Format: "+pixelReader.getPixelFormat());

        WritableImage wImage = new WritableImage((int)image.getWidth(), (int)image.getHeight());
        PixelWriter pixelWriter = wImage.getPixelWriter();

        // Determine the color of each pixel in the image
        for (int readY = 0; readY < image.getHeight(); readY++) {
            for (int readX = 0; readX < image.getWidth(); readX++) {
                Color color = pixelReader.getColor(readX, readY);
                System.out.println("\nPixel color at coordinates ("
                        + readX + "," + readY + ") "
                        + color.toString());
                System.out.println("R = " + color.getRed());
                System.out.println("G = " + color.getGreen());
                System.out.println("B = " + color.getBlue());
                System.out.println("Opacity = " + color.getOpacity());
                System.out.println("Saturation = " + color.getSaturation());

                // Write color to the wImage
                color = color.brighter();
                pixelWriter.setColor(readX,readY,color);
            }
        }



        // Display image on screen
        imageView.setImage(wImage);
        StackPane root = new StackPane();
        root.getChildren().add(imageView);
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Image Read Test");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
