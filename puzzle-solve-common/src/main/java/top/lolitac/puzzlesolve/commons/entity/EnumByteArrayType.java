package top.lolitac.puzzlesolve.commons.entity;

/**
 * 图片类型
 */
public enum EnumByteArrayType {
    /**
     * argb类型的byteArray
     * 四个字节定义一个像素
     */
    ARGB(4);

    private EnumByteArrayType(int size){
        this.size = size;
    }

    /**
     *  一个像素所占字节大小
     */
    private int size;
    public int getSize(){return size;}
}
