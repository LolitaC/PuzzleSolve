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
    private int imgWidth;
    private int imgHeight;

    /**
     * 图片切割成{@code rows}行 {@code columns}列
     */
    private int rows;
    private int columns;

    /**
     * 隐藏的图片的x跟y
     */
    private int hiddenX;
    private int hiddenY;

    /**
     * ImageView数组中隐藏图片的下标
     */
    private int hiddexIndex;

    /**
     * 隐藏图片的url
     */
    private String hiddenImageUrl = "Transparent.png";

    /**
     * 存储隐藏图片
     */
    ImageView hiddexImageView;

    /**
     * imageView[] 存储图片的数组
     */
    private ImageView[] imageView;


    /**
     * startTime 游戏开始时间
     */
    private long startTime;

    /**
     * endTime 游戏结束时间
     */
    private long endTime;

    private boolean isGameOver;

    @Override
    public void start(Stage primaryStage) throws Exception {
        String url = "puzzle/20180313171128.png";
        GridPane gridPane = new GridPane();

        Image image = new Image(url);
        int width = (int)image.getWidth();
        int height = (int)image.getHeight();
        rows = 3;
        columns = 3;

        imgWidth = width/columns;
        imgHeight = height/rows;
        imageView = new ImageView[rows * columns];

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

        hiddexImageView = new ImageView(hiddenImageUrl);
        hiddexImageView.setOnMouseClicked(new PuzzleMoveOnMouse());
        hiddexImageView.setViewport(new Rectangle2D(0,0,imgWidth,imgHeight));

        // 图片随机分布，并隐藏最后一张
        for(int i = 0, k = 0; i < rows; ++i) {
            for(int j = 0; j < columns; ++j, ++k) {
                if(k == a.length-1){
                    gridPane.add(hiddexImageView,j,i);
                    break;
                }
                gridPane.add(imageView[a[k]],j,i);
            }
        }

        hiddenX = rows;
        hiddenY = columns;
        hiddexIndex = a[rows*columns-1];

        gridPane.setGridLinesVisible(true);
        Scene scene = new Scene(gridPane, width, height);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        startTime = System.currentTimeMillis();
        isGameOver = false;
    }

    public static void main(String[] args) {
        Game.launch(args);
    }


    private class PuzzleMoveOnMouse implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {

            if(isGameOver){
                System.out.println("游戏已经结束.");
                return;
            }

            ImageView img = (ImageView)event.getSource();
            int clickX = GridPane.getColumnIndex(img) + 1;
            int clickY = GridPane.getRowIndex(img) + 1;
            if(hiddenImageCanMove(clickX,clickY)){
                if(swapImageView(img, hiddexImageView)){
                    hiddenX = clickX;
                    hiddenY = clickY;

                    if(checkGameOver()){
                        isGameOver = true;
                        endTime = System.currentTimeMillis();
                        System.out.println("游戏结束,恭喜完成拼图.");
                        System.out.println("耗时:"+ (endTime-startTime) + "ms");
                    }
                }
            }

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

    /**
     * 判断隐藏图片是否可以移动
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return {@code true} or {@code false}
     */
    private boolean hiddenImageCanMove(int x,int y){

        if( x == hiddenX || y == hiddenY){
            if( x + y == hiddenX + hiddenY -1 || x + y == hiddenX + hiddenY +1){
                return true;
            }
        }
        return false;
    }

    /**
     * 交换两个ImageView的位置
     *
     * @param imageView1 [ImageView]
     * @param imageView2 [ImageView]
     * @return 0:success 1:failure
     */
    private boolean swapImageView(ImageView imageView1,ImageView imageView2){
        Integer row1 = GridPane.getRowIndex(imageView1);
        Integer colu1 = GridPane.getColumnIndex(imageView1);
        Integer row2 = GridPane.getRowIndex(imageView2);
        Integer colu2 = GridPane.getColumnIndex(imageView2);

        GridPane.setRowIndex(imageView1, row2);
        GridPane.setColumnIndex(imageView1, colu2);
        GridPane.setRowIndex(imageView2, row1);
        GridPane.setColumnIndex(imageView2, colu1);
        return true;
    }


    /**
     * 测试游戏是否结束
     *
     * @return {@code true} or {@code false}
     */
    private boolean checkGameOver(){

        for(int i=0,k=0;i<rows;i++){
            for(int j=0;j<columns;j++,k++){
                Integer rowIndex = GridPane.getRowIndex(imageView[k]);
                Integer columnIndex = GridPane.getColumnIndex(imageView[k]);
                if( rowIndex!=null && columnIndex!=null && (rowIndex!=i || columnIndex!=j)){
                    return false;
                }
            }
        }
        return true;
    }

}