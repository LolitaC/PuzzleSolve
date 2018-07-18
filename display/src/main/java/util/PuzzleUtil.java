package util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Puzzle相关类
 *
 * @author LolitaC
 * @since 2018-7-18
 */
public class PuzzleUtil {

    /**
     * 获取puzzle资源目录的一层目录路径对象
     * @return puzzle资源目录一层的目录路径对象 {@code DirectoryStream}
     * @throws URISyntaxException 获取puzzle资源目录异常
     * @throws IOException 创建puzzle目录迭代对象异常
     */
    public static DirectoryStream<Path> getAllPuzzleDir() throws URISyntaxException, IOException {
        final String FOLDER = "puzzle/";
        Path path = Paths.get( PuzzleUtil.class.getClassLoader().getResource(FOLDER).toURI());

        return Files.newDirectoryStream(path);
    }

    /**
     * 获取{@code path}目录下所有的文件
     * @param path 需要访问的目录
     * @return {@code path}目录下所有的文件
     * @throws IOException 创建{@code DirectoryStream}失败
     */
    public static DirectoryStream<Path> getAllPuzzle(Path path) throws IOException {

        return Files.newDirectoryStream(path);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        getAllPuzzleDir();
    }

}
