package top.lolitac.puzzlesolve;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Random;


public class Game extends Application {

    /**
     * imgWidth 图片大小
     */
    int imgWidth;
    int imgHeight;

    /**
     * 图片切割成{@code rows}行 {@code columns}列
     */
    int rows;
    int columns;

    /**
     * 隐藏的图片的x跟y
     */
    int hiddenX;
    int hiddenY;
    @Override
    public void start(Stage primaryStage) throws Exception {
        String url = "puzzle/20180313171128.png";
        GridPane gridPane = new GridPane();

        Image image = new Image(url);
        int width = (int)image.getWidth();
        int height = (int)image.getHeight();
        rows = 4;
        columns = 4;

        imgWidth = width/columns;
        imgHeight = height/rows;
        ImageView[] imageView = new ImageView[rows * columns];

        // 生成随机数组
        int[] a = random(rows*columns);

        // 加载所有图片
        for(int i = 0, k = 0; i < rows; ++i) {
            for(int j = 0; j < columns; ++j, ++k) {
                imageView[k] = new ImageView(image);
                imageView[k].setOnMouseClicked(new PuzzleMoveOnMouse());
                imageView[k].setViewport(new Rectangle2D(imgWidth*j, imgHeight*i, imgWidth, imgHeight));
            }
        }

        // 图片随机分布，并隐藏最后一张
        for(int i = 0, k = 0; i < rows; ++i) {
            for(int j = 0; j < columns; ++j, ++k) {
                if(k == a.length-1){
                    break;
                }
                gridPane.add(imageView[a[k]],j,i);
            }
        }

        hiddenX = rows;
        hiddenY = columns;

        gridPane.setGridLinesVisible(true);
        Scene scene = new Scene(gridPane, width, height);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }



    public static void main(String[] args) {
        Game.launch(args);
    }


    private class PuzzleMoveOnMouse implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            ImageView img = (ImageView)event.getSource();
            System.out.println(img.getLayoutX()/imgWidth);
            System.out.println(img.getLayoutY()/imgHeight);
        }
    }

    /**
     * 随机函数
     *
     * @param size array size
     * @return {@code size}大小的随机数组
     */
    private int[] random(int size){
        int[] a = new int[size];
        for(int i=0;i<size;i++){
            a[i] = i;
        }
        int[] randomArray = new int[size];
        Random random = new Random();
        int index = 0;
        while(size > 0){
            int r = random.nextInt(size);
            randomArray[index++] = a[r];
            a[r] = a[--size];
        }

        return randomArray;
    }
}