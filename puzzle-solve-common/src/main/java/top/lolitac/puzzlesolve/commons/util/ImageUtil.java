package top.lolitac.puzzlesolve.commons.util;


import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;

/**
 * 图片工具类
 */
public class ImageUtil {
    private ImageUtil(){}

    /**
     * 图片类型
     */
    public enum ByteArrayType{
        /**
         * argb类型的byteArray
         * 四个字节定义一个像素
         */
        ARGB(4);

        private ByteArrayType(int size){
            this.size = size;
        }

        /* 一个像素所占字节大小 */
        private int size;
        public int getSize(){return size;}
    }

    /**
     * 获取图片的argb字节数组
     *
     * @param url 图片路径
     * @return byte[] 图片的字节数组
     */
    public static byte[] getByteArrayARGB(String url){
        return getByteArray(url, ByteArrayType.ARGB);
    }

    /**
     * 获取图片字节数组
     *
     * @param url 图片路径
     * @param byteArrayType 字节数组类型
     * @return byte[] 图片的字节数组
     */
    public static byte[] getByteArray(String url,ByteArrayType byteArrayType){
        int size = byteArrayType.getSize();
        Image image = new Image(url);
        final int IMAGE_HEIGHT = (int)image.getHeight();
        final int IMAGE_WIDTH = (int)image.getWidth();
        byte[] imageByteArray = new byte[IMAGE_HEIGHT*IMAGE_WIDTH*size];

        PixelReader pixelReader = image.getPixelReader();
        /**
         * pixelReader.getPixels
         * @param scanlineStride 一行数据与另一行数据之间的距离（每次读取一行）
         */
        pixelReader.getPixels(0,0,IMAGE_WIDTH,IMAGE_HEIGHT, WritablePixelFormat.getByteBgraInstance(),imageByteArray,0,IMAGE_WIDTH*size);
        return imageByteArray;
    }
}
