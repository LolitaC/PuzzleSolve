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

    /**
     * 拼图图片索引
     */
    int[] index;

    @Override
    public void start(Stage primaryStage) throws Exception {
        String url = "puzzle/20180313171128.png";
        GridPane gridPane = new GridPane();

        Image image = new Image(url);
        int width = (int)image.getWidth();
        int height = (int)image.getHeight();
        rows = 2;
        columns = 3;

        imgWidth = width/columns;
        imgHeight = height/rows;
        imageView = new ImageView[rows * columns];

        // 加载所有图片
        for(int i = 0, k = 0; i < rows; ++i) {
            for(int j = 0; j < columns; ++j, ++k) {
                imageView[k] = new ImageView(image);
                imageView[k].setOnMouseClicked(new PuzzleMoveOnMouse());
                imageView[k].setViewport(new Rectangle2D(imgWidth*j, imgHeight*i, imgWidth, imgHeight));
            }
        }

        // 初始化索引数组
        index = new int[rows * columns];
        for(int i=0;i<rows*columns;i++){
            index[i] = i;
        }

        // 随机生成一个隐藏图片索引并将其替代原图片
        int hiddexIndex = getRandomNum(rows*columns);
        hiddexImageView = new ImageView(hiddenImageUrl);
        hiddexImageView.setOnMouseClicked(new PuzzleMoveOnMouse());
        hiddexImageView.setViewport(new Rectangle2D(0,0,imgWidth,imgHeight));
        imageView[hiddexIndex] = hiddexImageView;
        hiddenX = hiddexIndex%columns + 1;
        hiddenY = hiddexIndex/rows + 1;

        System.err.println("图片排序中,请稍等......");
        do{
            // 隐藏图片自动移动,打乱图片顺序
            hiddexImageAutoMove(rows*columns*10);
        }while (checkGameOver());
        System.err.println("排序完成");


        // gridpane 绑定 imageView
        for(int i = 0, k = 0; i < rows; ++i) {
            for(int j = 0; j < columns; ++j, ++k) {
                gridPane.add(imageView[index[k]],j,i);
            }
        }




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
     * 获得随机数
     *
     * @param max 随机数不大于max
     * @return int
     */
    private int getRandomNum(int max){
        Random random = new Random();
        return random.nextInt(max);
    }

    /**
     * 判断隐藏图片是否可以移动
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return {@code true} or {@code false}
     */
    private boolean hiddenImageCanMove(int x,int y){

        if( x<=0 || x>columns || y<=0 || y>rows){
            return false;
        }

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

        int img = index[row1*columns+colu1];
        index[row1*columns+colu1] = index[(hiddenY-1)*columns+hiddenX-1];
        index[(hiddenY-1)*columns+hiddenX-1] = img;
        return true;
    }


    /**
     * 测试游戏是否结束
     *
     * @return {@code true} or {@code false}
     */
    private boolean checkGameOver(){

        for(int i=0;i<rows*columns;i++){
            if(index[i] != i){
                return false;
            }
        }
        return true;
    }

    /**
     * 隐藏图片自动移动,打乱拼图的排序
     *
     * @param size 图片移动次数,一般为图片数量*10
     */
    private void hiddexImageAutoMove(int size){
        Random random = new Random();
        int moveX;
        int moveY;
        for(int i=0;i<size;i++){
            moveX = hiddenX;
            moveY = hiddenY;
            /*
             * 0: 上移
             * 1: 下移
             * 2: 左移
             * 3: 右移
             */
            switch (random.nextInt(4)){
                case 0: moveY--;break;
                case 1: moveY++;break;
                case 2: moveX--;break;
                case 3: moveX++;break;
                default:break;
            }

            if(hiddenImageCanMove(moveX,moveY)){
                int img = index[(moveY-1)*columns+moveX-1];
                index[(moveY-1)*columns+moveX-1] = index[(hiddenY-1)*columns+hiddenX-1];
                index[(hiddenY-1)*columns+hiddenX-1] = img;

                hiddenX = moveX;
                hiddenY = moveY;

            }
        }


    }

}