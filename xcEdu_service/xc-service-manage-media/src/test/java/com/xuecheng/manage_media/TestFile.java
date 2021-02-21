package com.xuecheng.manage_media;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;

/**
 * @author Atom
 */
public class TestFile {


    /**
     * 测试文件分块
     *
     * @throws IOException
     */
    @Test
    public void testChunk() throws IOException {
        //源文件
        File sourceFile = new File("/Users/atom/Desktop/video.mp4");

        //块文件目录
        String chunkFileFolder = "/Users/atom/Desktop/chunks/";
        //先定义块文件大小
        long chunkFileSize = 1 * 1024 * 1024;// 1M

        //块数
        long chunkFileNum = (long) Math.ceil(sourceFile.length() * 1.0 / chunkFileSize);

        //根据块的字节数，去读源文件，当读到块的大小，创建下一块
        //创建块文件的读对象
        RandomAccessFile raf_read = new RandomAccessFile(sourceFile, "r");

        byte[] buffer = new byte[1024];

        for (long i = 0; i < chunkFileNum; i++) {
            File chunkFile = new File(chunkFileFolder + i);
            //创建块文件的写对象
            RandomAccessFile raf_write = new RandomAccessFile(chunkFile, "rw");
            int len;
            while ((len = raf_read.read(buffer)) != -1) {
                raf_write.write(buffer, 0, len);
                //如果这个块文件的大小达到1M，开始写下一块
                if (chunkFile.length() >= chunkFileSize) {
                    break;
                }
            }
            raf_write.close();
        }
        raf_read.close();
    }


    /**
     * 测试文件合并
     *
     * @throws IOException
     */
    @Test
    public void testMergeFile() throws IOException {
        //块文件目录
        String chunkFileFolderPath = "/Users/atom/Desktop/chunks/";
        //块文件目录对象
        File chunkFileFolder = new File(chunkFileFolderPath);
        //块文件列表
        File[] files = chunkFileFolder.listFiles();
        List<File> fileList = Arrays.asList(files);

        //将块文件按名称升序排序
        fileList.sort((o1, o2) -> {
            // 返回值为int类型，大于0表示正序，小于0表示逆序
            if (Integer.parseInt(o1.getName()) > Integer.parseInt(o2.getName())) {
                return 1;
            }
            return -1;
        });

        //合并文件
        File mergeFile = new File("/Users/atom/Desktop/merge/merge_video.mp4");
        mergeFile.createNewFile();
        //创建文件写对象
        RandomAccessFile raf_write = new RandomAccessFile(mergeFile, "rw");
        byte[] buffer = new byte[1024];
        for (File chunkFile : fileList) {
            //创建块文件的读对象
            RandomAccessFile raf_read = new RandomAccessFile(chunkFile, "r");
            int len;
            while ((len = raf_read.read(buffer)) != -1) {
                raf_write.write(buffer, 0, len);
            }
            raf_read.close();
        }
        raf_write.close();

    }
}
