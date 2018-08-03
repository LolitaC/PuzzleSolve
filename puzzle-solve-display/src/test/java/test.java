import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.stage.Stage;

public class test extends Application{

    // Drawing Surface (Canvas)
    private GraphicsContext gc;
    private Canvas canvas;
    private Group root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //加载图片
        Image image = new Image("puzzle/javafx-documentation.png");

        PixelReader pixelReader = image.getPixelReader();
        //图片高度
        int image_height = (int)image.getHeight();
        //图片宽度
        int image_width = (int)image.getWidth();
        //图片缓存区， rgba格式
        byte[] imageData = new byte[image_height*image_width*4];
        /**
         * pixelReader.getPixels
         * @param scanlineStride 一行数据与另一行数据之间的距离（每次读取一行）
         */
        pixelReader.getPixels(0,0,image_width,image_height, WritablePixelFormat.getByteBgraInstance(),imageData,0,image_width*4);

        root = new Group();
        canvas = new Canvas(200, 200);

        //改变画布中（0，0）点位置为setTranslateX与setTranslateY设置的位置
        canvas.setTranslateX(100);
        canvas.setTranslateY(100);
        gc = canvas.getGraphicsContext2D();
        PixelWriter pixelWriter = gc.getPixelWriter();
        pixelWriter.setPixels(0,0,image_width,image_height,PixelFormat.getByteBgraInstance(),imageData,0,image_width*4);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, 200, 200));
        primaryStage.show();
    }
}
