package top.lolitac.puzzlesolve.commons.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 容器类工具
 */
public class CollectionUtil {
    private CollectionUtil(){}

    /**
     *
     * @param list
     * @return
     */
    public static List randomQueue(List list){
        List randomQueue = new ArrayList();
        Random random = new Random();
        int size = 0;
        while( (size = list.size()) != 0 ){
            int index = random.nextInt(size);
            randomQueue.add(list.get(index));
            list.remove(index);
        }

        return randomQueue;
    }
}
