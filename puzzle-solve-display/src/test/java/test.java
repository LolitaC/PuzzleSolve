import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.stage.Stage;
import top.lolitac.puzzlesolve.commons.entity.ByteArrayImage;
import top.lolitac.puzzlesolve.commons.entity.EnumByteArrayType;
import top.lolitac.puzzlesolve.commons.util.ImageUtil;
import top.lolitac.puzzlesolve.helper.GameHelper;

import java.util.List;

public class test extends Application{

    // Drawing Surface (Canvas)
    private GraphicsContext gc;
    private Canvas canvas;
    private Group root;

    @Override
    public void start(Stage primaryStage) throws Exception {

//        String url = "puzzle/javafx-documentation.png";
//        GameHelper gameHelper = new GameHelper(url,2,2);

        int[] imageSize ;
        //图片缓存区， rgba格式
        List<ByteArrayImage> list = ImageUtil.getByteArrayList("puzzle/javafx-documentation.png", EnumByteArrayType.ARGB,2,2);
        ByteArrayImage imageData = list.get(1);

        //图片高度
        int image_height = imageData.getHeight();
        //图片宽度
        int image_width = imageData.getWidth();

        root = new Group();
        canvas = new Canvas(200, 200);

        //改变画布中（0，0）点位置为setTranslateX与setTranslateY设置的位置
        canvas.setTranslateX(100);
        canvas.setTranslateY(100);
        gc = canvas.getGraphicsContext2D();
        PixelWriter pixelWriter = gc.getPixelWriter();
        pixelWriter.setPixels(0,0,image_width,image_height,PixelFormat.getByteBgraInstance(),imageData.getByteArray(),0,image_width*4);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, 200, 200));
        primaryStage.show();


    }
}
