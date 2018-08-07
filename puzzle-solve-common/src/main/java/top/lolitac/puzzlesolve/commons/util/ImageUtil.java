package top.lolitac.puzzlesolve.commons.util;


import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;
import top.lolitac.puzzlesolve.commons.entity.ByteArrayImage;
import top.lolitac.puzzlesolve.commons.entity.EnumByteArrayType;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片工具类
 * 使用了javafx的类，需要启动javafx.application线程，否则抛出{@code RuntimeException}
 *
 * @see Image
 * @deprecated 使用ImageView可直接对图片进行截图
 */
@Deprecated
public class ImageUtil {
    private ImageUtil(){}

    /**
     * 获取图片的argb字节数组
     *
     * @param url 图片路径
     * @return ByteArrayImage 图片字节数组
     */
    public static ByteArrayImage getByteArrayARGB(String url){
        return getByteArrayList(url, EnumByteArrayType.ARGB,1,1).get(0);
    }

    public static List<ByteArrayImage> getByteArrayListARGB(String url, int rows, int columns){
        return getByteArrayList(url, EnumByteArrayType.ARGB,rows,columns);
    }

    /**
     * 获取图片字节数组
     *
     * @param url 图片路径
     * @param type 字节数组类型
     * @return ByteArrayImage 图片字节数组
     */
    public static ByteArrayImage getByteArray(String url,EnumByteArrayType type){
        return getByteArrayList(url,type,1,1).get(0);
    }

    /**
     * 获取图片字节数组列表对象
     *
     * @param url 图片路径
     * @param type 字节数组类型
     * @param rows 切割后的行数
     * @param columns 切割后的列数
     * @return List<ByteArrayImage> 图片列表
     */
    public static List<ByteArrayImage> getByteArrayList(String url, EnumByteArrayType type, int rows, int columns){
        int size = type.getSize();
        Image image = new Image(url);
        int width = (int)image.getWidth()/columns;
        int height = (int)image.getHeight()/rows;
        
        byte[][] imageByteArray = new byte[rows*columns][width*height*size];

        PixelReader pixelReader = image.getPixelReader();


        int index = 0;
        for(int y=0;y<rows;y++){
            for(int x=0;x<columns;x++){
                /**
                 * pixelReader.getPixels
                 * @param scanlineStride 一行数据与另一行数据之间的距离（每次读取一行）
                 */
                switch (type){
                    case ARGB:
                        pixelReader.getPixels(x*width,y*height,width,height, WritablePixelFormat.getByteBgraInstance(),imageByteArray[index++],0,width*size);
                        break;
                    default:
                }
            }
        }

        List<ByteArrayImage> list = new ArrayList<>();
        int x = 0;
        int y = 1;
        for(byte[] img : imageByteArray){
            if(++x > columns){
                x = 1;
                y++;
            }
            list.add(new ByteArrayImage(img,width,height,type,x,y));
        }
        return list;
    }

}
