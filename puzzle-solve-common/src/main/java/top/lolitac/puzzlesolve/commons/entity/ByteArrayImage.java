package top.lolitac.puzzlesolve.commons.entity;

public class ByteArrayImage {
    private byte[] image;
    private int width;
    private int height;
    private EnumByteArrayType type;

    /**
     * x  x coordinate of the list{@code top.lolitac.puzzlesolve.commons.util.ImageUtil.getByteArrayList}
     */
    private int x;
    /**
     * y  y coordinate of the list{@code top.lolitac.puzzlesolve.commons.util.ImageUtil.getByteArrayList}
     */
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public EnumByteArrayType getType() { return type; }

    public byte[] getByteArray() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ByteArrayImage(byte[] image, int width, int height, EnumByteArrayType type,int x,int y){
        this.image = image;
        this.width = width;
        this.height = height;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public void reset(byte[] image, int width, int height, EnumByteArrayType type,int x,int y){
        this.image = image;
        this.width = width;
        this.height = height;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "this image is ("+ x +","+ y +")";
    }
}
