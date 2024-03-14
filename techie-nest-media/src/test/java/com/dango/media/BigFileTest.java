package com.dango.media;

import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dango
 * @description * 文件分块测试类
 * * 用于测试大文件处理，特别是对于那些需要断点续传功能的应用场景。
 * @date 2024-03-12
 */
@SpringBootTest
public class BigFileTest {
    @Resource
    private MinioClient minioClient;

    /**
     * 测试文件分块的方法
     *
     * @throws IOException 抛出IO异常
     */
    @Test
    public void testChunk() throws IOException {
        // 指定需要分块的源文件路径
        File sourceFile = new File("/Users/dango/Movies/sora/Sora2.mp4");
        // 分块文件存储目录
        String chunkPath = "/Users/dango/Movies/sora/chunks/";
        // 创建分块文件存储目录
        File chunkFolder = new File(chunkPath);
        if (!chunkFolder.exists()) {
            chunkFolder.mkdirs(); // 如果目录不存在，则创建目录
        }

        // 定义每个块文件的大小，这里以5MB为例
        long chunkSize = 1024 * 1024 * 5;
        // 计算需要分多少块
        long chunkNum = (long) Math.ceil(sourceFile.length() * 1.0 / chunkSize);
        System.out.println("分块总数：" + chunkNum);

        // 设置文件读取的缓冲区大小
        byte[] b = new byte[1024];
        // 使用RandomAccessFile进行文件的随机读取
        RandomAccessFile raf_read = new RandomAccessFile(sourceFile, "r");

        // 循环分块
        for (int i = 0; i < chunkNum; i++) {
            // 为每一块创建一个新文件
            File file = new File(chunkPath + i);
            // 如果文件已存在，先删除旧的文件
            if (file.exists()) {
                file.delete();
            }
            // 创建新的块文件
            boolean newFile = file.createNewFile();
            if (newFile) {
                // 使用RandomAccessFile写入数据到新的块文件
                RandomAccessFile raf_write = new RandomAccessFile(file, "rw");
                int len = -1;
                // 读取数据并写入新的块文件，直到达到块文件的大小限制
                while ((len = raf_read.read(b)) != -1) {
                    raf_write.write(b, 0, len);
                    // 如果块文件的大小已达到预设值，则停止写入
                    if (file.length() >= chunkSize) {
                        break;
                    }
                }
                // 关闭块文件的写入流
                raf_write.close();
                System.out.println("完成分块：" + i);
            }
        }
        // 关闭源文件的读取流
        raf_read.close();
    }

    /**
     * 测试文件合并方法
     *
     * @throws IOException 抛出IO异常
     */
    @Test
    public void testMerge() throws IOException {
        // 指定存储块文件的目录
        File chunkFolder = new File("/Users/dango/Movies/sora/chunks/");
        // 指向原始完整文件的引用（用于校验）
        File originalFile = new File("/Users/dango/Movies/sora/Sora2.mp4");
        // 创建一个文件，用于存储合并后的视频
        File mergeFile = new File("/Users/dango/Movies/sora/Sora2_merge.mp4");
        // 如果合并的文件已存在，则删除重新创建
        if (mergeFile.exists()) {
            mergeFile.delete();
        }
        mergeFile.createNewFile();

        // 使用RandomAccessFile进行文件的写操作
        RandomAccessFile raf_write = new RandomAccessFile(mergeFile, "rw");
        // 设置文件指针到文件开头
        raf_write.seek(0);
        // 定义缓冲区
        byte[] b = new byte[1024];
        // 获取所有分块文件
        File[] fileArray = chunkFolder.listFiles();
        // 将文件数组转为列表，以便排序
        List<File> fileList = Arrays.asList(fileArray);
        // 对文件进行排序，以确保按正确的顺序进行合并
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                // 排序逻辑，根据文件名中的数字排序
                return Integer.parseInt(o1.getName()) - Integer.parseInt(o2.getName());
            }
        });

        // 遍历分块文件，进行合并
        for (File chunkFile : fileList) {
            // 对每个块文件使用RandomAccessFile进行读操作
            RandomAccessFile raf_read = new RandomAccessFile(chunkFile, "rw");
            int len = -1;
            // 读取块文件内容，并写入合并文件
            while ((len = raf_read.read(b)) != -1) {
                raf_write.write(b, 0, len);
            }
            // 关闭当前块文件的读取流
            raf_read.close();
        }
        // 关闭合并文件的写入流
        raf_write.close();

        // 文件合并后进行校验
        try (
                FileInputStream fileInputStream = new FileInputStream(originalFile);
                FileInputStream mergeFileStream = new FileInputStream(mergeFile);
        ) {
            // 计算原始文件和合并后文件的MD5值
            String originalMd5 = DigestUtils.md5Hex(fileInputStream);
            String mergeFileMd5 = DigestUtils.md5Hex(mergeFileStream);
            // 校验合并后文件是否与原始文件一致
            if (originalMd5.equals(mergeFileMd5)) {
                System.out.println("合并文件成功");
            } else {
                System.out.println("合并文件失败");
            }
        }
    }

    /**
     * 上传分块文件至MinIO存储服务
     */
    @Test
    public void uploadChunk() {
        // 指定分块文件存储目录
        String chunkFolderPath = "/Users/dango/Movies/sora/chunks/";
        File chunkFolder = new File(chunkFolderPath);
        // 获取目录下的所有分块文件
        File[] files = chunkFolder.listFiles();
        // 遍历分块文件并上传到MinIO
        for (int i = 0; i < files.length; i++) {
            try {
                // 构建上传参数，包括桶名、对象名和文件路径
                UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                        .bucket("testbucket2")
                        .object("chunk/" + files[i].getName())
                        .filename(files[i].getAbsolutePath())
                        .build();
                // 执行上传操作
                minioClient.uploadObject(uploadObjectArgs);
                System.out.println("上传分块成功" + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试文件合并方法，要求分块文件最小5MB
     * @throws Exception 抛出异常
     */
    @Test
    public void test_merge() throws Exception {
        // 构建分块文件来源列表
        List<ComposeSource> sources = Stream.iterate(0, i -> ++i)
                .limit(32)
                .map(i -> ComposeSource.builder()
                        .bucket("testbucket2")
                        .object("chunk/".concat(Integer.toString(i)))
                        .build())
                .collect(Collectors.toList());

        // 构建文件合并参数
        ComposeObjectArgs composeObjectArgs = ComposeObjectArgs.builder()
                .bucket("testbucket2")
                .object("merge01.mp4")
                .sources(sources)
                .build();
        // 执行合并操作
        minioClient.composeObject(composeObjectArgs);
    }

    /**
     * 清除MinIO中的分块文件
     */
    @Test
    public void test_removeObjects() {
        // 构建待删除分块文件列表
        List<DeleteObject> deleteObjects = Stream.iterate(0, i -> ++i)
                .limit(60)
                .map(i -> new DeleteObject("chunk/".concat(Integer.toString(i))))
                .collect(Collectors.toList());

        // 构建删除参数
        RemoveObjectsArgs removeObjectsArgs = RemoveObjectsArgs.builder()
                .bucket("testbucket2")
                .objects(deleteObjects)
                .build();
        // 执行删除操作，并处理可能的错误
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(removeObjectsArgs);
        results.forEach(r -> {
            try {
                DeleteError deleteError = r.get();
                // 这里可以添加错误处理逻辑
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}
